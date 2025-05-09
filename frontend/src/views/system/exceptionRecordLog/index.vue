<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="异常记录" prop="exrCode">
        <el-input v-model="queryParams.exrCode" placeholder="请输入异常记录" :disabled="mode === 1" />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="exrlDelete">
        <el-input
          v-model="queryParams.exrlDelete"
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
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:exceptionRecordLog:add']"
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
          v-hasPermi="['system:exceptionRecordLog:edit']"
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
          v-hasPermi="['system:exceptionRecordLog:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionRecordLog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionRecordLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="处理日志ID" align="center" prop="exrlId" v-if="true"/>
      <el-table-column label="处理日志编码" align="center" prop="exrlCode" />
      <el-table-column label="所属异常记录" align="center" prop="exrCode" :show-overflow-tooltip="true" />
      <el-table-column label="所属处理任务" align="center" prop="exrlTask" :show-overflow-tooltip="true" />
      <el-table-column label="处理人" align="center" prop="exrlUserHandle">
        <template slot-scope="scope">
          {{ scope.row.exrlUserHandle && (userList.find(ele => ele.userId === scope.row.exrlUserHandle).userName || '') }}
        </template>
      </el-table-column>
      <el-table-column label="处理时间" align="center" prop="exrlTime" />
      <el-table-column label="处理意见" align="center" prop="exrlResult" />
      <!-- <el-table-column label="已删除" align="center" prop="exrlDelete" /> -->
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exceptionRecordLog:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exceptionRecordLog:remove']"
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

    <!-- 添加或修改异常处理日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="异常记录" prop="exrCode">
          <el-select v-model="form.exrCode" placeholder="请选择所属异常记录" :disabled="mode === 1">
            <el-option
             v-for="option in exceptionRecordList"
             :key="option.exrCode"
             :label="option.exrCode"
             :value="option.exrCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="处理人" prop="exrlUserHandle">
          <el-select
              v-model="form.exrlUserHandle"
              placeholder="请选择处理人"
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
        <el-form-item label="处理时间" prop="exrlTime">
          <el-input v-model="form.exrlTime" placeholder="请输入处理时间" />
        </el-form-item>
        <el-form-item label="处理意见" prop="exrlResult">
          <el-input v-model="form.exrlResult" placeholder="请输入处理意见" />
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
import { listExceptionRecordLog, getExceptionRecordLog, delExceptionRecordLog, addExceptionRecordLog, updateExceptionRecordLog } from "@/api/system/exceptionRecordLog";import { listExceptionRecord } from "@/api/system/exceptionRecord";
import { listUser } from "@/api/system/user";

export default {
  name: "ExceptionRecordLog",
  props: {
    procInsId: {
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
      // 异常处理日志表格数据
      exceptionRecordLogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        exrlCode: undefined,
        exrCode: undefined,
        exrlDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        exrlId: [
          { required: true, message: "处理日志ID不能为空", trigger: "blur" }
        ],
        exrCode: [
          { required: true, message: "所属异常记录不能为空", trigger: "blur" }
        ],
        exrlUserHandle: [
          { required: true, message: "处理人不能为空", trigger: "blur" }
        ],
        exrlTime: [
          { required: true, message: "处理时间不能为空", trigger: "blur" }
        ],
        exrlResult: [
          { required: true, message: "处理意见不能为空", trigger: "blur" }
        ],
      },
      // 异常记录列表
      exceptionRecordList: [],
      // 用户列表
      userList: [],
      // 1-按照异常记录筛选处理日志
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    if (this.procInsId) {
      this.mode = 1
    } else {
      this.mode = 0
    }
    await this.getUserList();
    await this.getExceptionRecordList();
    this.getList();
  },
  async activated() {
    if (this.procInsId) {
      this.mode = 1
    } else {
      this.mode = 0
    }
    await this.getUserList();
    await this.getExceptionRecordList();
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
    // 获取异常记录列表
    getExceptionRecordList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listExceptionRecord().then(response => {
          this.exceptionRecordList = response.rows
          if (this.mode === 1) {
            // 设置筛选
            this.queryParams.exrCode = this.procInsId
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询异常处理日志列表 */
    getList() {
      this.loading = true;
      listExceptionRecordLog(this.queryParams).then(response => {
        this.exceptionRecordLogList = response.rows;
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
        exrlId: undefined,
        exrlCode: undefined,
        exrCode: undefined,
        exrlUserHandle: undefined,
        exrlTime: undefined,
        exrlResult: undefined,
        exrlDelete: undefined,
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
      this.queryParams.exrCode = this.procInsId
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.exrlId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.mode === 1) {
        this.form.exrCode = this.procInsId
      }
      this.open = true;
      this.title = "添加异常处理日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const exrlId = row.exrlId || this.ids
      getExceptionRecordLog(exrlId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常处理日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.exrlId != null) {
            updateExceptionRecordLog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionRecordLog(this.form).then(response => {
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
      const exrlIds = row.exrlId || this.ids;
      this.$modal.confirm('是否确认删除异常处理日志编号为"' + exrlIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionRecordLog(exrlIds);
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
      this.download('system/exceptionRecordLog/export', {
        ...this.queryParams
      }, `exceptionRecordLog_${new Date().getTime()}.xlsx`)
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
