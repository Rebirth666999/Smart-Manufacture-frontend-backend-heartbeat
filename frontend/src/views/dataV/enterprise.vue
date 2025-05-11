<template>
  <div class="data">
    <div class="topbox">
      <img src="@/assets/board/title.png" class="bigtitlefont"/>
      <img src="@/assets/board/toptitle.png" class="toptitle"/>
      <span class="smallfont">数据汇总看板</span>
      <img src="@/assets/board/smalltitle.png" class="smalltitle"/>
    </div>
    <div class="cover">
      <div class="box">
        <img src="@/assets/board/boxleft.png" class="boxleft"/>
        <img src="@/assets/board/boxright.png" class="boxright"/>
      </div>
      <div class="box2">
        <div class="cardbox">
          <div class="card">
            <img src="@/assets/board/cardbg.png" style="position: absolute;z-index: 1"/>
            <span style="position: absolute;z-index: 2;color: white">总设备数</span>
            <span style="position: absolute;z-index: 2;color: white;margin-top: 5%">100</span>
          </div>
          <div class="card">
            <img src="@/assets/board/cardbg.png" style="position: absolute;z-index: 1"/>
            <span style="position: absolute;z-index: 2;color: white">在线设备数</span>
            <span style="position: absolute;z-index: 2;color: white;margin-top: 5%">50</span>
          </div>
          <div class="card">
            <img src="@/assets/board/cardbg.png" style="position: absolute;z-index: 1"/>
            <span style="position: absolute;z-index: 2;color: white">维修设备数</span>
            <span style="position: absolute;z-index: 2;color: white;margin-top: 5%">20</span>
          </div>
        </div>
        <img src="@/assets/board/videobg.png" class="videobg"/>
        <img src="@/assets/board/dizuo1.png" class="dizuo"/>
        <img src="@/assets/board/dizuobg.png" class="dizuobg"/>
        <img src="@/assets/board/light.png" class="light"/>
        <img src="@/assets/board/bottom.png" class="boxbottom"/>
      </div>
    </div>

    <div class="left">
      <div class="lefttop">
        <div class="title">
          <span class="titlefont">根据生产计划查看生产任务完成情况</span>
          <big-data-title />
          <el-select v-model="selectedPlan" placeholder="请选择生产计划" @change="handlePlanChange" class="plan-select">
            <el-option
              v-for="item in planList"
              :key="item.mpCode"
              :label="item.mpCode"
              :value="item.mpCode">
            </el-option>
          </el-select>
        </div>
        <data6 ref="planTaskComponent" :selectedPlan="selectedPlan" :containerId="'planTaskChart'" />
      </div>
    
      <div class="lefbottom">
        <div class="title">
          <span class="titlefont">根据车间查看设备任务完成情况</span>
          <big-data-title />
          <el-select v-model="selectedArea" placeholder="请选择车间" @change="handleAreaChange" class="area-select">
            <el-option
              v-for="item in areaList"
              :key="item.arCode"
              :label="item.arName"
              :value="item.arCode">
            </el-option>
          </el-select>
        </div>
        <data6 ref="areaDeviceComponent" :selectedArea="selectedArea" :containerId="'areaDeviceChart'" />
      </div>
     
     
    </div>
    <div class="right">
      
      <div class="righttop">
<!--        <big-data-title></big-data-title>-->
        <div class="title">
          <span class="titlefont">根据生产任务查看设备任务完成情况</span>
          <big-data-title />
          <el-select v-model="selectedTask" placeholder="请选择生产任务" @change="handleTaskChange" class="task-select">
            <el-option
              v-for="item in taskList"
              :key="item.mtCode"
              :label="item.mtCode"
              :value="item.mtCode">
            </el-option>
          </el-select>
        </div>
        <data6 ref="taskDeviceComponent" :selectedTask="selectedTask" :containerId="'taskDeviceChart'" />
      </div>
     

     
      <div class="rightbottom">
        <div class="title">
          <span class="titlefont">根据订单查看生产计划完成情况</span>
          <big-data-title />
          <el-select v-model="selectedOrder" placeholder="请选择订单" @change="handleOrderChange" class="order-select">
            <el-option
              v-for="item in orderList"
              :key="item.orCode"
              :label="item.orCode"
              :value="item.orCode">
            </el-option>
          </el-select>
        </div>
        <data6 ref="data6Component" :selectedOrder="selectedOrder" :containerId="'orderPlanChart'" />
      </div>
    </div>
<!--    <iframe src="@/assets/board/planet.mp4" class="planet"/>-->
    <div>
      <video ref="videoPlayer" muted autoplay  class="planet" @ended="restartVideo">
        <source src="@/assets/board/planet.mp4" type="video/mp4">
      </video>
    </div>
  </div>
</template>

<script>
import data2 from "@/components/Board/data2.vue";
import Data1 from "@/components/Board/data1.vue";
import data3 from "@/components/Board/data3.vue";
import Data4 from "@/components/Board/data4.vue";
import data6 from "@/components/Board/data6.vue";
import { listOrder, getOrder } from "@/api/system/order";
import { getManufactureTask, listManufactureTask } from "@/api/system/manufactureTask";
import { listManufacturePlan } from "@/api/system/manufacturePlan";
import { listArea } from "@/api/system/area";

