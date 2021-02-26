package com.model;

public class UserModel {
	private int userId, role, status;
	private String name, address, contact, email, password, date_time;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public UserModel(int userId, String name, String address, String contact, String email, String password, int role,
			String date_time, int stat) {
		super();
		this.userId = userId;
		this.role = role;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.email = email;
		this.password = password;
		this.date_time = date_time;
		this.status = stat;
	}

	public UserModel(int userId, String name, String address, String contact, String email, int role) {
		super();
		this.userId = userId;
		this.role = role;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.email = email;
	}

	public UserModel(String name, String address, String contact, String email, String password, int role,
			String date_time, int stat) {
		super();
		this.role = role;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.email = email;
		this.password = password;
		this.date_time = date_time;
		this.status = stat;
	}

	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", role=" + role + ", status=" + status + ", name=" + name + ", address="
				+ address + ", contact=" + contact + ", email=" + email + ", password=" + password + ", date_time="
				+ date_time + "]";
	}

}
