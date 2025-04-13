<template>
  <div class="panel-tab__content">
    <el-table :data="demandList" size="mini" max-height="240" border fit>
      <el-table-column label="序号" width="50px" type="index" />
      <el-table-column label="名称" prop="maCode" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ maList.find(ele => ele.maCode === scope.row.maCode).maName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="数量" prop="pmDemand" show-overflow-tooltip />
      <el-table-column label="操作" width="90px">
        <template slot-scope="{ row, $index }">
          <el-button size="mini" type="text" @click="editDemand(row, $index)">编辑</el-button>
          <el-divider direction="vertical" />
          <el-button size="mini" type="text" style="color: #ff4d4f" @click="removeDemand(row, $index)">移除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="element-drawer__button">
      <el-button size="mini" type="primary" icon="el-icon-plus" @click="addDemand">添加原料需求</el-button>
    </div>

    <!-- 添加/修改原料需求窗口 -->
    <el-dialog :visible.sync="editOpen" title="参数配置" width="600px" append-to-body destroy-on-close>
      <el-form :model="form" label-width="130px" size="mini" ref="demandFormRef" @submit.native.prevent :rules="rules">
        <el-form-item label="原料：" prop="maCode">
          <el-select v-model="form.maCode" placeholder="请选择原料" style="width: 100%;">
            <el-option
              v-for="item in maList"
              :key="item.maCode"
              :label="item.maName"
              :value="item.maCode"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量：" prop="pmDemand">
          <el-input v-model="form.pmDemand" placeholder="请输入数量" clearable />
        </el-form-item>
      </el-form>
      <template slot="footer">
        <el-button size="mini" @click="cancel">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveDemand">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script>

export default {
  name: "EquipmentOperationStepBaseInfo",
  props: {
    id: String,
    maList: Array
  },
  data() {
    return {
      // 材料需求列表
      demandList: [],
      // 添加修改窗口是否打开
      editOpen: false,
      // 原料需求表单
      form: {
        maCode: undefined,
        pmDemand: undefined
      },
      // 校验规则
      rules: {
        maCode: [
          { required: true, message: "原料不能为空", trigger: "blur" }
        ],
        pmDemand: [
          { required: true, message: "数量不能为空", trigger: "blur" }
        ],
      },
      // 正在编辑的条目下标
      editIndex: -1
    };
  },
  watch: {
    id: {
      immediate: false,
      handler: function (val) {
        if (val && val.length) {
          this.getDemand()
        }
      }
    }
  },
  methods: {
    // 更新列表显示
    getDemand() {
      this.demandList = JSON.parse(window.bpmnInstances.bpmnElement.businessObject.$attrs.demand || '[]')
    },
    // 新增原料需求
    addDemand() {
      this.editIndex = -1
      this.form.maCode = undefined
      this.form.pmDemand = undefined
      this.editOpen = true
    },
    // 编辑原料需求
    editDemand(row, idx) {
      this.editIndex = idx
      this.form.maCode = row.maCode
      this.form.pmDemand = row.pmDemand
      this.editOpen = true
    },
    // 删除原料需求
    removeDemand(row, idx) {
      this.$confirm(`确认移除原料需求？`, "提示", {
        confirmButtonText: "确 认",
        cancelButtonText: "取 消"
      }).then(() => {
        this.demandList.splice(idx, 1);
        this.updateDemand()
      }).catch(() => console.info("操作取消"));
    },
    // 取消添加/修改
    cancel() {
      this.editOpen = false
    },
    // 保存原料需求 
    saveDemand() {
      this.$refs["demandFormRef"].validate(valid => {
        if (valid) {
          if (this.editIndex !== -1) {
            if (this.demandList.filter(ele => ele.maCode === this.form.maCode).length > 1) {
              // 如果已存在对应原料的需求，则拒绝更新
              this.$modal.msgError("已存在相同原料的需求");
              return
            }
            // 修改
            this.demandList[this.editIndex].maCode = this.form.maCode
            this.demandList[this.editIndex].pmDemand = this.form.pmDemand
          } else {
            if (this.demandList.filter(ele => ele.maCode === this.form.maCode).length > 0) {
              // 如果已存在对应原料的需求，则拒绝更新
              this.$modal.msgError("已存在相同原料的需求");
              return
            }
            // 添加
            this.demandList.push({
              maCode: this.form.maCode,
              pmDemand: this.form.pmDemand
            })
          }
          this.updateDemand()
        }
      })
    },
    // 更新原料需求到流程图
    updateDemand() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { demand: JSON.stringify(this.demandList) }
      )
      this.editOpen = false
      setTimeout(() => this.getDemand(), 100)
    }
  },
  beforeDestroy() {
    this.bpmnElement = null
  }
};
</script>
