package com.cjg.chat.repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

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

		//만료기간 설정
		hashOps.getOperations().expire(SESSIONID_USER, 7, TimeUnit.DAYS);
	}

	public void removeSessionId_User(String sessionId, User user){
		hashOps.delete(SESSIONID_USER, sessionId, user);
	}
	
	public User getSessionId_User(String sessionId) {
		return (User)hashOps.get(SESSIONID_USER, sessionId);
	}
	
	public void setRoomId_UserId(User user) {
		setOps.add(ROOMID_USERID + user.getRoomId(), user.getUserId());

		//만료기간 설정
		setOps.getOperations().expire(ROOMID_USERID + user.getRoomId(), 7, TimeUnit.DAYS);
	}
	
	public boolean isMember(User user) {
		return setOps.isMember(ROOMID_USERID + user.getRoomId(), user.getUserId());
	}
	
	public Long removeRoomId_UserId(User user) {
		return setOps.remove(ROOMID_USERID + user.getRoomId(), user.getUserId());
	}
	
	public Long getUserCountInRoom(String roomId) {
		return setOps.size(ROOMID_USERID + roomId);
	}
	
	public Set<String> getUserListInRoom(String roomId) {
		return setOps.members(ROOMID_USERID + roomId);
	}
	
}
