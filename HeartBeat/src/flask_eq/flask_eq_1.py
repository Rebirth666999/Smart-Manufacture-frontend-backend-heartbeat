from flask import Flask,request,jsonify
from flask_cors import CORS
import time
from threading import Thread
import requests
from datetime import datetime
app=Flask(__name__)
CORS(app)
state=0
eq_id=4   #设备eq_id
@app.route("/flasktest",methods=["POST"])
def test():
    global state
    if not request.json:
        print("error")
        return jsonify({"error": "received data is not json format"})
    task=request.get_json()
    op_param=task["op_param"]#获取操作参数
    print(f"param received,params:{op_param}")
    begin_time=datetime.now().isoformat()
    for _ in range(1,20):
        state=state+1
        time.sleep(1) #模拟操作时间
    print("task finish")
    finish_time=datetime.now().isoformat()
    finish_status="suceess" #定义操作完成反馈数据
    result={
        "eq_id": eq_id,
        "eq_name":"arm_3",
        "op_id":3,
        "op_name": "flask_test",
        "begin_time":begin_time,
        "finish_time":finish_time,
        "finish_status": {
            "msg":1
        } 
    }
    return jsonify(result),200
@app.route("/heartbeat",methods=["POST"])
def hearbeat():
    print("receive request for heartbeat")
    response={
        "eq_id":4,
        "eq_name":"arm_3",
        "status":{
            "msg":1
        }
    }
    return jsonify(response),200

def flask_thread():
    app.run(host="0.0.0.0", port=9000)
def main():
    t1=Thread(target=flask_thread)
    t1.start()
    t1.join()
main()