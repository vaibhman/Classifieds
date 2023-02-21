package com.amazon.classifieds;

import com.amazon.classifieds.operations.AppDriver;

import java.sql.SQLException;

import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.dbtools.ConnectionManager;
import com.amazon.classifieds.managers.ClassifiedManager;

public class App 
{
	public static void main( String[] args ) throws ClassNotFoundException, SQLException
	{
		if(args.length>0) {
			ConnectionManager.FILEPATH=args[0];
		}
		
		try {
			ConnectionManager.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Connection Issue..");
			e.printStackTrace();
		}	
		
		System.out.println( "************************************************" );
		System.out.println( "Welcome to Amazon Internal  Classified App" );
		System.out.println( "************************************************" );
		
		AppDriver appDriver= new AppDriver();
		appDriver.initiate();

	}
}