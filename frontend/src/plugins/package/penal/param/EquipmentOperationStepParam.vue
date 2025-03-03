<template>
  <div class="panel-tab__content">
    <!-- 显示参数表格 -->
    <el-table :data="paramList" size="mini" max-height="240" border fit>
      <el-table-column label="序号" width="50px" type="index" />
      <el-table-column label="参数名称" prop="name" min-width="100px" show-overflow-tooltip />
      <el-table-column label="父级参数序号" prop="parent" min-width="100px" show-overflow-tooltip />
      <el-table-column label="操作" width="90px">
        <template slot-scope="{ row, $index }">
          <el-button size="mini" type="text" @click="openAttributesForm(row, $index)">编辑</el-button>
          <el-divider direction="vertical" />
          <el-button size="mini" type="text" style="color: #ff4d4f" @click="removeAttributes(row, $index)">移除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="element-drawer__button">
      <el-button size="mini" type="primary" icon="el-icon-plus" @click="openAttributesForm(null, -1)">添加参数</el-button>
    </div>

    <!-- 添加/修改参数窗口 -->
    <el-dialog :visible.sync="propertyFormModelVisible" title="参数配置" width="600px" append-to-body destroy-on-close>
      <el-form :model="propertyForm" label-width="130px" size="mini" ref="attributeFormRef" @submit.native.prevent :rules="rules">
        <el-form-item label="参数名称：" prop="name">
          <el-input v-model="propertyForm.name" clearable />
        </el-form-item>
        <el-form-item label="父级参数序号：" prop="parent">
          <el-input v-model="propertyForm.parent" clearable />
        </el-form-item>
      </el-form>
      <template slot="footer">
        <el-button size="mini" @click="propertyFormModelVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveAttribute">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "EquipmentOperationStepParam",
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
        name: [
          { required: true, message: "参数名称不能为空", trigger: "blur" }
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
    openAttributesForm(attr, index) {
      this.editingPropertyIndex = index;
      // 读取要编辑的参数
      this.propertyForm = index === -1 ? {} : JSON.parse(JSON.stringify(attr));
      this.propertyFormModelVisible = true;
      this.$nextTick(() => {
        if (this.$refs["attributeFormRef"]) this.$refs["attributeFormRef"].clearValidate();
      });
    },
    // 删除参数
    removeAttributes(attr, index) {
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
    saveAttribute() {
      this.$refs["attributeFormRef"].validate(valid => {
        if (valid) {
          const { name, parent } = this.propertyForm;
          // 更新显示数组
          if (this.editingPropertyIndex !== -1) {
            // 修改参数
            this.paramList[this.editingPropertyIndex].name = name
            this.paramList[this.editingPropertyIndex].parent = parseInt(parent) || 0
          } else {
            // 新建参数
            this.paramList.push({
              name: name,
              parent: parseInt(parent) || 0
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
