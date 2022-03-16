package com.blogcrawling.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogcrawling.api.domain.Blog;
import com.blogcrawling.api.service.ParameterService;

@RestController
@RequestMapping("api/blogcrawling/")
public class MainController {

	@Autowired
	ParameterService service;

	@GetMapping("blogs/{param}")
	public ResponseEntity<List<Blog>> getByParam(@PathVariable(name = "param", required = true) String param) {
		return ResponseEntity.ok().body(service.getBlogByParam(param));
	}

}
