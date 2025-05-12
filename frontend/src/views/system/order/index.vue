<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>订单信息</div>
        </div>
      </div>
      <div>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="客户" prop="clCode">
            <el-select
              v-model="queryParams.clCode"
              placeholder="请选择客户"
              clearable
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
    
        <el-table
          v-loading="loading"
          :data="orderList"
          @current-change="handleCurrentChange"
          highlight-current-row
        >
          <el-table-column label="选择" width="55" align="center">
            <template slot-scope="scope">
              <el-radio :value="scope.row.orId === idSelect" :label="true" />
            </template>
          </el-table-column>
          <el-table-column label="订单ID" align="center" prop="orId" v-if="true"/>
          <el-table-column label="订单编码" align="center" prop="orCode" />
          <el-table-column label="原订单编码" align="center" prop="orCodeOrgn" />
          <el-table-column label="客户" align="center" prop="clCode">
            <template slot-scope="scope">
              {{ clientList.find(ele => ele.clCode === scope.row.clCode).clName || '' }}
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="orStat">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.ices_order_status" :value="scope.row.orStat"/>
            </template>
          </el-table-column>
          <el-table-column label="订单优先级" align="center" prop="orPriority" />
          <el-table-column label="截止时间" align="center" prop="orDeadline" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.orDeadline, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="总价" align="center" prop="orPrice" />
          <el-table-column label="创建人" align="center" prop="orCman" />
          <el-table-column label="创建时间" align="center" prop="orCdate" />
          <el-table-column label="修改人" align="center" prop="orMman" />
          <el-table-column label="修改时间" align="center" prop="orMdate" />
          <!-- <el-table-column label="已删除" align="center" prop="orDelete" /> -->
          <!-- <el-table-column label="描述" align="center" prop="orDesc" /> -->
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:order:edit']"
                v-show="scope.row.orStat==='1' || scope.row.orStat==='4' || scope.row.orStat==='d' || scope.row.orStat==='5'"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-finished"
                v-show="scope.row.orStat==='1'"
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
                v-show="scope.row.orStat==='1'"
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
      </div>
    </el-card>
    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>订单产品信息</div>
        </div>
      </div>
      <order-demand v-if='idSelect' :key="idSelect" :orCode="codeSelect" @update="getList"/>
      <el-empty v-else description="选中订单后即可管理订单产品" />
    </el-card>
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/system/order";
import { listProduct } from "@/api/system/product";
import { listClient } from "@/api/system/client";
import orderDemand from '@/views/system/orderDemand';

export default {
  name: "ManageOrder",
  components: {
    orderDemand
  },
  dicts: ['ices_order_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中内容
      idSelect: undefined,
      // 选中code
      codeSelect: undefined,
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
      // 用于编辑属性的表单
      form: {},
      // 产品列表
      productList: [],
      // 客户列表
      clientList: []
    };
  },
  async created() {
    await this.getProductList();
    await this.getClientList();
    await this.getList();
    if (this.$route.query.orCode) {
      const order = this.orderList.find(ele => ele.orCode === this.$route.query.orCode)
      this.$router.replace('/order/manageOrder')
      if (order) {
        this.idSelect = order.orId
        this.codeSelect = order.orCode
      }
    }
  },
  async activated() {
    await this.getProductList();
    await this.getClientList();
    await this.getList();
    if (this.$route.query.orCode) {
      const order = this.orderList.find(ele => ele.orCode === this.$route.query.orCode)
      this.$router.replace('/order/manageOrder')
      if (order) {
        this.idSelect = order.orId
        this.codeSelect = order.orCode
      }
    }
  },
  methods: {
    // 查询客户列表
    getClientList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listClient().then(response => {
          this.clientList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询产品列表
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
    /** 查询订单列表 */
    getList() {
      return new Promise((resolve, reject) => {
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
          resolve()
        }).catch(() => {
          reject()
        })
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.idSelect = undefined
      this.codeSelect = undefined
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeOrDeadline = [];
      this.resetForm("queryForm");
      this.idSelect = undefined
      this.codeSelect = undefined
      this.handleQuery();
    },
    // 选中数据条目
    handleCurrentChange(current, old) {
      if (current) {
        this.idSelect = current.orId
        this.codeSelect = current.orCode
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push(`/order/addOrder`)
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const orId = row.orId || this.idSelect
      this.$router.push(`/order/editOrder?orId=${orId}`)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const orIds = row.orId || this.idSelect;
      this.$modal.confirm('是否确认删除订单编号为"' + orIds + '"的数据项？').then(() => {
        this.loading = true;
        return delOrder(orIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.idSelect = undefined
        this.codeSelect = undefined
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
    },
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
::v-deep .el-radio span.el-radio__label {
  display: none;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 17px;
}
.controlled-card {
  margin-top: 10px;
}
</style>