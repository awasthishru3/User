package com.system.user.user.exception;

import lombok.Getter;


@Getter
public class UserAlreadyPresentException extends RuntimeException {
	private String message;

    public UserAlreadyPresentException() {}

    public UserAlreadyPresentException(String msg) {
        super(msg);
        this.message = msg;
    }
}
