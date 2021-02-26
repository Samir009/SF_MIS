package com.model;

public class EmpPaymentModel {
	private int id, emp_id;
	private float salary, advance;
	private String date_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public float getAdvance() {
		return advance;
	}

	public void setAdvance(float advance) {
		this.advance = advance;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	public EmpPaymentModel(int id, int emp_id, float salary, float advance, String date_time) {
		super();
		this.id = id;
		this.emp_id = emp_id;
		this.salary = salary;
		this.advance = advance;
		this.date_time = date_time;
	}

	public EmpPaymentModel(int emp_id, float salary, float advance, String date_time) {
		super();
		this.emp_id = emp_id;
		this.salary = salary;
		this.advance = advance;
		this.date_time = date_time;
	}

	@Override
	public String toString() {
		return "EmpPaymentModel [id=" + id + ", emp_id=" + emp_id + ", salary=" + salary + ", advance=" + advance
				+ ", date_time=" + date_time + "]";
	}

}
