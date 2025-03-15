<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选设备操作`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模型操作" prop="moId">
        <el-select v-model="queryParams.moId" placeholder="请选择模型操作" 
        @keyup.enter.native="handleQuery" clearable>
          <el-option
            v-for="item in modelOperationList"
            :key="item.moId"
            :label="item.moName"
            :value="item.moId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备" prop="eqId">
        <el-select v-model="queryParams.eqId" placeholder="请选择设备" 
        @keyup.enter.native="handleQuery" :disabled="mode !== 0" clearable>
          <el-option
            v-for="item in equipmentList"
            :key="item.eqId"
            :label="item.eqName"
            :value="item.eqId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="已删除" prop="eoDelete">
        <el-input
          v-model="queryParams.eoDelete"
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
          v-hasPermi="['system:equipmentOperation:add']"
          v-if="mode !== 2"
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
          v-hasPermi="['system:equipmentOperation:edit']"
          v-if="mode !== 2"
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
          v-hasPermi="['system:equipmentOperation:remove']"
          v-if="mode !== 2"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:equipmentOperation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentOperationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备操作ID" align="center" prop="eoId" v-if="true"/>
      <el-table-column label="对应模型操作" align="center" prop="moId">
        <template slot-scope="scope">
          {{ modelOperationList.find(ele => ele.moId === scope.row.moId).moName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="所属设备" align="center" prop="eqId">
        <template slot-scope="scope">
          {{ equipmentList.find(ele => ele.eqId === scope.row.eqId).eqName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="名称" align="center" prop="eoName" />
      <el-table-column label="执行时长" align="center" prop="eoExecTime" />
      <!-- <el-table-column label="已删除" align="center" prop="eoDelete" /> -->
      <!-- <el-table-column label="描述" align="center" prop="eoDesc" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:equipmentOperation:edit']"
            v-if="mode !== 2"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-brush"
            @click="handleDesigner(scope.row)"
            v-if="mode !== 2"
          >设计</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="handleViewer(scope.row)"
          >查看流程</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:equipmentOperation:remove']"
            v-if="mode !== 2"
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

    <!-- 添加或修改设备操作对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模型操作" prop="moId">
          <el-select v-model="form.moId" placeholder="请选择模型操作">
            <el-option
              v-for="item in modelOperationList"
              :key="item.moId"
              :label="item.moName"
              :value="item.moId"
            >
          </el-option>
        </el-select>
        </el-form-item>
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
        <el-form-item label="名称" prop="eoName">
          <el-input v-model="form.eoName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="执行时长" prop="eoExecTime">
          <el-input v-model="form.eoExecTime" placeholder="请输入执行时长" />
        </el-form-item>
        <el-form-item label="描述" prop="eoDesc">
          <el-input v-model="form.eoDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 流程设计对话框 -->
    <el-dialog :title="designerData.title" :visible.sync="designerOpen" append-to-body fullscreen>
      <process-designer
        :key="designerOpen"
        style="border:1px solid rgba(0, 0, 0, 0.1);"
        ref="modelDesigner"
        v-loading="designerData.loading"
        :bpmnXml="designerData.bpmnXml"
        :designerForm="designerData.form"
        :mode="1"
        :extraList="atomOperationList"
        @save="onSaveDesigner"
      />
    </el-dialog>

    <!-- 查看流程对话框 -->
    <el-dialog :title="viewerData.title" :visible.sync="viewerOpen" width="70%" append-to-body>
      <process-viewer
        v-loading="viewerData.loading"
        :key="`designer-${viewerData.index}`"
        :xml="viewerData.bpmnXml"
        :style="{height: '60vh'}"
      />
    </el-dialog>
  </div>
</template>

<script>
import { listEquipmentOperation, getEquipmentOperation, delEquipmentOperation, addEquipmentOperation, updateEquipmentOperation, saveModel, getBpmnXml } from "@/api/system/equipmentOperation";
import { listEquipment } from "@/api/system/equipment";
import { listModelOperation } from "@/api/system/modelOperation";
import { listEquipmentAtomOperation } from "@/api/system/equipmentAtomOperation";
import ProcessDesigner from '@/components/ProcessDesigner';
import ProcessViewer from '@/components/ProcessViewer';

export default {
  name: "EquipmentOperation",
  components: {
    ProcessDesigner,
    ProcessViewer,
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
      // 设备操作表格数据
      equipmentOperationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        moId: undefined,
        eqId: this.$route.query.eqId,
        eoDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        eoId: [
          { required: true, message: "设备操作ID不能为空", trigger: "blur" }
        ],
        moId: [
          { required: true, message: "对应模型操作ID不能为空", trigger: "blur" }
        ],
        eqId: [
          { required: true, message: "所属设备ID不能为空", trigger: "blur" }
        ],
        eoName: [
          { required: true, message: "操作名称不能为空", trigger: "blur" }
        ],
      },
      // 设备列表
      equipmentList: [],
      // 模型操作列表
      modelOperationList: [],
      atomOperationList: [],
      // 1-按设备查看设备操作（未发布，可修改）
      // 2-按设备查看设备操作（不可修改）
      mode: 0,
      // 页面顶部提示
      hint: '',
      // 设计窗口是否打开
      designerOpen: false,
      // 设计器相关数据
      designerData: {
        loading: false,
        bpmnXml: '',
        modelId: null,
        form: {
          processName: null,
          processKey: null
        }
      },
      // 查看窗口是否打开
      viewerOpen: false,
      // 查看器相关数据
      viewerData: {
        title: '',
        loading: false,
        index: undefined,
        bpmnXml: ''
      },
    };
  },
  created() {
    // 检查来源
    if (this.$route.query.eqId) {
      this.mode = 1;
    } else {
      this.$modal.msgError("请重新进入此页面");
      this.$router.back();
    }
    this.getExtraList();
  },
  methods: {
    // 查询列表
    getExtraList() {
      listEquipment().then(response => {
        this.equipmentList = response.rows;
        let equipment = response.rows.find(ele => ele.eqId === this.$route.query.eqId)
        // 获取设备所属设备模型的模型操作
        listModelOperation({ emId: equipment.emId, moDelete: 0 }).then(response => {
          this.modelOperationList = response.rows;
          // 获取设备的原子操作
          listEquipmentAtomOperation({ eqId: equipment.eqId, moDelete: 0 }).then(response => {
            this.atomOperationList = response.rows;
            this.getList();
          })
        })
        if (this.mode === 1) {
          // 设置筛选提示
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
    /** 查询设备操作列表 */
    getList() {
      this.loading = true;
      listEquipmentOperation(this.queryParams).then(response => {
        this.equipmentOperationList = response.rows;
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
        eoId: undefined,
        moId: undefined,
        eqId: undefined,
        eoName: undefined,
        eoExecTime: undefined,
        eoDelete: undefined,
        eoDesc: undefined,
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
      this.ids = selection.map(item => item.eoId)
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
      this.title = "添加设备操作";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const eoId = row.eoId || this.ids
      getEquipmentOperation(eoId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改设备操作";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.eoId != null) {
            updateEquipmentOperation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addEquipmentOperation(this.form).then(response => {
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
      const eoIds = row.eoId || this.ids;
      this.$modal.confirm('是否确认删除设备操作编号为"' + eoIds + '"的数据项？').then(() => {
        this.loading = true;
        return delEquipmentOperation(eoIds);
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
      this.download('system/equipmentOperation/export', {
        ...this.queryParams
      }, `equipmentOperation_${new Date().getTime()}.xlsx`)
    },
    /** 设计按钮操作 */
    handleDesigner(row) {
      this.designerData.title = "设备操作流程设计 - " + row.eoName;
      this.designerData.modelId = "model_" + row.eoId;
      this.designerData.form = {
        processName: row.eoName,
        processKey: "process_" + row.eoId
      }
      if (row.eoModel) {
        this.designerData.loading = true;
        getBpmnXml(row.eoModel).then(response => {
          this.designerData.bpmnXml = response.data || '';
          this.designerData.loading = false;
          this.designerOpen = true;
        })
      } else {
        this.designerData.bpmnXml = '';
        this.designerOpen = true;
      }
    },
    // 保存流程按钮操作
    onSaveDesigner(bpmnXml) {
      this.bpmnXml = bpmnXml;
      this.$confirm("是否保存设备操作流程？", "提示", {
        distinguishCancelAndClose: true,
        confirmButtonText: '是',
        cancelButtonText: '否'
      }).then(() => {
        this.designerData.loading = true;
        saveModel(bpmnXml).then(response => {
          this.designerOpen = false
          this.getList();
          this.$modal.msgSuccess("保存成功");
        }).finally(() => {
          this.designerData.loading = false;
        })
      }).catch(action => {
      })
    },
    /** 查看流程按钮操作 */
    handleViewer(row) {
      this.viewerData.loading = true
      this.viewerData.title = row.eoName
      this.viewerData.index = row.eoModel
      this.viewerOpen = true
      getBpmnXml(row.eoModel).then(response => {
        this.viewerData.bpmnXml = response.data || ''
        this.viewerData.loading = false
      })
    },
  }
};
</script>
