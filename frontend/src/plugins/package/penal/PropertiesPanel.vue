<template>
  <div class="process-panel__container" :style="{ width: `${this.width}px` }">
    <el-collapse v-model="activeTab">
      <!-- ruoyi表单审批流程 -->
      <el-collapse-item name="base" v-if="mode === 0">
        <div slot="title" class="panel-tab__title"><i class="el-icon-info"></i>常规</div>
        <element-base-info :id-edit-disabled="idEditDisabled" :business-object="elementBusinessObject" :type="elementType" />
      </el-collapse-item>
      <el-collapse-item name="condition" v-if="mode === 0 && elementType === 'Process'" key="message">
        <div slot="title" class="panel-tab__title"><i class="el-icon-s-comment"></i>消息与信号</div>
        <signal-and-massage />
      </el-collapse-item>
      <el-collapse-item name="condition" v-if="mode === 0 && conditionFormVisible" key="condition">
        <div slot="title" class="panel-tab__title"><i class="el-icon-s-promotion"></i>流转条件</div>
        <flow-condition :business-object="elementBusinessObject" :type="elementType" />
      </el-collapse-item>
      <el-collapse-item name="condition" v-if="mode === 0 && formVisible" key="form">
        <div slot="title" class="panel-tab__title"><i class="el-icon-s-order"></i>表单</div>
        <element-form :id="elementId" :type="elementType" />
      </el-collapse-item>
      <el-collapse-item name="task" v-if="mode === 0 && elementType.indexOf('Task') !== -1" key="task">
        <div slot="title" class="panel-tab__title"><i class="el-icon-s-claim"></i>任务</div>
        <element-task :id="elementId" :type="elementType" />
      </el-collapse-item>
      <el-collapse-item name="multiInstance" v-if="mode === 0 && elementType.indexOf('Task') !== -1 && elementType !== 'UserTask'" key="multiInstance">
        <div slot="title" class="panel-tab__title"><i class="el-icon-s-help"></i>多实例</div>
        <element-multi-instance :business-object="elementBusinessObject" :type="elementType" />
      </el-collapse-item>
      <el-collapse-item name="listeners" v-if="mode === 0" key="listeners">
        <div slot="title" class="panel-tab__title"><i class="el-icon-message-solid"></i>执行监听器</div>
        <element-listeners :id="elementId" :type="elementType" />
      </el-collapse-item>
      <el-collapse-item name="taskListeners" v-if="mode === 0 && elementType === 'UserTask'" key="taskListeners">
        <div slot="title" class="panel-tab__title"><i class="el-icon-message-solid"></i>任务监听器</div>
        <user-task-listeners :id="elementId" :type="elementType" />
      </el-collapse-item>
      <el-collapse-item name="extensions" v-if="mode === 0" key="extensions">
        <div slot="title" class="panel-tab__title"><i class="el-icon-circle-plus"></i>扩展属性</div>
        <element-properties :id="elementId" :type="elementType" />
      </el-collapse-item>
      <el-collapse-item name="other" v-if="mode === 0" key="other">
        <div slot="title" class="panel-tab__title"><i class="el-icon-s-promotion"></i>其他</div>
        <element-other-config :id="elementId" />
      </el-collapse-item>
      <!-- 设备操作流程 -->
      <el-collapse-item
        name="eosBasic"
        v-if="mode === 1"
        key="eosBasic"
      >
        <div slot="title" class="panel-tab__title">
          <i class="el-icon-info"></i>常规
        </div>
        <eos-base-info :id-edit-disabled="idEditDisabled" :business-object="elementBusinessObject" :type="elementType" />
      </el-collapse-item>
      <el-collapse-item
        name="eosProperties"
        v-if="mode === 1 && elementType === 'Task'"
        key="eosProperties"
      >
        <div slot="title" class="panel-tab__title">
          <i class="el-icon-s-promotion"></i>步骤属性
        </div>
        <eos-properties :id="elementId" :list="extraList"/>
      </el-collapse-item>
      <el-collapse-item
        name="eosParam"
        v-if="mode === 1 && elementType === 'Task'"
        key="eosParam"
      >
        <div slot="title" class="panel-tab__title">
          <i class="el-icon-s-operation"></i>步骤参数
        </div>
        <eos-param :id="elementId" :type="elementType" />
      </el-collapse-item>
    </el-collapse>
  </div>
</template>
<script>
import ElementBaseInfo from "./base/ElementBaseInfo";
import EosBaseInfo from "./base/EquipmentOperationStepBaseInfo";
import ElementOtherConfig from "./other/ElementOtherConfig";
import ElementTask from "./task/ElementTask";
import ElementMultiInstance from "./multi-instance/ElementMultiInstance";
import FlowCondition from "./flow-condition/FlowCondition";
import SignalAndMassage from "./signal-message/SignalAndMessage";
import ElementListeners from "./listeners/ElementListeners";
import ElementProperties from "./properties/ElementProperties";
import EosProperties from "./properties/EquipmentOperationStepProperties"
import ElementForm from "./form/ElementForm";
import UserTaskListeners from "./listeners/UserTaskListeners";
import EosParam from "./param/EquipmentOperationStepParam";
/**
 * 自定义bpmn画图侧边栏
 */
