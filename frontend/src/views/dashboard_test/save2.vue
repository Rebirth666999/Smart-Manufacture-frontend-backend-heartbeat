<template>
  <div>
    <h1>数据大屏</h1>
    <p
      style="border: 1px solid; width: 600px; height: 300px; margin-left: 100px"
    >
      {{ msg1 }}
    </p>
  </div>
</template>

<script>
import WebSocketClient from "@/api/websocket/websocketService";
export default {
  data() {
    return {
      msg1: "数据大屏",
      msg2: "数据大屏",
    };
  },
  mounted() {
    WebSocketClient.init(
      "ws://localhost:8080/heartbeat?client_name=browser",
      "heartbeat"
    );
    WebSocketClient.onMessage((data) => {
      this.msg1 = data;
    }, "heartbeat");
  },
  beforeDestroy() {
    this.client1.close();
  },
};
</script>

<style>
#app {
  text-align: center;
  margin-top: 50px;
}

button {
  margin: 10px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}
</style>
