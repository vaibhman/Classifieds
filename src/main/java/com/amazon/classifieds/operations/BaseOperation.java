package com.amazon.classifieds.operations;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.amazon.classifieds.customExceptions.ApplicationException;
import com.amazon.classifieds.customExceptions.UserException;
import com.amazon.classifieds.dbtools.Validator;
import com.amazon.classifieds.managers.UserManager;

/**
 * The class BaseOperation is a parent class for multiple Operation Classes in operations package.
 * It works as a gateway between the operations package/Upper Layer/User and the managers
 * package/Middle Layer.
 * It provides an upper-layer exception enclosure around common functions being used by the child
 * classes.
 * The child classes interact with the manager layer and user via these functions, while any
 * exceptions occurring due to user behaviour are thrown.
 **/

public class BaseOperation {

	private final static int MAX_PASSWORD_CONFIRM_TRIES = 3;
	private final static int MAX_PASSWORD_TRIES = 3;
	private final static int MAX_EXISTING_PASSWORD_TRIES = 3;

	protected String getName() throws UserException {
		Scanner sc = OperationFactory.getScannerInstance();

		String name;

		try {
			name = sc.next();
		} catch (InputMismatchException e) {
			throw new UserException("Entered Name is Invalid");
		}

		if (!Validator.isValidNameLength(name)) {
			throw new UserException("Name should be below 50 characters ");
		}

		if (!Validator.isAlphabeticWithSpaceAndDots(name)) {
			throw new UserException(" Name can only contain alphabets, spaces and dots in " +
					"appropriate manner\n");
		}
		return name;
	}

	protected String getEmail() throws UserException {
		Scanner sc = OperationFactory.getScannerInstance();

		String emailAddress;

		try {
			emailAddress = sc.next();
		} catch (InputMismatchException e) {
			throw new UserException("Entered e-mail address is Invalid ");
		}

		if (!Validator.isValidEmail(emailAddress)) {
			throw new UserException("Email Address not Valid.\n Format should be : abcd@amazon.com," +
					"xyz@gmail.com,etc. ");
		}
		return emailAddress;
	}

	protected String getContactNo() throws UserException {
		Scanner sc = OperationFactory.getScannerInstance();

		String contactNo;

		try {
			contactNo = sc.next();
		} catch (InputMismatchException e) {
			throw new UserException("Entered Phone Number is Invalid");
		}

		if (!Validator.isValidPhoneNoLength(contactNo)) {
			throw new UserException("Entered Phone Number is not 10 or 12 digits long\n" +
					"Phone Number can be of 10 digits without country code or, 12 digits with country code\n");
		}

		if (!Validator.isNumeric(contactNo)) {
			throw new UserException("Entered Phone number should only contain digits");
		}

		long contactAsLong = Long.parseLong(contactNo);

		if (!Validator.isPositive(contactAsLong)) {
			throw new UserException("Entered Phone number should not be negative");
		}

		return contactNo;
	}

	protected String getPassword() throws UserException {
		Scanner sc = OperationFactory.getScannerInstance();

		String password;
		int passwordTries = 1;

		try {
			password = sc.next();
		} catch (InputMismatchException e) {
			throw new UserException("Entered Password is Invalid ");
		}

		while (passwordTries < MAX_PASSWORD_TRIES) {
			if (!Validator.isValidPassword(password)) {
				++passwordTries;
				System.out.println("Please Enter a Valid password [Only 3 tries Allowed]: \n" +
						" 1. A password must have at least eight characters.\n" +
						" 2. A password consists of only letters and digits.\n" +
						" 3. A password must contain at least two digits \n");
				try {
					password = sc.next();
				} catch (InputMismatchException e) {
					throw new UserException("Entered Password is Invalid ");
				}
			} else {
				return password;
			}
		}
		if (!Validator.isValidPassword(password)) {
			throw new UserException("Password tries Exhausted");
		}
		return password;
	}

	// PARAMs
	protected String getConfirmedPassword(String password) throws UserException {
		Scanner sc = OperationFactory.getScannerInstance();

		String confirmedPassword;
		int passwordConfirmTries = 1;

		try {
			confirmedPassword = sc.next();
		} catch (InputMismatchException e) {
			throw new UserException("Entered Password is Invalid ");
		}

		while (passwordConfirmTries <= MAX_PASSWORD_CONFIRM_TRIES) {
			if (!Validator.arePasswordsMatching(password, confirmedPassword)) {
				++passwordConfirmTries;
				System.out.println("Please Enter a password which matches previous password value " +
						"[Only 3 tries Allowed]: \n");
				try {
					confirmedPassword = sc.next();
				} catch (InputMismatchException e) {
					throw new UserException("Entered Password is Invalid ");
				}
			} else {
				return confirmedPassword;
			}
		}
		if (!Validator.arePasswordsMatching(password, confirmedPassword)) {
			throw new UserException("Both Passwords do not match. Password tries Exhausted");
		}
		return confirmedPassword;
	}

