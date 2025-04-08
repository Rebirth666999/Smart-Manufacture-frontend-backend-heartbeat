<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属异常" prop="exCode">
        <el-select v-model="queryParams.exCode" placeholder="请选择所属异常" clearable>
         <el-option
          v-for="option in exceptionList"
          :key="option.exCode"
          :label="option.exName"
          :value="option.exCode">
         </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="expName">
        <el-input
          v-model="queryParams.expName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="expType">
        <el-select v-model="queryParams.expType" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.exp_type_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="expDelete">
        <el-input
          v-model="queryParams.expDelete"
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
          v-hasPermi="['system:exceptionParam:add']"
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
          v-hasPermi="['system:exceptionParam:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:exceptionParam:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionParam:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionParamList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="异常参数ID" align="center" prop="expId" v-if="true"/>
      <el-table-column label="异常参数编码" align="center" prop="expCode" />
      <el-table-column label="所属异常" align="center" prop="exCode">
        <template slot-scope="scope">
          {{ exceptionList.find(ele => ele.exCode === scope.row.exCode).exName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="名称" align="center" prop="expName" />
      <el-table-column label="类型" align="center" prop="expType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.exp_type_status" :value="scope.row.expType"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="expDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exceptionParam:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exceptionParam:remove']"
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

    <!-- 添加或修改异常参数对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属异常" prop="exCode">
          <el-select v-model="form.exCode" placeholder="请选择异常" clearable>
            <el-option
             v-for="option in exceptionList"
             :key="option.exCode"
             :label="option.exName"
             :value="option.exCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="expName">
          <el-input v-model="form.expName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="类型" prop="expType">
          <el-select v-model="form.expType" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.exp_type_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import { listExceptionParam, getExceptionParam, delExceptionParam, addExceptionParam, updateExceptionParam } from "@/api/system/exceptionParam";
import { listException } from "@/api/system/exception";

export default {
  name: "ExceptionParam",
  dicts: ['exp_type_status'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 异常参数表格数据
      exceptionParamList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        expCode: undefined,
        exCode: undefined,
        expName: undefined,
        expType: undefined,
        expDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        expId: [
          { required: true, message: "异常参数ID不能为空", trigger: "blur" }
        ],
        exCode: [
          { required: true, message: "所属异常不能为空", trigger: "blur" }
        ],
        expName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        expType: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
        expDelete: [
          { required: true, message: "已删除不能为空", trigger: "blur" }
        ],
      },
      // 异常列表
      exceptionList: []
    };
  },
  async created() {
    await this.getExceptionList();
    this.getList();
  },
  async activated() {
    await this.getExceptionList();
    this.getList();
  },
  methods: {
    // 获取异常列表
    getExceptionList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listException().then(response => {
          this.exceptionList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询异常参数列表 */
    getList() {
      this.loading = true;
      listExceptionParam(this.queryParams).then(response => {
        this.exceptionParamList = response.rows;
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
        expId: undefined,
        expCode: undefined,
        exCode: undefined,
        expName: undefined,
        expType: undefined,
        expDelete: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.expId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加异常参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const expId = row.expId || this.ids
      getExceptionParam(expId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常参数";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.expId != null) {
            updateExceptionParam(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionParam(this.form).then(response => {
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
      const expIds = row.expId || this.ids;
      this.$modal.confirm('是否确认删除异常参数编号为"' + expIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionParam(expIds);
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
      this.download('system/exceptionParam/export', {
        ...this.queryParams
      }, `exceptionParam_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scope>
.el-select{
  width: 100%;
}
.el-date-editor{
  width: 100%;
}
</style>