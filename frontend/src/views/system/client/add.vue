<template>
  <div class="app-container">
   <el-card shadow="never">
    <div slot="header">
      <div class="card-header">
        <div>客户基本信息</div>
        <div>
          <el-button
            :loading="buttonLoading"
            type="primary"
            @click="submitForm"
          >保存</el-button>
          <el-button
            :loading="buttonLoading"
            @click="resetPage"
          >重置</el-button>
        </div>
      </div>
    </div>
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
   </el-card>
    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>客户贸易信息</div>
        </div>
      </div>
      <client-trade v-if='form.clCode' :clCode="form.clCode" />
      <el-empty v-else description="保存客户信息后即可管理客户贸易信息" />
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
        this.selectClientLevel(this.form.cllCode,0);
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
        this.selectClientLevel(this.form.cllCode,0);
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
    //重置页面
    resetPage(){
      resetPage() 
      this.$router.replace(`/client/add`)
      this.reset()
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form && this.form.clId != null) {
            updateClient(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              let code = this.form.clCode
              this.form.clCode = ''
              setTimeout(() => {
                this.form.clCode = code
              }, 100)
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            this.form.clStat = '1'
            addClient(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.$router.replace(`/client/add?clId=${response.data.clId}`)
              this.form = response.data
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    // 选择客户等级的监听函数
    selectClientLevel(row, flag=1) {
      const clientLevel = this.clientLevelList.find(ele => ele.cllCode === row)
      if (clientLevel) {
        if (flag) {
          // flag=1用户主动选择
          // flag=0系统自动更新
          this.form.cllLabel = undefined;
          this.form.cllName = undefined;
          this.form.cllDesc = undefined;
        }
      } else {
        this.clientLevelList = []
      }
    },
    // 选择业务员的监听函数
    selectUser(row) {
    if (row) {
        // 更新表单中的归属部门
        this.form.deptId = row.deptId;
        // 更新表单中的手机号码
        this.form.phonenumber = row.phonenumber;
        // 你可以根据需求添加更多的更新逻辑，例如更新用户昵称、邮箱等
        this.form.nickName = row.nickName;
        this.form.email = row.email;
      }
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