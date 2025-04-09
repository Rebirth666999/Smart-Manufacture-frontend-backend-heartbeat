<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="异常类型" prop="extCode">
        <el-select v-model="queryParams.extCode" placeholder="请选择异常类型" clearable>
         <el-option
          v-for="option in exceptionTypeList"
          :key="option.extCode"
          :label="option.extName"
          :value="option.extCode">
         </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="exName">
        <el-input
          v-model="queryParams.exName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="exDelete">
        <el-input
          v-model="queryParams.exDelete"
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
          v-hasPermi="['system:exception:add']"
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
          v-hasPermi="['system:exception:edit']"
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
          v-hasPermi="['system:exception:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exception:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="异常ID" align="center" prop="exId" v-if="true"/>
      <el-table-column label="异常编码" align="center" prop="exCode" />
      <el-table-column label="异常类型" align="center" prop="extCode">
        <template slot-scope="scope">
          {{ exceptionTypeList.find(ele => ele.extCode === scope.row.extCode).extName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="名称" align="center" prop="exName" />
      <!-- <el-table-column label="已删除" align="center" prop="exDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="exDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exception:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleParam(scope.row)"
          >异常参数</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-help"
            @click="handleLifecycle(scope.row)"
          >生命周期</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exception:remove']"
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

    <!-- 添加或修改异常对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="异常类型" prop="extCode">
          <el-select v-model="form.extCode" placeholder="请选择异常类型" clearable>
            <el-option
              v-for="option in exceptionTypeList"
              :key="option.extCode"
              :label="option.extName"
              :value="option.extCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="exName">
          <el-input v-model="form.exName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="描述" prop="exDesc">
          <el-input v-model="form.exDesc" type="textarea" placeholder="请输入内容" />
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
import { listException, getException, delException, addException, updateException } from "@/api/system/exception";
import { listExceptionType } from "@/api/system/exceptionType";

export default {
  name: "Exception",
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
      // 异常表格数据
      exceptionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        exCode: undefined,
        extCode: undefined,
        exName: undefined,
        exDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        exId: [
          { required: true, message: "异常ID不能为空", trigger: "blur" }
        ],
        extCode: [
          { required: true, message: "异常类型不能为空", trigger: "blur" }
        ],
        exName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        exDelete: [
          { required: true, message: "已删除不能为空", trigger: "blur" }
        ],
      },
      // 异常类型列表
      exceptionTypeList: []
    };
  },
  async created() {
    await this.getExceptionTypeList();
    this.getList();
  },
  async activated() {
    await this.getExceptionTypeList();
    this.getList();
  },
  methods: {
    // 获取异常类型列表
    getExceptionTypeList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listExceptionType().then(response => {
          this.exceptionTypeList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询异常列表 */
    getList() {
      this.loading = true;
      listException(this.queryParams).then(response => {
        this.exceptionList = response.rows;
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
        exId: undefined,
        exCode: undefined,
        extCode: undefined,
        exName: undefined,
        exDelete: 0,
        exDesc: undefined,
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
      this.ids = selection.map(item => item.exId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加异常";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const exId = row.exId || this.ids
      getException(exId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.exId != null) {
            updateException(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addException(this.form).then(response => {
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
      const exIds = row.exId || this.ids;
      this.$modal.confirm('是否确认删除异常编号为"' + exIds + '"的数据项？').then(() => {
        this.loading = true;
        return delException(exIds);
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
      this.download('system/exception/export', {
        ...this.queryParams
      }, `exception_${new Date().getTime()}.xlsx`)
    },
    // 查看异常参数
    handleParam(row) {
      this.$router.push(`/exception/exceptionParam?exCode=${row.exCode}`)
    },
    // 查看生命周期
    handleLifecycle(row) {
      this.$router.push(`/exception/exceptionLifecycle?exCode=${row.exCode}`)
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