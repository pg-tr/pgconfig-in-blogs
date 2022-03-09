package com.blogcrawling.api.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Embeddable
public class BlogIdentity implements Serializable {

	private static final long serialVersionUID = 8662414833289268874L;

	@NotBlank
	String param;

	@NotBlank
	String blog_url;
}
