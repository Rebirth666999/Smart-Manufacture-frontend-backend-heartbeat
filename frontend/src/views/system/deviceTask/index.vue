<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="110px">
      <el-form-item label="所属生产任务" prop="mtCode">
        <el-select
          v-model="queryParams.mtCode"
          placeholder="请选择生产任务"
          clearable
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
            v-for="dict in dict.type.ices_device_task_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="dtDelete">
        <el-input
          v-model="queryParams.dtDelete"
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
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:deviceTask:add']"
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
          v-hasPermi="['system:deviceTask:edit']"
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
          v-hasPermi="['system:deviceTask:remove']"
        >删除</el-button>
      </el-col> -->
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
          {{ equipmentOperationList.find(ele => ele.eoCode === scope.row.eoCode).eoName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="dtStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_device_task_status" :value="scope.row.dtStat"/>
        </template>
      </el-table-column>
      <el-table-column label="实际开始时间" align="center" prop="dtBegin" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dtBegin, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际结束时间" align="center" prop="dtEnd" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dtEnd, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="dtDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="dtDesc" /> -->
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:deviceTask:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:deviceTask:remove']"
          >删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改设备任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属生产任务ID" prop="mtCode">
          <el-input v-model="form.mtCode" placeholder="请输入所属生产任务ID" />
        </el-form-item>
        <el-form-item label="设备操作ID" prop="eoCode">
          <el-input v-model="form.eoCode" placeholder="请输入设备操作ID" />
        </el-form-item>
        <el-form-item label="描述" prop="dtDesc">
          <el-input v-model="form.dtDesc" type="textarea" placeholder="请输入内容" />
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
import { listDeviceTask, getDeviceTask, delDeviceTask, addDeviceTask, updateDeviceTask } from "@/api/system/deviceTask";
import { listManufactureTask } from "@/api/system/manufactureTask";
import { listEquipmentOperation } from "@/api/system/equipmentOperation";

export default {
  name: "DeviceTask",
  dicts: ['ices_device_task_status'],
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mtCode: undefined,
        eoCode: undefined,
        dtStat: undefined,
        dtDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dtId: [
          { required: true, message: "设备任务ID不能为空", trigger: "blur" }
        ],
        mtCode: [
          { required: true, message: "所属生产任务ID不能为空", trigger: "blur" }
        ],
        eoCode: [
          { required: true, message: "设备操作ID不能为空", trigger: "blur" }
        ],
      },
      // 生产任务列表
      manufactureTaskList: [],
      // 设备操作列表
      equipmentOperationList: []
    };
  },
  async created() {
    await this.getManufactureTaskList();
    await this.getEquipmentOperationList();
    this.getList();
  },
  async activated() {
    await this.getManufactureTaskList();
    await this.getEquipmentOperationList();
    this.getList();
  },
  methods: {
    // 获取设备操作列表
    getEquipmentOperationList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listEquipmentOperation().then(response => {
          this.equipmentOperationList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 获取生产任务列表
    getManufactureTaskList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listManufactureTask().then(response => {
          this.manufactureTaskList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询设备任务列表 */
    getList() {
      this.loading = true;
      listDeviceTask(this.queryParams).then(response => {
        this.deviceTaskList = response.rows;
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
        dtId: undefined,
        mtCode: undefined,
        eoCode: undefined,
        dtStat: undefined,
        dtBegin: undefined,
        dtEnd: undefined,
        dtDelete: undefined,
        dtDesc: undefined,
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
      this.ids = selection.map(item => item.dtId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const dtId = row.dtId || this.ids
      getDeviceTask(dtId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改设备任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.dtId != null) {
            updateDeviceTask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addDeviceTask(this.form).then(response => {
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
      const dtIds = row.dtId || this.ids;
      this.$modal.confirm('是否确认删除设备任务编号为"' + dtIds + '"的数据项？').then(() => {
        this.loading = true;
        return delDeviceTask(dtIds);
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
      this.download('system/deviceTask/export', {
        ...this.queryParams
      }, `deviceTask_${new Date().getTime()}.xlsx`)
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