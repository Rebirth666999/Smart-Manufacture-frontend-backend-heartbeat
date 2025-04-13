<template>
  <div class="app-container">
    <el-card class="view-card">
      <div slot="header">
        <div class="card-header">
          <div>流程基本信息</div>
        </div>
      </div>
      <div>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="目标产品" prop="maCode">
            <el-select
              v-model="queryParams.maCode"
              placeholder="请选择目标产品"
              clearable
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
            <el-input
              v-model="queryParams.procName"
              placeholder="请输入工艺流程名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="状态" prop="procStat">
            <el-select v-model="queryParams.procStat" placeholder="请选择工艺流程状态" clearable>
              <el-option
                v-for="dict in dict.type.ices_process_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="已删除" prop="procDelete">
            <el-input
              v-model="queryParams.procDelete"
              placeholder="请输入已删除"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item> -->
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
    
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:process:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:process:edit']"
            >设计</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:process:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:process:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
    
        <el-table 
          v-loading="loading"
          :data="processList"
          @current-change="handleCurrentChange"
          highlight-current-row
          max-height="240"
        >
          <el-table-column label="选择" width="55" align="center">
            <template slot-scope="scope">
              <el-radio :value="scope.row.procId === idSelect" :label="true" />
            </template>
          </el-table-column>
          <el-table-column label="工艺流程ID" align="center" prop="procId" v-if="true"/>
          <el-table-column label="工艺流程编码" align="center" prop="procCode" />
          <el-table-column label="目标产品" align="center" prop="maCode">
            <template slot-scope="scope">
              {{ productList.find(ele => ele.maCode === scope.row.maCode).maName || '' }}
            </template>
          </el-table-column>
          <el-table-column label="工艺流程名称" align="center" prop="procName" />
          <el-table-column label="工艺流程状态" align="center" prop="procStat">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.ices_process_status" :value="scope.row.procStat"/>
            </template>
          </el-table-column>
          <!-- <el-table-column label="已删除" align="center" prop="procDelete" /> -->
          <!-- <el-table-column label="描述" align="center" prop="procDesc" /> -->
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-brush"
                v-show="scope.row.procStat === '1'"
                v-hasPermi="['system:process:edit']"
                @click="handleDesign(scope.row)"
              >设计</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-finished"
                v-show="scope.row.procStat === '1'"
                @click="handleSubmitReview(scope.row)"
              >提交审核</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-refresh-left"
                v-show="scope.row.procStat === '2' || scope.row.procStat === '7'"
                @click="handleWithdrawReview(scope.row)"
              >撤回审核</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-video-pause"
                v-show="scope.row.procStat === '5'"
                @click="handleDeactivate(scope.row)"
              >停用</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-video-play"
                v-show="scope.row.procStat === '4'"
                @click="handleActivate(scope.row)"
              >激活</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                v-show="scope.row.procStat === '4'"
                @click="handleDepreciateReview(scope.row)"
              >弃用</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:process:remove']"
                v-show="scope.row.procStat === '1'"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
    
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </el-card>
    <el-card class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>流程详细信息</div>
        </div>
      </div>
      <process-viewer 
        v-if='idSelect'
        :key="idSelect"
        v-loading="viewerData.loading"
        :xml="viewerData.bpmnXml"
        :style="{ height: 'calc(100vh - 180px)' }"
        :mode="2"
        :extraList="{ emList: equipmentModelList, moList: modelOperationList, maList: materialList }"
      />
      <el-empty v-else description="选中工艺流程后即可查看流程详情" />
    </el-card>
  </div>
</template>

<script>
import { listProcess, getProcess, delProcess, addProcess, updateProcess, saveModel, getBpmnXml } from "@/api/system/process";
import { listMaterial } from "@/api/system/material";
import { listEquipmentModel } from "@/api/system/equipmentModel";
import { listModelOperation } from "@/api/system/modelOperation";
import ProcessDesigner from '@/components/ProcessDesigner';
import ProcessViewer from '@/components/ProcessViewerIndustry';


