package com.cjg.chat.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.cjg.chat.dto.MessageDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompChatController {
	
	private final RedisTemplate<String, Object> redisTemplate;
	private final ChannelTopic channelTopic;
	
	@MessageMapping(value = "/chat/message")
	public void message(MessageDto message, Principal principal) {
		message.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));		
		System.out.println("message : " + message);
		
		redisTemplate.convertAndSend(channelTopic.getTopic(), message);
	}

}
