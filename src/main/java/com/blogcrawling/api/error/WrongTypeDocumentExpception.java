package com.blogcrawling.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(value = HttpStatus.CONFLICT)
@Getter
public class WrongTypeDocumentExpception extends RuntimeException {

	private static final long serialVersionUID = -13381249569934324L;

	private String type;

	public WrongTypeDocumentExpception(String message, String tpye) {
		super(message);
		this.type = tpye;
	}

}
