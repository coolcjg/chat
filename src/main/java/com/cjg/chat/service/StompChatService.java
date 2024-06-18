package com.cjg.chat.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import com.cjg.chat.dto.MessageDto;
import com.cjg.chat.dto.User;
import com.cjg.chat.repository.ChatRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StompChatService {
	
	Logger logger = LoggerFactory.getLogger(StompChatService.class);
	
	private final ChannelTopic channelTopic;
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;
	private final ChatRepository chatRepository;

	
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
		
		chatRepository.setSessionId_User(sessionId, user);
		chatRepository.setRoomId_UserId(user);
	}
	
	public void sendExitMessage(Message<?> message) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		//채팅 나가기 버튼 눌렀을 때 중복 메시지 전달 방지
		if(accessor.getFirstNativeHeader("receipt") != null){
			return;
		}

		String sessionId = accessor.getSessionId();
						
		User user = chatRepository.getSessionId_User(sessionId);
		logger.info("sendExitMessage user : "+ user);
		
		MessageDto messageDto = new MessageDto();
		messageDto.setType("exit");
		messageDto.setRoomId(user.getRoomId());
		messageDto.setUserId(user.getUserId());
		
		redisTemplate.convertAndSend(channelTopic.getTopic(), messageDto);
		chatRepository.removeRoomId_UserId(user);
		
	}
	
	public Long getUserCountInRoom(String roomId) {
		return chatRepository.getUserCountInRoom(roomId);
	}
	
	public Set<String> getUserListInRoom(String roomId) {
		return chatRepository.getUserListInRoom(roomId);
	}
	

}
