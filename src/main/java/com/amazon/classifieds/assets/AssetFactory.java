package com.amazon.classifieds.assets;

import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.managers.IdManager;

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
	

	public Classified getClassifiedInstance(int userId, String cStatus, String productName, 
											String headLine, String brand, int pCondition, String pDescription, float price) throws ApplicationException {
	    int classifiedId = IdManager.getInstance().getNewId("classified");
	    
		Classified classified = new Classified(classifiedId, userId, cStatus, productName, headLine, brand, pCondition, pDescription, price);
		
		return classified;
	}
}