	protected String getExistingPassword(int userId) throws UserException, ApplicationException {
		Scanner sc = OperationFactory.getScannerInstance();

		String existingPassword;
		int passwordEntryCount = 1;

		try {
			existingPassword = sc.next();
		} catch (InputMismatchException e) {
			throw new UserException("Entered Password is Invalid ");
		}

		while (passwordEntryCount <= MAX_EXISTING_PASSWORD_TRIES) {
			if (!UserManager.getInstance().isValidUserPassword(userId, existingPassword)) {
				++passwordEntryCount;
				System.out.println("Please enter value which matches current password " +
						"[Only 3 tries Allowed]: \n");
				try {
					existingPassword = sc.next();
				} catch (InputMismatchException e) {
					throw new UserException("Entered Password is invalid.");
				}
			} else {
				return existingPassword;
			}
		}
		if (!UserManager.getInstance().isValidUserPassword(userId, existingPassword)) {
			throw new UserException("Entered Password does not match existing value." +
					"Password tries Exhausted");
		}
		return existingPassword;
	}

	protected int getUserId() throws UserException {
		Scanner sc = OperationFactory.getScannerInstance();

		int userId;

		try {
			userId = sc.nextInt();
		} catch (InputMismatchException e) {
			throw new UserException("\n Please enter correct Employee ID. " +
					"\n It is a 9 digit number" +
					"\n You check your Phonetool Or, contact your manager to find further " +
					"information\n");
		}

		if (!Validator.isPositive(userId)) {
			throw new UserException("\n Employee ID cannot be a negative number.");
		}

		if (!Validator.isValidUserIdLength(userId)) {
			throw new UserException("The entered value is not a valid Employee id" +
					"\nIt is a 9 digit number" +
					"\nYou check your Phonetool Or, Contact your manager to find further " +
					"information\n");
		}
		return userId;
	}

	protected boolean arePasswordsMatching(String oldPassword, String newPassword) {
		return Validator.arePasswordsMatching(oldPassword,newPassword);
	}

	protected String getAdminId() throws UserException {
		Scanner sc = OperationFactory.getScannerInstance();

		String adminId;

		try {
			adminId = sc.next();
		} catch (InputMismatchException e) {
			throw new UserException(" Invalid Value Entered.");
		}
		return adminId;
	}

	protected String getAdminPassword() throws UserException {
		Scanner sc = OperationFactory.getScannerInstance();

		String adminPassword;

		try {
			adminPassword = sc.next();
		} catch (InputMismatchException e) {
			throw new UserException(" Invalid Password Entered.");
		}
		return adminPassword;
	}

	protected String getTimeString() throws UserException {
		Scanner sc = OperationFactory.getScannerInstance();

		String timeString;

		try {
			timeString = sc.next();
		} catch (InputMismatchException e) {
			throw new UserException("\n Please enter valid Timing info in 24 hour format ");
		}

		if (!Validator.isValidTimeString(timeString)) {
			throw new UserException("Please enter valid Timing value as per the below format :\n" +
					"1. Should start with two digits from 00 to 23 for Hours.\n" +
					"2. Must be followed by either of the following separators - ':' or '-' or '/' .\n" +
					"3. Should be followed by two digits from 00 to 59 for Minutes.\n");
		}
		return timeString;
	}

	protected String getComment() throws UserException {
		Scanner sc = OperationFactory.getScannerInstance();

		String comment;

		try {
			comment = sc.next();
		} catch (InputMismatchException e) {
			throw new UserException("Entered Comment is Invalid");
		}

		if (Validator.isCommentBlank(comment)) {
			throw new UserException("Blank Comment Detected");
		}

		if (!Validator.isValidComment(comment)) {
			throw new UserException("Please enter comments within a character limit of 100 ");
		}
		return comment;
	}

	protected void printMenuException(UserException e) {
		System.out.println("Returning to previous menu as the below issue has occurred.\n");
		System.out.println(e.getMessage());
	}

}

