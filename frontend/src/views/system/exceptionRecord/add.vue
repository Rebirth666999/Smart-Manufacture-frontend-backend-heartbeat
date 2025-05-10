<template>
  <div class="app-container" v-loading="loading">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>异常信息</div>
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
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-col :span="12">
          <el-form-item label="异常源" prop="exsCode">
            <el-select v-model="form.exsCode" placeholder="请选择异常源">
              <el-option
               v-for="option in exceptionSourceList"
               :key="option.exsCode"
               :label="option.exsName"
               :value="option.exsCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="异常" prop="exCode">
            <el-select v-model="form.exCode" placeholder="请选择异常">
              <el-option
               v-for="option in exceptionList"
               :key="option.exCode"
               :label="option.exName"
               :value="option.exCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="描述" prop="exrDesc">
            <el-input v-model="form.exrDesc" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="exrStat">
            <el-select v-model="form.exrStat" placeholder="请选择状态" disabled>
              <el-option
                v-for="dict in dict.type.ices_exception_record_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="等级" prop="exrLevel">
            <el-select v-model="form.exrLevel" placeholder="请选择等级">
              <el-option
                v-for="dict in dict.type.ices_exception_record_level"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="异常上报人" prop="exrUserReport">
            <el-select
              v-model="form.exrUserReport"
              placeholder="请选择异常上报人"
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
          <el-form-item label="当前处理人" prop="exrUserHandle">
            <el-select
              v-model="form.exrUserHandle"
              placeholder="请选择当前处理人"
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
          <el-form-item label="异常解除人" prop="exrUserFinish">
            <el-select
              v-model="form.exrUserFinish"
              placeholder="请选择异常解除人"
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
          <el-form-item label="异常责任人" prop="exrUserResp">
            <el-select
              v-model="form.exrUserResp"
              placeholder="请选择异常责任人"
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
        <el-col :span="24">
          <el-form-item label="持续时间" prop="exrDuration">
            <el-input v-model="form.exrDuration" placeholder="请输入持续时间" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="影响因子" prop="exrImpactFactor">
            <el-input v-model="form.exrImpactFactor" placeholder="请输入影响因子" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="影响等级" prop="exrImpactLevel">
            <el-select v-model="form.exrImpactLevel" placeholder="请选择影响等级">
              <el-option
                v-for="dict in dict.type.ices_exception_record_impact_level"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        
        <el-col :span="24">
          <el-form-item label="参数" prop="exrParam">
            <el-table class="mb8" :data="paramList">
              <el-table-column label="序号" align="center" type="index" />
              <el-table-column label="键" align="center" prop="paramKey">
                <template slot-scope="scope">
                  <el-input v-model="paramList[scope.$index].key" />
                </template>
              </el-table-column>
              <el-table-column label="值" align="center" prop="paramVal">
                <template slot-scope="scope">
                  <el-input v-model="paramList[scope.$index].val" />
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="80px">
                <template slot-scope="scope">
                  <el-button @click="deleteParam(scope)" type="danger" icon="el-icon-delete" size="small" circle plain></el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-button @click="addParam" type="primary" icon="el-icon-plus" size="small" plain>新增</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { listExceptionRecord, getExceptionRecord, delExceptionRecord, addExceptionRecord, updateExceptionRecord } from "@/api/system/exceptionRecord";
import { listUser } from "@/api/system/user";
import { listException } from "@/api/system/exception";
import { listExceptionSource } from "@/api/system/exceptionSource";

