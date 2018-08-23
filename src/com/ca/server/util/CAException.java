package com.ca.server.util;

public class CAException extends Exception{
	
private static final long serialVersionUID= 100L;
	
	private String errorMessage;
	private int errorCode;

	public String getErrorMessage() {
		return errorMessage;
	}
	public int getErrorCode(){
		return errorCode;
	}
	public CAException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	public CAException(String errorMessage, int errorCode) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.errorCode=errorCode;
	}
	public CAException() {
		super();
	}

}
