<template>
  <div class="panel-tab__content">
    <!-- 显示参数表格 -->
    <el-table :data="paramList" size="mini" max-height="240" border fit>
      <el-table-column label="序号" width="50px" type="index" />
      <el-table-column label="名称" prop="eospaName" show-overflow-tooltip />
      <el-table-column label="类型" prop="eospaType" show-overflow-tooltip>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_param_type" :value="scope.row.eospaType"/>
        </template>
      </el-table-column>
      <el-table-column label="位置" prop="eospaPos" show-overflow-tooltip>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_param_position" :value="scope.row.eospaPos"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="90px">
        <template slot-scope="{ row, $index }">
          <el-button size="mini" type="text" @click="openParamsForm(row, $index)">编辑</el-button>
          <el-divider direction="vertical" />
          <el-button size="mini" type="text" style="color: #ff4d4f" @click="removeParam(row, $index)">移除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="element-drawer__button">
      <el-button size="mini" type="primary" icon="el-icon-plus" @click="openParamsForm(null, -1)">添加参数</el-button>
    </div>

    <!-- 添加/修改参数窗口 -->
    <el-dialog :visible.sync="propertyFormModelVisible" title="参数配置" width="600px" append-to-body destroy-on-close>
      <el-form :model="propertyForm" label-width="130px" size="mini" ref="attributeFormRef" @submit.native.prevent :rules="rules">
        <el-form-item label="参数名称：" prop="eospaName">
          <el-input v-model="propertyForm.eospaName" clearable />
        </el-form-item>
        <el-form-item label="父级参数序号：" prop="eospaCodeParent">
          <el-input v-model="propertyForm.eospaCodeParent" clearable />
        </el-form-item>
        <el-form-item label="参数类型：" prop="eospaType">
          <el-select v-model="propertyForm.eospaType" placeholder="请选择参数类型" style="width: 100%;">
            <el-option
              v-for="dict in dict.type.ices_param_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="参数位置：" prop="eospaPos">
          <el-select v-model="propertyForm.eospaPos" placeholder="请选择参数位置" style="width: 100%;">
            <el-option
              v-for="dict in dict.type.ices_param_position"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="默认值：" prop="eospaValue">
          <el-input v-model="propertyForm.eospaValue" clearable />
        </el-form-item>
      </el-form>
      <template slot="footer">
        <el-button size="mini" @click="propertyFormModelVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveParam">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "EquipmentOperationStepParam",
  dicts: ['ices_param_type', 'ices_param_position'],
  props: {
    id: String,
    type: String
  },
  inject: {
    prefix: "prefix",
    width: "width"
  },
  data() {
    return {
      // 参数列表
      paramList: [],
      // 新增/修改参数表单
      propertyForm: {},
      // 当前选择的参数序号，从1开始
      editingPropertyIndex: -1,
      // 是否显示新增/修改窗口
      propertyFormModelVisible: false,
      // 表单校验
      rules: {
        eospaName: [
          { required: true, message: "参数名称不能为空", trigger: "blur" }
        ],
        eospaType: [
          { required: true, message: "参数类型不能为空", trigger: "blur" }
        ],
        eospaPos: [
          { required: true, message: "参数位置不能为空", trigger: "blur" }
        ],
      },
    };
  },
  watch: {
    id: {
      immediate: true,
      handler(val) {
        val && val.length && this.resetParamsList();
      }
    }
  },
  methods: {
    // 查看已有的参数并更新列表显示
    resetParamsList() {
      this.paramList = JSON.parse(window.bpmnInstances.bpmnElement.businessObject.$attrs.params || '[]')
    },
    // 打开新增/修改参数窗口
    openParamsForm(attr, index) {
      this.editingPropertyIndex = index;
      // 读取要编辑的参数
      this.propertyForm = index === -1 ? {} : JSON.parse(JSON.stringify(attr));
      this.propertyFormModelVisible = true;
      this.$nextTick(() => {
        if (this.$refs["attributeFormRef"]) this.$refs["attributeFormRef"].clearValidate();
      });
    },
    // 删除参数
    removeParam(attr, index) {
      this.$confirm("确认移除该参数吗？", "提示", {
        confirmButtonText: "确 认",
        cancelButtonText: "取 消"
      }).then(() => {
          this.paramList.splice(index, 1);
          this.updateParams()
        })
        .catch(() => console.info("操作取消"));
    },
    // 保存单个参数
    saveParam() {
      this.$refs["attributeFormRef"].validate(valid => {
        if (valid) {
          const { eospaName, eospaCodeParent, eospaType, eospaPos, eospaValue } = this.propertyForm
          // 更新显示数组
          if (this.editingPropertyIndex !== -1) {
            // 修改参数
            this.paramList[this.editingPropertyIndex].eospaName = eospaName
            this.paramList[this.editingPropertyIndex].eospaCodeParent = eospaCodeParent
            this.paramList[this.editingPropertyIndex].eospaType = eospaType
            this.paramList[this.editingPropertyIndex].eospaPos = eospaPos
            this.paramList[this.editingPropertyIndex].eospaValue = eospaValue
          } else {
            // 新建参数
            this.paramList.push({
              eospaName: eospaName,
              eospaCodeParent: eospaCodeParent,
              eospaType: eospaType,
              eospaPos: eospaPos,
              eospaValue: eospaValue
            })
          }
          this.updateParams()
        }
      })
    },
    // 保存参数的列表
    updateParams() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { params: JSON.stringify(this.paramList) }
      )
      this.propertyFormModelVisible = false
      setTimeout(() => this.resetParamsList(), 100)
    }
  }
};
</script>
