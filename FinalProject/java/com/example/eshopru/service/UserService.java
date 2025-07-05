package com.example.eshopru.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.eshopru.exception.MyException;
import com.example.eshopru.jwt.JwtUtil;
import com.example.eshopru.model.AuthorityEntity;
import com.example.eshopru.model.UserEntity;
import com.example.eshopru.repository.AuthorityRepository;
import com.example.eshopru.repository.UserRepository;

@Service
public class UserService {
@Autowired
UserRepository user_rep;
@Autowired
AuthorityRepository authority_rep;
@Autowired
JwtUtil jwt_util;
@Autowired
PasswordEncoder password_enc;

public void register(UserEntity userEntity) {
	if(user_rep.findByUsername(userEntity.getUsername()).isPresent()) {
		throw new RuntimeException("this user alr exists bro");
	} 
		UserEntity user_ent = new UserEntity();
		user_ent.setUsername(userEntity.getUsername());
		user_ent.setPassword(password_enc.encode(userEntity.getPassword()));
		user_ent.setEnabled(true);
		user_ent.setPhone_number(userEntity.getPhone_number());
		user_ent.setEmail(userEntity.getEmail());
		
		AuthorityEntity authority_ent = new AuthorityEntity();
		authority_ent.setUsername(user_ent.getUsername());
		authority_ent.setAuthority("ROLE_USER");
		user_rep.save(user_ent);
		authority_rep.save(authority_ent);
	
}

	public String login(String username, String password) {
		UserEntity user_ent = user_rep.findByUsername(username).orElseThrow(() -> new RuntimeException("there is no user like that lol"));
		if(!password_enc.matches(password, user_ent.getPassword())) {
			throw new RuntimeException("wrong password, r u sure its ur acc?");
		}
		return jwt_util.generateToken(username);
	}
	
	public void logout(String token) {
		SecurityContextHolder.clearContext();
		jwt_util.invalidateToken(token);
	}
	
	public UserEntity getUserInfo(String username) {
		return user_rep.findByUsername(username).orElseThrow(() -> new RuntimeException("There arent such usernames dawg"));
	}
}