export default {
  name: "BpmnPropertiesPanel",
  components: {
    UserTaskListeners,
    ElementForm,
    ElementProperties,
    ElementListeners,
    SignalAndMassage,
    FlowCondition,
    ElementMultiInstance,
    ElementTask,
    ElementOtherConfig,
    ElementBaseInfo,
    EosBaseInfo,
    EosProperties,
    EosParam
  },
  componentName: "BpmnPropertiesPanel",
  props: {
    bpmnModeler: Object,
    prefix: {
      type: String,
      default: "camunda"
    },
    width: {
      type: Number,
      default: 480
    },
    idEditDisabled: {
      type: Boolean,
      default: false
    },
    // mode代表设计器职能，决定可以添加的操作类型
    // 0-ruoyi原先的表单审批流程
    // 1-设备操作流程
    // 2-产品工艺流程
    mode: {
      type: Number,
      required: true
    },
    extraList: {
      type: Array,
      required: false
    }
  },
  provide() {
    return {
      prefix: this.prefix,
      width: this.width
    };
  },
  data() {
    return {
      activeTab: "base",
      elementId: "",
      elementType: "",
      elementBusinessObject: {}, // 元素 businessObject 镜像，提供给需要做判断的组件使用
      conditionFormVisible: false, // 流转条件设置
      formVisible: false // 表单配置
    };
  },
  watch: {
    elementId: {
      handler() {
        this.activeTab = "base";
      }
    }
  },
  created() {
    this.initModels();
  },
  methods: {
    initModels() {
      // 初始化 modeler 以及其他 moddle
      if (!this.bpmnModeler) {
        // 避免加载时 流程图 并未加载完成
        this.timer = setTimeout(() => this.initModels(), 10);
        return;
      }
      if (this.timer) clearTimeout(this.timer);
      window.bpmnInstances = {
        modeler: this.bpmnModeler,
        modeling: this.bpmnModeler.get("modeling"),
        moddle: this.bpmnModeler.get("moddle"),
        eventBus: this.bpmnModeler.get("eventBus"),
        bpmnFactory: this.bpmnModeler.get("bpmnFactory"),
        elementFactory: this.bpmnModeler.get("elementFactory"),
        elementRegistry: this.bpmnModeler.get("elementRegistry"),
        replace: this.bpmnModeler.get("replace"),
        selection: this.bpmnModeler.get("selection")
      };
      this.getActiveElement();
    },
    getActiveElement() {
      // 初始第一个选中元素 bpmn:Process
      this.initFormOnChanged(null);
      this.bpmnModeler.on("import.done", e => {
        this.initFormOnChanged(null);
      });
      // 监听选择事件，修改当前激活的元素以及表单
      this.bpmnModeler.on("selection.changed", ({ newSelection }) => {
        this.initFormOnChanged(newSelection[0] || null);
      });
      this.bpmnModeler.on("element.changed", ({ element }) => {
        // 保证 修改 "默认流转路径" 类似需要修改多个元素的事件发生的时候，更新表单的元素与原选中元素不一致。
        if (element && element.id === this.elementId) {
          this.initFormOnChanged(element);
        }
      });
    },
    // 在选择的对象发生变化时，初始化数据
    initFormOnChanged(element) {
      // 当前对象
      let activatedElement = element;
      if (!activatedElement) {
        activatedElement =
          window.bpmnInstances.elementRegistry.find(el => el.type === "bpmn:Process") ??
          window.bpmnInstances.elementRegistry.find(el => el.type === "bpmn:Collaboration");
      }
      if (!activatedElement) return;
      // 打印信息
      console.log(`
              ----------
      select element changed:
                id:  ${activatedElement.id}
              type:  ${activatedElement.businessObject.$type}
              ----------
              `);
      console.log("businessObject: ", activatedElement.businessObject);
      // 获取属性
      window.bpmnInstances.bpmnElement = activatedElement;
      this.bpmnElement = activatedElement;
      this.elementId = activatedElement.id;
      this.elementType = activatedElement.type.split(":")[1] || "";
      this.elementBusinessObject = JSON.parse(JSON.stringify(activatedElement.businessObject));
      // 依据类型，显示或隐藏各个条目
      if (this.mode === 0) {
        // ruoyi表单审批流程相关
        this.conditionFormVisible = !!(
          this.elementType === "SequenceFlow" &&
          activatedElement.source &&
          activatedElement.source.type.indexOf("StartEvent") === -1
        );
        this.formVisible = this.elementType === "UserTask" || this.elementType ===  "StartEvent";
      } else if (this.mode === 1) {
        // 设备操作流程相关
        // console.log(this.extraList)
      }
    },
    beforeDestroy() {
      window.bpmnInstances = null;
    }
  }
};
</script>
