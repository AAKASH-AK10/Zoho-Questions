package com.bankingApplication.Customer;

public class Customer {
	
	public static final String SPACE = " ";
	
	public Integer id;
	public Integer accountNo;
	public String customerName;
	public Double customerSalary;
	public String customerPassword;

	public Customer(Integer id, Integer accountNo, String customerName, Double customerSalary, String customerPassword) 
	{
		this.id = id;
		this.accountNo = accountNo;
		this.customerName = customerName;
		this.customerSalary = customerSalary;
		this.customerPassword = customerPassword;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Integer accountNo) {
		this.accountNo = accountNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getCustomerSalary() {
		return customerSalary;
	}

	public void setCustomerSalary(Double customerSalary) {
		this.customerSalary = customerSalary;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	@Override
	public String toString() {
		return id + SPACE + accountNo + SPACE +customerName + SPACE + customerSalary + SPACE + customerPassword;
	}
	
	
}
