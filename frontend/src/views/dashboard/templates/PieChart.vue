<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "../mixins/resize";

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "300px",
    },
  },
  data() {
    return {
      chart: null,
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");
      this.chart.setOption({
        // 图标提示框。鼠标移至图表中的图形元素时，会显示提示框。
        /*
        {a}、{b}、{c} 和 {d} 是 ECharts 提示框（tooltip）中预定义的占位符，它们的数据来源如下：

        {a}：系列名称（series name），来源于 series 配置项中的 name 属性。
        {b}：数据项名称（data item name），来源于 series.data 中每个数据项的 name 属性。
        {c}：数值（value），来源于 series.data 中每个数据项的 value 属性。
        {d}：百分比（percentage），由 ECharts 自动计算，表示该数据项在总和中所占的比例。
        具体到你的代码中：

        系列名称 {a} 来自 series.name，即 "WEEKLY WRITE ARTICLES"。
        数据项名称 {b} 和数值 {c} 分别来自 series.data 中的 name 和 value，例如 { value: 320, name: "Industries" }。
        百分比 {d} 是 ECharts 根据所有数据项的 value 总和自动计算得出的。
        */
        tooltip: {
          trigger: "item",
          // 提示框中的数据格式
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        /*
       这段代码配置了 ECharts 图表的图例（legend）部分，具体功能如下：
        left: "center"：设置图例水平居中显示。
        bottom: "10"：设置图例距离图表底部 10px。
        data: ["Industries", "Technology", "Forex", "Gold", "Forecasts"]：定义图例的数据项，每个数据项对应饼图中的一个分类名称。
        总结：该配置用于在图表底部居中位置显示图例，并指定图例包含的分类名称。
       */
        legend: {
          left: "center",
          bottom: "10",
          data: ["Industries", "Technology", "Forex", "Gold", "Forecasts"],
        },
        series: [
          {
            name: "WEEKLY WRITE ARTICLES",
            type: "pie",
            roseType: "radius",
            radius: [15, 95],
            center: ["50%", "38%"],
            data: [
              { value: 320, name: "Industries" },
              { value: 240, name: "Technology" },
              { value: 149, name: "Forex" },
              { value: 100, name: "Gold" },
              { value: 59, name: "Forecasts" },
            ],
            animationEasing: "cubicInOut",
            animationDuration: 2600,
          },
        ],
      });
    },
  },
};
</script>
