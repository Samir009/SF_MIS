package com.model;

import java.util.Date;

public class CartModel {
	private int cart_id, product_id, customer_id, qty;
	private float total;
	private Date addedDate, finishingDate;
	private String ordered;

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Date getFinishingDate() {
		return finishingDate;
	}

	public void setFinishingDate(Date finishingDate) {
		this.finishingDate = finishingDate;
	}

	public String getOrdered() {
		return ordered;
	}

	public void setOrdered(String ordered) {
		this.ordered = ordered;
	}

	public CartModel(int cart_id, int product_id, int customer_id, int qty, float total, Date addedDate,
			Date finishingDate, String ordered) {
		super();
		this.cart_id = cart_id;
		this.product_id = product_id;
		this.customer_id = customer_id;
		this.qty = qty;
		this.total = total;
		this.addedDate = addedDate;
		this.finishingDate = finishingDate;
		this.ordered = ordered;
	}

	public CartModel(int product_id, int customer_id, int qty, float total, Date addedDate, Date finishingDate,
			String ordered) {
		super();
		this.product_id = product_id;
		this.customer_id = customer_id;
		this.qty = qty;
		this.total = total;
		this.addedDate = addedDate;
		this.finishingDate = finishingDate;
		this.ordered = ordered;
	}

}
