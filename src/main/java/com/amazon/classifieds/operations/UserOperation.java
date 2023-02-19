package com.amazon.classifieds.operations;

import java.sql.SQLException;

import com.amazon.classifieds.assets.AssetFactory;
import com.amazon.classifieds.assets.Classified;
import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.customExceptions.UserException;
import com.amazon.classifieds.managers.ClassifiedManager;
import com.amazon.classifieds.managers.UserManager;
import com.amazon.classifieds.queryHelper.QueryBuilder;

@SuppressWarnings("unused")
public class UserOperation extends BaseOperation{
	
	void showMenu(int userId) throws ApplicationException {
		
		
		System.out.println("--------------------------------------");
		System.out.println("------------- Welcome User ------------");
		System.out.println("--------------------------------------");
		
		boolean exitCode = false;

		while (!exitCode) {

			System.out.println("\nSelect an Option :");
			System.out.println("\n1. Manage your Profile"
							+ "\n2. Post a Classified"
							+ "\n3. View Classifieds" //Only approved classified will be visible
							+ "\n4. Go to Wallet"
							+ "\n0. Exit \n");

			String choice = OperationFactory.getScannerInstance().next();

			switch (choice) {
			case "1":
				try {
					manageProfile(userId);
				} catch (ApplicationException e) {
					e.printStackTrace();
				}
				break;

			case "2":
				try {
					postClassified(userId);
				} catch (UserException e) {
					e.printStackTrace();
				} catch (ApplicationException e) {
					e.printStackTrace();
				}
				break;
				
			case "3":
				try {
					viewClassifieds();
				} catch (ApplicationException e) {
					e.printStackTrace();
				}
				break;
				
			case "4":
				try {
					viewWallet(userId);
				} catch (ClassNotFoundException | SQLException | ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "0":
				exitCode = true;
				break;

			default:
				System.out.println("Please Enter Valid Option");
			}
		}

		System.out.println("Thank You For Using our Employee Internal Classifieds Application\n");
	}
	

	private void viewWallet(int userId) throws ClassNotFoundException, SQLException, ApplicationException {
		
		float walletBalance= UserManager.getInstance().getWalletBalance(userId);
		System.out.println("Your Wallet Balance is: "+ walletBalance);
		System.out.println("1. Add Money to your Wallet");
		System.out.println("2. Withdraw Money from your Wallet");
	}


	private boolean viewClassifieds() throws ApplicationException{
		ClassifiedManager
			.getInstance()
			.viewApprovedClassifieds();
		return true;
	}


	private boolean postClassified(int userId) throws UserException, ApplicationException{
		System.out.println("\n Please Enter Classified Details Below :");
		
		System.out.println("\n Product Name: ");
		String productName = this.getProductName();
		
		System.out.println("\n Product HeadLine: ");
		String headLine = this.getHeadLine();
		
		System.out.println("\n Brand of Product: ");
		String brand = this.getBrand();
		
		System.out.println("Select Product Condition: ");
		int pCondition = this.getpCondition();
		
		System.out.println("\n Description of Product: ");
		String pDescription = this.getpDescription();
		
		System.out.println("\n Enter the price of Product: ");
		float price = this.getPrice();
		
		String cStatus="Pending Approval";
		
		Classified newClassified = AssetFactory.getInstance().getClassifiedInstance(userId, cStatus, productName, headLine, brand, pCondition, pDescription, price);
		
	    ClassifiedManager.getInstance().create(newClassified);
	    
	    System.out.println("Classified Created Successfully with id: "+newClassified.getUserId());
	    System.out.println("The classified is sent to Admin Approval");
		
		return true;
	}
	

	private boolean manageProfile(int userId) throws ApplicationException {
		boolean exCode = false;
		String choice = "";

		while (!exCode) {
			System.out.println("\nSelect Field to Update\n");

			System.out.println("1. Name \n" +
					"2. E-mail address \n" +
					"3. Contact Number \n" +
					"4. Password \n" +
					"0. Return to User Menu");

			choice = OperationFactory.getScannerInstance().next();

			if (!choice.equalsIgnoreCase("0")) {
				System.out.println("Please enter values below : ");
			}

			switch (choice) {
			case "1":
				try {
					updateName(userId);
				} catch (UserException e) {
					this.printMenuException(e);
				}
				break;

			case "2":
				try {
					updateEmail(userId);
				} catch (UserException e) {
					this.printMenuException(e);
				}
				break;

			case "3":
				try {
					updateContactNo(userId);
				} catch (UserException e) {
					this.printMenuException(e);
				}
				break;

			case "4":
				try {
					updatePassword(userId);
				} catch (UserException e) {
					this.printMenuException(e);
				}
				break;

			case "0":
				exCode = true;
				break;

			default:
				System.out.println("Please Enter Valid Option\n");
			}

		}
		System.out.println("Returning to User Menu");

		return true;
	}

	private boolean updatePassword(int userId) throws UserException, ApplicationException {
		System.out.println("Existing Password :\n");
		String oldPassword = this.getExistingPassword(userId);

		System.out.println("Enter New Password :\n" +
				"[Should be of at least 8 characters, contain only letters and digits and " +
				"must contain at least 2 digits]");
		String newPassword = this.getPassword();

		if (this.arePasswordsMatching(oldPassword, newPassword)) {
			System.out.println("New Password is the same as Current password.");
			return false;
		}

		System.out.println("Enter New Password Again :\n");
		String newConfirmedPassword = this.getConfirmedPassword(newPassword);

		UserManager.getInstance().update(userId, "password", newConfirmedPassword);

		System.out.println("Your password has been updated. Hereafter, you must log-in using your new " +
				"password\n");

		return true;
	}

	private boolean updateContactNo(int userId) throws UserException, ApplicationException {
		System.out.println("\nContact Number[Only 10 digits or 12 digits with country code] :");
		String contactNumber = this.getContactNo();

		UserManager
		.getInstance()
		.update(userId, "contactno", contactNumber);

		System.out.println("Your Contact Number has been Updated to : " + contactNumber);

		return true;
	}

	private boolean updateEmail(int userId) throws UserException, ApplicationException {
		System.out.println("E-mail Address :\n");
		String email = this.getEmail();

		UserManager
		.getInstance()
		.update(userId, "email", email);

		System.out.println("Your contact E-mail address has been Updated to : " + email);

		return true;
	}

	private void updateName(int userId) throws UserException, ApplicationException {
		System.out.println("Name :\n");
		String name = this.getName();


		UserManager
		.getInstance()
		.update(userId, "name", name);

		System.out.println("You Name has been updated to : " + name);
	}
	
}
