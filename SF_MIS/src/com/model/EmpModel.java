package com.model;

public class EmpModel {
	private int id, post, status;
	private String name, address, contact, email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public EmpModel(int id, String name, String address, String contact, String email, int post, int status) {
		super();
		this.id = id;
		this.post = post;
		this.status = status;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.email = email;
	}

	public EmpModel(String name, String address, String contact, String email, int post, int status) {
		super();
		this.post = post;
		this.status = status;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmpModel [id=" + id + ", post=" + post + ", status=" + status + ", name=" + name + ", address="
				+ address + ", contact=" + contact + ", email=" + email + "]";
	}

}
