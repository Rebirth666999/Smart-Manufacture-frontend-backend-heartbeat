<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>产品质检单信息</div>
        </div>
      </div>
      <div>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
          label-width="68px">
          <el-form-item label="批次" prop="pbCode">
            <el-select v-model="queryParams.pbCode" placeholder="请选择批次">
              <el-option v-for="item in productBatchList" :key="item.pbCode" :label="item.pbBatch" :value="item.pbCode">
              </el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="已删除" prop="piDelete">
            <el-input v-model="queryParams.piDelete" placeholder="请输入已删除" clearable @keyup.enter.native="handleQuery" />
          </el-form-item> -->
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
              v-hasPermi="['system:productInspection:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
              v-hasPermi="['system:productInspection:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
              v-hasPermi="['system:productInspection:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
              v-hasPermi="['system:productInspection:export']">导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table
          v-loading="loading"
          :data="productInspectionList"
          @current-change="handleCurrentChange"
          highlight-current-row
        >
          <el-table-column label="选择" width="55" align="center">
            <template slot-scope="scope">
              <el-radio :value="scope.row.piId === idSelect" :label="true" />
            </template>
          </el-table-column>
          <el-table-column label="质检单ID" align="center" prop="piId" v-if="true" />
          <el-table-column label="质检单编码" align="center" prop="piCode" />
          <el-table-column label="批次" align="center" prop="pbCode">
            <template slot-scope="scope">
              {{ productBatchList.find(ele => ele.pbCode === scope.row.pbCode).pbBatch || '' }}
            </template>
          </el-table-column>
          <el-table-column label="负责人" align="center" prop="piMan" />
          <el-table-column label="开始日期" align="center" prop="piSdate" />
          <el-table-column label="部门" align="center" prop="piDept" />
          <el-table-column label="结束日期" align="center" prop="piEdate" />
          <!-- <el-table-column label="已删除" align="center" prop="piDelete" /> -->
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['system:productInspection:edit']">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['system:productInspection:remove']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize" @pagination="getList" />

        <!-- 添加或修改产品质检单对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="批次" prop="pbCode">
              <el-select v-model="form.pbCode" placeholder="请选择批次">
                <el-option v-for="item in productBatchList" :key="item.pbCode" :label="item.pbBatch"
                  :value="item.pbCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
      </div>
    </el-card>
    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>产品质检单明细</div>
        </div>
      </div>
      <product-inspection-detail v-if='idSelect' :key="idSelect" :piCode="codeSelect" />
      <el-empty v-else description="选中质检单后即可管理质检单明细" />
    </el-card>
  </div>
</template>

<script>
import { listProductInspection, getProductInspection, delProductInspection, addProductInspection, updateProductInspection } from "@/api/system/productInspection";
import { listProductBatch } from "@/api/system/productBatch";
import productInspectionDetail from "@/views/system/productInspectionDetail";

export default {
  name: "ProductInspection",
  components: {
    productInspectionDetail
  },
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中内容
      idSelect: undefined,
      // 选中code
      codeSelect: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 产品质检单表格数据
      productInspectionList: [],
      //显示批次列表
      productBatchList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        piCode: undefined,
        pbCode: undefined,
        piDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        piId: [
          { required: true, message: "质检单ID不能为空", trigger: "blur" }
        ],
        pbCode: [
          { required: true, message: "批次不能为空", trigger: "blur" }
        ],
      }
    };
  },
  async created() {
    this.getList();
    await this.getProductBatchList();
  },
  methods: {
    //查询批次列表
    getProductBatchList() {
      listProductBatch().then(response => {
        this.productBatchList = response.rows;
      })
    },
    /** 查询产品质检单列表 */
    getList() {
      this.loading = true;
      listProductInspection(this.queryParams).then(response => {
        this.productInspectionList = response.rows;
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
        piId: undefined,
        piCode: undefined,
        pbCode: undefined,
        piMan: undefined,
        piSdate: undefined,
        piDept: undefined,
        piEdate: undefined,
        piDelete: undefined,
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
      this.codeSelect = undefined
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.idSelect = undefined
      this.codeSelect = undefined
      this.handleQuery();
    },
    // 选中数据条目
    handleCurrentChange(current, old) {
      this.idSelect = current.piId
      this.codeSelect = current.piCode
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加产品质检单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const piId = row.piId || this.idSelect
      getProductInspection(piId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改产品质检单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.piId != null) {
            updateProductInspection(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addProductInspection(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const piIds = row.piId || this.idSelect;
      this.$modal.confirm('是否确认删除产品质检单编号为"' + piIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProductInspection(piIds);
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
      this.download('system/productInspection/export', {
        ...this.queryParams
      }, `productInspection_${new Date().getTime()}.xlsx`)
    }
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