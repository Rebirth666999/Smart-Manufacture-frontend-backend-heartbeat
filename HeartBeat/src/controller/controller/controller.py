import rclpy
from rclpy.node import Node
from rclpy.action import ActionClient
from interfaces.action import Arm1Test,Arm2Test,IsMoving,BeltMove,BeltStop,GetStatus,MoveMood,MoveTime,StopTime,MoveToLeft,MoveToRight,Fetch,GripperOpen,HeadMove,MultipleTurn
from std_msgs.msg import String
from flask import Flask,request,jsonify
from flask_cors import CORS
from threading import Thread
import time
import requests
import websocket
from datetime import datetime
heartbeat_msg=[] #心跳信息
task_finish_msg=[] #动作执行完成后的反馈信息
task_sleep=False #当前任务是否停止
error_msg="no error"
client=None
server_ip="192.168.1.102"
db1={
    "db_name":"test",
    "db_user":"test",
    "db_password":"123" ,
    "db_ip":"192.168.1.108",
    "eq_name_col":"eq_name",
    "eq_link_col":"eq_link",
    "op_name_col":"op_name",
    "op_id_col":"op_id",
    "eq_id_col":"eq_id",
    "eq_tb":"equipment" ,
    "op_tb":"equipment_operation"
}
db2={
    "db_name":"industry_dev",
    "db_user":"chy",
    "db_password":"hit415ices@" ,
    "db_ip":"124.221.52.73",
    "eq_name_col":"eq_name",
    "eq_link_col":"eq_ip",
    "op_name_col":"eao_url",
    "op_id_col":"eao_code",
    "eq_id_col":"eq_code",
    "eq_tb":"ices_equipment" ,
    "op_tb":"ices_equipment_atom_operation"
}
db={
    "db_name":"industry_dev",
    "db_user":"ices",
    "db_password":"ices" ,
    "db_ip":"192.168.1.110",
    "eq_name_col":"eq_name",
    "eq_link_col":"eq_ip",
    "op_name_col":"eao_url",
    "op_id_col":"eao_code",
    "eq_id_col":"eq_code",
    "eq_tb":"ices_equipment" ,
    "op_tb":"ices_equipment_atom_operation"
}
#####################tool################################
import re
#横杠命名（snake_case）转换为驼峰命名（CamelCase）
def camel_to_snake(name):
    # 使用正则表达式匹配大写字母
    s1 = re.sub('(.)([A-Z][a-z]+)', r'\1_\2', name)
    # 将剩余的大写字母转换为小写，并添加下划线
    return re.sub('([a-z0-9])([A-Z])', r'\1_\2', s1).lower()
#驼峰命名（CamelCase）转换为 横杠命名（snake_case）
def snake_to_camel(name):
    # 使用正则表达式将下划线后的字符转换为大写
    return ''.join(word.capitalize() or '_' for word in name.split('_'))
#检查ip地址格式是否正确
def is_valid_url(url):
    # 定义 URL 的正则表达式
    url_pattern = re.compile(
        r'^(https?://)?'  # 协议头（http 或 https，可选）
        r'([a-zA-Z0-9.-]+)'  # 域名或 IP 地址
        r'(:\d+)?'  # 端口号（可选）
        r'(/[^ ]*)?$'  # 路径（可选）
    )
    # 正则表达式用于匹配 IP 地址和端口号
    # 格式为：IP 地址 + 冒号 + 端口号
    # url_pattern = re.compile(
    #     r'^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d+$'
    # )
    # 使用正则表达式匹配 URL
    return bool(url_pattern.match(url))
#全局异常处理,将错误日志保存到当前文件夹的子文件log下
import sys
import traceback
import os
from datetime import datetime
import threading
# 获取当前日期和时间
current_datetime = datetime.now()
def global_exception_handler(exc_type, exc_value, exc_traceback):
    print("全局异常处理器捕获异常:", file=sys.stderr)
    traceback.print_exception(exc_type, exc_value, exc_traceback, file=sys.stderr)
    with open("error.log", "a") as log_file:
        traceback.print_exception(exc_type, exc_value, exc_traceback, file=log_file)
        #

# 设置全局异常处理器
sys.excepthook = global_exception_handler

