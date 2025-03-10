<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="目标产品" prop="prId">
        <el-select
          v-model="queryParams.prId"
          placeholder="请选择目标产品"
          clearable
        >
          <el-option
            v-for="item in productList"
            :key="item.prId"
            :label="item.prName"
            :value="item.prId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="procName">
        <el-input
          v-model="queryParams.procName"
          placeholder="请输入工艺流程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="procStat">
        <el-select v-model="queryParams.procStat" placeholder="请选择工艺流程状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_process_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="procDelete">
        <el-input
          v-model="queryParams.procDelete"
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
          v-hasPermi="['system:process:add']"
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
          v-hasPermi="['system:process:edit']"
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
          v-hasPermi="['system:process:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:process:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工艺流程ID" align="center" prop="procId" v-if="true"/>
      <el-table-column label="目标产品" align="center" prop="prId">
        <template slot-scope="scope">
          {{ productList.find(ele => ele.prId === scope.row.prId).prName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="工艺流程名称" align="center" prop="procName" />
      <el-table-column label="工艺流程状态" align="center" prop="procStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_process_status" :value="scope.row.procStat"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="procDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="procDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:process:edit']"
            v-show="scope.row.procStat === '1'"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-brush"
            v-show="scope.row.procStat === '1'"
          >设计</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            v-show="scope.row.procStat === '1'"
          >提交审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            v-show="scope.row.procStat === '2' || scope.row.procStat === '7'"
          >撤回审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-pause"
            v-show="scope.row.procStat === '5'"
          >停用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            v-show="scope.row.procStat === '4'"
          >激活</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-show="scope.row.procStat === '4'"
          >弃用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:process:remove']"
            v-show="scope.row.procStat === '1'"
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

    <!-- 添加或修改工艺流程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="目标产品" prop="prId">
          <el-select
            v-model="form.prId"
            placeholder="请选择目标产品"
          >
            <el-option
              v-for="item in productList"
              :key="item.prId"
              :label="item.prName"
              :value="item.prId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="procName">
          <el-input v-model="form.procName" placeholder="请输入工艺流程名称" />
        </el-form-item>
        <el-form-item label="描述" prop="procDesc">
          <el-input v-model="form.procDesc" type="textarea" placeholder="请输入内容" />
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
import { listProcess, getProcess, delProcess, addProcess, updateProcess } from "@/api/system/process";
import { listProduct } from "@/api/system/product";

export default {
  name: "Process",
  dicts: ['ices_process_status'],
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
      // 工艺流程表格数据
      processList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        prId: undefined,
        procName: undefined,
        procStat: undefined,
        procDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        procId: [
          { required: true, message: "工艺流程ID不能为空", trigger: "blur" }
        ],
        prId: [
          { required: true, message: "目标产品ID不能为空", trigger: "blur" }
        ],
        procName: [
          { required: true, message: "工艺流程名称不能为空", trigger: "blur" }
        ],
      },
      // 产品列表
      productList: []
    };
  },
  async created() {
    await this.getProductList();
    this.getList();
  },
  methods: {
    // 查询产品列表
    getProductList() {
      listProduct().then(response => {
        this.productList = response.rows
      })
    },
    /** 查询工艺流程列表 */
    getList() {
      this.loading = true;
      listProcess(this.queryParams).then(response => {
        this.processList = response.rows;
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
        procId: undefined,
        prId: undefined,
        procName: undefined,
        procStat: undefined,
        procDelete: undefined,
        procDesc: undefined,
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
      this.ids = selection.map(item => item.procId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工艺流程";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const procId = row.procId || this.ids
      getProcess(procId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改工艺流程";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.procId != null) {
            updateProcess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 新增状态自动转为未发布
            this.form.procStat = '1'
            addProcess(this.form).then(response => {
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
      const procIds = row.procId || this.ids;
      this.$modal.confirm('是否确认删除工艺流程编号为"' + procIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProcess(procIds);
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
      this.download('system/process/export', {
        ...this.queryParams
      }, `process_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
