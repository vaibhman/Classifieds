package com.amazon.classifieds.managers;

import com.amazon.classifieds.assets.User;
import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.queryHelper.QueryBuilder;

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
				.insertValue("walletBalance", user.getWalletBalance());

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
}

