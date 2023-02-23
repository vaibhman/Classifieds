package com.amazon.classifieds.managers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazon.classifieds.assets.Classified;
import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.queryHelper.QueryBuilder;

/**
 * The class ClassifiedManager is a child class of BaseManager.
 * It works as a middle layer between the dbTools package/Lower Layer and the Operations
 * package/Upper Layer.
 * It contains functions related to classifieds table such as read, create a record, validate data
 * from table, operation specific functions which require db support, etc.
 * It is used by the Upper Layers/Operations classes.
 * It utilizes Lower Layer/dbTools package and helper classes via the parent - BaseManager which
 * converts system exceptions to ApplicationExceptions.
 **/

public class ClassifiedManager extends BaseManager{

	private static ClassifiedManager classifiedManager;

	public static ClassifiedManager getInstance() {
		if (classifiedManager == null) {
			classifiedManager = new ClassifiedManager();
		}

		return classifiedManager;
	}

	public void create(Classified classified) throws ApplicationException {

		QueryBuilder queryBuilder = this.getInsertInstance()
				.onTable("classifieds")
				.insertValue("classifiedId", classified.getClassifiedId())
				.insertValue("userId",classified.getUserId())
				.insertValue("cStatus",classified.getcStatus())
				.insertValue("productName",classified.getProductName())
				.insertValue("headLine",classified.getHeadLine())
				.insertValue("brand", classified.getBrand())
				.insertValue("pCondition", classified.getpCondition())
				.insertValue("pDescription",classified.getpDescription())
				.insertValue("pCategory",classified.getpCategory())
				.insertValue("price", classified.getPrice())
				.insertValue("recurringUnit",classified.getRecurringUnit());

		String sqlQuery=this.buildQuery(queryBuilder);

		this.executeQuery(sqlQuery);
	}

	public void update(int classifiedId, String field, String newValue) throws ApplicationException {
		QueryBuilder queryBuilder = this.getUpdateInstance()
				.onTable("classifieds")
				.updateValue(field, newValue)
				.whereEq("classifiedId", classifiedId);

		String sqlQuery = this.buildQuery(queryBuilder);

		this.executeQuery(sqlQuery);
	}

	public void delete(int classifiedIdToRemove) throws ApplicationException {
		QueryBuilder queryBuilder = this.getDeleteInstance()
				.onTable("classifieds")
				.whereEq("classifiedId", classifiedIdToRemove);

		String sqlQuery = this.buildQuery(queryBuilder);

		this.executeQuery(sqlQuery);
	}

	public String getpConditionString(int pCondition) {
		String[] productConditions = {"", "Brand New", "Lightly Used", "Moderately Used"
				,"Heavily Used","Damaged/Dented","Not Working"};

		return productConditions[pCondition];
	}

	public boolean viewAllClassifieds() throws ApplicationException, ClassNotFoundException, SQLException{
		String[] columns = {"classifiedId", "userId", "cStatus", 
				"productName", "headLine", "brand", 
				"pCondition", "pDescription", "pCategory", "price", "recurringUnit"};

		QueryBuilder queryBuilder = this.getSelectInstance()
				.selectColumns(columns)
				.onTable("classifieds");

		String sqlQuery = this.buildQuery(queryBuilder);

		if (!this.hasResult(sqlQuery)) {
			System.out.println("No Classifieds Found");
			return false;
		}


		ResultSet resultSet = this.getResultSet(sqlQuery);

		while(resultSet.next()) {
			System.out.println("----------------------------------");
			System.out.println("Classified Id\t: " + resultSet.getInt(1));
			System.out.println("Posted by\t: " + resultSet.getInt(2));
			System.out.println("Current Status\t: " + resultSet.getString(3));
			System.out.println("Product Name\t: " + resultSet.getString(4));
			System.out.println("Headline\t: " + resultSet.getString(5));
			System.out.println("Brand Name\t: " + resultSet.getString(6));
			System.out.println("Condition\t: " + getpConditionString(resultSet.getInt(7)));
			System.out.println("Description\t: " + resultSet.getString(8));
			System.out.println("Category\t: " + resultSet.getString(9));
			System.out.println("Product Price\t: " + resultSet.getFloat(10));
			System.out.println("Recurrance\t: " + resultSet.getString(11));
			System.out.println("----------------------------------");
		}
		System.out.println("----------------------------------");

		return true;
	}

