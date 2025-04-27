<template>
  <div :class="classObj" class="app-wrapper" :style="{'--current-color': theme}">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
    <sidebar v-if="!sidebar.hide" class="sidebar-container"/>
    <div :class="{hasTagsView:needTagsView,sidebarHide:sidebar.hide}" class="main-container">
      <div :class="{'fixed-header':fixedHeader}">
        <navbar/>
        <tags-view v-if="needTagsView"/>
      </div>
      <div id="tray" class="float-tray">
        <div>
          <el-button id="tray-header" class="tray-header" icon="el-icon-rank"></el-button>
          <el-button type="primary" :plain="!taskOpen" @click="switchTaskTray">任务托盘</el-button>
          <el-button type="danger" :plain="!alarmOpen" @click="switchAlarmTray">报警托盘</el-button>
        </div>
        <div class="tray-content">
          <task-tray v-if="taskOpen" />
          <div v-if="alarmOpen">报警托盘：正在施工</div>
        </div>
      </div>
      <app-main/>
      <right-panel>
        <settings/>
      </right-panel>
    </div>
  </div>
</template>

<script>
import RightPanel from '@/components/RightPanel'
import { AppMain, Navbar, Settings, Sidebar, TagsView } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import { mapState } from 'vuex'
import variables from '@/assets/styles/variables.scss'
import TaskTray from '@/tray/TaskTray.vue'

export default {
  name: 'Layout',
  components: {
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView,
    TaskTray
  },
  mixins: [ResizeMixin],
  computed: {
    ...mapState({
      theme: state => state.settings.theme,
      sideTheme: state => state.settings.sideTheme,
      sidebar: state => state.app.sidebar,
      device: state => state.app.device,
      needTagsView: state => state.settings.tagsView,
      fixedHeader: state => state.settings.fixedHeader
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    },
    variables() {
      return variables;
    }
  },
  data() {
    return {
      // 任务托盘是否展开
      taskOpen: false,
      // 报警托盘是否展开
      alarmOpen: false,
    }
  },
  mounted() {
    function dragElement(elmnt, target) {
      var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
      elmnt.onmousedown = dragMouseDown;
    
      function dragMouseDown(e) {
        e = e || window.event;
        e.preventDefault();
        // 在启动时获取鼠标光标位置:
        pos3 = e.clientX;
        pos4 = e.clientY;
        document.onmouseup = closeDragElement;
        // 每当光标移动时调用一个函数:
        document.onmousemove = elementDrag;
      }
    
      function elementDrag(e) {
        e = e || window.event;
        e.preventDefault();
        // 计算新的光标位置:
        pos1 = pos3 - e.clientX;
        pos2 = pos4 - e.clientY;
        pos3 = e.clientX;
        pos4 = e.clientY;
        // 设置元素的新位置:
        target.style.top = (target.offsetTop - pos2) + "px";
        target.style.left = (target.offsetLeft - pos1) + "px";
      }
    
      function closeDragElement() {
        // 释放鼠标按钮时停止移动:
        document.onmouseup = null;
        document.onmousemove = null;
      }
    }
    dragElement(document.getElementById("tray-header"),
                document.getElementById("tray"));
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    },
    /** 
     * 点击任务托盘按钮
     * 切换任务托盘的显示状态
     * @author YangZY
     * @date 20250427
     */
     switchTaskTray() {
      if (this.alarmOpen) {
        // 目前显示的是报警托盘，自动切换
        this.alarmOpen = false
        this.taskOpen = true
      } else if (this.taskOpen) {
        // 目前显示的是任务托盘，隐藏
        this.taskOpen = false
        // 重置大小
        document.getElementById("tray").style.height = '50px'
        document.getElementById("tray").style.width = '300px'
      } else {
        // 显示任务托盘
        this.taskOpen = true
        // 设置大小
        document.getElementById("tray").style.height = '300px'
        document.getElementById("tray").style.width = '400px'
      }
    },
    /** 
     * 点击报警托盘按钮
     * 切换报警托盘的显示状态
     * @author YangZY
     * @date 20250427
     */
    switchAlarmTray() {
      if (this.taskOpen) {
        // 目前显示的是任务托盘，自动切换
        this.taskOpen = false
        this.alarmOpen = true
      } else if (this.taskOpen) {
        // 目前显示的是报警托盘，隐藏
        this.alarmOpen = false
        // 重置大小
        document.getElementById("tray").style.height = '50px'
        document.getElementById("tray").style.width = '300px'
      } else {
        // 显示报警托盘
        this.alarmOpen = true
        // 设置大小
        document.getElementById("tray").style.height = '300px'
        document.getElementById("tray").style.width = '400px'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/assets/styles/mixin.scss";
  @import "~@/assets/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;

    &.mobile.openSidebar {
      position: fixed;
      top: 0;
    }
  }

  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$base-sidebar-width});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px);
  }

  .sidebarHide .fixed-header {
    width: 100%;
  }

  .mobile .fixed-header {
    width: 100%;
  }

  .tray-header {
    cursor: move;
    margin-bottom: 1rem;
  }

  .float-tray {
    overflow: hidden;
    position: absolute;
    min-height: 50px;
    min-width: 300px;
    height: 50px;
    width: 300px;
    padding: 5px;
    z-index: 99;
    right: 200px;
    top: 0;
    resize: both;
    background-color: #fff;
    border: 1px solid #e6ebf5;
    border-radius: 4px;
  }

  .tray-content {
    font-size: 14px;
  }
</style>