export default {
  name: "enterprise",
  components:{
    data2,
    data3,
    Data4,
    Data1,
    data6,
  },
  data(){
    return {
      imageSrc:"@/assets/board/toptitle.png",
      orderList: [],
      selectedOrder: null,
      planList: [],
      selectedPlan: null,
      taskList: [],
      selectedTask: null,
      areaList: [],
      selectedArea: null
    }
  },
  created() {
    this.getOrderList();
    this.getPlanList();
    this.getTaskList();
    this.getAreaList();
  },
  methods:{
    restartVideo(){
      this.$refs.videoPlayer.currentTime = 0;
      this.$refs.videoPlayer.play();
    },
    async getOrderList() {
      try {
        const response = await listOrder();
        this.orderList = response.rows;
      } catch (error) {
        console.error("获取订单列表失败:", error);
      }
    },
    async getPlanList() {
      try {
        const response = await listManufacturePlan();
        this.planList = response.rows;
      } catch (error) {
        console.error("获取生产计划列表失败:", error);
      }
    },
    async getTaskList() {
      try {
        const response = await listManufactureTask();
        this.taskList = response.rows;
      } catch (error) {
        console.error("获取生产任务列表失败:", error);
      }
    },
    async getAreaList() {
      try {
        const response = await listArea();
        this.areaList = response.rows;
      } catch (error) {
        console.error("获取车间列表失败:", error);
      }
    },
    handleOrderChange(value) {
      if (this.$refs.data6Component) {
        this.$refs.data6Component.updateChartData(value);
      }
    },
    handlePlanChange(value) {
      if (this.$refs.planTaskComponent) {
        this.$refs.planTaskComponent.updateTaskChartData(value);
      }
    },
    handleTaskChange(value) {
      if (this.$refs.taskDeviceComponent) {
        this.$refs.taskDeviceComponent.updateDeviceTaskChartData(value);
      }
    },
    handleAreaChange(value) {
      if (this.$refs.areaDeviceComponent) {
        this.$refs.areaDeviceComponent.updateAreaDeviceTaskChartData(value);
      }
    }
  }
}
</script>

<style scoped>
.topbox{
  display: flex;
  justify-content: center;
}
.data{
  height: 100%;
  /*background-color: #232630;*/
  background-color: #01112B;
}
.toptitle{
  position: absolute;
  width: 100%;
  height: 50px;
  z-index: 3;
}
.bigtitlefont{
  position: absolute;
  z-index: 4;
  width: 20%;
  top: 0;
  margin-top: 0.5%;
}
.smalltitle{
  position: absolute;
  z-index: 3;
  width: 100%;
  height: 100px;
}
.smallfont{
  position: absolute;
  z-index: 4;
  font-family: 'Source Han Sans', sans-serif;
  color: #A9F6FF;
  top: 70px;
  font-size: 20px;
}
.planet{
  position: absolute;
  bottom: 0;
  left: 0;
  z-index: 1;
  width: 100%;
  /*height: 100%;*/
}
.cover{
  position: absolute;
  z-index: 2;
  width: 100%;
  height: 100%;
  background-color: rgba(35,38,48,0.5);
}
.box{
  display: flex;
  height: 100%;
  width: 100%;
  align-items: center;
}
.box2{
  display: flex;
  height: auto;
  width: 100%;
  justify-content: center;
}
.boxleft{
  position: absolute;
  height: 80%;
  left: 1%;
}
.boxright{
  position: absolute;
  height: 80%;
  right: 1%;
}
.boxbottom{
  position: absolute;
  width: 100%;
  bottom: 0;
}
.light{
  position: absolute;
  bottom: 35px;
  width: 200px;
  z-index: 6;
}
.cardbox{
  position: absolute;
  display: flex;
  justify-content: space-between;
  width: 39%;
  top: 20%;
}
.card{
  width: 30%;
  margin-left: 3%;
  align-items: center;
  display: flex;
  justify-content: center;
}
.videobg{
  position: absolute;
  z-index: 4;
  bottom: 23%;
  width: 25%;
}
.dizuo{
  position: absolute;
  width: 100%;
  bottom: 0;
  z-index: 3;
}
.dizuobg{
  position: absolute;
  bottom: 0;
  width: 100%;
  z-index:2;
}
.left{
  height: 75%;
  width: 25%;
  position: absolute;
  left: 3.5%;
  top: 15%;
  /*background-color: pink;*/
  z-index: 3;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.lefttop{
  height: 50%;
  /*background-color: skyblue;*/
  position: relative;
  /*align-items: center;*/
}
.leftmiddle{
  height: 33%;
  /*background-color: skyblue;*/
}
.leftbottom{
  height: 50;
  /*background-color: skyblue;*/
}
.title{
  width: 100%;
  height: 15%;
  position: relative;
  /*display: flex;*/
  /*align-items: center;*/
  /*background-color: navajowhite;*/
}
.titlefont{
  color: #FFFFFF;
  z-index: 6;
  position: absolute;
  margin-left: 2%;
  margin-top: 1%;
  font-family: 'Microsoft YaHei', sans-serif;
  font-weight: bold;
  font-size: 18px;
}
.right{
  height: 75%;
  width: 25%;
  position: absolute;
  right: 3.5%;
  top: 15%;
  /*background-color: pink;*/
  z-index: 3;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.righttop{
  height: 50%;
  /*background-color: skyblue;*/
  position: relative;
  /*align-items: center;*/
}
.rightmiddle{
  height: 33%;
  /*background-color: skyblue;*/
}
.rightbottom{
  height: 50%;
  /*background-color: skyblue;*/
}
.order-select, .plan-select, .task-select, .area-select {
  position: absolute;
  right: 20px;
  top: 150%;
  transform: translateY(-50%);
  width: 200px;
  z-index: 50;
}
</style>
