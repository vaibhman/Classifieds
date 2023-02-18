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
}