export default {
  name: "Process",
  components: {
    ProcessDesigner,
    ProcessViewer,
  },
  dicts: ['ices_process_status'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中内容
      idSelect: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工艺流程表格数据
      processList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        maCode: undefined,
        procName: undefined,
        procStat: undefined,
        procDelete: 0,
      },
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
      // 查看器相关数据
      viewerData: {
        loading: false,
        bpmnXml: ''
      },
      // 产品列表
      productList: [],
      // 原料列表
      materialList: [],
      // 设备模型列表
      equipmentModelList: [],
      // 模型操作列表
      modelOperationList: [],
    };
  },
  async created() {
    await this.getProductList();
    await this.getEquipmentModelList();
    await this.getModelOperationList();
    this.getList();
  },
  async activated() {
    await this.getProductList();
    await this.getEquipmentModelList();
    await this.getModelOperationList();
    this.getList();
  },
  methods: {
    // 查询产品列表
    getProductList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listMaterial().then(response => {
          this.materialList = response.rows.filter(ele => ele.maType === '1')
          this.productList = response.rows.filter(ele => ele.maType === '2')
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
    /** 查询工艺流程列表 */
    getList() {
      this.loading = true;
      listProcess(this.queryParams).then(response => {
        this.processList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.idSelect = undefined
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.idSelect = undefined
      this.handleQuery();
    },
    // 选中数据条目
    handleCurrentChange(current, old) {
      this.idSelect = current.procId
      if (current.procModel) {
        this.viewerData.loading = true
        getBpmnXml(current.procModel).then(response => {
          this.viewerData.bpmnXml = response.data || ''
          this.viewerData.loading = false
        })
      } else {
        this.viewerData.bpmnXml = ''
      }
      
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push(`/process/design`)
    },
    /** 顶部设计按钮操作 */
    handleUpdate(event) {
      const row = this.processList.find(ele => ele.procId === this.ids[0])
      if (row) {
        this.handleDesign(row)
      }
    },
    /** 行内设计按钮操作 */
    handleDesign(row) {
      this.$router.push(`/process/design?procId=${row.procId}`)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const procIds = row.procId || this.idSelect;
      this.$modal.confirm('是否确认删除工艺流程编号为"' + procIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProcess(procIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/process/export', {
        ...this.queryParams
      }, `process_${new Date().getTime()}.xlsx`)
    },
    // 提交审核
    handleSubmitReview(row) {
      const procId = row.procId;
      this.$modal.confirm('是否要提交审核？审核在开始之前可以撤回。').then(() => {
        this.loading = true;
        getProcess(procId).then(response => {
          this.form = response.data;
          this.form.procStat = "2";
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已提交审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 撤回审核
    handleWithdrawReview(row) {
      const procId = row.procId;
      this.$modal.confirm('是否要撤回审核？若审核已开始即无法撤回。').then(() => {
        this.loading = true;
        getProcess(procId).then(response => {
          this.form = response.data;
          if (this.form.procStat === '2') this.form.procStat = '1'
          else this.form.procStat = '4';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已撤回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 弃用工艺流程
    handleDepreciateReview(row) {
      const procId = row.procId;
      this.$modal.confirm('是否弃用此工艺流程？弃用工艺流程需要进行工艺流程弃用审核。').then(() => {
        this.loading = true;
        getProcess(procId).then(response => {
          this.form = response.data;
          this.form.procStat = '7';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已提交工艺流程弃用审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 激活工艺流程
    handleActivate(row) {
      const procId = row.procId;
      this.$modal.confirm('是否激活此工艺流程？').then(() => {
        this.loading = true;
        getProcess(procId).then(response => {
          this.form = response.data;
          this.form.procStat = '5';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已激活工艺流程");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 取消激活工艺流程
    handleDeactivate(row) {
      const procId = row.procId;
      this.$modal.confirm('是否停用此工艺流程？').then(() => {
        this.loading = true;
        getProcess(procId).then(response => {
          this.form = response.data;
          this.form.procStat = '4';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已停用工艺流程");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    }
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
::v-deep .el-radio span.el-radio__label {
  display: none;
}
.view-card {
  max-height: 50vh;
  overflow: scroll;
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