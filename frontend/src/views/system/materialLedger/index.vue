<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>台账信息</div>
        </div>
      </div>
      <div>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="原料" prop="maCode">
            <el-select
              v-model="queryParams.maCode"
              placeholder="请选择原料"
              clearable
            >
              <el-option
                v-for="item in materialList"
                :key="item.maCode"
                :label="item.maName"
                :value="item.maCode"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="已删除" prop="mlDelete">
            <el-input
              v-model="queryParams.mlDelete"
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
              v-hasPermi="['system:materialLedger:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:materialLedger:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:materialLedger:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
    
        <el-table
          v-loading="loading"
          :data="materialLedgerList"
          @current-change="handleCurrentChange"
          highlight-current-row
          max-height="240"
        >
        <el-table-column label="选择" width="55" align="center">
          <template slot-scope="scope">
            <el-radio :value="scope.row.mlId === idSelect" :label="true" />
          </template>
        </el-table-column>
          <el-table-column label="台账ID" align="center" prop="mlId" v-if="true"/>
          <el-table-column label="台账编码" align="center" prop="mlCode" />
          <el-table-column label="原料" align="center" prop="maCode">
            <template slot-scope="scope">
              {{ materialList.find(ele => ele.maCode === scope.row.maCode).maName || '' }}
            </template>
          </el-table-column>
          <el-table-column label="库存量" align="center" prop="mlStock" />
          <!-- <el-table-column label="已删除" align="center" prop="mlDelete" /> -->
          <!-- <el-table-column label="描述" align="center" prop="mlDesc" /> -->
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:materialLedger:remove']"
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
    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>库存信息</div>
        </div>
      </div>
      <material-stock v-if='idSelect' :key="idSelect" :maCode="codeSelect" @update="getList" />
      <el-empty v-else description="选中台账后即可管理库存情况" />
    </el-card>

    <!-- 添加或修改原料台账对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="原料" prop="maCode">
          <el-select
            v-model="form.maCode"
            placeholder="请选择原料"
          >
            <el-option
              v-for="item in materialList"
              :key="item.maCode"
              :label="item.maName"
              :value="item.maCode"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="mlDesc">
          <el-input v-model="form.mlDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMaterialLedger, getMaterialLedger, delMaterialLedger, addMaterialLedger, updateMaterialLedger } from "@/api/system/materialLedger";
import { listMaterial } from "@/api/system/material";
import materialStock from '@/views/system/materialStock';

export default {
  name: "MaterialLedger",
  components: {
    materialStock
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
      // 原料台账表格数据
      materialLedgerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mlCode: undefined,
        maCode: undefined,
        mlDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mlId: [
          { required: true, message: "台账ID不能为空", trigger: "blur" }
        ],
        maCode: [
          { required: true, message: "原料不能为空", trigger: "blur" }
        ],
        mlStock: [
          { required: true, message: "库存量不能为空", trigger: "blur" }
        ],
      },
      // 原料列表
      materialList: [],
    };
  },
  async created() {
    await this.getMaterialList();
    this.getList();
  },
  async activated() {
    await this.getMaterialList();
    this.getList();
  },
  methods: {
    // 查询原料列表
    getMaterialList() {
      return new Promise((resolve, reject) => {
        listMaterial().then(response => {
          this.materialList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
        })
      })
    },
    /** 查询原料台账列表 */
    getList() {
      this.loading = true;
      listMaterialLedger(this.queryParams).then(response => {
        this.materialLedgerList = response.rows;
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
        mlId: undefined,
        mlCode: undefined,
        maCode: undefined,
        mlStock: undefined,
        mlDelete: undefined,
        mlDesc: undefined,
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
      if (current) {
        this.idSelect = current.mlId
        this.codeSelect = current.maCode
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加原料台账";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const mlId = row.mlId || this.idSelect
      getMaterialLedger(mlId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改原料台账";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.mlId != null) {
            updateMaterialLedger(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            this.form.mlStock = 0
            addMaterialLedger(this.form).then(response => {
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
      const mlIds = row.mlId || this.idSelect;
      this.$modal.confirm('是否确认删除原料台账编号为"' + mlIds + '"的数据项？').then(() => {
        this.loading = true;
        return delMaterialLedger(mlIds);
      }).then(() => {
        this.loading = false;
        this.idSelect = undefined
        this.codeSelect = undefined
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/materialLedger/export', {
        ...this.queryParams
      }, `materialLedger_${new Date().getTime()}.xlsx`)
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
