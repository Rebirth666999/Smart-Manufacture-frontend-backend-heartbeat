<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选设备模型`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模型类型" prop="emtCode">
        <el-select
          v-model="queryParams.emtCode"
          placeholder="请选择模型类型"
          clearable
          :disabled="mode === 1"
        >
          <el-option
            v-for="item in equipmentModelTypeListFull"
            :key="item.emtCode"
            :label="item.emtName"
            :value="item.emtCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="emName">
        <el-input
          v-model="queryParams.emName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="emStat">
        <el-select v-model="queryParams.emStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_equipment_model_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="emDelete">
        <el-input
          v-model="queryParams.emDelete"
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
          v-hasPermi="['system:equipmentModel:add']"
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
          v-hasPermi="['system:equipmentModel:edit']"
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
          v-hasPermi="['system:equipmentModel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:equipmentModel:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentModelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备模型ID" align="center" prop="emId" v-if="true"/>
      <el-table-column label="设备模型编码" align="center" prop="emCode" />
      <el-table-column label="名称" align="center" prop="emName" />
      <el-table-column label="所属模型类型" align="center" prop="emtCode">
        <template slot-scope="scope">
          {{ equipmentModelTypeListFull.find(ele => ele.emtCode === scope.row.emtCode).emtName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="emStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_equipment_model_status" :value="scope.row.emStat"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="emDelete" /> -->
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:equipmentModel:edit']"
            v-show="scope.row.emStat === '1'"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-position"
            @click="handleModelOperationView(scope.row)"
          >模型操作</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            v-show="scope.row.emStat === '1'"
            @click="handleSubmitReview(scope.row)"
          >提交审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            v-show="scope.row.emStat === '2' || scope.row.emStat === '6'"
            @click="handleWithdrawReview(scope.row)"
          >撤回审核</el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-document-copy"
            v-show="scope.row.emStat !== '1' && scope.row.emStat !== '2' && scope.row.emStat !== '3'"
          >复制配置</el-button> -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-show="scope.row.emStat === '4'"
            @click="handleDepreciateReview(scope.row)"
          >弃用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:equipmentModel:remove']"
            v-show="scope.row.emStat === '1'"
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

    <!-- 添加或修改设备模型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模型类型" prop="emtCode">
          <el-select
            v-model="form.emtCode"
            placeholder="请选择模型类型"
            clearable
            :disabled="mode === 1"
          >
            <el-option
              v-for="item in equipmentModelTypeList"
              :key="item.emtCode"
              :label="item.emtName"
              :value="item.emtCode"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="emName">
          <el-input v-model="form.emName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="描述" prop="emDesc">
          <el-input v-model="form.emDesc" type="textarea" placeholder="请输入内容" />
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
import { listEquipmentModel, getEquipmentModel, delEquipmentModel, addEquipmentModel, updateEquipmentModel } from "@/api/system/equipmentModel";
import { listEquipmentModelType } from "@/api/system/equipmentModelType";

export default {
  name: "EquipmentModel",
  dicts: ['ices_equipment_model_status'],
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
      // 设备模型表格数据
      equipmentModelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        emtCode: this.$route.query.emtCode,
        emName: undefined,
        emStat: undefined,
        emDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        emId: [
          { required: true, message: "设备模型ID不能为空", trigger: "blur" }
        ],
        emtCode: [
          { required: true, message: "所属模型类型不能为空", trigger: "change" }
        ],
        emName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
      },
      // 设备模型类型数据（附带已弃用的模型类型）
      equipmentModelTypeListFull: [],
      // 设备模型类型数据
      equipmentModelTypeList: [],
      // 1-根据设备类型管理
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.emtCode) {
      this.mode = 1
    }
    await this.getEquipmentModelTypeList();
    this.getList();
  },
  methods: {
    // 查询模型类型列表
    getEquipmentModelTypeList() {
      // 完整列表
      listEquipmentModelType().then(response => {
        this.equipmentModelTypeListFull = response.rows;
        if (this.mode === 1) {
          this.hint = "设备模型类型 "
          this.hint += response.rows.find(ele => ele.emtCode === this.$route.query.emtCode).emtName
          this.hint += " "
        }
      });
      // 未弃用列表
      listEquipmentModelType({ emtDelete: 0 }).then(response => {
        this.equipmentModelTypeList = response.rows;
      });
    },
    /** 查询设备模型列表 */
    getList() {
      this.loading = true;
      listEquipmentModel(this.queryParams).then(response => {
        this.equipmentModelList = response.rows;
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
        emId: undefined,
        emtCode: undefined,
        emName: undefined,
        emStat: undefined,
        emDelete: undefined,
        emDesc: undefined,
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
      this.ids = selection.map(item => item.emId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.mode === 1) {
        this.form.emtCode = this.$route.query.emtCode
      }
      this.open = true;
      this.title = "添加设备模型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const emId = row.emId || this.ids
      getEquipmentModel(emId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改设备模型";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.emId != null) {
            updateEquipmentModel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            this.form.emStat = '1'
            addEquipmentModel(this.form).then(response => {
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
      const emIds = row.emId || this.ids;
      this.$modal.confirm('是否确认删除设备模型编号为"' + emIds + '"的数据项？').then(() => {
        this.loading = true;
        return delEquipmentModel(emIds);
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
      this.download('system/equipmentModel/export', {
        ...this.queryParams
      }, `equipmentModel_${new Date().getTime()}.xlsx`)
    },
    // 提交审核
    handleSubmitReview(row) {
      const emId = row.emId;
      this.$modal.confirm('是否要提交审核？审核在开始之前可以撤回。').then(() => {
        this.loading = true;
        getEquipmentModel(emId).then(response => {
          this.form = response.data;
          this.form.emStat = "2";
          updateEquipmentModel(this.form).then(response => {
            this.$modal.msgSuccess("已提交审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 撤回审核
    handleWithdrawReview(row) {
      const emId = row.emId;
      this.$modal.confirm('是否要撤回审核？若审核已开始即无法撤回。').then(() => {
        this.loading = true;
        getEquipmentModel(emId).then(response => {
          this.form = response.data;
          if (this.form.emStat === '2') this.form.emStat = '1'
          else this.form.emStat = '4';
          updateEquipmentModel(this.form).then(response => {
            this.$modal.msgSuccess("已撤回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 弃用设备模型
    handleDepreciateReview(row) {
      const emId = row.emId;
      this.$modal.confirm('是否弃用此设备模型？弃用设备模型需要进行模型弃用审核。').then(() => {
        this.loading = true;
        getEquipmentModel(emId).then(response => {
          this.form = response.data;
          this.form.emStat = '6';
          updateEquipmentModel(this.form).then(response => {
            this.$modal.msgSuccess("已提交模型弃用审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 查看模型操作
    handleModelOperationView(row) {
      this.$router.push(`/equipment/modelOperation?emCode=${row.emCode}`)
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