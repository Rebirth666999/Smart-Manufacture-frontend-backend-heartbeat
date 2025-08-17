<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选异常生命周期`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>异常生命周期信息</div>
        </div>
      </div>
      <div>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="所属异常" prop="exCode">
            <el-select v-model="queryParams.exCode" placeholder="请选择所属异常" :disabled="mode === 1" clearable>
              <el-option
               v-for="option in exceptionList"
               :key="option.exCode"
               :label="option.exName"
               :value="option.exCode">
              </el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="已删除" prop="exlDelete">
            <el-input
              v-model="queryParams.exlDelete"
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
              v-hasPermi="['system:exceptionLifecycle:add']"
              :disabled="exceptionLifecycleList.length > 0"
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
              v-hasPermi="['system:exceptionLifecycle:edit']"
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
              v-hasPermi="['system:exceptionLifecycle:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:exceptionLifecycle:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
    
        <el-table
          v-loading="loading"
          :data="exceptionLifecycleList"
          @current-change="handleCurrentChange"
          highlight-current-row
        >
          <el-table-column label="选择" width="55" align="center">
            <template slot-scope="scope">
              <el-radio :value="scope.row.exlId === idSelect" :label="true" />
            </template>
          </el-table-column>
          <el-table-column label="生命周期ID" align="center" prop="exlId" v-if="true"/>
          <el-table-column label="生命周期编码" align="center" prop="exlCode" />
          <el-table-column label="所属异常" align="center" prop="exCode">
            <template slot-scope="scope">
              {{ exceptionList.find(ele => ele.exCode === scope.row.exCode).exName || '' }}
            </template>
          </el-table-column>
          <el-table-column label="异常上报编码" align="center" prop="exrCode" />
          <el-table-column label="模型ID" align="center" prop="exlModelId" />
          <el-table-column label="模型key" align="center" prop="exlModelKey" />
          <!-- <el-table-column label="已删除" align="center" prop="exlDelete" /> -->
          <!-- <el-table-column label="描述" align="center" prop="exlDesc" /> -->
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:exceptionLifecycle:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-brush"
                @click="handleDesign(scope.row)"
              >设计</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-s-help"
                @click="handleDeploy(scope.row)"
              >部署</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:exceptionLifecycle:remove']"
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
    
    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>异常生命周期版本信息</div>
        </div>
      </div>
      <exception-lifecycle-version v-if='idSelect' :key="idSelect" :exlCode="codeSelect" />
      <el-empty v-else description="选中参数模板后即可查看详细信息" />
    </el-card>

    <!-- 添加或修改异常生命周期对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属异常" prop="exCode">
          <el-select v-model="form.exCode" placeholder="请选择异常" :disabled="mode === 1">
            <el-option
             v-for="option in exceptionList"
             :key="option.exCode"
             :label="option.exName"
             :value="option.exCode">
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="缩略图" prop="exlSnapshot">
          <el-input v-model="form.exlSnapshot" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="模型ID" prop="exlModelId">
          <el-input v-model="form.exlModelId" placeholder="请输入模型ID" />
        </el-form-item>
        <el-form-item label="模型key" prop="exlModelKey">
          <el-input v-model="form.exlModelKey" placeholder="请输入模型key" />
        </el-form-item> -->
        <el-form-item label="描述" prop="exlDesc">
          <el-input v-model="form.exlDesc" type="textarea" placeholder="请输入内容" />
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
import { listExceptionLifecycle, getExceptionLifecycle, delExceptionLifecycle, addExceptionLifecycle, updateExceptionLifecycle, deployExceptionLifecycle } from "@/api/system/exceptionLifecycle";
import { listException } from "@/api/system/exception";
import ExceptionLifecycleVersion from '@/views/system/exceptionLifecycleVersion';

export default {
  name: "ExceptionLifecycle",
  components: {
    ExceptionLifecycleVersion
  },
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
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
      // 异常生命周期表格数据
      exceptionLifecycleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        exlCode: undefined,
        exCode: undefined,
        exlDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        exlId: [
          { required: true, message: "生命周期ID不能为空", trigger: "blur" }
        ],
        exCode: [
          { required: true, message: "所属异常不能为空", trigger: "blur" }
        ],
      },
      // 异常列表
      exceptionList: [],
      // 1-按照异常筛选生命周期
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    if (this.$route.query.exCode) {
      this.mode = 1
    }
    await this.getExceptionList();
    this.getList();
  },
  async activated() {
    if (this.$route.query.exCode) {
      this.mode = 1
    } else {
      this.mode = 0
    }
    await this.getExceptionList();
    this.getList();
  },
  methods: {
    // 获取异常列表
    getExceptionList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listException().then(response => {
          this.exceptionList = response.rows
          if (this.mode === 1) {
            let exception = response.rows.find(ele => ele.exCode === this.$route.query.exCode)
            // 构造提示文本
            this.hint = "异常 "
            this.hint += exception.exName
            this.hint += " "
            // 设置筛选
            this.queryParams.exCode = exception.exCode
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询异常生命周期列表 */
    getList() {
      this.loading = true;
      listExceptionLifecycle(this.queryParams).then(response => {
        this.exceptionLifecycleList = response.rows;
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
        exlId: undefined,
        exlCode: undefined,
        exCode: undefined,
        exlSnapshot: undefined,
        exlModelId: undefined,
        exlModelKey: undefined,
        exlDelete: undefined,
        exlDesc: undefined,
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
      this.queryParams.exCode = this.$route.query.exCode
      this.handleQuery();
    },
    // 选中数据条目
    handleCurrentChange(current, old) {
      if (current) {
        this.idSelect = current.exlId
        this.codeSelect = current.exlCode
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.mode === 1) {
        this.form.exCode = this.$route.query.exCode
      }
      this.open = true;
      this.title = "添加异常生命周期";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const exlId = row.exlId || this.idSelect
      getExceptionLifecycle(exlId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常生命周期";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.exlId != null) {
            updateExceptionLifecycle(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionLifecycle(this.form).then(response => {
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
      const exlIds = row.exlId || this.idSelect;
      this.$modal.confirm('是否确认删除异常生命周期编号为"' + exlIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionLifecycle(exlIds);
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
      this.download('system/exceptionLifecycle/export', {
        ...this.queryParams
      }, `exceptionLifecycle_${new Date().getTime()}.xlsx`)
    },
    // 查看生命周期版本
    handleVersion(row) {
      this.$router.push(`/exception/exceptionLifecycleVersion?exlCode=${row.exlCode}`)
    },
    // 生命周期设计
    handleDesign(row) {
      this.$router.push(`/exception/exceptionLifecycleDesign?exlId=${row.exlId}`)
    },
    // 生命周期部署
    handleDeploy(row) {
      this.$modal.confirm('是否确认部署此异常生命周期的最新版本？').then(() => {
        const select = this.idSelect
        this.idSelect = undefined
        deployExceptionLifecycle(row).then(response => {
          this.$modal.msgSuccess("部署成功");
        }).finally(() => {
          this.idSelect = select
        })
      })
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
