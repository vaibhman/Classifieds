package com.amazon.classifieds.dbtools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The class ConnectionManager is a Singleton implementation which controls single-point creation of
 * a connection object from a DB.
 * It sends the connection instance to other classes of the Lower Layer/DB Layer/dbTools Package.
 * Only Lower Layer classes interact with this class to get connection instance.
 **/

public class ConnectionManager {

	private static Connection con;
	public static String FILEPATH="/Users/vaibh/eclipse-workspace/classifieds/src/main/java/dbconfig.txt";
	public static String URL="jdbc:mysql://localhost:3306/classifieds_db?serverTimezone=UTC";
	public static String USER="root";
	public static String PASSWORD="password";

	public ConnectionManager() { }

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		try{
			File file = new File(FILEPATH);
			if(file.exists()){
				FileReader reader = new FileReader(file);
				BufferedReader buffer= new BufferedReader(reader);
				URL = buffer.readLine();
				USER= buffer.readLine();
				PASSWORD= buffer.readLine();
				buffer.close();
				reader.close();
				System.out.println("[DB] configured using files");
			}
		}catch(Exception e){
			System.err.println("FILEPATH argument Error: "+e);
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			return con;
		} catch (Exception e) {
			System.out.println("Connection Issue Found");
			e.printStackTrace();
			throw e;
		}
	}
}


