package com.cjg.chat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class ChatHandler implements ChannelInterceptor{
	
	Logger logger = LoggerFactory.getLogger(ChatHandler.class);
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel){
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);		
		logger.info(accessor.toString());
		
		accessor.getCommand();
		
		return message;
	}
	

}
