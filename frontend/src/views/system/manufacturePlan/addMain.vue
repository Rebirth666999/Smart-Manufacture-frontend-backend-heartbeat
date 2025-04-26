<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>生产计划信息</div>
          <div>
            <el-button
              :loading="buttonLoading"
              type="primary"
              @click="submitForm"
            >保 存</el-button>
            <el-button
              :loading="buttonLoading"
              @click="resetPage"
            >重 置</el-button>
          </div>
        </div>
      </div>
      <div>
        <el-form :model="form" :rules="rules" ref="form" label-width="110px">
          <el-col :span="12">
            <el-form-item label="订单" prop="orCode">
              <el-button plain @click="openSelectOrder" class="order-button" :disabled="form.mpmId !== undefined">
                {{ currentOrCode || '请选择订单' }}
              </el-button>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="mpmCode">
              <span slot="label">
                <el-tooltip placement="top">
                  <div slot="content">
                    <div>生产计划编码须以"ManufacturePlanMain-"开头</div>
                    <div>在其后输入五位数字，高位补0</div>
                    <div>若留空则系统自动生成</div>
                    <div>保存后编码不可更改</div>
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
                编码
              </span>
              <el-input v-model="form.mpmCode" placeholder="请输入生产计划编码"  :disabled="form.mpmId !== undefined" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实际开始时间" prop="mpmBegin">
              <el-date-picker clearable
                v-model="form.mpmBegin"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择实际开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最晚结束时间" prop="mpmEndPlan">
              <el-date-picker clearable
                v-model="form.mpmEndPlan"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择最晚结束时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-form>
      </div>
    </el-card>

    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>生产计划详细信息 <el-tag v-if="preview">预览中</el-tag></div>
        </div>
      </div>
      <div v-if="manufacturePlanList.length > 0">
        <el-row :gutter="10" class="mb8" v-if="!preview">
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
        </el-row>

        <el-table
          v-loading="loading"
          :data="manufacturePlanList"
          @current-change="handleCurrentChange"
          highlight-current-row
        >
          <el-table-column label="选择" width="55" align="center" v-if="!preview">
            <template slot-scope="scope">
              <el-radio :value="scope.row.mpId === idSelect" :label="true" />
            </template>
          </el-table-column>
          <el-table-column label="计划详情ID" align="center" prop="mpId" v-if="true"/>
          <el-table-column label="计划详情编码" align="center" prop="mpCode" />
          <el-table-column label="所属生产计划" align="center" prop="mpmCode" />
          <el-table-column label="产品" align="center" prop="odCode">
            <template slot-scope="scope">
              {{ parseOdCode(scope.row.odCode) }}
            </template>
          </el-table-column>
          <!-- <el-table-column label="状态" align="center" prop="mpStat">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.ices_manufacture_plan_status" :value="scope.row.mpStat"/>
            </template>
          </el-table-column> -->
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
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="!preview">
            <template slot-scope="scope">
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-show="scope.row.mpStat==='1' || scope.row.mpStat==='4' || scope.row.mpStat==='5'"
                v-hasPermi="['system:manufacturePlan:edit']"
              >修改</el-button> -->
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-finished"
                v-show="scope.row.mpStat==='1'"
                @click="handleSubmitReview(scope.row)"
              >提交审核</el-button> -->
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-refresh-left"
                v-show="scope.row.mpStat === '2'"
                @click="handleWithdrawReview(scope.row)"
              >撤回审核</el-button> -->
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                v-show="scope.row.mpStat==='4' || scope.row.mpStat==='5'"
                @click="handleDeprecated(scope.row)"
              >弃用</el-button> -->
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:manufacturePlan:remove']"
                v-show="scope.row.mpStat === '1'"
              >删除</el-button> -->

              
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
      </div>
      <el-empty v-else description="暂无生产计划详细信息" />
    </el-card>

    <el-card shadow="never" class="controlled-card">
      <el-tabs value="task">
        <el-tab-pane label="生产任务" name="task">
          <manufacture-task v-if='idSelect' :key="idSelect" :mpCode="codeSelect" @update="getList" />
          <el-empty v-else description="选中生产计划后即可管理下属生产任务" />
        </el-tab-pane>
        <el-tab-pane label="生产材料" name="material">
          <manufacture-material v-if='idSelect' :key="idSelect" :mpCode="codeSelect" />
          <el-empty v-else description="选中生产计划后即可管理生产材料" />
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <!-- 选择订单对话框 -->
    <el-dialog title="选择订单" :visible.sync="open" width="550px" append-to-body>
      <el-form label-width="110px">
        <el-form-item label="订单编号" prop="orCode">
          <el-select
            v-model="form.orCode"
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

    <!-- 添加或修改计划详情对话框 -->
    <el-dialog :title="manufacturePlan.title" :visible.sync="manufacturePlan.open" width="850px" append-to-body>
      <el-form ref="manufacturePlanForm" :model="manufacturePlan.form" :rules="manufacturePlan.rules" label-width="110px">
        <el-col :span="12">
          <el-form-item label="所属订单" prop="orCode">
            <el-select v-model="manufacturePlan.form.orCode" placeholder="请选择订单" disabled>
              <el-option v-for="item in orderList" :key="item.orCode" :label="item.orCode" :value="item.orCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="所属生产计划" prop="mpmCode">
            <el-input v-model="manufacturePlan.form.mpmCode" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="odCode">
            <span slot="label">
              <el-tooltip placement="top">
                <div slot="content">
                  <div>须先选择订单，才能够加载订单的产品需求</div>
                </div>
                <i class="el-icon-question"></i>
              </el-tooltip>
              产品
            </span>
            <el-select v-model="manufacturePlan.form.odCode" placeholder="请选择产品" @change="selectProduct">
              <el-option
                v-for="item in orderDemandList.filter(ele => ele.orCode === manufacturePlan.form.orCode)"
                :key="item.odCode"
                :label="parseOdCode(item.odCode)"
                :value="item.odCode"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最晚结束时间" prop="mpEndPlan">
            <el-date-picker clearable v-model="manufacturePlan.form.mpEndPlan" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择最晚结束时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="优先级" prop="mpPriority">
            <el-input v-model="manufacturePlan.form.mpPriority" placeholder="请输入优先级" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="计划产品数量" prop="mpQtyPlan">
            <el-input v-model="manufacturePlan.form.mpQtyPlan" placeholder="请输入计划产品数量" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="描述" prop="mpDesc">
            <el-input v-model="manufacturePlan.form.mpDesc" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="manufacturePlan.buttonLoading" type="primary" @click="submitManufacturePlanForm">确 定</el-button>
        <el-button @click="cancelManufacturePlanForm">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listManufacturePlan, getManufacturePlan, delManufacturePlan, addManufacturePlan, updateManufacturePlan } from "@/api/system/manufacturePlan";
