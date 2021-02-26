package com.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import com.db.DB;
import com.model.CustomerModel;
import com.mysql.jdbc.PreparedStatement;

public class CustomerServiceImpl implements CustomerService {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public boolean addCustomer(CustomerModel c) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con
					.prepareStatement("insert into tbl_customer(name, address, contact, email) values (?, ?, ?, ?)");
			pst.setString(1, c.getName());
			pst.setString(2, c.getAddress());
			pst.setString(3, c.getContact());
			pst.setString(4, c.getEmail());

			int rs = pst.executeUpdate();

			if (rs == 1) {
				JOptionPane.showMessageDialog(null, "Customer saved successfully :)", "Saved",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to save Customer :(", "Failed", JOptionPane.ERROR_MESSAGE);
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
	public List<CustomerModel> fetchAllCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fetchCustomerId(String num) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_customer where contact = ?");
			pst.setString(1, num);
			rs = pst.executeQuery();

			if (rs.next() == true) {
				int id = rs.getInt("cust_id");

				return id;

			}
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

		return 0;
	}

	@Override
	public CustomerModel fetchCustByNum(String num) {
		con = DB.getDBObject();
		CustomerModel model;
		try {
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_customer where contact = ?");
			pst.setString(1, num);
			rs = pst.executeQuery();

			if (rs.next() == true) {
				int id = rs.getInt("cust_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String contact = rs.getString("contact");
				String email = rs.getString("email");
				model = new CustomerModel(id, name, address, contact, email);

				return model;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int customerDoesExist(String num) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_customer where contact = ?");
			pst.setString(1, num);

			rs = pst.executeQuery();

			if (rs.next() == true) {
				return rs.getInt("cust_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

}
