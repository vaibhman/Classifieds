package com.amazon.classifieds.customExceptions;

@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	public ApplicationException(String message) {
		super(message);
	}
}
