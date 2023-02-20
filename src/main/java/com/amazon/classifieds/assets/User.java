package com.amazon.classifieds.assets;

/**
 * The class User acts as a POJO which holds entity details for a User.
 * It supports retrieval and storage of User details. It is used in Upper Layer-to-Middle
 *  Layer/Operations package-to-Managers package data transmission.
 * About attributes :
 * userId - Unique ID
 * name - Namec
 * email - email of user
 * contactNo - contactNo of user
 * password - password of user
 * walletbalance - Wallet Balance of user
 * isActive	- User Account Status
 **/

public class User {
	private int userId;
	private String name;
	private String email;
	private String contactNo;
	private String password;
	private float walletBalance;
	private boolean isActive;
	
	public User(int userid, String name,  String email, String contactNo, String password, float walletBalance) {
		super();
		this.userId = userid;
		this.name = name;
		this.email = email;
		this.contactNo = contactNo;
		this.password = password;
		this.walletBalance = walletBalance;
		this.isActive =true;
	}
	
	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public String getPassword() {
		return password;
	}

	public float getWalletBalance() {
		return walletBalance;
	}

	public boolean isActive() {
		return isActive;
	}
}

