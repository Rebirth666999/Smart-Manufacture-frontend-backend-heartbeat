<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="eqName">
        <el-input
          v-model="queryParams.eqName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车间" prop="arCode">
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
      <el-form-item label="设备模型" prop="emCode">
        <el-select v-model="queryParams.emCode" placeholder="请选择设备模型" 
        @keyup.enter.native="handleQuery" clearable>
          <el-option
            v-for="item in equipmentModelListFull"
            :key="item.emCode"
            :label="item.emName"
            :value="item.emCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="eqStat">
        <el-select v-model="queryParams.eqStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_equipment_status_review"
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
          v-hasPermi="['system:equipment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备ID" align="center" prop="eqId" v-if="true"/>
      <el-table-column label="设备编码" align="center" prop="eqCode" />
      <el-table-column label="名称" align="center" prop="eqName" />
      <el-table-column label="所属车间" align="center" prop="arCode">
        <template slot-scope="scope">
          {{ areaList.find(ele => ele.arCode === scope.row.arCode).arName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="所属设备模型" align="center" prop="emCode">
        <template slot-scope="scope">
          {{ equipmentModelListFull.find(ele => ele.emCode === scope.row.emCode).emName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="eqStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_equipment_status_review" :value="scope.row.eqStat"/>
        </template>
      </el-table-column>
      <el-table-column label="IP地址" align="center" prop="eqIp" />
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
            @click="handleEquipmentOperationView(scope.row)"
          >设备操作</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="scope.row.eqStat === '2' || scope.row.eqStat === 'b'"
            @click="startReview(scope.row)"
          >开始审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            v-show="scope.row.eqStat === '3' || scope.row.eqStat === 'c'"
            @click="passReview(scope.row)"
          >通过审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            v-show="scope.row.eqStat === '3' || scope.row.eqStat === 'c'"
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
import { getEquipment, listReviewEquipment, updateEquipment } from "@/api/system/equipment";
import { listArea } from "@/api/system/area";
import { listEquipmentModel } from "@/api/system/equipmentModel";

export default {
  name: "EquipmentReview",
  dicts: ['ices_equipment_status_review'],
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
      // 设备表格数据
      equipmentList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        arCode: undefined,
        emCode: undefined,
        eqName: undefined,
        eqStat: undefined,
        eqDelete: 0,
      },
      // 车间列表
      areaList: [],
      // 已发布的设备模型列表
      equipmentModelList: [],
      // 设备模型列表
      equipmentModelListFull: [],
    };
  },
  async created() {
    await this.getAreaList();
    await this.getEquipmentModelList();
    this.getList();
  },
  async activated() {
    await this.getAreaList();
    await this.getEquipmentModelList();
    this.getList();
  },
  methods: {
    // 开始审核
    startReview(row) {
      this.$modal.confirm('是否要开始审核？').then(() => {
        this.loading = true;
        getEquipment(row.eqId).then(response => {
          this.form = response.data;
          if (this.form.eqStat === '2') this.form.eqStat = '3';
          else this.form.eqStat = 'c';
        updateEquipment(this.form).then(response => {
            this.$modal.msgSuccess("已开始审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 通过审核
    passReview(row) {
      this.$modal.confirm('是否要通过审核？ ').then(() => {
        this.loading = true;
        getEquipment(row.eqId).then(response => {
          this.form = response.data;
          this.form.eqStat = '4';
        updateEquipment(this.form).then(response => {
            this.$modal.msgSuccess('已通过审核');
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
      this.$modal.confirm('是否要驳回审核？ ').then(() => {
        this.loading = true;
        getEquipment(row.eqId).then(response => {
           this.form = response.data;
           this.form.eqStat = '1';
        updateEquipment(this.form).then(response => {
          this.$modal.msgSuccess('已驳回审核');
          this.getList();
        })
        });
      }).catch(() => {
      }).fianlly(() => {
        this.loading = false;
      });
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
    // 查询设备模型列表
    getEquipmentModelList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listEquipmentModel().then(response => {
          this.equipmentModelListFull = response.rows
          this.equipmentModelList = response.rows.filter(ele => ele.emStat === '4')
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询设备列表 */
    getList() {
      this.loading = true;
      listReviewEquipment(this.queryParams).then(response => {
        this.equipmentList = response.rows;
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
      this.ids = selection.map(item => item.eqId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/equipment/export', {
        ...this.queryParams
      }, `equipment_${new Date().getTime()}.xlsx`)
    },
    // 查看设备操作
    handleEquipmentOperationView(row) {
      this.$router.push(`/equipment/equipmentOperation?eqId=${row.eqId}`)
    }
  }
};
</script> 