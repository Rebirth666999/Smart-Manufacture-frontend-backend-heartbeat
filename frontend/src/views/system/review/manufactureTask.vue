<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选生产任务`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="生产计划" prop="mpCode">
        <el-select
          v-model="queryParams.mpCode"
          placeholder="请选择生产计划"
          clearable
          :disabled="mode === 1"
        >
          <el-option
            v-for="item in manufacturePlanList"
            :key="item.mpCode"
            :label="item.mpCode"
            :value="item.mpCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="目标车间" prop="arCode">
        <el-select
          v-model="queryParams.arCode"
          placeholder="请选择车间"
          clearable
        >
          <el-option
            v-for="item in areaList"
            :key="item.arCode"
            :label="item.arName"
            :value="item.arCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="mtStat">
        <el-select v-model="queryParams.mtStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_manufacture_task_review"
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
          v-hasPermi="['system:manufactureTask:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="manufactureTaskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="生产任务ID" align="center" prop="mtId" v-if="true"/>
      <el-table-column label="生产任务编码" align="center" prop="mtCode" />
      <el-table-column label="所属生产计划" align="center" prop="mpCode" />
      <el-table-column label="目标车间" align="center" prop="arCode">
        <template slot-scope="scope">
          {{ areaList.find(ele => ele.arCode === scope.row.arCode).arName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="mtStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_manufacture_task_review" :value="scope.row.mtStat"/>
        </template>
      </el-table-column>
      <el-table-column label="最晚结束时间" align="center" prop="mtEndPlan" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mtEndPlan, '{y}-{m}-{d}') }}</span>
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
            icon="el-icon-view"
            @click="handleTaskDetailView(scope.row)"
          >任务详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="scope.row.mtStat === '2' || scope.row.mtStat === '7' || scope.row.mtStat === 'a'"
            @click="startReview(scope.row)"
          >开始审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="scope.row.mtStat === '3' || scope.row.mtStat === '8' || scope.row.mtStat === 'b'"
            @click="passReview(scope.row)"
          >通过审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="scope.row.mtStat === '3' || scope.row.mtStat === '8' || scope.row.mtStat === 'b'"
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
import { getManufactureTask, listReviewManufactureTask , updateManufactureTask} from "@/api/system/manufactureTask";
import { listManufacturePlan } from "@/api/system/manufacturePlan";
import { listArea } from "@/api/system/area";


export default {
  name: "ManufactureTaskReview",
  dicts: ['ices_manufacture_task_review'],
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
      // 生产任务表格数据
      manufactureTaskList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mpCode: undefined,
        arCode: undefined,
        mtStat: undefined,
        mtDelete: 0,
      },
      // 生产计划数据
      manufacturePlanList: [],
      // 车间数据
      areaList: [],
      // 1-根据生产计划筛选
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.mpCode) {
      this.mode = 1
    }
    await this.getManufacturePlanList();
    await this.getAreaList();
    this.getList();
  },
  async activated() {
    if (this.$route.query.mpCode) {
      this.mode = 1
    } else {
      this.mode = 0
    }
    await this.getManufacturePlanList();
    await this.getAreaList();
    this.getList();
  },
  methods: {
    //开始审核
    startReview(row) {
      this.$modal.confirm('是否要开始审核？').then(() => {
        this.loading = true;
        getManufactureTask(row.mtId).then(response => {
          this.form = response.data;
          if (this.form.mtStat === '2') this.form.mtStat = '3';
          else if (this.form.mtStat === '7') this.form.mtStat = '8';
          else this.form.mtStat = 'b';
        updateManufactureTask(this.form).then(response => {
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
        getManufactureTask(row.mtId).then(response => {
          this.form = response.data;
          if (this.form.mtStat === '3' ||this.form.mtStat === '7' ||this.form.mtStat === 'b') this.form.mtStat = '4';
        updateManufactureTask(this.form).then(response => {
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
        getManufactureTask(row.mtId).then(response => {
          this.form = response.data;
          if (this.form.mtStat === '3' ||this.form.mtStat === '7' ||this.form.mtStat === 'b' ) this.form.mtStat = '1';
        updateManufactureTask(this.form).then(response => {
            this.$modal.msgSuccess("已驳回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 查询生产计划列表
    getManufacturePlanList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listManufacturePlan().then(response => {
          this.manufacturePlanList = response.rows
          if (this.mode === 1) {
            // 构造提示文本
            this.hint = "生产计划 "
            this.hint += this.$route.query.mpCode
            this.hint += " "
            // 设置筛选
            this.queryParams.mpCode = this.$route.query.mpCode
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false;
        })
      })
    },
    // 获取车间列表
    getAreaList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listArea().then(response => {
          this.areaList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询生产任务审核列表 */
    getList() {
      this.loading = true;
      listReviewManufactureTask(this.queryParams).then(response => {
        this.manufactureTaskList = response.rows;
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
      this.ids = selection.map(item => item.mtId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/manufactureTask/export', {
        ...this.queryParams
      }, `manufactureTask_${new Date().getTime()}.xlsx`)
    },
    // 查看任务详情
    handleTaskDetailView(row) {
      this.$router.push(`/manufacture/taskDetail?mtCode=${row.mtCode}`)
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