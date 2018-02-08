package com.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class Top10DebtDTO {
	private List<Long> customerList = new ArrayList<Long>();
	private List<Double> debtList = new ArrayList<Double>();
	private List<String> nameList = new ArrayList<String>();
	
	
	public List<String> getNameList() {
		return nameList;
	}

	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

	public List<Long> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Long> customerList) {
		this.customerList = customerList;
	}

	public List<Double> getDebtList() {
		return debtList;
	}

	public void setDebtList(List<Double> debtList) {
		this.debtList = debtList;
	}

	public void addCustomerId(Long a) {
		this.customerList.add(a);
	}

	public void addDebt(Double a) {
		this.debtList.add(a);
	}
	
	public void addName(String a) {
		this.nameList.add(a);
	}
}
