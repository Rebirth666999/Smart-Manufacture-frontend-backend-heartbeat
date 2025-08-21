<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="异常记录编码" prop="exrCode">
        <el-input
          v-model="queryParams.exrCode"
          placeholder="请输入异常记录编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="异常源" prop="exsCode">
        <el-input
          v-model="queryParams.exsCode"
          placeholder="请输入异常源"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="异常" prop="exCode">
        <el-input
          v-model="queryParams.exCode"
          placeholder="请输入异常"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态(数据字典)" prop="exrStat">
        <el-input
          v-model="queryParams.exrStat"
          placeholder="请输入状态(数据字典)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="等级(数据字典)" prop="exrLevel">
        <el-input
          v-model="queryParams.exrLevel"
          placeholder="请输入等级(数据字典)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上报时间">
        <el-date-picker
          v-model="daterangeExrCdate"
          style="width: 240px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['00:00:00', '23:59:59']"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="人工确认异常分类" prop="exrHumCls">
        <el-input
          v-model="queryParams.exrHumCls"
          placeholder="请输入人工确认异常分类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="已删除" prop="exrDelete">
        <el-input
          v-model="queryParams.exrDelete"
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
          v-hasPermi="['system:exceptionRecordNew:add']"
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
          v-hasPermi="['system:exceptionRecordNew:edit']"
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
          v-hasPermi="['system:exceptionRecordNew:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionRecordNew:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionRecordNewList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="异常记录ID" align="center" prop="exrId" v-if="false"/>
      <el-table-column label="异常记录编码" align="center" prop="exrCode" />
      <el-table-column label="异常源" align="center" prop="exsCode" />
      <el-table-column label="异常" align="center" prop="exCode" />
      <el-table-column label="参数(JSON形式)" align="center" prop="exrParam" />
      <el-table-column label="状态(数据字典)" align="center" prop="exrStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_exception_record_status" :value="scope.row.exrStat"/>
        </template>
      </el-table-column>
      <el-table-column label="等级(数据字典)" align="center" prop="exrLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_exception_record_level" :value="scope.row.exrLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="异常上报人" align="center" prop="exrUserReport" />
      <el-table-column label="当前处理人" align="center" prop="exrUserHandle" />
      <el-table-column label="异常解除人" align="center" prop="exrUserFinish" />
      <el-table-column label="异常责任人" align="center" prop="exrUserResp" />
      <el-table-column label="上报时间" align="center" prop="exrCdate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.exrCdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="影响因子(百分比描述)" align="center" prop="exrImpactFactor" />
      <el-table-column label="影响等级(数据字典)" align="center" prop="exrImpactLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_exception_record_impact_level" :value="scope.row.exrImpactLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="异常执行流程" align="center" prop="exrProcess" />
      <el-table-column label="持续时间(秒)" align="center" prop="exrDuration" />
      <el-table-column label="异常图片路径" align="center" prop="exrImg" />
      <el-table-column label="异常处理完成图片" align="center" prop="exrFinImg" />
      <el-table-column label="Ai确认是否是异常" align="center" prop="exrAiVer" />
      <el-table-column label="Ai确认异常分类" align="center" prop="exrAiCls" />
      <el-table-column label="人工确认异常分类" align="center" prop="exrHumCls" />
      <el-table-column label="涉及到的部门" align="center" prop="exrDep" />
      <el-table-column label="已删除" align="center" prop="exrDelete" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exceptionRecordNew:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exceptionRecordNew:remove']"
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

    <!-- 添加或修改异常记录（新）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="异常源" prop="exsCode">
          <el-input v-model="form.exsCode" placeholder="请输入异常源" />
        </el-form-item>
        <el-form-item label="异常" prop="exCode">
          <el-input v-model="form.exCode" placeholder="请输入异常" />
        </el-form-item>
        <el-form-item label="描述" prop="exrDesc">
          <el-input v-model="form.exrDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="状态(数据字典)" prop="exrStat">
          <el-input v-model="form.exrStat" placeholder="请输入状态(数据字典)" />
        </el-form-item>
        <el-form-item label="等级(数据字典)" prop="exrLevel">
          <el-input v-model="form.exrLevel" placeholder="请输入等级(数据字典)" />
        </el-form-item>
        <el-form-item label="异常上报人" prop="exrUserReport">
          <el-input v-model="form.exrUserReport" placeholder="请输入异常上报人" />
        </el-form-item>
        <el-form-item label="异常责任人" prop="exrUserResp">
          <el-input v-model="form.exrUserResp" placeholder="请输入异常责任人" />
        </el-form-item>
        <el-form-item label="影响因子(百分比描述)" prop="exrImpactFactor">
          <el-input v-model="form.exrImpactFactor" placeholder="请输入影响因子(百分比描述)" />
        </el-form-item>
        <el-form-item label="影响等级(数据字典)" prop="exrImpactLevel">
          <el-input v-model="form.exrImpactLevel" placeholder="请输入影响等级(数据字典)" />
        </el-form-item>
        <el-form-item label="异常图片路径" prop="exrImg">
          <el-input v-model="form.exrImg" placeholder="请输入异常图片路径" />
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
import { listExceptionRecordNew, getExceptionRecordNew, delExceptionRecordNew, addExceptionRecordNew, updateExceptionRecordNew } from "@/api/system/exceptionRecordNew";

