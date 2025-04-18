<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库" prop="msCode">
        <el-select
          v-model="queryParams.msCode"
          placeholder="请选择仓库"
          clearable
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
      <el-form-item label="原料" prop="maCode">
        <el-select
          v-model="queryParams.maCode"
          placeholder="请选择原料"
          clearable
        >
          <el-option
            v-for="item in materialList"
            :key="item.maCode"
            :label="item.maName"
            :value="item.maCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="mssDelete">
        <el-input
          v-model="queryParams.mssDelete"
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
          v-hasPermi="['system:materialStock:add']"
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
          v-hasPermi="['system:materialStock:edit']"
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
          v-hasPermi="['system:materialStock:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:materialStock:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialStockList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="仓库原料库存ID" align="center" prop="mssId" v-if="true"/>
      <el-table-column label="仓库原料库存编码" align="center" prop="mssCode" />
      <el-table-column label="仓库" align="center" prop="msCode">
        <template slot-scope="scope">
          {{ materialStoreList.find(ele => ele.msCode === scope.row.msCode).msName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="原料" align="center" prop="maCode">
        <template slot-scope="scope">
          {{ materialList.find(ele => ele.maCode === scope.row.maCode).maName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="库存" align="center" prop="mssStock" />
      <!-- <el-table-column label="已删除" align="center" prop="mssDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:materialStock:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:materialStock:remove']"
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

    <!-- 添加或修改仓库原料库存对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库" prop="msCode">
          <el-select
            v-model="form.msCode"
            placeholder="请选择仓库"
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
        <el-form-item label="原料" prop="maCode">
          <el-select
            v-model="form.maCode"
            placeholder="请选择原料"
          >
            <el-option
              v-for="item in materialList"
              :key="item.maCode"
              :label="item.maName"
              :value="item.maCode"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库存" prop="mssStock">
          <el-input v-model="form.mssStock" placeholder="请输入库存" />
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
import { listMaterialStock, getMaterialStock, delMaterialStock, addMaterialStock, updateMaterialStock } from "@/api/system/materialStock";
import { listMaterial } from "@/api/system/material";
import { listMaterialStore } from "@/api/system/materialStore";

export default {
  name: "MaterialStock",
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
      // 仓库原料库存表格数据
      materialStockList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mssCode: undefined,
        msCode: undefined,
        maCode: undefined,
        mssDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mssId: [
          { required: true, message: "仓库原料库存ID不能为空", trigger: "blur" }
        ],
        msCode: [
          { required: true, message: "仓库不能为空", trigger: "blur" }
        ],
        maCode: [
          { required: true, message: "原料不能为空", trigger: "blur" }
        ],
      },
      // 原料列表
      materialList: [],
      // 原料仓库列表
      materialStoreList: []
    };
  },
  async created() {
    await this.getMaterialStoreList();
    await this.getMaterialList();
    this.getList();
  },
  async activated() {
    await this.getMaterialStoreList();
    await this.getMaterialList();
    this.getList();
  },
  methods: {
    // 查询原料列表
    getMaterialList() {
      return new Promise((resolve, reject) => {
        listMaterial().then(response => {
          this.materialList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
        })
      })
    },
    // 查询仓库列表
    getMaterialStoreList() {
      return new Promise((resolve, reject) => {
        listMaterialStore().then(response => {
          this.materialStoreList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
        })
      })
    },
    /** 查询仓库原料库存列表 */
    getList() {
      this.loading = true;
      listMaterialStock(this.queryParams).then(response => {
        this.materialStockList = response.rows;
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
        mssId: undefined,
        mssCode: undefined,
        msCode: undefined,
        maCode: undefined,
        mssStock: undefined,
        mssDelete: undefined,
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
      this.ids = selection.map(item => item.mssId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加仓库原料库存";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const mssId = row.mssId || this.ids
      getMaterialStock(mssId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改仓库原料库存";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.mssId != null) {
            updateMaterialStock(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addMaterialStock(this.form).then(response => {
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
      const mssIds = row.mssId || this.ids;
      this.$modal.confirm('是否确认删除仓库原料库存编号为"' + mssIds + '"的数据项？').then(() => {
        this.loading = true;
        return delMaterialStock(mssIds);
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
      this.download('system/materialStock/export', {
        ...this.queryParams
      }, `materialStock_${new Date().getTime()}.xlsx`)
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
