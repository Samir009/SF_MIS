package com.service.user;

import java.sql.Connection;
import java.sql.ResultSet;

import com.db.DB;
import com.model.UserModel;
import com.mysql.jdbc.PreparedStatement;

public class UserServiceImpl implements UserService {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public boolean addUser(UserModel m) {

		try {

			try {
				con = DB.getDBObject();
				pst = (PreparedStatement) con.prepareStatement("select * from tbl_user where contact = ?");
				pst.setString(1, m.getContact());
				rs = pst.executeQuery();
				if (rs.next()) {
					System.out.println("User may exist");
					return false;
				} else {
					System.out.println("user info inserting...........");
					con = DB.getDBObject();
					pst = (PreparedStatement) con.prepareStatement(
							"insert into tbl_user(name, address, contact, email, password, role, date_time, status) "
									+ "values(?, ?, ?, ?, ?, ?, ?, ?)");
					pst.setString(1, m.getName());
					pst.setString(2, m.getAddress());
					pst.setString(3, m.getContact());
					pst.setString(4, m.getEmail());
					pst.setString(5, m.getPassword());
					pst.setInt(6, m.getRole());
					pst.setString(7, m.getDate_time());
					pst.setInt(8, m.getStatus());
					pst.execute();
					System.out.println("user info inserted successfully !!!");
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultSet loginUser(String u, String p) {
		try {
			con = DB.getDBObject();

			pst = (PreparedStatement) con.prepareStatement("select * from tbl_user where email = ? and password = ?");
			pst.setString(1, u);
			pst.setString(2, p);

			rs = pst.executeQuery();
			if (rs.next()) {
				return rs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserModel searchUserByNum(String num) {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_user where contact = ?");
			pst.setString(1, num);
			rs = pst.executeQuery();
			if (rs.next()) {
				UserModel m = new UserModel(rs.getInt("user_id"), rs.getString("name"), rs.getString("address"),
						rs.getString("contact"), rs.getString("email"), rs.getInt("role"));
				return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean editUser(UserModel m) {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con
					.prepareStatement("update tbl_user set name = ?, address = ?, contact = ?, email = ?, role = ?"
							+ " where user_id = " + m.getUserId());
			pst.setString(1, m.getName());
			pst.setString(2, m.getAddress());
			pst.setString(3, m.getContact());
			pst.setString(4, m.getEmail());
			pst.setInt(5, m.getRole());

			pst.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getUserStatus(int id) {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select status from tbl_user where user_id = " + id);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt("status");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean userLeaved(int id) {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("update tbl_user set status = 2 where user_id = " + id);
			pst.execute();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultSet getAllUsers() {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_user");
			rs = pst.executeQuery();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
