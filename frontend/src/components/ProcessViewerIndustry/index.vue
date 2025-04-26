<template>
  <div class="process-viewer">
    <div class="process-canvas" style="height: 100%;" ref="processCanvas" v-show="!isLoading" />
    <!-- 画布视角控件：放大缩小按钮 -->
    <div style="position: absolute; top: 0px; left: 0px;">
      <el-row type="flex" justify="start">
        <el-button type="primary" v-if="mode === 3" @click="submitDeviceTask">提交设备任务</el-button>
        <el-button-group key="scale-control" size="medium">
          <el-button size="medium" type="default" :plain="true" :disabled="defaultZoom <= 0.3" icon="el-icon-zoom-out" @click="processZoomOut()" />
          <el-button size="medium" type="default" style="width: 90px;">{{ Math.floor(this.defaultZoom * 10 * 10) + "%" }}</el-button>
          <el-button size="medium" type="default" :plain="true" :disabled="defaultZoom >= 3.9" icon="el-icon-zoom-in" @click="processZoomIn()" />
          <el-button size="medium" type="default" icon="el-icon-c-scale-to-original" @click="processReZoom()" />
          <slot />
        </el-button-group>
      </el-row>
      <!-- 选择轮次 -->
      <el-card shadow="never" v-if="mode === 4">
        <el-form>
          <el-form-item label="查看轮次">
            <el-input-number v-model="viewRound" :min="1" :max="maxRound" @change="paint" />
          </el-form-item>
        </el-form>
      </el-card>
      <!-- 图例 -->
      <el-card shadow="never" header="图例" v-if="mode === 4">
        <div class="legend">
          <div>当前轮次<br/>已完成</div>
          <div class="legend-box green-box"></div>
        </div>
        <div class="legend">
          <div>当前轮次<br/>进行中</div>
          <div class="legend-box blue-box"></div>
        </div>
        <div class="legend">
          <div>当前轮次<br/>未开始</div>
          <div class="legend-box white-box"></div>
        </div>
      </el-card>
    </div>
    <!-- 右侧边栏显示属性信息 -->
    <div class="viewer-penal">
      <el-collapse v-model="activeTab">
        <!-- 设备操作流程 -->
        <el-collapse-item
          name="eosBasic"
          v-if="mode === 1"
          key="eosBasic"
        >
          <div slot="title" class="panel-tab__title">
            <i class="el-icon-info"></i>常规
          </div>
          <eos-base-info :element="element" />
        </el-collapse-item>
        <el-collapse-item
          name="eosProperties"
          v-if="mode === 1 && element.type === 'bpmn:Task'"
          key="eosProperties"
        >
          <div slot="title" class="panel-tab__title">
            <i class="el-icon-s-promotion"></i>步骤属性
          </div>
          <eos-properties :element="element" :list="extraList.eaoList"/>
        </el-collapse-item>
        <el-collapse-item
          name="eosParam"
          v-if="mode === 1 && element.type === 'bpmn:Task'"
          key="eosParam"
        >
          <div slot="title" class="panel-tab__title">
            <i class="el-icon-s-operation"></i>步骤参数
          </div>
          <eos-param :element="element" />
        </el-collapse-item>

        <!-- 产品工艺流程 -->
        <el-collapse-item
          name="procBasic"
          v-if="mode === 2 || mode === 3 || mode === 4"
          key="procBasic"
        >
          <div slot="title" class="panel-tab__title">
            <i class="el-icon-info"></i>常规
          </div>
          <proc-base-info :element="element" />
        </el-collapse-item>
        <el-collapse-item
          name="procProperties"
          v-if="(mode === 2 || mode === 3 || mode === 4) && element.type === 'bpmn:ServiceTask'"
          key="procProperties"
        >
          <div slot="title" class="panel-tab__title">
            <i class="el-icon-s-promotion"></i>步骤属性
          </div>
          <proc-properties :element="element" :emList="extraList.emList" :moList="extraList.moList" />
        </el-collapse-item>
        <el-collapse-item
          name="procMaterial"
          v-if="(mode === 2 || mode === 3 || mode === 4) && element.type === 'bpmn:ServiceTask'"
          key="procMaterial"
        >
          <div slot="title" class="panel-tab__title">
            <i class="el-icon-box"></i>原料需求
          </div>
          <proc-material :element="element" :maList="extraList.maList" />
        </el-collapse-item>

        <!-- 分配设备给对应步骤 -->
        <el-collapse-item
          name="deviceTask"
          v-if="mode === 3 && element.type === 'bpmn:ServiceTask'"
          key="deviceTask"
        >
          <div slot="title" class="panel-tab__title">
            <i class="el-icon-link"></i>分配设备
          </div>
          <proc-device-task @updateTask="updateTask" :element="element" :eqList="extraList.eqList" :eoList="extraList.eoList" :taskList="taskList" />
        </el-collapse-item>

        <!-- 设置设备操作参数 -->
        <el-collapse-item
          name="deviceTaskParam"
          v-if="mode === 3 && element.type === 'bpmn:ServiceTask'"
          key="deviceTaskParam"
        >
          <div slot="title" class="panel-tab__title">
            <i class="el-icon-s-operation"></i>操作参数
          </div>
          <proc-device-task-param @updateTask="updateTask" :element="element" :eosList="extraList.eosList" :eospaList="extraList.eospaList" :taskList="taskList" />
        </el-collapse-item>

        <!-- 查看设备任务基本信息 -->
        <el-collapse-item
          name="deviceTaskView"
          v-if="mode === 4 && element.type === 'bpmn:ServiceTask'"
          key="deviceTaskView"
        >
          <div slot="title" class="panel-tab__title">
            <i class="el-icon-link"></i>设备任务
          </div>
          <proc-device-task-view :element="element" :eqList="extraList.eqList" :eoList="extraList.eoList" :dtList="extraList.dtList" />
        </el-collapse-item>
        <!-- 查看设备任务操作参数 -->
        <el-collapse-item
          name="deviceTaskParamView"
          v-if="mode === 4 && element.type === 'bpmn:ServiceTask'"
          key="deviceTaskParamView"
        >
          <div slot="title" class="panel-tab__title">
            <i class="el-icon-s-operation"></i>操作参数
          </div>
          <proc-device-task-param-view @updateTask="updateTask" :element="element" :eosList="extraList.eosList" :eospaList="extraList.eospaList" :dtList="extraList.dtList" :dtpaList="extraList.dtpaList" />
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
import '@/plugins/package/theme/index.scss';
import BpmnViewer from 'bpmn-js/lib/Viewer';
import MoveCanvasModule from 'diagram-js/lib/navigation/movecanvas';
import ProcBaseInfo from "@/plugins/package/penal/base/IndustryBaseInfoView";
import ProcProperties from "@/plugins/package/penal/properties/ProcessPropertiesView";
import ProcMaterial from "@/plugins/package/penal/other/ProcessMaterialView";
import EosBaseInfo from "@/plugins/package/penal/base/IndustryBaseInfoView";
import EosProperties from "@/plugins/package/penal/properties/EquipmentOperationStepPropertiesView";
import EosParam from "@/plugins/package/penal/param/EquipmentOperationStepParamView";
import ProcDeviceTask from "@/plugins/package/penal/task/DeviceTask";
import ProcDeviceTaskView from "@/plugins/package/penal/task/DeviceTaskView";
import ProcDeviceTaskParam from "@/plugins/package/penal/param/DeviceTaskParam";
import ProcDeviceTaskParamView from "@/plugins/package/penal/param/DeviceTaskParamView";

