<template>
  <div class="process-viewer">
    <div class="process-canvas" style="height: 100%;" ref="processCanvas" v-show="!isLoading" />
    <!-- 画布视角控件：放大缩小按钮 -->
    <div style="position: absolute; top: 0px; left: 0px; width: 100%;">
      <el-row type="flex" justify="start">
        <el-button-group key="scale-control" size="medium">
          <el-button size="medium" type="default" :plain="true" :disabled="defaultZoom <= 0.3" icon="el-icon-zoom-out" @click="processZoomOut()" />
          <el-button size="medium" type="default" style="width: 90px;">{{ Math.floor(this.defaultZoom * 10 * 10) + "%" }}</el-button>
          <el-button size="medium" type="default" :plain="true" :disabled="defaultZoom >= 3.9" icon="el-icon-zoom-in" @click="processZoomIn()" />
          <el-button size="medium" type="default" icon="el-icon-c-scale-to-original" @click="processReZoom()" />
          <slot />
        </el-button-group>
      </el-row>
    </div>
    <!-- 右侧边栏显示属性信息 -->
    <div class="viewer-penal">
      
    </div>
  </div>
</template>

<script>
import '@/plugins/package/theme/index.scss';
import BpmnViewer from 'bpmn-js/lib/Viewer';
import MoveCanvasModule from 'diagram-js/lib/navigation/movecanvas';

export default {
  props: {
    xml: {
      type: String
    },
    extraList: {
      type: Object
    }
  },
  data () {
    return {
      defaultZoom: 1,
      // 是否正在加载流程图
      isLoading: false,
      bpmnViewer: undefined,
      // 已完成流程元素
      processNodeInfo: undefined,
      // 当前任务id
      selectTaskId: undefined,
      // 任务节点审批记录
      taskCommentList: [],
      // 已完成任务悬浮延迟Timer
      hoverTimer: null
    }
  },
  watch: {
    xml: {
      handler(newXml) {
        this.importXML(newXml);
      },
      immediate: true
    }
  },
  created() {
    this.$nextTick(() => {
      this.importXML(this.xml)
    })
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
    // 任务悬浮弹窗
    onSelectElement(element) {
      console.log(element)
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
        }
      }
    }
  },
  destroyed() {
    this.clearViewer();
  }
}
</script>

<style scoped>
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
</style>
