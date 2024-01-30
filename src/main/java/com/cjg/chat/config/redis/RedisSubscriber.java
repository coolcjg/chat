package com.cjg.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.cjg.chat.dto.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
	
	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;
	private final SimpMessagingTemplate messagingTemplate;
	
	Logger logger = LoggerFactory.getLogger(RedisSubscriber.class);
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {			
			String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
			MessageDto messageDto = objectMapper.readValue(publishMessage, MessageDto.class);
			messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
		}catch(Exception e) {
			logger.error("ERROR : ", e);
		}
	}
}
