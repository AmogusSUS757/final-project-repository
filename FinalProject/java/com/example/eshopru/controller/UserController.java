package com.example.eshopru.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eshopru.jwt.JwtUtil;
import com.example.eshopru.jwt.SecurityUtil;
import com.example.eshopru.model.UserEntity;
import com.example.eshopru.request.UserLoginRequest;
import com.example.eshopru.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
 	private UserService user_service;
 
 	@Autowired
 	private AuthenticationManager auth_manager;
 
 	@Autowired
 	private JwtUtil jwtUtil;
 
 	@PostMapping("/login")
 	public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginRequest user_login_req) {
 		String token = user_service.login(user_login_req.getUsername(), user_login_req.getPassword());
 		return ResponseEntity.ok(Map.of("token", token));
 	}
 
 	@PostMapping("/register")
 	public void register(@Valid @RequestBody UserEntity userEntity) {
 
 		user_service.register(userEntity);
 	}
 	
 	@PostMapping("/logout")
 	public void logout(HttpServletRequest request) {
 		String header = request.getHeader("Authorization");
 		String token = header.substring(7);
 		user_service.logout(token);
 	}
 	
 	@GetMapping("/user")
 	public UserEntity userInfo() {
 		String username = SecurityUtil.GetCurrentuser();
 		return user_service.getUserInfo(username);
 	}
}
