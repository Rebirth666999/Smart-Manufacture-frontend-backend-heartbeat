# _*_ coding:utf-8 _*_
import time

import serial  # 导入serial模块，serial模块封装了对串口的访问
import threading
# threading提供了一个比thread模块更高层的API来提供线程的并发性。这些线程并发运行并共享内存。

import serial.tools.list_ports  # 读取串口
import queue
q=queue.Queue()
port_list = list(serial.tools.list_ports.comports())


def findport():
    portname = "NULL"
    port_list = list(serial.tools.list_ports.comports())
    if len(port_list) == 0:
        print("无可用串口！")
    elif len(port_list) > 0:
        for com in port_list:  # 遍历整个列表
            print(com)  # 打印出查找的串口列表
            print(com.name)  # 打印出查找的串口列表
            portname = "/dev/ttyUSB1"
    return portname


class Track(object):  # 创建关于串口的类
    EQUIPMENT_ID="track"
    def __init__(self, port):
        self.status=0
        self.port = port
        # 打开端口
        self.ser = serial.Serial(self.port, baudrate=115200, timeout=1, )
        self.rl=2
        self.isToEnd=False
        self.error=False
        self.pos=2#1为右边 ，0为左 2为其他
        thread1=threading.Thread(target=self.receive_message)
        thread1.start()
        self.move_to_right()
        #0为未初始化，1为空闲，2为忙碌但是未在工作，3为工作中，4为故障
        self.status=1
 

    def receive_message(self):
        if self.ser.isOpen():  # 如果串口已经打开
            print("\nopen sucess！")
            print("接收信息：")
            self.receive = True  # 建立标志
            while self.receive:  # 表示一直循行读取的内容
                receives = self.ser.readline()
                print(receives)
                # readlines()读取多行内容
                if receives == b'':
                
                    # 没接受有效数据时，会一直读取到空列表，等于空列表时，利用continue语句再次重新读取
                    continue

                if (self.rl==0 and b'left1\r\n' in receives) or (self.rl==1 and b'right1\r\n' in receives ):  # 确定接收到这个元素后，执行以下程序
                    self.isToEnd=True
                    self.status=1
                    thread2=threading.Thread(target=self.stop)
                    thread2.run()
                    # self.ser.close()  # 关闭端口
                 # 结束循环

            '''
            decode()的作用是将bytes数据类型转化成str数据类型的函数,
            不论什么编码方式的二进制数据，通过decode函数后，统一编成utf-8编码格式
            因为utf-8格式是python里面的标准
            '''
        else:  # 如果没有读取到com1串口，则执行以下程序
            print(f"\nopen port {self.port} failed")
            self.error=True
            self.status=4
            self.ser.close()  # 关闭端口
    def stop(self):#轨道每打开一次串口，都会停止，因此只需要一直打开串口，就能保持其静止状态
        while self.isToEnd:
            self.ser.close()
            self.ser = serial.Serial(self.port, baudrate=115200, timeout=1, )
            time.sleep(0.5)
    def move_to_left(self):
        if self.pos==0:     #如果现在的位置就在左侧
            return True
        self.pos=2
        self.status=3
        self.isToEnd=False
        self.rl=0
        while not self.isToEnd:
            if self.error:
                return False
            time.sleep(0.2)
            
        self.pos=0
        return True
    def move_to_right(self):
        if self.pos==1:
            return True
        self.pos=2
        self.status=3
        self.isToEnd=False
        self.rl=1
        while not self.isToEnd:
            if self.error:
                return False
            time.sleep(0.2)
        self.pos=1
        return True
    
if __name__ == '__main__':
    # 寻找机械臂的串口

    com = findport();
    if com != "NULL":
        print("\n收发信息程序已开始！")
        action = Track(com)  # 这里可更改连接的串口
        action.move_to_right()
        # 创建一个线程，运行发送信息的函数send_message()

        print("\n收发信息程序已")
    else:
        print("找不到机械臂的串口\n")
