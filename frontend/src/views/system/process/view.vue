<template>
  <div class="app-container">
    <el-tabs v-model="activeName" v-loading="loading">
      <el-tab-pane label="基本信息" name="basic">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="工艺流程ID">
            {{ current && current.procId }}
          </el-descriptions-item>
          <el-descriptions-item label="工艺流程编码">
            {{ current && current.procCode }}
          </el-descriptions-item>
          <el-descriptions-item label="目标产品">
            {{ current && productList.find(ele => ele.maCode === current.maCode).maName || ''}}
          </el-descriptions-item>
          <el-descriptions-item label="工艺流程名称">
            {{ current && current.procName }}
          </el-descriptions-item>
          <el-descriptions-item label="工艺流程状态">
            <dict-tag :options="dict.type.ices_process_status" :value="current.procStat" />
          </el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>
      <el-tab-pane label="原料需求" name="material">
        <ProcessMaterial :viewMode="2" style="width: 100%;"/>
      </el-tab-pane>
      <el-tab-pane label="流程查看" name="process">
        <process-viewer v-loading="viewerData.loading" :key="`designer-${viewerData.index}`" :xml="viewerData.bpmnXml"
          :style="{ height: 'calc(100vh - 180px)' }" :mode="2"
          :extraList="{ emList: equipmentModelList, moList: modelOperationList }" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getProcess, getBpmnXml } from "@/api/system/process";
import { listMaterial } from "@/api/system/material";
import { listEquipmentModel } from "@/api/system/equipmentModel";
import { listModelOperation } from "@/api/system/modelOperation";
import ProcessDesigner from '@/components/ProcessDesigner';
import ProcessViewer from '@/components/ProcessViewerIndustry';
import ProcessMaterial from '@/views/system/processMaterial';


export default {
  name: "Process",
  components: {
    ProcessDesigner,
    ProcessViewer,
    ProcessMaterial
  },
  dicts: ['ices_process_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 查看器相关数据
      viewerData: {
        loading: false,
        index: undefined,
        bpmnXml: ''
      },
      // 显示标签页
      activeName: 'basic',
      // 产品列表
      productList: [],
      // 设备模型列表
      equipmentModelList: [],
      // 模型操作列表
      modelOperationList: [],
      // 当前工艺流程
      current: null,
    };
  },
  async created() {
    if (this.$route.query.procId) {
      this.activeName = 'basic'
      await this.getProductList();
      await this.getEquipmentModelList();
      await this.getModelOperationList();
      this.viewerData.loading = true
      getProcess(this.$route.query.procId).then(response => {
        const row = response.data
        this.current = row
        this.viewerData.index = row.procModel
        getBpmnXml(row.procModel).then(response => {
          this.viewerData.bpmnXml = response.data || ''
          this.viewerData.loading = false
        })
      })
    } else {
      this.$modal.msgError("请重新进入此页面");
      this.$router.back();
    }
  },
  async activated() {
    if (this.$route.query.procId) {
      this.activeName = 'basic'
      await this.getProductList();
      await this.getEquipmentModelList();
      await this.getModelOperationList();
      this.viewerData.loading = true
      getProcess(this.$route.query.procId).then(response => {
        const row = response.data
        this.current = row
        this.viewerData.index = row.procModel
        getBpmnXml(row.procModel).then(response => {
          this.viewerData.bpmnXml = response.data || ''
          this.viewerData.loading = false
        })
      })
    } else {
      this.$modal.msgError("请重新进入此页面");
      this.$router.back();
    }
  },
  methods: {
    // 查询产品列表
    getProductList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listMaterial({ maType: '2' }).then(response => {
          this.productList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询设备模型列表
    getEquipmentModelList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listEquipmentModel().then(response => {
          this.equipmentModelList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询模型操作列表
    getModelOperationList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listModelOperation().then(response => {
          this.modelOperationList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
  }
};
</script>
<style scoped>
.el-select {
  width: 100%;
}

.el-date-editor {
  width: 100%;
}
</style>