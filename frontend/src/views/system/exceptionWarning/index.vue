<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
      <!-- <el-form-item label="已删除" prop="exwDelete">
        <el-input
          v-model="queryParams.exwDelete"
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
          v-hasPermi="['system:exceptionWarning:add']"
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
          v-hasPermi="['system:exceptionWarning:edit']"
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
          v-hasPermi="['system:exceptionWarning:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:exceptionWarning:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exceptionWarningList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="预警配置ID" align="center" prop="exwId" v-if="true"/>
      <el-table-column label="预警配置编码" align="center" prop="exwCode" />
      <el-table-column label="异常" align="center" prop="exCode">
        <template slot-scope="scope">
          {{ exceptionList.find(ele => ele.exCode === scope.row.exCode).exName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="异常源" align="center" prop="exsCode">
        <template slot-scope="scope">
          {{ exceptionSourceList.find(ele => ele.exsCode === scope.row.exsCode).exsName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="时间表达式" align="center" prop="exwFormula" />
      <el-table-column label="消息接收人" align="center" prop="exwRecv">
        <template slot-scope="scope">
          {{ scope.row.exwRecv && (userList.find(ele => ele.userId === scope.row.exwRecv).userName || '') }}
        </template>
      </el-table-column>
      <el-table-column label="消息通道" align="center" prop="exwTunnel" />
      <el-table-column label="消息模板" align="center" prop="exwTemplate" />
      <el-table-column label="是否触发源系统预警" align="center" prop="exwWarningOrgn">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_yn" :value="scope.row.exwWarningOrgn"/>
        </template>
      </el-table-column>
      <el-table-column label="是否触发源系统处理" align="center" prop="exwHandleOrgn">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_yn" :value="scope.row.exwHandleOrgn"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="exwDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:exceptionWarning:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:exceptionWarning:remove']"
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

    <!-- 添加或修改异常预警配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
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
        <el-form-item label="时间表达式" prop="exwFormula">
          <el-input v-model="form.exwFormula" placeholder="请输入时间表达式" />
        </el-form-item>
        <el-form-item label="消息接收人" prop="exwRecv">
          <el-select
            v-model="form.exwRecv"
            placeholder="请选择消息接收人"
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
        <el-form-item label="消息通道" prop="exwTunnel">
          <el-input v-model="form.exwTunnel" placeholder="请输入消息通道" />
        </el-form-item>
        <el-form-item label="消息模板" prop="exwTemplate">
          <el-input v-model="form.exwTemplate" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否触发源系统预警" prop="exwWarningOrgn">
          <el-select v-model="form.exwWarningOrgn" placeholder="请选择是否触发源系统预警">
            <el-option
              v-for="dict in dict.type.ices_yn"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否触发源系统处理" prop="exwHandleOrgn">
          <el-select v-model="form.exwHandleOrgn" placeholder="请选择是否触发源系统处理">
            <el-option
              v-for="dict in dict.type.ices_yn"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import { listExceptionWarning, getExceptionWarning, delExceptionWarning, addExceptionWarning, updateExceptionWarning } from "@/api/system/exceptionWarning";
import { listUser } from "@/api/system/user";
import { listException } from "@/api/system/exception";
import { listExceptionSource } from "@/api/system/exceptionSource";

export default {
  name: "ExceptionWarning",
  dicts: ['ices_yn'],
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
      // 异常预警配置表格数据
      exceptionWarningList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        exwCode: undefined,
        exCode: undefined,
        exsCode: undefined,
        exwWarningOrgn: undefined,
        exwHandleOrgn: undefined,
        exwDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        exwId: [
          { required: true, message: "预警配置ID不能为空", trigger: "blur" }
        ],
        exCode: [
          { required: true, message: "异常不能为空", trigger: "blur" }
        ],
        exsCode: [
          { required: true, message: "异常源不能为空", trigger: "blur" }
        ],
        exwFormula: [
          { required: true, message: "时间表达式不能为空", trigger: "blur" }
        ],
        exwRecv: [
          { required: true, message: "消息接收人不能为空", trigger: "blur" }
        ],
        exwTunnel: [
          { required: true, message: "消息通道不能为空", trigger: "blur" }
        ],
        exwTemplate: [
          { required: true, message: "消息模板不能为空", trigger: "blur" }
        ],
        exwWarningOrgn: [
          { required: true, message: "是否触发源系统预警不能为空", trigger: "change" }
        ],
        exwHandleOrgn: [
          { required: true, message: "是否触发源系统处理不能为空", trigger: "change" }
        ],
      },
      // 异常列表
      exceptionList: [],
      // 异常源列表
      exceptionSourceList: [],
      // 用户列表
      userList: []
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
    /** 查询异常预警配置列表 */
    getList() {
      this.loading = true;
      listExceptionWarning(this.queryParams).then(response => {
        this.exceptionWarningList = response.rows;
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
        exwId: undefined,
        exwCode: undefined,
        exCode: undefined,
        exsCode: undefined,
        exwFormula: undefined,
        exwRecv: undefined,
        exwTunnel: undefined,
        exwTemplate: undefined,
        exwWarningOrgn: undefined,
        exwHandleOrgn: undefined,
        exwDelete: undefined,
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
      this.ids = selection.map(item => item.exwId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加异常预警配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const exwId = row.exwId || this.ids
      getExceptionWarning(exwId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改异常预警配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.exwId != null) {
            updateExceptionWarning(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addExceptionWarning(this.form).then(response => {
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
      const exwIds = row.exwId || this.ids;
      this.$modal.confirm('是否确认删除异常预警配置编号为"' + exwIds + '"的数据项？').then(() => {
        this.loading = true;
        return delExceptionWarning(exwIds);
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
      this.download('system/exceptionWarning/export', {
        ...this.queryParams
      }, `exceptionWarning_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scope>
.el-select{
  width: 100%;
}
.el-date-editor{
  width: 100%;
}
</style>
