<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选贸易信息`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="贸易信息编码" prop="ctCode">
        <el-input
          v-model="queryParams.ctCode"
          placeholder="请输入贸易信息编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="客户" prop="clCode">
       <el-select v-model="queryParams.clCode" placeholder="请选择客户" :disabled="mode !== 0" disabled>
        <el-option
        v-for="option in clientList"
        :key="option.clCode"
        :label="option.clName"
        :value="option.clCode">
        </el-option>
       </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="ctDelete">
        <el-input
          v-model="queryParams.ctDelete"
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
      <el-col :span="1.5" v-if="mode !== 2">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:clientTrade:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5" v-if="mode !== 2">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:clientTrade:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5" v-if="mode !== 2">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:clientTrade:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:clientTrade:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="clientTradeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="贸易信息ID" align="center" prop="ctId" v-if="true"/>
      <el-table-column label="贸易信息编码" align="center" prop="ctCode" />
      <el-table-column label="客户" align="center" prop="clCode" />
      <el-table-column label="收货人" align="center" prop="ctName" />
      <el-table-column label="收货地址" align="center" prop="ctAddr" />
      <el-table-column label="收货电话" align="center" prop="ctPhone" />
      <el-table-column label="国家" align="center" prop="ctCountry" />
      <!-- <el-table-column label="已删除" align="center" prop="ctDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="ctDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="mode !== 2">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:clientTrade:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:clientTrade:remove']"
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

    <!-- 添加或修改客户贸易信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户" prop="clCode">
          <el-select v-model="form.clCode" placeholder="请选择客户" disabled>
           <el-option
            v-for="option in clientList"
           :key="option.clCode"
           :label="option.clName"
           :value="option.clCode">
           </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="收货人" prop="ctName">
          <el-input v-model="form.ctName" placeholder="请输入收货人" />
        </el-form-item>
        <el-form-item label="收货地址" prop="ctAddr">
          <el-input v-model="form.ctAddr" type="textarea" placeholder="请输入收货地址" />
        </el-form-item>
        <el-form-item label="收货电话" prop="ctPhone">
          <el-input v-model="form.ctPhone" placeholder="请输入收货电话" />
        </el-form-item>
        <el-form-item label="国家" prop="ctCountry">
          <el-input v-model="form.ctCountry" placeholder="请输入国家" />
        </el-form-item>
        <el-form-item label="描述" prop="ctDesc">
          <el-input v-model="form.ctDesc" type="textarea" placeholder="请输入内容" />
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
import { listClientTrade, getClientTrade, delClientTrade, addClientTrade, updateClientTrade } from "@/api/system/clientTrade";
import { listClient } from "@/api/system/client";

export default {
  name: "ClientTrade",
  props: {
    clCode: {
      required: false
    }
  },
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
      // 客户贸易信息表格数据
      clientTradeList: [],
      //客户表格数据
      clientList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ctCode: undefined,
        clCode: undefined,
        ctDelete: 0
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ctId: [
          { required: true, message: "贸易信息ID不能为空", trigger: "blur" }
        ],
        clCode: [
          { required: true, message: "客户不能为空", trigger: "blur" }
        ],
        ctName: [
          { required: true, message: "收货人不能为空", trigger: "blur" }
        ],
        ctAddr: [
          { required: true, message: "收货地址不能为空", trigger: "blur" }
        ],
        ctPhone: [
          { required: true, message: "收货电话不能为空", trigger: "blur" }
        ],
        ctCountry: [
          { required: true, message: "国家不能为空", trigger: "blur" }
        ],
      },
      // 1-按照客户筛选贸易信息
      // 2-按客户筛选贸易信息（不可修改）
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    if (this.clCode) {
      this.queryParams.clCode = this.clCode
      this.mode = 1
    }
    await this.getClientList();
    this.getList();
  },
  async activated() {
    if (this.clCode) {
      this.queryParams.clCode = this.clCode
      this.mode = 1
    } else {
      this.mode = 0
    }
    await this.getClientList();
    this.getList();
  },
  methods: {
    //客户贸易信息列表
    getClientList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listClient().then(response => {
          this.clientList = response.rows;
          if (this.mode === 1) {
            let client = response.rows.find(ele => ele.clCode === this.clCode)
            // 检查状态
            if (client.clStat !== '1') {
              this.mode = 2
            }
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false;
        })
      })
    },
    /** 查询客户列表 */
    getList() {
      this.loading = true;
      listClientTrade(this.queryParams).then(response => {
        this.clientTradeList = response.rows;
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
        ctId: undefined,
        ctCode: undefined,
        clCode: undefined,
        ctName: undefined,
        ctAddr: undefined,
        ctPhone: undefined,
        ctCountry: undefined,
        ctDelete: undefined,
        ctDesc: undefined,
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
      this.queryParams.clCode = this.clCode
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      if (this.clCode) {
        this.queryParams.clCode = this.clCode
      }
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.ctId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.mode === 1) {
        this.form.clCode = this.clCode
      }
      this.open = true;
      this.title = "添加客户贸易信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      if (this.clCode) {
      this.queryParams.clCode = this.clCode
    }
      const ctId = row.ctId || this.ids
      getClientTrade(ctId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改客户贸易信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.ctId != null) {
            updateClientTrade(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit('update')
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addClientTrade(this.form).then(response => {
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const ctIds = row.ctId || this.ids;
      this.$modal.confirm('是否确认删除客户贸易信息编号为"' + ctIds + '"的数据项？').then(() => {
        this.loading = true;
        return delClientTrade(ctIds);
      }).then(() => {
        this.loading = false;
        this.$emit('update')
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/clientTrade/export', {
        ...this.queryParams
      }, `clientTrade_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
.el-select{
  width: 100%;
}
.el-date-editor{
  width: 100%;
}
</style>