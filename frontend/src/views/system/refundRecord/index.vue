<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单" prop="orCode">
        <el-select
          v-model="queryParams.orCode"
          placeholder="请选择订单"
        >
          <el-option
            v-for="item in orderList"
            :key="item.orCode"
            :label="item.orName"
            :value="item.orCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="产品" prop="prCode">
        <el-select
          v-model="queryParams.prCode"
          placeholder="请选择产品"
        >
          <el-option
            v-for="item in productList"
            :key="item.prCode"
            :label="item.prName"
            :value="item.prCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="客户" prop="clCode">
        <el-select
          v-model="queryParams.clCode"
          placeholder="请选择客户"
        >
          <el-option
            v-for="item in clientList"
            :key="item.clCode"
            :label="item.clName"
            :value="item.clCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="rrDelete">
        <el-input
          v-model="queryParams.rrDelete"
          placeholder="请输入已删除"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="办理人" prop="rrMan">
        <el-input
          v-model="queryParams.rrMan"
          placeholder="请输入办理人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="办理日期" prop="rrDate">
        <el-input
          v-model="queryParams.rrDate"
          placeholder="请输入办理日期"
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
          v-hasPermi="['system:refundRecord:add']"
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
          v-hasPermi="['system:refundRecord:edit']"
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
          v-hasPermi="['system:refundRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:refundRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="refundRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="退货记录ID" align="center" prop="rrId" v-if="true"/>
      <el-table-column label="退货记录编码" align="center" prop="rrCode" />
      <el-table-column label="订单" align="center" prop="orCode" />
      <el-table-column label="产品" align="center" prop="prCode" />
      <el-table-column label="客户" align="center" prop="clCode" />
      <el-table-column label="数量" align="center" prop="rrCount" />
      <!-- <el-table-column label="已删除" align="center" prop="rrDelete" />
      <el-table-column label="描述" align="center" prop="rrDesc" /> -->
      <el-table-column label="办理人" align="center" prop="rrMan" />
      <el-table-column label="办理日期" align="center" prop="rrDate" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:refundRecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:refundRecord:remove']"
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

    <!-- 添加或修改退货记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单" prop="orCode">
          <el-select
          v-model="form.orCode"
          placeholder="请选择订单"
        >
          <el-option
            v-for="item in orderList"
            :key="item.orCode"
            :label="item.orName"
            :value="item.orCode"
          >
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="产品" prop="prCode">
          <el-select
          v-model="queryParams.prCode"
          placeholder="请选择产品"
        >
          <el-option
            v-for="item in productList"
            :key="item.prCode"
            :label="item.prName"
            :value="item.prCode"
          >
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="客户" prop="clCode">
          <el-select
          v-model="queryParams.clCode"
          placeholder="请选择客户"
        >
          <el-option
            v-for="item in clientList"
            :key="item.clCode"
            :label="item.clName"
            :value="item.clCode"
          >
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="rrCount">
          <el-input v-model="form.rrCount" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="描述" prop="rrDesc">
          <el-input v-model="form.rrDesc" type="textarea" placeholder="请输入内容" />
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
import { listRefundRecord, getRefundRecord, delRefundRecord, addRefundRecord, updateRefundRecord } from "@/api/system/refundRecord";
import { listOrder } from "@/api/system/order";
import { listProduct } from "@/api/system/product";
import { listClient } from "@/api/system/client";


export default {
  name: "RefundRecord",
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
      // 退货记录表格数据
      refundRecordList: [],
      //订单列表
      orderList: [],
      //客户列表
      clientList: [],
      //产品列表
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rrCode: undefined,
        orCode: undefined,
        prCode: undefined,
        clCode: undefined,
        rrDelete: undefined,
        rrMan: undefined,
        rrDate: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rrId: [
          { required: true, message: "退货记录ID不能为空", trigger: "blur" }
        ],
        orCode: [
          { required: true, message: "订单不能为空", trigger: "blur" }
        ],
        prCode: [
          { required: true, message: "产品不能为空", trigger: "blur" }
        ],
        clCode: [
          { required: true, message: "客户不能为空", trigger: "blur" }
        ],
        rrCount: [
          { required: true, message: "数量不能为空", trigger: "blur" }
        ]
      }
    };
  },
  async created() {
    this.getList();
    await this.getOrderList();
    await this.getProductList();
    await this.getClientList();
  },
  methods: {
    //查询订单列表
    getOrderList() {
      listOrder().then(response => {
        this.orderList = response.rows;
      })
    },
    //查询产品列表
    getProductList() {
      listProduct().then(response => {
        this.productList = response.rows;
      })
    },
    //查询客户列表
    getClientList() {
      listClient().then(response => {
        this.clientList = response.rows;
      })
    },
    /** 查询退货记录列表 */
    getList() {
      this.loading = true;
      listRefundRecord(this.queryParams).then(response => {
        this.refundRecordList = response.rows;
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
        rrId: undefined,
        rrCode: undefined,
        orCode: undefined,
        prCode: undefined,
        clCode: undefined,
        rrCount: undefined,
        rrDelete: 0,
        rrDesc: undefined,
        createBy: undefined,
        updateBy: undefined,
        createTime: undefined,
        updateTime: undefined,
        rrMan: undefined,
        rrDate: undefined
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
      this.ids = selection.map(item => item.rrId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加退货记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const rrId = row.rrId || this.ids
      getRefundRecord(rrId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改退货记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.rrId != null) {
            updateRefundRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addRefundRecord(this.form).then(response => {
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
      const rrIds = row.rrId || this.ids;
      this.$modal.confirm('是否确认删除退货记录编号为"' + rrIds + '"的数据项？').then(() => {
        this.loading = true;
        return delRefundRecord(rrIds);
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
      this.download('system/refundRecord/export', {
        ...this.queryParams
      }, `refundRecord_${new Date().getTime()}.xlsx`)
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