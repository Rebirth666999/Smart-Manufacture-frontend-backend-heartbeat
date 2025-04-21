<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="订单产品需求" prop="odCode">
        <el-select
          v-model="queryParams.odCode"
          placeholder="请选择订单产品需求"
        >
          <el-option
            v-for="item in orderDemandList"
            :key="item.odCode"
            :label="`【${item.orName}】${item.prName}`"
            :value="item.odCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="批次编号" prop="pbBatch">
        <el-input
          v-model="queryParams.pbBatch"
          placeholder="请输入批次编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="pbDelete">
        <el-input
          v-model="queryParams.pbDelete"
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
          v-hasPermi="['system:productBatch:add']"
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
          v-hasPermi="['system:productBatch:edit']"
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
          v-hasPermi="['system:productBatch:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:productBatch:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productBatchList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品批次ID" align="center" prop="pbId" v-if="true"/>
      <el-table-column label="产品批次编码" align="center" prop="pbCode" />
      <el-table-column label="订单产品需求" align="center" prop="odCode">
        <template slot-scope="scope">
          {{ parseOdCode(scope.row.odCode) }}
        </template>
      </el-table-column>
      <el-table-column label="批次编号" align="center" prop="pbBatch" />
      <el-table-column label="起始号码" align="center" prop="pbStart" />
      <el-table-column label="终止号码" align="center" prop="pbEnd" />
      <!-- <el-table-column label="已删除" align="center" prop="pbDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:productBatch:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:productBatch:remove']"
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

    <!-- 添加或修改实际产品批次编码对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="订单产品需求" prop="odCode">
          <el-select
          v-model="form.odCode"
          placeholder="请选择订单产品需求"
        >
          <el-option
            v-for="item in orderDemandList"
            :key="item.odCode"
            :label="`【${item.orName}】${item.prName}`"
            :value="item.odCode"
          >
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="批次编号" prop="pbBatch">
          <el-input v-model="form.pbBatch" placeholder="请输入批次编号" />
        </el-form-item>
        <el-form-item label="起始号码" prop="pbStart">
          <el-input v-model="form.pbStart" placeholder="请输入起始号码" />
        </el-form-item>
        <el-form-item label="终止号码" prop="pbEnd">
          <el-input v-model="form.pbEnd" placeholder="请输入终止号码" />
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
import { listProductBatch, getProductBatch, delProductBatch, addProductBatch, updateProductBatch } from "@/api/system/productBatch";
import { listOrder } from "@/api/system/order";
import { listOrderDemand } from "@/api/system/orderDemand";
import { listProduct } from "@/api/system/product";

export default {
  name: "ProductBatch",
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
      // 实际产品批次编码表格数据
      productBatchList: [],
      //订单产品需求列表
      orderDemandList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pbCode: undefined,
        odCode: undefined,
        pbBatch: undefined,
        pbDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pbId: [
          { required: true, message: "产品批次ID不能为空", trigger: "blur" }
        ],
        odCode: [
          { required: true, message: "订单产品需求不能为空", trigger: "blur" }
        ],
        pbBatch: [
          { required: true, message: "批次编号不能为空", trigger: "blur" }
        ],
        pbStart: [
          { required: true, message: "起始号码不能为空", trigger: "blur" }
        ],
        pbEnd: [
          { required: true, message: "终止号码不能为空", trigger: "blur" }
        ],
      },
      // 订单列表
      orderList: [],
      // 订单产品需求列表
      orderDemandList: [],
      // 产品列表
      productList: [],
    };
  },
  async created() {
    await this.getOrderList();
    await this.getProductList();
    await this.getOrderDemandList();
    this.getList();
  },
  async activated() {
    await this.getOrderList();
    await this.getProductList();
    await this.getOrderDemandList();
    this.getList();
  },
  methods: {
    // 查询订单
    getOrderList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listOrder().then(response => {
          this.orderList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询产品
    getProductList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listProduct().then(response => {
          this.productList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询订单产品需求
    getOrderDemandList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listOrderDemand().then(response => {
          this.orderDemandList = []
          response.rows.forEach(demand => {
            this.orderDemandList.push({
              ...demand,
              orName: this.orderList.find(ele => ele.orCode === demand.orCode).orName,
              prName: this.productList.find(ele => ele.prCode === demand.prCode).prName
            })
          });
          console.log(this.orderDemandList)
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询实际产品批次编码列表 */
    getList() {
      this.loading = true;
      listProductBatch(this.queryParams).then(response => {
        this.productBatchList = response.rows;
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
        pbId: undefined,
        pbCode: undefined,
        odCode: undefined,
        pbBatch: undefined,
        pbStart: undefined,
        pbEnd: undefined,
        pbDelete: undefined,
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
      this.ids = selection.map(item => item.pbId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加实际产品批次编码";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const pbId = row.pbId || this.ids
      getProductBatch(pbId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改实际产品批次编码";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.pbId != null) {
            updateProductBatch(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addProductBatch(this.form).then(response => {
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
      const pbIds = row.pbId || this.ids;
      this.$modal.confirm('是否确认删除实际产品批次编码编号为"' + pbIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProductBatch(pbIds);
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
      this.download('system/productBatch/export', {
        ...this.queryParams
      }, `productBatch_${new Date().getTime()}.xlsx`)
    },
    // 解析odCode为显示格式
    parseOdCode(odCode) {
      const demand = this.orderDemandList.find(ele => ele.odCode === odCode)
      if (demand) {
        return `【${demand.orName}】${demand.prName}`
      } else return ''
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
