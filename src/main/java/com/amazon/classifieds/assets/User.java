package com.amazon.classifieds.assets;


public class User {
	private int userId;
	private String name;
	private String email;
	private String contactNo;
	private String password;
	private float walletBalance;
	
	public User(int userid, String name,  String email, String contactNo, String password, float walletBalance) {
		super();
		this.userId = userid;
		this.name = name;
		this.contactNo = contactNo;
		this.password = password;
		this.walletBalance = walletBalance;
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
}

