<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="异常" prop="exCode">
        <el-select v-model="queryParams.exCode" placeholder="请选择异常" clearable>
          <el-option
           v-for="option in exceptionList"
           :key="option.exCode"
           :label="option.exName"
           :value="option.exCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="异常源" prop="exsCode">
        <el-select v-model="queryParams.exsCode" placeholder="请选择异常源" clearable>
          <el-option
           v-for="option in exceptionSourceList"
           :key="option.exsCode"
           :label="option.exsName"
           :value="option.exsCode">
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="exptDelete">
        <el-input
          v-model="queryParams.exptDelete"
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
          v-hasPermi="['system:exceptionParamTemplate:add']"
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
          v-hasPermi="['system:exceptionParamTemplate:edit']"
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
          v-hasPermi="['system:exceptionParamTemplate:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionParamTemplate:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionParamTemplateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="参数模板ID" align="center" prop="exptId" v-if="true"/>
      <el-table-column label="参数模板编码" align="center" prop="exptCode" />
      <el-table-column label="异常" align="center" prop="exCode">
        <template slot-scope="scope">
          {{ exceptionList.find(ele => ele.exCode === scope.row.exCode).exName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="异常源" align="center" prop="exsCode">
        <template slot-scope="scope">
          {{ exceptionSourceList.find(ele => ele.exsCode === scope.row.exsCode).exsName || '' }}
        </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="exptDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="exptDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exceptionParamTemplate:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleMap(scope.row)"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exceptionParamTemplate:remove']"
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

    <!-- 添加或修改异常参数模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="异常" prop="exCode">
          <el-select v-model="form.exCode" placeholder="请选择异常" clearable>
            <el-option
             v-for="option in exceptionList"
             :key="option.exCode"
             :label="option.exName"
             :value="option.exCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="异常源" prop="exsCode">
          <el-select v-model="form.exsCode" placeholder="请选择异常源" clearable>
            <el-option
             v-for="option in exceptionSourceList"
             :key="option.exsCode"
             :label="option.exsName"
             :value="option.exsCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="exptDesc">
          <el-input v-model="form.exptDesc" type="textarea" placeholder="请输入内容" />
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
import { listExceptionParamTemplate, getExceptionParamTemplate, delExceptionParamTemplate, addExceptionParamTemplate, updateExceptionParamTemplate } from "@/api/system/exceptionParamTemplate";
import { listException } from "@/api/system/exception";
import { listExceptionSource } from "@/api/system/exceptionSource";

export default {
  name: "ExceptionParamTemplate",
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
      // 异常参数模板表格数据
      exceptionParamTemplateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        exptCode: undefined,
        exCode: undefined,
        exsCode: undefined,
        exptDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        exptId: [
          { required: true, message: "参数模板ID不能为空", trigger: "blur" }
        ],
        exCode: [
          { required: true, message: "异常不能为空", trigger: "blur" }
        ],
        exsCode: [
          { required: true, message: "异常源不能为空", trigger: "blur" }
        ],
      },
      // 异常列表
      exceptionList: [],
      // 异常源列表
      exceptionSourceList: []
    };
  },
  async created() {
    await this.getExceptionList();
    await this.getExceptionSourceList();
    this.getList();
  },
  async activated() {
    await this.getExceptionList();
    await this.getExceptionSourceList();
    this.getList();
  },
  methods: {
    // 获取异常源列表
    getExceptionSourceList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listExceptionSource().then(response => {
          this.exceptionSourceList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
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
    /** 查询异常参数模板列表 */
    getList() {
      this.loading = true;
      listExceptionParamTemplate(this.queryParams).then(response => {
        this.exceptionParamTemplateList = response.rows;
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
        exptId: undefined,
        exptCode: undefined,
        exCode: undefined,
        exsCode: undefined,
        exptDelete: undefined,
        exptDesc: undefined,
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
      this.ids = selection.map(item => item.exptId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加异常参数模板";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const exptId = row.exptId || this.ids
      getExceptionParamTemplate(exptId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常参数模板";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.exptId != null) {
            updateExceptionParamTemplate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionParamTemplate(this.form).then(response => {
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
      const exptIds = row.exptId || this.ids;
      this.$modal.confirm('是否确认删除异常参数模板编号为"' + exptIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionParamTemplate(exptIds);
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
      this.download('system/exceptionParamTemplate/export', {
        ...this.queryParams
      }, `exceptionParamTemplate_${new Date().getTime()}.xlsx`)
    },
    // 查看模板条目
    handleMap(row) {
      this.$router.push(`/exception/exceptionParamMap?exptCode=${row.exptCode}`)
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