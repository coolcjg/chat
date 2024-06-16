package com.cjg.chat.service;

import com.cjg.chat.dto.MessageDto;
import com.cjg.chat.repository.ChatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@ExtendWith(MockitoExtension.class)
public class StompChatServiceTest {

    @InjectMocks
    StompChatService stompChatService;

    @Mock
    ChannelTopic channelTopic;

    @Mock
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    ChatRepository chatRepository;

    @Test
    @DisplayName("sendMessage")
    public void sendMessage(){
        MessageDto messageDto = new MessageDto();
        stompChatService.sendMessage(messageDto);
    }
}