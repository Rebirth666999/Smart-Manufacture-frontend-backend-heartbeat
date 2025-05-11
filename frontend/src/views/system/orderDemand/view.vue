<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单" prop="orCode">
        <el-select v-model="queryParams.orCode" placeholder="请选择订单" disabled clearable>
          <el-option
            v-for="option in orderList"
            :key="option.orCode"
            :label="option.orCode"
            :value="option.orCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所需产品" prop="prCode">
        <el-select v-model="queryParams.prCode" placeholder="请选择产品" clearable>
          <el-option
           v-for="option in productList"
           :key="option.prCode"
           :label="option.prName"
           :value="option.prCode">
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="odDelete">
        <el-input
          v-model="queryParams.odDelete"
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
      <!-- <el-col :span="1.5" v-if="mode === 0">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:orderDemand:add']"
        >新增</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5" v-if="mode === 0">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:orderDemand:edit']"
        >修改</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5" v-if="mode === 0">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:orderDemand:remove']"
        >删除</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:orderDemand:export']"
        >导出</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderDemandList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所需产品ID" align="center" prop="odId" v-if="true"/>
      <el-table-column label="所需产品编码" align="center" prop="odCode" />
      <el-table-column label="订单" align="center" prop="orCode" />
      <el-table-column label="所需产品" align="center" prop="prCode">
        <template slot-scope="scope">
          {{ productList.find(ele => ele.prCode === scope.row.prCode).prName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="数量" align="center" prop="odDemand" />
      <el-table-column label="金额小计" align="center" prop="odPrice" />
      <el-table-column label="定制详情" align="center" prop="odCust" />
      <!-- <el-table-column label="已删除" align="center" prop="odDelete" /> -->
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="mode === 0">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:orderDemand:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:orderDemand:remove']"
          >删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改订单所需产品关联对话框 -->
    <!-- <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单" prop="orCode">
          <el-select v-model="form.orCode" placeholder="请选择订单" disabled>
            <el-option
              v-for="option in orderList"
              :key="option.orCode"
              :label="option.orCode"
              :value="option.orCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所需产品" prop="prCode">
          <el-select v-model="form.prCode" placeholder="请选择产品" @change="selectPrCode">
            <el-option
             v-for="option in productList"
             :key="option.prCode"
             :label="option.prName"
             :value="option.prCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="odDemand">
          <el-input v-model="form.odDemand" placeholder="请输入所需产品数量" />
        </el-form-item>
        <el-form-item label="金额小计" prop="odPrice">
          <el-input v-model="form.odPrice" placeholder="请输入金额小计" />
        </el-form-item>
        <el-form-item label="定制详情" prop="odCust" v-if="form.prCode">
          <el-table class="mb8" :data="custList" size="mini">
            <el-table-column label="序号" align="center" type="index" />
            <el-table-column label="定制项名称" align="center" prop="custKey">
              <template slot-scope="scope">
                <el-select v-model="custList[scope.$index].custKey" @change="selectCustKey(scope)">
                  <el-option
                    v-for="item in Object.keys(custInfo)"
                    :key="item"
                    :label="item"
                    :value="item"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="值" align="center" prop="custVal">
              <template slot-scope="scope">
                <el-select v-model="custList[scope.$index].custVal">
                  <el-option
                    v-for="item in custInfo[custList[scope.$index].custKey]"
                    :key="item"
                    :label="item"
                    :value="item"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="60px">
              <template slot-scope="scope">
                <el-button @click="deleteCust(scope)" type="danger" icon="el-icon-delete" size="small" circle plain></el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button @click="addCust" type="primary" icon="el-icon-plus" size="small" plain>新增</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog> -->
  </div>
</template>

<script>
import { listOrderDemand, getOrderDemand, delOrderDemand, addOrderDemand, updateOrderDemand } from "@/api/system/orderDemand";
import { listProduct } from "@/api/system/product";
import { listOrder } from "@/api/system/order";

export default {
  name: "OrderDemand",
  props: {
    orCode: {
      required: false
    }
  },
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // // 选中数组
      // ids: [],
      // // 非单个禁用
      // single: true,
      // // 非多个禁用
      // multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单所需产品关联表格数据
      orderDemandList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        odCode: undefined,
        prCode: undefined,
        orCode: undefined,
        odDemand: undefined,
        odPrice: undefined,
        odCust: undefined,
        odDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        odId: [
          { required: true, message: "所需产品ID不能为空", trigger: "blur" }
        ],
        prCode: [
          { required: true, message: "所需产品不能为空", trigger: "blur" }
        ],
        orCode: [
          { required: true, message: "订单不能为空", trigger: "blur" }
        ],
        odDemand: [
          { required: true, message: "所需产品数量不能为空", trigger: "blur" }
        ],
        odPrice: [
          { required: true, message: "金额小计不能为空", trigger: "blur" }
        ],
      },
      // 产品列表
      productList: [],
      // 订单列表
      orderList: [],
      // 暂存用定制信息表格
      custList: [],
      // 定制信息
      custInfo: [],
      // 0-可编辑
      // 1-不可编辑
      mode: 0
    };
  },
  async created() {
    if (this.orCode) {
      this.queryParams.orCode = this.orCode
    }
    await this.getOrderList();
    await this.getMaterialList();
    this.getList();
  },
  async activated() {
    if (this.orCode) {
      this.queryParams.orCode = this.orCode
    }
    await this.getOrderList();
    await this.getMaterialList();
    this.getList();
  },
  methods: {
    // 查询订单
    getOrderList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listOrder().then(response => {
          this.orderList = response.rows
          const order = response.rows.find(ele => ele.orCode === this.orCode)
          if (order && order.orStat !== '1') {
            this.mode = 1
          } else {
            this.mode = 0
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询产品
    getMaterialList() {
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
    /** 查询订单所需产品关联列表 */
    getList() {
      this.loading = true;
      listOrderDemand(this.queryParams).then(response => {
        this.orderDemandList = response.rows;
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
        odId: undefined,
        odCode: undefined,
        prCode: undefined,
        orCode: undefined,
        odDemand: undefined,
        odPrice: undefined,
        odCust: undefined,
        odDelete: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
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
      this.queryParams.orCode = this.orCode
      this.handleQuery();
    },
    // // 多选框选中数据
    // handleSelectionChange(selection) {
    //   this.ids = selection.map(item => item.odId)
    //   this.single = selection.length!==1
    //   this.multiple = !selection.length
    // },
    // /** 新增按钮操作 */
    // handleAdd() {
    //   this.reset();
    //   if (this.orCode) {
    //     this.form.orCode = this.orCode
    //   }
    //   this.open = true;
    //   this.title = "添加订单所需产品关联";
    // },
    // /** 修改按钮操作 */
    // handleUpdate(row) {
    //   this.loading = true;
    //   this.reset();
    //   const odId = row.odId || this.ids
    //   getOrderDemand(odId).then(response => {
    //     this.loading = false;
    //     this.form = response.data;
    //     // 载入定制选项
    //     this.selectPrCode(this.form.prCode);
    //     this.parseCustString(this.form.odCust);
    //     this.open = true;
    //     this.title = "修改订单所需产品关联";
    //   });
    // },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.parseCustObject()) {
            this.$modal.msgWarning("定制详情存在错误或重复");
            return
          }
          this.buttonLoading = true;
          if (this.form.odId != null) {
            updateOrderDemand(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit('update')
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addOrderDemand(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.$emit('update')
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    // /** 删除按钮操作 */
    // handleDelete(row) {
    //   const odIds = row.odId || this.ids;
    //   this.$modal.confirm('是否确认删除订单所需产品关联编号为"' + odIds + '"的数据项？').then(() => {
    //     this.loading = true;
    //     return delOrderDemand(odIds);
    //   }).then(() => {
    //     this.loading = false;
    //     this.$emit('update')
    //     this.getList();
    //     this.$modal.msgSuccess("删除成功");
    //   }).catch(() => {
    //   }).finally(() => {
    //     this.loading = false;
    //   });
    // },
    // /** 导出按钮操作 */
    // handleExport() {
    //   this.download('system/orderDemand/export', {
    //     ...this.queryParams
    //   }, `orderDemand_${new Date().getTime()}.xlsx`)
    // },
    /**
     * 选中产品后的回调函数
     * @param {string?} prCode 选中的prCode
     * @author YangZY
     * @date 20250423
     */
    selectPrCode(prCode) {
      if (prCode) {
        const product = this.productList.find(ele => ele.prCode === prCode)
        this.custInfo = JSON.parse(product.prCust)
      }
      this.custList = []
    },
    /**
     * 定制详情选中某个定制项名称
     * 需要清空已选择的值
     * @param {any} scope 当前选中内容
     * @author YangZY
     * @date 20250423
     */
    selectCustKey(scope) {
      this.custList[scope.$index].custVal = ''
    },
    /**
     * 删除指定的定制详情
     * @param {any} scope 表格行信息
     * @author YangZY
     * @date 20250423
     */
    deleteCust(scope) {
      this.custList.splice(scope.$index, 1)
    },
    /**
     * 添加一条定制详情
     * @author YangZY
     * @date 20250423
     */
    addCust() {
      this.custList.push({
        custKey: '',
        custVal: ''
      })
    },
    /**
     * 定制详情JSON转换为List
     * @param {string?} str 待转换字符串
     * @author YangZY
     * @date 20250423
     */
    parseCustString(str) {
      if (str) {
        const json = JSON.parse(str)
        this.custList = []
        Object.keys(json).forEach(key => {
          this.custList.push({
            custKey: key,
            custVal: json[key]
          })
        })
      }
    },
    /**
     * 定制详情List转换为JSON
     * @returns 是否存在不合法记录
     * @author YangZY
     * @date 20250423
     */
    parseCustObject() {
      const result = {}
      let success = true
      const keys = this.custList.map(ele => ele.custKey)
      const keySet = new Set(keys)
      if (keys.length !== keySet.size) {
        // 有重复key
        return false
      }
      this.custList.forEach(cust => {
        if (cust.custKey.length > 0 && cust.custVal.length > 0) {
          result[cust.custKey] = cust.custVal
        } else {
          success = false
        }
      })
      this.form.odCust = JSON.stringify(result)
      return success
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
