package com.ruoyi.heart_beat.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import java.util.Collections;

public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        response.getHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));


        String uri = request.getRequestURI().toString();
        System.out.println("Handshake URI: " + uri);

        // 解析 URI 参数
        String userId = null;
        if (uri.contains("?")) {
            String queryString = uri.split("\\?")[1];
            String[] params = queryString.split("&");
            for (String param : params) {
                if (param.startsWith("client_name=")) {
                    userId = param.split("=")[1];
                    break;
                }
            }
        }

        if (userId != null) {
            sec.getUserProperties().put("client_name", userId);
            System.out.println("User ID stored in WebSocket config: " + userId);
        } else {
            System.out.println("No user ID found in handshake request");
        }

        response.getHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
    }
}
