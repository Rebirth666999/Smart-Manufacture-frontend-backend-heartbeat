<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="生产计划" prop="mpId">
        <el-select
          v-model="queryParams.mpId"
          placeholder="请选择生产计划"
          clearable
        >
          <el-option
            v-for="item in manufacturePlanList"
            :key="item.mpId"
            :label="item.mpId"
            :value="item.mpId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="目标车间" prop="arId">
        <el-select
          v-model="queryParams.arId"
          placeholder="请选择车间"
          clearable
        >
          <el-option
            v-for="item in areaList"
            :key="item.arId"
            :label="item.arName"
            :value="item.arId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="mtStat">
        <el-select v-model="queryParams.mtStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="优先级" prop="mtPriority">
        <el-input
          v-model="queryParams.mtPriority"
          placeholder="请输入任务优先级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="mtDelete">
        <el-input
          v-model="queryParams.mtDelete"
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
          v-hasPermi="['system:manufactureTask:add']"
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
          v-hasPermi="['system:manufactureTask:edit']"
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
          v-hasPermi="['system:manufactureTask:remove']"
        >删除</el-button>
      </el-col>
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
      <el-table-column label="所属生产计划ID" align="center" prop="mpId" />
      <el-table-column label="目标车间" align="center" prop="arId">
        <template slot-scope="scope">
          {{ areaList.find(ele => ele.arId === scope.row.arId).arName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="mtStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_order_status" :value="scope.row.mtStat"/>
        </template>
      </el-table-column>
      <el-table-column label="实际开始时间" align="center" prop="mtBegin" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mtBegin, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最晚结束时间" align="center" prop="mtEndPlan" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mtEndPlan, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际结束时间" align="center" prop="mtEndReal" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mtEndReal, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="mtPriority" />
      <el-table-column label="计划产品数量" align="center" prop="mtQtyPlan" />
      <el-table-column label="已完成产品数量" align="center" prop="mtQtyReal" />
      <!-- <el-table-column label="已删除" align="center" prop="mtDelete" /> -->
      <el-table-column label="描述" align="center" prop="mtDesc" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:manufactureTask:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleGenerateDeviceTask(scope.row)"
          >生成设备任务</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:manufactureTask:remove']"
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

    <!-- 添加或修改生产任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="540px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="所属生产计划" prop="mpId">
          <el-select
            v-model="form.mpId"
            placeholder="请选择生产计划"
            clearable
          >
            <el-option
              v-for="item in manufacturePlanList"
              :key="item.mpId"
              :label="item.mpId"
              :value="item.mpId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="目标车间" prop="arId">
          <el-select
            v-model="form.arId"
            placeholder="请选择车间"
          >
            <el-option
              v-for="item in areaList"
              :key="item.arId"
              :label="item.arName"
              :value="item.arId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="最晚结束时间" prop="mtEndPlan">
          <el-date-picker clearable
            v-model="form.mtEndPlan"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择最晚结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="优先级" prop="mtPriority">
          <el-input v-model="form.mtPriority" placeholder="请输入任务优先级" />
        </el-form-item>
        <el-form-item label="计划产品数量" prop="mtQtyPlan">
          <el-input v-model="form.mtQtyPlan" placeholder="请输入计划产品数量" />
        </el-form-item>
        <el-form-item label="描述" prop="mtDesc">
          <el-input v-model="form.mtDesc" placeholder="请输入描述" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 分配设备任务对话框 -->
    <el-dialog :title="viewerData.title" :visible.sync="viewerOpen" append-to-body fullscreen>
      <process-viewer
        v-loading="viewerData.loading"
        :key="`designer-${viewerData.index}`"
        :xml="viewerData.bpmnXml"
        :style="{height: 'calc(100vh - 124.5px)'}"
        :mode="3"
        :extraList="{ emList: viewerData.emList, moList: viewerData.moList, eqList: viewerData.eqList, eoList: viewerData.eoList, eosList: viewerData.eosList, eospaList: viewerData.eospaList }"
      />
    </el-dialog>
  </div>
</template>

<script>
import { listManufactureTask, getManufactureTask, delManufactureTask, addManufactureTask, updateManufactureTask } from "@/api/system/manufactureTask";
import { listArea } from "@/api/system/area";
import { listManufacturePlan } from "@/api/system/manufacturePlan";
import { listProcess, getBpmnXml } from "@/api/system/process";

import { listEquipment } from "@/api/system/equipment";
import { listEquipmentOperation } from "@/api/system/equipmentOperation";
import { listModelOperation } from "@/api/system/modelOperation";
import { listEquipmentModel } from "@/api/system/equipmentModel";
import { listEquipmentOperationStep } from "@/api/system/equipmentOperationStep";
import { listEquipmentOperationStepParam } from "@/api/system/equipmentOperationStepParam";

import ProcessViewer from '@/components/ProcessViewerIndustry';

export default {
  name: "ManufactureTask",
  components: {
    ProcessViewer,
  },
  dicts: ['ices_order_status'],
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mpId: undefined,
        arId: undefined,
        mtStat: undefined,
        mtPriority: undefined,
        mtDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mtId: [
          { required: true, message: "生产任务不能为空", trigger: "blur" }
        ],
        mpId: [
          { required: true, message: "所属生产计划不能为空", trigger: "blur" }
        ],
        arId: [
          { required: true, message: "目标车间不能为空", trigger: "blur" }
        ],
        mtEndPlan: [
          { required: true, message: "最晚结束时间不能为空", trigger: "blur" }
        ],
        mtPriority: [
          { required: true, message: "任务优先级不能为空", trigger: "blur" }
        ],
        mtQtyPlan: [
          { required: true, message: "计划产品数量不能为空", trigger: "blur" }
        ],
      },
      // 车间列表
      areaList: [],
      // 生产计划列表
      manufacturePlanList: [],
      // 工艺流程列表
      processList: [],
      // 查看窗口是否打开
      viewerOpen: false,
      // 查看器相关数据
      viewerData: {
        title: '',
        loading: false,
        index: undefined,
        bpmnXml: '',
        emList: [],     // 设备模型列表
        moList: [],     // 模型操作列表
        eqList: [],     // 设备列表（满足在对应的车间）
        eoList: [],     // 设备操作列表
        eosList: [],    // 设备操作步骤列表
        eospaList: [],  // 设备操作步骤参数列表
      },
      // 设备列表（全）
      eqList: [],
    };
  },
  async created() {
    await this.getAreaList();
    await this.getManufacturePlanList();
    await this.getProcessList();
    await this.getReferenceList();
    this.getList();
  },
  methods: {
    // 获取流程信息参照所需的列表
    // 设备模型、模型操作、设备操作、设备
    getReferenceList() {
      listEquipmentModel().then(response => {
        this.viewerData.emList = response.rows;
      });
      listModelOperation().then(response => {
        this.viewerData.moList = response.rows;
      });
      listEquipmentOperation().then(response => {
        this.viewerData.eoList = response.rows;
      });
      listEquipment().then(response => {
        this.eqList = response.rows;
      });
      listEquipmentOperationStep().then(response => {
        this.viewerData.eosList = response.rows;
      });
      listEquipmentOperationStepParam().then(response => {
        this.viewerData.eospaList = response.rows;
      });
    },
    // 获取工艺流程列表
    getProcessList() {
      listProcess().then(response => {
        this.processList = response.rows;
      });
    },
    // 获取生产计划列表
    getManufacturePlanList() {
      listManufacturePlan().then(response => {
        this.manufacturePlanList = response.rows;
      });
    },
    // 获取车间列表
    getAreaList() {
      listArea().then(response => {
        this.areaList = response.rows;
      });
    },
    /** 查询生产任务列表 */
    getList() {
      this.loading = true;
      listManufactureTask(this.queryParams).then(response => {
        this.manufactureTaskList = response.rows;
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
        mtId: undefined,
        mpId: undefined,
        arId: undefined,
        mtStat: undefined,
        mtBegin: undefined,
        mtEndPlan: undefined,
        mtEndReal: undefined,
        mtPriority: undefined,
        mtQtyPlan: undefined,
        mtQtyReal: undefined,
        mtDelete: undefined,
        mtDesc: undefined,
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
      this.ids = selection.map(item => item.mtId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加生产任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const mtId = row.mtId || this.ids
      getManufactureTask(mtId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改生产任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.mtId != null) {
            updateManufactureTask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 生产任务的默认状态
            this.form.mtStat = '1'
            addManufactureTask(this.form).then(response => {
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
      const mtIds = row.mtId || this.ids;
      this.$modal.confirm('是否确认删除生产任务编号为"' + mtIds + '"的数据项？').then(() => {
        this.loading = true;
        return delManufactureTask(mtIds);
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
      this.download('system/manufactureTask/export', {
        ...this.queryParams
      }, `manufactureTask_${new Date().getTime()}.xlsx`)
    },
    // 生成生产任务
    handleGenerateDeviceTask(row) {
      // 找到生产计划
      const manufacturePlan = this.manufacturePlanList.find(ele => ele.mpId === row.mpId)
      // 找到工艺流程
      const process = this.processList.find(ele => ele.procId === manufacturePlan.procId)
      // 找到可分配的设备
      this.viewerData.eqList = this.eqList.filter(ele => ele.arId === row.arId)
      // 打开流程
      this.viewerData.loading = true
      this.viewerData.title = "分配设备任务"
      this.viewerData.index = process.procModel
      this.viewerOpen = true
      getBpmnXml(process.procModel).then(response => {
        this.viewerData.bpmnXml = response.data || ''
        this.viewerData.loading = false
      })
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