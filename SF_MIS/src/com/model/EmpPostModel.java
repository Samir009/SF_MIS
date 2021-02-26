package com.model;

public class EmpPostModel {
	private int id;
	private float salary;
	private String post;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public EmpPostModel(int id, float salary, String post) {
		super();
		this.id = id;
		this.salary = salary;
		this.post = post;
	}

	public EmpPostModel(float salary, String post) {
		super();
		this.salary = salary;
		this.post = post;
	}

	public EmpPostModel(int id, String post) {
		super();
		this.id = id;
		this.post = post;
	}

}
