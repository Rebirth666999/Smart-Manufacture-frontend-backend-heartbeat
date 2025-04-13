<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选异常生命周期版本`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="110px">
      <el-form-item label="所属生命周期" prop="exlCode">
        <el-select v-model="queryParams.exlCode" placeholder="请选择异常生命周期" :disabled="mode === 1" clearable>
          <el-option
           v-for="option in exceptionLifecycleList"
           :key="option.exlCode"
           :label="option.exlCode"
           :value="option.exlCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="版本名称" prop="exlvName">
        <el-input
          v-model="queryParams.exlvName"
          placeholder="请输入版本名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="exlvDelete">
        <el-input
          v-model="queryParams.exlvDelete"
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
          v-hasPermi="['system:exceptionLifecycleVersion:add']"
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
          v-hasPermi="['system:exceptionLifecycleVersion:edit']"
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
          v-hasPermi="['system:exceptionLifecycleVersion:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionLifecycleVersion:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionLifecycleVersionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="生命周期版本ID" align="center" prop="exlvId" v-if="true"/>
      <el-table-column label="生命周期版本编码" align="center" prop="exlvCode" />
      <el-table-column label="所属生命周期" align="center" prop="exlCode" />
      <el-table-column label="版本名称" align="center" prop="exlvName" />
      <el-table-column label="模型定义ID" align="center" prop="exlvDefId" />
      <el-table-column label="模型文件ID" align="center" prop="exlvGeId" />
      <!-- <el-table-column label="已删除" align="center" prop="exlvDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exceptionLifecycleVersion:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exceptionLifecycleVersion:remove']"
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

    <!-- 添加或修改异常生命周期版本对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="所属生命周期" prop="exlCode">
          <el-select v-model="form.exlCode" placeholder="请选择异常生命周期" :disabled="mode === 1">
            <el-option
             v-for="option in exceptionLifecycleList"
             :key="option.exlCode"
             :label="option.exlCode"
             :value="option.exlCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="版本名称" prop="exlvName">
          <el-input v-model="form.exlvName" placeholder="请输入版本名称" />
        </el-form-item>
        <!-- <el-form-item label="模型定义ID" prop="exlvDefId">
          <el-input v-model="form.exlvDefId" placeholder="请输入模型定义ID" />
        </el-form-item>
        <el-form-item label="模型文件ID" prop="exlvGeId">
          <el-input v-model="form.exlvGeId" placeholder="请输入模型文件ID" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listExceptionLifecycleVersion, getExceptionLifecycleVersion, delExceptionLifecycleVersion, addExceptionLifecycleVersion, updateExceptionLifecycleVersion } from "@/api/system/exceptionLifecycleVersion";
import { listExceptionLifecycle } from "@/api/system/exceptionLifecycle";

export default {
  name: "ExceptionLifecycleVersion",
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
      // 异常生命周期版本表格数据
      exceptionLifecycleVersionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        exlvCode: undefined,
        exlCode: undefined,
        exlvName: undefined,
        exlvDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        exlvId: [
          { required: true, message: "生命周期版本ID不能为空", trigger: "blur" }
        ],
        exlvCode: [
          { required: true, message: "生命周期版本编码不能为空", trigger: "blur" }
        ],
        exlCode: [
          { required: true, message: "所属生命周期不能为空", trigger: "blur" }
        ],
        exlvName: [
          { required: true, message: "版本名称不能为空", trigger: "blur" }
        ],
      },
      // 异常生命周期列表
      exceptionLifecycleList: [],
      // 1-按生命周期筛选版本
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    if (this.$route.query.exlCode) {
      this.mode = 1
    }
    await this.getExceptionLifecycleList();
    this.getList();
  },
  async activated() {
    if (this.$route.query.exlCode) {
      this.mode = 1
    } else {
      this.mode = 0
    }
    await this.getExceptionLifecycleList();
    this.getList();
  },
  methods: {
    // 获取异常生命周期列表
    getExceptionLifecycleList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listExceptionLifecycle().then(response => {
          this.exceptionLifecycleList = response.rows
          if (this.mode === 1) {
            let exl = response.rows.find(ele => ele.exlCode === this.$route.query.exlCode)
            // 构造提示文本
            this.hint = "异常生命周期 "
            this.hint += exl.exlCode
            this.hint += " "
            // 设置筛选
            this.queryParams.exlCode = exl.exlCode
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询异常生命周期版本列表 */
    getList() {
      this.loading = true;
      listExceptionLifecycleVersion(this.queryParams).then(response => {
        this.exceptionLifecycleVersionList = response.rows;
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
        exlvId: undefined,
        exlvCode: undefined,
        exlCode: undefined,
        exlvName: undefined,
        exlvDefId: undefined,
        exlvGeId: undefined,
        exlvDelete: undefined,
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
      this.queryParams.exlCode = this.$route.query.exlCode
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.exlvId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.mode === 1) {
        this.form.exlCode = this.$route.query.exlCode
      }
      this.open = true;
      this.title = "添加异常生命周期版本";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const exlvId = row.exlvId || this.ids
      getExceptionLifecycleVersion(exlvId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常生命周期版本";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.exlvId != null) {
            updateExceptionLifecycleVersion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionLifecycleVersion(this.form).then(response => {
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
      const exlvIds = row.exlvId || this.ids;
      this.$modal.confirm('是否确认删除异常生命周期版本编号为"' + exlvIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionLifecycleVersion(exlvIds);
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
      this.download('system/exceptionLifecycleVersion/export', {
        ...this.queryParams
      }, `exceptionLifecycleVersion_${new Date().getTime()}.xlsx`)
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
