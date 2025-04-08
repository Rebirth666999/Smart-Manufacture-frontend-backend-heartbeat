<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选客户等级对应的优惠策略`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户等级" prop="cllCode">
       <el-select v-model="queryParams.cllCode" placeholder="请输入客户等级" clearable :disabled="mode === 1">
        <el-option
         v-for="option in clientLevelList"
         :key="option.cllCode"
         :label="option.cllLabel"
         :value="option.cllCode">
        </el-option>
       </el-select>
      </el-form-item>
      <el-form-item label="优惠策略" prop="cpCode">
       <el-select v-model="queryParams.cpCode" placeholder="请选择优惠策略" clearable :disabled="mode === 2">
        <el-option
         v-for="option in clientPreferentialList"
         :key="option.cpCode"
         :label="option.cpName"
         :value="option.cpCode">
        </el-option>
       </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="clpDelete">
        <el-input
          v-model="queryParams.clpDelete"
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
          v-hasPermi="['system:clientLevelPreferential:add']"
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
          v-hasPermi="['system:clientLevelPreferential:edit']"
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
          v-hasPermi="['system:clientLevelPreferential:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:clientLevelPreferential:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="clientLevelPreferentialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="对应关系ID" align="center" prop="clpId" v-if="true"/>
      <el-table-column label="对应关系编码" align="center" prop="clpCode" />
      <el-table-column label="客户等级" align="center" prop="cllCode" >
      <template slot-scope="scope">
        {{ clientLevelList.find(ele => ele.cllCode === scope.row.cllCode).cllLabel || '' }}
       </template>
      </el-table-column>
      <el-table-column label="优惠策略" align="center" prop="cpCode" >
       <template slot-scope="scope">
        {{ clientPreferentialList.find(ele => ele.cpCode === scope.row.cpCode).cpName || '' }}
       </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="clpDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:clientLevelPreferential:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:clientLevelPreferential:remove']"
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

    <!-- 添加或修改关联-客户等级对应的优惠策略对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户等级" prop="cllCode">
          <el-select v-model="form.cllCode" placeholder="请选择客户等级" :disabled="mode === 1">
            <el-option
             v-for="option in clientLevelList"
             :key="option.cllCode"
             :label="option.cllLabel"
             :value="option.cllCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="优惠策略" prop="cpCode">
         <el-select v-model="form.cpCode" placeholder="请选择优惠策略" :disabled="mode === 2">
            <el-option
              v-for="option in clientPreferentialList"
              :key="option.cpCode"
              :label="option.cpName"
              :value="option.cpCode">
            </el-option>
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
import { listClientLevelPreferential, getClientLevelPreferential, delClientLevelPreferential, addClientLevelPreferential, updateClientLevelPreferential } from "@/api/system/clientLevelPreferential";
import { listClientLevel } from "@/api/system/clientLevel";
import { listClientPreferential } from "@/api/system/clientPreferential";

export default {
  name: "ClientLevelPreferential",
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
      // 关联-客户等级对应的优惠策略表格数据
      clientLevelPreferentialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 客户列表
      clientLevelList:[],
      // 优惠政策列表
      clientPreferentialList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        clpCode: undefined,
        cllCode: undefined,
        cpCode: undefined,
        clpDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        clpId: [
          { required: true, message: "对应关系ID不能为空", trigger: "blur" }
        ],
        cllCode: [
          { required: true, message: "客户等级不能为空", trigger: "blur" }
        ],
        cpCode: [
          { required: true, message: "优惠策略不能为空", trigger: "blur" }
        ],
        clpDelete: [
          { required: true, message: "已删除不能为空", trigger: "blur" }
        ],
      },
      // 1-按客户等级查看优惠策略
      // 2-按优惠策略查看客户等级
      mode: 0,
      // 顶部提示文本
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.cllCode) {
      this.mode = 1
    } else if (this.$route.query.cpCode) {
      this.mode = 2
    }
    await this.getClientLevelList();
    await this.getClientPreferentialList();
    this.getList();
  },
  async activated() {
    if (this.$route.query.cllCode) {
      this.mode = 1
    } else if (this.$route.query.cpCode) {
      this.mode = 2
    } else {
      this.mode = 0
    }
    await this.getClientLevelList();
    await this.getClientPreferentialList();
    this.getList();
  },
  methods: {
    // 查询客户等级列表
    getClientLevelList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listClientLevel().then(response => {
          this.clientLevelList = response.rows;
          if (this.mode === 1) {
            let clientLevel = response.rows.find(ele => ele.cllCode === this.$route.query.cllCode)
            // 构造提示文本
            this.hint = "客户等级 "
            this.hint += clientLevel.cllLabel
            this.hint += " "
            // 设置筛选
            this.queryParams.cllCode = clientLevel.arCode
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false;
        })
      })
    },
    // 查询优惠政策列表
    getClientPreferentialList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listClientPreferential().then(response => {
          this.clientPreferentialList = response.rows;
          if (this.mode === 2) {
            let clientPreferential = response.rows.find(ele => ele.cpCode === this.$route.query.cpCode)
            // 构造提示文本
            this.hint = "优惠策略 "
            this.hint += clientPreferential.cpName
            this.hint += " "
            // 设置筛选
            this.queryParams.cpCode = clientLevel.cpCode
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false;
        })
      })
    },
    /** 查询关联-客户等级对应的优惠策略列表 */
    getList() {
      this.loading = true;
      listClientLevelPreferential(this.queryParams).then(response => {
        this.clientLevelPreferentialList = response.rows;
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
        clpId: undefined,
        clpCode: undefined,
        cllCode: undefined,
        cpCode: undefined,
        clpDelete: undefined,
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
      this.ids = selection.map(item => item.clpId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.mode === 1) {
        this.form.cllCode = this.$route.query.cllCode
      } else if (this.mode === 2) {
        this.form.cpCode = this.$route.query.cpCode
      }
      this.open = true;
      this.title = "添加客户等级对应的优惠策略";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const clpId = row.clpId || this.ids
      getClientLevelPreferential(clpId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改客户等级对应的优惠策略";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.clpId != null) {
            updateClientLevelPreferential(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addClientLevelPreferential(this.form).then(response => {
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
      const clpIds = row.clpId || this.ids;
      this.$modal.confirm('是否确认删除客户等级对应的优惠策略编号为"' + clpIds + '"的数据项？').then(() => {
        this.loading = true;
        return delClientLevelPreferential(clpIds);
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
      this.download('system/clientLevelPreferential/export', {
        ...this.queryParams
      }, `clientLevelPreferential_${new Date().getTime()}.xlsx`)
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
