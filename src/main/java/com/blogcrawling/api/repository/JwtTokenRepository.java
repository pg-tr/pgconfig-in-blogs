package com.blogcrawling.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.blogcrawling.api.domain.JwtToken;
import com.blogcrawling.api.domain.JwtTokenIdentity;

public interface JwtTokenRepository extends CrudRepository<JwtToken, JwtTokenIdentity> {

	Optional<JwtToken> findByIdentity_JwtToken(String token);

	boolean existsByIdentity_JwtToken(String token);
}
