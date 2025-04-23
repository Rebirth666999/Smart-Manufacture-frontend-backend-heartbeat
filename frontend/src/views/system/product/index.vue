<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名称" prop="prName">
        <el-input
          v-model="queryParams.prName"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="prDelete">
        <el-input
          v-model="queryParams.prDelete"
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
          v-hasPermi="['system:product:add']"
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
          v-hasPermi="['system:product:edit']"
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
          v-hasPermi="['system:product:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:product:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品ID" align="center" prop="prId" v-if="true"/>
      <el-table-column label="产品编码" align="center" prop="prCode" />
      <el-table-column label="产品名称" align="center" prop="prName" />
      <el-table-column label="占用货位数量" align="center" prop="prOccupy" />
      <el-table-column label="定制详情" align="center" prop="prCust" />
      <!-- <el-table-column label="已删除" align="center" prop="prDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="prDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:product:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:product:remove']"
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

    <!-- 添加或修改产品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="产品名称" prop="prName">
          <el-input v-model="form.prName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="占用货位数量" prop="prOccupy">
          <el-input v-model="form.prOccupy" placeholder="请输入占用货位数量" />
        </el-form-item>
        <el-form-item label="定制详情" prop="prCust">
          <el-table class="mb8" :data="custList" size="mini">
            <el-table-column label="序号" align="center" type="index" />
            <el-table-column label="定制项名称" align="center" prop="custKey">
              <template slot-scope="scope">
                <el-input v-model="custList[scope.$index].custKey" />
              </template>
            </el-table-column>
            <el-table-column label="可选值" align="center" prop="custVal">
              <template slot-scope="scope">
                <el-input v-model="custList[scope.$index].custVal" />
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="60px">
              <template slot-scope="scope">
                <el-button @click="deleteCust(scope)" type="danger" icon="el-icon-delete" size="small" circle plain></el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button @click="addCust" type="primary" icon="el-icon-plus" size="small" plain>新增</el-button>
        </el-form-item>
        <el-form-item label="描述" prop="prDesc">
          <el-input v-model="form.prDesc" type="textarea" placeholder="请输入内容" />
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
import { listProduct, getProduct, delProduct, addProduct, updateProduct } from "@/api/system/product";

export default {
  name: "Product",
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
      // 产品表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        prCode: undefined,
        prName: undefined,
        prDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        prId: [
          { required: true, message: "产品ID不能为空", trigger: "blur" }
        ],
        prName: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
        prOccupy: [
          { required: true, message: "占用货位数量不能为空", trigger: "blur" }
        ],
      },
      // 暂存用定制信息表格
      custList: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询产品列表 */
    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
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
        prId: undefined,
        prCode: undefined,
        prName: undefined,
        prOccupy: undefined,
        prCust: undefined,
        prDelete: undefined,
        prDesc: undefined,
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
      this.ids = selection.map(item => item.prId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加产品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const prId = row.prId || this.ids
      getProduct(prId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.parseCustString(this.form.prCust);
        this.open = true;
        this.title = "修改产品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.parseCustObject()) {
            this.$modal.msgWarning("请完整填写定制详情");
            return
          }
          this.buttonLoading = true;
          if (this.form.prId != null) {
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addProduct(this.form).then(response => {
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
      const prIds = row.prId || this.ids;
      this.$modal.confirm('是否确认删除产品编号为"' + prIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProduct(prIds);
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
      this.download('system/product/export', {
        ...this.queryParams
      }, `product_${new Date().getTime()}.xlsx`)
    },
    /**
     * 删除指定的定制详情
     * @param {any} scope 表格行信息
     * @author YangZY
     * @date 20250423
     */
    deleteCust(scope) {
      this.custList.splice(scope.$index, 1)
    },
    /**
     * 添加一条定制详情
     * @author YangZY
     * @date 20250423
     */
    addCust() {
      this.custList.push({
        custKey: '',
        custVal: ''
      })
    },
    /**
     * 定制详情JSON转换为List
     * @param {string} str 待转换字符串
     * @author YangZY
     * @date 20250423
     */
    parseCustString(str) {
      const json = JSON.parse(str)
      this.custList = []
      Object.keys(json).forEach(key => {
        json[key].forEach(val => {
          this.custList.push({
            custKey: key,
            custVal: val
          })
        })
      })
    },
    /**
     * 定制详情List转换为JSON
     * @returns 是否存在不合法记录
     * @author YangZY
     * @date 20250423
     */
    parseCustObject() {
      const result = {}
      let success = true
      this.custList.forEach(cust => {
        if (cust.custKey.length > 0 && cust.custVal.length > 0) {
          if (cust.custKey in result) {
            result[cust.custKey].push(cust.custVal)
          } else {
            result[cust.custKey] = [cust.custVal]
          }
        } else {
          success = false
        }
      })
      this.form.prCust = JSON.stringify(result)
      return success
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