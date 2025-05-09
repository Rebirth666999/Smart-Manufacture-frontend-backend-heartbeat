<template>
  <div class="app-container">
    <el-card class="view-card">
      <div slot="header">
        <div class="card-header">
          <div>流程基本信息</div>
        </div>
      </div>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
            v-for="dict in dict.type.ices_process_status_review"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
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
      <el-table-column label="产品需求" align="center" prop="odCode">
        <template slot-scope="scope">
          {{ parseOdCode(scope.row.odCode) }}
        </template>
      </el-table-column>
      <el-table-column label="工艺流程名称" align="center" prop="procName" />
      <el-table-column label="工艺流程状态" align="center" prop="procStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_process_status_review" :value="scope.row.procStat"/>
        </template>
      </el-table-column>
     
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">

          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="scope.row.procStat === '2' || scope.row.procStat === '7'"
            @click="startReview(scope.row)"
          >开始审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            v-show="scope.row.procStat === '3' || scope.row.procStat === '8'"
            @click="passReview(scope.row)"
          >通过审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            v-show="scope.row.procStat === '3' || scope.row.procStat === '8'"
            @click="rejectReview(scope.row)"
          >驳回审核</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    /></el-card>



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
import { getProcess, listProcess, getBpmnXml, updateProcess ,listReviewProcess} from "@/api/system/process";
import { listProduct } from "@/api/system/product";
import { listOrderDemand } from "@/api/system/orderDemand";
import ProcessViewer from '@/components/ProcessViewerIndustry';
import { listMaterial } from "@/api/system/material";
import { listEquipmentModel } from "@/api/system/equipmentModel";
import { listModelOperation } from "@/api/system/modelOperation";

export default {
  name: "ProcessReview",
  components: {
    ProcessViewer,
  },
  dicts: ['ices_process_status_review'],
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        prCode: undefined,
        procName: undefined,
        procStat: undefined,
        procDelete: 0,
      },
      // 产品列表
      productList: [],
      // 查看窗口是否打开
      viewerOpen: false,
      // 查看器相关数据
      viewerData: {
        title: '',
        loading: false,
        index: undefined,
        bpmnXml: ''
      },
      // 表单数据
      form: {},
      // 产品列表
      productList: [],
      // 原料列表
      materialList: [],
      // 设备模型列表
      equipmentModelList: [],
      // 模型操作列表
      modelOperationList: [],
      // 订单产品需求列表
      orderDemandList: []
    };
  },
  async created() {
    await this.getMaterialList();
    await this.getProductList();
    await this.getOrderDemandList();
    await this.getEquipmentModelList();
    await this.getModelOperationList();
    this.getList();
  },
  async activated() {
    await this.getMaterialList();
    await this.getProductList();
    await this.getOrderDemandList();
    await this.getEquipmentModelList();
    await this.getModelOperationList();
    this.getList();
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
          response.rows.forEach(demand => {
            this.orderDemandList.push({
              ...demand,
              prName: this.productList.find(ele => ele.prCode === demand.prCode).prName
            })
          });
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
    /** 查询工艺流程列表 */
    getList() {
      this.loading = true;
      listReviewProcess(this.queryParams).then(response => {
        this.processList = response.rows;
        this.total = response.total;
        this.idSelect = undefined
        this.loading = false;
      });
    },
    // 开始审核
    startReview(row) {
      this.$modal.confirm('是否要开始审核？').then(() => {
        this.loading = true;
        getProcess(row.procId).then(response => {
          this.form = response.data;
          if (this.form.procStat === '2') this.form.procStat = '3';
          else this.form.procStat = '8';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已开始审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 通过审核
    passReview(row) {
      this.$modal.confirm('是否要通过审核？').then(() => {
        this.loading = true;
        getProcess(row.procId).then(response => {
          this.form = response.data;
          if (this.form.procStat === '3' || this.form.procStat === '8')
            this.form.procStat = '4';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已通过审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 驳回审核
    rejectReview(row) {
      this.$modal.confirm('是否要驳回审核？').then(() => {
        this.loading = true;
        getProcess(row.procId).then(response => {
          this.form = response.data;
          if (this.form.procStat === '3' || this.form.procStat === '8')
            this.form.procStat = '1';
          updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已驳回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
      this.idSelect = undefined
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.idSelect = undefined
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/process/export', {
        ...this.queryParams
      }, `process_${new Date().getTime()}.xlsx`)
    },
     // 选中数据条目
     handleCurrentChange(current, old) {
      if (current) {
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
      }
    },
    /**
     * 解析odCode为显示格式
     * @author YangZY
     * @date 20250423
     */ 
    parseOdCode(odCode) {
      const demand = this.orderDemandList.find(ele => ele.odCode === odCode)
      if (demand) {
        return `【${demand.orCode}】${demand.prName}`
      } else return ''
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