def thread_exception_handler(args):
    print("线程异常处理器捕获异常:", args.exc_value)
    e={"error":"error happened in controller node"}
    client.send_msg(json.dumps(e))
    with open("error.log", "a") as log_file:
        log_file.write("\n"+str(current_datetime)+"\n")
        traceback.print_exception(args.exc_type, args.exc_value, args.exc_traceback, file=log_file)
    print("程序结束，查看错误日志")
    os._exit(0)

# 设置线程异常处理器
threading.excepthook = thread_exception_handler

##################mysql#########################################3

eq_id_name_dict = {}  # 设备id-名称字典
eq_id_link_dict = {}  # 设备id-连接方式字典
op_id_name_dict = {}  # 操作id-名称字典

import mysql.connector
def sql_op():#在数据库中获取到所有设备信息以及所有操作信息，存储在字典中
    # 配置数据库连接参数
    config = {
        'user': db["db_user"],
        'password': db["db_password"],
        'host': db["db_ip"],
        'database': db["db_name"],
        'raise_on_warnings': True
    }
    # 连接到 MySQL 数据库
    conn = mysql.connector.connect(**config)
    # 创建一个 Cursor 对象并使用它执行 SQL 查询(建立设备id-设备名称和设备id-设备连接方式字典)
    cursor = conn.cursor()

    # 查询数据
    cursor.execute(f"SELECT * FROM {db['eq_tb']}")
    #获取列名
    equipment_names=cursor.column_names
    # 获取查询结果
    rows = cursor.fetchall()
    results=[dict(zip(equipment_names,row)) for row in rows]
    for result in results:
        eq_id_name_dict[result[db["eq_id_col"]]]=result[db["eq_name_col"]]
        eq_id_link_dict[result[db["eq_id_col"]]]=result[db["eq_link_col"]]
    #建立操作id-设备名称字典
    cursor=conn.cursor()
    cursor.execute(f"select * from {db['op_tb']}")
    operation_names=cursor.column_names
    rows=cursor.fetchall()
    results=[dict(zip(operation_names,row)) for row in rows]
    for result in results:
        op_id_name_dict[result[db["op_id_col"]]]=result[db["op_name_col"]]
    # 关闭 Cursor 和 Connection 对象
    cursor.close()
    conn.close()
    print("--------------------------------设备表--------------------------------")
    for item in eq_id_name_dict.items():
        print(f"{item[0]}:{item[1]}")
    print("--------------------------------操作表--------------------------------")
    for item in op_id_name_dict.items():
        print(f"{item[0]}:{item[1]}")
    print("数据库数据读取完毕！")
sql_op()
############################flask####################################
control_node=None
Tasks=[]#当前要执行的所有任务
app=Flask(__name__)
op_to_do=[]#待完成的动作
op_done=[]#已经完成的动作
op_doing=[]#正在执行的操作
now_heartbeat_num=0 #当前任务的操作总数
now_task=None #现在正在执行的任务
task_done=[] #已经完成的任务
task_info=[] #用于保存传给后端的任务详情信息
op_info=[] #用于保存传给后端的当前任务中操作的详情
CORS(app)
flag=True
@app.route("/execute",methods=["POST"])
def test():
    global flag
    if not request.json:
        print("error")
        return jsonify({"error": "Invalid json data"}),400
    if control_node==None:
        print("此时控制节点未初始化完成，请等待")
        while control_node==None:
            time.sleep(0.1)
        print(f"控制节点:{control_node}")
        print("控制节点为初始化完成")
    data=request.get_json()
    #打印控制节点信息
    if flag:
        print("----------------------------控制节点信息 --------------------------------")
        for key,value in data["areaControl"][0].items():
            print(f'{key}:{value}')
        print("-----------------------------------------------------------------------")
        flag=False
    print(f'\n++++++++++++接受到新任务,任务id : {data["productionTask"]["ptId"]}，任务优先级 : {data["productionTask"]["ptPriority"]}，任务是否为抢占式 : {data["productionTask"]["preemptive"]}++++++++++++\n')
    Tasks.append(data)
    task_info.append(data)
    #任务信息，若更高优先级任务到达，可抢占式地执行，是否抢占由data["productionTask"]["ptPriority"]确定
    task_sort(data)
    return jsonify({"success": "received json data"}),200

