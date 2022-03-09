package com.blogcrawling.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blogcrawling.api.domain.Blog;
import com.blogcrawling.api.domain.BlogIdentity;

@Repository
public interface BlogRepository extends CrudRepository<Blog, BlogIdentity> {

	List<Blog> findAllByIdentity_Param(String param);
}
