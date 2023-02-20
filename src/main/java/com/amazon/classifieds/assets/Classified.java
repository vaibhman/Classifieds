package com.amazon.classifieds.assets;

/**
 * The class Classified acts as a POJO which holds entity details for a Classified.
 * It supports retrieval and storage of Classified details. It is used in Upper Layer-to-Middle
 *  Layer/Operations package-to-Managers package data transmission.
 * About attributes :
 * classifiedId - Unique ID
 * userId - ID of the user who posted this classified
 * cStatus - Current status of classified(Approved, Rejected, Sold)
 * productName - Name of product
 * headLine - headLine of classified
 * brand - brand name of product/ manufacturer name
 * pCondition - Condition of product(1.Brand New, 2.Lightly Used, 3.Moderately Used
 * 				,4. Heavily Used, 5.Damaged/Dented, 6. Not Working)
 * pDescription	- Description of product
 * price -  price of product 
 **/

public class Classified {
	private int classifiedId; 
	private int userId; 
	private String cStatus; 
	private String productName; 
	private String headLine; 
	private String brand; 
	private int pCondition; 
	private String pDescription; 
	private float price;
	
	public Classified(int classifiedId, int userId, String cStatus, String productName, String headLine, String brand,
			int pCondition, String pDescription, float price) {
		super();
		this.classifiedId = classifiedId;
		this.userId = userId;
		this.cStatus = cStatus;
		this.productName = productName;
		this.headLine = headLine;
		this.brand = brand;
		this.pCondition = pCondition;
		this.pDescription = pDescription;
		this.price = price;
	}
	
	public int getClassifiedId() {
		return classifiedId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public String getcStatus() {
		return cStatus;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public String getHeadLine() {
		return headLine;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public int getpCondition() {
		return pCondition;
	}
	
	public String getpConditionString(int pCondition) {
		String[] productConditions = {"", "Brand New", "Lightly Used", "Moderately Used"
				,"Heavily Used","Damaged/Dented","Not Working"};
			
		return productConditions[pCondition];
	}
	
	public String getpDescription() {
		return pDescription;
	}
	
	public float getPrice() {
		return price;
	}
}
