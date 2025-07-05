package com.example.eshopru.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
public static String GetCurrentuser() {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	return auth.getName();
}
}
