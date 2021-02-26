package com.model;

import java.util.Date;

public class PaymentModel {
	private int payment_id, customer_id, status, order_id;
	private float discount, total;
	private Date payment_date;

	public int getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Date getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public PaymentModel(int payment_id, int customer_id, int status, int order_id, float discount, float total,
			Date payment_date) {
		super();
		this.payment_id = payment_id;
		this.customer_id = customer_id;
		this.status = status;
		this.order_id = order_id;
		this.discount = discount;
		this.total = total;
		this.payment_date = payment_date;
	}

	public PaymentModel(int customer_id, int status, int order_id, float discount, float total, Date payment_date) {
		super();
		this.customer_id = customer_id;
		this.status = status;
		this.order_id = order_id;
		this.discount = discount;
		this.total = total;
		this.payment_date = payment_date;
	}

	@Override
	public String toString() {
		return "PaymentModel [payment_id=" + payment_id + ", customer_id=" + customer_id + ", status=" + status
				+ ", order_id=" + order_id + ", discount=" + discount + ", total=" + total + ", payment_date="
				+ payment_date + "]";
	}

}
