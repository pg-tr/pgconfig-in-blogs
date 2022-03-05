package com.blogcrawling.api.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "blog")
public class Blog {

	@EmbeddedId
	BlogIdentity identity;

	String title;

	public Blog(BlogIdentity identity, String title) { 
		this.identity = identity;
		this.title = title;
	}
}
