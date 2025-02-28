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
      <el-form-item label="状态" prop="eqStat">
        <el-select v-model="queryParams.eqStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_equipment_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="eqDelete">
        <el-input
          v-model="queryParams.eqDelete"
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
          v-hasPermi="['system:equipment:add']"
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
          v-hasPermi="['system:equipment:edit']"
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
          v-hasPermi="['system:equipment:remove']"
        >删除</el-button>
      </el-col>
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
      <el-table-column label="所属车间ID" align="center" prop="arId" />
      <el-table-column label="所属设备模型ID" align="center" prop="emId" />
      <el-table-column label="名称" align="center" prop="eqName" />
      <el-table-column label="状态" align="center" prop="eqStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_equipment_status" :value="scope.row.eqStat"/>
        </template>
      </el-table-column>
      <el-table-column label="上次通讯时间" align="center" prop="eqCommunicateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.eqCommunicateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="IP地址" align="center" prop="eqIp" />
      <!-- <el-table-column label="已删除" align="center" prop="eqDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:equipment:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:equipment:remove']"
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

    <!-- 添加或修改设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="eqName">
          <el-input v-model="form.eqName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="采购时间" prop="eqIntroduceTime">
          <el-date-picker clearable
            v-model="form.eqIntroduceTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择采购时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="IP地址" prop="eqIp">
          <el-input v-model="form.eqIp" placeholder="请输入IP地址" />
        </el-form-item>
        <el-form-item label="描述" prop="eqDesc">
          <el-input v-model="form.eqDesc" type="textarea" placeholder="请输入内容" />
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
import { listEquipment, getEquipment, delEquipment, addEquipment, updateEquipment } from "@/api/system/equipment";

export default {
  name: "Equipment",
  dicts: ['ices_equipment_status'],
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        arId: undefined,
        emId: undefined,
        eqName: undefined,
        eqStat: undefined,
        eqDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        eqId: [
          { required: true, message: "设备ID不能为空", trigger: "blur" }
        ],
        arId: [
          { required: true, message: "所属车间ID不能为空", trigger: "change" }
        ],
        emId: [
          { required: true, message: "所属设备模型ID不能为空", trigger: "change" }
        ],
        eqName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        eqStat: [
          { required: true, message: "状态代码不能为空", trigger: "change" }
        ],
        eqIp: [
          { required: true, message: "IP地址不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询设备列表 */
    getList() {
      this.loading = true;
      listEquipment(this.queryParams).then(response => {
        this.equipmentList = response.rows;
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
        eqId: undefined,
        arId: undefined,
        emId: undefined,
        eqName: undefined,
        eqStat: undefined,
        eqIntroduceTime: undefined,
        eqCommunicateTime: undefined,
        eqIp: undefined,
        eqDelete: undefined,
        eqDesc: undefined,
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
      this.ids = selection.map(item => item.eqId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const eqId = row.eqId || this.ids
      getEquipment(eqId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改设备";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.eqId != null) {
            updateEquipment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addEquipment(this.form).then(response => {
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
      const eqIds = row.eqId || this.ids;
      this.$modal.confirm('是否确认删除设备编号为"' + eqIds + '"的数据项？').then(() => {
        this.loading = true;
        return delEquipment(eqIds);
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
      this.download('system/equipment/export', {
        ...this.queryParams
      }, `equipment_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
