package com.blogcrawling.api.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blogcrawling.api.repository.JwtTokenRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

	@Autowired
	JwtTokenRepository jwtTokenRepository;

	public boolean validate(String token) {

		return jwtTokenRepository.existsByIdentity_JwtToken(token);
	}

}
