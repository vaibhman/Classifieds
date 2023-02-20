package com.amazon.classifieds;

import com.amazon.classifieds.operations.AppDriver;

import java.sql.SQLException;

import com.amazon.classifieds.dbtools.ConnectionManager;

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
		
		try {
			ConnectionManager.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AppDriver appDriver= new AppDriver();
		appDriver.initiate();

	}
}