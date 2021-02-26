package com.model;

import java.util.Date;

public class OrderModel {
	private int o_id, cart_id, order_status, delivery_status, ordered_by, payment_status;
	private float total;
	private Date orderedDate, deliveryDate;

	public int getO_id() {
		return o_id;
	}

	public void setO_id(int o_id) {
		this.o_id = o_id;
	}

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public int getDelivery_status() {
		return delivery_status;
	}

	public void setDelivery_status(int delivery_status) {
		this.delivery_status = delivery_status;
	}

	public int getOrdered_by() {
		return ordered_by;
	}

	public void setOrdered_by(int ordered_by) {
		this.ordered_by = ordered_by;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Date getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(int payment_status) {
		this.payment_status = payment_status;
	}

	public OrderModel(int cart_id, int order_status, int delivery_status, int ordered_by, float total, Date orderedDate,
			Date deliveryDate, int ps) {
		super();
		this.cart_id = cart_id;
		this.order_status = order_status;
		this.delivery_status = delivery_status;
		this.ordered_by = ordered_by;
		this.total = total;
		this.orderedDate = orderedDate;
		this.deliveryDate = deliveryDate;
		this.payment_status = ps;
	}

	public OrderModel(int o_id, int cart_id, int order_status, int delivery_status, int ordered_by, float total,
			Date orderedDate, Date deliveryDate, int ps) {
		super();
		this.o_id = o_id;
		this.cart_id = cart_id;
		this.order_status = order_status;
		this.delivery_status = delivery_status;
		this.ordered_by = ordered_by;
		this.total = total;
		this.orderedDate = orderedDate;
		this.deliveryDate = deliveryDate;
		this.payment_status = ps;
	}

	@Override
	public String toString() {
		return "OrderModel [o_id=" + o_id + ", cart_id=" + cart_id + ", order_status=" + order_status
				+ ", delivery_status=" + delivery_status + ", ordered_by=" + ordered_by + ", total=" + total
				+ ", orderedDate=" + orderedDate + ", deliveryDate=" + deliveryDate + "]";
	}

}
