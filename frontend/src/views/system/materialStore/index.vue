<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="仓库名称" prop="msName">
        <el-input
          v-model="queryParams.msName"
          placeholder="请输入仓库名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="msDelete">
        <el-input
          v-model="queryParams.msDelete"
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
          v-hasPermi="['system:materialStore:add']"
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
          v-hasPermi="['system:materialStore:edit']"
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
          v-hasPermi="['system:materialStore:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:materialStore:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialStoreList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="原料仓库ID" align="center" prop="msId" v-if="true"/>
      <el-table-column label="原料仓库编码" align="center" prop="msCode" />
      <el-table-column label="仓库名称" align="center" prop="msName" />
      <el-table-column label="货位数量" align="center" prop="msSpace" />
      <el-table-column label="空闲货位数量" align="center" prop="msFree" />
      <!-- <el-table-column label="已删除" align="center" prop="msDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="msDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:materialStore:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:materialStore:remove']"
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

    <!-- 添加或修改原料仓库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="仓库名称" prop="msName">
          <el-input v-model="form.msName" placeholder="请输入仓库名称" />
        </el-form-item>
        <el-form-item label="货位数量" prop="msSpace">
          <el-input v-model="form.msSpace" placeholder="请输入货位数量" />
        </el-form-item>
        <el-form-item label="空闲货位数量" prop="msFree">
          <el-input v-model="form.msFree" placeholder="请输入空闲货位数量" />
        </el-form-item>
        <el-form-item label="描述" prop="msDesc">
          <el-input v-model="form.msDesc" type="textarea" placeholder="请输入内容" />
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
import { listMaterialStore, getMaterialStore, delMaterialStore, addMaterialStore, updateMaterialStore } from "@/api/system/materialStore";

export default {
  name: "MaterialStore",
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
      // 原料仓库表格数据
      materialStoreList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        msCode: undefined,
        msName: undefined,
        msSpace: undefined,
        msFree: undefined,
        msDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        msId: [
          { required: true, message: "原料仓库ID不能为空", trigger: "blur" }
        ],
        msName: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
        msSpace: [
          { required: true, message: "货位数量不能为空", trigger: "blur" }
        ],
        msFree: [
          { required: true, message: "空闲货位数量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询原料仓库列表 */
    getList() {
      this.loading = true;
      listMaterialStore(this.queryParams).then(response => {
        this.materialStoreList = response.rows;
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
        msId: undefined,
        msCode: undefined,
        msName: undefined,
        msSpace: undefined,
        msFree: undefined,
        msDelete: undefined,
        msDesc: undefined,
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
      this.ids = selection.map(item => item.msId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加原料仓库";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const msId = row.msId || this.ids
      getMaterialStore(msId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改原料仓库";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.msId != null) {
            updateMaterialStore(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addMaterialStore(this.form).then(response => {
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
      const msIds = row.msId || this.ids;
      this.$modal.confirm('是否确认删除原料仓库编号为"' + msIds + '"的数据项？').then(() => {
        this.loading = true;
        return delMaterialStore(msIds);
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
      this.download('system/materialStore/export', {
        ...this.queryParams
      }, `materialStore_${new Date().getTime()}.xlsx`)
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