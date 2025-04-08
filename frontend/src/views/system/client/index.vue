<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户等级" prop="cllCode">
        <el-select
          v-model="queryParams.cllCode"
          placeholder="请选择客户等级"
          clearable
        >
          <el-option
            v-for="item in clientLevelList"
            :key="item.cllCode"
            :label="item.cllLabel"
            :value="item.cllCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="业务员" prop="clOperator">
        <el-select
          v-model="queryParams.clOperator"
          placeholder="请选择业务员"
          clearable
        >
          <el-option
            v-for="item in userList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="客户名称" prop="clName">
        <el-input
          v-model="queryParams.clName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="clStat">
        <el-select v-model="queryParams.clStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_client_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="clDelete">
        <el-input
          v-model="queryParams.clDelete"
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
          v-hasPermi="['system:client:add']"
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
          v-hasPermi="['system:client:edit']"
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
          v-hasPermi="['system:client:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:client:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="clientList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="基本信息ID" align="center" prop="clId" v-if="true"/>
      <el-table-column label="客户编码" align="center" prop="clCode" />
      <el-table-column label="客户等级" align="center" prop="cllCode">
        <template slot-scope="scope">
          {{ clientLevelList.find(ele => ele.cllCode === scope.row.cllCode).cllLabel || '' }}
        </template>
      </el-table-column>
      <el-table-column label="业务员" align="center" prop="clOperator">
        <template slot-scope="scope">
          {{ scope.row.clOperator && (userList.find(ele => ele.userId === scope.row.clOperator).userName || '') }}
        </template>
      </el-table-column>
      <el-table-column label="客户名称(中)" align="center" prop="clName" />
      <el-table-column label="客户名称(英)" align="center" prop="clNameEn" />
      <!-- <el-table-column label="法人代表" align="center" prop="clLegalRepres" /> -->
      <!-- <el-table-column label="企业联系人" align="center" prop="clContact" /> -->
      <el-table-column label="联系电话1" align="center" prop="clPhone1" />
      <!-- <el-table-column label="联系电话2" align="center" prop="clPhone2" /> -->
      <!-- <el-table-column label="传真" align="center" prop="clFax" /> -->
      <!-- <el-table-column label="邮政编码" align="center" prop="clPostcode" /> -->
      <el-table-column label="联系地址" align="center" prop="clAddr" />
      <!-- <el-table-column label="企业性质" align="center" prop="clType" /> -->
      <!-- <el-table-column label="信用等级" align="center" prop="clCreditRank" /> -->
      <!-- <el-table-column label="信用额度" align="center" prop="clCreditQuota" /> -->
      <!-- <el-table-column label="注册资金" align="center" prop="clRegisterCaptial" /> -->
      <!-- <el-table-column label="注册号码" align="center" prop="clRegisterNo" /> -->
      <!-- <el-table-column label="银行卡号" align="center" prop="clBankNum" /> -->
      <!-- <el-table-column label="开户行" align="center" prop="clBank" /> -->
      <!-- <el-table-column label="网址" align="center" prop="clWebsite" /> -->
      <!-- <el-table-column label="注册日期" align="center" prop="clRegisterDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.clRegisterDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="状态" align="center" prop="clStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_client_status" :value="scope.row.clStat"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="clDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="clDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:client:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-location-information"
            @click="handleClientTrade(scope.row)"
          >贸易信息</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:client:remove']"
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

    <!-- 添加或修改客户基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-col :span="12">
          <el-form-item label="客户等级" prop="cllCode">
            <el-select
              v-model="form.cllCode"
              placeholder="请选择客户等级"
            >
              <el-option
                v-for="item in clientLevelList"
                :key="item.cllCode"
                :label="item.cllLabel"
                :value="item.cllCode"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="业务员" prop="clOperator">
            <el-select
              v-model="form.clOperator"
              placeholder="请选择业务员"
              clearable
            >
              <el-option
                v-for="item in userList"
                :key="item.userId"
                :label="item.userName"
                :value="item.userId"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="客户名称(中)" prop="clName">
            <el-input v-model="form.clName" placeholder="请输入客户名称(中)" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="客户名称(英)" prop="clNameEn">
            <el-input v-model="form.clNameEn" placeholder="请输入客户名称(英)" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="法人代表" prop="clLegalRepres">
            <el-input v-model="form.clLegalRepres" placeholder="请输入法人代表" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业联系人" prop="clContact">
            <el-input v-model="form.clContact" placeholder="请输入企业联系人" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话1" prop="clPhone1">
            <el-input v-model="form.clPhone1" placeholder="请输入联系电话1" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话2" prop="clPhone2">
            <el-input v-model="form.clPhone2" placeholder="请输入联系电话2" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="传真" prop="clFax">
            <el-input v-model="form.clFax" placeholder="请输入传真" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮政编码" prop="clPostcode">
            <el-input v-model="form.clPostcode" placeholder="请输入邮政编码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系地址" prop="clAddr">
            <el-input v-model="form.clAddr" placeholder="请输入联系地址" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业性质" prop="clType">
            <el-input v-model="form.clType" placeholder="请输入企业性质" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="信用等级" prop="clCreditRank">
            <el-input v-model="form.clCreditRank" placeholder="请输入信用等级" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="信用额度" prop="clCreditQuota">
            <el-input v-model="form.clCreditQuota" placeholder="请输入信用额度" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="注册资金" prop="clRegisterCaptial">
            <el-input v-model="form.clRegisterCaptial" placeholder="请输入注册资金" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="注册号码" prop="clRegisterNo">
            <el-input v-model="form.clRegisterNo" placeholder="请输入注册号码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="银行卡号" prop="clBankNum">
            <el-input v-model="form.clBankNum" placeholder="请输入银行卡号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="开户行" prop="clBank">
            <el-input v-model="form.clBank" placeholder="请输入开户行" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="网址" prop="clWebsite">
            <el-input v-model="form.clWebsite" placeholder="请输入网址" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="注册日期" prop="clRegisterDate">
            <el-date-picker clearable
              v-model="form.clRegisterDate"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择注册日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="clStat">
            <el-select v-model="form.clStat" placeholder="请选择状态">
              <el-option
                v-for="dict in dict.type.ices_client_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="描述" prop="clDesc">
            <el-input v-model="form.clDesc" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listClient, getClient, delClient, addClient, updateClient } from "@/api/system/client";
