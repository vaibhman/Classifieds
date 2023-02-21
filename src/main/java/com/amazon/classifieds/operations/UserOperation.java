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
							+ "\n5. Buy Product"
							+ "\n0. Sign Out \n");

			String choice = OperationFactory.getScannerInstance().next();

			switch (choice) {
			case "1":
				try {
					manageProfile(userId);
				} catch (ApplicationException e) {
					System.out.println("Something Went Wrong...");
					e.printStackTrace();
				}
				break;

			case "2":
				try {
					postClassified(userId);
				} catch (UserException e) {
					System.out.println("Something Went Wrong...");
					e.printStackTrace();
				} catch (ApplicationException e) {
					System.out.println("Something Went Wrong...");
					e.printStackTrace();
				}
				break;
				
			case "3":
				try {
					viewClassifieds();
				} catch (ApplicationException e) {
					System.out.println("Something Went Wrong...");
					e.printStackTrace();
				}
				break;
				
			case "4":
				try {
					viewWallet(userId);
				} catch (ClassNotFoundException | SQLException | ApplicationException e) {
					System.out.println("Something Went Wrong...");
					e.printStackTrace();
				}
				break;
				
			case "5":
		
				try {
					buyProduct(userId);
				} catch (ClassNotFoundException | ApplicationException | UserException | SQLException e) {
					System.out.println("Something Went Wrong...");
					e.printStackTrace();
				}

				
			case "0":
				exitCode = true;
				break;

			default:
				System.out.println("Please Enter Valid Option");
			}
		}

		System.out.println("Thank You For Using our Employee Internal Classifieds Application\n");
	}
	

	private boolean buyProduct(int userId) throws ApplicationException, UserException, ClassNotFoundException, SQLException {
		
		System.out.println("For your referrence...");
		
		viewClassifieds();
		
		System.out.println("Enter Classified ID to purchase...");
		int classifiedId= this.getClassifiedId();
		
		if(!ClassifiedManager.getInstance().isClassifiedApproved(classifiedId)) {
			throw new UserException(" Invalid Classified ID Entered.");
		}
		
		float productPrice = ClassifiedManager.getInstance().getPrice(classifiedId);
		
		float userBalance= UserManager.getInstance().getWalletBalance(userId);
		
		if(userBalance < productPrice) {
			System.out.println("Not Enough Wallet Balance ");
			System.out.println("Please add money to your wallet");
			return false;
		}
	
		int sellerId = ClassifiedManager.getInstance().getSellerId(classifiedId);
		
		float sellerBalance = UserManager.getInstance().getWalletBalance(sellerId);
		
		UserManager.getInstance().setWalletBalance(userId, sellerBalance-productPrice);
		
		UserManager.getInstance().setWalletBalance(sellerId, userBalance+productPrice);
		
		ClassifiedManager.getInstance().update(classifiedId, "cStatus", "Sold");
		
		System.out.println("Product Purchased Successfully!!!");
		System.out.println("---------------------------------------------");
		return true;
	}


	private void viewWallet(int userId) throws ClassNotFoundException, SQLException, ApplicationException {

		boolean exitCode = false;

		while (!exitCode) {
			float walletBalance= UserManager.getInstance().getWalletBalance(userId);
			System.out.println("---------------------------------------------");
			System.out.println("Your Wallet Balance is: "+ walletBalance);
			System.out.println("1. Add Money to your Wallet \n"
							+"2. Withdraw Money from your Wallet\n"
							+"0. Return to Previous Menu");

			String choice = OperationFactory.getScannerInstance().next();

			switch (choice) {
				case "1":
					try {
						UserManager.getInstance().addMoneytoWallet(userId, walletBalance);						
						System.out.println("\nMoney Added Successfully!");
						System.out.println("\n---------------------------------------------\n");
					} catch (ClassNotFoundException | SQLException | ApplicationException | UserException e) {
						System.out.println("Something Went Wrong...");
						e.printStackTrace();
					}
					break;
					
				case "2":
					try {
						UserManager.getInstance().withdrawMoneyFromWallet(userId, walletBalance);
						System.out.println("\nMoney Withdrawed Successfully!");
						System.out.println("\n---------------------------------------------\n");
					} catch (ClassNotFoundException | SQLException | ApplicationException | UserException e) {
						System.out.println("Something Went Wrong...");
						e.printStackTrace();
					}

					break;
				
				case "3":
					exitCode=true;
					break;
				default:
					System.out.println("Please select valid option.");
			}
		}
		System.out.println("---------------------------------------------");
	}


	private boolean viewClassifieds() throws ApplicationException{
		ClassifiedManager
			.getInstance()
			.viewApprovedClassifieds();
		System.out.println("---------------------------------------------");
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
		
		System.out.println("\nSelect Product Condition: ");
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
		System.out.println("---------------------------------------------");
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
		System.out.println("---------------------------------------------");
		return true;
	}

	private boolean updateContactNo(int userId) throws UserException, ApplicationException {
		System.out.println("\nContact Number[Only 10 digits or 12 digits with country code] :");
		String contactNumber = this.getContactNo();

		UserManager
		.getInstance()
		.update(userId, "contactno", contactNumber);

		System.out.println("Your Contact Number has been Updated to : " + contactNumber);
		System.out.println("---------------------------------------------");
		return true;
	}

	private boolean updateEmail(int userId) throws UserException, ApplicationException {
		System.out.println("E-mail Address :\n");
		String email = this.getEmail();

		UserManager
		.getInstance()
		.update(userId, "email", email);

		System.out.println("Your contact E-mail address has been Updated to : " + email);
		System.out.println("---------------------------------------------");
		return true;
	}

	private void updateName(int userId) throws UserException, ApplicationException {
		System.out.println("Name :\n");
		String name = this.getName();


		UserManager
		.getInstance()
		.update(userId, "name", name);

		System.out.println("You Name has been updated to : " + name);
		System.out.println("---------------------------------------------");
	}
	
}
