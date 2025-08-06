<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="异常源" prop="exsCode">
        <el-select v-model="queryParams.exsCode" placeholder="请选择异常源" clearable>
          <el-option
            v-for="option in exceptionSourceList"
            :key="option.exsCode"
            :label="option.exsName"
            :value="option.exsCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="异常" prop="exCode">
        <el-select v-model="queryParams.exCode" placeholder="请选择异常" clearable>
          <el-option
            v-for="option in exceptionList"
            :key="option.exCode"
            :label="option.exName"
            :value="option.exCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="exrStat">
        <el-select v-model="queryParams.exrStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_exception_record_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="等级" prop="exrLevel">
        <el-select v-model="queryParams.exrLevel" placeholder="请选择等级" clearable>
          <el-option
            v-for="dict in dict.type.ices_exception_record_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="exrDelete">
        <el-input
          v-model="queryParams.exrDelete"
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
          v-hasPermi="['system:exceptionRecord:add']"
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
          v-hasPermi="['system:exceptionRecord:edit']"
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
          v-hasPermi="['system:exceptionRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="异常记录ID" align="center" prop="exrId" v-if="true"  :show-overflow-tooltip="true" />
      <el-table-column label="异常记录编码" align="center" prop="exrCode" :show-overflow-tooltip="true" />
      <el-table-column label="异常源" align="center" prop="exsCode">
        <template slot-scope="scope">
          {{ exceptionSourceList.find(ele => ele.exsCode === scope.row.exsCode).exsName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="异常" align="center" prop="exCode">
        <template slot-scope="scope">
          {{ exceptionList.find(ele => ele.exCode === scope.row.exCode).exName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="exrDesc" :show-overflow-tooltip="true" />
      <el-table-column label="参数" align="center" prop="exrParam" :show-overflow-tooltip="true" />
      <el-table-column label="状态" width="100" align="center" prop="exrStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_exception_record_status" :value="scope.row.exrStat"/>
        </template>
      </el-table-column>
      <el-table-column label="等级" width="80" align="center" prop="exrLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_exception_record_level" :value="scope.row.exrLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="上报类型" width="90" align="center" prop="exrUserReport">
        <template slot-scope="scope">
          <el-tag type="warning" v-if="scope.row.exrUserReport === '信息系统'">自动上报</el-tag>
          <el-tag type="info" v-else>人工上报</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="异常上报人" align="center" prop="exrUserReport" />
      <el-table-column label="当前处理人" align="center" prop="exrUserHandle" />
      <el-table-column label="异常解除人" align="center" prop="exrUserFinish" />
      <el-table-column label="异常责任人" align="center" prop="exrUserResp" />
      <el-table-column label="上报时间" width="150" align="center" prop="exrCdate" />
      <el-table-column label="影响因子" width="80" align="center" prop="exrImpactFactor" />
      <el-table-column label="影响等级" width="80" align="center" prop="exrImpactLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_exception_record_impact_level" :value="scope.row.exrImpactLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="异常执行流程" align="center" prop="exrProcess" :show-overflow-tooltip="true" />
      <!-- <el-table-column label="已删除" align="center" prop="exrDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button-group>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleReference(scope.row)"
              v-hasPermi="['system:exceptionRecord:edit']"
            >查看详情</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-upload"
              @click="handleByJson(scope.row)"
              v-hasPermi="['system:exceptionRecord:edit']"
              v-show="scope.row.exrStat === '4' && !scope.row.exrPro"
            >上传知识库</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:exceptionRecord:edit']"
              v-show="scope.row.exrStat === '1'"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-finished"
              @click="handleStartConfirm(scope.row)"
              v-show="scope.row.exrStat === '1'"
            >开始确认</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              v-show="scope.row.exrStat === '2'"
              @click="handleConfirmPositive(scope.row)"
            >异常</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-close"
              v-show="scope.row.exrStat === '3'"
              @click="handleConfirmNegative(scope.row)"
            >非异常</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:exceptionRecord:remove']"
              v-show="scope.row.exrStat === '1'"
            >删除</el-button>
          </el-button-group>
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

    <!-- 添加或修改异常记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
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
        <el-col :span="24">
          <el-form-item label="参数" prop="exrParam">
            <el-input v-model="form.exrParam" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="exrStat">
            <el-select v-model="form.exrStat" placeholder="请选择状态">
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--  查看异常图片及其详情信息的对话框  -->
    <el-dialog :title="title" :visible.sync="open1" width="700px" append-to-body>
      <!-- 1. 顶部大图 -->
      <div style="text-align:center;margin-bottom:16px;">
        <img
          :src="form.exrImg || defaultImg"
          style="width:100%;max-height:260px;object-fit:cover;border-radius:6px;"
        />
      </div>
      <!-- 2. 信息列表 -->
      <el-descriptions :column="1" border>
        <el-descriptions-item label="异常源">
          {{ form.exsCode || '-' }}
        </el-descriptions-item>
        <el-descriptions-item label="异常记录人">
          {{ form.exrUserReport || '-' }}
        </el-descriptions-item>
        <el-descriptions-item label="异常时间">
          {{ form.exrCdate || '-' }}
        </el-descriptions-item>
        <el-descriptions-item label="异常类型">
{{ (exceptionList.find(ele => ele.exCode === form.exCode) || {}).exName  }}
        </el-descriptions-item>
        <el-descriptions-item label="描述信息">
          {{ form.exrDesc || '-' }}
        </el-descriptions-item>
        <!-- 3. 知识库解决方法展示 -->
        <el-descriptions-item
          label="知识库分析结果"
          v-if="form.exrPro"
         >
          <div style="background-color: #f5f7fa; padding: 12px; border-radius: 6px; line-height: 1.8; white-space: pre-line;">
            {{ form.exrPro }}
          </div>
        </el-descriptions-item>

      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel1" type="primary">关 闭</el-button>
      </div>

    </el-dialog>

  </div>
</template>

<script>
import { listExceptionRecord, getExceptionRecord, delExceptionRecord, addExceptionRecord, updateExceptionRecord ,saveDescToKnowledge,checkdetail,getdetail,saveKnowledgeToBackend} from "@/api/system/exceptionRecord";
import { listUser } from "@/api/system/user";
import { listException } from "@/api/system/exception";
import { listExceptionSource } from "@/api/system/exceptionSource";

export default {
  name: "ExceptionRecord",
  dicts: ['ices_exception_record_status', 'ices_exception_record_level', 'ices_exception_record_impact_level'],
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
      // 异常记录表格数据
      exceptionRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open1:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        exrCode: undefined,
        exsCode: undefined,
        exCode: undefined,
        exrStat: undefined,
        exrLevel: undefined,
        exrDelete: 0,
      },

      // 表单参数
      form: {},
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
        exrParam: [
          { required: true, message: "参数不能为空", trigger: "blur" }
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
      //异常图片
      defaultImg: 'https://via.placeholder.com/520x260?text=暂无图片',
      //存储每条记录的上传状态
      uploadStatus: {},
      // 存储各异常对应的知识库内容
      knowledgeContent: {},
      //记录是否能呈现
      update1: false,
      //记录是否能成功传后端并呈现
      uploadSuccessMap: {},
    };
  },
  async created() {
    await this.getUserList();
    await this.getExceptionList();
    await this.getExceptionSourceList();
    this.getList();
  },
  async activated() {
    await this.getUserList();
    await this.getExceptionList();
    await this.getExceptionSourceList();
    this.getList();
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
    /** 查询异常记录列表 */
    getList() {
      this.loading = true;
      listExceptionRecord(this.queryParams).then(response => {
        this.exceptionRecordList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancel1() {
      this.open1 = false;
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
        exrStat: undefined,
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
      this.ids = selection.map(item => item.exrId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push(`/exception/exceptionRecordAdd`)
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const exrId = row.exrId || this.ids
      this.$router.push(`/exception/exceptionRecordAdd?exrId=${exrId}`)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.exrId != null) {
            updateExceptionRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionRecord(this.form).then(response => {
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
      const exrIds = row.exrId || this.ids;
      this.$modal.confirm('是否确认删除异常记录编号为"' + exrIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionRecord(exrIds);
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
      this.download('system/exceptionRecord/export', {
        ...this.queryParams
      }, `exceptionRecord_${new Date().getTime()}.xlsx`)
    },
    /** 开始确认上报记录
     * @param {any} row 记录信息
     * @author YangZY
     * @date 20250508
     */
    handleStartConfirm(row) {
      this.$modal.confirm('是否开始确认异常上报记录？').then(() => {
        this.loading = true
        getExceptionRecord(row.exrId).then(response => {
          this.form = response.data
          this.form.exrStat = "2"
          updateExceptionRecord(this.form).then(response => {
            this.$modal.msgSuccess("已开始确认")
            this.getList()
            this.loading = false
          })
        })
      })
    },
    /** 确认上报记录非异常
     * @param {any} row 记录信息
     * @author YangZY
     * @date 20250508
     */
    handleConfirmNegative(row) {
      this.$modal.confirm('是否确认上报记录为非异常？').then(() => {
        this.loading = true
        getExceptionRecord(row.exrId).then(response => {
          this.form = response.data
          this.form.exrStat = "3"
          updateExceptionRecord(this.form).then(response => {
            this.$modal.msgSuccess("确认完成")
            this.getList()
            this.loading = false
          })
        })
      })
    },
    /** 确认上报记录为异常
     * @param {any} row 记录信息
     * @author YangZY
     * @date 20250508
     */
    handleConfirmPositive(row) {
      this.$modal.confirm('是否确认上报记录为异常？').then(() => {
        this.loading = true
        getExceptionRecord(row.exrId).then(response => {
          this.form = response.data
          console.log(row.exrId)
          console.log(this.form)
          this.form.exrStat = "4"
          updateExceptionRecord(this.form).then(response => {
            this.$modal.msgSuccess("确认完成，已启动异常处理")
            this.getList()
            this.loading = false
          })
        })
      })
    },
    /**
     * @param {any} row 记录信息
     * @author cuiyutong
     * @date 20250801
     */

// ...existing code...
handleByJson(row){
  getExceptionRecord(row.exrId).then(response => {
    this.form = response.data
    const descObj = this.form.exrDesc
    console.log(descObj)

    this.$message.info('正在同步到知识库...')

    saveDescToKnowledge(descObj, row.exrCode).then(response => {
      console.log('原始响应:', response)

      // 检查响应状态
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }

      // 解析 JSON 响应
      return response.json()
    }).then(data => {
      console.log('解析后的数据:', data)
      console.log('完整响应结构:', JSON.stringify(data, null, 2))

      // 检查API返回状态
      if (data.code !== 0) {
        throw new Error(data.msg || '调用API失败')
      }

      // 获取对话信息
      const conversationId = data.data.conversation_id  // 注意这里是 conversation_id
      const chatId = data.data.id  // 这里是 chat 的 id

      console.log('对话ID:', conversationId)
      console.log('聊天ID:', chatId)

      // 轮询检查聊天状态
      this.checkChatStatus(conversationId, chatId,row)

    }).catch(error => {
      console.error('调用知识库API失败:', error)
      this.$message.error('同步到知识库失败: ' + error.message)
    })
  }).catch(error => {
    console.error('获取异常记录失败:', error)
    this.$message.error('获取异常记录失败')
  })
},

/**
 * 检查聊天状态
 * @param {string} conversationId 对话ID
 * @param {string} chatId 聊天ID
 */
checkChatStatus(conversationId, chatId,row, maxRetries = 30, retryCount = 0) {
  if (retryCount >= maxRetries) {
    this.$message.error('检查状态超时，请稍后重试')
    return
  }

  console.log(`第${retryCount + 1}次检查状态...`)

  checkdetail(conversationId, chatId).then(response => {
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  }).then(data => {
    console.log('状态检查结果:', data)

    if (data.code !== 0) {
      throw new Error(data.msg || '检查状态失败')
    }

    const status = data.data.status
    console.log('当前状态:', status)

    if (status === 'completed') {
      // 聊天完成，获取详细结果
      this.$message.success('知识库同步完成！')
      this.getChatDetail(conversationId, chatId,row)
    } else if (status === 'failed') {
      // 聊天失败
      this.$message.error('知识库同步失败')
      console.error('聊天失败:', data.data)
    } else if (status === 'in_progress' || status === 'created') {
      // 聊天进行中，继续检查
      setTimeout(() => {
        this.checkChatStatus(conversationId, chatId,row, maxRetries, retryCount + 1)
      }, 2000) // 每2秒检查一次
    } else {
      // 其他状态
      console.log('未知状态:', status)
      setTimeout(() => {
        this.checkChatStatus(conversationId, chatId,row, maxRetries, retryCount + 1)
      }, 2000)
    }

  }).catch(error => {
    console.error('检查状态失败:', error)

    // 重试
    if (retryCount < maxRetries - 1) {
      setTimeout(() => {
        this.checkChatStatus(conversationId, chatId,row, maxRetries, retryCount + 1)
      }, 3000) // 出错时等待3秒再重试
    } else {
      this.$message.error('检查状态失败: ' + error.message)
    }
  })
},

/**
 * 获取聊天详细结果
 * @param {string} conversationId 对话ID
 * @param {string} chatId 聊天ID
 */
getChatDetail(conversationId, chatId, row) {
  console.log('获取聊天详细结果...')

  getdetail(conversationId, chatId).then(response => {
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  }).then(data => {
    console.log('========== 聊天详细结果 ==========')
    console.log('完整结果:', JSON.stringify(data, null, 2))

    if (data.code === 0) {
      //上传成功：标记当前行的上传状态为true,按钮隐藏,对话框中解决方案显现

      this.$set(this.uploadStatus, row.exrId, true)
      this.$message.success('已成功获取知识库分析结果，请点击查看详情')

      // 打印聊天消息
      if (data.data && data.data.messages&& data.data.messages.length > 0) {
       const targetMsg=data.data.messages.find(message=>
         message.role==='assistant'&&message.type==='answer'
       )
        if(targetMsg && targetMsg.content){
          const cleanContent = targetMsg.content.replace(/^"/, '').replace(/"$/, '');
          this.$set(this.knowledgeContent, row.exrId, cleanContent);
          this.$message.success('已获取知识库分析结果，可查看详情');
        }

        console.log('聊天消息:')
        data.data.messages.forEach((message, index) => {
          console.log(`消息${index + 1} [${message.role}]:`, message.content)
        })
      }
      const backendParams = {
        exrId: row.exrId, // 原始异常记录ID（用于关联）
        exrCode: row.exrCode, // 原始异常编码
        conversationId: conversationId, // 对话ID
        chatId: chatId, // 聊天ID
        data: data.data // 知识库返回的完整数据（包含messages、usage等）
      }
      saveKnowledgeToBackend(backendParams).then(() => {
        // ✅ 标记为成功
        this.$set(this.uploadSuccessMap, row.exrId, true);
        this.form.exrStat= '5' // 更新状态为已发送到知识库
      }).catch(() => {
        // ✅ 标记为失败
        this.$set(this.uploadSuccessMap, row.exrId, false);
      });
      // 打印使用统计
      if (data.data && data.data.usage) {
        console.log('Token使用情况:', data.data.usage)
      }

      this.$message.success('已成功获取知识库分析结果，请查看控制台')
    } else {
      console.error('获取结果失败:', data.msg)
      this.$message.error('获取结果失败: ' + data.msg)
    }

  }).catch(error => {
    console.error('获取聊天详细结果失败:', error)
    this.$message.error('获取详细结果失败: ' + error.message)
  })
},
// ...existing code...
    /** 查看详情按钮操作*/
    /** 确认上报记录为异常
     * @param {any} row 记录信息
     * @author cuiyutong
     * @date 20250801
     */
    handleReference(row){
      getExceptionRecord(row.exrId).then(response=>{
        this.form=response.data
        console.log(this.form.exrImg)
      })
      this.open1=true
      this.title="查看详情"
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
</style>

