<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="变动类型" prop="mrType">
        <el-select v-model="queryParams.mrType" placeholder="请选择变动类型" clearable>
          <el-option
            v-for="dict in dict.type.ices_record_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="mrDelete">
        <el-input
          v-model="queryParams.mrDelete"
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
          v-hasPermi="['system:materialRecord:add']"
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
          v-hasPermi="['system:materialRecord:edit']"
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
          v-hasPermi="['system:materialRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:materialRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="原料台账ID" align="center" prop="mrId" v-if="true"/>
      <el-table-column label="所属原料ID" align="center" prop="maId" />
      <el-table-column label="所属车间ID" align="center" prop="arId" />
      <el-table-column label="变动类型" align="center" prop="mrType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_record_type" :value="scope.row.mrType"/>
        </template>
      </el-table-column>
      <el-table-column label="预计变动值" align="center" prop="mrEst" />
      <el-table-column label="实际变动值" align="center" prop="mrReal" />
      <!-- <el-table-column label="已删除" align="center" prop="mrDelete" /> -->
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:materialRecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:materialRecord:remove']"
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

    <!-- 添加或修改原料台账对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="变动类型" prop="mrType">
          <el-select v-model="form.mrType" placeholder="请选择变动类型">
            <el-option
              v-for="dict in dict.type.ices_record_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预计变动值" prop="mrEst">
          <el-input v-model="form.mrEst" placeholder="请输入预计变动值" />
        </el-form-item>
        <el-form-item label="实际变动值" prop="mrReal">
          <el-input v-model="form.mrReal" placeholder="请输入实际变动值" />
        </el-form-item>
        <el-form-item label="描述" prop="mrDesc">
          <el-input v-model="form.mrDesc" type="textarea" placeholder="请输入内容" />
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
import { listMaterialRecord, getMaterialRecord, delMaterialRecord, addMaterialRecord, updateMaterialRecord } from "@/api/system/materialRecord";

export default {
  name: "MaterialRecord",
  dicts: ['ices_record_type'],
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
      // 原料台账表格数据
      materialRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        maId: undefined,
        arId: undefined,
        mrType: undefined,
        mrDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mrId: [
          { required: true, message: "原料台账ID不能为空", trigger: "blur" }
        ],
        maId: [
          { required: true, message: "所属原料ID不能为空", trigger: "change" }
        ],
        arId: [
          { required: true, message: "所属车间ID不能为空", trigger: "change" }
        ],
        mrType: [
          { required: true, message: "变动类型不能为空", trigger: "change" }
        ],
        mrEst: [
          { required: true, message: "预计变动值不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询原料台账列表 */
    getList() {
      this.loading = true;
      listMaterialRecord(this.queryParams).then(response => {
        this.materialRecordList = response.rows;
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
        mrId: undefined,
        maId: undefined,
        arId: undefined,
        mrType: undefined,
        mrEst: undefined,
        mrReal: undefined,
        mrDelete: undefined,
        mrDesc: undefined,
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
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.mrId)
      this.single = selection.length!==1
      this.multiple = !selection.length
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
      const mrId = row.mrId || this.ids
      getMaterialRecord(mrId).then(response => {
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
          if (this.form.mrId != null) {
            updateMaterialRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addMaterialRecord(this.form).then(response => {
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
      const mrIds = row.mrId || this.ids;
      this.$modal.confirm('是否确认删除原料台账编号为"' + mrIds + '"的数据项？').then(() => {
        this.loading = true;
        return delMaterialRecord(mrIds);
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
      this.download('system/materialRecord/export', {
        ...this.queryParams
      }, `materialRecord_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
