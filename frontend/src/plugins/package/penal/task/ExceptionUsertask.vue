<template>
  <div class="panel-tab__content">
    <div class="element-property input-property">
      <div class="element-property__label">处理人：</div>
      <div class="element-property__value">
        <el-select
          multiple
          size="mini"
          v-model="user"
          @change="updateUser"
          @blur="updateUser"
        >
          <el-option
            v-for="item in userList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          >
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="element-property input-property">
      <div class="long-label">从此步骤结束：</div>
      <div class="element-property__value">
        <el-switch
          v-model="endFromThis"
          active-text="允许"
          inactive-text="不允许"
          active-value="true"
          inactive-value="false"
          @change="updateEnd"
        />
      </div>
    </div>
    <div class="element-property input-property">
      <div class="long-label">从此步骤跳转：</div>
      <div class="element-property__value">
        <el-switch
          v-model="jumpFromThis"
          active-text="允许"
          inactive-text="不允许"
          active-value="true"
          inactive-value="false"
          @change="updateJump"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "EquipmentOperationStepProperties",
  props: {
    id: String,
    userList: Array
  },
  data() {
    return {
      user: [],
      endFromThis: 'true',
      jumpFromThis: 'true'
    };
  },
  watch: {
    // 切换选择的对象时，获取信息
    id: {
      immediate: true,
      handler: function(id) {
        const userStr = window.bpmnInstances.bpmnElement.businessObject.$attrs.user
        if (userStr) {
          this.user = userStr.split(",")
          this.user.pop()
          // 转换为number
          for (let i in this.user) {
            this.user[i] = parseInt(this.user[i])
          }
        } else {
          this.user = []
        }

        const endFromThis = window.bpmnInstances.bpmnElement.businessObject.$attrs.endFromThis
        if (endFromThis) {
          this.endFromThis = endFromThis
        } else {
          this.endFromThis = 'true'
          this.updateEnd()
        }
        const jumpFromThis = window.bpmnInstances.bpmnElement.businessObject.$attrs.jumpFromThis
        if (jumpFromThis) {
          this.jumpFromThis = jumpFromThis
        } else {
          this.jumpFromThis = 'true'
          this.updateJump()
        }
      }
    }
  },
  methods: {
    // 更新属性：处理人
    updateUser() {
      let result = ""
      this.user.forEach(ele => {
        result += ele
        result += ","
      })
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { user: result }
      );
    },
    // 更新属性：是否允许从此步骤结束
    updateEnd() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { endFromThis: this.endFromThis }
      );
    },
    // 更新属性：是否允许从此步骤跳转
    updateJump() {
      window.bpmnInstances.modeling.updateProperties(
        window.bpmnInstances.bpmnElement,
        { jumpFromThis: this.jumpFromThis }
      );
    }
  },
  beforeDestroy() {
    this.bpmnElement = null;
  }
};
</script>
<style scoped>
.long-label {
  display: block;
  width: 37%;
  text-align: right;
  overflow: hidden;
  padding-right: 2px;
  line-height: 32px;
  font-size: 14px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
</style>