package com.amazon.classifieds;

import com.amazon.classifieds.operations.AppDriver;
import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.dbtools.ConnectionManager;
import com.amazon.classifieds.managers.UserManager;

public class App 
{
	public static void main( String[] args )
	{
		System.out.println( "************************************************" );
		System.out.println( "Welcome to Amazon Internal  Classified App" );
		System.out.println( "************************************************" );
		
		if(args.length>0) {
			ConnectionManager.FILEPATH=args[0];
		}
				
		AppDriver appDriver= new AppDriver();
		appDriver.initiate();

	}
}