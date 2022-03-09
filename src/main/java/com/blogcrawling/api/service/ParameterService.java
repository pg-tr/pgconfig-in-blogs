package com.blogcrawling.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogcrawling.api.domain.Blog;
import com.blogcrawling.api.repository.BlogRepository;

@Service
public class ParameterService {

	@Autowired
	BlogRepository blogRepository;

	public List<Blog> getBlogByParam(String param) {
		
		List<Blog> blog = blogRepository.findAllByIdentity_Param(param);

		return blog;
	}

}
