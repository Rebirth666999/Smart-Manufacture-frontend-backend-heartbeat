<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所需产品" prop="maCode">
        <el-select
          v-model="queryParams.maCode"
          placeholder="请选择产品"
          clearable
        >
          <el-option
            v-for="item in productList"
            :key="item.maCode"
            :label="item.maName"
            :value="item.maCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="客户" prop="clCode">
        <el-input
          v-model="queryParams.clCode"
          placeholder="请输入客户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单名称" prop="orName">
        <el-input
          v-model="queryParams.orName"
          placeholder="请输入订单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="orStat">
        <el-select v-model="queryParams.orStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="优先级" prop="orPriority">
        <el-input
          v-model="queryParams.orPriority"
          placeholder="请输入订单优先级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="截止时间">
        <el-date-picker
          v-model="daterangeOrDeadline"
          style="width: 240px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['00:00:00', '23:59:59']"
        ></el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="orDelete">
        <el-input
          v-model="queryParams.orDelete"
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
          v-hasPermi="['system:order:add']"
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
          v-hasPermi="['system:order:edit']"
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
          v-hasPermi="['system:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单ID" align="center" prop="orId" v-if="true"/>
      <el-table-column label="所需产品" align="center" prop="maCode">
        <template slot-scope="scope">
          {{ productList.find(ele => ele.maCode === scope.row.maCode).maName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="客户" align="center" prop="clCode" />
      <el-table-column label="订单名称" align="center" prop="orName" />
      <el-table-column label="状态代码" align="center" prop="orStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_order_status" :value="scope.row.orStat"/>
        </template>
      </el-table-column>
      <el-table-column label="所需产品数量" align="center" prop="orDemand" />
      <el-table-column label="订单优先级" align="center" prop="orPriority" />
      <el-table-column label="截止时间" align="center" prop="orDeadline" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orDeadline, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="总价" align="center" prop="orPrice" />
      <!-- <el-table-column label="已删除" align="center" prop="orDelete" /> -->
      <el-table-column label="描述" align="center" prop="orDesc" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            v-if="scope.row.orStat==='1'"
            @click="handleSubmitReview(scope.row)"
          >提交审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            v-show="scope.row.orStat === '2' || scope.row.orStat === 'b'"
            @click="handleWithdrawReview(scope.row)"
          >撤回审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-show="scope.row.orStat === '4'"
            @click="handleDepreciateReview(scope.row)"
          >弃用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:order:remove']"
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

    <!-- 添加或修改订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="530px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="所需产品" prop="maCode">
          <el-select
            v-model="form.maCode"
            placeholder="请选择产品"
          >
            <el-option
              v-for="item in productList"
              :key="item.maCode"
              :label="item.maName"
              :value="item.maCode"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户" prop="clCode">
          <el-input v-model="form.clCode" placeholder="请输入客户" />
        </el-form-item>
        <el-form-item label="订单名称" prop="orName">
          <el-input v-model="form.orName" placeholder="请输入订单名称" />
        </el-form-item>
        <el-form-item label="所需产品数量" prop="orDemand">
          <el-input v-model="form.orDemand" placeholder="请输入所需产品数量" />
        </el-form-item>
        <el-form-item label="订单优先级" prop="orPriority">
          <el-input v-model="form.orPriority" placeholder="请输入订单优先级" />
        </el-form-item>
        <el-form-item label="截止时间" prop="orDeadline">
          <el-date-picker clearable
            v-model="form.orDeadline"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="总价" prop="orPrice">
          <el-input v-model="form.orPrice" placeholder="请输入总价" />
        </el-form-item>
        <el-form-item label="描述" prop="orDesc">
          <el-input v-model="form.orDesc" type="textarea" placeholder="请输入内容" />
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/system/order";
import { listMaterial } from "@/api/system/material";

export default {
  name: "Order",
  dicts: ['ices_order_status'],
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
      // 订单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 描述时间范围
      daterangeOrDeadline: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        maCode: undefined,
        clCode: undefined,
        orName: undefined,
        orStat: undefined,
        orPriority: undefined,
        orDeadline: undefined,
        orDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orId: [
          { required: true, message: "订单ID不能为空", trigger: "blur" }
        ],
        maCode: [
          { required: true, message: "所需产品不能为空", trigger: "blur" }
        ],
        orName: [
          { required: true, message: "订单名称不能为空", trigger: "blur" }
        ],
        orDemand: [
          { required: true, message: "所需产品数量不能为空", trigger: "blur" }
        ],
        orPriority: [
          { required: true, message: "订单优先级不能为空", trigger: "blur" }
        ],
        orDeadline: [
          { required: true, message: "截止时间不能为空", trigger: "blur" }
        ],
        orPrice: [
          { required: true, message: "总价不能为空", trigger: "blur" }
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
      listMaterial({ maType: '2' }).then(response => {
        this.productList = response.rows
      })
    },
    /** 查询订单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeOrDeadline && '' != this.daterangeOrDeadline) {
        this.queryParams.params["beginOrDeadline"] = this.daterangeOrDeadline[0];
        this.queryParams.params["endOrDeadline"] = this.daterangeOrDeadline[1];
      }
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
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
        orId: undefined,
        maCode: undefined,
        clCode: undefined,
        orName: undefined,
        orStat: undefined,
        orDemand: undefined,
        orPriority: undefined,
        orDeadline: undefined,
        orPrice: undefined,
        orDelete: undefined,
        orDesc: undefined,
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
      this.daterangeOrDeadline = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const orId = row.orId || this.ids
      getOrder(orId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.orId != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 新增订单的默认状态
            this.form.orStat = '1'
            addOrder(this.form).then(response => {
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
      const orIds = row.orId || this.ids;
      this.$modal.confirm('是否确认删除订单编号为"' + orIds + '"的数据项？').then(() => {
        this.loading = true;
        return delOrder(orIds);
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
      this.download('system/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    },
    // 提交审核
    handleSubmitReview(row) {
      const orId = row.orId;
      this.$modal.confirm('是否要提交审核？审核在开始之前可以撤回。').then(() => {
        this.loading = true;
        getOrder(orId).then(response => {
          this.form = response.data;
          this.form.orStat = "2";
          updateOrder(this.form).then(response => {
            this.$modal.msgSuccess("已提交审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 撤回审核操作
    handleWithdrawReview(row) {
      const orId = row.orId;
      this.$modal.confirm('是否要撤回审核？若审核已开始即无法撤回。').then(() => {
        this.loading = true;
        getOrder(orId).then(response => {
          this.form = response.data;
          if (this.form.orStat === '2') this.form.orStat = '1'
          else if (this.form.orStat === 'b') this.form.orStat = '4'
          updateOrder(this.form).then(response => {
            this.$modal.msgSuccess("已撤回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 弃用订单
    handleDepreciateReview(row) {
      const orId = row.orId;
      this.$modal.confirm('是否弃用此订单？弃用订单需要进行订单弃用审核。').then(() => {
        this.loading = true;
        getOrder(orId).then(response => {
          this.form = response.data;
          this.form.orStat = "b";
          updateOrder(this.form).then(response => {
            this.$modal.msgSuccess("已提交订单弃用审核");
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
<style scoped>
.el-select {
  width: 100%;
}
.el-date-editor{
  width: 100%;
}
</style>