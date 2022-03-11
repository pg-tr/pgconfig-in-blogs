package com.blogcrawling.api.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Embeddable
public class JwtTokenIdentity implements Serializable {

	private static final long serialVersionUID = -1728801118554751588L;

	@NotBlank
	String userName;
	@NotBlank
	String jwtToken;

}
