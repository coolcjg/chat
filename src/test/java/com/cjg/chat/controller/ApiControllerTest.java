package com.cjg.chat.controller;


import com.cjg.chat.config.SecurityConfig;
import com.cjg.chat.service.ApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = ApiController.class
        , includeFilters={
        @ComponentScan.Filter(
                type= FilterType.ASSIGNABLE_TYPE,
                classes = SecurityConfig.class
        )
})
public class ApiControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ApiService apiService;

    @Test
    @DisplayName("isMember")
    public void isMember() throws Exception{

        Map<String,String> param = new HashMap<>();
        param.put("roomId", "1");
        param.put("userId", "coolcjg");

        Map<String, Object> result = new HashMap();
        result.put("message" , "ok");

        given(apiService.isMember(param)).willReturn(result);

        MultiValueMap<String, String> mvm = new LinkedMultiValueMap(
                param.entrySet().stream().collect(
                        Collectors.toMap(Map.Entry::getKey, e -> Arrays.asList(e.getValue()))
                )
        );

        mvc.perform(get("/isMember").params(mvm))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("ok"))
                .andDo(print());
    }
}