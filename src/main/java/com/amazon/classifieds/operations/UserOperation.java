package com.amazon.classifieds.operations;

import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.customExceptions.UserException;
import com.amazon.classifieds.managers.UserManager;

@SuppressWarnings("unused")
public class UserOperation extends BaseOperation{
	void showMenu(int userId) throws ApplicationException {
		System.out.println("--------------------------------------");
		System.out.println("-------Welcome User: "+userId+"------");
		System.out.println("--------------------------------------");
		
		boolean exitCode = false;

		while (!exitCode) {

			System.out.println("\nSelect an Option :");
			System.out.println("\n1. Manage your Profile"
							+ "\n2. Post a Classified"
							+ "\n3. View all Classified"
							+ "\n0. Exit \n");

			String choice = OperationFactory.getScannerInstance().next();

			switch (choice) {
			case "1":
				manageProfile(userId);
				break;

			case "2":
				System.out.println("\nPost Classified not implemented yet");
				break;
			case "3":
				System.out.println("\nView all Classifieds not implemented yet");
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
		.update(userId, "fname", name);

		System.out.println("You Name has been updated to : " + name);
	}

}
