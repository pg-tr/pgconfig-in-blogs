package com.blogcrawling.api.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "jwt_token")
public class JwtToken {

	@Id
	JwtTokenIdentity identity;

}
