package com.amazon.classifieds.operations;

import java.util.Scanner;

import com.amazon.classifieds.assets.AssetFactory;
import com.amazon.classifieds.assets.User;
import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.customExceptions.UserException;
import com.amazon.classifieds.managers.UserManager;

/**
 * This class is implements the sign-in/sign-up/account control page functionality for the USER.
 * It is an extension of BaseOperation class.
 *
 * Existing Users can login with their employee id and password
 *  - If login is successful, they are routed to the USER homepage of the application.
 *  - If login is unsuccessful even after 3 tries, they are routed back to the USER Account Control
 *  page.
 *
 *  New users can create a account by entering requested credentials.
 *   - If create account is successful, they can proceed to login.
 *   - If create account is unsuccessful, they are routed back to the USER Account Control page.
 * **/

public class UserLoginOperation extends BaseOperation {

	private final static int maxLoginTries = 5;
	private static int loginTries = 0;

	public boolean showMenu() throws ApplicationException {
		boolean exitCode = false;
		String choice = "";
		Scanner sc = OperationFactory.getScannerInstance();

		while (!exitCode) {
			System.out.println("\nWelcome to the User Login/Sign-up Page");
			System.out.println("\n1. Login " +
					"\n2. Create Account" +
					"\n0. Exit\n");

			choice = sc.next();

			switch (choice) {
			case "1":
				try {
					setLoginDetails();
				} catch (UserException e) {
					System.out.println("Returning to previous menu as the below exception has occurred.");
					System.out.println(e.getMessage());
				}
				break;
			case "2":
				try {
					createAccount();
				} catch (UserException e) {
					System.out.println("Returning to previous menu as the below exception has occurred.");
					System.out.println(e.getMessage());
				}
				break;
			case "0":
				exitCode = true;
				break;

			default:
				System.out.println("Please Enter Valid Option");
			}
		}

		System.out.println("Returning to Main Menu");

		return true;
	}

	// Creates a User Account and sends to login page
	private void createAccount() throws ApplicationException, UserException {
		System.out.println("\nCreate your new User account");
		System.out.println("\nPlease Enter the below details as prompted and Press Enter to confirm entry." +
				"\nPress Enter Twice to return to Previous Menu. ");

		System.out.println("\n Employee ID [9 Digit Number]: \n");
		int userId = this.getUserId();

		boolean userAlreadyExists = UserManager.getInstance().isValidUser(userId);

		if (userAlreadyExists) {
			System.out.println("User Id for " + userId + " already exists\n");
		}
		
		if (!userAlreadyExists) {
			System.out.println("\n Name : \n");
			String name = this.getName();

			System.out.println("\n Email Address : \n");
			String email = this.getEmail();

			System.out.println("\n Contact No.[Only 10 digits or 12 digits with country code] :\n");
			String contactNo = this.getContactNo();

			System.out.println("\n Password : \n" +
					"[Should be of at least 8 characters, contain only letters and digits and " +
					"must contain at least 2 digits]");
			String password = this.getPassword();

			System.out.println("\n Confirm Password : \n" +
					"[Should be the same value as entered before]");
			String confirmedPassword = this.getConfirmedPassword(password);

			float walletBalance=0;

			User user = AssetFactory.getInstance().getUserInstance(userId, name,
					email, contactNo, confirmedPassword, walletBalance);

			UserManager.getInstance().create(user);

			System.out.println("Your Account with User ID : " + user.getUserId() +
					" has been created ! \n");
			System.out.println("Please Login with your User ID and Password below : \n");

			setLoginDetails();
		}
	}

	// If the user and password combination exist, redirect to UserOperations
	private void login(int userId, String password) throws ApplicationException, UserException {
		if(!UserManager.getInstance().isUserActive(userId)) {
			System.out.println("Looks like this account was De-activated by Admin!");
			System.out.println("Please contact Admin to Activate Your Account Again!");
			return;
		}
		
		if (UserManager.getInstance().isValidUserPassword(userId, password)) {
			System.out.println("User Login Successful!");
			OperationFactory.getUserOperationInstance().showMenu(userId); 
		} else {
			System.out.println("\nUnable to load account with entered credentials. " +
					"\nPlease make sure that the entered credentials are correct \n");
			setLoginDetails();
		}
	}

	private boolean setLoginDetails() throws ApplicationException, UserException {
		loginTries += 1;
		System.out.println("\nUser Login \n");
		System.out.println("Enter Employee ID : \n");
		int userId = this.getUserId();

		System.out.println("Enter Password : \n");
		String password = this.getPassword();

		if (loginTries > maxLoginTries) {
			System.out.println("Maximum Login Tries Exceeded! \n Returning to Home.");

			loginTries = 0;
			return false;
		}
		login(userId, password);

		return true;
	}
}
