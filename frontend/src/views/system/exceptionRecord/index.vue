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
      <el-col :span="1.5" > 
          <el-button
            type="info"
            plain
            icon="el-icon-upload"
            size="mini"
            @click="handleimg"

          >上传图片</el-button>
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
      <el-table-column label=" 操 作 " align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          
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
              @click="getkg(scope.row)"
              v-hasPermi="['system:exceptionRecord:edit']"
              v-show="scope.row.exrStat === '4'"
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
              v-show="scope.row.exrStat === '2'"
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
            <el-button
              size="mini"
              type="text"
              icon="el-icon-document"
              @click="autoAddLifeCycle(scope.row)"
              v-show="scope.row.exrStat === '5'"
              >生成生命周期
          </el-button>
          <el-button
          size="mini"
          type="text"
          icon="el-icon-play"
          @click="handleStartLifeCycle(scope.row)"
          v-show="scope.row.exrStat === '6'"
          >
          启动生命周期
          </el-button>
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

  

    <!--  查看异常图片及其详情信息的对话框  -->
    <el-dialog :title="title" :visible.sync="open1" width="700px" append-to-body>
      <!-- 1. 顶部大图 -->
      <div style="text-align:center;margin-bottom:16px;">
        <img
          :src="form.exrImg"
          style="width:100%;max-height:260px;object-fit:contain;border-radius:6px;"
        />
      </div>
      <!-- 2. 信息列表 -->
      <el-descriptions :column="1" border>
        <el-descriptions-item label="异常源">
          {{( exceptionSourceList.find(ele => ele.exsCode === form.exsCode)||{}).exsName || '' }}
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
          v-model="this.form.exrPro" >
        {{ this.form.exrPro }}
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel1" type="primary">关 闭</el-button>
      </div>
    </el-dialog>



  </div>
</template>

<script>
import { listExceptionRecord, getExceptionRecord, delExceptionRecord, addExceptionRecord, updateExceptionRecord 
  ,saveDescToKnowledge,sendimg,createComplexUserTaskFlow,getKG ,startLifeCycle} from "@/api/system/exceptionRecord";
