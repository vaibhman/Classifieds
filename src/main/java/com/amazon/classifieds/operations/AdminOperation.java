package com.amazon.classifieds.operations;

import java.sql.SQLException;

import com.amazon.classifieds.assets.AssetFactory;
import com.amazon.classifieds.assets.Classified;
import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.customExceptions.UserException;
import com.amazon.classifieds.managers.ClassifiedManager;
import com.amazon.classifieds.managers.UserManager;

public class AdminOperation extends BaseOperation{
	
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
								+"\n0. Sign Out \n");
			String choice = OperationFactory.getScannerInstance().next();

			switch (choice) {
			case "1":
				try {
					postClassified();
				} catch (UserException e) {
					e.printStackTrace();
				} catch (ApplicationException e) {
					e.printStackTrace();
				}
				break;
				
			case "2":
				manageClassified();
				break;
				
			case "3":
				manageUsers();
				break;
				
			case "4":
				/*
				System.out.println("\nReport Date Not Available");
				System.out.println("\nPlease try later");*/
				try {
					generateReport();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
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

	private void generateReport() throws ClassNotFoundException, SQLException {
		
		System.out.println("\n------------Report-------------");

		int totalUsers = this.getUsersCount();
		int activeUsers = this.getActiveUsersCount();
		int deActivatedUser = this.getDeActivatedUsersCount();
		int totalClassifieds = this.getClassifiedsCount();
		int pendingClassifieds = this.getPendingClassifiedsCount();
		int approvedClassifieds = this.getApprovedClassifiedsCount();
		int rejectedClassifieds = this.getRejectedClassifiedsCount();
		
		
		System.out.println("Total Number of Users: \t\t" + totalUsers);
		System.out.println("Active Users: \t\t\t" + activeUsers);
		System.out.println("De-activated Users: \t\t" + deActivatedUser);
		System.out.println("Total Number of Classifieds: \t" + totalClassifieds);		
		System.out.println("Pending Classifieds: \t\t" + pendingClassifieds);
		System.out.println("Active Classifieds: \t\t" + approvedClassifieds);
		System.out.println("Rejected Classifieds: \t\t" + rejectedClassifieds);
		System.out.println("\n-----------------------------------------------\n");
		
	}

	private void manageClassified() throws ApplicationException {
		
		boolean exitCode = false;
		while(!exitCode) {
			System.out.println("\n-----------------------------------------------\n");

			System.out.println("\nSelect an Option:");
			System.out.println("\n1. Approve Classified"
					+"\n2. Reject Classified"
					+"\n3. View All Classifieds"
					+"\n4. View Pending Classifieds"
					+"\n5. Remove Classified"
					+"\n0. Return to Previous Menu \n");
			
			String choice = OperationFactory.getScannerInstance().next();

			switch (choice) {
				case "1":
					try {
						changeClassifiedStatus("Approved");
					} catch (ApplicationException e) {
						System.out.println("Something Went Wrong");
						e.printStackTrace();
					} catch (UserException e) {
						System.out.println("Something Went Wrong");
						e.printStackTrace();
					}
					break;
					
				case "2":
					try {
						changeClassifiedStatus("Rejected");
					} catch (ApplicationException e) {
						System.out.println("Something Went Wrong");
						e.printStackTrace();
					} catch (UserException e) {
						System.out.println("Something Went Wrong");
						e.printStackTrace();
					}
					break;
					
				case "3":
					try {
						viewAllClassifieds();
					} catch (ApplicationException e) {
						System.out.println("Something Went Wrong");
						e.printStackTrace();
					}
					break;
				
				case "4":
					try {
						viewPendingClassifieds();
					} catch (ApplicationException e) {
						System.out.println("Something Went Wrong");
						e.printStackTrace();
					}
					break;
					
				case "5":
				try {
					removeClassified();
				} catch (ApplicationException | UserException e) {
					System.out.println("Something Went Wrong");
					e.printStackTrace();
				}
					break;
				case "0":
					exitCode=true;
					break;
				default: 
					System.out.println("Please Enter Valid Option");
			}	
		}
		System.out.println("Returning to Previous Menu");
		
	}
	
	private boolean removeClassified() throws ApplicationException, UserException {
		System.out.println("For your reference,");
		viewAllClassifieds();
	    System.out.println("\nPlease enter Classified Id to remove : \n");
	    int classifiedIdToRemove = this.getClassifiedId();

	    if (!ClassifiedManager
	            .getInstance()
	            .isPresent("classifieds", "classifiedId", classifiedIdToRemove)) {
	      System.out.println("Invalid Classified ID Entered ===>" + classifiedIdToRemove + "<=== " +
	              "\nReturning to Previous Menu\n");

	      return false;
	    }

	    ClassifiedManager.getInstance().delete(classifiedIdToRemove);

	    System.out.println("\nUpdated Classifieds list : \n");
		viewAllClassifieds();

	    System.out.println("\nclassified with ID : " + classifiedIdToRemove + " has been removed!\n");
		System.out.println("\n-----------------------------------------------\n");

	    return true;
	}

	private boolean viewPendingClassifieds() throws ApplicationException{
		ClassifiedManager
		.getInstance()
		.viewPendingClassifieds();
		return true;
	}
	
	private boolean viewAllClassifieds() throws ApplicationException{
		ClassifiedManager
			.getInstance()
			.viewAllClassifieds();
		return true;
	}

	private boolean changeClassifiedStatus(String newSatus) throws ApplicationException, UserException {
		System.out.println("____________________________________");

		System.out.println("Enter Id of classified");

		int classifiedId = this.getClassifiedId();
		
		if (!ClassifiedManager
	            .getInstance()
	            .isPresent("classifieds", "classifiedId", classifiedId)) {
	      System.out.println("Classified Not Found");
	      return false;
		}
		
		ClassifiedManager
	            .getInstance()
	            .update(classifiedId, "cStatus", newSatus);
		
		System.out.println("____________________________________");

		return true;
	}

	private boolean postClassified() throws UserException, ApplicationException {
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

		String cStatus="Approved";
		
		int userId= 111111111;

		Classified newClassified = AssetFactory.getInstance().getClassifiedInstance(userId, cStatus, productName, headLine, brand, pCondition, pDescription, price);
		
		ClassifiedManager.getInstance().create(newClassified);

		System.out.println("Classified Created Successfully with id: "+newClassified.getClassifiedId());
		System.out.println("The classified is already Approved");
		System.out.println("\n-----------------------------------------------\n");


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
		System.out.println("____________________________________");

	}

	private boolean activateUser() throws ApplicationException{

		System.out.println("Enter User Id (9 digit): ");
		int userId = OperationFactory.getScannerInstance().nextInt();
		UserManager
		.getInstance()
		.update(userId, "isActive", "true");
		
		System.out.println("User Activated for userId: " + userId);
		System.out.println("\n-----------------------------------------------\n");

		return true;
	}
	
	private boolean deActivateUser() throws ApplicationException{

		System.out.println("Enter User Id (9 digit): ");
		int userId = OperationFactory.getScannerInstance().nextInt();
		UserManager
		.getInstance()
		.update(userId, "isActive", "false");
		
		System.out.println("User De-activated for userId: " + userId);
		System.out.println("\n-----------------------------------------------\n");

		return true;
	}
}
