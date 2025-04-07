<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="异常源编码" prop="exsCode">
        <el-input
          v-model="queryParams.exsCode"
          placeholder="请输入异常源编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="exsName">
        <el-input
          v-model="queryParams.exsName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="exsDelete">
        <el-input
          v-model="queryParams.exsDelete"
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
          v-hasPermi="['system:exceptionSource:add']"
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
          v-hasPermi="['system:exceptionSource:edit']"
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
          v-hasPermi="['system:exceptionSource:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionSource:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionSourceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="异常源ID" align="center" prop="exsId" v-if="true"/>
      <el-table-column label="异常源编码" align="center" prop="exsCode" />
      <el-table-column label="名称" align="center" prop="exsName" />
      <el-table-column label="认证token" align="center" prop="exsToken" />
      <el-table-column label="巡检接口" align="center" prop="exsInspectionUrl" />
      <el-table-column label="处理接口" align="center" prop="exsHandleUrl" />
      <el-table-column label="状态检查接口" align="center" prop="exsStateUrl" />
      <el-table-column label="预警接口" align="center" prop="exsNotifyUrl" />
      <!-- <el-table-column label="已删除" align="center" prop="exsDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="exsDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exceptionSource:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exceptionSource:remove']"
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

    <!-- 添加或修改异常源对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="exsName">
          <el-input v-model="form.exsName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="认证token" prop="exsToken">
          <el-input v-model="form.exsToken" placeholder="请输入认证token" />
        </el-form-item>
        <el-form-item label="巡检接口" prop="exsInspectionUrl">
          <el-input v-model="form.exsInspectionUrl" placeholder="请输入巡检接口" />
        </el-form-item>
        <el-form-item label="处理接口" prop="exsHandleUrl">
          <el-input v-model="form.exsHandleUrl" placeholder="请输入处理接口" />
        </el-form-item>
        <el-form-item label="状态检查接口" prop="exsStateUrl">
          <el-input v-model="form.exsStateUrl" placeholder="请输入状态检查接口" />
        </el-form-item>
        <el-form-item label="预警接口" prop="exsNotifyUrl">
          <el-input v-model="form.exsNotifyUrl" placeholder="请输入预警接口" />
        </el-form-item>
        <el-form-item label="描述" prop="exsDesc">
          <el-input v-model="form.exsDesc" type="textarea" placeholder="请输入内容" />
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
import { listExceptionSource, getExceptionSource, delExceptionSource, addExceptionSource, updateExceptionSource } from "@/api/system/exceptionSource";

export default {
  name: "ExceptionSource",
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
      // 异常源表格数据
      exceptionSourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        exsCode: undefined,
        exsName: undefined,
        exsDelete: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        exsId: [
          { required: true, message: "异常源ID不能为空", trigger: "blur" }
        ],
        exsName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        exsToken: [
          { required: true, message: "认证token不能为空", trigger: "blur" }
        ],
        exsInspectionUrl: [
          { required: true, message: "巡检接口不能为空", trigger: "blur" }
        ],
        exsHandleUrl: [
          { required: true, message: "处理接口不能为空", trigger: "blur" }
        ],
        exsStateUrl: [
          { required: true, message: "状态检查接口不能为空", trigger: "blur" }
        ],
        exsNotifyUrl: [
          { required: true, message: "预警接口不能为空", trigger: "blur" }
        ],
        exsDelete: [
          { required: true, message: "已删除不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询异常源列表 */
    getList() {
      this.loading = true;
      listExceptionSource(this.queryParams).then(response => {
        this.exceptionSourceList = response.rows;
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
        exsId: undefined,
        exsCode: undefined,
        exsName: undefined,
        exsToken: undefined,
        exsInspectionUrl: undefined,
        exsHandleUrl: undefined,
        exsStateUrl: undefined,
        exsNotifyUrl: undefined,
        exsDelete: 0,
        exsDesc: undefined,
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
      this.ids = selection.map(item => item.exsId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加异常源";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const exsId = row.exsId || this.ids
      getExceptionSource(exsId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常源";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.exsId != null) {
            updateExceptionSource(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionSource(this.form).then(response => {
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
      const exsIds = row.exsId || this.ids;
      this.$modal.confirm('是否确认删除异常源编号为"' + exsIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionSource(exsIds);
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
      this.download('system/exceptionSource/export', {
        ...this.queryParams
      }, `exceptionSource_${new Date().getTime()}.xlsx`)
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