const WebSocketClient = {
    sockets: {}, // 存储多个 WebSocket 实例
    onMessageCallbacks: {}, // 存储每个连接的消息回调函数
  
    // 初始化 WebSocket 连接
    init(url, identifier) {
      if (this.sockets[identifier]) {
        console.warn(`WebSocket ${identifier} 已经初始化，无需重复初始化！`);
        return;
      }
  
      this.sockets[identifier] = new WebSocket(url);
  
      this.sockets[identifier].onopen = () => {
        console.log(`WebSocket ${identifier} 连接已打开`);
      };
  
      this.sockets[identifier].onmessage = (event) => {
        console.log(`收到 ${identifier} 消息：`, event.data);
        let data=event.data;
        if (this.onMessageCallbacks[identifier]) {
          this.onMessageCallbacks[identifier].forEach(callback => callback(data)); // 调用所有注册的回调函数
        }
      };
  
      this.sockets[identifier].onclose = () => {
        console.log(`WebSocket ${identifier} 连接已关闭`);
      };
  
      this.sockets[identifier].onerror = (error) => {
        console.error(`WebSocket ${identifier} 发生错误：`, error);
      };
    },
  
    // 发送消息到服务端
    sendMessage(message, identifier) {
      if (this.sockets[identifier] && this.sockets[identifier].readyState === WebSocket.OPEN) {
        this.sockets[identifier].send(JSON.stringify(message));
      } else {
        console.error(`WebSocket ${identifier} 连接未打开，无法发送消息！`);
      }
    },
  
    // 注册消息回调函数
    onMessage(callback, identifier) {
      if (!this.onMessageCallbacks[identifier]) {
        this.onMessageCallbacks[identifier] = [];
      }
      this.onMessageCallbacks[identifier].push(callback);
    },
  
    // 关闭 WebSocket 连接
    close(identifier) {
      if (this.sockets[identifier]) {
        this.sockets[identifier].close();
        this.sockets[identifier] = null;
        console.log(`WebSocket ${identifier} 连接已关闭`);
      }
    }
  };
  
  export default WebSocketClient;