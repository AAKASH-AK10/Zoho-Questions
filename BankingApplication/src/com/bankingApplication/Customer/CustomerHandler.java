package com.bankingApplication.Customer;

import java.util.Scanner;

import com.bankingApplication.Bank;

public class CustomerHandler {
	
	public void addCustomer() 
	{
		@SuppressWarnings("resource")
		Scanner scanner= new Scanner(System.in);
		
		System.out.println("Enter your name : ");
		String customerName = scanner.nextLine();
		
		System.out.println("Enter password : ");
		String password = scanner.nextLine();
		
		System.out.println("Re-type password : ");
		String reTypePassword = scanner.nextLine();
		
		if(!password.equals(reTypePassword))
		{
			System.out.print("Password is not matching...!");
			return;
		}
		
		if (!isValidPassoword(password)) 
		{
			System.out.print("Invalid Password...!");
			return;
		}
		else 
		{
			password = getEncryptedPassword(password);
		}
		
		
		Customer customer = new Customer(Bank.refId, Bank.refAccountNo, customerName, Bank.INITIAL_BALANCE, password);
		Bank.customers.add(customer);
		Bank.customerMap.put(customer.id, customer);
		
		CustomerFileHandler.getInstance().addCustomerToFile(customer);
	}
	
	@SuppressWarnings("null")
	public String getEncryptedPassword(String password) 
	{
		StringBuilder encryptedPassword = new StringBuilder();
		char[] passwordArr = password.toCharArray();
		for (int i = 0; i < passwordArr.length; i++) 
		{
			int passwordchar = passwordArr[i];
			if(passwordchar == 90 || passwordchar == 122)
			{
				encryptedPassword.append(Character.toString(passwordchar-25));
			}
			else if (passwordchar == 57) 
			{
				encryptedPassword.append(Character.toString(passwordchar-9));
			}
			else 
			{
				encryptedPassword.append(Character.toString(passwordchar+1));
			}
		}
		return encryptedPassword.toString();
	}
	
	public boolean isValidPassoword(String password)
	{
		char[] passwordArr = password.toCharArray();
		for (int i = 0; i < passwordArr.length; i++) 
		{
			if((passwordArr[i] >= 65 && passwordArr[i] <= 90) || (passwordArr[i] >= 97 && passwordArr[i] <= 122) || (passwordArr[i] >= 48 && passwordArr[i] <= 57))
			{
				continue;
			}
			else 
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean authenticateCustomer(Integer customerId, String password) 
	{
		String userpassword = getEncryptedPassword(password);
		
		Customer customer = Bank.customerMap.get(customerId);
		
		if(customer == null)
		{
			System.out.print("Customer ID is Invalid");
			return false;
		}
		
		if(!customer.getCustomerPassword().equals(userpassword))
		{
			System.out.print("Authentication Failed...:(");
			return false;
		}
		System.out.print("Authentication Successfull...:)");
		return true;
	}
}
