package com.amazon.classifieds.managers;

import java.sql.SQLException;

import com.amazon.classifieds.assets.User;
import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.customExceptions.UserException;
import com.amazon.classifieds.operations.OperationFactory;
import com.amazon.classifieds.queryHelper.QueryBuilder;

/**
 * The class UserManager is a child class of BaseManager.
 * It works as a middle layer between the dbTools package/Lower Layer and the Operations
 * package/Upper Layer.
 * It contains functions related to User table such as read, create a record, validate data
 * from table, operation specific functions which require db support, etc.
 * It is used by the Upper Layers/Operations classes.
 * It utilizes Lower Layer/dbTools package and helper classes via the parent - BaseManager which
 * converts system exceptions to ApplicationExceptions.
 **/

public class UserManager extends BaseManager {

	private static UserManager userManager;

	public static UserManager getInstance() {
		if (userManager == null) {
			userManager = new UserManager();
		}
		return userManager;
	}

	public void create(User user) throws ApplicationException {
		QueryBuilder queryBuilder = this.getInsertInstance()
				.onTable("user")
				.insertValue("userid", user.getUserId())
				.insertValue("name", user.getName())
				.insertValue("email", user.getEmail())
				.insertValue("contactno", user.getContactNo())
				.insertValue("password", user.getPassword())
				.insertValue("walletBalance", user.getWalletBalance())
				.insertValue("isActive","true");

		String sqlQuery = this.buildQuery(queryBuilder);

		this.executeQuery(sqlQuery);
	}

	public void update(int employeeId, String field, String newValue) throws ApplicationException {
		QueryBuilder queryBuilder = this.getUpdateInstance()
				.updateValue(field, newValue)
				.whereEq("userid", employeeId)
				.onTable("user");
		String sqlQuery = this.buildQuery(queryBuilder);

		this.executeQuery(sqlQuery);
	}
	
	public boolean isValidUserPassword(int userId, String password) throws ApplicationException {
		QueryBuilder queryBuilder = this.getSelectInstance()
				.selectColumns("userid")
				.onTable("user")
				.whereEq("userid", userId)
				.whereEq("password", password);

		String sqlQuery = this.buildQuery(queryBuilder);


		return this.hasResult(sqlQuery);
	}
	
	public boolean isValidUser(int userId) throws ApplicationException {
		QueryBuilder queryBuilder = this.getSelectInstance()
				.selectColumns("userid")
				.onTable("user")
				.whereEq("userid", userId);

		String sqlQuery = this.buildQuery(queryBuilder);

		return this.hasResult(sqlQuery);
	}
	
	public boolean isUserActive(int userId)  throws ApplicationException {
		QueryBuilder queryBuilder = this.getSelectInstance()
				.selectColumns("userId")
				.onTable("user")
				.whereEq("userId", userId)
				.whereEq("isActive", "true");

		String sqlQuery = this.buildQuery(queryBuilder);
		
		return this.hasResult(sqlQuery);
	}

	
	public float getWalletBalance(int userId) throws ClassNotFoundException, SQLException, ApplicationException {	

		QueryBuilder queryBuilder = this.getSelectInstance()
	              .selectColumns("walletBalance")
	              .onTable("user")
	              .whereEq("userId", userId);
		
		String sqlQuery = this.buildQuery(queryBuilder);

		return this.getQueryNumberFloat(sqlQuery);
	}
	
	public boolean setWalletBalance(int userId, float newValue) throws ClassNotFoundException, SQLException, ApplicationException {	

	    QueryBuilder queryBuilder = this.getUpdateInstance()
	            .onTable("user")
	            .updateValue("walletBalance", newValue)
	            .whereEq("userId", userId);

	    String sqlQuery = this.buildQuery(queryBuilder);

	    this.executeQuery(sqlQuery);
	    
		return true;
	}
	
	public boolean addMoneytoWallet(int userId, float walletBalance) throws ClassNotFoundException, SQLException, ApplicationException, UserException {
		System.out.println("Enter Amount: ");
		float amount = this.getAmountInput(); 
		
		System.out.println("Enter upi id: ");
		@SuppressWarnings("unused")
		String upiId = OperationFactory.getScannerInstance().next(); 
		
		System.out.println("Enter password: ");
		@SuppressWarnings("unused")
		String pass = OperationFactory.getScannerInstance().next(); 
		
		float newValue = walletBalance+amount;

		return UserManager
		.getInstance()
		.setWalletBalance(userId, newValue);
	}
	
	public boolean withdrawMoneyFromWallet(int userId, float walletBalance) throws ClassNotFoundException, SQLException, ApplicationException, UserException {
		System.out.println("Enter Amount to Withdraw: ");
		float amount = this.getAmountInput();  
		
		if(amount>walletBalance) {
			System.out.println("\nCan't withdraw more that Wallet balance\n");
			return false;
		}
		System.out.println("Enter Bank Account No. : ");
		@SuppressWarnings("unused")
		String bankNo = OperationFactory.getScannerInstance().next(); 

		float newValue = walletBalance-amount;

		return UserManager
		.getInstance()
		.setWalletBalance(userId, newValue);
	}
	
}

