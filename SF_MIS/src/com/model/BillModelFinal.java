package com.model;

public class BillModelFinal {
	private int o_id, qty;
	private float rate, total;
	private String product, o_date, d_date, payment_status;

	public int getO_id() {
		return o_id;
	}

	public void setO_id(int o_id) {
		this.o_id = o_id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getO_date() {
		return o_date;
	}

	public void setO_date(String o_date) {
		this.o_date = o_date;
	}

	public String getD_date() {
		return d_date;
	}

	public void setD_date(String d_date) {
		this.d_date = d_date;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public BillModelFinal(int o_id, String product, int qty, float rate, float total, String o_date, String d_date,
			String payment_status) {
		super();
		this.o_id = o_id;
		this.qty = qty;
		this.rate = rate;
		this.total = total;
		this.product = product;
		this.o_date = o_date;
		this.d_date = d_date;
		this.payment_status = payment_status;
	}

	@Override
	public String toString() {
		return "BillModelFinal [o_id=" + o_id + ", qty=" + qty + ", rate=" + rate + ", total=" + total + ", product="
				+ product + ", o_date=" + o_date + ", d_date=" + d_date + " payment_status=" + payment_status + "]";
	}

}
