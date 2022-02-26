package com.blogcrawling.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConnectionException extends RuntimeException {
	private static final long serialVersionUID = 965333870764109619L;

	public ConnectionException(String message) {
		super(message);
	}
}