static_now_task_ptPriority=None
def task_sort(task_data):#对所有任务按优先级排序
    global op_done,op_to_do
    #按优先级对任务进行排序
    Tasks.sort(key=lambda x :-x["productionTask"]["ptPriority"])
    production_task = task_data["productionTask"]
    preemptive=production_task["preemptive"]
    if preemptive:#若为抢占式
        if is_preempt():#如果正在执行的任务的优先级低，则当前任务停止执行
            print("当前任务被抢占")
            op_done=op_done+op_to_do
            op_to_do=[]
    pt_id = production_task["ptId"]
    pt_latest_endtime = production_task["ptLatestEndtime"]
    pt_num = production_task["ptNum"]
    pt_priority = production_task["ptPriority"]
    

#当为抢占式时，判断当前正在执行的任务是否被抢占执行
def is_preempt():
    global static_now_task_ptPriority
    if static_now_task_ptPriority is None :
        return False
    for task in Tasks:
        if static_now_task_ptPriority < task["productionTask"]["ptPriority"] :
            return True
    return False
            
#--------------------上为接受任务，并对任务按优先级排序，下为在Tasks中取出任务，执行------------------
topic_sub=[]
#向每个flask设备请求心跳信息的线程集合
flask_heartbeat_url_list=[]
def task_add():#将未执行的任务列表的第一个任务（优先级最高的），取出执行
    global op_done,static_now_task_ptPriority,now_heartbeat_num,topic_sub,control_node,op_doing,flask_heartbeat_url_list,now_task,task_finish_msg,task_info
    while True:
        if len(Tasks)>0:
            task_data=Tasks.pop(0)
            now_task=task_data
            static_now_task_ptPriority=task_data["productionTask"]["ptPriority"]
            now_heartbeat_num=0
            topic_set=set()
            for op in task_data["processRoute"]:#当前任务需要的话题数量，即当前任务所涉及的所有设备种类
                if eq_id_link_dict[ op["eqId"]]!="sleep":
                    topic_set.add(op["eqId"])
            now_heartbeat_num=len(topic_set)
            #在新任务到来时每，将上次任务的无用数据清空。即防止订阅的话题和请求的flask接口重复。也使下一个任务到来前仍能接收到设备心跳
            op_done=[]
            task_finish_msg=[]
            #将订阅的话题列表清空，防止重复订阅一个话题
            while topic_sub:
                sub=topic_sub.pop(0)
                control_node.destroy_subscription(sub)
            topic_sub=[]
            #清空订阅flask设备心跳的列表
            flask_heartbeat_url_list=[]
            print("====================================新任务开始====================================================")
            task_dispatch(task_data)
            op_doing=[]
            task_info=[]
        else:
            static_now_task_ptPriority=None#当所有任务执行完毕后，重置该静态变量
            
