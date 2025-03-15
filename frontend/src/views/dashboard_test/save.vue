<template>
  <div>
    <h1>数据大屏</h1>
    <p
      style="border: 1px solid; width: 600px; height: 300px; margin-left: 100px"
    >
      {{ msg1 }}
    </p>
    <!-- 添加图表容器 -->
    <div
      ref="tasksStatus"
      style="
        width: 600px;
        height: 400px;
        margin-left: 100px;
        border: 1px solid #ccc;
      "
    ></div>
    <div
      ref="nowTaskStatus"
      style="
        width: 600px;
        height: 400px;
        margin-left: 100px;
        border: 1px solid #ccc;
      "
    ></div>
  </div>
</template>

<script>
// 导入自定义的websocket服务
import WebSocketClient from "@/api/websocket/websocketService";
// echarts
import * as echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";

export default {
  mixins: [resize],
  data() {
    return {
      a: {
        tasks_status: {
          task_undo: [],
          task_doning: [],
          task_done: [5, 5, 5, 5, 5],
        },
        now_task_status: {
          task_id: 5,
          op_done: [1, 2, 3, 4, 5],
          op_doing: [],
          op_undo: [],
        },
        heartbeat: [
          { eq_id: 6, is_moving: 1, status: 1, move_mood: 2 },
          { eq_id: 7, is_moving: false, status: 1 },
        ],
        op_finsh_msg: [
          {
            eq_id: 7,
            op_name: "move_to_left",
            is_ok: true,
            begin_time: "2025-03-05T17:27:39.888007",
            finish_time: "2025-03-05T17:27:55.337446",
          },
          {
            eq_id: 8,
            op_name: "sleep",
            begin_time: "2025-03-05T17:27:55.503383",
            finish_time: "2025-03-05T17:27:58.508212",
          },
        ],
        error: "no error",
      },
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
        op_finsh_msg: {},
        error: {},
      },
      msg2: "信息2",
      // 任务执行进度统计扇形图
      tasks_chart: null,
      //当前任务执行进度统计柱状图
      now_task_chart: null,
    };
  },
  mounted() {
    // 创建第一个websocket连接，用于获取设备心跳信息以及当前订单中任务的执行进度
    WebSocketClient.init(
      "ws://localhost:8080/heartbeat?client_name=browser",
      "heartbeat"
    );
    // 收到信息时的回调函数
    WebSocketClient.onMessage((data) => {
      this.msg1 = JSON.parse(data);
      if (this.msg1["tasks_status"] != "no task now") {
        this.msg1_parse = this.message_parse(this.msg1);
        this.updateChart(); // 更新图表数据
      }
    }, "heartbeat");

    this.$nextTick(() => {
      this.initChart();
    });
  },
  methods: {
    message_parse(data) {
      // 任务执行进度统计
      const task_undo_num = data["tasks_status"]["task_undo"].length;
      const task_done_num = data["tasks_status"]["task_done"].length;
      const task_doing_num = data["tasks_status"]["task_doning"].length;
      //当前任务中操作执行进度统计
      const op_undo_num = data["now_task_status"]["op_undo"].length;
      const op_done_num = data["now_task_status"]["op_done"].length;
      const op_doing_num = data["now_task_status"]["op_doing"].length;
      const ret = {
        tasks_status: {
          process: 0,
          task_undo_num: task_undo_num,
          task_done_num: task_done_num,
          task_doing_num: task_doing_num,
          task_undo: data["tasks_status"]["task_undo"],
          task_doning: data["tasks_status"]["task_doning"],
          task_done: data["tasks_status"]["task_done"],
        },
        now_task_status: {
          task_id: 0,
          op_undo_num: op_undo_num,
          op_done_num: op_done_num,
          op_doing_num: op_doing_num,
          op_done: data["now_task_status"]["op_done"],
          op_doing: data["now_task_status"]["op_doing"],
          op_undo: data["now_task_status"]["op_undo"],
        },
        heartbeat: data["heartbeat"],
        op_finsh_msg: data["op_finsh_msg"],
        error: data["error"],
      };
      return ret;
    },
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
      ];
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
      ];
      this.tasks_chart.setOption({
        // 图标提示框。鼠标移至图表中的图形元素时，会显示提示框。
        tooltip: {
          trigger: "item",
          // 提示框中的数据格式
          formatter: function (params) {
            return `${params.name}个数 : ${params.value} (${params.percent}%) <br/>
                      ${params.name}id: ${params.data.info}`;
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
            return `${params.name}个数 : ${params.value}(${params.percent}%) <br>
                      ${params.name}id: ${params.data.info}`;
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
  },
  beforeDestroy() {
    if (this.tasks_chart) {
      this.tasks_chart.dispose();
    }
    WebSocketClient.close("heartbeat");
  },
};
</script>
