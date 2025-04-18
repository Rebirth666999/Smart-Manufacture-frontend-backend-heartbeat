<template>
  <div class="panel-tab__content">
    <div class="element-property input-property">
      <div class="element-property__label">设备模型：</div>
      <div class="element-property__value">
        <el-select
          size="mini"
          v-model="emCode"
          @change="updateEquipmentModel"
          @blur="updateEquipmentModel"
        >
          <el-option
            v-for="item in emList.filter((ele) => ele.emStat === '4')"
            :key="item.emCode"
            :label="item.emName"
            :value="item.emCode"
          ></el-option>
        </el-select>
      </div>
    </div>
    <div class="element-property input-property" v-show="emCode">
      <div class="element-property__label">模型操作：</div>
      <div class="element-property__value">
        <el-select
          size="mini"
          v-model="moCode"
          @change="updateModelOperation"
          @blur="updateModelOperation"
        >
          <el-option
            v-for="item in moList.filter((ele) => ele.emCode === emCode)"
            :key="item.moCode"
            :label="item.moName"
            :value="item.moCode"
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
            :label="`[${item.businessObject.id}] ${item.businessObject.name}`"
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
      emCode: '',
      moCode: '',
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
        this.emCode = window.bpmnInstances.bpmnElement.businessObject.$attrs.emCode || ''
        this.moCode = window.bpmnInstances.bpmnElement.businessObject.$attrs.moCode || ''
        this.psDesc = window.bpmnInstances.bpmnElement.businessObject.$attrs.psDesc || ''
        this.elements = window.bpmnInstances.bpmnElement.parent.children.filter((ele) => ele.type === "bpmn:ServiceTask" && ele.businessObject.$attrs.moCode)
        this.prev = JSON.parse(window.bpmnInstances.bpmnElement.businessObject.$attrs.prev || '[]')
        // 删除不存在的ID
        for (let i = this.prev.length - 1; i > -1; i--) {
          if (this.elements.findIndex((ele) => ele.id === this.prev[i]) === -1) {
            this.prev.splice(i, 1); 
          }
        }
        window.bpmnInstances.modeling.updateProperties(
          window.bpmnInstances.bpmnElement,
          { prev: JSON.stringify(this.prev) }
        );
      }
    }
  },
  methods: {
    // 更新属性：设备模型
    updateEquipmentModel() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { emCode: this.emCode }
      );
      this.moCode = ''
      let concat = this.emList.find(ele => ele.emCode === this.emCode).emName || ''
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { name: concat }
      );
    },
    // 更新属性：设备模型
    updateModelOperation() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { moCode: this.moCode }
      );
      let name = this.emList.find(ele => ele.emCode === this.emCode).emName + ": " || ''
      let concat = this.moList.find(ele => ele.moCode === this.moCode).moName || ''
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
        { psDesc: this.psDesc }
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
