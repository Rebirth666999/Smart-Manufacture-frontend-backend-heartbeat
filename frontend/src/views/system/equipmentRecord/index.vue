<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选设备事件`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>
    
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备" prop="eqId">
        <el-select v-model="queryParams.eqId" placeholder="请选择设备" 
        @keyup.enter.native="handleQuery" :disabled="mode === 1" clearable>
          <el-option
            v-for="item in equipmentList"
            :key="item.eqId"
            :label="item.eqName"
            :value="item.eqId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="事件类型" prop="erType">
        <el-select v-model="queryParams.erType" placeholder="请选择事件类型代码" clearable>
          <el-option
            v-for="dict in dict.type.ices_equipment_record_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="事件状态" prop="erStat">
        <el-select v-model="queryParams.erStat" placeholder="请选择事件状态代码" clearable>
          <el-option
            v-for="dict in dict.type.ices_equipment_record_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发生时间">
        <el-date-picker
          v-model="daterangeErBegin"
          style="width: 240px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['00:00:00', '23:59:59']"
        ></el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="erDelete">
        <el-input
          v-model="queryParams.erDelete"
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
          v-hasPermi="['system:equipmentRecord:add']"
        >新增</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:equipmentRecord:edit']"
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
          v-hasPermi="['system:equipmentRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:equipmentRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="事件日志ID" align="center" prop="erId" v-if="true"/>
      <el-table-column label="关联设备" align="center" prop="eqId">
        <template slot-scope="scope">
          {{ equipmentList.find(ele => ele.eqId === scope.row.eqId).eqName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="事件类型" align="center" prop="erType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_equipment_record_type" :value="scope.row.erType"/>
        </template>
      </el-table-column>
      <el-table-column label="事件状态" align="center" prop="erStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_equipment_record_status" :value="scope.row.erStat"/>
        </template>
      </el-table-column>
      <el-table-column label="事件等级" align="center" prop="erLevel" />
      <el-table-column label="发生时间" align="center" prop="erBegin" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.erBegin, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="erEnd" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.erEnd, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="已删除" align="center" prop="erDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:equipmentRecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:equipmentRecord:remove']"
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

    <!-- 添加或修改设备事件日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="描述" prop="erDesc">
          <el-input v-model="form.erDesc" type="textarea" placeholder="请输入内容" />
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
import { listEquipmentRecord, getEquipmentRecord, delEquipmentRecord, addEquipmentRecord, updateEquipmentRecord } from "@/api/system/equipmentRecord";
import { listEquipment } from "@/api/system/equipment";

export default {
  name: "EquipmentRecord",
  dicts: ['ices_equipment_record_type', 'ices_equipment_record_status'],
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
      // 设备事件日志表格数据
      equipmentRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 描述时间范围
      daterangeErBegin: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        eqId: this.$route.query.eqId,
        erType: undefined,
        erStat: undefined,
        erBegin: undefined,
        erDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        erId: [
          { required: true, message: "事件日志ID不能为空", trigger: "blur" }
        ],
        eqId: [
          { required: true, message: "关联设备不能为空", trigger: "change" }
        ],
        erType: [
          { required: true, message: "事件类型代码不能为空", trigger: "change" }
        ],
        erStat: [
          { required: true, message: "事件状态代码不能为空", trigger: "change" }
        ],
        erLevel: [
          { required: true, message: "事件等级不能为空", trigger: "blur" }
        ],
        erBegin: [
          { required: true, message: "发生时间不能为空", trigger: "blur" }
        ],
      },
      // 设备列表
      equipmentList: [],
      // 1-按设备查看日志
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.eqId) {
      this.mode = 1
    }
    await this.getEquipmentList();
    this.getList();
  },
  methods: {
    // 查询设备列表
    getEquipmentList() {
      listEquipment().then(response => {
        this.equipmentList = response.rows;
        if (this.mode === 1) {
          this.hint = "设备 "
          this.hint += response.rows.find(ele => ele.eqId === this.$route.query.eqId).eqName
          this.hint += " "
        }
      });
    },
    /** 查询设备事件日志列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeErBegin && '' != this.daterangeErBegin) {
        this.queryParams.params["beginErBegin"] = this.daterangeErBegin[0];
        this.queryParams.params["endErBegin"] = this.daterangeErBegin[1];
      }
      listEquipmentRecord(this.queryParams).then(response => {
        this.equipmentRecordList = response.rows;
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
        erId: undefined,
        eqId: undefined,
        erType: undefined,
        erStat: undefined,
        erLevel: undefined,
        erBegin: undefined,
        erEnd: undefined,
        erDelete: undefined,
        erDesc: undefined,
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
      this.daterangeErBegin = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.erId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备事件日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const erId = row.erId || this.ids
      getEquipmentRecord(erId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改设备事件日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.erId != null) {
            updateEquipmentRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addEquipmentRecord(this.form).then(response => {
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
      const erIds = row.erId || this.ids;
      this.$modal.confirm('是否确认删除设备事件日志编号为"' + erIds + '"的数据项？').then(() => {
        this.loading = true;
        return delEquipmentRecord(erIds);
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
      this.download('system/equipmentRecord/export', {
        ...this.queryParams
      }, `equipmentRecord_${new Date().getTime()}.xlsx`)
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