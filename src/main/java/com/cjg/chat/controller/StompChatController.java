package com.cjg.chat.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.cjg.chat.dto.Message;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompChatController {
	
	private final SimpMessagingTemplate template;
	
	@MessageMapping(value = "/chat/message")
	public void message(Message message, Principal principal) {
		System.out.println("message : " + message);
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}

}
