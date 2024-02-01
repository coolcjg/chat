package com.cjg.chat.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import com.cjg.chat.dto.User;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRepository {
	
	@Resource(name="redisTemplate")
	private HashOperations<String, String, Object> hashOps;
	
	@Resource(name="redisTemplate")
	private SetOperations<String, String> setOps;
	
	private final String SESSIONID_USER = "SessionId_User";
	private final String ROOMID_USERID = "RoomId_UserId_";

	public void setSessionId_User(String sessionId, User user) {
		hashOps.put(SESSIONID_USER, sessionId, user);
	}
	
	public User getSessionId_User(String sessionId) {
		return (User)hashOps.get(SESSIONID_USER, sessionId);
	}
	
	public void setRoomId_UserId(User user) {
		setOps.add(ROOMID_USERID + user.getRoomId(), user.getUserId());
	}
	
	public boolean isMember(User user) {
		return setOps.isMember(ROOMID_USERID + user.getRoomId(), user.getUserId());
	}
	
	public Long removeRoomId_UserId(User user) {
		return setOps.remove(ROOMID_USERID + user.getRoomId(), user.getUserId());
	}	
	
}
