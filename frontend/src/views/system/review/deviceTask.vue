<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选设备任务`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="生产任务" prop="mtCode">
        <el-select
          v-model="queryParams.mtCode"
          placeholder="请选择生产任务"
          clearable
          :disabled="mode === 1"
        >
          <el-option
            v-for="item in manufactureTaskList"
            :key="item.mtCode"
            :label="item.mtCode"
            :value="item.mtCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备操作" prop="eoCode">
        <el-select
          v-model="queryParams.eoCode"
          placeholder="请选择设备操作"
          clearable
        >
          <el-option
            v-for="item in equipmentOperationList"
            :key="item.eoCode"
            :label="item.eoName"
            :value="item.eoCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="dtStat">
        <el-select v-model="queryParams.dtStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_manufacture_task_status_review"
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
          v-hasPermi="['system:deviceTask:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deviceTaskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备任务ID" align="center" prop="dtId" v-if="true"/>
      <el-table-column label="设备任务编码" align="center" prop="dtCode" />
      <el-table-column label="所属生产任务" align="center" prop="mtCode" />
      <el-table-column label="设备操作" align="center" prop="eoCode">
        <template slot-scope="scope">
          {{ equipmentOperationList.find(ele => ele.eoCode === scope.row.eoCode).eoName || scope.row.eoCode }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="dtStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_manufacture_task_status_review" :value="scope.row.dtStat"/>
        </template>
      </el-table-column>
      <el-table-column label="计划开始时间" align="center" prop="dtBeginPlan" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dtBeginPlan, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划结束时间" align="center" prop="dtEndPlan" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dtEndPlan, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleTaskDetailView(scope.row)"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="scope.row.dtStat === '2' || scope.row.dtStat === '6'"
          >审核</el-button>
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
import { listReviewDeviceTask } from "@/api/system/deviceTask";
import { listManufactureTask } from "@/api/system/manufactureTask";
import { listEquipmentOperation } from "@/api/system/equipmentOperation";

export default {
  name: "DeviceTaskReview",
  dicts: ['ices_manufacture_task_status_review'],
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
      // 设备任务表格数据
      deviceTaskList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mtCode: this.$route.query.mtCode,
        eoCode: undefined,
        dtStat: undefined,
        dtDelete: 0,
      },
      // 生产任务数据
      manufactureTaskList: [],
      // 设备操作数据
      equipmentOperationList: [],
      // 1-根据生产任务筛选
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.mtCode) {
      this.mode = 1
    }
    await this.getManufactureTaskList();
    await this.getEquipmentOperationList();
    this.getList();
  },
  methods: {
    // 查询生产任务列表
    getManufactureTaskList() {
      return listManufactureTask().then(response => {
        this.manufactureTaskList = response.rows;
        if (this.mode === 1) {
          this.hint = "生产任务 "
          this.hint += this.$route.query.mtCode
          this.hint += " "
        }
      });
    },
    // 获取设备操作列表
    getEquipmentOperationList() {
      return listEquipmentOperation().then(response => {
        this.equipmentOperationList = response.rows;
      });
    },
    /** 查询设备任务审核列表 */
    getList() {
      this.loading = true;
      listReviewDeviceTask(this.queryParams).then(response => {
        this.deviceTaskList = response.rows;
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
      this.ids = selection.map(item => item.dtId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/deviceTask/export', {
        ...this.queryParams
      }, `deviceTask_${new Date().getTime()}.xlsx`)
    },
    // 查看任务详情
    handleTaskDetailView(row) {
      this.$router.push(`/device/taskDetail?dtCode=${row.dtCode}`)
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