<template>
  <div class="panel-tab__content">
    <el-form class="penal-form" label-width="70px">
      <el-form-item label="设备">
        <el-select
          v-model="eqId"
          @change="updateEquipment"
        >
          <el-option
            v-for="item in eqList.filter(ele => ele.emId === attrs.emId)"
            :key="item.eqId"
            :label="item.eqName"
            :value="item.eqId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备操作" v-show="eqId">
        <el-select
          v-model="eoId"
          @change="updateEquipmentOperation"
        >
          <el-option
            v-for="item in eoList.filter(ele => ele.eqId === eqId && ele.moId === attrs.moId)"
            :key="item.eoId"
            :label="item.eoName"
            :value="item.eoId"
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
      eqId: '',
      eoId: ''
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
          this.eqId = currentTask.eqId
          this.eoId = currentTask.eoId
        } else {
          this.eqId = ''
          this.eoId = ''
        }
      }
    }
  },
  methods: {
    // 更新属性：设备
    updateEquipment() {
      this.eoId = ''
      this.$emit('updateTask', { id: this.current.id, eqId: this.eqId, eoId: '' })
    },
    // 更新属性：设备操作
    updateEquipmentOperation() {
      this.$emit('updateTask', { id: this.current.id, eqId: this.eqId, eoId: this.eoId })
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
