package com.bankingApplication;

import com.bankingApplication.Customer.Customer;

public class AccountActionHandler {
	
	static AccountActionHandler accountActionHandler;
	
	public static AccountActionHandler getInstance() 
	{
		if (accountActionHandler == null) 
		{
			accountActionHandler = new AccountActionHandler();
		}
		return accountActionHandler;
	} 
	@SuppressWarnings("unlikely-arg-type")
	public void deposit(Integer customerId, Double depositAmount) 
	{
		Customer customer = Bank.customerMap.get(customerId);
		
		if (depositAmount < 0) 
		{
			System.out.print("Deposit Amount should not be zero or below zero rupees");
			return;
		}
		
		customer.customerSalary += depositAmount ;
		
		Bank.customerMap.put(customer.id, customer);
	}
	
	public boolean withDrawal(Integer customerId, Double withdrawAmount) 
	{
		Customer customer = Bank.customerMap.get(customerId);
		
		customer.customerSalary -= withdrawAmount;
		
		if (customer.customerSalary <= 1000) 
		{
			System.out.print("Maintain minimum balence....!");
			return false;
		}
		
		Bank.customerMap.put(customer.id, customer);
		
		return true;
	}
	
	public void transfer(Integer fromCustomerId, Integer toCustomerId, Double transferAmount) 
	{
		Customer toCustomer= Bank.customerMap.get(toCustomerId);
		
		if (toCustomer == null) 
		{
			System.out.print("Customer does not exist...!");
			return;
		}
		
		Customer fromCustomer = Bank.customerMap.get(fromCustomerId);
		
		boolean isSuccess = withDrawal(fromCustomer.getId(), transferAmount);
		
		if (isSuccess) 
		{
			deposit(toCustomer.getId(), transferAmount);
		}
	}
}
