<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="预警记录编码" prop="exwrCode">
        <el-input
          v-model="queryParams.exwrCode"
          placeholder="请输入预警记录编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预警配置" prop="exwCode">
        <el-input
          v-model="queryParams.exwCode"
          placeholder="请输入预警配置"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否产生预警" prop="exwrWarning">
        <el-select v-model="queryParams.exwrWarning" placeholder="请选择是否产生预警" clearable>
          <el-option
            v-for="dict in dict.type.ices_yn"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="已删除" prop="exwrDelete">
        <el-input
          v-model="queryParams.exwrDelete"
          placeholder="请输入已删除"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
          v-hasPermi="['system:exceptionWarningRecord:add']"
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
          v-hasPermi="['system:exceptionWarningRecord:edit']"
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
          v-hasPermi="['system:exceptionWarningRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionWarningRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionWarningRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="预警记录ID" align="center" prop="exwrId" v-if="true"/>
      <el-table-column label="预警记录编码" align="center" prop="exwrCode" />
      <el-table-column label="预警配置" align="center" prop="exwCode" />
      <el-table-column label="检查时间" align="center" prop="exwrTime" />
      <el-table-column label="检查结果" align="center" prop="exwrResult" />
      <el-table-column label="是否产生预警" align="center" prop="exwrWarning">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_yn" :value="scope.row.exwrWarning"/>
        </template>
      </el-table-column>
      <el-table-column label="已删除" align="center" prop="exwrDelete" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exceptionWarningRecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exceptionWarningRecord:remove']"
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

    <!-- 添加或修改异常预警记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="预警配置" prop="exwCode">
          <el-input v-model="form.exwCode" placeholder="请输入预警配置" />
        </el-form-item>
        <el-form-item label="检查时间" prop="exwrTime">
          <el-input v-model="form.exwrTime" placeholder="请输入检查时间" />
        </el-form-item>
        <el-form-item label="检查结果" prop="exwrResult">
          <el-input v-model="form.exwrResult" placeholder="请输入检查结果" />
        </el-form-item>
        <el-form-item label="是否产生预警" prop="exwrWarning">
          <el-select v-model="form.exwrWarning" placeholder="请选择是否产生预警">
            <el-option
              v-for="dict in dict.type.ices_yn"
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
import { listExceptionWarningRecord, getExceptionWarningRecord, delExceptionWarningRecord, addExceptionWarningRecord, updateExceptionWarningRecord } from "@/api/system/exceptionWarningRecord";

export default {
  name: "ExceptionWarningRecord",
  dicts: ['ices_yn'],
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
      // 异常预警记录表格数据
      exceptionWarningRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        exwrCode: undefined,
        exwCode: undefined,
        exwrWarning: undefined,
        exwrDelete: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        exwrId: [
          { required: true, message: "预警记录ID不能为空", trigger: "blur" }
        ],
        exwCode: [
          { required: true, message: "预警配置不能为空", trigger: "blur" }
        ],
        exwrTime: [
          { required: true, message: "检查时间不能为空", trigger: "blur" }
        ],
        exwrResult: [
          { required: true, message: "检查结果不能为空", trigger: "blur" }
        ],
        exwrWarning: [
          { required: true, message: "是否产生预警不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询异常预警记录列表 */
    getList() {
      this.loading = true;
      listExceptionWarningRecord(this.queryParams).then(response => {
        this.exceptionWarningRecordList = response.rows;
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
        exwrId: undefined,
        exwrCode: undefined,
        exwCode: undefined,
        exwrTime: undefined,
        exwrResult: undefined,
        exwrWarning: undefined,
        exwrDelete: undefined,
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
      this.ids = selection.map(item => item.exwrId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加异常预警记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const exwrId = row.exwrId || this.ids
      getExceptionWarningRecord(exwrId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常预警记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.exwrId != null) {
            updateExceptionWarningRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionWarningRecord(this.form).then(response => {
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
      const exwrIds = row.exwrId || this.ids;
      this.$modal.confirm('是否确认删除异常预警记录编号为"' + exwrIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionWarningRecord(exwrIds);
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
      this.download('system/exceptionWarningRecord/export', {
        ...this.queryParams
      }, `exceptionWarningRecord_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