import { listManufacturePlanMain, getManufacturePlanMain, delManufacturePlanMain, addManufacturePlanMain, updateManufacturePlanMain } from "@/api/system/manufacturePlanMain";
import { listOrder } from "@/api/system/order";
import { listProduct } from "@/api/system/product";
import { listOrderDemand } from "@/api/system/orderDemand";
import manufactureTask from '@/views/system/manufactureTask';
import ManufactureMaterial from '@/views/system/manufactureTask/material';

export default {
  name: "ManufacturePlanAdd",
  components: {
    manufactureTask,
    ManufactureMaterial
  },
  dicts: ['ices_manufacture_plan_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 主表提交按钮是否加载中
      buttonLoading: false,
      // 选中内容
      idSelect: undefined,
      // 选中code
      codeSelect: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 生产计划表格数据
      manufacturePlanList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orCode: undefined,
        mpmCode: undefined,
        mpStat: undefined,
        mpPriority: undefined,
        mpDelete: 0,
      },
      // 表单
      form: {},
      // 表单校验
      rules: {
        orCode: [
          { required: true, message: "所属订单不能为空", trigger: "blur" }
        ],
        mpmEndPlan: [
          { required: true, message: "最晚结束时间不能为空", trigger: "blur" }
        ]
      },
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
      // 生产计划详情是否在预览模式
      preview: false,
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
      }
    };
  },
  async created() {
    await this.getProductList();
    await this.getOrderList();
    await this.getOrderDemandList();
    if (this.$route.query.mpmId) {
      getManufacturePlanMain(this.$route.query.mpmId).then(response => {
        this.form = response.data;
        this.queryParams.mpmCode = response.data.mpmCode;
        this.currentOrCode = response.data.orCode;
        this.getList();
        this.loading = false;
      });
    } else {
      this.loading = false;
    }
  },
  async activated() {
    await this.getProductList();
    await this.getOrderList();
    await this.getOrderDemandList();
    if (this.$route.query.mpmId) {
      getManufacturePlanMain(this.$route.query.mpmId).then(response => {
        this.form = response.data;
        this.queryParams.mpmCode = response.data.mpmCode;
        this.currentOrCode = response.data.orCode;
        this.getList();
        this.loading = false;
      });
    } else {
      this.loading = false;
    }
  },
  methods: {
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
    /** 查询生产计划列表 */
    getList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listManufacturePlan(this.queryParams).then(response => {
          this.manufacturePlanList = response.rows;
          this.total = response.total;
          this.loading = false;
          resolve()
        }).catch(() => {
          reject()
        })
      })
    },
    // 选中数据条目
    handleCurrentChange(current, old) {
      if (current) {
        this.idSelect = current.mpId
        this.codeSelect = current.mpCode
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.resetManufacturePlanForm();
      this.manufacturePlan.form.orCode = this.currentOrCode
      this.manufacturePlan.form.mpmCode = this.form.mpmCode
      this.manufacturePlan.open = true;
      this.manufacturePlan.title = "添加生产计划详情";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.resetManufacturePlanForm();
      const mpId = row.mpId || this.idSelect
      getManufacturePlan(mpId).then(response => {
        this.loading = false;
        this.manufacturePlan.form = response.data;
        this.manufacturePlan.open = true;
        this.manufacturePlan.title = "修改生产计划详情";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const mpIds = row.mpId || this.idSelect;
      this.$modal.confirm('是否确认删除生产计划详情编号为"' + mpIds + '"的数据项？').then(() => {
        this.loading = true;
        return delManufacturePlan(mpIds);
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
      this.download('system/manufacturePlan/export', {
        ...this.queryParams
      }, `manufacturePlan_${new Date().getTime()}.xlsx`)
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
     * 选择订单
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
      this.form.orCode = this.currentOrCode
      this.open = false
    },
    /**
     * 确认选择订单
     * @author YangZY
     * @date 20250426
     */
    submitOrder() {
      this.currentOrCode = this.form.orCode
      this.form.orCode = this.currentOrCode
      // 加载出所有产品需求，填入生产计划信息
      this.preview = true
      const demands = this.orderDemandList.filter(ele => ele.orCode === this.currentOrCode)
      this.manufacturePlanList = []
      demands.forEach(demand => {
        this.manufacturePlanList.push({
          orCode: this.currentOrCode,
          mpEndPlan: this.form.mpmEndPlan,
          odCode: demand.odCode,
          mpQtyPlan: demand.odDemand,
          mpPriority: this.currentOrder.orPriority
        })
      })
      this.open = false
    },
    /**
     * 重置表单
     * @author YangZY
     * @date 20250426
     */
    reset() {
      this.form = {
        mpmId: undefined,
        orCode: undefined,
        mpmCode: undefined,
        mpmBegin: undefined,
        mpmEndPlan: undefined,
        mpmStat: undefined,
        createBy: undefined,
        updateBy: undefined,
        createTime: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
      this.currentOrCode = '';
      this.manufacturePlanList = [];
      this.preview = false;
      this.idSelect = undefined;
      this.codeSelect = undefined;
    },
    /**
     * 重置此页面
     * @author YangZY
     * @date 20250426
     */
    resetPage() {
      this.$router.replace(`/manufacture/manufacturePlan/addMain`)
      this.reset()
    },
    /**
     * 提交主表信息
     * @author YangZY
     * @date 20250426
     */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.mpmId != null) {
            updateManufacturePlanMain(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.preview = false
              this.getList()
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            this.form.mpmStat = '1'
            addManufacturePlanMain(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.$router.replace(`/manufacture/manufacturePlan/addMain?mpmId=${response.data.mpmId}`)
              this.form = response.data
              this.queryParams.mpmCode = this.form.mpmCode
              this.preview = false
              this.getList()
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /**
     * 提交生产计划详情表单
     * @author YangZY
     * @date 20250426
     */
    submitManufacturePlanForm() {
      this.$refs["manufacturePlanForm"].validate(valid => {
        if (valid) {
          this.manufacturePlan.buttonLoading = true;
          if (this.manufacturePlan.form.mpId != null) {
            updateManufacturePlan(this.manufacturePlan.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.getList();
              this.manufacturePlan.open = false;
            }).finally(() => {
              this.manufacturePlan.buttonLoading = false;
            });
          } else {
            this.manufacturePlan.form.mpStat = '1'
            addManufacturePlan(this.manufacturePlan.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.getList();
              this.manufacturePlan.open = false;
            }).finally(() => {
              this.manufacturePlan.buttonLoading = false;
            });
          }
        }
      });
    },
    /**
     * 关闭生产计划详情表单
     * @author YangZY
     * @date 20250426
     */
    cancelManufacturePlanForm() {
      this.manufacturePlan.open = false
      this.resetManufacturePlanForm()
    },
    /**
     * 重置生产计划详情表单
     * @author YangZY
     * @date 20250426
     */
    resetManufacturePlanForm() {
      this.manufacturePlan.form = {
        mpId: undefined,
        orCode: undefined,
        odCode: undefined,
        mpmCode: undefined,
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
      this.resetForm("manufacturePlanForm");
    },
    /**
     * 选择产品的监听函数
     * 自动填入计划数量
     * @param {string} odCode 选择的odCode
     * @author YangZY
     * @date 20250423
     */
    selectProduct(odCode) {
      const demand = this.orderDemandList.find(ele => ele.odCode === odCode)
      if (demand) {
        this.manufacturePlan.form.mpQtyPlan = demand.odDemand
      }
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
.order-button {
  width: 100%;
  text-align: left;
}
</style>
