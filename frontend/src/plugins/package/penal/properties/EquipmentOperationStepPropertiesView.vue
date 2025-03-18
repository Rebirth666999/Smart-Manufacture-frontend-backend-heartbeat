<template>
  <div class="panel-tab__content">
    <el-descriptions border :column="1">
      <el-descriptions-item label="原子操作">
        {{ list.find(ele => ele.eaoId === eaoId).eaoName }}
      </el-descriptions-item>
      <el-descriptions-item label="描述">
        {{ eosDesc || '' }}
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
export default {
  name: "EquipmentOperationStepProperties",
  props: {
    element: Object,
    list: Array
  },
  data() {
    return {
      eaoId: '',
      eosDesc: ''
    };
  },
  watch: {
    // 切换选择的对象时，获取信息
    element: {
      immediate: true,
      handler: function(element) {
        this.eaoId = element.businessObject.$attrs.eaoId || ''
        this.eosDesc = element.businessObject.$attrs.eosDesc || ''
      }
    }
  },
  beforeDestroy() {
    this.bpmnElement = null;
  }
};
</script>
