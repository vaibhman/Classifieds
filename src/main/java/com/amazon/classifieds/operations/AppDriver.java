package com.amazon.classifieds.operations;

import java.util.concurrent.TimeUnit;

import com.amazon.classifieds.customExceptions.*;

@SuppressWarnings("unused")
public class AppDriver {
	public void initiate()  {
		boolean exitCode = false;

		while (!exitCode) {

			loadScreen();
			System.out.println("\nEnter your User Type :");
			System.out.println("\n1. Admin \n2. User\n0. Exit \n");

			String choice = OperationFactory.getScannerInstance().next();

			switch (choice) {
			case "1":
				System.out.println("Welcome Administrator!\n");

				try {
					OperationFactory.getAdminLoginInstance().showMenu();
				} catch (ApplicationException e) {
					e.printStackTrace();
				}
				break;

			case "2":
				System.out.println("Welcome User!\n");

				try {
					OperationFactory.getUserLoginInstance().showMenu();
				} catch (ApplicationException e) {
					System.out.println(e.getMessage());
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
		loadScreen();
	}

	private void loadScreen(){
		System.out.println("_______________________________________________________________");
		System.out.println("---------------------------------------------------------------");

		int del = 0;
		while(del !=6){
			try {
				TimeUnit.MILLISECONDS.sleep(1000/(del+1*2));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int k = del;
			while(k!=0){
				System.out.print("_~`~");
				k--;
			}
			del++;
		}
		System.out.print("_~`~");
		System.out.println("\n---------------------------------------------------------------");
	}
}