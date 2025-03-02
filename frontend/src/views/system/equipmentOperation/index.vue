<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="对应模型操作ID" prop="moId">
        <el-input
          v-model="queryParams.moId"
          placeholder="请输入对应模型操作ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属设备ID" prop="eqId">
        <el-input
          v-model="queryParams.eqId"
          placeholder="请输入所属设备ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="eoDelete">
        <el-input
          v-model="queryParams.eoDelete"
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
          v-hasPermi="['system:equipmentOperation:add']"
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
          v-hasPermi="['system:equipmentOperation:edit']"
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
          v-hasPermi="['system:equipmentOperation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:equipmentOperation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentOperationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备操作ID" align="center" prop="eoId" v-if="true"/>
      <el-table-column label="对应模型操作ID" align="center" prop="moId" />
      <el-table-column label="所属设备ID" align="center" prop="eqId" />
      <el-table-column label="名称" align="center" prop="eoName" />
      <el-table-column label="执行时长" align="center" prop="eoExecTime" />
      <!-- <el-table-column label="已删除" align="center" prop="eoDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="eoDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:equipmentOperation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:equipmentOperation:remove']"
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

    <!-- 添加或修改设备操作对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="对应模型操作ID" prop="moId">
          <el-input v-model="form.moId" placeholder="请输入对应模型操作ID" />
        </el-form-item>
        <el-form-item label="所属设备ID" prop="eqId">
          <el-input v-model="form.eqId" placeholder="请输入所属设备ID" />
        </el-form-item>
        <el-form-item label="名称" prop="eoName">
          <el-input v-model="form.eoName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="执行时长" prop="eoExecTime">
          <el-input v-model="form.eoExecTime" placeholder="请输入执行时长" />
        </el-form-item>
        <el-form-item label="描述" prop="eoDesc">
          <el-input v-model="form.eoDesc" type="textarea" placeholder="请输入内容" />
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
import { listEquipmentOperation, getEquipmentOperation, delEquipmentOperation, addEquipmentOperation, updateEquipmentOperation } from "@/api/system/equipmentOperation";

export default {
  name: "EquipmentOperation",
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
      // 设备操作表格数据
      equipmentOperationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        moId: undefined,
        eqId: undefined,
        eoDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        eoId: [
          { required: true, message: "设备操作ID不能为空", trigger: "blur" }
        ],
        moId: [
          { required: true, message: "对应模型操作ID不能为空", trigger: "blur" }
        ],
        eqId: [
          { required: true, message: "所属设备ID不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询设备操作列表 */
    getList() {
      this.loading = true;
      listEquipmentOperation(this.queryParams).then(response => {
        this.equipmentOperationList = response.rows;
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
        eoId: undefined,
        moId: undefined,
        eqId: undefined,
        eoName: undefined,
        eoExecTime: undefined,
        eoDelete: undefined,
        eoDesc: undefined,
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
      this.ids = selection.map(item => item.eoId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备操作";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const eoId = row.eoId || this.ids
      getEquipmentOperation(eoId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改设备操作";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.eoId != null) {
            updateEquipmentOperation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addEquipmentOperation(this.form).then(response => {
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
      const eoIds = row.eoId || this.ids;
      this.$modal.confirm('是否确认删除设备操作编号为"' + eoIds + '"的数据项？').then(() => {
        this.loading = true;
        return delEquipmentOperation(eoIds);
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
      this.download('system/equipmentOperation/export', {
        ...this.queryParams
      }, `equipmentOperation_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
