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
          <el-button size="mini" type="text" @click="openParamsForm(row, $index)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 查看参数窗口 -->
    <el-dialog :visible.sync="propertyFormModelVisible" title="参数配置" width="600px" append-to-body destroy-on-close>
      <el-descriptions border :column="1">
        <el-descriptions-item label="参数名称">
          {{ editParam.eospaName || '' }}
        </el-descriptions-item>
        <el-descriptions-item label="父级参数序号">
          {{ editParam.eospaIdParent || 0 }}
        </el-descriptions-item>
        <el-descriptions-item label="参数类型">
          <dict-tag :options="dict.type.ices_param_type" :value="editParam.eospaType"/>
        </el-descriptions-item>
        <el-descriptions-item label="参数位置">
          <dict-tag :options="dict.type.ices_param_position" :value="editParam.eospaPos"/>
        </el-descriptions-item>
        <el-descriptions-item label="默认值">
          {{ editParam.eospaValue || 0 }}
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "EquipmentOperationStepParam",
  dicts: ['ices_param_type', 'ices_param_position'],
  props: {
    element: Object
  },
  data() {
    return {
      // 参数列表
      paramList: [],
      // 是否显示新增/修改窗口
      propertyFormModelVisible: false,
      // 查看参数的属性
      editParam: {
        eospaName: '',
        eospaIdParent: 0,
        eospaType: '',
        eospaPos: '',
        eospaValue: ''
      }
    };
  },
  watch: {
    element: {
      immediate: true,
      handler(element) {
        if (element.type === 'bpmn:Task') {
          this.paramList = JSON.parse(element.businessObject.$attrs.params || '[]')
        }
      }
    }
  },
  methods: {
    // 打开新增/修改参数窗口
    openParamsForm(attr, index) {
      this.editParam = this.paramList[index]
      this.propertyFormModelVisible = true;
    }
  }
};
</script>