export default {
  name: "ExceptionRecordAdd",
  dicts: ['ices_exception_record_status', 'ices_exception_record_level', 'ices_exception_record_impact_level'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 表单参数
      form: {
        exrStat: "1",
      },
      // 表单校验
      rules: {
        exrId: [
          { required: true, message: "异常记录ID不能为空", trigger: "blur" }
        ],
        exsCode: [
          { required: true, message: "异常源不能为空", trigger: "blur" }
        ],
        exCode: [
          { required: true, message: "异常不能为空", trigger: "blur" }
        ],
        exrDesc: [
          { required: true, message: "描述不能为空", trigger: "blur" }
        ],
        exrStat: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        exrLevel: [
          { required: true, message: "等级不能为空", trigger: "change" }
        ],
        exrDuration: [
          { required: true, message: "持续时间不能为空", trigger: "blur" }
        ],
        exrImpactFactor: [
          { required: true, message: "影响因子不能为空", trigger: "blur" }
        ],
        exrImpactLevel: [
          { required: true, message: "影响等级不能为空", trigger: "blur" }
        ],
      },
      // 异常列表
      exceptionList: [],
      // 异常源列表
      exceptionSourceList: [],
      // 用户列表
      userList: [],
      // 暂存用参数表格
      paramList: []
    };
  },
  async created() {
    this.loading = true
    await this.getUserList()
    await this.getExceptionList()
    await this.getExceptionSourceList()
    if (this.$route.query.exrId) {
      getExceptionRecord(this.$route.query.exrId).then(response => {
        this.form = response.data
        this.parseParamString(this.form.exrParam)
        this.loading = false
      })
    } else {
      this.loading = false
    }
  },
  async activated() {
    this.loading = true
    await this.getUserList()
    await this.getExceptionList()
    await this.getExceptionSourceList()
    if (this.$route.query.exrId) {
      getExceptionRecord(this.$route.query.exrId).then(response => {
        this.form = response.data
        this.parseParamString(this.form.exrParam)
        this.loading = false
      })
    } else {
      this.loading = false
    }
  },
  methods: {
    // 获取用户列表
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
    // 获取异常源列表
    getExceptionSourceList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listExceptionSource().then(response => {
          this.exceptionSourceList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 获取异常列表
    getExceptionList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listException().then(response => {
          this.exceptionList = response.rows
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
      this.$router.replace(`/exception/exceptionRecordAdd`)
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        exrId: undefined,
        exrCode: undefined,
        exsCode: undefined,
        exCode: undefined,
        exrDesc: undefined,
        exrParam: undefined,
        exrStat: "1",
        exrLevel: undefined,
        exrUserReport: undefined,
        exrUserHandle: undefined,
        exrUserFinish: undefined,
        exrUserResp: undefined,
        exrDuration: undefined,
        exrImpactFactor: undefined,
        exrImpactLevel: undefined,
        exrDelete: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
      };
      this.paramList = []
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.parseParamObject()) {
            this.$modal.msgWarning("请完整填写参数");
            return
          }
          this.buttonLoading = true;
          if (this.form.exrId != null) {
            updateExceptionRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionRecord(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.$router.replace(`/exception/exceptionRecordAdd?exrId=${response.data.exrId}`)
              this.form = response.data
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /**
     * 删除指定的定制详情
     * @param {any} scope 表格行信息
     * @author YangZY
     * @date 20250507
     */
    deleteParam(scope) {
      this.paramList.splice(scope.$index, 1)
    },
    /**
     * 添加一条定制详情
     * @author YangZY
     * @date 20250507
     */
    addParam() {
      this.paramList.push({
        key: '',
        val: ''
      })
    },
    /**
     * 参数JSON转换为List
     * @param {string} str 待转换字符串
     * @author YangZY
     * @date 20250507
     */
    parseParamString(str) {
      if (str) {
        const json = JSON.parse(str)
        this.paramList = []
        Object.keys(json).forEach(key => {
          this.paramList.push({
            key: key,
            val: json[key]
          })
        })
      }
    },
    /**
     * 参数List转换为JSON
     * @returns 是否存在不合法记录
     * @author YangZY
     * @date 20250423
     */
    parseParamObject() {
      const result = {}
      let success = true
      this.paramList.forEach(param => {
        if (param.key.length > 0 && param.val.length > 0) {
          result[param.key] = param.val
        } else {
          success = false
        }
      })
      this.form.exrParam = JSON.stringify(result)
      return success
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
