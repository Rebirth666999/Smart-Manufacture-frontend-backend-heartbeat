<template>
  <div class="panel-tab__content">
    <el-form class="penal-form" label-width="70px">
      <!-- 显示参数表格 -->
      <el-table :data="paramList" size="mini" max-height="240" border fit>
        <el-table-column label="名称" prop="param.eospaName" show-overflow-tooltip />
        <el-table-column label="类型" prop="param.eospaType" show-overflow-tooltip>
          <template slot-scope="scope">
            <dict-tag :options="dict.type.ices_param_type" :value="scope.row.param.eospaType"/>
          </template>
        </el-table-column>
        <el-table-column label="位置" prop="param.eospaPos" show-overflow-tooltip>
          <template slot-scope="scope">
            <dict-tag :options="dict.type.ices_param_position" :value="scope.row.param.eospaPos"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="90px">
          <template slot-scope="{ row, $index }">
            <el-button size="mini" type="text" @click="openParamsForm(row, $index)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 查看参数窗口 -->
      <el-dialog :visible.sync="viewOpen" title="查看参数" width="600px" append-to-body destroy-on-close>
        <el-descriptions border :column="1">
          <el-descriptions-item label="参数名称">
            {{ this.paramSelect.param.eospaName || '' }}
          </el-descriptions-item>
          <el-descriptions-item label="父级参数">
            {{ this.paramSelect.param.eospaCodeParent || '' }}
          </el-descriptions-item>
          <el-descriptions-item label="参数类型">
            <dict-tag :options="dict.type.ices_param_type" :value="this.paramSelect.param.eospaType"/>
          </el-descriptions-item>
          <el-descriptions-item label="参数位置">
            <dict-tag :options="dict.type.ices_param_position" :value="this.paramSelect.param.eospaPos"/>
          </el-descriptions-item>
          <el-descriptions-item label="默认值">
            {{ this.paramSelect.param.eospaValue || '' }}
          </el-descriptions-item>
          <el-descriptions-item label="配置参数值">
            {{ this.paramSelect.value.dtpaValue || '' }}
          </el-descriptions-item>
        </el-descriptions>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "DeviceTaskParamView",
  dicts: ['ices_param_type', 'ices_param_position'],
  props: {
    element: Object,
    eosList: Array,
    eospaList: Array,
    dtList: Array,
    dtpaList: Array
  },
  data() {
    return {
      // 参数列表
      paramList: [],
      // 参数值列表
      paramValueList: [],
      // 是否显示弹出框
      viewOpen: false,
      // 选中的参数
      paramSelect: {
        param: {
          eoCode: undefined,
          eosCode: undefined,
          eospaCode: undefined,
          eospaCodeParent: undefined,
          eospaDelete: undefined,
          eospaId: undefined,
          eospaName: undefined,
          eospaPos: undefined,
          eospaType: undefined,
          eospaValue: undefined
        },
        value: {
          dtCode: undefined,
          dtpaCode: undefined,
          dtpaDelete: undefined,
          dtpaId: undefined,
          dtapValue: undefined,
          eospaCode: undefined,
          mtCode: undefined
        }
      }
    };
  },
  watch: {
    // 切换选择的对象时，获取信息
    element: {
      immediate: true,
      handler: function(element) {
        if (element.type === 'bpmn:ServiceTask') {
          const tasks = this.dtList.filter(ele => ele.dtModel === element.id)
          this.paramList = []
          if (tasks.length > 0) {
            this.task = tasks[0]
            this.paramValueList = this.dtpaList.filter(ele => ele.dtCode === tasks[0].dtCode)
            this.paramValueList.forEach(paramValue => {
              const eospa = this.eospaList.find(ele => ele.eospaCode === paramValue.eospaCode)
              this.paramList.push({
                param: eospa,
                value: paramValue
              })
            });
          }
        }
      }
    },
  },
  methods: {
    // 打开查看参数窗口
    openParamsForm(attr, index) {
      console.log(attr)
      this.paramSelect = attr
      this.viewOpen = true
    }
  },
  beforeDestroy() {
    this.bpmnElement = null;
  }
};
</script>
<style scoped>
.penal-form {
  padding: 0 8px;
}
</style>
