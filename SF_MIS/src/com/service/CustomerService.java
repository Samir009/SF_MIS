package com.service;

import java.util.List;

import com.model.CustomerModel;

public interface CustomerService {

	boolean addCustomer(CustomerModel c);

	int customerDoesExist(String num);

	int fetchCustomerId(String num);

	CustomerModel fetchCustByNum(String num);

	List<CustomerModel> fetchAllCustomer();

}
