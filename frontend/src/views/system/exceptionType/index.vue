<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="110px">
      <el-form-item label="上级异常类型" prop="extCodeParent">
        <el-select v-model="queryParams.extCodeParent" placeholder="请选择上级异常类型" clearable>
            <el-option
              v-for="option in exceptionTypeList"
              :key="option.extCodeParent"
              :label="option.extNameParent"
              :value="option.extCodeParent">
            </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="extName">
        <el-input
          v-model="queryParams.extName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="extDelete">
        <el-input
          v-model="queryParams.extDelete"
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
          v-hasPermi="['system:exceptionType:add']"
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
          v-hasPermi="['system:exceptionType:edit']"
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
          v-hasPermi="['system:exceptionType:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionType:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionTypeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="异常类型ID" align="center" prop="extId" v-if="true"/>
      <el-table-column label="异常类型编码" align="center" prop="extCode" />
      <el-table-column label="上级异常类型" align="center" prop="extCodeParent">
        <template slot-scope="scope">
          {{ scope.row.extCodeParent && exceptionTypeList.find(ele => ele.extCode === scope.row.extCodeParent).extName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="名称" align="center" prop="extName" />
      <!-- <el-table-column label="已删除" align="center" prop="extDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="extDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exceptionType:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exceptionType:remove']"
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

    <!-- 添加或修改异常类型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="上级异常类型" prop="extCodeParent">
          <el-select v-model="form.extCodeParent" placeholder="请选择上级异常类型" clearable>
            <el-option
              v-for="option in exceptionTypeList"
              :key="option.extCode"
              :label="option.extName"
              :value="option.extCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="extName">
          <el-input v-model="form.extName" placeholder="请输入名称" />
        </el-form-item>
        <!-- <el-form-item label="已删除" prop="extDelete">
          <el-input v-model="form.extDelete" placeholder="请输入已删除" />
        </el-form-item> -->
        <el-form-item label="描述" prop="extDesc">
          <el-input v-model="form.extDesc" type="textarea" placeholder="请输入内容" />
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
import { listExceptionType, getExceptionType, delExceptionType, addExceptionType, updateExceptionType } from "@/api/system/exceptionType";

export default {
  name: "ExceptionType",
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
      // 异常类型表格数据
      exceptionTypeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        extCode: undefined,
        extCodeParent: undefined,
        extName: undefined,
        extDelete: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        extId: [
          { required: true, message: "异常类型ID不能为空", trigger: "blur" }
        ],
        extName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        extDelete: [
          { required: true, message: "已删除不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询异常类型列表 */
    getList() {
      this.loading = true;
      listExceptionType(this.queryParams).then(response => {
        this.exceptionTypeList = response.rows;
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
        extId: undefined,
        extCode: undefined,
        extCodeParent: undefined,
        extName: undefined,
        extDelete: 0,
        extDesc: undefined,
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
      this.ids = selection.map(item => item.extId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加异常类型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const extId = row.extId || this.ids
      getExceptionType(extId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常类型";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.extId != null) {
            updateExceptionType(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionType(this.form).then(response => {
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
      const extIds = row.extId || this.ids;
      this.$modal.confirm('是否确认删除异常类型编号为"' + extIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionType(extIds);
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
      this.download('system/exceptionType/export', {
        ...this.queryParams
      }, `exceptionType_${new Date().getTime()}.xlsx`)
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