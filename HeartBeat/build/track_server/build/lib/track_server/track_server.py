import rclpy
from rclpy.node import Node
from rclpy.action import ActionServer, CancelResponse, GoalResponse
from rclpy.action.server import ServerGoalHandle
from rclpy.callback_groups import ReentrantCallbackGroup
from rclpy.executors import ExternalShutdownException
from std_msgs.msg import String
from interfaces.action import MoveToLeft, MoveToRight, IsMoving
from .track import Track
import json
import time
import threading
import sys
from datetime import datetime

# eq_id = 7  # 设备的 ID 号
# eq_name="track" #设备的名称
# #设备涉及的操作
# op_id={
#     "MoveToLeft": 11,
#     "MoveToRight": 12,
#     "IsMoving": 4
# }
eq_id = "Equipment-00027"  # 设备的 ID 号
eq_name="track" #设备的名称
#设备涉及的操作
op_id={
    "MoveToLeft": "EquipmentAtomOperation-00012",
    "MoveToRight": "EquipmentAtomOperation-00013",
    "IsMoving": "EquipmentAtomOperation-00014"
}
class TrackActionServer(Node):
    def __init__(self):
        super().__init__(f"{Track.EQUIPMENT_ID}Node")
        port_list = sys.argv[1:2]
        if len(port_list) == 0:
            port = "/dev/ttyUSB0"
        else:
            port = str(port_list[0])
        print(f"Track is connected to {port}")
        self.track = Track(port)

        # 创建动作服务器
        self._action_servers = []
        self._action_servers.append(ActionServer(
            self, MoveToLeft, f'{Track.EQUIPMENT_ID}_MoveToLeft', execute_callback=self.handle_move_to_left,
            callback_group=ReentrantCallbackGroup(), goal_callback=self.goal_callback,
            cancel_callback=self.cancel_callback
        ))
        self._action_servers.append(ActionServer(
            self, MoveToRight, f'{Track.EQUIPMENT_ID}_MoveToRight', execute_callback=self.handle_move_to_right,
            callback_group=ReentrantCallbackGroup(), goal_callback=self.goal_callback,
            cancel_callback=self.cancel_callback
        ))
        self._action_servers.append(ActionServer(
            self, IsMoving, f'{Track.EQUIPMENT_ID}_IsMoving', execute_callback=self.handle_is_moving,
            callback_group=ReentrantCallbackGroup(), goal_callback=self.goal_callback,
            cancel_callback=self.cancel_callback
        ))

        self.pub = self.create_publisher(String, f"{Track.EQUIPMENT_ID}_heartbeat", 10)
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

    def handle_move_to_left(self, goal_handle):
        self.get_logger().info('Executing goal: MoveToLeft')
        begin_time = datetime.now().isoformat()  # 使用 ISO 8601 格式记录时间
        is_ok = self.track.move_to_left()
        feedback_msg = MoveToLeft.Feedback()
        feedback_msg.feedback = 1
        finish_time = datetime.now().isoformat()
        goal_handle.publish_feedback(feedback_msg)
        result = MoveToLeft.Result()
        result.finish = json.dumps({
            "eq_id": eq_id,
            "eq_name":eq_name,
            "op_id": op_id["MoveToLeft"],
            "op_name": "MoveToLeft",
            "begin_time": begin_time,
            "finish_time": finish_time,
            "status":{
                "is_ok": is_ok,
            }
        })
        goal_handle.succeed()
        return result

    def handle_move_to_right(self, goal_handle):
        self.get_logger().info('Executing goal: MoveToRight')
        # 获取开始时间
        begin_time = datetime.now().isoformat()  # 使用 ISO 8601 格式记录时间
        # 执行移动操作
        is_ok = self.track.move_to_right()
        # 获取完成时间
        finish_time = datetime.now().isoformat()  # 使用 ISO 8601 格式记录时间
        # 发布反馈
        feedback_msg = MoveToRight.Feedback()
        feedback_msg.feedback = 1
        goal_handle.publish_feedback(feedback_msg)
        # 构造结果
        result = MoveToRight.Result()
        result.finish = json.dumps({
            "eq_id": eq_id,
            "eq_name":eq_name,
            "op_id": op_id["MoveToRight"],
            "op_name": "MoveToRight",
            "begin_time": begin_time,
            "finish_time": finish_time,
            "status":{
                "is_ok": is_ok,
            }
        })
        # 标记目标完成
        goal_handle.succeed()
        return result

    def handle_is_moving(self, goal_handle):
        self.get_logger().info('Executing goal: IsMoving')
        begin_time = datetime.now().isoformat()
        is_moving = not self.track.isToEnd
        finish_time = datetime.now().isoformat()
        feedback_msg = IsMoving.Feedback()
        feedback_msg.feedback = 1
        goal_handle.publish_feedback(feedback_msg)
        result = IsMoving.Result()
        result.finish = json.dumps({
            "eq_id": eq_id,
            "eq_name":eq_name,
            "op_id": op_id["IsMoving"],
            "op_name": "IsMoving",
            "begin_time": begin_time,
            "finish_time": finish_time,
            "status":{
                "pos": self.track.pos,
                "is_moving": is_moving,
            }
        })
        goal_handle.succeed()
        return result

    def publish_state(self):
        while True:
            is_moving = not self.track.isToEnd
            state = {
                "eq_id": eq_id,
                "eq_name":eq_name,
                "status":{
                    "is_moving": is_moving,
                    "pos": self.track.pos,
                    "status": self.track.status
                }
            }
            msg = String()
            msg.data = json.dumps(state)
            self.pub.publish(msg)
            time.sleep(2)

def main(args=None):
    rclpy.init(args=args)
    node = TrackActionServer()
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