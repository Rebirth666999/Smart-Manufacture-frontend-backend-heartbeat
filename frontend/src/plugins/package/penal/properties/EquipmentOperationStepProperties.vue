<template>
  <div class="panel-tab__content">
    <div class="element-property input-property">
      <div class="element-property__label">原子操作：</div>
      <div class="element-property__value">
        <el-select
          size="mini"
          v-model="eaoId"
          @change="updateAtomOperation"
          @blur="updateAtomOperation"
        >
          <el-option
            v-for="item in list"
            :key="item.eaoId"
            :label="item.eaoName"
            :value="item.eaoId"
          ></el-option>
        </el-select>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "EquipmentOperationStepProperties",
  props: {
    id: String,
    list: Array
  },
  data() {
    return {
      eaoId: ''
    };
  },
  watch: {
    // 切换选择的对象时，获取信息
    id: {
      immediate: true,
      handler: function(id) {
        this.eaoId = window.bpmnInstances.bpmnElement.businessObject.$attrs.eaoId || ''
      }
    }
  },
  methods: {
    // 更新属性：原子操作
    updateAtomOperation() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { eaoId: this.eaoId }
      );
    }
  },
  beforeDestroy() {
    this.bpmnElement = null;
  }
};
</script>
