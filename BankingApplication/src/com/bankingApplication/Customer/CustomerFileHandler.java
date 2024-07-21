package com.bankingApplication.Customer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.bankingApplication.Bank;

public class CustomerFileHandler {
	
	static CustomerFileHandler handler;
	
	public static CustomerFileHandler getInstance() 
	{
		if(handler == null)
		{
			handler = new CustomerFileHandler();
		}
		return handler;
	}

	public static final String fileName = "sample.txt";

	public void initialize() 
	{
		try 
		{
			ArrayList<Customer> customers = new ArrayList<Customer>();
			HashMap<Integer, Customer> customerMap = new HashMap<Integer, Customer>();
			
			File file = new File(fileName);
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			
			String customerInfo = bufferedReader.readLine();
			do 
			{
				Customer customerDetails = setCustomerDetails(customerInfo);
				customers.add(customerDetails);
				customerMap.put(customerDetails.getId(), customerDetails);
				customerInfo = bufferedReader.readLine();
			} while (customerInfo != null);
			
			bufferedReader.close();
			
			Bank.customers=customers;
			Bank.customerMap = customerMap;
			Bank.refId = customers.size() + 1;
			Bank.refAccountNo = customers.get(customers.size()-1).getAccountNo() + 1;
		} 
		catch (IOException e) 
		{
			System.out.print("Exception while reading the file... " + e);
		}
	}
	
	private Customer setCustomerDetails(String customerInfo) 
	{
		String[] customerDetailsRow = customerInfo.split("\\s+");
		Customer customers = new Customer(
				Integer.parseInt(customerDetailsRow[0]),
				Integer.parseInt(customerDetailsRow[1]),
				customerDetailsRow[2],
				Double.valueOf(customerDetailsRow[3]),
				customerDetailsRow[4]
				);
		return customers;
	}
	
	@SuppressWarnings("resource")
	public void addCustomerToFile(Customer customer)
	{
		File file = new File(fileName);
		FileWriter fileWriter = null;
		try 
		{
			fileWriter = new FileWriter(file, true);
			fileWriter.write("\n" + customer.toString());	
			System.out.print("Customer Added Successfully...!!!");
		} 
		catch (IOException e) 
		{
			System.out.print("Exception while writing the file... " + e);
		}
		finally 
		{
			if(fileWriter != null)
			{
				try 
				{
					fileWriter.close();
				} 
				catch (IOException e) 
				{
					System.out.print("Exception while closing the file... " + e);
				}
			}
		}
	}
		
	@SuppressWarnings("resource")
	public void updateCustomerDetails()
	{
		File file = new File(fileName);
		FileWriter fileWriter = null;
		try 
		{
			fileWriter = new FileWriter(file);
			Set keySet = Bank.customerMap.keySet();
			
			Iterator iterator = keySet.iterator();
			
			while (iterator.hasNext()) 
			{
				Integer id = Integer.valueOf((int) iterator.next());
				Customer customer = Bank.customerMap.get(id);
				
				fileWriter.write(customer.toString() + "\n");
			}
			
			System.out.print("Customer Details Updated Successfully...!!!");
		} 
		catch (IOException e) 
		{
			System.out.print("Exception while writing the file... " + e);
		}
		finally 
		{
			if(fileWriter != null)
			{
				try 
				{
					fileWriter.close();
				} 
				catch (IOException e) 
				{
					System.out.print("Exception while closing the file... " + e);
				}
			}
		}
	}
}
