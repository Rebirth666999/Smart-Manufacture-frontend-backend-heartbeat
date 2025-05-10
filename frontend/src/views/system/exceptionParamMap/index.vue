<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="110px">
      <el-form-item label="异常参数模板" prop="exptCode">
        <el-select v-model="queryParams.exptCode" placeholder="请选择异常参数模板" :disabled="mode === 1" clearable>
          <el-option
           v-for="option in exceptionParamTemplateList"
           :key="option.exptCode"
           :label="option.exptCode"
           :value="option.exptCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="源字段名" prop="expmNameOrgn">
        <el-input
          v-model="queryParams.expmNameOrgn"
          placeholder="请输入源字段名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标字段名" prop="expName">
        <el-input
          v-model="queryParams.expName"
          placeholder="请输入目标字段名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="expmDelete">
        <el-input
          v-model="queryParams.expmDelete"
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
          v-hasPermi="['system:exceptionParamMap:add']"
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
          v-hasPermi="['system:exceptionParamMap:edit']"
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
          v-hasPermi="['system:exceptionParamMap:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionParamMap:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionParamMapList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="模板条目ID" align="center" prop="expmId" v-if="true"/>
      <el-table-column label="模板条目编码" align="center" prop="expmCode" />
      <el-table-column label="异常参数模板" align="center" prop="exptCode" />
      <el-table-column label="源字段名" align="center" prop="expmNameOrgn" />
      <el-table-column label="源字段编码" align="center" prop="expmCodeOrgn" />
      <el-table-column label="目标字段名" align="center" prop="expName" />
      <el-table-column label="目标字段" align="center" prop="expCode">
        <template slot-scope="scope">
          {{ exceptionParamList.find(ele => ele.expCode === scope.row.expCode).expName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="默认值" align="center" prop="expmDefault" />
      <el-table-column label="转换公式" align="center" prop="expmFormula" />
      <!-- <el-table-column label="已删除" align="center" prop="expmDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="expmDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exceptionParamMap:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exceptionParamMap:remove']"
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

    <!-- 添加或修改异常参数模板条目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="异常参数模板" prop="exptCode">
          <el-select v-model="form.exptCode" placeholder="请选择异常参数模板" :disabled="mode === 1">
            <el-option
             v-for="option in exceptionParamTemplateList"
             :key="option.exptCode"
             :label="option.exptCode"
             :value="option.exptCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="源字段名" prop="expmNameOrgn">
          <el-input v-model="form.expmNameOrgn" placeholder="请输入源字段名" />
        </el-form-item>
        <el-form-item label="源字段编码" prop="expmCodeOrgn">
          <el-input v-model="form.expmCodeOrgn" placeholder="请输入源字段编码" />
        </el-form-item>
        <el-form-item label="目标字段名" prop="expName">
          <el-input v-model="form.expName" placeholder="请输入目标字段名" />
        </el-form-item>
        <el-form-item label="目标字段" prop="expCode">
          <el-select v-model="form.expCode" placeholder="请选择目标字段">
            <el-option
             v-for="option in exceptionParamList"
             :key="option.expCode"
             :label="option.expName"
             :value="option.expCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="默认值" prop="expmDefault">
          <el-input v-model="form.expmDefault" placeholder="请输入默认值" />
        </el-form-item>
        <el-form-item label="转换公式" prop="expmFormula">
          <el-input v-model="form.expmFormula" placeholder="请输入转换公式" />
        </el-form-item>
        <el-form-item label="描述" prop="expmDesc">
          <el-input v-model="form.expmDesc" type="textarea" placeholder="请输入内容" />
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
import { listExceptionParamMap, getExceptionParamMap, delExceptionParamMap, addExceptionParamMap, updateExceptionParamMap } from "@/api/system/exceptionParamMap";
import { listExceptionParamTemplate } from "@/api/system/exceptionParamTemplate";
import { listExceptionParam } from "@/api/system/exceptionParam";

export default {
  name: "ExceptionParamMap",
  props: {
    exptCode: {
      required: false
    }
  },
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
      // 异常参数模板条目表格数据
      exceptionParamMapList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        expmCode: undefined,
        exptCode: undefined,
        expmNameOrgn: undefined,
        expName: undefined,
        expmDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        expmId: [
          { required: true, message: "模板条目ID不能为空", trigger: "blur" }
        ],
        exptCode: [
          { required: true, message: "异常参数模板不能为空", trigger: "blur" }
        ],
        expmNameOrgn: [
          { required: true, message: "源字段名不能为空", trigger: "blur" }
        ],
        expmCodeOrgn: [
          { required: true, message: "源字段编码不能为空", trigger: "blur" }
        ],
        expName: [
          { required: true, message: "目标字段名不能为空", trigger: "blur" }
        ],
        expCode: [
          { required: true, message: "目标字段编码不能为空", trigger: "blur" }
        ],
      },
      // 异常参数模板列表
      exceptionParamTemplateList: [],
      // 异常参数列表
      exceptionParamList: [],
      // 1-按模板查看
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    if (this.exptCode) {
      this.mode = 1
    }
    await this.getExceptionParamTemplateList();
    await this.getExceptionParamList();
    this.getList();
  },
  async activated() {
    if (this.exptCode) {
      this.mode = 1
    } else {
      this.mode = 0
    }
    await this.getExceptionParamTemplateList();
    await this.getExceptionParamList();
    this.getList();
  },
  methods: {
    // 获取异常参数列表
    getExceptionParamList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listExceptionParam().then(response => {
          this.exceptionParamList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 获取异常参数模板列表
    getExceptionParamTemplateList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listExceptionParamTemplate().then(response => {
          this.exceptionParamTemplateList = response.rows
          if (this.mode === 1) {
            let expt = response.rows.find(ele => ele.exptCode === this.exptCode)
            // 构造提示文本
            this.hint = "异常参数模板 "
            this.hint += expt.exptCode
            this.hint += " "
            // 设置筛选
            this.queryParams.exptCode = expt.exptCode
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询异常参数模板条目列表 */
    getList() {
      this.loading = true;
      listExceptionParamMap(this.queryParams).then(response => {
        this.exceptionParamMapList = response.rows;
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
        expmId: undefined,
        expmCode: undefined,
        exptCode: undefined,
        expmNameOrgn: undefined,
        expmCodeOrgn: undefined,
        expName: undefined,
        expCode: undefined,
        expmDefault: undefined,
        expmFormula: undefined,
        expmDelete: undefined,
        expmDesc: undefined,
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
      this.queryParams.exptCode = this.exptCode
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.expmId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.mode === 1) {
        this.form.exptCode = this.exptCode
      }
      this.open = true;
      this.title = "添加异常参数模板条目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const expmId = row.expmId || this.ids
      getExceptionParamMap(expmId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常参数模板条目";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.expmId != null) {
            updateExceptionParamMap(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionParamMap(this.form).then(response => {
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
      const expmIds = row.expmId || this.ids;
      this.$modal.confirm('是否确认删除异常参数模板条目编号为"' + expmIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionParamMap(expmIds);
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
      this.download('system/exceptionParamMap/export', {
        ...this.queryParams
      }, `exceptionParamMap_${new Date().getTime()}.xlsx`)
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