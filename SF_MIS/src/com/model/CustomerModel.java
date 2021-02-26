package com.model;

public class CustomerModel {
	/**
	 * 
	 */

	private int cust_id;
	private String name, address, contact, email;

	public int getId() {
		return cust_id;
	}

	public void setId(int id) {
		this.cust_id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CustomerModel(String name, String address, String contact, String email) {
		super();
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.email = email;
	}

	public CustomerModel(int id, String name, String address, String contact, String email) {
		super();
		this.cust_id = id;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.email = email;
	}

	@Override
	public String toString() {
		return "CustomerModel [cust_id=" + cust_id + ", name=" + name + ", address=" + address + ", contact=" + contact
				+ ", email=" + email + "]";
	}

}
