package com.amazon.classifieds.operations;

import com.amazon.classifieds.customExceptions.ApplicationException;

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
				System.out.println("\nSelect an Option:");
				System.out.println("\n1. Activate User"
								+"\n2. De-activate User"
								+"\n0. Exit \n");
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
		
		return true;
	}
}
