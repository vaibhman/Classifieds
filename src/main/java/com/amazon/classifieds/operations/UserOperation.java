package com.amazon.classifieds.operations;


public class UserOperation {
	void showMenu(int userid){
		System.out.println("--------------------------------------");
		System.out.println("-------Welcome User: "+userid+"------");
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
}
