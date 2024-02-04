package com.cjg.chat.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.cjg.chat.dto.MessageDto;
import com.cjg.chat.service.StompChatService;

@Controller
public class StompChatController {
	
	@Autowired
	StompChatService stompChatService;
	
	@MessageMapping(value = "/chat/message")
	public void message(MessageDto message, Principal principal) {
		message.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		message.setMessageId(UUID.randomUUID().toString());
		
		stompChatService.sendMessage(message);
	}

}
