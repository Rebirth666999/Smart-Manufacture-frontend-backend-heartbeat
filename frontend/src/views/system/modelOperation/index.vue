<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选模型操作`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="moName">
        <el-input
          v-model="queryParams.moName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备模型" prop="emId">
        <el-select
          v-model="queryParams.emId"
          placeholder="请选择设备模型"
          @keyup.enter.native="handleQuery"
          clearable
          :disabled="mode !== 0"
        >
          <el-option
            v-for="item in equipmentModelList"
            :key="item.emId"
            :label="item.emName"
            :value="item.emId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="moDelete">
        <el-input
          v-model="queryParams.moDelete"
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
      <el-col :span="1.5" v-if="mode !== 2">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:modelOperation:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5" v-if="mode !== 2">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:modelOperation:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5" v-if="mode !== 2">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:modelOperation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:modelOperation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="modelOperationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="模型操作ID" align="center" prop="moId" v-if="true"/>
      <el-table-column label="名称" align="center" prop="moName" />
      <el-table-column label="所属设备模型" align="center" prop="emId">
        <template slot-scope="scope">
          {{ equipmentModelList.find(ele => ele.emId === scope.row.emId).emName || '' }}
        </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="moDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width"  v-if="mode !== 2">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:modelOperation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:modelOperation:remove']"
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

    <!-- 添加或修改设备模型操作对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="moName">
          <el-input v-model="form.moName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="设备模型" prop="emId">
          <el-select
            v-model="form.emId"
            placeholder="请选择设备模型"
            :disabled="mode !== 0"
          >
            <el-option
              v-for="item in equipmentModelList"
              :key="item.emId"
              :label="item.emName"
              :value="item.emId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="moDesc">
          <el-input v-model="form.moDesc" type="textarea" placeholder="请输入内容" />
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
import { listModelOperation, getModelOperation, delModelOperation, addModelOperation, updateModelOperation } from "@/api/system/modelOperation";
import { listEquipmentModel } from "@/api/system/equipmentModel";

export default {
  name: "ModelOperation",
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
      // 设备模型操作表格数据
      modelOperationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        emId: this.$route.query.emId,
        moName: undefined,
        moDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        moId: [
          { required: true, message: "模型操作ID不能为空", trigger: "blur" }
        ],
        emId: [
          { required: true, message: "所属设备模型不能为空", trigger: "change" }
        ],
        moName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
      },
      // 设备模型列表
      equipmentModelList: [],
      // 1-按照模型查看模型操作（模型未发布，操作可编辑）
      // 2-按照模型查看模型操作（不可编辑）
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.emId) {
      this.mode = 1
    }
    await this.getEquipmentModelList();
    this.getList();
  },
  methods: {
    // 查询设备模型列表
    getEquipmentModelList() {
      listEquipmentModel().then(response => {
        this.equipmentModelList = response.rows;
        if (this.mode === 1) {
          let model = response.rows.find(ele => ele.emId === this.$route.query.emId)
          // 构建筛选提示文本
          this.hint = "设备模型 "
          this.hint += model.emName
          this.hint += " "
          // 检查模型状态
          if (model.emStat !== '1') {
            this.mode = 2
          }
        }
      });
    },
    /** 查询设备模型操作列表 */
    getList() {
      this.loading = true;
      listModelOperation(this.queryParams).then(response => {
        this.modelOperationList = response.rows;
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
        moId: undefined,
        emId: undefined,
        moName: undefined,
        moDelete: undefined,
        moDesc: undefined,
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
      this.ids = selection.map(item => item.moId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.mode === 1) {
        this.form.emId = this.$route.query.emId
      }
      this.open = true;
      this.title = "添加设备模型操作";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const moId = row.moId || this.ids
      getModelOperation(moId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改设备模型操作";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.moId != null) {
            updateModelOperation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addModelOperation(this.form).then(response => {
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
      const moIds = row.moId || this.ids;
      this.$modal.confirm('是否确认删除设备模型操作编号为"' + moIds + '"的数据项？').then(() => {
        this.loading = true;
        return delModelOperation(moIds);
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
      this.download('system/modelOperation/export', {
        ...this.queryParams
      }, `modelOperation_${new Date().getTime()}.xlsx`)
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