def task_dispatch(task_data):#分派任务，发送动作请求，订阅话题
    global control_node,now_heartbeat_num,error_msg
    process_route = task_data["processRoute"]

    #如果需要对当前任务的工艺流程进行排序，此函数未实现，因为默认在后端就已经排好序
    #process_route_sort(process_route)

    #需要订阅的话题集合set,一个设备对应一个话题，其中存储设备名
    topic_set=set()
    #flask设备心跳请求url集合
    flask_eq_set=set()
    global op_to_do,op_done,op_doing,topic_sub,flask_heartbeat_url_list,task_finish_msg,task_sleep,task_done,task_info
    op_num=len(now_task["processRoute"])
    print(f'当前执行的任务{now_task["productionTask"]["ptId"]}所含的操作总数:{op_num}')
    for process in process_route:
        prd_pid = process["prdId"]
        # eq_id = int(process["eqId"])
        eq_id=process["eqId"]
        eq_name=eq_id_name_dict[eq_id]
        eq_link=eq_id_link_dict[eq_id]#连接方式，为ROS2或者flask对应的地址
        #op_id = int(process["opId"])#动作id
        op_id = process["opId"]
        print({eq_link})
        op_name=op_id_name_dict[op_id]
        prd_pre_process = process["prePrdId"]
        #prd_para = process["prdPara"]#暂时未用
        op_param=process["opParam"]#动作接口的参数，为字典
        if eq_link=="ROS2":
            action_name=eq_name+"_"+op_name
            action_interface=globals().get(op_name)
            #为这个动作创建action_client
            action_client=ActionClient(control_node,action_interface,action_name)
            #为这个动作对应的接口赋值
            goal=action_interface.Goal()
            print(f"准备进行ROS2设备操作：prdId: {prd_pid}, eqId: {eq_id}, eq_name: {eq_name} , opId: {op_id} , op_name:{op_name} , action_name:{action_name} , action_interface:{action_interface} prePrdId: {prd_pre_process}")
            print("[",end="")
            if len(op_param)==0:
                print("操作参数值:该动作无参")
            else:
                print("操作参数值:")
                for key,value in op_param.items():
                    print(f"key:{key} : value: {value}")
                    if hasattr(goal, key):  # 检查 goal 是否有该属性
                        attr_type = type(getattr(goal, key))  # 获取属性的类型
                        # print(f"Type of goal.{key}: {attr_type}")
                        # 检查传入值的类型是否与目标类型一致,并且不是list类型
                        if not isinstance(value, attr_type) and (not isinstance(value, list)):
                            try:
                                # 尝试将值转换为目标类型
                                converted_value = attr_type(value)
                                print(f'将任务{now_task["productionTask"]["ptId"]}中对ROS2的设备{eq_id}的操作{op_id}中的参数{key}转换为 {attr_type}: {converted_value}')
                                value = converted_value
                            except (ValueError, TypeError) as e:
                                print(f'操作参数错误： !!!将任务{now_task["productionTask"]["ptId"]}中对ROS2的设备{eq_id}的操作{op_id}中的参数{key}转换为 {attr_type} 时失败，原类型:{type(value)}!!!')
                                continue  # 跳过当前属性的设置
                        setattr(goal, key, value)  # 设置属性
                    else:
                        print(f'操作参数错误： !!!任务{now_task["productionTask"]["ptId"]}中对ROS2的设备{eq_id}的操作{op_id}中的不含参数{key}!!!')
            print("]")
            #创建对应的话题
            topic_set.add(eq_name)
            action_op={
                "eq_link":eq_link,
                "action_client":action_client,
                "action_interface":goal,
                "pre_op":prd_pre_process,
                "op_pid":prd_pid,
                "eq_id":eq_id,
                "op_id":op_id
            }
            op_to_do.append(action_op)
        elif eq_link=="sleep":
            #生产线停止x秒
            op={
                "eq_link":eq_link,
                "pre_op":prd_pre_process,
                "op_pid":prd_pid,
                "op_param":op_param,
                "eq_id":eq_id
            }
            print(f"准备进行sleep操作： prdId: {prd_pid}, eqId: {eq_id}, eq_name: {eq_name} , opId: {op_id} , op_name:{op_name} ,op_parma:{op_param} ,prePrdId: {prd_pre_process}")
            op_to_do.append(op)
        #对于flask设备
        elif (is_valid_url(eq_link) and eq_link!="ROS2" and eq_link!="sleep"):
            #请求的url地址
            # print(f"url:{url}")
            url=eq_link+"/"+op_name
            headers = {
                "Content-Type": "application/json"
            }
            op={
                "eq_link":eq_link,
                "url":url,
                "headers":headers,
                "op_param":op_param,
                "pre_op":prd_pre_process,
                "op_pid":prd_pid,
                "eq_name":eq_name,
                "eq_id":eq_id,
                "op_id":op_id,
            }
            print(f'准备进行flask设备操作：wq_link:{eq_link}prdId: {prd_pid}, eqId: {eq_id}, eq_name: {eq_name} , opId: {op_id} , op_name:{op_name} ,url: {url},  prePrdId: {prd_pre_process}')
            op_to_do.append(op)
            flask_eq_set.add(eq_link+'/heartbeat')#一个设备可能有多个操作，因此必须设置set
        
        else:
            error_msg=(f"设备连接属性有误，请及时查看,错误eq_link:{eq_link}")
            print(f"!!!设备连接属性有误，请及时查看，错误eq_link:{eq_link}")

    #为每个设备创建对应的话题
    for eq_name in topic_set:
        topic_name=eq_name+"_heartbeat"
        sub=control_node.create_subscription(String,topic_name,control_node.listener_callback,20)
        topic_sub.append(sub)

    #定时请求flask设备的状态
    def request_flask_eq_heartbeat(flask_heartbeat_sign):
        global flask_heartbeat_url_list
        # url="http://"+heartbeat_url
        url=flask_heartbeat_sign["heartbeat_url"]
        headers={"Content-Type": "application/json"}
        while True:
            response=requests.post(url,json={},headers=headers)
            #print(f"heartbeat from {heartbeat_url}")
            #print(f":{response.json()}")
            global heartbeat_msg
            #print(f'heartbeat from {heartbeat_msg}')
            heartbeat_msg.append(response.json())
            indices = [item for item in flask_heartbeat_url_list if item == flask_heartbeat_sign]
            #print(indices)
            if len(indices)==0:
                break
            time.sleep(200)
    #为每个flask设备创建线程，定时请求状态
    for heartbeat_url in flask_eq_set:
        #该字典的作用是为了表示这个请求flask心跳的线程属于哪个大任务和设备操作，对于现在的生产环境，大任务id可能一样，但是设备操作id不一样
        flask_heartbeat_sign={
            "heartbeat_url":heartbeat_url,
            "eq_id":eq_id,
            "task":now_task["productionTask"]["ptId"],
            "op_id":op_id,
            "prd_id":prd_pid
        }
        flask_heartbeat_url_list.append(flask_heartbeat_sign)
        t=Thread(target=request_flask_eq_heartbeat,args=(flask_heartbeat_sign,))
        t.start()
    #为每个flask操作发起请求，并开辟一个线程异步等待结果
    def flask_task_resolve(op):
        global op_done,task_finish_msg
        url=op["url"]
        op_param=op["op_param"]
        headers=op["headers"]
        response=requests.post(url,json=op_param,headers=headers)
        if response.status_code==200:
            op_done.append(op)
            op_doing.remove(op)
            if "application/json" in response.headers.get("Content-Type", ""):
                task_finish_msg.append(response.json())
            else:
                print(f"!!!eq_id为{op['eq_id']}的flask设备,响应内容不是 JSON 格式!!!")
            print(f"[INFO] flask设备{op['eq_name']}操作执行完成,result:{response.json()}")
        else:
            print(f'flask设备{op["eq_name"]}执行出错')
    def sleep_task(op):
            op_doing.append(op)
            sleep_time=op["op_param"]["sleep_time"]
            begin_time=datetime.now().isoformat()
            print(f"开始停止{sleep_time}s")
            task_sleep=True
            time.sleep(sleep_time)
            finish_time=datetime.now().isoformat()
            print(f"停止{sleep_time}s结束")
            op_done.append(op)
            op_doing.remove(op)
            task_sleep=False
            #需要修改 op_id
            result={
               "eq_id":op["eq_id"],
                "op_name":"sleep",
                "op_id":13,
                "begin_time":begin_time,
               "finish_time":finish_time
            }
            task_finish_msg.append(result)
    #处理op_to_do中未完成的操作
    now_op_len=len(op_to_do)
    while op_to_do:
        op=op_to_do.pop(0)
        if op["eq_link"]=="ROS2" :
            if is_preop_done(op['pre_op']):#对于ROS2设备并且前序操作完成时
                op_doing.append(op)
                print(f"[INFO] 向ROS2设备{op['eq_id']} 发起动作请求:{op['op_id']}")
                action_client=op["action_client"]
                action_info=op["action_interface"]
                control_node.send_goal(action_info,action_client,op)
            else:
                op_to_do.append(op)
                time.sleep(0.1)
        elif op["eq_link"]=="sleep":#生产停止x秒
            if is_preop_done(op['pre_op']):#对于ROS2设备并且前序操作完成时
                t=Thread(target=sleep_task,args=(op,))
                t.start()
            else:
                op_to_do.append(op)
                time.sleep(0.1)
        #处理非ROS2设备，首先判断ip地址是否有效，再发送操作请求
        else:
            if is_preop_done(op['pre_op']):#对于flask设备并且前序操作完成时
                op_doing.append(op)
                op_str=json.dumps(op)
                t=Thread(target=flask_task_resolve,args=(op,))
                print(f"[INFO] 向flask设备{op['eq_id']}发送设备任务 :{op['op_id']}")
                t.start()
            else:
                op_to_do.append(op)
                time.sleep(0.1)
    #等待当前任务中的操作全部处理完毕，之后可进行下一任务
    while (not len(op_done)==now_op_len):
        pass
	#当完成任务列表长度大于10时，将列表清空，避免数据冗杂
    if len(task_done)>10:
        print("任务列表长度大于20，现清空列表")
        task_done=[]
        task_info=[]
    task_done.append(now_task["productionTask"]["ptId"])