import { listUser } from "@/api/system/user";
import { listException } from "@/api/system/exception";
import { listExceptionSource } from "@/api/system/exceptionSource";
import{addExceptionLifecycle,listExceptionLifecycle} from "@/api/system/exceptionLifecycle";
export default {
  name: "ExceptionRecord",
  dicts: ['ices_exception_record_status', 'ices_exception_record_level', 'ices_exception_record_impact_level'],
  data() {
    return {
      //知识库返回信息
      knowledgeResponse: null,
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
        exrId: undefined,
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
    handleStartLifeCycle(row){
          const params = {
        exrId: row.exrId,
        exrCode: row.exrCode,
        exsCode: row.exsCode,
        exCode: row.exCode
    };
      console.log("生命周期启动响应:", params);
      startLifeCycle(params).then(response=>{
        this.$modal.msgSuccess("生命周期已启动");
        

      console.log("响应文本内容:", response);
      }).catch(error=>{
        this.$modal.msgError("生命周期启动失败: " + error.message);
      })

    },
async getkg(row) {
  try {
const message = this.$message({
  message: '正在查询知识库，请稍候...',
  type: 'info',
  duration: 0, // 不自动关闭
  showClose: true
});;
    let form=row
    // 第一步：获取异常名称
    const response = await listException({ exCode: row.exCode });
    console.log("异常查询响应:", response);
    
    if (!response.rows || response.rows.length === 0) {
      this.$modal.msgError("未找到对应的异常信息");
      return;
    }
    
    const exName = response.rows[0].exName;
    console.log("异常名称:", exName);
    
    if (!exName) {
      this.$modal.msgError("异常名称为空，无法查询知识图谱");
      return;
    }
    
    // 第二步：调用知识图谱API
    const kgResponse = await getKG(row.exrDesc, exName);
    console.log("知识图谱响应:", kgResponse);
    
 // 第三步：正确解析Response对象
    if (kgResponse.ok) {
      // 先获取响应文本
      const responseText = await kgResponse.text();
      console.log("响应文本内容:", responseText);
      
      
        // 尝试解析为JSON
        const responseData = JSON.parse(responseText);
        console.log("解析后的数据:", responseData);
        form.exrPro=JSON.stringify(responseData.solutions)
        // 第四步：提取并打印处理方案
        if (responseData && responseData.solutions) {
          console.log("=== 知识图谱处理方案 ===");
          responseData.solutions.forEach((solution, index) => {
            console.log(`方案 ${index + 1}:`);
            console.log(`- 异常处理方案: ${solution.异常处理方案}`);
            console.log(`- 涉及到部门: ${solution.涉及到部门}`);
            console.log("---");
          });}}

      form.exrStat="5"  // 更新状态为已上传知识库
      form.pageNum=1
      form.pageSize=10
      form.exrUserHandle="admin"
      form.exrUserFinish="admin"
      form.exrUserResp="admin"
      // 第四步：更新异常记录
      const updateResult = await updateExceptionRecord(form);
      console.log("更新异常记录:", updateResult);
    message.close();
      this.$modal.msgSuccess("知识库内容已更新");
      this.getList();
          
  } catch (error) {
    console.error("获取知识图谱失败:", error);
    
    if (error.message.includes('异常名称不能为空')) {
      this.$modal.msgError("异常信息不完整，无法查询知识图谱");
    } else if (error.message.includes('Failed to fetch')) {
      this.$modal.msgError("知识图谱服务器连接失败，请检查网络");
    } else {
      this.$modal.msgError("知识库内容更新失败: " + error.message);
    }
  }
},
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
        exrPro: undefined,
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
sendToKnowledge(row){
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
      console.log('完整响应结构:', JSON.stringify(data, null, 2))
      // 检查API返回状态
      if (data.code !== 0) {
        throw new Error(data.msg || '调用API失败')}

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



autoAddLifeCycle(row){
const autoLifeCycle = {//新增异常生命周期的字段
              //exlId,exlCode
              exrCode: row.exrCode,
              exCode: row.exCode,
              exsCode: row.exsCode,
              exlSnapshot:"",
              exlModelId:"",
              exlModelKey:"",
              exlDelete: 0,
              exlDesc: "自动生成",
            };
            console.log("自动添加异常生命周期", JSON.stringify(autoLifeCycle));
              addExceptionLifecycle(autoLifeCycle).then(response => {
                console.log("新增异常生命周期成功", response);
                this.open = false;
                this.getList();
                listExceptionLifecycle().then(response => {
              const exlId = response.rows.find(ele => ele.exrCode === row.exrCode ).exlId//寻找新建的异常生命周期的id
              console.log("excode", row.exrCode);
              console.log("exlId", exlId);
            createComplexUserTaskFlow(exlId,row.exrCode,row.exrPro);//生成对应xml
            getExceptionRecord(row.exrId).then(response =>{
              this.form = response.data;
              this.form.exrStat = "6";//更改异常上报记录状态
              updateExceptionRecord(this.form).then(response => {
                this.$modal.msgSuccess("异常生命周期已生成");
                this.getList();
              });
            })
            })
              }).finally(() => {
                this.buttonLoading = false;
                this.reset();
              });
            },



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
// 处理图片上传
      handleimg() {
const imgsrc="/test1.jpg"
    sendimg(imgsrc).then(response => {
        console.log('图片上传:', response);


    // 自动添加异常记录的逻辑
    // const autoRecord = {
    //   exrStat: "1",
    //   exsCode:"ExceptionSource-00005",
    //   exCode:"Exception-00004",
    //   exrDesc:"测试图片上传"+response.final_result.anomaly_description,
    //   exrLevel:"3",
    //   exrCdate:"",
    //   exrUserReport:"admin",
    //   exrUserHandle:"admin",
    //   exrUserResp:"admin",
    //   exrImpactLevel:"1",
    //   exrImpactFactor:"3",
    //   exrImg:"/frame_000150.jpg",
    //   exrParam:'{"orCode": "Order-00003", "orCodeOrgn": "Order-00679"}',
    //   exrDelete: 0,
    //   mtCode:"ManufactureTask-00023"
    // };


    // addExceptionRecord(autoRecord).then(response => {
    //           this.$modal.msgSuccess("新增成功");
    //           this.$router.replace(`/exception/exceptionRecordAdd?exrId=${response.data.exrId}`)
    //           this.form = response.data})
    }).catch(error => {
      console.error('图片上传失败:', error);
      this.$message.error('图上传失败: ' + error.message);
    });
  },

  autoAddExpectionRecord() {


  }},


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

