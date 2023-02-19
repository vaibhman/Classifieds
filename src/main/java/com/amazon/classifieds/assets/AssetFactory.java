package com.amazon.classifieds.assets;

public class AssetFactory {

	private static AssetFactory assetFactory;

	public static AssetFactory getInstance() {
		if (assetFactory == null) {
			assetFactory = new AssetFactory();
		}
		return assetFactory;
	}
	
	public User getUserInstance(int userid, String name,  String email, 
								String contactNo, String password, float walletBalance) {

		return new User(userid, name,  email, contactNo, password, walletBalance);
	}
	

	public Classified getClassifiedInstance( int classifiedId, int userId, String cStatus, String productName, 
											String headLine, String brand, int pCondition, String pDescription, float price) {
		
		return new Classified(classifiedId, userId, cStatus, productName, headLine, brand, pCondition, pDescription, price);
	}
}

