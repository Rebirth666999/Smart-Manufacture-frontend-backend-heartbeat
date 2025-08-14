<template>
  <div class="app-container">
    <!-- 4 分区监控控制台（第二段） -->
    <div class="console-container">
      <!-- 左上：设备图片 -->
      <div class="panel img-panel">
        <h3>设备图片</h3>
        <img :src="deviceImg" alt="设备图片" />
      </div>

      <!-- 右上：实时视频 -->
      <div class="panel video-panel">
        <h3>实时监控</h3>
        <img
          :src="videoSrc"
          style="width:100%;height:100%;object-fit:cover;"
        />
      </div>

      <!-- 左下：快捷操作-->
      <div class="panel left-bottom">
        <h3>快捷操作</h3>
        <!-- 只读信息：设备ID / 设备名称 / 所属车间 / 所属设备模型 -->
        <el-descriptions :column="1" border>
          <el-descriptions-item label="设备ID">
           1
          </el-descriptions-item>

          <el-descriptions-item label="设备名称">
           1
          </el-descriptions-item>

          <el-descriptions-item label="所属车间">
          1
          </el-descriptions-item>

          <el-descriptions-item label="所属设备模型">
           1
          </el-descriptions-item>
          <el-descriptions-item label="设备状态">
            1
          </el-descriptions-item>
        </el-descriptions>

        <el-select
          v-model="selectedAction"
          placeholder="请选择设备动作"
          size="medium"
          style="width: 100%; margin-top: 8px"
        >
          <el-option
            v-for="act in actions"
            :key="act.key"
            :label="act.label"
            :value="act.key"
          />
        </el-select>

        <el-button
          style="margin-top: 12px;margin-left: auto;display: block;"
          type="warning"
          size="medium"
          :loading="loading"
          @click="handleModify"
        >
          修改参数
        </el-button>
      </div>

      <!-- 右下：参数设置（可读写表格） -->
      <div class="panel right-bottom">
        <h3>参数设置</h3>

        <el-descriptions :column="1" border>
          <el-descriptions-item label="参数名称">
            <el-input v-model="param.name" size="mini" />
          </el-descriptions-item>

          <el-descriptions-item label="父级参数序号">
            <el-input v-model="param.parentIndex" size="mini" />
          </el-descriptions-item>

          <el-descriptions-item label="参数类型">
            <el-input v-model="param.type" size="mini" />
          </el-descriptions-item>

          <el-descriptions-item label="参数位置">
            <el-input v-model="param.position" size="mini" />
          </el-descriptions-item>

          <el-descriptions-item label="默认值">
            <el-input v-model="param.defaultValue" size="mini" />
          </el-descriptions-item>

          <el-descriptions-item label="参数值">
            <el-input
              v-model="param.value"
              type="textarea"
              :rows="2"
              size="mini"
              resize="none"
            />
          </el-descriptions-item>
        </el-descriptions>

        <el-button
          style="margin-top: 12px; margin-left: auto; display: block;"
          type="primary"
          size="medium"
          :loading="loading"
          @click="handleSaveParam"
        >
          保存参数
        </el-button>
      </div>



    </div>
  </div>
</template>

<script>
import { listEquipment, getEquipment, delEquipment, addEquipment, updateEquipment } from "@/api/system/equipment";
import { listArea } from "@/api/system/area";
import { listEquipmentModel } from "@/api/system/equipmentModel";
//import { startDevice } from "@/api/device"; // 新增：监控启动接口

export default {
  name: "DeviceMonitor",
  dicts: ['ices_equipment_status'],
  data() {
    return {
      /* 第一段原有数据 */
      equipmentList: [],
      areaList: [],
      equipmentModelList: [],
      total: 0,
      loading: false,
      showSearch: true,
      queryParams: { pageNum: 1, pageSize: 10, arCode: undefined, emCode: undefined, eqName: undefined, eqStat: undefined, eqDelete: 0 },
      open: false,
      title: "",
      form: {},
      rules: { /* 与原来一致 */ },

      /* 第二段新增数据 */
      selectedAction: '',
      deviceImg: "/static/img/device.jpg",
      videoSrc: "http://localhost:5000/video_feed" ,
      actions: [
        { key: "move", label: "机械臂移动" },
        { key: "rotate", label: "旋转" },
        { key: "grab", label: "抓取" }
      ],
      param: {
        name: '',
        parentIndex: '',
        type: '',
        position: '',
        defaultValue: '',
        value: ''
      },
    };
  },
  created() {
    this.getAreaList();
    this.getEquipmentModelList();
    this.getList();
  },
  methods: {

    selectAction(act) {
      this.$message.info(`已选择：${act.label}`);
    },
    async handleStart() {
      this.loading = true;
      try {
        await startDevice({ action: this.actions[0].key, ...this.params });
        this.$message.success("已发送启动指令");
      } catch (e) {
        this.$message.error(e.message || "启动失败");
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
/* 第一段原有样式 */
.el-select, .el-date-editor { width: 100%; }

/* 第二段 4 分区样式 */
.console-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  height: 100vh;
  gap: 10px;
  background: #f5f5f5;
}
.panel {
  background: #fff;
  border-radius: 8px;
  padding: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}
.img-panel img,
video {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.action-panel,
.param-panel {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.no-border-input{
  width: 100%;
}
.no-border-input >>> .el-input__inner {
  border: none;
  outline: none;
  background: transparent;
  padding: 0;
  text-align: center;
}
h3{
  margin: 0;
}
.right-bottom >>> .el-descriptions-item__label {
  text-align: center !important;
}
/* 仅把表格整体下移，标题保持原位置 */
.right-bottom>>>.el-descriptions,
.left-bottom >>> .el-descriptions {
  margin-top: 25px;   /* 根据需要微调 */
}
/* 让输入框整体充满单元格 */
.right-bottom >>> .el-descriptions-item__content {
  padding: 0 !important;
}
.right-bottom >>> .el-input,
.right-bottom >>> .el-textarea {
  align-items: center;
  height: 100%;
  width: 100%;
}
.right-bottom >>> .el-input__inner,
.right-bottom >>> .el-textarea__inner {
  height: 100%;
  width: 100%;
  border: none;
  outline: none;
  background: transparent;
  box-sizing: border-box;
}
.left-bottom >>> .el-descriptions-item__label {
  width: 25% !important;
  min-width: 25% !important;
  max-width: 25% !important;
  text-align: center;
}
/* 两区域里所有单元格文字居中 */
.left-bottom >>> .el-descriptions-item__content
 {
  display: flex;
  justify-content: center;
  align-items: center;
}


</style>
