<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属订单" prop="orId">
        <el-select
          v-model="queryParams.orId"
          placeholder="请选择订单"
          clearable
        >
          <el-option
            v-for="item in orderList"
            :key="item.orId"
            :label="item.orName"
            :value="item.orId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="工艺流程" prop="procId">
        <el-select
          v-model="queryParams.procId"
          placeholder="请选择工艺流程"
          clearable
        >
          <el-option
            v-for="item in processListFull"
            :key="item.procId"
            :label="item.procName"
            :value="item.procId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="mpStat">
        <el-select v-model="queryParams.mpStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_manufacture_plan_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="优先级" prop="mpPriority">
        <el-input
          v-model="queryParams.mpPriority"
          placeholder="请输入优先级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="mpDelete">
        <el-input
          v-model="queryParams.mpDelete"
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
          v-hasPermi="['system:manufacturePlan:add']"
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
          v-hasPermi="['system:manufacturePlan:edit']"
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
          v-hasPermi="['system:manufacturePlan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:manufacturePlan:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="manufacturePlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="生产计划ID" align="center" prop="mpId" v-if="true"/>
      <el-table-column label="所属订单" align="center" prop="orId">
        <template slot-scope="scope">
          {{ orderList.find(ele => ele.orId === scope.row.orId).orName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="工艺流程" align="center" prop="procId">
        <template slot-scope="scope">
          {{ processListFull.find(ele => ele.procId === scope.row.procId).procName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="mpStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_manufacture_plan_status" :value="scope.row.mpStat"/>
        </template>
      </el-table-column>
      <el-table-column label="实际开始时间" align="center" prop="mpBegin" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mpBegin, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最晚结束时间" align="center" prop="mpEndPlan" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mpEndPlan, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际结束时间" align="center" prop="mpEndReal" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mpEndReal, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="mpPriority" />
      <el-table-column label="计划产品数量" align="center" prop="mpQtyPlan" />
      <el-table-column label="已完成产品数量" align="center" prop="mpQtyReal" />
      <!-- <el-table-column label="已删除" align="center" prop="mpDelete" /> -->
      <el-table-column label="描述" align="center" prop="mpDesc" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:manufacturePlan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            v-if="scope.row.mpStat==='1'"
            @click="handleSubmitReview(scope.row)"          
          >提交审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            v-show="scope.row.mpStat === '2'"
            @click="handleWithdrawReview(scope.row)"
          >撤回审核</el-button>          
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:manufacturePlan:remove']"
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

    <!-- 添加或修改生产计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="540px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="所属订单" prop="orId">
          <el-select
            v-model="form.orId"
            placeholder="请选择订单"
            clearable
          >
            <el-option
              v-for="item in orderList"
              :key="item.orId"
              :label="item.orName"
              :value="item.orId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工艺流程" prop="procId">
          <el-select
            v-model="form.procId"
            placeholder="请选择工艺流程"
            clearable
          >
            <el-option
              v-for="item in processList"
              :key="item.procId"
              :label="item.procName"
              :value="item.procId"
            >
            </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="最晚结束时间" prop="mpEndPlan">
          <el-date-picker clearable
            v-model="form.mpEndPlan"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择最晚结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="优先级" prop="mpPriority">
          <el-input v-model="form.mpPriority" placeholder="请输入优先级" />
        </el-form-item>
        <el-form-item label="计划产品数量" prop="mpQtyPlan">
          <el-input v-model="form.mpQtyPlan" placeholder="请输入计划产品数量" />
        </el-form-item>
        <el-form-item label="描述" prop="mpDesc">
          <el-input v-model="form.mpDesc" type="textarea" placeholder="请输入内容" />
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
import { listManufacturePlan, getManufacturePlan, delManufacturePlan, addManufacturePlan, updateManufacturePlan } from "@/api/system/manufacturePlan";
import { listProcess } from "@/api/system/process";
import { listOrder } from "@/api/system/order";

export default {
  name: "ManufacturePlan",
  dicts: ['ices_manufacture_plan_status'],
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
      // 生产计划表格数据
      manufacturePlanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orId: undefined,
        procId: undefined,
        mpStat: undefined,
        mpPriority: undefined,
        mpDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mpId: [
          { required: true, message: "生产计划不能为空", trigger: "blur" }
        ],
        orId: [
          { required: true, message: "所属订单不能为空", trigger: "blur" }
        ],
        procId: [
          { required: true, message: "工艺流程不能为空", trigger: "blur" }
        ],
        mpEndPlan: [
          { required: true, message: "最晚结束时间不能为空", trigger: "blur" }
        ],
        mpPriority: [
          { required: true, message: "优先级不能为空", trigger: "blur" }
        ],
        mpQtyPlan: [
          { required: true, message: "产品数量不能为空", trigger: "blur" }
        ],
      },
      // 已发布的所有工艺流程
      processList: [],
      // 所有工艺流程
      processListFull: [],
      // 订单列表
      orderList: []
    };
  },
  async created() {
    await this.getProcessList();
    await this.getOrderList();
    this.getList();
  },
  methods: {
    // 查询工艺流程列表
    getProcessList() {
      listProcess().then(response => {
        this.processListFull = response.rows;
        for (let item of response.rows) {
          if (item.procStat === '5') this.processList.push(item)
        }
      });
    },
    // 查询产品列表
    getOrderList() {
      listOrder().then(response => {
        this.orderList = response.rows;
      });
    },
    /** 查询生产计划列表 */
    getList() {
      this.loading = true;
      listManufacturePlan(this.queryParams).then(response => {
        this.manufacturePlanList = response.rows;
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
        mpId: undefined,
        orId: undefined,
        procId: undefined,
        mpStat: undefined,
        mpBegin: undefined,
        mpEndPlan: undefined,
        mpEndReal: undefined,
        mpPriority: undefined,
        mpQtyPlan: undefined,
        mpQtyReal: undefined,
        mpDelete: undefined,
        mpDesc: undefined,
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
      this.ids = selection.map(item => item.mpId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加生产计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const mpId = row.mpId || this.ids
      getManufacturePlan(mpId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改生产计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.mpId != null) {
            updateManufacturePlan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 新增生产计划分配默认状态
            this.form.mpStat = '1'
            addManufacturePlan(this.form).then(response => {
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
      const mpIds = row.mpId || this.ids;
      this.$modal.confirm('是否确认删除生产计划编号为"' + mpIds + '"的数据项？').then(() => {
        this.loading = true;
        return delManufacturePlan(mpIds);
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
      this.download('system/manufacturePlan/export', {
        ...this.queryParams
      }, `manufacturePlan_${new Date().getTime()}.xlsx`)
    },
    // 提交审核
    handleSubmitReview(row) {
      const mpId = row.mpId;
      this.$modal.confirm('是否要提交审核？审核在开始之前可以撤回。').then(() => {
        this.loading = true;
        getManufacturePlan(mpId).then(response => {
          this.form = response.data;
          this.form.mpStat = "2";
          updateManufacturePlan(this.form).then(response => {
            this.$modal.msgSuccess("已提交审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 撤回审核
    handleWithdrawReview(row) {
      const mpId = row.mpId;
      this.$modal.confirm('是否要撤回审核？若审核已开始即无法撤回。').then(() => {
        this.loading = true;
        getManufacturePlan(mpId).then(response => {
          this.form = response.data;
          this.form.mpStat = "1";
          updateManufacturePlan(this.form).then(response => {
            this.$modal.msgSuccess("已撤回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    }
  }
};
</script>
