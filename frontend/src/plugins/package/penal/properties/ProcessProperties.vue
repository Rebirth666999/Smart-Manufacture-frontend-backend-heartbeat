<template>
  <div class="panel-tab__content">
    <div class="element-property input-property">
      <div class="element-property__label">设备模型：</div>
      <div class="element-property__value">
        <el-select
          size="mini"
          v-model="emId"
          @change="updateEquipmentModel"
          @blur="updateEquipmentModel"
        >
          <el-option
            v-for="item in emList"
            :key="item.emId"
            :label="item.emName"
            :value="item.emId"
          ></el-option>
        </el-select>
      </div>
    </div>
    <div class="element-property input-property" v-show="emId">
      <div class="element-property__label">模型操作：</div>
      <div class="element-property__value">
        <el-select
          size="mini"
          v-model="moId"
          @change="updateModelOperation"
          @blur="updateModelOperation"
        >
          <el-option
            v-for="item in moList.filter((ele) => ele.emId === emId)"
            :key="item.moId"
            :label="item.moName"
            :value="item.moId"
          ></el-option>
        </el-select>
      </div>
    </div>
    <div class="element-property input-property">
      <div class="element-property__label">描述：</div>
      <div class="element-property__value">
        <el-input
          type="textarea"
          v-model="psDesc"
          size="mini"
          resize="vertical"
          :autosize="{ minRows: 2, maxRows: 4 }"
          @input="updateDesc"
          @blur="updateDesc"
        />
      </div>
    </div>
    <div class="element-property input-property">
      <div class="element-property__label">跨轮次前序步骤：</div>
      <div class="element-property__value">
        <el-select
          size="mini"
          multiple
          v-model="prev"
          @change="updatePrevious"
          @blur="updatePrevious"
        >
          <el-option
            v-for="item in elements"
            :key="item.businessObject.id"
            :label="item.businessObject.name"
            :value="item.businessObject.id"
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
    emList: Array,
    moList: Array
  },
  data() {
    return {
      emId: '',
      moId: '',
      psDesc: '',
      prev: [],
      elements: []
    };
  },
  watch: {
    // 切换选择的对象时，获取信息
    id: {
      immediate: true,
      handler: function(id) {
        this.emId = window.bpmnInstances.bpmnElement.businessObject.$attrs.emId || ''
        this.moId = window.bpmnInstances.bpmnElement.businessObject.$attrs.moId || ''
        this.psDesc = window.bpmnInstances.bpmnElement.businessObject.$attrs.psDesc || ''
        this.elements = window.bpmnInstances.bpmnElement.parent.children.filter((ele) => ele.type === "bpmn:ServiceTask")
        this.prev = JSON.parse(window.bpmnInstances.bpmnElement.businessObject.$attrs.prev || '[]')
      }
    }
  },
  methods: {
    // 更新属性：设备模型
    updateEquipmentModel() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { emId: this.emId }
      );
      this.moId = ''
      let concat = this.emList.find(ele => ele.emId === this.emId).emName || ''
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { name: concat }
      );
    },
    // 更新属性：设备模型
    updateModelOperation() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { moId: this.moId }
      );
      let name = this.emList.find(ele => ele.emId === this.emId).emName + ": " || ''
      let concat = this.moList.find(ele => ele.moId === this.moId).moName || ''
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { name: name + concat || '' }
      );
    },
    // 更新属性：跨轮次前序步骤
    updatePrevious() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { prev: JSON.stringify(this.prev) }
      );
    },
    // 更新属性：描述
    updateDesc() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { eosDesc: this.eosDesc }
      );
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
