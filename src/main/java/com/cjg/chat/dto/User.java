package com.cjg.chat.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {
	
	private String roomId;
	private String userId;
	private String userName;

}
