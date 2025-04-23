<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="85px">
      <el-form-item label="所属质检单" prop="piCode">
        <el-select
          v-model="queryParams.piCode"
          placeholder="请选择所属质检单"
          disabled
        >
          <el-option
            v-for="item in productInspectionList"
            :key="item.piCode"
            :label="item.piCode"
            :value="item.piCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="合格标志" prop="pidFlag">
        <el-select
          v-model="queryParams.pidFlag"
          placeholder="请选择是否合格"
          clearable
        >
          <el-option
            v-for="dict in dict.type.ices_yn"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="pidDelete">
        <el-input
          v-model="queryParams.pidDelete"
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
          v-hasPermi="['system:productInspectionDetail:add']"
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
          v-hasPermi="['system:productInspectionDetail:edit']"
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
          v-hasPermi="['system:productInspectionDetail:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:productInspectionDetail:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productInspectionDetailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="质检单明细ID" align="center" prop="pidId" v-if="true"/>
      <el-table-column label="质检单明细编码" align="center" prop="pidCode" />
      <el-table-column label="所属质检单" align="center" prop="piCode" />
      <el-table-column label="产品类型" align="center" prop="prCode">
        <template slot-scope="scope">
          {{ productList[0].prName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="产品编码" align="center" prop="pidBatchNum" />
      <el-table-column label="质检结果" align="center" prop="pidResult" />
      <el-table-column label="合格标志" align="center" prop="pidFlag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_yn" :value="scope.row.pidFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="质检人" align="center" prop="pidMan" />
      <el-table-column label="质检日期" align="center" prop="pidDate" />
      <!-- <el-table-column label="已删除" align="center" prop="pidDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:productInspectionDetail:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:productInspectionDetail:remove']"
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

    <!-- 添加或修改产品质检单明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="所属质检单" prop="piCode">
          <el-select
          v-model="form.piCode"
          placeholder="请选择所属质检单"
          disabled
        >
          <el-option
            v-for="item in productInspectionList"
            :key="item.piCode"
            :label="item.piName"
            :value="item.piCode"
          >
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="产品类型" prop="prCode">
          <el-select
          v-model="form.prCode"
          placeholder="请选择产品类型"
          disabled
        >
          <el-option
            v-for="item in productList"
            :key="item.prCode"
            :label="item.prName"
            :value="item.prCode"
          >
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="产品编码" prop="pidBatchNum">
          <el-input v-model="form.pidBatchNum" placeholder="请输入产品编码" />
        </el-form-item>
        <el-form-item label="质检结果" prop="pidResult">
          <el-input v-model="form.pidResult" type="textarea" placeholder="请输入质检结果" />
        </el-form-item>
        <el-form-item label="合格标志" prop="pidFlag">
          <el-select
            v-model="form.pidFlag"
            placeholder="请选择是否合格"
            clearable
          >
            <el-option
              v-for="dict in dict.type.ices_yn"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import { listProductInspectionDetail, getProductInspectionDetail, delProductInspectionDetail, addProductInspectionDetail, updateProductInspectionDetail, listInspectionProduct } from "@/api/system/productInspectionDetail";
import { listProductInspection } from "@/api/system/productInspection";

export default {
  name: "ProductInspectionDetail",
  dicts: ['ices_yn'],
  props: {
    piCode: String
  },
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
      // 产品质检单明细表格数据
      productInspectionDetailList: [],
      //所属质检单列表
      productInspectionList: [],
      //产品类型列表
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pidCode: undefined,
        piCode: undefined,
        prCode: undefined,
        pidFlag: undefined,
        pidDelete: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pidId: [
          { required: true, message: "质检单明细ID不能为空", trigger: "blur" }
        ],
        piCode: [
          { required: true, message: "所属质检单不能为空", trigger: "blur" }
        ],
        prCode: [
          { required: true, message: "产品类型不能为空", trigger: "blur" }
        ],
        pidBatchNum: [
          { required: true, message: "产品编码不能为空", trigger: "blur" }
        ],
      },
      currentProductInspection: null
    };
  },
  async created() {
    if (this.piCode) {
      this.queryParams.piCode = this.piCode
    }
    await this.getProductInspectionList();
    await this.getProductList();
    this.getList();
  },
  async activated() {
    if (this.piCode) {
      this.queryParams.piCode = this.piCode
    }
    await this.getProductInspectionList();
    await this.getProductList();
    this.getList();
  },
  methods: {
    // 查询质检单列表
    getProductInspectionList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listProductInspection().then(response => {
          this.productInspectionList = response.rows;
          if (this.piCode) {
            this.currentProductInspection = this.productInspectionList.find(ele => ele.piCode === this.piCode)
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询产品列表
    getProductList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listInspectionProduct(this.currentProductInspection).then(response => {
          this.productList = [response];
          if (this.piCode) {
            this.form.prCode = this.productList[0].prCode
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询产品质检单明细列表 */
    getList() {
      this.loading = true;
      listProductInspectionDetail(this.queryParams).then(response => {
        this.productInspectionDetailList = response.rows;
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
        pidId: undefined,
        pidCode: undefined,
        piCode: undefined,
        prCode: undefined,
        pidBatchNum: undefined,
        pidResult: undefined,
        pidFlag: undefined,
        pidMan: undefined,
        pidDate: undefined,
        pidDelete: undefined,
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
      this.queryParams.piCode = this.piCode
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.pidId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.piCode) {
        this.form.piCode = this.piCode
        this.form.prCode = this.productList[0].prCode
      }
      this.open = true;
      this.title = "添加产品质检单明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const pidId = row.pidId || this.ids
      getProductInspectionDetail(pidId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改产品质检单明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.pidId != null) {
            updateProductInspectionDetail(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addProductInspectionDetail(this.form).then(response => {
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
      const pidIds = row.pidId || this.ids;
      this.$modal.confirm('是否确认删除产品质检单明细编号为"' + pidIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProductInspectionDetail(pidIds);
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
      this.download('system/productInspectionDetail/export', {
        ...this.queryParams
      }, `productInspectionDetail_${new Date().getTime()}.xlsx`)
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