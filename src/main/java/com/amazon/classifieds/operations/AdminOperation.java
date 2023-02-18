package com.amazon.classifieds.operations;

import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.customExceptions.UserException;
import com.amazon.classifieds.managers.UserManager;

public class AdminOperation {
	public boolean showMenu() throws ApplicationException{
		System.out.println("--------------------------------------");
		System.out.println("--------Welcome Administrator--------");
		System.out.println("--------------------------------------");
		
		boolean exitCode = false;
		while(!exitCode) {
			System.out.println("\nSelect an Option:");
			System.out.println("\n1. Add Classified"
								+"\n2. Manage Classifieds"
								+"\n3. Manage Users"
								+"\n4. Generate report"
								+"\n0. Exit \n");
			String choice = OperationFactory.getScannerInstance().next();

			switch (choice) {
			case "1":
				System.out.println("Add Classified Function is not added yet");
				break;
			case "2":
				System.out.println("\nSelect an Option:");
				System.out.println("\n1. Approve Classifieds"
								+"\n2. Reject Classifieds"
								+"\n3. Change Type of classified"
								+"\n0. Exit \n");
				break;
			case "3":
				manageUsers();

				break;
			case "4":
				System.out.println("\nReport Date Not Available");
				System.out.println("\nPlease try later");
				break;
			case "0":
				exitCode=true;
				break;
			default:
				System.out.println("Please Enter Valid Option");
			}
		}
		System.out.println("Returning to Main Menu");
		return true;
	}
	
	
	private void manageUsers() {
		boolean excode=false;
		while(!excode) {
			System.out.println("\nSelect an Option:");
			System.out.println("\n1. Activate User"
							+"\n2. De-activate User"
							+"\n0. Return to Admin Main Menu \n");
			String choice = OperationFactory.getScannerInstance().next();
			switch (choice) {
			case "1":
				try {
					activateUser();
				}catch(Exception e) {
					System.out.println("Something went wrong: "+e);
				}
				break;
			case "2":
				try {
					deActivateUser();
				}catch(Exception e) {
					System.out.println("Something went wrong: "+e);
				}
				break;
			case "0":
				excode=true;
				break;
			default:
				System.out.println("Please Enter Valid Option");
			}
		}
		System.out.println("Returning to Admin Menu");
	}

	private boolean activateUser() throws ApplicationException{
		System.out.println("Activate user code");
		System.out.println("Enter User Id (9 digit): ");
		int userId = OperationFactory.getScannerInstance().nextInt();
		UserManager
		.getInstance()
		.update(userId, "isActive", "true");
		
		System.out.println("User Activated for userId: " + userId);
		
		return true;
	}
	
	private boolean deActivateUser() throws ApplicationException{
		System.out.println("Activate user code");
		System.out.println("Enter User Id (9 digit): ");
		int userId = OperationFactory.getScannerInstance().nextInt();
		UserManager
		.getInstance()
		.update(userId, "isActive", "false");
		
		System.out.println("User De-activated for userId: " + userId);
		
		return true;
	}
}