export default {
  name: 'ProcessViewerIndustry',
  components: {
    ProcBaseInfo,
    ProcProperties,
    EosBaseInfo,
    EosProperties,
    EosParam,
    ProcDeviceTask,
    ProcDeviceTaskView,
    ProcDeviceTaskParam,
    ProcDeviceTaskParamView,
    ProcMaterial
  },
  props: {
    xml: {
      type: String
    },
    // mode代表查看器职能
    // 1-设备操作流程查看
    // 2-产品工艺流程查看
    // 3-分配设备任务
    // 4-查看设备任务
    mode: {
      type: Number
    },
    extraList: {
      type: Object
    },
    flag: {
      type: Number
    }
  },
  data () {
    return {
      defaultZoom: 1,
      // 是否正在加载流程图
      isLoading: false,
      bpmnViewer: undefined,
      // 当前任务id
      selectTaskId: undefined,
      // 选中对象
      element: {
        type: null
      },
      // mode = 3时，维护步骤对应的设备和设备操作
      taskList: [],
      // 默认展开的页签
      activeTab: [],
      // 当前查看的轮次
      viewRound: 1,
      // 最大轮次
      maxRound: 1,
    }
  },
  watch: {
    xml: {
      handler(newXml) {
        this.importXML(newXml);
      },
      immediate: true
    },
    flag: {
      handler(flag) {
        if (this.mode === 4) {
          this.paint()
        }
      },
      immediate: true
    }
  },
  created() {
    this.$nextTick(() => {
      this.importXML(this.xml)
    })
    if (this.mode === 1) {
      this.activeTab = ['eosBasic', 'eosProperties', 'eosParam']
    } else if (this.mode === 2 || this.mode === 3) {
      this.activeTab = ['procBasic', 'procProperties', 'procMaterial']
    } else if (this.mode === 4) {
      this.activeTab = ['procBasic', 'procProperties', 'procMaterial']
    }
  },
  methods: {
    processReZoom() {
      this.defaultZoom = 1;
      this.bpmnViewer.get('canvas').zoom('fit-viewport', 'auto');
    },
    processZoomIn(zoomStep = 0.1) {
      let newZoom = Math.floor(this.defaultZoom * 100 + zoomStep * 100) / 100;
      if (newZoom > 4) {
        throw new Error('[Process Designer Warn ]: The zoom ratio cannot be greater than 4');
      }
      this.defaultZoom = newZoom;
      this.bpmnViewer.get('canvas').zoom(this.defaultZoom);
    },
    processZoomOut(zoomStep = 0.1) {
      let newZoom = Math.floor(this.defaultZoom * 100 - zoomStep * 100) / 100;
      if (newZoom < 0.2) {
        throw new Error('[Process Designer Warn ]: The zoom ratio cannot be less than 0.2');
      }
      this.defaultZoom = newZoom;
      this.bpmnViewer.get('canvas').zoom(this.defaultZoom);
    },
    // 流程图预览清空
    clearViewer() {
      if (this.$refs.processCanvas) {
        this.$refs.processCanvas.innerHTML = '';
      }
      if (this.bpmnViewer) {
        this.bpmnViewer.destroy();
      }
      this.bpmnViewer = null;
    },
    // 点击步骤的回调函数
    onSelectElement(element) {
      this.element = element;
    },
    // 显示流程图
    async importXML(xml) {
      this.clearViewer();
      if (xml != null && xml !== '') {
        try {
          this.bpmnViewer = new BpmnViewer({
            additionalModules: [
              // 移动整个画布
              MoveCanvasModule
            ],
            container: this.$refs.processCanvas,
          });
          // 任务节点悬浮事件
          this.bpmnViewer.on('element.click', ({ element }) => {
            this.onSelectElement(element);
          });

          this.isLoading = true;
          await this.bpmnViewer.importXML(xml);
        } catch (e) {
          this.clearViewer();
        } finally {
          this.isLoading = false;
          if (this.mode === 4) {
            this.paint()
            const model = this.extraList.dtList[0].dtModel
            this.maxRound = this.extraList.dtList.filter(ele => ele.dtModel === model).length
          }
        }
      }
    },
    // 根据任务完成情况，给节点染色
    paint() {
      if (this.bpmnViewer) {
        const canvas = this.bpmnViewer.get('canvas')
        // primary, success, warning, danger
        const nodes = []
        this.extraList.dtList.forEach(element => {
          if (element.dtStat === '4') {
            // 已完成
            let idx = nodes.findIndex(ele => ele.dtModel === element.dtModel)
            if (idx === -1) {
              nodes.push({
                dtModel: element.dtModel,
                cnt: 1,
                progress: false
              })
            } else {
              nodes[idx].cnt += 1
            }
          } else if (element.dtStat === '3') {
            // 进行中
            let idx = nodes.findIndex(ele => ele.dtModel === element.dtModel)
            if (idx === -1) {
              nodes.push({
                dtModel: element.dtModel,
                cnt: 0,
                progress: true
              })
            } else {
              nodes[idx].progress = true
            }
          } else {
            // 未开始
            let idx = nodes.findIndex(ele => ele.dtModel === element.dtModel)
            if (idx === -1) {
              nodes.push({
                dtModel: element.dtModel,
                cnt: 0,
                progress: false
              })
            }
          }
        })
        // 染色
        nodes.forEach(element => {
          if (element.progress && element.cnt === this.viewRound - 1) {
            canvas.removeMarker(element.dtModel, 'primary')
            canvas.removeMarker(element.dtModel, 'success')
            canvas.addMarker(element.dtModel, 'primary')
          } else if (element.cnt >= this.viewRound) {
            canvas.removeMarker(element.dtModel, 'primary')
            canvas.removeMarker(element.dtModel, 'success')
            canvas.addMarker(element.dtModel, 'success')
          } else {
            canvas.removeMarker(element.dtModel, 'primary')
            canvas.removeMarker(element.dtModel, 'success')
          }
        })
      }
    },
    // 更新已经暂存的任务
    updateTask(data) {
      const idx = this.taskList.findIndex(ele => ele.id === data.id)
      if (idx === -1) {
        this.taskList.push(data)
      } else {
        this.taskList.splice(idx, 1)
        this.taskList.push(data)
      }
    },
    // 提交设备任务
    submitDeviceTask() {
      this.$emit("saveTask", this.taskList)
    }
  },
  destroyed() {
    this.clearViewer();
  }
}
</script>

<style scoped lang="scss">
.process-viewer {
  display: flex;
}
.process-canvas {
  width: 100%;
}
.viewer-penal {
  width: 480px;
  background: #fff;
  box-sizing: border-box;
  -webkit-box-sizing: border-box;
  padding: 0 8px;
  border-left: 1px solid #eee;
  box-shadow: 0 0 8px #ccc;
  max-height: 100%;
  overflow: scroll;
}
.panel-tab__title {
  font-weight: 600;
  padding: 0 8px;
  font-size: 1.1em;
  line-height: 1.2em;
  i {
    margin-right: 8px;
    font-size: 1.2em;
  }
}
.legend {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  text-align: center;
  font-size: 120%;
}
.legend-box {
  width: 102px;
  height: 82px;
  border: 2.9px solid;
  border-radius: 10%;
  margin-top: 5px;
}
.green-box {
  background-color: #e3f4db;
  border-color: #4eb819;
}
.blue-box {
  background-color: #e2f1ff;
  border-color: #409eff;
}
.white-box {
  background-color: #fff;
  border-color: #000;
}
</style>
