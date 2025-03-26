<template>
  <div class="panel-tab__content">
    <el-descriptions border :column="1">
      <el-descriptions-item label="设备模型">
        {{ emList.find(ele => ele.emCode === emCode).emName }}
      </el-descriptions-item>
      <el-descriptions-item label="模型操作">
        {{ moList.find(ele => ele.moCode === moCode).moName }}
      </el-descriptions-item>
      <el-descriptions-item label="描述">
        {{ psDesc || '' }}
      </el-descriptions-item>
      <el-descriptions-item label="跨轮次前序步骤">
        <div v-for="item in elements.filter(ele => prev.indexOf(ele.businessObject.id) !== -1)">
          {{ item.businessObject.name }}
        </div>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
export default {
  name: "EquipmentOperationStepProperties",
  props: {
    element: Object,
    emList: Array,
    moList: Array
  },
  data() {
    return {
      emCode: '',
      moCode: '',
      psDesc: '',
      prev: [],
      elements: []
    };
  },
  watch: {
    // 切换选择的对象时，获取信息
    element: {
      immediate: true,
      handler: function(element) {
        if (element.type === 'bpmn:ServiceTask') {
          this.emCode = element.businessObject.$attrs.emCode || ''
          this.moCode = element.businessObject.$attrs.moCode || ''
          this.psDesc = element.businessObject.$attrs.psDesc || ''
          this.prev = JSON.parse(element.businessObject.$attrs.prev || '[]')
          this.elements = element.parent.children
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
.element-property__label {
  width: 125px !important;
}
</style>