def is_preop_done(pre_op_pid):#判断前序操作是否完成
    global op_done
    pre_op_pid=pre_op_pid.copy()#在python中，列表和字典为可变对象，在函数内的修改会影响外部的变量
    if len(pre_op_pid)==0:
        return True
    for action_op in op_done:
        if action_op["op_pid"] in pre_op_pid:
            pre_op_pid.remove(action_op["op_pid"])
        #print(len(pre_op_pid))
        if len(pre_op_pid)==0:
            return True
        time.sleep(0.1)
    return False
def flask_thread():
    app.run(host="0.0.0.0", port=8000)
########################ROS2节点############################################
import json
class Controller(Node):
    def __init__(self,name):
        super().__init__(name)
        self.task=[]#保存当前执行的操作
    def listener_callback(self,msg):#均为String型，为json字符串
        status=json.loads(msg.data)
        #self.get_logger().info(f"heartbeat from {eq_id_name_dict[int(status['eq_id'])] }:{status}")
        global heartbeat_msg
        heartbeat_msg.append(status)
    def send_goal(self,action_info,action_client,task):
        action_client.wait_for_server()
        self.task.append(task)
        # print(f"动作接口： {action_info}")
        self._send_goal_future=action_client.send_goal_async(
            action_info,
            feedback_callback=self.feedback_callback
        )
        self._send_goal_future.add_done_callback(self.goal_response_callback)
    
    def goal_response_callback(self,future):
        goal_handle=future.result()
        if not goal_handle.accepted:
            self.get_logger().info("设备节点拒接动作请求 :(")
            return
        self.get_logger().info("设备节点同意动作请求 :)")
        self._get_result_future=goal_handle.get_result_async()
        self._get_result_future.add_done_callback(self.get_result_callback)
    
    def get_result_callback(self, future): # 创建一个收到最终结果的回调函数
        global task_finish_msg                                 
        result = future.result().result                                       # 读取动作执行的结果
        # self.get_logger().info(f'Result: {result.finish}')               # 日志输出执行结果
        global op_done
        finish_msg_json=json.loads(result.finish)
        #在self.task中找到eq_id为Result["eq_id"]且op_id为Result["op_id"]的项
        task_list=list(filter(lambda x: x["eq_id"]==finish_msg_json["eq_id"] and op_id_name_dict[x["op_id"]]==finish_msg_json["op_name"],self.task))
        if(len(task_list)==0):
            print(f'!!!!ROS2设备动作返回信息可能格式不正确，返回的信息:{finish_msg_json}!!!')
            return
        task=task_list[0]
        #动作执行成功后
        # print(f"[INFO] ROS2 : task finish:{task}")
        print(f"[INFO] ROS2设备动作执行完成,结束反馈信息:{finish_msg_json}")
        # for item in op_doing:
            # print(f"[INFO] ROS2 : op doing:{item}")
        # print()
        op_doing.remove(task)
        self.task.remove(task)
        task_finish_msg.append(finish_msg_json)
        op_done.append(task)

    def feedback_callback(self, feedback_msg):                                # 创建处理周期反馈消息的回调函数
        feedback = feedback_msg.feedback                                      # 读取反馈的数据
        #self.get_logger().info(f'Received feedback: {feedback.state}' ) 

