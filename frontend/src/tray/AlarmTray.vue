<template>
  <div class="tray-container">
    <el-tree v-if="treeData.length > 0" :data="treeData" @node-click="handleNodeClick">
      <div slot-scope="{ node, data }">
        <span v-if="data.root">
          <el-tag size="mini" type="danger">{{ data.children.length }}</el-tag>{{ data.label }}
        </span>
        <el-button v-else type="text">{{ data.label }}</el-button>
      </div>
    </el-tree>
    <el-empty v-else :image="emptyImageUrl" :image-size="100" description="暂无需要处理的报警" />
  </div>
</template>
<script>
import { listTodoProcess } from '@/api/system/exceptionRunning';
import emptyImage from '@/assets/images/empty.svg';

export default {
  name: "AlarmTray",
  data() {
    return {
      // 渲染用的数据列表
      treeData: [],
      // 是否存在未完成任务
      show: true,
      // 待办任务列表
      todoList: [],
      // "空白"图片URL
      emptyImageUrl: emptyImage
    }
  },
  async created() {
    this.treeData = []
    await this.getTodoList()
    this.calcTodo()
  },
  async activated() {
    this.treeData = []
    await this.getTodoList()
    this.calcTodo()
  },
  methods: {
    /**
     * 查询待办任务
     * @author YangZY
     * @date 20250509
     */
    getTodoList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listTodoProcess().then(response => {
          this.todoList = response.data;
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /**
     * 处理待办任务的格式
     * @author YangZY
     * @date 20250509
     */
    calcTodo() {
      if (this.todoList.length > 0) {
        const arr = []
        this.todoList.forEach(ele => {
          arr.push({
            label: `${ele.exName} - ${ele.taskName}`,
            taskId: ele.taskId
          })
        })
        this.treeData.push({
          label: '待处理异常',
          root: true,
          children: arr
        })
      }
    },
    handleNodeClick(data) {
      if (data.taskId) {
        this.$router.push(`/todoException?taskId=${data.taskId}`)
      }
    }
  }
}
</script>
<style scoped>
.tray-container {
  height: 300px;
  overflow: scroll;
}
</style>