import { listUser } from "@/api/system/user";
import { listClientLevel } from "@/api/system/clientLevel";

export default {
  name: "Client",
  dicts: ['ices_client_status'],
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
      // 客户基本信息表格数据
      clientList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cllCode: undefined,
        clOperator: undefined,
        clName: undefined,
        clStat: undefined,
        clDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        clId: [
          { required: true, message: "基本信息ID不能为空", trigger: "blur" }
        ],
        cllCode: [
          { required: true, message: "客户等级不能为空", trigger: "blur" }
        ],
        clName: [
          { required: true, message: "客户名称不能为空", trigger: "blur" }
        ],
        clStat: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
      },
      // 用户列表
      userList: [],
      // 客户等级列表
      clientLevelList: []
    };
  },
  async created() {
    await this.getUserList();
    await this.getClientLevelList();
    this.getList();
  },
  async activated() {
    await this.getUserList();
    await this.getClientLevelList();
    this.getList();
  },
  methods: {
    // 获取客户等级列表
    getClientLevelList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listClientLevel().then(response => {
          this.clientLevelList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 获取用户列表用于业务员字段
    getUserList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listUser().then(response => {
          this.userList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询客户基本信息列表 */
    getList() {
      this.loading = true;
      listClient(this.queryParams).then(response => {
        this.clientList = response.rows;
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
        clId: undefined,
        clCode: undefined,
        cllCode: undefined,
        clOperator: undefined,
        clName: undefined,
        clNameEn: undefined,
        clLegalRepres: undefined,
        clContact: undefined,
        clPhone1: undefined,
        clPhone2: undefined,
        clFax: undefined,
        clPostcode: undefined,
        clAddr: undefined,
        clType: undefined,
        clCreditRank: undefined,
        clCreditQuota: undefined,
        clRegisterCaptial: undefined,
        clRegisterNo: undefined,
        clBankNum: undefined,
        clBank: undefined,
        clWebsite: undefined,
        clRegisterDate: undefined,
        clStat: undefined,
        clDelete: undefined,
        clDesc: undefined,
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
      this.ids = selection.map(item => item.clId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const clId = row.clId || this.ids
      getClient(clId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改客户基本信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.clId != null) {
            updateClient(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addClient(this.form).then(response => {
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
      const clIds = row.clId || this.ids;
      this.$modal.confirm('是否确认删除客户基本信息编号为"' + clIds + '"的数据项？').then(() => {
        this.loading = true;
        return delClient(clIds);
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
      this.download('system/client/export', {
        ...this.queryParams
      }, `client_${new Date().getTime()}.xlsx`)
    },
    // 查看客户贸易信息
    handleClientTrade(row) {
      this.$router.push(`/order/clientTrade?clCode=${row.clCode}`)
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