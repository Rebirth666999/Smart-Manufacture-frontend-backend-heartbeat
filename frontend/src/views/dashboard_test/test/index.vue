<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>基本信息</span>
            </div>
          </template>
          <div>所属车间: {{ workspace }}</div>
          <div>当前订单：{{ order }}</div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>控制节点状态</span>
            </div>
          </template>
          <div v-if="error !== 'no error'">
            <el-alert
              title="错误警告"
              type="error"
              description="控制节点发生错误，请查看控制节点的错误日志"
              show-icon
            ></el-alert>
          </div>
          <div v-else>
            <el-alert
              title="无错误"
              type="success"
              description="控制节点运行正常"
              show-icon
            ></el-alert>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>任务状态饼图</span>
            </div>
          </template>
          <div
            ref="tasksStatus"
            style="width: 100%; height: 400px; border: 1px solid #ccc"
          ></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>当前任务状态饼图</span>
            </div>
          </template>
          <div
            ref="nowTaskStatus"
            style="width: 100%; height: 400px; border: 1px solid #ccc"
          ></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>任务执行进度</span>
            </div>
          </template>
          <el-progress
            :percentage="(taskStatus.task_done / taskStatus.total_tasks) * 100"
            :format="progressFormat"
          ></el-progress>
          <div>
            已完成任务: {{ taskStatus.task_done }} /
            {{ taskStatus.total_tasks }}
          </div>
          <el-divider></el-divider>
          <div>
            <p>正在执行的任务：</p>
            <el-descriptions :column="1" border>
              <el-descriptions-item
                v-for="task in taskStatus.running_tasks"
                :key="task.id"
                :label="task.id"
              >
                任务id：{{ task.ptId }} 优先级：{{ task.priority }}，
                {{ task.is_preemptive ? "抢占式" : "非抢占式" }}
              </el-descriptions-item>
            </el-descriptions>
            <p>已完成的任务：</p>
            <el-descriptions :column="1" border>
              <el-descriptions-item
                v-for="task in taskStatus.finished_tasks"
                :key="task.id"
                :label="task.id"
              >
                任务id：{{ task.ptId }} 优先级：{{ task.priority }}，
                {{ task.is_preemptive ? "抢占式" : "非抢占式" }}
              </el-descriptions-item>
            </el-descriptions>
            <p>未完成的任务：</p>
            <el-descriptions :column="1" border>
              <el-descriptions-item
                v-for="task in taskStatus.pending_tasks"
                :key="task.id"
                :label="task.id"
              >
                任务id：{{ task.ptId }} 优先级：{{ task.priority }}，
                {{ task.is_preemptive ? "抢占式" : "非抢占式" }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>当前任务执行进度</span>
            </div>
          </template>
          <el-progress
            :percentage="
              (operationStatus.op_done / operationStatus.total_operations) * 100
            "
            :format="
              progressFormat(
                (operationStatus.op_done / operationStatus.total_operations) *
                  100
              )
            "
          ></el-progress>
          <div>
            已完成操作: {{ operationStatus.op_done }} /
            {{ operationStatus.total_operations }}
          </div>
          <el-divider></el-divider>
          <div>
            <p>正在执行的操作：</p>
            <el-descriptions :column="1" border>
              <el-descriptions-item
                v-for="op in operationStatus.doing_operations"
                :key="op.id"
                :label="op.id"
              >
                当前工序id：{{ op.prdId }} 前序工序id：{{ op.prePrdId }},
                设备操作id：{{ op.opId }}
              </el-descriptions-item>
            </el-descriptions>
            <p>已完成的操作：</p>
            <el-descriptions :column="1" border>
              <el-descriptions-item
                v-for="op in operationStatus.finished_operations"
                :key="op.id"
                :label="op.id"
              >
                当前工序id：{{ op.prdId }} 前序工序id：{{ op.prePrdId }},
                设备操作id：{{ op.opId }}, 任务完成信息:{{ op.opFinishMsg }}
              </el-descriptions-item>
            </el-descriptions>
            <p>未完成的操作：</p>
            <el-descriptions :column="1" border>
              <el-descriptions-item
                v-for="op in operationStatus.pending_operations"
                :key="op.id"
                :label="op.id"
              >
                当前工序id：{{ op.prdId }} 前序工序id：{{ op.prePrdId }},
                设备操作id：{{ op.opId }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="24">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>当前任务设备心跳信息</span>
            </div>
          </template>
          <el-descriptions :column="1" border>
            <el-descriptions-item
              v-for="msg in heartbeatMsg"
              :key="msg.id"
              :label="msg.id"
            >
              设备id:{{ msg.eqId }},设备名称:{{ msg.eqName }}, 心跳信息:{{
                msg.status
              }}
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 导入自定义的websocket服务
import WebSocketClient from "@/api/websocket/websocketService";
//倒入心跳信息处理函数
import { message_parse } from "@/api/websocket/data_parse";
// echarts
import * as echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize.js";

export default {
  mixins: [resize],
  data() {
    return {
      msg1: "heartbeat",
      // 解析后的msg1
      msg1_parse: {
        tasks_status: {
          process: 0,
          task_undo_num: 0,
          task_done_num: 0,
          task_doing_num: 0,
          task_undo: [],
          task_doning: [],
          task_done: [],
        },
        now_task_status: {
          task_id: 0,
          op_undo_num: 0,
          op_done_num: 0,
          op_doing_num: 0,
          op_done: [],
          op_doing: [],
          op_undo: [],
        },
        heartbeat: {},
        op_finish_msg: {},
        error: "no error",
      },
      // 任务执行进度统计扇形图
      tasks_chart: null,
      // 当前任务执行进度统计柱状图
      now_task_chart: null,
      // 初始化 taskStatus 和 operationStatus
      taskStatus: {
        task_done: 0,
        total_tasks: 0,
        running_tasks: [],
        finished_tasks: [],
        pending_tasks: [],
      },
      operationStatus: {
        op_done: 0,
        total_operations: 0,
        doing_operations: [],
        finished_operations: [],
        pending_operations: [],
      },
      heartbeatMsg: [
        {
          id: 1,
          eq_id: 0,
          eq_name: "eq",
          status: [],
        },
      ],
      error: "no error",
      workspace: "车间1",
      order: "订单1",
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
    // 创建第一个websocket连接，用于获取设备心跳信息以及当前订单中任务的执行进度
    WebSocketClient.init(
      "ws://localhost:8080/heartbeat?client_name=browser",
      "heartbeat"
    );
    // 收到信息时的回调函数
    WebSocketClient.onMessage((data) => {
      this.msg1 = JSON.parse(data);
      if (this.msg1["error"] != "no error") {
        this.error = this.msg1["error"];
        //弹出错误信息
        alert("控制节点发生错误，请及时查看");
      } else if (this.msg1["tasks_status"] != "no task now") {
        this.msg1_parse = message_parse(this.msg1);
        this.updateTaskStatus();
        this.updateOperationStatus();
        this.updateHeartbeatMsg();
        this.updateChart(); // 更新图表数据
        this.updateErrorMsg();
      }
    }, "heartbeat");
  },
  methods: {
    initChart() {
      // 使用 ref 获取图表容器
      this.tasks_chart = echarts.init(this.$refs.tasksStatus, "macarons");
      this.now_task_chart = echarts.init(this.$refs.nowTaskStatus, "macarons");
      this.updateChart(); // 初始化图表数据
    },
    updateChart() {
      const task_data = [
        {
          value: this.msg1_parse["tasks_status"]["task_done_num"],
          name: "已完成的任务",
          info: this.msg1_parse["tasks_status"]["task_done"],
        },
        {
          value: this.msg1_parse["tasks_status"]["task_doing_num"],
          name: "正在执行的任务",
          info: this.msg1_parse["tasks_status"]["task_doning"],
        },
        {
          value: this.msg1_parse["tasks_status"]["task_undo_num"],
          name: "未完成的任务",
          info: this.msg1_parse["tasks_status"]["task_undo"],
        },
      ].filter((item) => item.value > 0); // 过滤掉值为 0 的项

      const op_data = [
        {
          value: this.msg1_parse["now_task_status"]["op_done_num"],
          name: "已完成的操作",
          info: this.msg1_parse["now_task_status"]["op_done"],
        },
        {
          value: this.msg1_parse["now_task_status"]["op_doing_num"],
          name: "正在执行的操作",
          info: this.msg1_parse["now_task_status"]["op_doing"],
        },
        {
          value: this.msg1_parse["now_task_status"]["op_undo_num"],
          name: "未完成的操作",
          info: this.msg1_parse["now_task_status"]["op_undo"],
        },
      ].filter((item) => item.value > 0); // 过滤掉值为 0 的项

      this.tasks_chart.setOption({
        // 图标提示框。鼠标移至图表中的图形元素时，会显示提示框。
        tooltip: {
          trigger: "item",
          // 提示框中的数据格式
          formatter: function (params) {
            return `${params.name} 个数: ${params.value} (${params.percent}%) <br/>
                    ${params.name} ID: ${params.data.info}`;
          },
        },
        // 图例配置
        legend: {
          left: "center",
          bottom: "10",
          data: task_data.map((item) => item.name),
        },
        // 系列配置
        series: [
          {
            name: "任务执行进度",
            type: "pie",
            radius: "50%",
            center: ["50%", "50%"],
            data: task_data,
            animationEasing: "cubicInOut",
            animationDuration: 2600,
          },
        ],
      });

      this.now_task_chart.setOption({
        // 图标提示框。鼠标移至图表中的图形元素时，会显示提示框。
        tooltip: {
          trigger: "item",
          // 提示框中的数据格式
          formatter: function (params) {
            return `${params.name} 个数: ${params.value} (${params.percent}%) <br/>
                    ${params.name} ID: ${params.data.info}`;
          },
        },
        // 图例配置
        legend: {
          left: "center",
          bottom: "10",
          data: op_data.map((item) => item.name),
        },
        // 系列配置
        series: [
          {
            name: "当前任务状态",
            type: "pie",
            radius: "50%",
            center: ["50%", "50%"],
            data: op_data,
            animationEasing: "cubicInOut",
            animationDuration: 2600,
          },
        ],
      });
    },
    updateTaskStatus() {
      this.taskStatus = {
        task_done: this.msg1_parse["tasks_status"]["task_done_num"],
        total_tasks:
          this.msg1_parse["tasks_status"]["task_done_num"] +
          this.msg1_parse["tasks_status"]["task_doing_num"] +
          this.msg1_parse["tasks_status"]["task_undo_num"],
        running_tasks: this.msg1_parse["tasks_status"]["task_doning_info"].map(
          (task, index) => ({
            id: index + 1,
            ptId: task.ptId,
            priority: task.ptPriority || 0,
            is_preemptive: task.is_preemptive || false,
          })
        ),
        finished_tasks: this.msg1_parse["tasks_status"]["task_done_info"].map(
          (task, index) => ({
            id: index + 1,
            ptId: task.ptId,
            priority: task.ptPriority || 0,
            is_preemptive: task.is_preemptive || false,
          })
        ),
        pending_tasks: this.msg1_parse["tasks_status"]["task_undo_info"].map(
          (task, index) => ({
            id: index + 1,
            ptId: task.ptId,
            priority: task.ptPpriority || 0,
            is_preemptive: task.is_preemptive || false,
          })
        ),
      };
    },
    updateOperationStatus() {
      this.operationStatus = {
        op_done: this.msg1_parse["now_task_status"]["op_done_num"],
        total_operations:
          this.msg1_parse["now_task_status"]["op_done_num"] +
          this.msg1_parse["now_task_status"]["op_doing_num"] +
          this.msg1_parse["now_task_status"]["op_undo_num"],

        doing_operations: this.msg1_parse["now_task_status"][
          "op_doing_info"
        ].map((op, index) => ({
          id: index + 1,
          prdId: op.prdId,
          prePrdId: op.prePrdId || 0,
          opId: op.opId,
        })),
        finished_operations: this.msg1_parse["now_task_status"][
          "op_done_info"
        ].map((op, index) => ({
          id: index + 1,
          prdId: op.prdId,
          prePrdId: op.prePrdId || 0,
          opId: op.opId,
          opFinishMsg: op.opFinishMsg,
          // info: op.info || "",
        })),
        pending_operations: this.msg1_parse["now_task_status"][
          "op_undo_info"
        ].map((op, index) => ({
          id: index + 1,
          prdId: op.prdId,
          prePrdId: op.prePrdId || 0,
          opId: op.opId,
        })),
      };
    },
    updateHeartbeatMsg() {
      this.heartbeatMsg = this.msg1_parse["heartbeat"].map((msg, index) => ({
        id: index + 1,
        eqId: msg.eq_id,
        eqName: msg.eq_name,
        status: msg.status,
      }));
    },
    updateErrorMsg() {
      // this.error = this.error === "no error" ? "some error" : "no error";
      this.error = this.msg1["error"];
    },
    progressFormat(percentage) {
      return `${percentage.toFixed(2)}%`;
    },
  },

  beforeDestroy() {
    if (this.tasks_chart) {
      this.tasks_chart.dispose();
    }
    if (this.now_task_chart) {
      this.now_task_chart.dispose();
    }
    WebSocketClient.close("heartbeat");
  },
};
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 3px 0;
}

.el-descriptions-item__content {
  word-break: break-all;
}
</style>
