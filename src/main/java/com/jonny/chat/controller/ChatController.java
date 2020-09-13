package com.jonny.chat.controller;

import com.jonny.chat.bean.Message;
import com.jonny.chat.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //定时广播推送消息
    @Scheduled(fixedRate = 1000)
    public void sendTopicMessage() {
        Message message = new Message();
        message.setContent(DateUtil.date2String(new Date()));
        this.simpMessagingTemplate.convertAndSend("/topic", message);
    }

    /**
     * 广播通信
     */
    @MessageMapping("/send/message")//客户端访问服务端的时候config中配置的服务端接收前缀也要加上 例：/app/change-notice
    @SendTo("/message")//config中配置的订阅前缀记得要加上
    public Message topic(Principal principal, Message message) {
        //我们使用这个方法进行消息的转发发送
        //this.simpMessagingTemplate.convertAndSend("/message", message);
        //也可以使用sendTo发送
        message.setFrom(principal.getName());
        return message;
    }

    /**
     * 点对点通信
     *
     * @param principal
     * @param message
     * @MessageMapping("/chat") 发送到/chat上
     */
    @MessageMapping("/chat")
    public void index(Principal principal, Message message) {
        message.setFrom(principal.getName());
        //发送到/queue/{user}/get
//        this.simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/get", message);
        this.simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/get", message);
    }
}
