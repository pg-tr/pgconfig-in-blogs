package com.blogcrawling.api.configuration.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// Get authorization header and validate
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		header = header != null ? header : "";
		if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		final String token = header.split(" ")[1].trim();

		if (!jwtTokenUtil.validate(token)) {
			chain.doFilter(request, response);
			return;
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(null, null,
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}
}
