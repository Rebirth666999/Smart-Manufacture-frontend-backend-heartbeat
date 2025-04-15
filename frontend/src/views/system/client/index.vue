<template>
  <div class="app-container">
    <el-card class="view-card">
      <div slot="header">
        <div class="card-header">
          <div>客户基本信息</div>
        </div>
      </div>
      <div>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户等级" prop="cllCode">
        <el-select
          v-model="queryParams.cllCode"
          placeholder="请选择客户等级"
          clearable
          @keyup.enter.native="handleQuery"
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
          @keyup.enter.native="handleQuery"
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

    <el-table
          v-loading="loading"
          :data="clientList"
          @current-change="handleCurrentChange"
          highlight-current-row
          max-height="240"
        >
          <el-table-column label="选择" width="55" align="center">
            <template slot-scope="scope">
              <el-radio :value="scope.row.clId === idSelect" :label="true" />
            </template>
          </el-table-column>
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
  </div>
    </el-card>
    <el-card class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>客户贸易信息</div>
        </div>
      </div>
      <client-trade v-if='idSelect' :key="idSelect" :clCode="codeSelect" />
      <el-empty v-else description="选中客户信息后即可管理客户贸易信息" />
    </el-card>
  </div>
</template>

<script>
import { listClient, getClient, delClient, addClient, updateClient } from "@/api/system/client";
import { listUser } from "@/api/system/user";
import { listClientLevel } from "@/api/system/clientLevel";
import clientTrade from '@/views/system/clientTrade';

export default {
  name: "Client",
  components: {
    clientTrade
  },
  dicts: ['ices_client_status'],
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.idSelect = undefined
      this.codeSelect = undefined
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.idSelect = undefined
      this.codeSelect = undefined
      this.handleQuery();
    },
    // 选中数据条目
    handleCurrentChange(current, old) {
      this.idSelect = current.clId
      this.codeSelect = current.clCode
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push(`/client/add`)
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const clId = row.clId || this.idSelect
      this.$router.push(`/client/add?clId=${clId}`)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const clIds = row.clId || this.idSelect;
      this.$modal.confirm('是否确认删除客户信息编号为"' + clIds + '"的数据项？').then(() => {
        this.loading = true;
        return delClient(clIds);
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
      this.download('system/client/export', {
        ...this.queryParams
      }, `client_${new Date().getTime()}.xlsx`)
    },
    // 查看客户贸易信息
    handleClientTrade(row) {
      this.$router.push(`/clientTrade?clCode=${row.clCode}`)
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
::v-deep .el-radio span.el-radio__label {
  display: none;
}
.view-card {
  max-height: 50vh;
  overflow: scroll;
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