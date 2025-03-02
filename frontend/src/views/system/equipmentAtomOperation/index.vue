<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选设备原子操作`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备" prop="eqId">
        <el-select v-model="queryParams.eqId" placeholder="请选择设备" 
        @keyup.enter.native="handleQuery" :disabled="mode !== 0">
          <el-option
            v-for="item in equipmentList"
            :key="item.eqId"
            :label="item.eqName"
            :value="item.eqId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="eaoDelete">
        <el-input
          v-model="queryParams.eaoDelete"
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
      <el-col :span="1.5" v-if="this.mode !== 2">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:equipmentAtomOperation:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5" v-if="this.mode !== 2">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:equipmentAtomOperation:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5" v-if="this.mode !== 2">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:equipmentAtomOperation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:equipmentAtomOperation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentAtomOperationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备原子操作ID" align="center" prop="eaoId" v-if="true"/>
      <el-table-column label="所属设备" align="center" prop="eqId">
        <template slot-scope="scope">
          {{ equipmentList.find(ele => ele.eqId === scope.row.eqId).eqName }}
        </template>
      </el-table-column>
      <el-table-column label="操作类型" align="center" prop="eaoType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_equipment_atom_operation_type" :value="scope.row.eaoType"/>
        </template>
      </el-table-column>
      <el-table-column label="请求方法" align="center" prop="eaoRequestType" />
      <el-table-column label="URL" align="center" prop="eaoUrl" />
      <el-table-column label="执行时长" align="center" prop="eaoExecTime" />
      <!-- <el-table-column label="已删除" align="center" prop="eaoDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="eaoDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="this.mode !== 2">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:equipmentAtomOperation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:equipmentAtomOperation:remove']"
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

    <!-- 添加或修改设备原子操作对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备" prop="eqId">
          <el-select v-model="form.eqId" placeholder="请选择设备" 
          :disabled="mode !== 0">
            <el-option
              v-for="item in equipmentList"
              :key="item.eqId"
              :label="item.eqName"
              :value="item.eqId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="操作类型" prop="eaoType">
          <el-select v-model="form.eaoType" placeholder="请选择操作类型">
            <el-option
              v-for="dict in dict.type.ices_equipment_atom_operation_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请求方法" prop="eaoRequestType">
          <el-input v-model="form.eaoRequestType" placeholder="请输入请求方法" />
        </el-form-item>
        <el-form-item label="URL" prop="eaoUrl">
          <el-input v-model="form.eaoUrl" placeholder="请输入URL" />
        </el-form-item>
        <el-form-item label="执行时长" prop="eaoExecTime">
          <el-input v-model="form.eaoExecTime" placeholder="请输入执行时长" />
        </el-form-item>
        <el-form-item label="描述" prop="eaoDesc">
          <el-input v-model="form.eaoDesc" type="textarea" placeholder="请输入内容" />
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
import { listEquipmentAtomOperation, getEquipmentAtomOperation, delEquipmentAtomOperation, addEquipmentAtomOperation, updateEquipmentAtomOperation } from "@/api/system/equipmentAtomOperation";
import { listEquipment } from "@/api/system/equipment";

export default {
  name: "EquipmentAtomOperation",
  dicts: ['ices_equipment_atom_operation_type'],
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
      // 设备原子操作表格数据
      equipmentAtomOperationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        eqId: this.$route.query.eqId,
        eaoDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        eaoId: [
          { required: true, message: "设备原子操作ID不能为空", trigger: "blur" }
        ],
        eqId: [
          { required: true, message: "所属设备ID不能为空", trigger: "blur" }
        ],
        eaoType: [
          { required: true, message: "操作类型不能为空", trigger: "change" }
        ],
        eaoRequestType: [
          { required: true, message: "请求方法不能为空", trigger: "blur" }
        ],
        eaoUrl: [
          { required: true, message: "URL不能为空", trigger: "blur" }
        ],
      },
      // 设备列表
      equipmentList: [],
      // 1-按设备查看原子操作（未发布，可修改）
      // 2-按设备查看原子操作（不可编辑）
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  created() {
    // 检查来源
    if (this.$route.query.eqId) {
      this.mode = 1
    }
    this.getEquipmentList();
    this.getList();
  },
  methods: {
    // 查询设备模型列表
    getEquipmentList() {
      listEquipment().then(response => {
        this.equipmentList = response.rows;
        if (this.mode === 1) {
          let equipment = response.rows.find(ele => ele.eqId === this.$route.query.eqId)
          // 构建筛选提示文本
          this.hint = "设备 "
          this.hint += equipment.eqName
          this.hint += " "
          // 检查状态
          if (equipment.eqStat !== '1') {
            this.mode = 2
          }
        }
      });
    },
    /** 查询设备原子操作列表 */
    getList() {
      this.loading = true;
      listEquipmentAtomOperation(this.queryParams).then(response => {
        this.equipmentAtomOperationList = response.rows;
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
        eaoId: undefined,
        eqId: undefined,
        eaoType: undefined,
        eaoRequestType: undefined,
        eaoUrl: undefined,
        eaoExecTime: undefined,
        eaoDelete: undefined,
        eaoDesc: undefined,
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
      this.ids = selection.map(item => item.eaoId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.mode === 1) {
        this.form.eqId = this.$route.query.eqId
      }
      this.open = true;
      this.title = "添加设备原子操作";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const eaoId = row.eaoId || this.ids
      getEquipmentAtomOperation(eaoId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改设备原子操作";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.eaoId != null) {
            updateEquipmentAtomOperation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addEquipmentAtomOperation(this.form).then(response => {
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
      const eaoIds = row.eaoId || this.ids;
      this.$modal.confirm('是否确认删除设备原子操作编号为"' + eaoIds + '"的数据项？').then(() => {
        this.loading = true;
        return delEquipmentAtomOperation(eaoIds);
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
      this.download('system/equipmentAtomOperation/export', {
        ...this.queryParams
      }, `equipmentAtomOperation_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
