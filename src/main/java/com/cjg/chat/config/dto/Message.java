package com.cjg.chat.config.dto;

import lombok.Data;

@Data
public class Message {
	
	private String roomId;
	private String userId;
	private String message;

}