def rcl_thread():
    global control_node
    rclpy.init(args=None)
    control_node = Controller("controller")
    # node.send_goal(True)
    rclpy.spin(control_node)                                       # 循环等待ROS2退出
    control_node.destroy_node()                                    # 销毁节点对象
    rclpy.shutdown()                                       # 关闭ROS2 Python接口
#################################WebSocket#################################################

class WebSocketClient:
    def __init__(self,  login_data, ws_url):
        # 创建 WebSocket 连接
        self.ws_url = ws_url
        self.ws = None
        self.connect()
        # 启动一个后台线程，用于保持连接
        self.keep_alive_thread = Thread(target=self.keep_alive)
        self.keep_alive_thread.daemon = True
        self.keep_alive_thread.start()
    def connect(self):
        """建立 WebSocket 连接"""
        self.ws = websocket.create_connection(self.ws_url)
        print("WebSocket connection established.")
    def send_msg(self, msg):
        """发送消息到服务器"""
        if self.ws:
            self.ws.send(msg)
            #print(f"sent to backend: {msg}")
        else:
            print("WebSocket connection is not established.")
    def keep_alive(self):
        """保持连接的后台线程"""
        try:
            while True:
                # 每隔一段时间发送一个心跳消息，防止连接超时
                self.send_msg("Heartbeat")
                time.sleep(30)  # 每 30 秒发送一次心跳
        except Exception as e:
            print(f"Keep-alive thread error: {e}")
    def close(self):
        """关闭 WebSocket 连接"""
        if self.ws:
            self.ws.close()
            print("WebSocket connection closed.")
