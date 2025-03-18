<template>
  <div class="panel-tab__content">
    <el-descriptions border>
      <el-descriptions-item label="ID">{{ elementId }}</el-descriptions-item>
    </el-descriptions>
  </div>
</template>
<script>

export default {
  name: "EquipmentOperationStepBaseInfo",
  props: {
    element: Object,
  },
  data() {
    return {
      elementId: ''
    };
  },
  watch: {
    element: {
      immediate: false,
      handler: function(val) {
        if (val) {
          this.elementId = val.id
        }
      }
    }
  }
};
</script>