	public boolean viewApprovedClassifieds() throws ApplicationException, SQLException, ClassNotFoundException{
		String[] columns = {"classifiedId", "userId", "productName", 
				"headLine", "brand", "pCondition", 
				"pDescription", "pCategory", "price", "recurringUnit"};

		QueryBuilder queryBuilder = this.getSelectInstance()
				.selectColumns(columns)
				.onTable("classifieds")
				.whereEq("cStatus", "Approved");

		String sqlQuery = this.buildQuery(queryBuilder);

		if (!this.hasResult(sqlQuery)) {
			System.out.println("------------------------------------");
			System.out.println("\tNo Classifieds Found	");
			System.out.println("------------------------------------");
			return false;
		}

		ResultSet resultSet = this.getResultSet(sqlQuery);

		while(resultSet.next()) {
			System.out.println("----------------------------------");
			System.out.println("Classified Id\t: " + resultSet.getInt(1));
			System.out.println("Posted by\t: " + getSellerNameFromUser(resultSet.getInt(2)));
			System.out.println("Product Name\t: " + resultSet.getString(3));
			System.out.println("Headline\t: " + resultSet.getString(4));
			System.out.println("Brand Name\t: " + resultSet.getString(5));
			System.out.println("Condition\t: " + getpConditionString(resultSet.getInt(6)));
			System.out.println("Description\t: " + resultSet.getString(7));
			System.out.println("Category\t: " + resultSet.getString(8));
			System.out.println("Product Price\t: " + resultSet.getFloat(9));
			System.out.println("Recurrance\t: " + resultSet.getString(10));
			System.out.println("----------------------------------");
		}
		System.out.println("----------------------------------");
		return true;
	}

	public boolean viewPendingClassifieds() throws ApplicationException, ClassNotFoundException, SQLException{
		String[] columns = {"classifiedId", "userId", "cStatus", 
				"productName", "headLine", "brand", 
				"pCondition", "pDescription", "pCategory", "price", "recurringUnit"};

		QueryBuilder queryBuilder = this.getSelectInstance()
				.selectColumns(columns)
				.onTable("classifieds")
				.whereEq("cStatus", "Pending Approval");

		String sqlQuery = this.buildQuery(queryBuilder);

		if (!this.hasResult(sqlQuery)) {
			System.out.println("No Classifieds Found");
			return false;
		}


		ResultSet resultSet = this.getResultSet(sqlQuery);

		while(resultSet.next()) {
			System.out.println("----------------------------------");
			System.out.println("Classified Id\t: " + resultSet.getInt(1));
			System.out.println("Posted by\t: " + resultSet.getInt(2));
			System.out.println("Current Status\t: " + resultSet.getString(3));
			System.out.println("Product Name\t: " + resultSet.getString(4));
			System.out.println("Headline\t: " + resultSet.getString(5));
			System.out.println("Brand Name\t: " + resultSet.getString(6));
			System.out.println("Condition\t: " + getpConditionString(resultSet.getInt(7)));
			System.out.println("Description\t: " + resultSet.getString(8));
			System.out.println("Category\t: " + resultSet.getString(9));
			System.out.println("Product Price\t: " + resultSet.getFloat(10));
			System.out.println("Recurrance\t: " + resultSet.getString(11));
			System.out.println("----------------------------------");
		}
		System.out.println("----------------------------------");
		return true;

	}

	public boolean isClassifiedApproved(int classifiedId) throws ApplicationException {
		QueryBuilder queryBuilder = this.getSelectInstance()
				.selectColumns("classifiedId")
				.onTable("classifieds")
				.whereEq("classifiedId", classifiedId)
				.whereEq("cStatus", "Approved");
		String sqlQuery = this.buildQuery(queryBuilder);

		return this.hasResult(sqlQuery);
	}

	public int getSellerId(int classifiedId) throws ClassNotFoundException, SQLException, ApplicationException {	

		QueryBuilder queryBuilder = this.getSelectInstance()
				.selectColumns("userId")
				.onTable("classifieds")
				.whereEq("classifiedId", classifiedId);

		String sqlQuery = this.buildQuery(queryBuilder);

		return this.getQueryNumber(sqlQuery);
	}

	public String getSellerNameFromUser(int userId) throws ClassNotFoundException, SQLException, ApplicationException {	

		QueryBuilder queryBuilder = this.getSelectInstance()
				.selectColumns("name")
				.onTable("user")
				.whereEq("userId", userId);

		String sqlQuery = this.buildQuery(queryBuilder);

		return this.getQueryString(sqlQuery);
	}


	public float getPrice(int classifiedId) throws ClassNotFoundException, SQLException, ApplicationException {	

		QueryBuilder queryBuilder = this.getSelectInstance()
				.selectColumns("price")
				.onTable("classifieds")
				.whereEq("classifiedId", classifiedId);

		String sqlQuery = this.buildQuery(queryBuilder);

		return this.getQueryNumberFloat(sqlQuery);
	}


}
