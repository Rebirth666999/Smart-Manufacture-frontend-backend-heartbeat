<template>
  <div class="app-container">
    <el-tabs v-model="activeName" v-loading="loading">
      <el-tab-pane label="基本信息" name="basic">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="目标产品" prop="maCode">
            <el-select
              v-model="form.maCode"
              placeholder="请选择目标产品"
            >
              <el-option
                v-for="item in productList"
                :key="item.maCode"
                :label="item.maName"
                :value="item.maCode"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="名称" prop="procName">
            <el-input v-model="form.procName" placeholder="请输入工艺流程名称" />
          </el-form-item>
          <el-form-item label="描述" prop="procDesc">
            <el-input v-model="form.procDesc" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-form>
        <div>
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">更 新</el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane label="流程设计" name="process">
        <process-designer
          :key="designerOpen"
          :style="{height: 'calc(100vh - 180px)'}"
          ref="modelDesigner"
          v-loading="designerData.loading"
          :bpmnXml="designerData.bpmnXml"
          :designerForm="designerData.form"
          :mode="2"
          :extraList="{emList: equipmentModelList, moList: modelOperationList, maList: materialList}"
          @save="onSaveDesigner"
        />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { listProcess, getProcess, delProcess, addProcess, updateProcess, saveModel, getBpmnXml } from "@/api/system/process";
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
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        procId: [
          { required: true, message: "工艺流程ID不能为空", trigger: "blur" }
        ],
        maCode: [
          { required: true, message: "目标产品不能为空", trigger: "blur" }
        ],
        procName: [
          { required: true, message: "工艺流程名称不能为空", trigger: "blur" }
        ],
      },
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
      // 显示标签页
      activeName: 'basic',
      // 产品列表
      productList: [],
      // 原料列表
      materialList: [],
      // 当前工艺流程
      current: null,
      // 设备模型列表
      equipmentModelList: [],
      // 模型操作列表
      modelOperationList: [],
    };
  },
  async created() {
    if (this.$route.query.procId) {
      this.activeName = 'basic'
      await this.getProductList();
      await this.getEquipmentModelList();
      await this.getModelOperationList();
      this.designerData.loading = true
      getProcess(this.$route.query.procId).then(response => {
        const row = response.data
        this.current = row
        this.form = row
        this.designerData.modelId = "model_" + row.procId;
        this.designerData.form = {
          processName: row.procName,
          processKey: "process_" + row.procId
        }
        // 读取已设计的流程
        if (row.procModel) {
          this.designerData.loading = true;
          getBpmnXml(row.procModel).then(response => {
            this.designerData.bpmnXml = response.data || '';
            this.designerData.loading = false;
            this.designerOpen = true;
          })
        } else {
          this.designerData.bpmnXml = '';
          this.designerOpen = true;
        }
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
      this.designerData.loading = true
      getProcess(this.$route.query.procId).then(response => {
        const row = response.data
        this.current = row
        this.form = row
        this.designerData.modelId = "model_" + row.procId;
        this.designerData.form = {
          processName: row.procName,
          processKey: "process_" + row.procId
        }
        // 读取已设计的流程
        if (row.procModel) {
          this.designerData.loading = true;
          getBpmnXml(row.procModel).then(response => {
            this.designerData.bpmnXml = response.data || '';
            this.designerData.loading = false;
            this.designerOpen = true;
          })
        } else {
          this.designerData.bpmnXml = '';
          this.designerOpen = true;
        }
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
        listMaterial().then(response => {
          this.productList = response.rows.filter(ele => ele.maType === '2')
          this.materialList = response.rows.filter(ele => ele.maType === '1')
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
    // 表单重置
    reset() {
      this.form = {
        procId: undefined,
        maCode: undefined,
        procName: undefined,
        procStat: undefined,
        procDelete: undefined,
        procDesc: undefined,
        createBy: undefined,
        updateBy: undefined,
        createTime: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.procId != null) {
            updateProcess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 新增状态自动转为未发布
            this.form.procStat = '1'
            addProcess(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    // 保存流程按钮操作
    onSaveDesigner(bpmnXml) {
      this.$confirm("是否保存工艺流程？", "提示", {
        distinguishCancelAndClose: true,
        confirmButtonText: '是',
        cancelButtonText: '否'
      }).then(() => {
        this.designerData.loading = true;
        saveModel(bpmnXml).then(response => {
          this.$modal.msgSuccess("保存成功");
        }).finally(() => {
          this.designerData.loading = false;
        })
      }).catch(action => {
      })
    },
  }
};
</script>
<style scoped>
.el-select {
  width: 100%;
}
.el-date-editor{
  width: 100%;
}
</style>