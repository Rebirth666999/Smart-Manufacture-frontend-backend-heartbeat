<template>
  <div class="app-container">
    <process-designer
      v-if='designerOpen'
      :key="designerOpen"
      :style="{ height: 'calc(100vh - 130px)' }"
      ref="modelDesigner"
      v-loading="designerData.loading"
      :bpmnXml="designerData.bpmnXml"
      :designerForm="designerData.form" :mode="3"
      :extraList="{}"
      @save="onSaveDesigner"
    />
  </div>
</template>
<script>
import ProcessDesigner from '@/components/ProcessDesigner';

export default {
  name: "Process",
  components: {
    ProcessDesigner,
  },
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
      // 设计窗口是否打开
      designerOpen: true,
      // 设计器相关数据
      designerData: {
        loading: false,
        bpmnXml: '',
        modelId: null,
        form: {
          processName: null,
          processKey: null
        }
      },
      exlId: ''
    };
  },
  created() {
    this.exlId = this.$route.query.exlId
  },
  activated() {
    this.exlId = this.$route.query.exlId
  },
  methods: {
    // 保存流程按钮操作
    onSaveDesigner(bpmnXml) {
      console.log(bpmnXml)
      // this.$confirm("是否保存工艺流程？", "提示", {
      //   distinguishCancelAndClose: true,
      //   confirmButtonText: '是',
      //   cancelButtonText: '否'
      // }).then(() => {
      //   this.designerData.loading = true;
      //   saveModel(bpmnXml).then(response => {
      //     this.$modal.msgSuccess("保存成功");
      //   }).finally(() => {
      //     this.designerData.loading = false;
      //   })
      // }).catch(action => {
      // })
    },
  }
}
</script>