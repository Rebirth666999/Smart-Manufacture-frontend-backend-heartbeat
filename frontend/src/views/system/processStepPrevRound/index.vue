<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="已删除" prop="psprDelete">
        <el-input
          v-model="queryParams.psprDelete"
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
          v-hasPermi="['system:processStepPrevRound:add']"
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
          v-hasPermi="['system:processStepPrevRound:edit']"
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
          v-hasPermi="['system:processStepPrevRound:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:processStepPrevRound:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processStepPrevRoundList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="跨轮次前序步骤关联ID" align="center" prop="psprId" v-if="true"/>
      <el-table-column label="当前工艺步骤ID" align="center" prop="psIdCur" />
      <el-table-column label="跨轮次前序工艺步骤ID" align="center" prop="psIdPrev" />
      <el-table-column label="已删除" align="center" prop="psprDelete" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:processStepPrevRound:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:processStepPrevRound:remove']"
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

    <!-- 添加或修改关联-工艺步骤的跨轮次前序步骤对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="当前工艺步骤ID" prop="psIdCur">
          <el-input v-model="form.psIdCur" placeholder="请输入当前工艺步骤ID" />
        </el-form-item>
        <el-form-item label="跨轮次前序工艺步骤ID" prop="psIdPrev">
          <el-input v-model="form.psIdPrev" placeholder="请输入跨轮次前序工艺步骤ID" />
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
import { listProcessStepPrevRound, getProcessStepPrevRound, delProcessStepPrevRound, addProcessStepPrevRound, updateProcessStepPrevRound } from "@/api/system/processStepPrevRound";

export default {
  name: "ProcessStepPrevRound",
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
      // 关联-工艺步骤的跨轮次前序步骤表格数据
      processStepPrevRoundList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        psprDelete: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        psprId: [
          { required: true, message: "跨轮次前序步骤关联ID不能为空", trigger: "blur" }
        ],
        psIdCur: [
          { required: true, message: "当前工艺步骤ID不能为空", trigger: "blur" }
        ],
        psIdPrev: [
          { required: true, message: "跨轮次前序工艺步骤ID不能为空", trigger: "blur" }
        ],
        psprDelete: [
          { required: true, message: "已删除不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询关联-工艺步骤的跨轮次前序步骤列表 */
    getList() {
      this.loading = true;
      listProcessStepPrevRound(this.queryParams).then(response => {
        this.processStepPrevRoundList = response.rows;
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
        psprId: undefined,
        psIdCur: undefined,
        psIdPrev: undefined,
        psprDelete: undefined,
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
      this.ids = selection.map(item => item.psprId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加关联-工艺步骤的跨轮次前序步骤";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const psprId = row.psprId || this.ids
      getProcessStepPrevRound(psprId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改关联-工艺步骤的跨轮次前序步骤";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.psprId != null) {
            updateProcessStepPrevRound(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addProcessStepPrevRound(this.form).then(response => {
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
      const psprIds = row.psprId || this.ids;
      this.$modal.confirm('是否确认删除关联-工艺步骤的跨轮次前序步骤编号为"' + psprIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProcessStepPrevRound(psprIds);
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
      this.download('system/processStepPrevRound/export', {
        ...this.queryParams
      }, `processStepPrevRound_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
