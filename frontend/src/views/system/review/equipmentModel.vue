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
            v-for="dict in dict.type.ices_equipment_model_status_review"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
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
      <el-table-column label="名称" align="center" prop="emName" />
      <el-table-column label="所属模型类型" align="center" prop="emtCode">
        <template slot-scope="scope">
          {{ equipmentModelTypeListFull.find(ele => ele.emtCode === scope.row.emtCode).emtName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="emStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_equipment_model_status_review" :value="scope.row.emStat"/>
        </template>
      </el-table-column>
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
            icon="el-icon-position"
            @click="handleModelOperationView(scope.row)"
          >模型操作</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="scope.row.emStat === '2' || scope.row.emStat === '6'"
            @click="startReview(scope.row)"
          >开始审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            v-show="scope.row.emStat === '3' || scope.row.emStat === '7'"
            @click="passReview(scope.row)"
          >通过审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-reject"
            v-show="scope.row.emStat === '3' || scope.row.emStat === '7'"
            @click="rejectReview(scope.row)"
          >驳回审核</el-button>
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
</template>

<script>
import { updateEquipmentModel , getEquipmentModel, listReviewEquipmentModel } from "@/api/system/equipmentModel";
import { listEquipmentModelType } from "@/api/system/equipmentModelType";

export default {
  name: "EquipmentModelReview",
  dicts: ['ices_equipment_model_status_review'],
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        emtCode: this.$route.query.emtCode,
        emName: undefined,
        emStat: undefined,
        emDelete: 0,
      },
      // 设备模型类型数据（附带已弃用的模型类型）
      equipmentModelTypeListFull: [],
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
    //开始审核
    startReview(row) {
      this.$modal.confirm('是否要开始审核？').then(() => {
        this.loading = true;
        getEquipmentModel(row.emId).then(response => {
          this.form = response.data;
          if (this.form.emStat === '2') this.form.emStat = '3';
          else this.form.emStat = '7';
        updateEquipmentModel(this.form).then(response => {
            this.$modal.msgSuccess("已开始审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    //通过审核
    passReview(row) {
      this.$modal.confirm('是否要通过审核？').then(() => {
        this.loading = true;
        getEquipmentModel(row.emId).then(response => {
          this.form = response.data;
          if (this.form.emStat === '3' ||this.form.emStat === '7' ) this.form.emStat = '4';
        updateEquipmentModel(this.form).then(response => {
            this.$modal.msgSuccess("已通过审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    //驳回审核
    rejectReview(row) {
      this.$modal.confirm('是否要驳回审核？').then(() => {
        this.loading = true;
        getEquipmentModel(row.emId).then(response => {
          this.form = response.data;
          if (this.form.emStat === '3' ||this.form.emStat === '7' ) this.form.emStat = '1';
        updateEquipmentModel(this.form).then(response => {
            this.$modal.msgSuccess("已驳回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
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
    },
    /** 查询设备模型列表 */
    getList() {
      this.loading = true;
      listReviewEquipmentModel(this.queryParams).then(response => {
        this.equipmentModelList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/equipmentModel/export', {
        ...this.queryParams
      }, `equipmentModel_${new Date().getTime()}.xlsx`)
    },
    // 查看模型操作
    handleModelOperationView(row) {
      this.$router.push(`/equipment/modelOperation?emCode=${row.emCode}`)
    }
  }
};
</script>
