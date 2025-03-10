package com.ruoyi.heart_beat.controller;

import javax.servlet.http.HttpSession;

import com.ruoyi.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.*;
@RestController
public class HeartBeatController extends BaseController {
    @PostMapping("/heartbeat1") // 等同于 @RequestMapping(value = "/heartbeat1", method = RequestMethod.POST)
    public String test(@RequestBody String s, HttpSession session) {
        System.out.println("in HeartBeatController.Received client_name: " + s+" session_id:"+session.getId());
        session.setAttribute("client_name",s);
        return s; // 返回接收到的字符串
    }//作为websocket客户端的有两个，一个是前端，一个是控制节点。其client_name分别为browse,controller

    @GetMapping("/getName")
    public String getName(HttpSession session){
        String client_name= (String) session.getAttribute("client_name");
        //System.out.println("return client_name: "+client_name+ "  session_id: "+session.getId());
        return client_name;
    }
}
