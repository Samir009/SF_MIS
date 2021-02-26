package com.service;

import java.sql.ResultSet;
import java.util.List;

import com.model.BillModelFinal;

public interface OrderService {
	boolean insertOrder(ResultSet rs, int p_stat);

	float sumOfTotalOrderPNpaid(int id);

	ResultSet fetchOrdersToDisplayInTable(int id);

	ResultSet fetchOrders(int id);

	// to display all orders by id in "order product details" where order status,
	// delivery status are are displayed.
	ResultSet fetchAllOrdersById(int id);

	boolean orderStatusPaid(int id);

	boolean getOrderStatus(int id);

	boolean setOrderStatusReady(int id, int stat);

	boolean getDeliveryStatus(int id);

	boolean setDeliveryStatsReady(int id, int stat);

	List<BillModelFinal> fetchAllOrdersforBill(String[] s);

	ResultSet getCustomerDetailsByOrderId(int oid);
}
