package com.cjg.chat.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.cjg.chat.service.StompChatService;

@Component
public class ChatHandler implements ChannelInterceptor{
	
	Logger logger = LoggerFactory.getLogger(ChatHandler.class);
	
	@Autowired
	StompChatService stompChatService;
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel){
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);		
		logger.info(accessor.toString());
		
		if(accessor.getCommand() == StompCommand.CONNECT) {
			stompChatService.setSessionId_User(message);
		}else if(accessor.getCommand() == StompCommand.DISCONNECT) {
			stompChatService.sendExitMessage(message);
			logger.info("DISCONNECT");
		}
		
		return message;
	}
	

}
