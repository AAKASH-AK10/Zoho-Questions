package com.bankingApplication;

import java.util.Scanner;

import com.bankingApplication.Customer.CustomerFileHandler;
import com.bankingApplication.Customer.CustomerHandler;

public class BankingApplication {

	public static void main(String[] args) 
	{
		CustomerFileHandler.getInstance().initialize();
		printMenu();
		CustomerFileHandler.getInstance().updateCustomerDetails();
	}
	
	public static void printMenu()
	{
		CustomerHandler customerHandler = new CustomerHandler();
		System.out.print("Please select an Option"+ "\n1. CreateUser"+ "\n2. Deposit"+ "\n3. Withdraw"+ "\n4. FundTransfer\n");
		
		Scanner scanner= new Scanner(System.in);
		int key = scanner.nextInt();
		switch (key) 
		{
			case 1: 
				customerHandler.addCustomer();
				break;
			case 2:
				deposit();
				break;
			case 3:
				withdrawal();
				break;
			case 4: 
				//Authentication
				transfer();
				break;
			default:
				System.out.print("Invalid Input...!");
		}
	}

	private static void withdrawal() {
		Scanner scanner= new Scanner(System.in);
		
		CustomerHandler customerHandler = new CustomerHandler();
		
		System.out.print("Enter Customer id : ");
		Integer customerId = scanner.nextInt();
		
		System.out.print("\nEnter Password : ");
		String password = scanner.next();
		
		if (!customerHandler.authenticateCustomer(customerId, password)) 
		{
			System.out.print("Invalid User...!");
		}
		
		System.out.print("\nEnter withdrawal Amount : ");
		Double withdrawalAmount = scanner.nextDouble();
		
		boolean withDrawal = AccountActionHandler.getInstance().withDrawal(customerId, withdrawalAmount);
		
		if(withDrawal)
		{
			System.out.print("Amount Debited Successfully...!");
		}
	}

	private static void deposit() 
	{
		Scanner scanner= new Scanner(System.in);
		
		CustomerHandler customerHandler = new CustomerHandler();
		
		System.out.print("Enter Customer id : ");
		Integer customerId = scanner.nextInt();
		
		System.out.print("\nEnter Password : ");
		String password = scanner.next();
		
		if (!customerHandler.authenticateCustomer(customerId, password)) 
		{
			System.out.print("Invalid User...!");
		}
		
		System.out.print("\nEnter Deposit Amount : ");
		Double depositAmount = scanner.nextDouble();
		
		AccountActionHandler.getInstance().deposit(customerId, depositAmount);
	}
	
	private static void transfer() 
	{
		Scanner scanner= new Scanner(System.in);
		
		CustomerHandler customerHandler = new CustomerHandler();
		
		System.out.print("Enter Customer id : ");
		Integer customerId = scanner.nextInt();
		
		System.out.print("\nEnter Password : ");
		String password = scanner.next();
		
		if (!customerHandler.authenticateCustomer(customerId, password)) 
		{
			System.out.print("Invalid User...!");
		}
		
		System.out.print("\nEnter Transfer Amount : ");
		Double transferAmount = scanner.nextDouble();
		
		System.out.print("Enter Customer id to transfer money: ");
		Integer customerTransferId = scanner.nextInt();
		
		AccountActionHandler.getInstance().withDrawal(customerId, transferAmount);
		AccountActionHandler.getInstance().deposit(customerTransferId, transferAmount);
	}
}
