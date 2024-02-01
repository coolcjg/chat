package com.cjg.chat.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjg.chat.service.ApiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {
	
	private final ApiService apiService;
	
	@GetMapping("/isMember")
	public Map<String, Object> isMember(@RequestParam(required = false) Map<String,String> param){
		return apiService.isMember(param);
	}
}
