package com.bankingApplication;

import java.util.ArrayList;
import java.util.HashMap;

import com.bankingApplication.Customer.Customer;

public class Bank {
	
	public static ArrayList<Customer> customers = new ArrayList<Customer>();
	public static HashMap<Integer, Customer> customerMap = new HashMap<Integer, Customer>();
	
	//Add Customers
	public static Integer refId;
	public static Integer refAccountNo;
	public static final Double INITIAL_BALANCE = 10000D;
	
}
