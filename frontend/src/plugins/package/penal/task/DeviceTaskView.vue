<template>
  <div class="panel-tab__content">
    <el-descriptions border :column="1">
      <el-descriptions-item label="设备">
        {{ eqList.find(ele => ele.eqCode === eqCode).eqName || '' }}
      </el-descriptions-item>
      <el-descriptions-item label="设备操作">
        {{ eoList.find(ele => ele.eoCode === eoCode).eoName || '' }}
      </el-descriptions-item>
      <el-descriptions-item label="已生成次数">
        {{ tasks.length || 0 }}
      </el-descriptions-item>
      <el-descriptions-item label="已下发次数">
        {{ tasks.filter(ele => ele.dtStat === '2' || ele.dtStat === '3' || ele.dtStat === '4').length || 0 }}
      </el-descriptions-item>
      <el-descriptions-item label="已执行次数">
        {{ tasks.filter(ele => ele.dtStat === '4').length || 0 }}
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
export default {
  name: "DeviceTaskView",
  props: {
    element: Object,
    eqList: Array,
    eoList: Array,
    dtList: Array
  },
  data() {
    return {
      tasks: [],
      eqCode: '',
      eoCode: ''
    };
  },
  watch: {
    // 切换选择的对象时，获取信息
    element: {
      immediate: true,
      handler: function(element) {
        if (element.type === 'bpmn:ServiceTask') {
          this.tasks = this.dtList.filter(ele => ele.dtModel === element.id)
          if (this.tasks.length > 0) {
            this.eqCode = this.tasks[0].eqCode
            this.eoCode = this.tasks[0].eoCode
          }
        }
      }
    }
  },
  beforeDestroy() {
    this.bpmnElement = null;
  }
};
</script>
<style scoped>
.penal-form {
  padding: 0 8px;
}
</style>
