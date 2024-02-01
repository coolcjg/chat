package com.cjg.chat.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cjg.chat.dto.User;
import com.cjg.chat.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiService {
	
	private final ChatRepository chatRepository;
		
	public Map<String, Object> isMember(Map<String,String> param){
		
		Map<String, Object> result = new HashMap();
		
		if(param.get("roomId") == null || param.get("userId") == null) {
			result.put("code", 400);
			result.put("message", "parameter is null");
			return result;
		}
		
		User user = new User();
		user.setRoomId(param.get("roomId").toString());
		user.setUserId(param.get("userId").toString());
		
		if(chatRepository.isMember(user)) {
			result.put("code", 400);
			result.put("message", "userId is already in room");			
		}else {
			result.put("code", 200);
			result.put("message", "ok");
		}
		
		return result;
	}
	
	
	

}
