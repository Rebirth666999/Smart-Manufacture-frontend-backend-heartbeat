<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>生产计划信息</div>
        </div>
      </div>
      <div>
        <el-form :model="queryParams" ref="queryOrderForm" size="small" :inline="true"  label-width="68px">
          <el-form-item label="订单" prop="orCode">
            <el-button size="mini" type="primary" @click="openSelectOrder">
              {{ currentOrCode || '选择订单' }}
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-refresh" size="mini" @click="resetOrderQuery">重置</el-button>
          </el-form-item>
        </el-form>
  
        <el-table
          v-loading="loading"
          :data="manufacturePlanMainList"
          @current-change="handleCurrentChange"
          highlight-current-row
        >
          <el-table-column label="选择" width="55" align="center">
            <template slot-scope="scope">
              <el-radio :value="scope.row.mpmId === idSelect" :label="true" />
            </template>
          </el-table-column>
          <el-table-column label="计划ID" align="center" prop="mpmId" v-if="true"/>
          <el-table-column label="计划编码" align="center" prop="mpmCode" />
          <el-table-column label="订单" align="center" prop="orCode" />
          <el-table-column label="状态" align="center" prop="mpmStat">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.ices_manufacture_plan_status" :value="scope.row.mpmStat"/>
            </template>
          </el-table-column>
          <el-table-column label="实际开始时间" align="center" prop="mpmBegin">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpmBegin, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="最晚结束时间" align="center" prop="mpmEndPlan">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpmEndPlan, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="实际结束时间" align="center" prop="mpmEndReal">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpmEndReal, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="创建人" align="center" prop="mpmCman" />
          <el-table-column label="创建时间" align="center" prop="mpmCdate" />
          <el-table-column label="修改人" align="center" prop="mpmMman" />
          <el-table-column label="修改时间" align="center" prop="mpmMdate" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                v-show="scope.row.mpmStat === '2' || scope.row.mpmStat === '7' || scope.row.mpmStat=== 'a'"
                @click="startReview(scope.row)"
              >开始审核</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-check"
                v-show="scope.row.mpmStat === '3' || scope.row.mpmStat === '8' || scope.row.mpmStat=== 'b'"
                @click="passReview(scope.row)"
              >通过审核</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-close"
                v-show="scope.row.mpmStat === '3' || scope.row.mpmStat === '8' || scope.row.mpmStat=== 'b'"
                @click="rejectReview(scope.row)"
              >驳回审核</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>

    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>生产计划详细信息</div>
        </div>
      </div>
      <div v-if="idSelect">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-if="showSearch" label-width="68px">
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

        <el-table
          v-loading="loading"
          :data="manufacturePlanList"
        >
          <el-table-column label="计划详情ID" align="center" prop="mpId" v-if="true"/>
          <el-table-column label="计划详情编码" align="center" prop="mpCode" />
          <el-table-column label="所属生产计划" align="center" prop="mpmCode" />
          <el-table-column label="产品" align="center" prop="odCode">
            <template slot-scope="scope">
              {{ parseOdCode(scope.row.odCode) }}
            </template>
          </el-table-column>
          <el-table-column label="实际开始时间" align="center" prop="mpBegin">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpBegin, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="最晚结束时间" align="center" prop="mpEndPlan">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpEndPlan, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="实际结束时间" align="center" prop="mpEndReal">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpEndReal, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="优先级" align="center" prop="mpPriority" />
          <el-table-column label="计划产品数量" align="center" prop="mpQtyPlan" />
          <el-table-column label="已完成产品数量" align="center" prop="mpQtyReal" />
          <!-- <el-table-column label="已删除" align="center" prop="mpDelete" /> -->
          <!-- <el-table-column label="描述" align="center" prop="mpDesc" /> -->
          <el-table-column label="创建人" align="center" prop="mpCman" />
          <el-table-column label="创建时间" align="center" prop="mpCdate" />
          <el-table-column label="下发人" align="center" prop="mpRman" />
          <el-table-column label="下发时间" align="center" prop="mpRdate" />
          <el-table-column label="修改人" align="center" prop="mpMman" />
          <el-table-column label="修改时间" align="center" prop="mpMdate" />
        </el-table>

        <pagination
          v-if="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
      <el-empty v-else description="暂无生产计划详细信息" />
    </el-card>

    <!-- 选择订单对话框 -->
    <el-dialog title="选择订单" :visible.sync="open" width="550px" append-to-body>
      <el-form label-width="110px">
        <el-form-item label="订单编号" prop="orCode">
          <el-select
            v-model="queryParams.orCode"
            placeholder="请选择订单"
            filterable
            @change="selectOrder"
          >
            <el-option
              v-for="item in orderList"
              :key="item.orCode"
              :label="item.orCode"
              :value="item.orCode"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <el-descriptions border :column="1">
        <el-descriptions-item label="订单ID">
          {{ currentOrder ? currentOrder.orId : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="订单编码">
          {{ currentOrder ? currentOrder.orCode : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="订单优先级">
          {{ currentOrder ? currentOrder.orPriority : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="截止时间">
          {{ currentOrder ? parseTime(currentOrder.orDeadline, '{y}-{m}-{d}') : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="总价">
          {{ currentOrder ? currentOrder.orPrice : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="创建人">
          {{ currentOrder ? currentOrder.orCman : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ currentOrder ? currentOrder.orCdate : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="修改人">
          {{ currentOrder ? currentOrder.orMman : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="修改时间">
          {{ currentOrder ? currentOrder.orMdate : '' }}
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitOrder">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listManufacturePlan, getManufacturePlan, delManufacturePlan, addManufacturePlan, updateManufacturePlan } from "@/api/system/manufacturePlan";
import { listManufacturePlanMain, listManufacturePlanMainReview, getManufacturePlanMain, delManufacturePlanMain, addManufacturePlanMain, updateManufacturePlanMain } from "@/api/system/manufacturePlanMain";
import { listOrder } from "@/api/system/order";
import { listProduct } from "@/api/system/product";
import { listOrderDemand } from "@/api/system/orderDemand";
import manufactureTask from '@/views/system/manufactureTask';
import ManufactureMaterial from '@/views/system/manufactureTask/material';

export default {
  name: "ManufacturePlan",
  components: {
    manufactureTask,
    ManufactureMaterial
  },
  dicts: ['ices_manufacture_plan_status'],
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
      // 生产计划表格数据
      manufacturePlanList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orCode: undefined,
        procCode: undefined,
        mpStat: undefined,
        mpPriority: undefined,
        mpDelete: 0,
      },
      // 用于编辑属性的表单
      form: {},
      // 选择订单窗口是否打开
      open: false,
      // 用于显示的当前选择订单
      currentOrCode: '',
      // 当前选择订单信息
      currentOrder: undefined,
      // 订单列表
      orderList: [],
      // 产品列表
      productList: [],
      // 产品需求列表
      orderDemandList: [],
      // 生产计划主表
      manufacturePlanMainList: [],
      // 生产计划详情编辑相关
      manufacturePlan: {
        // 窗口标题
        title: '',
        // 窗口是否打开
        open: false,
        // 按钮加载中
        buttonLoading: false,
        // 表单内容
        form: {},
        // 校验规则
        rules: {
          mpId: [
            { required: true, message: "生产计划不能为空", trigger: "blur" }
          ],
          orCode: [
            { required: true, message: "所属订单不能为空", trigger: "blur" }
          ],
          mpmCode: [
            { required: true, message: "所属生产计划不能为空", trigger: "blur" }
          ],
          odCode: [
            { required: true, message: "产品不能为空", trigger: "blur" }
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
      },
      // 0-生产计划未发布可修改
      // 1-生产计划不可修改
      mode: 0
    };
  },
  watch: {
    $route(route) {
      if (route.path === '/manufacture/manufacturePlan') {
        this.setUpPage()
      }
    }
  },
  async created() {
    await this.setUpPage()
  },
  async activated() {
    await this.setUpPage()
  },
  methods: {
    /** 
     * 页面初始化
     * @author YangZY
     * @date 20250427
     */
    async setUpPage() {
      await this.getProductList();
      await this.getOrderList();
      await this.getOrderDemandList();
      await this.getList();
      await this.handleQuery();
    },
    // 查询订单产品需求
    getOrderDemandList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listOrderDemand().then(response => {
          this.orderDemandList = response.rows
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
    // 查询订单列表
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
    // 查询生产计划主表列表
    getMainList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listManufacturePlanMainReview({ orCode: this.queryParams.orCode }).then(response => {
          this.manufacturePlanMainList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询生产计划列表 */
    getList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        const id = this.idSelect
        this.idSelect = undefined
        listManufacturePlan(this.queryParams).then(response => {
          this.manufacturePlanList = response.rows;
          this.total = response.total;
          this.idSelect = id
          this.loading = false;
          resolve()
        }).catch(() => {
          reject()
        })
      })
    },
    /** 搜索按钮操作 */
    async handleQuery() {
      this.queryParams.pageNum = 1;
      this.idSelect = undefined
      this.codeSelect = undefined
      if (this.queryParams.orCode) {
        await this.getList();
        await this.getMainList();
        if (this.manufacturePlanMainList.length > 0) {
          const stat = this.manufacturePlanMainList[0].mpmStat
          this.mode = stat === '1'? 0 : 1
        }
      } else {
        await this.getList();
        await this.getMainList();
      }
    },
    /** 
     * 计划详细信息重置按钮
     * 只能重置订单以外的筛选项
     * @author YangZY
     * @date 20250423
     */
    resetQuery() {
      this.resetForm("queryForm");
      this.idSelect = undefined
      this.codeSelect = undefined
      this.handleQuery();
    },
    /** 
     * 计划信息重置按钮
     * 只能重置订单筛选项
     * @author YangZY
     * @date 20250423
     */
    resetOrderQuery() {
      this.resetForm("queryOrderForm");
      this.currentOrCode = ''
      this.idSelect = undefined
      this.codeSelect = undefined
      this.handleQuery();
    },
    // 选中数据条目
    handleCurrentChange(current, old) {
      if (current) {
        this.idSelect = current.mpmId
        this.codeSelect = current.mpmCode
        this.queryParams.mpmCode = current.mpmCode
        this.getList()
      }
    },
    // 开始审核
    startReview(row) {
      const mpmId = row.mpmId;
      this.$modal.confirm('是否要开始审核？').then(() => {
        this.loading = true;
        getManufacturePlanMain(mpmId).then(response => {
          const form = response.data;
          if (form.mpmStat === "2") form.mpmStat = "3"
          else if (form.mpmStat === "7") form.mpmStat = "8"
          else if (form.mpmStat === "a") form.mpmStat = "b"
          updateManufacturePlanMain(form).then(response => {
            this.$modal.msgSuccess("已开始审核");
            this.handleQuery();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 通过审核
    passReview(row) {
      const mpmId = row.mpmId;
      this.$modal.confirm('是否要通过审核？').then(() => {
        this.loading = true;
        getManufacturePlanMain(mpmId).then(response => {
          const form = response.data;
          if (form.mpmStat === "3") form.mpmStat = "4"
          else if (form.mpmStat === "8") form.mpmStat = "4"
          else if (form.mpmStat === "b") form.mpmStat = "9"
          updateManufacturePlanMain(form).then(response => {
            this.$modal.msgSuccess("已通过审核");
            this.handleQuery();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 驳回审核
    rejectReview(row) {
      const mpmId = row.mpmId;
      this.$modal.confirm('是否要驳回审核？').then(() => {
        this.loading = true;
        getManufacturePlanMain(mpmId).then(response => {
          const form = response.data;
          if (form.mpmStat === "3") form.mpmStat = "1"
          else if (form.mpmStat === "8") form.mpmStat = "4"
          else if (form.mpmStat === "b") form.mpmStat = "4"
          updateManufacturePlanMain(form).then(response => {
            this.$modal.msgSuccess("已驳回审核");
            this.handleQuery();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /**
     * 解析产品需求字段
     * @author YangZY
     * @date 20250423
     */
    parseOdCode(odCode) {
      const demand = this.orderDemandList.find(ele => ele.odCode === odCode)
      if (demand) {
        return this.productList.find(ele => ele.prCode === demand.prCode).prName || ''
      }
      return ''
    },
    /**
     * 打开选择订单窗口
     * @author YangZY
     * @date 20250426
     */
    openSelectOrder() {
      this.open = true
      if (this.currentOrCode) {
        this.selectOrder(this.currentOrCode)
      } else {
        this.currentOrder = undefined
      }
    },
    /**
     * 生产计划选择订单
     * @author YangZY
     * @date 20250426
     */
    selectOrder(orCode) {
      this.currentOrder = this.orderList.find(ele => ele.orCode === orCode)
    },
    /**
     * 关闭选择订单窗口
     * @author YangZY
     * @date 20250426
     */
    cancel() {
      this.queryParams.orCode = this.currentOrCode
      this.open = false
    },
    /**
     * 确认选择订单
     * @author YangZY
     * @date 20250426
     */
    submitOrder() {
      this.currentOrCode = this.queryParams.orCode
      this.handleQuery()
      this.open = false
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
::v-deep .el-tabs__item {
  font-size: 17px;
}
</style>
