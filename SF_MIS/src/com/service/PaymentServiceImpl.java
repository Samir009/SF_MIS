package com.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

import com.db.DB;
import com.model.PaymentModel;
import com.mysql.jdbc.PreparedStatement;

public class PaymentServiceImpl implements PaymentService {

	OrderService orderService = new OrderServiceImpl();

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public boolean insertPayment(PaymentModel m) {
		boolean b;
		System.out.println(m.toString());
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement(
					"insert into tbl_payment(customer_id, discount, total, payment_date, status, order_id) "
							+ "values (?, ?, ?, ?, ?, ?)");
			pst.setInt(1, m.getCustomer_id());
			pst.setFloat(2, m.getDiscount());
			pst.setFloat(3, m.getTotal());
			pst.setDate(4, (Date) m.getPayment_date());
			pst.setInt(5, m.getStatus());
			pst.setInt(6, m.getOrder_id());

			pst.execute();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultSet getPaymentDetails(int id) { // fetches only the payment of not paid and partially paid
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement(
					"select * from tbl_payment where customer_id = " + id + " and (status = 2 || status = 3)");
			rs = pst.executeQuery();

			if (rs.next()) {
				while (rs.next()) {
					System.out.println("payment table" + rs.getString("total"));
				}
			} else {
				System.out.println("payment table is empty by id");
				return null;
			}

			rs.beforeFirst();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
