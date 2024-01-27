package com.cjg.chat.dto;

import lombok.Data;

@Data
public class Message {
	
	private String type;
	private String roomId;
	private String userId;
	private String message;

}
