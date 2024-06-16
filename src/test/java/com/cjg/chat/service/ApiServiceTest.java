package com.cjg.chat.service;


import com.cjg.chat.dto.User;
import com.cjg.chat.repository.ChatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {

    @Mock
    ChatRepository chatRepository;

    @InjectMocks
    ApiService apiService;

    @Test
    @DisplayName("isMember")
    public void isMember(){

        Map<String, String> param = new HashMap();
        param.put("roomId", "1");
        param.put("userId", "coolcjg");

        User user = new User();

        user.setRoomId(param.get("roomId"));
        user.setUserId(param.get("userId"));

        given(chatRepository.isMember(user)).willReturn(false);

        Map<String, Object> result = apiService.isMember(param);

        Assertions.assertThat(result.get("message")).isEqualTo("ok");
    }

    @Test
    @DisplayName("isMember")
    public void isMember_existUser(){

        Map<String, String> param = new HashMap();
        param.put("roomId", "1");
        param.put("userId", "coolcjg");

        User user = new User();

        user.setRoomId(param.get("roomId"));
        user.setUserId(param.get("userId"));

        given(chatRepository.isMember(user)).willReturn(true);

        Map<String, Object> result = apiService.isMember(param);

        Assertions.assertThat(result.get("message")).isEqualTo("userId is already in room");
    }

    @Test
    @DisplayName("isMember")
    public void isMember_paramNull(){
        Map<String, String> param = new HashMap();
        Map<String, Object> result = apiService.isMember(param);
        Assertions.assertThat(result.get("message")).isEqualTo("parameter is null");
    }
}