package com.blogcrawling.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/blogcrawling/")
public class MainController {

	@GetMapping("hello/")
	public ResponseEntity<String> getCompany() {
		return ResponseEntity.ok().body("Blog Crawling");
	}

}
