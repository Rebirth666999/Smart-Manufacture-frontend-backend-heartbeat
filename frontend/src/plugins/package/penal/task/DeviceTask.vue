<template>
  <div class="panel-tab__content">
    <el-form class="penal-form" label-width="70px">
      <el-form-item label="设备">
        <el-select
          v-model="eqCode"
          @change="updateEquipment"
        >
          <el-option
            v-for="item in eqList.filter(ele => ele.emCode === attrs.emCode)"
            :key="item.eqCode"
            :label="item.eqName"
            :value="item.eqCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备操作" v-show="eqCode">
        <el-select
          v-model="eoCode"
          @change="updateEquipmentOperation"
        >
          <el-option
            v-for="item in eoList.filter(ele => ele.eqCode === eqCode && ele.moCode === attrs.moCode)"
            :key="item.eoCode"
            :label="item.eoName"
            :value="item.eoCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "DeviceTask",
  props: {
    element: Object,
    eqList: Array,
    eoList: Array,
    taskList: Array
  },
  data() {
    return {
      current: null,
      attrs: {},
      eqCode: '',
      eoCode: ''
    };
  },
  watch: {
    // 切换选择的对象时，获取信息
    element: {
      immediate: true,
      handler: function(element) {
        this.current = element
        this.attrs = element.businessObject.$attrs
        const currentTask = this.taskList.find(ele => ele.id === element.id)
        if (currentTask) {
          this.eqCode = currentTask.eqCode
          this.eoCode = currentTask.eoCode
        } else {
          this.eqCode = ''
          this.eoCode = ''
        }
      }
    }
  },
  methods: {
    // 更新属性：设备
    updateEquipment() {
      this.eoCode = ''
      this.$emit('updateTask', { id: this.current.id, eqCode: this.eqCode, eoCode: '', dtModel: this.current.id, param: [] })
    },
    // 更新属性：设备操作
    updateEquipmentOperation() {
      this.$emit('updateTask', { id: this.current.id, eqCode: this.eqCode, eoCode: this.eoCode, dtModel: this.current.id, param: [] })
    },
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
