package com.amazon.classifieds.operations;

import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.customExceptions.UserException;

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
				System.out.println("\nManage Profile not implemented yet");
				//updateProfile(userId);
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
	
/*
	private boolean updateProfile(int userId) throws ApplicationException {
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
	}*/
}
