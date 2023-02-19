package com.amazon.classifieds.managers;

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
				  .insertValue("price", classified.getPrice());

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
	  
	  public boolean viewAllClassifieds() throws ApplicationException {
			String[] columns = {"classifiedId", "userId", "cStatus", "productName", "headLine", "brand", "pCondition", "pDescription","price"};

			QueryBuilder queryBuilder = this.getSelectInstance()
					.selectColumns(columns)
					.onTable("classifieds");

			String sqlQuery = this.buildQuery(queryBuilder);
			
			if (!this.hasResult(sqlQuery)) {
				System.out.println("No Classifieds Found");
				return false;
			}

			String[] headers = {"CLASSIFIED ID", "POSTED BY", "STATUS", "PRODUCT NAME", "HEADLINE", "BRAND", "CONDITION", "DESCRIPTION", "PRICE"};
			this.executeQuery(sqlQuery, headers);

			return true;
	  }

	  public boolean viewPendingClassifieds() throws ApplicationException{
			String[] columns = {"classifiedId", "userId", "cStatus", "productName", "headLine", "brand", "pCondition", "pDescription","price"};

			QueryBuilder queryBuilder = this.getSelectInstance()
					.selectColumns(columns)
					.onTable("classifieds")
					.whereEq("cStatus", "Pending Approval");

			String sqlQuery = this.buildQuery(queryBuilder);
			
			if (!this.hasResult(sqlQuery)) {
				System.out.println("No Classifieds Found");
				return false;
			}

			String[] headers = {"CLASSIFIED ID", "POSTED BY", "STATUS", "PRODUCT NAME", "HEADLINE", "BRAND", "CONDITION", "DESCRIPTION", "PRICE"};
			this.executeQuery(sqlQuery, headers);

			return true;
		  
	  }
	  
	  public boolean viewApprovedClassifieds() throws ApplicationException{
			String[] columns = {"classifiedId", "userId", "productName", "headLine", "brand", "pCondition", "pDescription","price"};

			QueryBuilder queryBuilder = this.getSelectInstance()
					.selectColumns(columns)
					.onTable("classifieds")
					.whereEq("cStatus", "Approved");

			String sqlQuery = this.buildQuery(queryBuilder);
			
			if (!this.hasResult(sqlQuery)) {
				System.out.println("No Classifieds Found");
				return false;
			}

			String[] headers = {"CLASSIFIED ID", "POSTED BY", "PRODUCT NAME", "HEADLINE", "BRAND", "CONDITION", "DESCRIPTION", "PRICE"};
			this.executeQuery(sqlQuery, headers);

			return true;
		  
	  }
	  
	   

}
