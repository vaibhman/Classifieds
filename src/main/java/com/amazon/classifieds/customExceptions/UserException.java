package com.amazon.classifieds.customExceptions;

@SuppressWarnings("serial")
public class UserException extends Exception {

	public UserException(String message) {
		super(message);
	}
}