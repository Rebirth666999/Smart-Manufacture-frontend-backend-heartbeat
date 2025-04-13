<template>
  <div class="panel-tab__content">
    <el-table :data="demandList" size="mini" max-height="240" border fit>
      <el-table-column label="序号" width="50px" type="index" />
      <el-table-column label="名称" prop="maCode" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ maList.find(ele => ele.maCode === scope.row.maCode).maName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="数量" prop="pmDemand" show-overflow-tooltip />
    </el-table>
  </div>
</template>
<script>

export default {
  name: "EquipmentOperationStepBaseInfo",
  props: {
    element: Object,
    maList: Array
  },
  data() {
    return {
      // 材料需求列表
      demandList: [],
    };
  },
  watch: {
    element: {
      immediate: true,
      handler: function(element) {
        if (element.type === 'bpmn:ServiceTask') {
          this.demandList = JSON.parse(element.businessObject.$attrs.demand || '[]')
        }
      }
    }
  },
  beforeDestroy() {
    this.bpmnElement = null
  }
};
</script>
