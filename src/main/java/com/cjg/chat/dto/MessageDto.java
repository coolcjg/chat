package com.cjg.chat.dto;

import lombok.Data;

@Data
public class MessageDto {
	
	private String type;
	private String roomId;
	private String userId;
	private String message;
	private String time;
	private Long userCount;

}
