package com.system.user.user.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {

	 private int statusCode;
	    private String message;

	    public ErrorResponse(String message)
	    {
	        super();
	        this.message = message;
	    }

}
