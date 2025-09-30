<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="生产计划" prop="mpCode">
        <el-select
          v-model="queryParams.mpCode"
          placeholder="请选择生产计划"
          clearable
          disabled
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
      <el-form-item label="工艺流程" prop="procCode">
        <el-select
          v-model="queryParams.procCode"
          placeholder="请选择工艺流程"
          clearable
        >
          <el-option
            v-for="item in processList"
            :key="item.procCode"
            :label="item.procName"
            :value="item.procCode"
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
            v-for="dict in dict.type.ices_manufacture_task_status"
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
          :disabled="single"
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
      <el-table-column label="生产任务ID" align="center" prop="mtId" v-if="true" width="180"/>
      <el-table-column label="生产任务编码" align="center" prop="mtCode" width="180" />
      <el-table-column label="所属生产计划" align="center" prop="mpCode" width="180" />
      <el-table-column label="工艺流程" align="center" prop="procCode" width="150">
        <template slot-scope="scope">
    {{ (processList.find(ele => ele.procCode === scope.row.procCode) || {}).procName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="目标车间" align="center" prop="arCode">
        <template slot-scope="scope">
          {{ areaList.find(ele => ele.arCode === scope.row.arCode).arName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="原料仓库" align="center" prop="msCode">
        <template slot-scope="scope">
          {{ materialStoreList.find(ele => ele.msCode === scope.row.msCode).msName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="产品仓库" align="center" prop="prsCode">
        <template slot-scope="scope">
          {{ productStoreList.find(ele => ele.prsCode === scope.row.prsCode).prsName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="mtStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_manufacture_task_status" :value="scope.row.mtStat"/>
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
      <el-table-column label="创建人" align="center" prop="mtCman" />
      <el-table-column label="创建时间" align="center" prop="mtCdate" width="180" />
      <el-table-column label="下发人" align="center" prop="mtRman" />
      <el-table-column label="下发时间" align="center" prop="mtRdate" width="180" />
      <el-table-column label="修改人" align="center" prop="mtMman" />
      <el-table-column label="修改时间" align="center" prop="mtMdate" width="180" />
      <!-- <el-table-column label="已删除" align="center" prop="mtDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="mtDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="150">
        <template slot-scope="scope">
        <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-show="scope.row.mtStat==='4' || scope.row.mtStat==='d'"
            @click="handleDeprecated(scope.row)"
          >弃用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="scope.row.mtStat==='1' || scope.row.mtStat==='4' || scope.row.mtStat==='d'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:manufactureTask:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            v-show="scope.row.mtStat==='4' || scope.row.mtStat==='d'"
            @click="handleGenerateDeviceTask(scope.row)"
          >生成设备任务</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-search"
            v-show="scope.row.mtStat!=='1' && scope.row.mtStat!=='2' && scope.row.mtStat!=='3' && scope.row.mtStat!=='4'"
            @click="handleViewDeviceTask(scope.row)"
          >查看设备任务</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-files"
            v-show="scope.row.mtStat==='d'||scope.row.mtStat==='5'" 
            @click="handleExecuteDeviceTask(scope.row)"
          >下发设备任务</el-button> 
           <!-- //设备任务的复用||scope.row.mtStat==='5' /> -->  
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            v-show="scope.row.mtStat==='5'"
            @click="handleStopDeviceTask(scope.row)"
          >停止设备任务</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-show="scope.row.mtStat==='1'"
            v-hasPermi="['system:manufactureTask:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            v-show="scope.row.mtStat === '1'"
            @click="handleSubmitReview(scope.row)"
          >提交审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            v-show="scope.row.mtStat === '2' || scope.row.mtStat === '7' || scope.row.mtStat === 'a'"
            @click="handleWithdrawReview(scope.row)"
          >撤回审核</el-button>
          
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
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-col :span="12">
          <el-form-item label="所属生产计划" prop="mpCode">
            <el-select
              v-model="form.mpCode"
              placeholder="请选择生产计划"
              disabled
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
        </el-col>
        <el-col :span="12">
          <el-form-item label="工艺流程" prop="procCode">
            <el-select
              v-model="form.procCode"
              placeholder="请选择工艺流程"
            >
              <el-option
                v-for="item in processListSelection"
                :key="item.procCode"
                :label="item.procName"
                :value="item.procCode"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="目标车间" prop="arCode">
            <el-select
              v-model="form.arCode"
              placeholder="请选择车间"
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
        </el-col>
        <el-col :span="12">
          <el-form-item label="原料仓库" prop="msCode">
            <el-select
              v-model="form.msCode"
              placeholder="请选择原料仓库"
            >
              <el-option
                v-for="item in materialStoreList"
                :key="item.msCode"
                :label="item.msName"
                :value="item.msCode"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="产品仓库" prop="prsCode">
            <el-select
              v-model="form.prsCode"
              placeholder="请选择产品仓库"
            >
              <el-option
                v-for="item in productStoreList"
                :key="item.prsCode"
                :label="item.prsName"
                :value="item.prsCode"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最晚结束时间" prop="mtEndPlan">
            <el-date-picker clearable
              v-model="form.mtEndPlan"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择最晚结束时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="优先级" prop="mtPriority">
            <el-input v-model="form.mtPriority" placeholder="请输入任务优先级" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="计划产品数量" prop="mtQtyPlan">
            <el-input v-model="form.mtQtyPlan" placeholder="请输入计划产品数量" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="描述" prop="mtDesc">
            <el-input v-model="form.mtDesc" type="textarea" placeholder="请输入描述" />
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listManufactureTask, getManufactureTask, delManufactureTask, addManufactureTask, updateManufactureTask } from "@/api/system/manufactureTask";
import { listDeviceTask,  listDeviceTaskParam, listDeviceTaskPrev, executeDeviceTask, findRemainByManufactureTask } from "@/api/system/deviceTask";

import { listArea } from "@/api/system/area";
import { listAreaControl } from "@/api/system/areaControl";
import { listManufacturePlan } from "@/api/system/manufacturePlan";
import { listProcess, getBpmnXml } from "@/api/system/process";
import { listMaterialStore } from "@/api/system/materialStore";
import { listProductStore } from "@/api/system/productStore";

import { listEquipmentOperationStep } from "@/api/system/equipmentOperationStep";
import { listEquipmentOperationStepParam } from "@/api/system/equipmentOperationStepParam";import { listEquipmentAtomOperation } from "@/api/system/equipmentAtomOperation";

import ProcessViewer from '@/components/ProcessViewerIndustry';

export default {
  name: "ManufactureTask",
  components: {
    ProcessViewer,
  },
  props: {
    mpCode: {
      required: false
    }
  },
  dicts: ['ices_manufacture_task_status'],
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
        mpCode: undefined,
        arCode: undefined,
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
        mpCode: [
          { required: true, message: "所属生产计划不能为空", trigger: "blur" }
        ],
        procCode: [
          { required: true, message: "工艺流程不能为空", trigger: "blur" }
        ],
        arCode: [
          { required: true, message: "目标车间不能为空", trigger: "blur" }
        ],
        msCode: [
          { required: true, message: "原料仓库不能为空", trigger: "blur" }
        ],
        prsCode: [
          { required: true, message: "产品仓库不能为空", trigger: "blur" }
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
      // 已发布，供选择的工艺流程列表
      processListSelection: [],
      // 原料仓库列表
      materialStoreList: [],
      // 产品仓库列表
      productStoreList: [],
      // 设备操作步骤列表
      eosList: [],
      // 设备操作步骤参数列表
      eospaList: [],
      // 设备任务列表
      dtList: [],
      // 设备任务参数列表
      dtpaList: [],    
      // 设备列表（全）
      eqList: [],
      // 当前选中的生产计划
      currentManufacturePlan: null,
    };
  },
  async created() {
    await this.getAreaList();
    await this.getManufacturePlanList();
    await this.getProcessList();
    await this.getStoreList();
    await this.getReferenceList();
    if (this.mpCode) {
      this.queryParams.mpCode = this.mpCode
    }
    this.getList();
  },
  async activated() {
    await this.getAreaList();
    await this.getManufacturePlanList();
    await this.getProcessList();
    await this.getStoreList();
    await this.getReferenceList();
    if (this.mpCode) {
      this.queryParams.mpCode = this.mpCode
    }
    this.getList();
  },
  methods: {
    // 获取流程信息参照所需的列表
    // 设备模型、模型操作、设备操作、设备
    getReferenceList() {
      return new Promise(async (resolve, reject) => {
        this.loading = true
        try {
          this.eosList = (await listEquipmentOperationStep()).rows
          this.eospaList = (await listEquipmentOperationStepParam()).rows
        } catch (err) {
          reject()
        }
        this.loading = false
        resolve()
      })
    },
    // 获取仓库列表
    getStoreList() {
      return new Promise(async (resolve, reject) => {
        this.loading = true
        try {
          this.materialStoreList = (await listMaterialStore()).rows
          this.productStoreList = (await listProductStore()).rows
        } catch (err) {
          reject()
        }
        this.loading = false
        resolve()
      })
    },
    // 获取工艺流程列表
    getProcessList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listProcess().then(response => {
          this.processList = response.rows.filter(ele => ele.odCode === this.currentManufacturePlan.odCode)
          this.processListSelection = response.rows.filter(ele => ele.odCode === this.currentManufacturePlan.odCode && ele.procStat === '5')
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 获取生产计划列表
    getManufacturePlanList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listManufacturePlan().then(response => {
          this.manufacturePlanList = response.rows
          this.currentManufacturePlan = response.rows.find(ele => ele.mpCode === this.mpCode)
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
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
        mpCode: undefined,
        procCode: undefined,
        arCode: undefined,
        msCode: undefined,
        prsCode: undefined,
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
      if (this.mpCode) {
        this.queryParams.mpCode = this.mpCode
      }
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
      if (this.mpCode) {
        this.form.mpCode = this.mpCode
        this.form.mtEndPlan = this.currentManufacturePlan.mpEndPlan
        this.form.mtPriority = this.currentManufacturePlan.mpPriority
        this.form.mtQtyPlan = this.currentManufacturePlan.mpQtyPlan
      }
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
            const desc = this.form.mtDesc || '';
            if (this.form.mtStat === '4' || this.form.mtStat === 'd') {
              if (!desc.includes('已发布') && !desc.includes('已生成')) {
                this.$message.warning('请在描述中手动输入原状态（已发布或已生成）信息');
                this.buttonLoading = false;
                return;
              }
               this.form.mtStat = '7';
              }
              if (this.form.mtStat === '7' || this.form.mtStat === 'a') {
              if (!desc.includes('已发布') && !desc.includes('已生成')) {
                this.$message.warning('请在描述中手动输入原状态（已发布或已生成）信息');
                this.buttonLoading = false;
                return;
              }
              }
            updateManufactureTask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit('update')
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
              this.$emit('update')
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
        this.$emit('update')
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
    // 提交审核
    handleSubmitReview(row) {
      const mtId = row.mtId;
      this.$modal.confirm('是否要提交审核？审核在开始之前可以撤回。').then(() => {
        this.loading = true;
        getManufactureTask(mtId).then(response => {
          this.form = response.data;
          if (this.form.mtStat === '1') this.form.mtStat = '2';
        updateManufactureTask(this.form).then(response => {
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
       const mtId = row.mtId;
       this.$modal.confirm('是否要撤回审核？若审核已开始即无法撤回。').then(() => {
         this.loading = true;
      getManufactureTask(mtId).then(response => {
        this.form = response.data;
        const desc = this.form.mtDesc || '';
        if (this.form.mtStat === '2') {
          this.form.mtStat = '1';
        } else if (this.form.mtStat === '7' || this.form.mtStat === 'a') {
          if (desc.includes('已发布')) {
            this.form.mtStat = '4';
          } else if (desc.includes('已生成')) {
            this.form.mtStat = 'd';
          }
        }
      updateManufactureTask(this.form).then(response => {
          this.$modal.msgSuccess("已撤回审核");
          this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 弃用
    handleDeprecated(row) {
      const mtId = row.mtId;
      this.$modal.confirm('是否确认弃用该生产计划？').then(() => {
        this.loading = true;
        getManufactureTask(mtId).then(response => {
          this.form = response.data;
          const desc = this.form.mtDesc || '';
          if (this.form.mtStat === '4' || this.form.mtStat === 'd') {
            if (!desc.includes('已发布') && !desc.includes('已生成')) {
              this.$prompt('请在描述中手动输入原状态（已发布或已生成）信息', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputValue: desc
              }).then(({ value }) => {
                this.form.mtDesc = value;
                this.form.mtStat = 'a';
                updateManufactureTask(this.form).then(response => {
                  this.$modal.msgSuccess("已弃用");
                  this.getList();
                }).finally(() => {
                  this.loading = false;
                });
              }).catch(() => {
                this.loading = false;
              });
            } else {
              this.form.mtStat = 'a';
              updateManufactureTask(this.form).then(response => {
                this.$modal.msgSuccess("已弃用");
                this.getList();
              }).finally(() => {
                this.loading = false;
              });
            }
          }
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 生成设备任务
    handleGenerateDeviceTask(row) {
      this.$router.push(`/manufacture/addDeviceTask?mtId=${row.mtId}`)
    },
    // 查看设备任务
    async handleViewDeviceTask(row) {
      this.$router.push(`/manufacture/viewDeviceTask?mtId=${row.mtId}`)
    },
    // 下发设备任务
    async handleExecuteDeviceTask(row) {
      this.$modal.confirm('是否下发任务？').then(async () => {
        this.loading = true
        // manufactureTask信息
        const productionTask = {
          "ptId": row.mtCode,
          "ptLatestEndtime": '',
          "ptNum": 1,
          "ptPriority": row.mtPriority,
          "preemptive": false,
        }

        // 对应车间主控节点
        const areaControl = (await listAreaControl({ arCode: row.arCode })).rows
        // 所有的设备原子操作
        const equipmentAtomOperationList = (await listEquipmentAtomOperation()).rows
        // 当前生产任务的所有设备任务
        const deviceTask = (await listDeviceTask({ mtCode: row.mtCode })).rows
        // 当前生产任务的所有任务参数
        const deviceTaskParam = (await listDeviceTaskParam({ mtCode: row.mtCode })).rows
        // 当前生产任务的所有设备任务前序关系
        const deviceTaskPrev = (await listDeviceTaskPrev({ mtCode: row.mtCode })).rows
        let processRoute = []

        // 处理每个task
        for (let task of deviceTask) {
          // 当前task的前序
          const prev = deviceTaskPrev.filter(ele => ele.dtCodeCur === task.dtCode)
          // 当前task的设备操作步骤
          // 排除开始和结束两个步骤
          const equipmentOperationStep = this.eosList.find(ele => ele.eoCode === task.eoCode && ele.eaoCode)
          // 当前task的原子操作
          const equipmentAtomOperation = equipmentAtomOperationList.find(ele => ele.eaoCode === equipmentOperationStep.eaoCode)
          // 当前task的所有操作参数（模板）
          const equipmentOperationStepParams = this.eospaList.filter(ele => ele.eosCode === equipmentOperationStep.eosCode)
          // 取出所需信息
          let route = {
            "prdId": task.dtCode,
            "prePrdId": prev.map(ele => ele.dtCodePrev),
            "eqId": task.eqCode,
            "opId": equipmentAtomOperation.eaoCode,
            "opParam": {}
          }
          // 解析参数信息
          // 遍历当前任务的所有实际参数
          for (let param of deviceTaskParam.filter(ele => ele.dtCode === task.dtCode)) {
            // 找到参数模板
            const paramInfo = equipmentOperationStepParams.find(ele => ele.eospaCode === param.eospaCode)
            // 解析参数
            if (paramInfo) {
              try {
                if (paramInfo.eospaType === '1')
                  route.opParam[paramInfo.eospaName] = parseInt(param.dtpaValue)
                else if (paramInfo.eospaType === '2')
                  route.opParam[paramInfo.eospaName] = parseFloat(param.dtpaValue)
                else if (paramInfo.eospaType === '4')
                  route.opParam[paramInfo.eospaName] = JSON.parse(param.dtpaValue)
                else
                  route.opParam[paramInfo.eospaName] = param.dtpaValue
                if (route.opParam[paramInfo.eospaName] === NaN) {
                  this.$modal.msgError("参数类型不合法，请重新生成设备任务")
                  return
                }
              } catch (error) {
                console.error("参数解析出错", error)
                this.$modal.msgError("参数类型不合法，请重新生成设备任务")
                return
              }
            }
          }
          processRoute.push(route)
        }
        console.log("area",areaControl)
        console.log("production",productionTask)
        console.log("process",processRoute)
        // 下发开始执行
        // return executeDeviceTask(areaControl[0]['acIp'], {
        //   "areaControl": areaControl,
        //   "productionTask": productionTask,
        //   "processRoute": processRoute
        // })
      }).then(() => {
        const mtId = row.mtId;
        // 先获取完整的生产任务信息
        return getManufactureTask(mtId);
      }).then(response => {
        const task = response.data;
        // 更新状态为进行中
        //task.mtStat = '5';设备任务的复用
        // 提交更新请求
        return updateManufactureTask(task);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("下发任务成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /**
     * 停止设备任务
     * @param row 生产任务
     */
    async handleStopDeviceTask(row) {
      this.$modal.confirm('是否停止任务？').then(async () => {
        ///////////////////////////
        // TODO 通知主控节点暂停下发
        ///////////////////////////

        this.loading = true
        // manufactureTask信息
        const productionTask = {
          "ptId": row.mtCode,
          "ptLatestEndtime": '',
          "ptNum": 1,
          "ptPriority": row.mtPriority,
          "preemptive": false,
        }

        // 对应车间主控节点
        const areaControl = (await listAreaControl({ arCode: row.arCode })).rows
        // 所有的设备原子操作
        const equipmentAtomOperationList = (await listEquipmentAtomOperation()).rows
        // 当前生产任务的所有设备任务
        const deviceTask = (await listDeviceTask({ mtCode: row.mtCode })).rows
        // 获取剩余任务
        const remainTask = (await findRemainByManufactureTask(row)).data
        // 当前生产任务的所有任务参数
        const deviceTaskParam = (await listDeviceTaskParam({ mtCode: row.mtCode })).rows
        // 当前生产任务的所有设备任务前序关系
        const deviceTaskPrev = (await listDeviceTaskPrev({ mtCode: row.mtCode })).rows
        let processRoute = []

        // 处理每个task
        for (let task of remainTask) {
          // 当前task的前序
          // 筛选出那些未完成的前序
          const prev = deviceTaskPrev.filter(ele => ele.dtCodeCur === task.dtCode && deviceTask.find(t => t.dtCode === ele.dtCodePrev).dtStat !== '4')

          // 当前task的设备操作步骤
          // 排除开始和结束两个步骤
          const equipmentOperationStep = this.eosList.find(ele => ele.eoCode === task.eoCode && ele.eaoCode)
          // 当前task的原子操作
          const equipmentAtomOperation = equipmentAtomOperationList.find(ele => ele.eaoCode === equipmentOperationStep.eaoCode)
          // 当前task的所有操作参数（模板）
          const equipmentOperationStepParams = this.eospaList.filter(ele => ele.eosCode === equipmentOperationStep.eosCode)
          // 取出所需信息
          let route = {
            "prdId": task.dtCode,
            "prePrdId": prev.map(ele => ele.dtCodePrev),
            "eqId": task.eqCode,
            "opId": equipmentAtomOperation.eaoCode,
            "opParam": {}
          }
          // 解析参数信息
          // 遍历当前任务的所有实际参数
          for (let param of deviceTaskParam.filter(ele => ele.dtCode === task.dtCode)) {
            // 找到参数模板
            const paramInfo = equipmentOperationStepParams.find(ele => ele.eospaCode === param.eospaCode)
            // 解析参数
            if (paramInfo) {
              try {
                if (paramInfo.eospaType === '1')
                  route.opParam[paramInfo.eospaName] = parseInt(param.dtpaValue)
                else if (paramInfo.eospaType === '2')
                  route.opParam[paramInfo.eospaName] = parseFloat(param.dtpaValue)
                else if (paramInfo.eospaType === '4')
                  route.opParam[paramInfo.eospaName] = JSON.parse(param.dtpaValue)
                else
                  route.opParam[paramInfo.eospaName] = param.dtpaValue

                if (route.opParam[paramInfo.eospaName] === NaN) {
                  this.$modal.msgError("参数类型不合法，请重新生成设备任务")
                  return
                }
              } catch (error) {
                console.error("参数解析出错", error)
                this.$modal.msgError("参数类型不合法，请重新生成设备任务")
                return
              }
            }
          }
          processRoute.push(route)
        }
        
        ///////////////////////////
        // TODO 通知主控节点清空队列
        ///////////////////////////

        
        ///////////////////////////
        // TODO 下发新的队列
        ///////////////////////////
        
        // 下发开始执行
        // return executeDeviceTask(areaControl[0]['acIp'], {
        //   "areaControl": areaControl,
        //   "productionTask": productionTask,
        //   "processRoute": processRoute
        // })
        console.log({
          "areaControl": areaControl,
          "productionTask": productionTask,
          "processRoute": processRoute
        })

        ///////////////////////////
        // TODO 通知主控节点继续下发
        ///////////////////////////
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
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
