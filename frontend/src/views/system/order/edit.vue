<template>
  <div class="app-container" v-loading="loading">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>订单信息</div>
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
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-col :span="12">
          <el-form-item label="客户" prop="clCode">
            <el-select v-model="form.clCode" placeholder="请选择客户" clearable>
              <el-option v-for="item in clientList" :key="item.clCode" :label="item.clName" :value="item.clCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="orCode">
            <span slot="label">
              <el-tooltip placement="top">
                <div slot="content">
                  <div>订单编码须以"Order-"开头</div>
                  <div>在其后输入五位数字，高位补0</div>
                  <div>若留空则系统自动生成</div>
                  <div>生成后编码不可更改</div>
                </div>
                <i class="el-icon-question"></i>
              </el-tooltip>
              订单编码
            </span>
            <el-input v-model="form.orCode" placeholder="请输入订单编码" :disabled="$route.query.orId != undefined" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订单优先级" prop="orPriority">
            <el-input v-model="form.orPriority" placeholder="请输入订单优先级" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="截止时间" prop="orDeadline">
            <el-date-picker clearable v-model="form.orDeadline" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择截止时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="总价" prop="orPrice">
            <el-input v-model="form.orPrice" placeholder="请输入总价" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="ctCode">
            <span slot="label">
              <el-tooltip placement="top">
                <div slot="content">
                  <div>选择客户贸易信息后，将自动填充收货人和收货地址</div>
                  <div>须先选择客户，才能够加载贸易信息</div>
                </div>
                <i class="el-icon-question"></i>
              </el-tooltip>
              客户贸易信息
            </span>
            <el-select v-model="form.ctCode" placeholder="请选择客户贸易信息" clearable @change="selectClientTrade">
              <el-option v-for="item in clientTradeList.filter(ele => ele.clCode === form.clCode)" :key="item.ctCode" :label="`【${item.ctName}】${item.ctAddr}`" :value="item.ctCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="收货人" prop="orRecv">
            <el-input v-model="form.orRecv" placeholder="请输入收货人" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="收货地址" prop="orAddr">
            <el-input v-model="form.orAddr" type="textarea" placeholder="请输入收货地址" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="描述" prop="orDesc">
            <el-input v-model="form.orDesc" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
      </el-form>
    </el-card>
    <el-card shadow="never" class="controlled-card" v-if="mode !== 2">
      <div slot="header">
        <div class="card-header">
          <div>订单产品信息</div>
        </div>
      </div>
      <order-demand v-if='form.orCode' :orCode="form.orCode" />
      <el-empty v-else description="保存订单后即可管理订单产品" />
    </el-card>
    <el-card shadow="never" class="controlled-card" v-if="mode === 2">
      <div slot="header">
        <div class="card-header">
          <div>订单产品信息（预览）</div>
        </div>
      </div>
      <order-demand-preview v-if='form.orCode' :orCode="form.orCode" ref="orderDemand" />
    </el-card>
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/system/order";
import { updateOrderWithDemand } from "@/api/system/orderDemand";
import { listProduct } from "@/api/system/product";
import { listClient } from "@/api/system/client";
import { listClientTrade } from "@/api/system/clientTrade";
import orderDemand from '@/views/system/orderDemand';
import orderDemandPreview from '@/views/system/orderDemand/preview';

export default {
  name: "EditOrder",
  components: {
    orderDemand,
    orderDemandPreview
  },
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
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orId: [
          { required: true, message: "订单ID不能为空", trigger: "blur" }
        ],
        clCode: [
          { required: true, message: "客户不能为空", trigger: "blur" }
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
        orRecv: [
          { required: true, message: "收货人不能为空", trigger: "blur" }
        ],
        orAddr: [
          { required: true, message: "收货地址不能为空", trigger: "blur" }
        ],
      },
      // 产品列表
      productList: [],
      // 客户列表
      clientList: [],
      // 客户贸易信息列表
      clientTradeList: [],
      // 0-新增
      // 1-未发布修改
      // 2-发布后修改
      mode: 0
    };
  },
  async created() {
    this.loading = true;
    await this.getProductList();
    await this.getClientList();
    await this.getClientTradeList();
    this.reset();
    if (this.$route.query.orId) {
      getOrder(this.$route.query.orId).then(response => {
        this.form = response.data;
        if (response.data.orStat === '1') {
          this.mode = 1
        } else {
          this.mode = 2
        }
        this.loading = false;
      });
    } else {
      this.mode = 0
      this.loading = false;
    }
  },
  async activated() {
    this.loading = true;
    await this.getProductList();
    await this.getClientList();
    await this.getClientTradeList();
    this.reset();
    if (this.$route.query.orId) {
      getOrder(this.$route.query.orId).then(response => {
        this.form = response.data;
        if (response.data.orStat === '1') {
          this.mode = 1
        } else {
          this.mode = 2
        }
        this.loading = false;
      });
    } else {
      this.mode = 0
      this.loading = false;
    }
  },
  methods: {
    // 查询客户贸易信息列表
    getClientTradeList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listClientTrade().then(response => {
          this.clientTradeList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
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
    // 重置页面
    resetPage() {
      this.$router.replace(`/order/addOrder`)
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        orId: undefined,
        maCode: undefined,
        clCode: undefined,
        orName: undefined,
        orCodeOrgn: undefined,
        orStat: undefined,
        orDemand: undefined,
        orPriority: undefined,
        orDeadline: undefined,
        orPrice: undefined,
        orRecv: undefined,
        orAddr: undefined,
        orDelete: undefined,
        orDesc: undefined,
        createBy: undefined,
        updateBy: undefined,
        createTime: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.orId != null) {
            if (this.form.orStat !== '1') {
              this.$confirm('修改已发布的订单将记录订单历史记录并启动报警处理，请确认订单信息已修改完毕。', '提示', {
                confirmButtonText: '确定修改',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(async () => {
                // 已发布之后的修改需要记录历史
                this.form.orCodeOrgn = this.form.orCode
                const newOrder = (await updateOrder(this.form)).data
                await updateOrderWithDemand({
                  order: newOrder,
                  demand: this.$refs.orderDemand.orderDemandList
                })
                // 发布之后修改，应当提醒用户
                // 然后离开此界面
                this.$modal.msgSuccess("修改成功，已启动报警处理");
                this.$tab.closeOpenPage({ path: "/order" });
                this.buttonLoading = false;
              }).catch(() => {
                // 取消操作
                this.buttonLoading = false;
              })
            } else {
              // 未发布的可以直接修改
              updateOrder(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
              }).finally(() => {
                this.buttonLoading = false;
              })
            }
          } else {
            // 新增订单的默认状态
            this.form.orStat = '1'
            addOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.$router.replace(`/order/addOrder?orId=${response.data.orId}`)
              this.form = response.data
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    // 关闭本页，返回到订单管理
    close() {
      const obj = { path: "/order" };
      this.$tab.closeOpenPage(obj);
    },
    // 监听选中的贸易信息变化
    selectClientTrade(ctCode) {
      const ct = this.clientTradeList.find(ele => ele.ctCode === ctCode)
      if (ct) {
        this.form.orRecv = ct.ctName
        this.form.orAddr = ct.ctAddr
      }
    }
  }
};
</script>
<style scoped>
.el-select {
  width: 100%;
}

.el-date-editor {
  width: 100%;
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