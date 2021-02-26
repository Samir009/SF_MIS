package com.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.db.DB;
import com.model.BillModelFinal;
import com.mysql.jdbc.PreparedStatement;

import util.GlobalVariable;

public class OrderServiceImpl implements OrderService {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public boolean insertOrder(ResultSet rs, int p_stat) {
		con = DB.getDBObject();

		try {
			System.out.println(rs.getFloat("total"));
			System.out.println(rs.getInt("customer_id"));
			System.out.println(rs.getDate("addedDate"));

			pst = (PreparedStatement) con.prepareStatement(
					"insert into tbl_order (cart_id, total, ordered_by, ordered_date, delivery_date, order_status, delivery_status, payment_status) values (?, ?, ?, ?, ?, ?, ?, ?)");

			pst.setInt(1, Integer.parseInt(rs.getString("cart_id")));
			pst.setFloat(2, rs.getFloat("total"));
			pst.setInt(3, rs.getInt("customer_id"));
			pst.setDate(4, rs.getDate("addedDate"));
			pst.setDate(5, rs.getDate("finishingDate"));
			pst.setInt(6, GlobalVariable.OrderNotReady);
			pst.setInt(7, GlobalVariable.NotDelivered);
			pst.setInt(8, p_stat);

			int r = pst.executeUpdate();

			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultSet fetchOrders(int id) {
		System.out.println("Customer id is: " + id);
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_order where ordered_by = " + id
					+ " and (payment_status = 2 || payment_status = 3)");

			rs = pst.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					System.out.println("Total: " + rs.getFloat("total") + " date: " + rs.getDate("ordered_date"));
				}
			} else {
				System.err.println("Order table is empty");
			}

			rs.beforeFirst();
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public ResultSet fetchOrdersToDisplayInTable(int id) { // fetches order for not paid or partially paid
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con
					.prepareStatement("select tbl_order.cart_id, tbl_customer.name, tbl_product.name as product, "
							+ "tbl_cart.qty, tbl_product.rate, tbl_order.total, tbl_order.ordered_date as o_date, "
							+ "tbl_order.delivery_date as d_date, tbl_order_status.order_status, tbl_delivery_status.delivery_status "
							+ "from tbl_order inner join tbl_cart on tbl_order.cart_id = tbl_cart.cart_id "
							+ "inner join tbl_order_status on tbl_order.order_status = tbl_order_status.o_stat_id "
							+ "inner join tbl_delivery_status on tbl_order.delivery_status = tbl_delivery_status.delivery_id "
							+ "inner join tbl_product on tbl_cart.product_id = tbl_product.prod_id "
							+ "inner join tbl_customer on tbl_order.ordered_by = tbl_customer.cust_id "
							+ "WHERE tbl_order.ordered_by = " + id + " and (payment_status = 2 || payment_status =3)");
			rs = pst.executeQuery();
//
//			rs.beforeFirst();
			return rs;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet fetchAllOrdersById(int id) {
		con = DB.getDBObject();

		try {
			pst = (PreparedStatement) con.prepareStatement(
					"select tbl_order.o_id, tbl_product.name as product, tbl_cart.qty, tbl_product.rate, tbl_order.total, "
							+ "tbl_order.ordered_date as o_date, tbl_order.delivery_date as d_date, tbl_order_status.order_status, "
							+ "tbl_delivery_status.delivery_status, tbl_payment_status.payment_status from "
							+ "tbl_order inner join tbl_cart on tbl_order.cart_id = tbl_cart.cart_id "
							+ "inner join tbl_order_status on tbl_order.order_status = tbl_order_status.o_stat_id "
							+ "inner join tbl_delivery_status on tbl_order.delivery_status = tbl_delivery_status.delivery_id "
							+ "inner join tbl_product on tbl_cart.product_id = tbl_product.prod_id "
							+ "inner join tbl_customer on tbl_order.ordered_by = tbl_customer.cust_id "
							+ "inner join tbl_payment_status on tbl_order.payment_status = tbl_payment_status.pay_stat_id "
							+ "WHERE tbl_order.ordered_by = " + id);
			rs = pst.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					System.out.println("Product : " + rs.getString("product") + " Total : " + rs.getFloat("total"));
				}
				rs.beforeFirst();
				return rs;
			} else {
				System.err.println("Fetch all orders is null");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public float sumOfTotalOrderPNpaid(int id) {
		float total = 0;
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con
					.prepareStatement("select sum(total) as total from tbl_order WHERE ordered_by = " + id
							+ " and (payment_status = 3 || payment_status = 2)");
			rs = pst.executeQuery();
			if (rs.next()) {
				total = rs.getFloat("total");
			}
			return total;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean orderStatusPaid(int id) {
		boolean b;
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con
					.prepareStatement("update tbl_order set payment_status = 1 where ordered_by = " + id);

			b = pst.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getOrderStatus(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setOrderStatusReady(int id, int stat) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("update tbl_order set order_status = " + stat
					+ " where ordered_by = " + id + " and order_status = 2");
			pst.execute();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getDeliveryStatus(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setDeliveryStatsReady(int id, int stat) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("update tbl_order set delivery_status = " + stat
					+ " where ordered_by = " + id + " and delivery_status = 2");
			pst.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<BillModelFinal> fetchAllOrdersforBill(String[] st) {
		List<BillModelFinal> list = new ArrayList<>();
		if (st != null) {
			try {
				con = DB.getDBObject();
				for (String s : st) {
//				idList.add(Integer.parseInt(s));
					pst = (PreparedStatement) con
							.prepareStatement("select tbl_order.o_id, tbl_product.name as product, tbl_cart.qty, "
									+ "tbl_product.rate, tbl_order.total, tbl_order.ordered_date as o_date, "
									+ "tbl_order.delivery_date as d_date," + "tbl_payment_status.payment_status "
									+ "from tbl_order inner join tbl_cart on tbl_order.cart_id = tbl_cart.cart_id "
									+ "inner join tbl_order_status on tbl_order.order_status = tbl_order_status.o_stat_id "
									+ "inner join tbl_delivery_status on tbl_order.delivery_status = tbl_delivery_status.delivery_id "
									+ "inner join tbl_product on tbl_cart.product_id = tbl_product.prod_id "
									+ "inner join tbl_customer on tbl_order.ordered_by = tbl_customer.cust_id  "
									+ "inner join tbl_payment_status on tbl_order.payment_status = tbl_payment_status.pay_stat_id "
									+ "WHERE tbl_order.o_id = " + Integer.parseInt(s));
					rs = pst.executeQuery();
					if (rs.next()) {
						BillModelFinal b = new BillModelFinal(rs.getInt("o_id"), rs.getString("product"),
								rs.getInt("qty"), rs.getFloat("rate"), rs.getFloat("total"), rs.getString("o_date"),
								rs.getString("d_date"), rs.getString("payment_status"));

						list.add(b);
					}
				}

				return list;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public ResultSet getCustomerDetailsByOrderId(int oid) {
		System.out.println("Called");
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement(
					"select tbl_customer.cust_id, tbl_customer.name, tbl_customer.contact from tbl_order "
							+ "inner join tbl_customer on tbl_order.ordered_by = tbl_customer.cust_id "
							+ "WHERE tbl_order.o_id = " + oid);
			rs = pst.executeQuery();

			return rs;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
