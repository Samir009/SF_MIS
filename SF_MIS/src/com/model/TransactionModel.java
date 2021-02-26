package com.model;

public class TransactionModel {
	private int id, cust_id, transacted_by;
	private float amt;
	private String datetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCust_id() {
		return cust_id;
	}

	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}

	public float getAmt() {
		return amt;
	}

	public void setAmt(float amt) {
		this.amt = amt;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public int getTransacted_by() {
		return transacted_by;
	}

	public void setTransacted_by(int transacted_by) {
		this.transacted_by = transacted_by;
	}

	public TransactionModel(int id, int cust_id, int transacted_by, float amt, String datetime) {
		super();
		this.id = id;
		this.cust_id = cust_id;
		this.transacted_by = transacted_by;
		this.amt = amt;
		this.datetime = datetime;
	}

	public TransactionModel(int cust_id, int transacted_by, float amt, String datetime) {
		super();
		this.cust_id = cust_id;
		this.transacted_by = transacted_by;
		this.amt = amt;
		this.datetime = datetime;
	}

}
