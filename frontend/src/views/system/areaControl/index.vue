<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选主控节点`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="车间" prop="arId">
        <el-select
          v-model="queryParams.arId"
          placeholder="请选择车间"
          clearable
          :disabled="mode === 1"
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
      <el-form-item label="名称" prop="acName">
        <el-input
          v-model="queryParams.acName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="IP地址" prop="acIp">
        <el-input
          v-model="queryParams.acIp"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="acDelete">
        <el-input
          v-model="queryParams.acDelete"
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
          v-hasPermi="['system:areaControl:add']"
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
          v-hasPermi="['system:areaControl:edit']"
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
          v-hasPermi="['system:areaControl:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:areaControl:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="areaControlList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主控节点ID" align="center" prop="acId" v-if="true"/>
      <el-table-column label="所属车间" align="center" prop="arId">
        <template slot-scope="scope">
          {{ areaList.find(ele => ele.arId === scope.row.arId).arName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="名称" align="center" prop="acName" />
      <el-table-column label="IP地址" align="center" prop="acIp" />
      <!-- <el-table-column label="已删除" align="center" prop="acDelete" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:areaControl:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:areaControl:remove']"
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

    <!-- 添加或修改主控节点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="车间" prop="arId">
          <el-select
            v-model="form.arId"
            placeholder="请选择车间"
            :disabled="mode === 1"
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
        <el-form-item label="名称" prop="acName">
          <el-input v-model="form.acName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="IP地址" prop="acIp">
          <el-input v-model="form.acIp" placeholder="请输入IP地址" />
        </el-form-item>
        <el-form-item label="描述" prop="acDesc">
          <el-input v-model="form.acDesc" type="textarea" placeholder="请输入内容" />
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
import { listAreaControl, getAreaControl, delAreaControl, addAreaControl, updateAreaControl } from "@/api/system/areaControl";
import { listArea } from "@/api/system/area";

export default {
  name: "AreaControl",
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
      // 主控节点表格数据
      areaControlList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        arId: this.$route.query.arId,
        acName: undefined,
        acIp: undefined,
        acDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        acId: [
          { required: true, message: "主控节点ID不能为空", trigger: "blur" }
        ],
        arId: [
          { required: true, message: "所属车间不能为空", trigger: "change" }
        ],
        acName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        acIp: [
          { required: true, message: "IP地址不能为空", trigger: "blur" }
        ],
      },
      // 1-按照车间筛选主控节点
      mode: 0,
      // 车间列表（筛选用）
      areaList: [],
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.arId) {
      this.mode = 1
    }
    await this.getAreaList();
    this.getList();
  },
  methods: {
    // 获取车间列表
    getAreaList() {
      listArea().then(response => {
        this.areaList = response.rows;
        if (this.mode === 1) {
          this.hint = "车间 "
          this.hint += response.rows.find(ele => ele.arId === this.$route.query.arId).arName
          this.hint += " "
        }
      });
    },
    /** 查询主控节点列表 */
    getList() {
      this.loading = true;
      listAreaControl(this.queryParams).then(response => {
        this.areaControlList = response.rows;
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
        acId: undefined,
        arId: undefined,
        acName: undefined,
        acIp: undefined,
        acDelete: undefined,
        acDesc: undefined,
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
      this.ids = selection.map(item => item.acId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.mode === 1) {
        this.form.arId = this.$route.query.arId
      }
      this.open = true;
      this.title = "添加主控节点";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const acId = row.acId || this.ids
      getAreaControl(acId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改主控节点";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.acId != null) {
            updateAreaControl(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addAreaControl(this.form).then(response => {
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
    async handleDelete(row) {
      const acIds = row.acId || this.ids;
      try {
        await this.$modal.confirm('是否确认删除主控节点编号为"' + acIds + '"的数据项？')
        this.loading = true;
        if (typeof (acIds) === "string") {
          let form = (await getAreaControl(acIds)).data
          form.acDelete = 1
          await updateAreaControl(form)
        } else {
          // id数组
          let form = null
          for (let i = 0; i < acIds.length; i++) {
            form = (await getAreaControl(acIds[i])).data
            form.acDelete = 1
            await updateAreaControl(form)
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
      this.download('system/areaControl/export', {
        ...this.queryParams
      }, `areaControl_${new Date().getTime()}.xlsx`)
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