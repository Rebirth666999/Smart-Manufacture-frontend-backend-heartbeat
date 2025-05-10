<template>
  <div class="panel-tab__content">
    <div class="element-property input-property">
      <div class="element-property__label">任务描述：</div>
      <div class="element-property__value">
        <el-input
          type="textarea"
          v-model="documentation"
          size="mini"
          resize="vertical"
          :autosize="{ minRows: 2, maxRows: 4 }"
          @input="updateDocumentation"
          @blur="updateDocumentation"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ElementOtherConfig",
  props: {
    id: String
  },
  data() {
    return {
      documentation: ""
    };
  },
  watch: {
    id: {
      immediate: true,
      handler: function(id) {
        if (id && id.length) {
          this.$nextTick(() => {
          this.documentation = "";
          });
        } else {
          this.documentation = "";
        }
      }
    }
  },
  methods: {
    updateDocumentation() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { formKey: this.documentation }
      );
    }
  },
  beforeDestroy() {
    this.bpmnElement = null;
  }
};
</script>
