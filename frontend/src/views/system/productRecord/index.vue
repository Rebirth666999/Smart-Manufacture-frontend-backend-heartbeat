<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品" prop="prId">
        <el-select
          v-model="queryParams.prId"
          placeholder="请选择产品"
          clearable
        >
          <el-option
            v-for="item in productList"
            :key="item.prId"
            :label="item.prName"
            :value="item.prId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="变动类型" prop="prrType">
        <el-select v-model="queryParams.prrType" placeholder="请选择变动类型" clearable>
          <el-option
            v-for="dict in dict.type.ices_record_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="prrDelete">
        <el-input
          v-model="queryParams.prrDelete"
          placeholder="请输入已删除"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['00:00:00', '23:59:59']"
        ></el-date-picker>
      </el-form-item>
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
          v-hasPermi="['system:productRecord:add']"
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
          v-hasPermi="['system:productRecord:edit']"
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
          v-hasPermi="['system:productRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:productRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品台账ID" align="center" prop="prrId" v-if="true"/>
      <el-table-column label="所属产品ID" align="center" prop="prId" />
      <el-table-column label="变动类型" align="center" prop="prrType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_record_type" :value="scope.row.prrType"/>
        </template>
      </el-table-column>
      <el-table-column label="预计变动值" align="center" prop="prrEst" />
      <el-table-column label="实际变动值" align="center" prop="prrReal" />
      <!-- <el-table-column label="已删除" align="center" prop="prrDelete" /> -->
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:productRecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:productRecord:remove']"
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

    <!-- 添加或修改产品台账对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="产品" prop="prId">
          <el-select
            v-model="form.prId"
            placeholder="请选择产品"
            clearable
          >
            <el-option
              v-for="item in productList"
              :key="item.prId"
              :label="item.prName"
              :value="item.prId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="变动类型" prop="prrType">
          <el-select v-model="form.prrType" placeholder="请选择变动类型">
            <el-option
              v-for="dict in dict.type.ices_record_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预计变动值" prop="prrEst">
          <el-input v-model="form.prrEst" placeholder="请输入预计变动值" />
        </el-form-item>
        <el-form-item label="实际变动值" prop="prrReal">
          <el-input v-model="form.prrReal" placeholder="请输入实际变动值" />
        </el-form-item>
        <el-form-item label="描述" prop="prrDesc">
          <el-input v-model="form.prrDesc" type="textarea" placeholder="请输入内容" />
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
import { listProductRecord, getProductRecord, delProductRecord, addProductRecord, updateProductRecord } from "@/api/system/productRecord";
import { listProduct } from "@/api/system/product";

export default {
  name: "ProductRecord",
  dicts: ['ices_record_type'],
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
      // 产品台账表格数据
      productRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 描述时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        prId: undefined,
        prrType: undefined,
        prrDelete: 0,
        createTime: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        prrId: [
          { required: true, message: "产品台账ID不能为空", trigger: "blur" }
        ],
        prId: [
          { required: true, message: "所属产品ID不能为空", trigger: "change" }
        ],
        prrType: [
          { required: true, message: "变动类型不能为空", trigger: "change" }
        ],
        prrEst: [
          { required: true, message: "预计变动值不能为空", trigger: "blur" }
        ],
      },
      // 产品列表
      productList: []
    };
  },
  created() {
    this.getProductList();
    this.getList();
  },
  methods: {
    // 查询产品列表
    getProductList() {
      listProduct().then(response => {
        this.productList = response.rows
      })
    },
    /** 查询产品台账列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listProductRecord(this.queryParams).then(response => {
        this.productRecordList = response.rows;
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
        prrId: undefined,
        prId: undefined,
        prrType: undefined,
        prrEst: undefined,
        prrReal: undefined,
        prrDelete: undefined,
        prrDesc: undefined,
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
      this.daterangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.prrId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加产品台账";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const prrId = row.prrId || this.ids
      getProductRecord(prrId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改产品台账";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.prrId != null) {
            updateProductRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addProductRecord(this.form).then(response => {
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
      const prrIds = row.prrId || this.ids;
      this.$modal.confirm('是否确认删除产品台账编号为"' + prrIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProductRecord(prrIds);
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
      this.download('system/productRecord/export', {
        ...this.queryParams
      }, `productRecord_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