export default {
  name: "ExceptionRecordNew",
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
      // 异常记录（新）表格数据
      exceptionRecordNewList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 异常图片路径时间范围
      daterangeExrCdate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        exrCode: undefined,
        exsCode: undefined,
        exCode: undefined,
        exrStat: undefined,
        exrLevel: undefined,
        exrCdate: undefined,
        exrHumCls: undefined,
        exrDelete: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        exrId: [
          { required: true, message: "异常记录ID不能为空", trigger: "blur" }
        ],
        exrCode: [
          { required: true, message: "异常记录编码不能为空", trigger: "blur" }
        ],
        exsCode: [
          { required: true, message: "异常源不能为空", trigger: "blur" }
        ],
        exCode: [
          { required: true, message: "异常不能为空", trigger: "blur" }
        ],
        exrDesc: [
          { required: true, message: "描述不能为空", trigger: "blur" }
        ],
        exrParam: [
          { required: true, message: "参数(JSON形式)不能为空", trigger: "blur" }
        ],
        exrStat: [
          { required: true, message: "状态(数据字典)不能为空", trigger: "blur" }
        ],
        exrLevel: [
          { required: true, message: "等级(数据字典)不能为空", trigger: "blur" }
        ],
        exrUserReport: [
          { required: true, message: "异常上报人不能为空", trigger: "blur" }
        ],
        exrUserResp: [
          { required: true, message: "异常责任人不能为空", trigger: "blur" }
        ],
        exrImpactFactor: [
          { required: true, message: "影响因子(百分比描述)不能为空", trigger: "blur" }
        ],
        exrImpactLevel: [
          { required: true, message: "影响等级(数据字典)不能为空", trigger: "blur" }
        ],
        exrDuration: [
          { required: true, message: "持续时间(秒)不能为空", trigger: "blur" }
        ],
        exrDelete: [
          { required: true, message: "已删除不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询异常记录（新）列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeExrCdate && '' != this.daterangeExrCdate) {
        this.queryParams.params["beginExrCdate"] = this.daterangeExrCdate[0];
        this.queryParams.params["endExrCdate"] = this.daterangeExrCdate[1];
      }
      listExceptionRecordNew(this.queryParams).then(response => {
        this.exceptionRecordNewList = response.rows;
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
        exrId: undefined,
        exrCode: undefined,
        exsCode: undefined,
        exCode: undefined,
        exrDesc: undefined,
        exrParam: undefined,
        exrStat: undefined,
        exrLevel: undefined,
        exrUserReport: undefined,
        exrUserHandle: undefined,
        exrUserFinish: undefined,
        exrUserResp: undefined,
        exrCdate: undefined,
        exrImpactFactor: undefined,
        exrImpactLevel: undefined,
        exrProcess: undefined,
        exrDuration: undefined,
        exrImg: undefined,
        exrFinImg: undefined,
        exrAiVer: undefined,
        exrAiCls: undefined,
        exrHumCls: undefined,
        exrDep: undefined,
        exrDelete: undefined,
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
      this.daterangeExrCdate = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.exrId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加异常记录（新）";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const exrId = row.exrId || this.ids
      getExceptionRecordNew(exrId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常记录（新）";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.exrId != null) {
            updateExceptionRecordNew(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionRecordNew(this.form).then(response => {
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
      const exrIds = row.exrId || this.ids;
      this.$modal.confirm('是否确认删除异常记录（新）编号为"' + exrIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionRecordNew(exrIds);
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
      this.download('system/exceptionRecordNew/export', {
        ...this.queryParams
      }, `exceptionRecordNew_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
