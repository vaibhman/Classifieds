package com.amazon.classifieds.managers;

import java.sql.SQLException;

import com.amazon.classifieds.dbtools.QueryExecutor;

public class ReportManager extends BaseManager{

	private static ReportManager reportManager;

	public static ReportManager getInstance() {
		if (reportManager == null) {
			reportManager = new ReportManager();
		}
		return reportManager;
	}
	
	public int getUserCount() throws ClassNotFoundException, SQLException {
		return QueryExecutor.getInstance().getQueryNumber("SELECT COUNT(userId) from user");
	}
	
	public int getActiveUsersCount() throws ClassNotFoundException, SQLException {
		return QueryExecutor.getInstance().getQueryNumber("SELECT COUNT(userId) from user where isActive='true'");
	}
	
	public int getDeActivatedUsersCount() throws ClassNotFoundException, SQLException {
		return QueryExecutor.getInstance().getQueryNumber("SELECT COUNT(userId) from user where isActive='false'");
	}
	
	public int getClassifiedsCount() throws ClassNotFoundException, SQLException {
		return QueryExecutor.getInstance().getQueryNumber("SELECT COUNT(classifiedId) from classifieds");
	}

	public int getPendingClassifiedsCount() throws ClassNotFoundException, SQLException {
		return QueryExecutor.getInstance().getQueryNumber("SELECT COUNT(classifiedId) from classifieds where cStatus='Pending Approval'");
	}
	
	public int getApprovedClassifiedsCount() throws ClassNotFoundException, SQLException {
		return QueryExecutor.getInstance().getQueryNumber("SELECT COUNT(classifiedId) from classifieds where cStatus='Approved'");
	}
	
	public int getRejectedClassifiedsCount() throws ClassNotFoundException, SQLException {
		return QueryExecutor.getInstance().getQueryNumber("SELECT COUNT(classifiedId) from classifieds where cStatus='Rejected'");
	}
	
}
