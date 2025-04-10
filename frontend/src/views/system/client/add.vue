<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="110px" v-loading="loading">
      <el-col :span="6">
        <el-form-item label="客户等级" prop="cllCode">
          <el-select v-model="form.cllCode" placeholder="请选择客户等级">
            <el-option v-for="item in clientLevelList" :key="item.cllCode" :label="item.cllLabel" :value="item.cllCode">
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="业务员" prop="clOperator">
          <el-select v-model="form.clOperator" placeholder="请选择业务员" clearable>
            <el-option v-for="item in userList" :key="item.userId" :label="item.userName" :value="item.userId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="客户名称(中)" prop="clName">
          <el-input v-model="form.clName" placeholder="请输入客户名称(中)" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="客户名称(英)" prop="clNameEn">
          <el-input v-model="form.clNameEn" placeholder="请输入客户名称(英)" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="法人代表" prop="clLegalRepres">
          <el-input v-model="form.clLegalRepres" placeholder="请输入法人代表" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="企业联系人" prop="clContact">
          <el-input v-model="form.clContact" placeholder="请输入企业联系人" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="联系电话1" prop="clPhone1">
          <el-input v-model="form.clPhone1" placeholder="请输入联系电话1" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="联系电话2" prop="clPhone2">
          <el-input v-model="form.clPhone2" placeholder="请输入联系电话2" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="传真" prop="clFax">
          <el-input v-model="form.clFax" placeholder="请输入传真" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="邮政编码" prop="clPostcode">
          <el-input v-model="form.clPostcode" placeholder="请输入邮政编码" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="联系地址" prop="clAddr">
          <el-input v-model="form.clAddr" placeholder="请输入联系地址" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="企业性质" prop="clType">
          <el-input v-model="form.clType" placeholder="请输入企业性质" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="信用等级" prop="clCreditRank">
          <el-input v-model="form.clCreditRank" placeholder="请输入信用等级" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="信用额度" prop="clCreditQuota">
          <el-input v-model="form.clCreditQuota" placeholder="请输入信用额度" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="注册资金" prop="clRegisterCaptial">
          <el-input v-model="form.clRegisterCaptial" placeholder="请输入注册资金" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="注册号码" prop="clRegisterNo">
          <el-input v-model="form.clRegisterNo" placeholder="请输入注册号码" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="银行卡号" prop="clBankNum">
          <el-input v-model="form.clBankNum" placeholder="请输入银行卡号" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="开户行" prop="clBank">
          <el-input v-model="form.clBank" placeholder="请输入开户行" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="网址" prop="clWebsite">
          <el-input v-model="form.clWebsite" placeholder="请输入网址" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="注册日期" prop="clRegisterDate">
          <el-date-picker clearable v-model="form.clRegisterDate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择注册日期">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="状态" prop="clStat">
          <el-select v-model="form.clStat" placeholder="请选择状态">
            <el-option v-for="dict in dict.type.ices_client_status" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="18">
        <el-form-item label="描述" prop="clDesc">
          <el-input v-model="form.clDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-col>
    </el-form>
    <div>
      <el-button :loading="buttonLoading" type="primary" @click="submitForm">提 交</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
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
      // 总条数
      total: 0,
      // 客户基本信息表格数据
      clientList: [],
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
    this.loading = true;
    await this.getUserList();
    await this.getClientLevelList();
    this.reset();
    if (this.$route.query.clId) {
      getClient(this.$route.query.clId).then(response => {
        this.form = response.data;
        this.loading = false;
      });
    } else {
      this.loading = false;
    }
  },
  async activated() {
    this.loading = true;
    await this.getUserList();
    await this.getClientLevelList();
    this.reset();
    if (this.$route.query.clId) {
      getClient(this.$route.query.clId).then(response => {
        this.form = response.data;
        this.loading = false;
      });
    } else {
      this.loading = false;
    }
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
    // 取消按钮
    cancel() {
      this.close();
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
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.clId != null) {
            updateClient(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.close();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addClient(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.close();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    // 关闭本页，返回到客户关系管理
    close() {
      const obj = { path: "/client" };
      this.$tab.closeOpenPage(obj);
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
</style>