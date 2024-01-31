package com.cjg.chat.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import com.cjg.chat.dto.MessageDto;
import com.cjg.chat.dto.User;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StompChatService {
	
	private final ChannelTopic channelTopic;
	private final RedisTemplate<String, Object> redisTemplate;
	
	@Resource(name="redisTemplate")
	private HashOperations<String, Object, Object> hashOps;
	
	public void sendMessage(MessageDto message) {
		redisTemplate.convertAndSend(channelTopic.getTopic(), message);
	}
	
	public void setSessionId_User(Message<?> message) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		String roomId = accessor.getNativeHeader("roomId").get(0);
		String userId = accessor.getNativeHeader("userId").get(0);
		
		String sessionId = accessor.getSessionId();
		User user = new User();
		user.setRoomId(roomId);
		user.setUserId(userId);
		
		hashOps.put("SessionId_User", sessionId, user);
	}
	
	public void sendExitMessage(Message<?> message) {
		
		//방 정보 가져오기
		
		//이름 가져오기
		
	}
	
	

}
