package com.system.user.user.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{

	private String message;

    public UserNotFoundException() {}

    public UserNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
	
	
}
