package com.amazon.classifieds.operations;

import com.amazon.classifieds.customExceptions.ApplicationException;

public class AdminOperation {
	public boolean showMenu() throws ApplicationException{
		System.out.println("--------------------------------------");
		System.out.println("--------Welcome to Admin Menu--------");
		System.out.println("--------------------------------------");
		
		return true;
	}
}
