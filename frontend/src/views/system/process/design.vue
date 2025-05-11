<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>工艺流程信息</div>
          <div>
            <el-button :loading="buttonLoading" type="primary" @click="submitForm">保 存</el-button>
            <el-button :loading="buttonLoading" @click="resetPage">重 置</el-button>
          </div>
        </div>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-col :span="8">
          <el-form-item label="产品需求" prop="odCode">
            <el-cascader v-model="form.odCode" :options="orderDemandOptions" placeholder="请选择产品需求" @change="selectOdCode"/>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="名称" prop="procName">
            <el-input v-model="form.procName" placeholder="请输入工艺流程名称" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="描述" prop="procDesc">
            <el-input v-model="form.procDesc" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
      </el-form>
    </el-card>
    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>工艺流程设计</div>
        </div>
      </div>
      <process-designer
        v-if='designerOpen'
        :key="designerOpen"
        :style="{ height: 'calc(100vh - 370px)' }"
        ref="modelDesigner"
        v-loading="designerData.loading"
        :bpmnXml="designerData.bpmnXml"
        :designerForm="designerData.form" :mode="2"
        :extraList="{ emList: equipmentModelList, moList: modelOperationList, maList: materialList }"
        @save="onSaveDesigner"
      />
      <el-empty v-else description="保存工艺流程后即可进行设计" />
    </el-card>
  </div>
</template>

<script>
import { listProcess, getProcess, delProcess, addProcess, updateProcess, saveModel, getBpmnXml } from "@/api/system/process";
import { listMaterial } from "@/api/system/material";
import { listProduct } from "@/api/system/product";
import { listOrderDemand } from "@/api/system/orderDemand";
import { listEquipmentModel } from "@/api/system/equipmentModel";
import { listModelOperation } from "@/api/system/modelOperation";
import ProcessDesigner from '@/components/ProcessDesigner';
import ProcessViewer from '@/components/ProcessViewerIndustry';


export default {
  name: "Process",
  components: {
    ProcessDesigner,
    ProcessViewer
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
        odCode: [
          { required: true, message: "产品需求不能为空", trigger: "blur" }
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
      // 订单产品需求列表
      orderDemandList: [],
      // 用于级联选择器的产品需求列表
      orderDemandOptions: []
    };
  },
  async created() {
    await this.getMaterialList();
    await this.getProductList();
    await this.getOrderDemandList();
    await this.getEquipmentModelList();
    await this.getModelOperationList();
    if (this.$route.query.procId) {
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
          this.designerData.loading = false;
          this.designerOpen = true;
        }
      })
    }
  },
  async activated() {
    await this.getMaterialList();
    await this.getProductList();
    await this.getOrderDemandList();
    await this.getEquipmentModelList();
    await this.getModelOperationList();
    if (this.$route.query.procId) {
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
          this.designerData.loading = false;
          this.designerOpen = true;
        }
      })
    }
  },
  methods: {
    /**
     * 查询订单产品需求
     * @author YangZY
     * @date 20250423
     */ 
    getOrderDemandList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listOrderDemand().then(response => {
          this.orderDemandList = []
          this.orderDemandOptions = []
          const orders = new Set()  // 集合，维护不重复的订单orCode
          // 构建原料需求列表
          // 须包含产品名称给用户显示
          response.rows.forEach(demand => {
            this.orderDemandList.push({
              ...demand,
              prName: this.productList.find(ele => ele.prCode === demand.prCode).prName
            })
            orders.add(demand.orCode)
          })
          // 订单为第一级，产品需求为第二级，构造级联选择器的选项
          orders.forEach(orCode => {
            this.orderDemandOptions.push({
              value: orCode,
              label: orCode,
              children: this.orderDemandList.filter(ele => ele.orCode === orCode).map(ele => { return {value: ele.odCode, label: ele.prName} })
            })
          })
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询原料列表
    getMaterialList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listMaterial().then(response => {
          this.materialList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询产品列表
    getProductList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listProduct().then(response => {
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
    // 表单重置
    reset() {
      this.form = {
        procId: undefined,
        odCode: undefined,
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
    // 重置按钮
    resetPage() {
      this.$router.replace(`/process/designProcess`)
      this.designerOpen = false
      this.reset()
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          // 级联选择器记录完整的选择路径，但提交只需要最后的那个
          this.form.odCode = this.form.odCode[1]
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
              this.$router.replace(`/process/designProcess?procId=${response.data.procId}`)
              this.form = response.data
              // 初始化设计器
              this.designerData.bpmnXml = ''
              this.designerData.modelId = "model_" + this.form.procId;
              this.designerData.form = {
                processName: this.form.procName,
                processKey: "process_" + this.form.procId
              }
              this.designerOpen = true
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
    /**
     * 选择产品需求的监听函数
     * 自动填入定制详情
     * @param {string[]} event 树形选择的所有key
     * @author YangZY
     * @date 20250426
     */
    selectOdCode(event) {
      const odCode = event[1]
      const demand = this.orderDemandList.find(ele => ele.odCode === odCode)
      this.form.procDesc = demand.odCust
    }
  }
};
</script>
<style scoped>
.el-select {
  width: 100%;
}

.el-cascader {
  width: 100%;
}

.el-date-editor {
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 17px;
}

.controlled-card {
  margin-top: 10px;
}
</style>