def get_op_pid_list(op):
    op_list=op
    op_pid_list=[]
    if isinstance(op_list, list):
        for op in op_list:
            op_pid_list.append(op["op_pid"])
    elif isinstance(op_list,dict):
        if len(op_list)>0:
            op_pid_list.append(op_list["op_pid"])
    return op_pid_list
def get_task_state():
    #global op_done,op_to_do,now_heartbeat_num,op_doing
    #progress=float(len(op_done))/now_heartbeat_num
    task_state={
        "task_id":now_task["productionTask"]["ptId"],
        "op_done": get_op_pid_list(op_done),
        "op_doing": get_op_pid_list(op_doing),
        "op_undo": get_op_pid_list(op_to_do)
    }
    return task_state
def get_tasks_info():
    global Tasks
    tasks_id_list=[]
    for task in Tasks:
        tasks_id_list.append(task["productionTask"]["ptId"])
    task_doning = [] if len(op_doing) == 0 else [now_task["productionTask"]["ptId"]]
    tasks_info={
        "task_undo":tasks_id_list,
		"task_doning":task_doning,
		"task_done":task_done
    }
    return tasks_info
#需要修改
def websocket_thread():
    login_data = "controller"
    ws_url = f"ws://{server_ip}:8080/heartbeat?client_name=controller"
    global heartbeat_msg,now_heartbeat_num,client
    client = WebSocketClient(login_data, ws_url)
    while True:
        #print(now_heartbeat_num,len(heartbeat_msg))
        if (task_sleep and now_heartbeat_num==0) or (now_heartbeat_num<=len(heartbeat_msg) and len(heartbeat_msg))>0:#如果当前任务停止或者心跳信息收集齐全使，发布消息
            heartbeat_msg=sorted(heartbeat_msg,key=lambda x : x["eq_id"])
            task_state=get_task_state()
            tasks_info=get_tasks_info()
            return_msg_json={
                "tasks_status": tasks_info,
                "now_task_status": task_state,
                "heartbeat": heartbeat_msg,
                "op_finish_msg":task_finish_msg,
                "error":error_msg,
                "task_info":task_info
            }
            return_msg=json.dumps(return_msg_json)
            client.send_msg(return_msg)
            heartbeat_msg=[]#将心跳信息清空
        elif now_heartbeat_num==0:
            return_msg_json={
                "tasks_status": "no task now",
                "error":"no error"
            }
            # return_msg_json={
            #     "tasks_status": {
            #     "task_undo":[],
            #     "task_doning":[],
            #     "task_done":[]
            # },
            #     "now_task_status": {
            #         "task_id":"no task now",
            #         "op_done":[],
            #         "op_undo":[],
            #         "op_doing":[]
            #     },
            #     "heartbeat": [],
            #     "op_finish_msg":[],
            #     "error":"no error",
            #     "task_info":[]
            # }
            client.send_msg(json.dumps(return_msg_json))
        time.sleep(2)
        
######################################################################
def main():
    t1=Thread(target=rcl_thread)
    t2=Thread(target=flask_thread)
    t3=Thread(target=websocket_thread)
    t4=Thread(target=task_add)
    t1.start()
    t2.start()
    t3.start()
    t4.start()
    t1.join()
    t2.join()
    t3.join()
    t4.join()
main()
