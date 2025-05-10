import rclpy
from rclpy.node import Node
from rclpy.action import ActionServer, CancelResponse, GoalResponse
from rclpy.action.server import ServerGoalHandle
from rclpy.executors import ExternalShutdownException
from rclpy.callback_groups import ReentrantCallbackGroup
from std_msgs.msg import String
from interfaces.action import (
    StopTime,
    MoveMood,
    MoveTime,
    IsMoving,
    GetStatus,
    BeltMove,
    BeltStop
)
from .BeltCtr import Belt
import json
import threading
import time
import sys
from datetime import datetime

eq_id = "Equipment-00026"  # 设备的 ID 号
eq_name="belt_long" #设备的名称
op_id={
    "StopTime": "EquipmentAtomOperation-00019",
    "MoveMood": "EquipmentAtomOperation-00020",
    "MoveTime": "EquipmentAtomOperation-00018",
    "IsMoving": "EquipmentAtomOperation-00016",
    "GetStatus": "EquipmentAtomOperation-00021",
    "BeltMove": "EquipmentAtomOperation-00015",
    "BeltStop": "EquipmentAtomOperation-00017"
}

class BeltActionServer(Node):
    def __init__(self):
        super().__init__(f'{Belt.EQUIPMENT_ID}Node')
        port_list = sys.argv[1:2]
        if len(port_list) == 0:
            port = "/dev/ttyUSB0"
        else:
            port = str(port_list[0])
        print(f"{Belt.EQUIPMENT_ID} is connected to {port}")
        self.belt = Belt(port)

        # 创建动作服务器
        self._action_servers = []
        self._action_servers.append(ActionServer(
            self, GetStatus, f'{Belt.EQUIPMENT_ID}_GetStatus', execute_callback=self.handle_get_status,
            callback_group=ReentrantCallbackGroup(), goal_callback=self.goal_callback,
            cancel_callback=self.cancel_callback
        ))
        self._action_servers.append(ActionServer(
            self, StopTime, f'{Belt.EQUIPMENT_ID}_StopTime', execute_callback=self.handle_stop_time,
            callback_group=ReentrantCallbackGroup(), goal_callback=self.goal_callback,
            cancel_callback=self.cancel_callback
        ))
        self._action_servers.append(ActionServer(
            self, MoveTime, f'{Belt.EQUIPMENT_ID}_MoveTime', execute_callback=self.handle_move_time,
            callback_group=ReentrantCallbackGroup(), goal_callback=self.goal_callback,
            cancel_callback=self.cancel_callback
        ))
        self._action_servers.append(ActionServer(
            self, MoveMood, f'{Belt.EQUIPMENT_ID}_MoveMood', execute_callback=self.handle_move_mood,
            callback_group=ReentrantCallbackGroup(), goal_callback=self.goal_callback,
            cancel_callback=self.cancel_callback
        ))
        self._action_servers.append(ActionServer(
            self, IsMoving, f'{Belt.EQUIPMENT_ID}_IsMoving', execute_callback=self.handle_is_moving,
            callback_group=ReentrantCallbackGroup(), goal_callback=self.goal_callback,
            cancel_callback=self.cancel_callback
        ))
        self._action_servers.append(ActionServer(
            self, BeltMove, f'{Belt.EQUIPMENT_ID}_BeltMove', execute_callback=self.handle_belt_move,
            callback_group=ReentrantCallbackGroup(), goal_callback=self.goal_callback,
            cancel_callback=self.cancel_callback
        ))
        self._action_servers.append(ActionServer(
            self, BeltStop, f'{Belt.EQUIPMENT_ID}_BeltStop', execute_callback=self.handle_belt_stop,
            callback_group=ReentrantCallbackGroup(), goal_callback=self.goal_callback,
            cancel_callback=self.cancel_callback
        ))

        self.pub = self.create_publisher(String, f'{Belt.EQUIPMENT_ID}_heartbeat', 10)
        thread = threading.Thread(target=self.publish_state)
        thread.start()

    def goal_callback(self, goal_request):
        """Accepts or rejects a client request to begin an action."""
        self.get_logger().info('Received goal request')
        return GoalResponse.ACCEPT

    def cancel_callback(self, goal_handle):
        """Accepts or rejects a client request to cancel an action."""
        self.get_logger().info('Received cancel request')
        return CancelResponse.ACCEPT

    def handle_get_status(self, goal_handle):
        self.get_logger().info('Executing goal: GetStatus')
        begin_time = datetime.now().isoformat()
        status = self.belt.status
        finish_time = datetime.now().isoformat()
        result = GetStatus.Result()
        result.finish = json.dumps({
            "eq_id": eq_id,
            "eq_name":eq_name,
            "op_id": op_id["GetStatus"],
            "op_name": "GetStatus",
            "begin_time": begin_time,
            "finish_time": finish_time,
            "status":{
                "status": status,
            }
        })
        goal_handle.succeed()
        return result

    def handle_stop_time(self, goal_handle):
        self.get_logger().info('Executing goal: StopTime')
        begin_time = datetime.now().isoformat()
        request = goal_handle.request
        is_ok = self.belt.stop_time(request.stoptime)
        feedback_msg = StopTime.Feedback()
        feedback_msg.feedback = 1
        goal_handle.publish_feedback(feedback_msg)
        finish_time = datetime.now().isoformat()
        result = StopTime.Result()
        result.finish = json.dumps({
           "eq_id": eq_id,
            "eq_name":eq_name,
            "op_id": op_id["StopTime"],
            "op_name": "StopTime",
            "begin_time": begin_time,
            "finish_time": finish_time,
            "status":{
                "is_ok": is_ok,
            }
        })
        goal_handle.succeed()
        return result

    def handle_move_time(self, goal_handle):
        self.get_logger().info('Executing goal: MoveTime')
        begin_time = datetime.now().isoformat()
        request = goal_handle.request
        is_ok = self.belt.move_time(request.runtime)
        feedback_msg = MoveTime.Feedback()
        feedback_msg.feedback = 1
        goal_handle.publish_feedback(feedback_msg)
        finish_time = datetime.now().isoformat()
        result = MoveTime.Result()
        result.finish = json.dumps({
           "eq_id": eq_id,
            "eq_name":eq_name,
            "op_id": op_id["MoveTime"],
            "op_name": "MoveTime",
            "begin_time": begin_time,
            "finish_time": finish_time,
            "status":{
                "is_ok": is_ok,
            }
        })
        goal_handle.succeed()
        return result

    def handle_move_mood(self, goal_handle):
        self.get_logger().info('Executing goal: MoveMood')
        begin_time = datetime.now().isoformat()
        request = goal_handle.request
        is_ok = self.belt.move_mood(mood=request.mood, stop_sec=request.stop_sec)
        feedback_msg = MoveMood.Feedback()
        feedback_msg.feedback = 1
        goal_handle.publish_feedback(feedback_msg)
        finish_time = datetime.now().isoformat()
        result = MoveMood.Result()
        result.finish = json.dumps({
           "eq_id": eq_id,
            "eq_name":eq_name,
            "op_id": op_id["MoveMood"],
            "op_name": "MoveMood",
            "begin_time": begin_time,
            "finish_time": finish_time,
            "status":{
                "is_ok": is_ok,
            }
        })
        goal_handle.succeed()
        return result

    def handle_is_moving(self, goal_handle):
        self.get_logger().info('Executing goal: IsMoving')
        begin_time = datetime.now().isoformat()
        is_moving = 1 if self.belt.isMove() else 0
        finish_time = datetime.now().isoformat()
        result = IsMoving.Result()
        result.finish = json.dumps({
            "eq_id": eq_id,
            "eq_name":eq_name,
            "op_id": op_id["IsMoving"],
            "op_name": "IsMoving",
            "begin_time": begin_time,
            "finish_time": finish_time,
            "status":{
                "is_moving": is_moving,
            }
        })
        goal_handle.succeed()
        return result

    def handle_belt_move(self, goal_handle):
        self.get_logger().info('Executing goal: BeltMove')
        begin_time = datetime.now().isoformat()
        is_ok = self.belt.stop_time(0)
        feedback_msg = BeltMove.Feedback()
        feedback_msg.feedback = 1
        goal_handle.publish_feedback(feedback_msg)
        finish_time = datetime.now().isoformat()
        result = BeltMove.Result()
        result.finish = json.dumps({
            "eq_id": eq_id,
            "eq_name":eq_name,
            "op_id": op_id["BeltMove"],
            "op_name": "BeltMove",
            "begin_time": begin_time,
            "finish_time": finish_time,
            "status":{
                "is_ok": is_ok,
            }
        })
        goal_handle.succeed()
        return result

    def handle_belt_stop(self, goal_handle):
        self.get_logger().info('Executing goal: BeltStop')
        begin_time = datetime.now().isoformat()
        is_ok = self.belt.move_time(0)
        feedback_msg = BeltStop.Feedback()
        feedback_msg.feedback = 1
        goal_handle.publish_feedback(feedback_msg)
        finish_time = datetime.now().isoformat()
        result = BeltStop.Result()
        result.finish = json.dumps({
            "eq_id": eq_id,
            "eq_name": "belt_short",
            "op_id": op_id["BeltStop"],
            "op_name": "BeltStop",
            "begin_time": begin_time,
            "finish_time": finish_time,
            "status":{
                "is_ok": is_ok,
            }
        })
        goal_handle.succeed()
        return result

    def publish_state(self):
        while True:
            if self.belt.isMove():
                is_moving = 1
            else:
                is_moving = 0
            state = {
                "eq_id": eq_id,
                "eq_name":eq_name,
                "status":{
                    'is_moving': is_moving,
                    "status": self.belt.status,
                    "move_mood": self.belt.mood
                }
            }
            msg = String()
            msg.data = json.dumps(state)
            self.pub.publish(msg)
            time.sleep(2)

def main(args=None):
    rclpy.init(args=args)
    node = BeltActionServer()
    try:
        rclpy.spin(node)
    except KeyboardInterrupt:
        node.get_logger().info('KeyboardInterrupt caught')
    except ExternalShutdownException:
        node.get_logger().info('External shutdown request received')
    finally:
        node.destroy_node()
        rclpy.try_shutdown()

if __name__ == '__main__':
    main()