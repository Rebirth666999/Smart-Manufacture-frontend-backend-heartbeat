<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工艺流程" prop="procId">
        <el-select
          v-model="queryParams.procId"
          placeholder="请选择工艺流程"
          clearable
        >
          <el-option
            v-for="item in processList"
            :key="item.procId"
            :label="item.procName"
            :value="item.procId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="原料" prop="maId">
        <el-select
          v-model="queryParams.maId"
          placeholder="请选择原料"
          clearable
        >
          <el-option
            v-for="item in materialList"
            :key="item.maId"
            :label="item.maName"
            :value="item.maId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="pmDelete">
        <el-input
          v-model="queryParams.pmDelete"
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
          v-hasPermi="['system:processMaterial:add']"
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
          v-hasPermi="['system:processMaterial:edit']"
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
          v-hasPermi="['system:processMaterial:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:processMaterial:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processMaterialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="原料需求ID" align="center" prop="pmId" v-if="true"/>
      <el-table-column label="所属工艺流程" align="center" prop="procId">
        <template slot-scope="scope">
          {{ processList.find(ele => ele.procId === scope.row.procId).procName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="所用原料" align="center" prop="maId">
        <template slot-scope="scope">
          {{ materialList.find(ele => ele.maId === scope.row.maId).maName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="所需原料数量" align="center" prop="pmDemand" />
      <!-- <el-table-column label="已删除" align="center" prop="pmDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:processMaterial:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:processMaterial:remove']"
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

    <!-- 添加或修改关联-工艺流程原料需求对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="所属工艺流程" prop="procId">
          <el-select
            v-model="form.procId"
            placeholder="请选择工艺流程"
            clearable
          >
            <el-option
              v-for="item in processList"
              :key="item.procId"
              :label="item.procName"
              :value="item.procId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所用原料" prop="maId">
          <el-select
            v-model="form.maId"
            placeholder="请选择原料"
            clearable
          >
            <el-option
              v-for="item in materialList"
              :key="item.maId"
              :label="item.maName"
              :value="item.maId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所需原料数量" prop="pmDemand">
          <el-input v-model="form.pmDemand" placeholder="请输入所需原料数量" />
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
import { listProcessMaterial, getProcessMaterial, delProcessMaterial, addProcessMaterial, updateProcessMaterial } from "@/api/system/processMaterial";
import { listProcess } from "@/api/system/process";
import { listMaterial } from "@/api/system/material";

export default {
  name: "ProcessMaterial",
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
      // 关联-工艺流程原料需求表格数据
      processMaterialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        procId: undefined,
        maId: undefined,
        pmDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pmId: [
          { required: true, message: "原料需求ID不能为空", trigger: "blur" }
        ],
        procId: [
          { required: true, message: "所属工艺流程不能为空", trigger: "blur" }
        ],
        maId: [
          { required: true, message: "所用原料不能为空", trigger: "blur" }
        ],
        pmDemand: [
          { required: true, message: "所需原料数量不能为空", trigger: "blur" }
        ],
      },
      // 工艺流程列表
      processList: [],
      // 原料列表
      materialList: []
    };
  },
  async created() {
    await this.getProcessList();
    await this.getMaterialList();
    this.getList();
  },
  methods: {
    // 查询工艺流程列表
    getProcessList() {
      listProcess().then(response => {
        this.processList = response.rows
      })
    },
    // 查询原料列表
    getMaterialList() {
      listMaterial().then(response => {
        this.materialList = response.rows
      })
    },
    /** 查询关联-工艺流程原料需求列表 */
    getList() {
      this.loading = true;
      listProcessMaterial(this.queryParams).then(response => {
        this.processMaterialList = response.rows;
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
        pmId: undefined,
        procId: undefined,
        maId: undefined,
        pmDemand: undefined,
        pmDelete: undefined,
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
      this.ids = selection.map(item => item.pmId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加关联-工艺流程原料需求";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const pmId = row.pmId || this.ids
      getProcessMaterial(pmId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改关联-工艺流程原料需求";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.pmId != null) {
            updateProcessMaterial(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addProcessMaterial(this.form).then(response => {
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
      const pmIds = row.pmId || this.ids;
      this.$modal.confirm('是否确认删除关联-工艺流程原料需求编号为"' + pmIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProcessMaterial(pmIds);
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
      this.download('system/processMaterial/export', {
        ...this.queryParams
      }, `processMaterial_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
