<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="110px">
      <el-form-item label="客户等级标识" prop="cllLabel">
        <el-input
          v-model="queryParams.cllLabel"
          placeholder="请输入客户等级标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户等级名称" prop="cllName">
        <el-input
          v-model="queryParams.cllName"
          placeholder="请输入客户等级名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="cllDelete">
        <el-input
          v-model="queryParams.cllDelete"
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
          v-hasPermi="['system:clientLevel:add']"
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
          v-hasPermi="['system:clientLevel:edit']"
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
          v-hasPermi="['system:clientLevel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:clientLevel:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="clientLevelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="客户等级ID" align="center" prop="cllId" v-if="true"/>
      <el-table-column label="客户等级编码" align="center" prop="cllCode" />
      <el-table-column label="客户等级标识" align="center" prop="cllLabel" />
      <el-table-column label="客户等级名称" align="center" prop="cllName" />
      <!-- <el-table-column label="已删除" align="center" prop="cllDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="cllDesc" />  -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:clientLevel:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-notebook-1"
            @click="handlePreferential(scope.row)"
          >优惠策略</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:clientLevel:remove']"
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

    <!-- 添加或修改客户等级对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="客户等级标识" prop="cllLabel">
          <el-input v-model="form.cllLabel" placeholder="请输入客户等级标识" />
        </el-form-item>
        <el-form-item label="客户等级名称" prop="cllName">
          <el-input v-model="form.cllName" placeholder="请输入客户等级名称" />
        </el-form-item>
        <el-form-item label="描述" prop="cllDesc">
          <el-input v-model="form.cllDesc" type="textarea" placeholder="请输入内容" />
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
import { listClientLevel, getClientLevel, delClientLevel, addClientLevel, updateClientLevel } from "@/api/system/clientLevel";

export default {
  name: "ClientLevel",
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
      // 客户等级表格数据
      clientLevelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cllLabel: undefined,
        cllName: undefined,
        cllDelete: 0
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cllId: [
          { required: true, message: "客户等级ID不能为空", trigger: "blur" }
        ],
        cllLabel: [
          { required: true, message: "客户等级标识不能为空", trigger: "blur" }
        ],
        cllName: [
          { required: true, message: "客户等级名称不能为空", trigger: "blur" }
        ],
        cllDelete: [
          { required: true, message: "已删除不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  activated() {
    this.getList();
  },
  methods: {
    /** 查询客户等级列表 */
    getList() {
      this.loading = true;
      listClientLevel(this.queryParams).then(response => {
        this.clientLevelList = response.rows;
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
        cllId: undefined,
        cllLabel: undefined,
        cllName: undefined,
        cllDelete: undefined,
        cllDesc: undefined,
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
      this.ids = selection.map(item => item.cllId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户等级";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const cllId = row.cllId || this.ids
      getClientLevel(cllId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改客户等级";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.cllId != null) {
            updateClientLevel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addClientLevel(this.form).then(response => {
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
      const cllIds = row.cllId || this.ids;
      this.$modal.confirm('是否确认删除客户等级编号为"' + cllIds + '"的数据项？').then(() => {
        this.loading = true;
        return delClientLevel(cllIds);
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
      this.download('system/clientLevel/export', {
        ...this.queryParams
      }, `clientLevel_${new Date().getTime()}.xlsx`)
    },
    // 查看对应的优惠策略
    handlePreferential(row) {
      this.$router.push(`/client/clientLevelPreferential?cllCode=${row.cllCode}`)
    }
  }
};
</script>
<style scoped>
.el-select{
 width: 100%;
}
.el-date-editor{
  width: 100%;
}
</style>
