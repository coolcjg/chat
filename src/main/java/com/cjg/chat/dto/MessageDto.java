package com.cjg.chat.dto;

import java.util.Set;

import lombok.Data;

@Data
public class MessageDto {
	
	private String messageId;
	private String type;
	private String roomId;
	private String userId;
	private String message;
	private String time;
	private Long userCount;
	private Set<String> userList;

}
