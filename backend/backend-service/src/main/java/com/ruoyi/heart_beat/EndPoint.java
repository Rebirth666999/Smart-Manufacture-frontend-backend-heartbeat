package com.ruoyi.heart_beat;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.ruoyi.heart_beat.service.IcesEquipmentHeartbeatSaveService;
import lombok.extern.slf4j.Slf4j;
import com.ruoyi.heart_beat.config.GetHttpSessionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@ServerEndpoint(value = "/heartbeat",configurator = GetHttpSessionConfig.class)
@Component
@Slf4j
public class EndPoint {
    static Map<String, Session> OnlineClient1Sessions=new ConcurrentHashMap<String ,Session>();
    static Map<String, Session> OnlineClient2Sessions=new ConcurrentHashMap<String ,Session>();

    static private final String client_name_1="browser";//前端名称代号
    static private final String client_name_2="controller";//控制节点名称代号

    IcesEquipmentHeartbeatSaveService heartbeatSaveService;
    private static AutowireCapableBeanFactory beanFactory;
//    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Autowired
    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        EndPoint.beanFactory = beanFactory;
    }
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        String client_name=(String) config.getUserProperties().get("client_name");
        session.getUserProperties().put("client_name", client_name);
        if(client_name.equals(client_name_1)){
            OnlineClient1Sessions.put(session.getId(),session);
        }else if(client_name.equals(client_name_2)){
            OnlineClient2Sessions.put(session.getId(),session);
        }
        System.out.println("a session opens.client_name:"+client_name);
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        String client_name = (String) session.getUserProperties().get("client_name");
        // log.info("received :" + msg + " from :" + client_name);
        if (client_name.equals("controller")) {
            if (!msg.equals("Heartbeat") && !msg.equals("{\"tasks_status\": \"no task now\", \"error\": \"no error\"}")) {

                // 获取线程池
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                System.out.println("in onMessage");
                // 提交保存心跳的任务
                executorService.submit(() -> {
                    System.out.println("in thread1");
                    this.heartbeatSaveService = beanFactory.getBean(IcesEquipmentHeartbeatSaveService.class);
                    System.out.println(msg);
                    heartbeatSaveService.saveHeartbeat(msg);
                });

                // 提交发送消息到浏览器的任务
                executorService.submit(() -> {
                    System.out.println("in thread2");
                    sendToBrowser(msg);
                });

                // 关闭线程池
                executorService.shutdown();
            }
        } else {
            System.out.println(client_name + " send a message:" + msg);
        }
    }
    private void sendToBrowser(String msg){
        try {
            for(String session_id:OnlineClient1Sessions.keySet()){
                Session session=OnlineClient1Sessions.get(session_id);
                session.getBasicRemote().sendText(msg);
//                System.out.println("in sendToBrowser.msg:"+msg);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @OnClose
    public void Onclose(Session session){
//        log.info("websocket close,session id: "+session.getId());
        String client_name = (String) session.getUserProperties().get("client_name");
        if(client_name.equals(client_name_1)){
            OnlineClient1Sessions.remove(session.getId());
        }else if(client_name.equals(client_name_2)){
            OnlineClient2Sessions.remove(session.getId());
        }
        System.out.println("a session closed.client_name:"+client_name+"session_id:"+session.getId());
    }

//    @Scheduled(fixedRate = 2000)
//    public void send() throws IOException {
//        for(String key:OnlineClient1Sessions.keySet()){
//            OnlineClient1Sessions.get(key).getBasicRemote().sendText("heartbeat");
//        }
//    }
}
