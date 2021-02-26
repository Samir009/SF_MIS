package com.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.db.DB;
import com.model.CartModel;
import com.mysql.jdbc.PreparedStatement;

import util.GlobalVariable;

public class CartServiceImpl implements CartService {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public boolean addToCart(CartModel m) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement(
					"insert into tbl_cart(product_id, customer_id, qty, total, addedDate, finishingDate, ordered) "
							+ "values (?, ?, ?, ?, ?, ?, ?)");
			pst.setInt(1, m.getProduct_id());
			pst.setInt(2, m.getCustomer_id());
			pst.setInt(3, m.getQty());
			pst.setFloat(4, m.getTotal());
			pst.setDate(5, (Date) m.getAddedDate());
			pst.setDate(6, (Date) m.getFinishingDate());
			pst.setString(7, m.getOrdered());

			int rs = pst.executeUpdate();

			if (rs == 1) {
				JOptionPane.showMessageDialog(null, "Added to cart successfully :)", "Saved",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to add cart:(", "Failed", JOptionPane.ERROR_MESSAGE);
			}

			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public ResultSet fetchCart() {
		con = DB.getDBObject();

		try {
			pst = (PreparedStatement) con.prepareStatement(
					"select tbl_product.name as product_name, tbl_product.rate, tbl_cart.qty, tbl_cart.total, tbl_customer.name as added_by, tbl_cart.addedDate, tbl_cart.finishingDate from tbl_cart inner join tbl_product on tbl_cart.product_id = tbl_product.prod_id inner join tbl_customer on tbl_cart.customer_id = tbl_customer.cust_id where tbl_cart.ordered = \"n\" ORDER BY tbl_cart.finishingDate ASC;");
			rs = pst.executeQuery();

			if (rs != null) {

//				con.close();
				return rs;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet fetchCartById(int id) {

		System.err.println("Fetch cart by ID called. Id:" + id);
		con = DB.getDBObject();
		try {

			pst = (PreparedStatement) con.prepareStatement("select * from tbl_cart where customer_id = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();

			while (rs.next()) {
				System.out.println("cart total: " + rs.getFloat("total"));
				System.out.println("cart rows: " + rs.getRow());
			}
			rs.beforeFirst();
			return rs;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public float fetchCartTotalById(int id) {
		System.out.println("Total sum of cart function called.");

		con = DB.getDBObject();

		try {
			pst = (PreparedStatement) con
					.prepareStatement("select sum(total) as total from tbl_cart where customer_id = " + id);

			rs = pst.executeQuery();

			if (rs.next()) {
				float total = rs.getFloat("total");
				System.out.println("total amt cart: " + total);

				return total;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean deleteFromCartById(int id) {
		con = DB.getDBObject();

		try {

			pst = (PreparedStatement) con.prepareStatement("delete from tbl_cart WHERE customer_id = " + id + ";");

			int r = pst.executeUpdate();

			if (r == 1) {
				System.err.println("Removed from cart, customer id: " + id);
			} else {
				System.err.println("unable to remove from cart, customer id: " + id);
			}
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public void setCartOrder(int id) {
		if (id > 0) {
			con = DB.getDBObject();
			try {
				pst = (PreparedStatement) con
						.prepareStatement("update tbl_cart set ordered = ? where customer_id = " + id);
				pst.setString(1, GlobalVariable.cartOrdered);
				pst.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Customer id not found to change cart's ordered column");
		}
	}

	@Override
	public String getCartOrderStat(int id) {
		String os = null;
		if (id > 0) {
			try {
				con = DB.getDBObject();
				pst = (PreparedStatement) con
						.prepareStatement("select ordered from tbl_cart where customer_id = " + id);
				rs = pst.executeQuery();
				while (rs.next()) {
					os = rs.getString("ordered");
				}
				return os;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
