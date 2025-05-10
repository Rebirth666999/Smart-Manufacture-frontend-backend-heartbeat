<template>
  <div class="app-container">
    <process-designer
      :key="designerOpen"
      :style="{ height: 'calc(100vh - 130px)' }"
      ref="modelDesigner"
      v-loading="designerData.loading"
      :bpmnXml="designerData.bpmnXml"
      :designerForm="designerData.form" :mode="3"
      :extraList="extraList"
      @save="onSaveDesigner"
    />
  </div>
</template>
<script>
import ProcessDesigner from '@/components/ProcessDesigner';
import { saveModel, getModel } from "@/api/system/exceptionLifecycle";
import { listUser } from "@/api/system/user";
import { listDept } from "@/api/system/dept";

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
      designerOpen: false,
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
      // 传入的生命周期ID
      exlId: '',
      // 额外传入编辑器的内容
      extraList: {
        userList: [],
        deptList: []
      }
    };
  },
  async created() {
    this.designerData.loading = true
    await this.getUserList()
    await this.getDeptList()
    this.exlId = this.$route.query.exlId
    getModel({exlId: this.exlId}).then(response => {
      this.designerData.bpmnXml = response.data
    }).finally(() => {
      this.designerOpen = true
      this.designerData.loading = false
    })
  },
  async activated() {
    this.designerData.loading = true
    await this.getUserList()
    await this.getDeptList()
    this.exlId = this.$route.query.exlId
    getModel({exlId: this.exlId}).then(response => {
      this.designerData.bpmnXml = response.data
    }).finally(() => {
      this.designerOpen = true
      this.designerData.loading = false
    })
  },
  methods: {
    // 获取部门列表
    getDeptList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listDept().then(response => {
          this.extraList.deptList = response.data
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 获取用户列表
    getUserList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listUser().then(response => {
          this.extraList.userList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 保存流程按钮操作
    onSaveDesigner(bpmnXml) {
      this.$confirm('是否保存异常生命周期为新版本？', '提示', {
        confirmButtonText: '保存',
        cancelButtonText: '取消',
      }).then(() => {
        // 保存为新版本
        this.designerData.loading = true;
        saveModel({
          exlId: this.exlId,
          xml: bpmnXml,
        }).then(response => {
          this.$modal.msgSuccess("保存成功");
          this.$tab.closePage();
        }).finally(() => {
          this.designerData.loading = false;
        })
      }).catch({

      })
    },
  }
}
</script>