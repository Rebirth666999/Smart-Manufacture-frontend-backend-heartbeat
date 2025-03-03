<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选车间原料库存`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="车间" prop="arId">
        <el-select v-model="queryParams.arId" placeholder="请选择车间" 
        @keyup.enter.native="handleQuery" :disabled="mode === 2" clearable>
          <el-option
            v-for="item in areaList"
            :key="item.arId"
            :label="item.arName"
            :value="item.arId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="原料" prop="maId">
        <el-select v-model="queryParams.maId" placeholder="请选择原料" 
        @keyup.enter.native="handleQuery" :disabled="mode === 1" clearable>
          <el-option
            v-for="item in materialList"
            :key="item.maId"
            :label="item.maName"
            :value="item.maId"
          >
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
      <el-table-column label="车间" align="center" prop="arId">
        <template slot-scope="scope">
          {{ areaList.find(ele => ele.arId === scope.row.arId).arName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="原料" align="center" prop="maId">
        <template slot-scope="scope">
          {{ materialList.find(ele => ele.maId === scope.row.maId).maName || '' }}
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

    <!-- 添加或修改车间原料库存对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="车间" prop="arId">
          <el-select
            v-model="form.arId"
            placeholder="请选择车间"
            :disabled="mode === 2 || form.msId != null"
            style="width: 100%;"
          >
            <el-option
              v-for="item in areaList"
              :key="item.arId"
              :label="item.arName"
              :value="item.arId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="原料" prop="maId">
          <el-select
            v-model="form.maId"
            placeholder="请选择原料"
            :disabled="mode === 1 || form.msId != null"
            style="width: 100%;"
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
import { listArea } from "@/api/system/area";

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
      // 车间原料库存表格数据
      materialStockList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        arId: this.$route.query.arId,
        maId: this.$route.query.maId,
        msDelete: 0,
      },
      // 1-查原料的库存，2-查车间的库存
      mode: 0,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        msId: [
          { required: true, message: "车间原料库存ID不能为空", trigger: "blur" }
        ],
        arId: [
          { required: true, message: "车间不能为空", trigger: "change" }
        ],
        maId: [
          { required: true, message: "原料不能为空", trigger: "change" }
        ],
        msStock: [
          { required: true, message: "库存不能为空", trigger: "blur" }
        ],
      },
      // 原料列表（筛选用）
      materialList: [],
      // 车间列表（筛选用）
      areaList: [],
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.maId) {
      this.mode = 1
    } else if (this.$route.query.arId) {
      this.mode = 2
    }
    // 获取原料和车间列表
    await this.getMaterialList()
    await this.getAreaList()
    // 初次筛选
    this.getList();
  },
  methods: {
    // 获取原料列表
    getMaterialList() {
      listMaterial().then(response => {
        this.materialList = response.rows;
        if (this.mode === 1) {
          this.hint = "原料 "
          this.hint += response.rows.find(ele => ele.maId === this.$route.query.maId).maName
          this.hint += " "
        }
      });
    },
    // 获取车间列表
    getAreaList() {
      listArea().then(response => {
        this.areaList = response.rows;
        if (this.mode === 2) {
          this.hint = "车间 "
          this.hint += response.rows.find(ele => ele.arId === this.$route.query.arId).arName
          this.hint += " "
        }
      });
    },
    /** 查询车间原料库存列表 */
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
        arId: undefined,
        maId: undefined,
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
        this.form.maId = this.$route.query.maId
      } else if (this.mode === 2) {
        this.form.arId = this.$route.query.arId
      }
      this.open = true;
      this.title = "添加车间原料库存";
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
        this.title = "修改车间原料库存";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.msId != null) {
            // 修改
            updateMaterialStock(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 添加
            // 先过额外检验
            if (!this.checkMaterialStock()) {
              this.$modal.msgError("已存在对应项目");
              this.buttonLoading = false;
              return;
            }
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
    // 插入和修改的额外检验
    // 原料-区域组合不可与已有组合重复
    async checkMaterialStock() {
      const response = await listMaterialStock({ arId: this.form.arId, maId: this.form.maId, msDelete: 0})
      return response.rows.length < 1
    },
    /** 删除按钮操作 */
    async handleDelete(row) {
      const msIds = row.msId || this.ids;
      try {
        await this.$modal.confirm('是否确认删除车间原料库存编号为"' + msIds + '"的数据项？')
        this.loading = true;
        if (typeof (msIds) === "string") {
          let form = (await getMaterialStock(msIds)).data
          form.msDelete = 1
          await updateMaterialStock(form)
        } else {
          // id数组
          let form = null
          for (let i = 0; i < msIds.length; i++) {
            form = (await getMaterialStock(msIds[i])).data
            form.msDelete = 1
            await updateMaterialStock(form)
          }
        }
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      } catch (error) {
        // 取消删除
      }
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
