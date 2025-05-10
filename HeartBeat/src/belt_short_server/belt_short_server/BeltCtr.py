# _*_ coding:utf-8 _*_
import time

import serial  # 导入serial模块，serial模块封装了对串口的访问
import threading
# threading提供了一个比thread模块更高层的API来提供线程的并发性。这些线程并发运行并共享内存。

import serial.tools.list_ports  # 读取串口

file_path = '/home/berry/USB_path.txt'

port_list = list(serial.tools.list_ports.comports())


def read_USB_path(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        lines = tuple(line.strip() for line in file)
    return lines

# 连接到接口，两个传送带和轨道同时连接一台设备时，其名称一致，需人工区分
def findport():
    portname = "NULL"
    port_list = list(serial.tools.list_ports.comports())
    print( port_list )
    if len(port_list) == 0:
        print("无可用串口！")
    elif len(port_list) > 0:
        for com in port_list:  # 遍历整个列表
            print(com)
            print(type(com))
            print(com.name)
            portname = read_USB_path(file_path)[2]
    return portname

class Belt(object):  # 创建关于串口的类
    EQUIPMENT_ID="belt_short"
    def __init__(self, port):
        self.status= 0
        self.port = port
        self.mood=2
        self.speed = 5.0
        # 打开端口
        self.ser = serial.Serial(self.port, baudrate=115200, timeout=1, )
        self.is_move=True
        self.isReady=False
        self.stop_sec=3
        self.findPortIsReady()
        thread=threading.Thread(target=self.receive_message,args=(False,))#第一个参数是是否接受日志信息，第二个是红外线检测到物体后停止运动的时间
        thread.start()
        self.move_time(0)
        self.move_mood(mood=2)
        self.status= 1
    def send_message(self,content):
        if self.ser.isOpen():  # 如果串口已经打开
            if self.isReady:
                self.ser.write((str(content) + "\r").encode("utf-8"))
                print("向串口发送："+content)
            else:
                print("端口未初始化完成，若3秒后仍无反应，请重新发送指令")
                time.sleep(3)
                self.isReady=True
                self.ser.write((str(content) + "\r").encode("utf-8"))
            # encode()函数是将字符串转化成相应编码方式的字节形式
            # 如str2.encode('utf-8')，表示将unicode编码的字符串str2转换成utf-8编码的字节数据。
            # 如果不转换，COM1发送到COM2的信息，COM2（调试助手）中文会识别不出来或者会出现乱码现象
        else:  # 如果没有读取到com1串口，则执行以下程序
            print("串口开启失败")
            self.status=4
            self.ser.close()  # 关闭端口
    #红外线计数时，用来确定此时传送带的运动状态，每隔设定的秒数就设置传送带为运动状态
    def countTime(self):
        time.sleep(self.stop_sec)
        self.is_move=True
    #通过接受传送带消息，确定其运动状态
    def receive_message(self,showMsg):
        if self.ser.isOpen():  # 如果串口已经打开
            print("\nopen sucess！")
            print("接收信息：")
            while True:  # 表示一直循行读取的内容
                time.sleep(0.1)
                receives = self.ser.readlines()
                #print("is_moving:"+str(self.is_move))
                if self.mood==1 or self.mood==2:
                    
                    # readlines()读取多行内容
                    if receives == []:
                        # 没接受有效数据时，会一直读取到空列表，等于空列表时，利用continue语句再次重新读取
                        continue
                    else:  # 表示当接收数据不等于空列表[]时，执行以下程序
                        for asr in receives:  # 运用for语句将元素提取出来
                            read = asr.decode("utf-8")  # 将bytes字节数据，转化为字符数据
                            if showMsg:
                                print(read)
                            if read.__contains__("sum"):
                                self.is_move=False
                                thread=threading.Thread(target=self.countTime)
                                thread.start()
                if self.mood==0:
                    
                    # readlines()读取多行内容
                    if receives == []:
                        # 没接受有效数据时，会一直读取到空列表，等于空列表时，利用continue语句再次重新读取
                        continue
                    else:  # 表示当接收数据不等于空列表[]时，执行以下程序
                        for asr in receives:  # 运用for语句将元素提取出来
                            read = asr.decode("utf-8")  # 将bytes字节数据，转化为字符数据
                            if showMsg:
                                print(read)
                            if read.__contains__("sum"):
                                if not self.is_move:
                                 self.move_time(0)
        else:  # 如果没有读取到com1串口，则执行以下程序
            print("\nopen failed")
            self.ser.close()  # 关闭端口
    def findPortIsReady(self):
        count = 0
        if self.isReady == True:
            return True
        if self.ser.isOpen():  # 如果串口已经打开
            self.receive = True  # 建立标志
            while self.receive:  # 表示一直循行读取的内容
                receives = self.ser.readlines()
                # readlines()读取多行内容
                print(receives, count)
                if (b'init\r\n') in receives or count > 3:  # 确定接收到这个元素后，执行以下程序
                    self.receive = False
                    print("接口初始化完成")
                    self.isReady = True
                    return True
                if receives == []:
                    count = count + 1
                    # 没接受有效数据时，会一直读取到空列表，等于空列表时，利用continue语句再次重新读取
                    continue
        else:  # 如果没有读取到com1串口，则执行以下程序
            print("\nopen failed")
            self.status=4
            self.ser.close()  # 关闭端口
    '''
    以下为提供的接口
    '''
    # 获取传送带运动状态，当设为模式2时，由于设备自身局限，状态不准确
    def isMove(self):
        return self.is_move
    def move_mood(self,**info):
        mood=info['mood']
        self.status=3
        if mood==0:
            self.mood=0
            stop_sec =0
            self.send_message(f"cmd#setDelayTime#{stop_sec}")
            self.send_message("cmd#switchMode#0")
        if mood==1:
            self.mood=1
            stop_sec=info['stop_sec']*1000
            self.send_message(f"cmd#setDelayTime#{stop_sec}")
            self.send_message("cmd#switchMode#0")
            self.stop_sec=info['stop_sec']
        if mood==2:
            self.mood=2
            self.send_message("cmd#switchMode#1")
            self.stop_sec=3
        self.status=1
        return True
    #运动runtime秒，然后停止运动
    def move_time(self,runtime):
        self.status=3
        self.send_message("cmd#run")
        self.is_move = True
        time.sleep(runtime)
        self.send_message("cmd#stop")
        self.is_move = False
        self.status=1
        return True

    #停止stoptime秒，然后继续运动
    def stop_time(self,stoptime):
        self.status=3
        self.send_message("cmd#stop")
        self.is_move=False
        time.sleep(stoptime)
        self.send_message("cmd#run")
        self.is_move=True
        self.status=1
        return True
if __name__ == '__main__':
    # 寻找机械臂的串口
    com = findport();
    if com != "NULL":
        print("\n收发信息程序已开始！")
        action = Belt(com)  # 这里可更改连接的串口
        action.stop_time(5)
        print("\n收发信息程序已结束!")
    else:
        print("找不到机械臂的串口\n")
