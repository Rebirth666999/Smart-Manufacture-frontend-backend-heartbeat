<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选物料库存`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="仓库原料库存编码" prop="msCode">
        <el-input
          v-model="queryParams.msCode"
          placeholder="请输入仓库原料库存编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="仓库" prop="stCode">
       <el-select v-model="queryParams.stCode" placeholder="请选择仓库" clearable>
        <el-option
         v-for="option in storeList"
         :key="option.stCode"
         :label="option.stName"
         :value="option.stCode">
        </el-option>
       </el-select>
      </el-form-item>
      <el-form-item label="物料" prop="maCode">
       <el-select v-model="queryParams.maCode" placeholder="请选择物料" clearable>
        <el-option
         v-for="option in materialList"
         :key="option.maCode"
         :label="option.maName"
         :value="option.maCode">
        </el-option>
       </el-select>
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
      <el-table-column label="车间原料库存ID" align="center" prop="msId" v-if="true"/>
      <el-table-column label="仓库原料库存编码" align="center" prop="msCode" />
      <el-table-column label="仓库" align="center" prop="stCode" >
        <template slot-scope="scope">
        {{ storeList.find(ele => ele.stCode === scope.row.stCode).stName || '' }}
       </template>
      </el-table-column>
      <el-table-column label="物料" align="center" prop="maCode" >
       <template slot-scope="scope">
         {{ materialList.find(ele => ele.maCode === scope.row.maCode).maName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="库存" align="center" prop="msStock" />
      <!-- <el-table-column label="已删除" align="center" prop="msDelete" /> -->
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

    <!-- 添加或修改原料库存对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库" prop="stCode">
         <el-select v-model="form.stCode" placeholder="请选择仓库" clearable>
          <el-option
           v-for="option in storeList"
           :key="option.stCode"
           :label="option.stName"
           :value="option.stCode">
          </el-option>
         </el-select>
        </el-form-item>
        <el-form-item label="物料" prop="maCode">
         <el-select v-model="form.maCode" placeholder="请选择仓库" clearable>
          <el-option
           v-for="option in materialList"
           :key="option.maCode"
           :label="option.maName"
           :value="option.maCode">
          </el-option>
         </el-select>
        </el-form-item>
        <el-form-item label="库存" prop="msStock">
          <el-input v-model="form.msStock" placeholder="请输入库存" />
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
import { listStore } from "@/api/system/store";

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
      // 原料库存表格数据
      materialStockList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        msCode: undefined,
        stCode: this.$route.query.stCode,
        maCode: this.$route.query.maCode,
        msDelete:0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        msId: [
          { required: true, message: "车间原料库存ID不能为空", trigger: "blur" }
        ],
        stCode: [
          { required: true, message: "仓库不能为空", trigger: "blur" }
        ],
        maCode: [
          { required: true, message: "物料不能为空", trigger: "blur" }
        ],
        msStock: [
          { required: true, message: "库存不能为空", trigger: "blur" }
        ],
      },
      // 仓库列表
      storeList: [],
      // 物料列表
      materialList: [],
      // 1-按照物料筛选
      // 2-按照仓库筛选
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.maCode) {
      this.mode = 1
    } else if (this.$route.query.stCode) {
      this.mode = 2
    }
    this.getList();
    await this.getMaterialList();
    await this.getStoreList();
  },
  methods: {
    //查询库存列表
    getStoreList(){
      listStore().then(response => {
        this.storeList = response.rows;
        if (this.mode === 2) {
          this.hint = "仓库 "
          this.hint += response.rows.find(ele => ele.stCode === this.$route.query.stCode).stName
          this.hint += " "
        }
      });
    },
    //查询台账列表
    getMaterialList(){
      listMaterial().then(response => {
        this.materialList = response.rows;
        if (this.mode === 1) {
          this.hint = "物料 "
          this.hint += response.rows.find(ele => ele.maCode === this.$route.query.maCode).maName
          this.hint += " "
        }
      });
    },
    /** 查询原料库存列表 */
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
        msId: undefined,
        msCode: undefined,
        stCode: undefined,
        maCode: undefined,
        msStock: undefined,
        msDelete: undefined,
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
      if (this.mode === 1) {
        this.form.maCode = this.$route.query.maCode
      } else if (this.mode === 2) {
        this.form.stCode = this.$route.query.stCode
      }
      this.open = true;
      this.title = "添加原料库存";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const msId = row.msId || this.ids
      getMaterialStock(msId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改原料库存";
      });
    },
    /** 提交按钮 */
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const selectedStore = this.storeList.find(store => store.stCode === this.form.stCode);
          const selectedMaterial = this.materialList.find(material => material.maCode === this.form.maCode);
          if (selectedStore && selectedMaterial) {
            const storeType = selectedStore.stType;
            const materialType = selectedMaterial.maType;
            if ((storeType === '1' && materialType === '1') || (storeType === '2' && materialType === '2')) {
              this.buttonLoading = true;
              if (this.form.msId != null) {
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
             } else {
              this.$modal.msgWarning("请选择与物料类型相符的仓库");
            }
          } else {
            this.$modal.msgWarning("未找到对应的仓库或物料信息");
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const msIds = row.msId || this.ids;
      this.$modal.confirm('是否确认删除原料库存编号为"' + msIds + '"的数据项？').then(() => {
        this.loading = true;
        return delMaterialStock(msIds);
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
<style>
.el-select{
  width: 100%;
}
.el-date-editor{
  width: 100%;
}
</style>
