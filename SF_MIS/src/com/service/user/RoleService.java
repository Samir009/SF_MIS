package com.service.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.db.DB;
import com.model.RoleModel;
import com.mysql.jdbc.PreparedStatement;

public class RoleService {

	private static Connection con;
	private static PreparedStatement pst;
	private static ResultSet rs;

	public boolean insertNewRole(String role) {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("insert into tbl_role (role) values (?)");
			pst.setString(1, role);
			pst.execute();

			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	public ResultSet getAllRoles() {
		try {
			List<RoleModel> r = new ArrayList<>();
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_role");
			rs = pst.executeQuery();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<RoleModel> getRoles() {
		try {
			List<RoleModel> r = new ArrayList<>();
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_role");
			rs = pst.executeQuery();
			while (rs.next()) {
				RoleModel m = new RoleModel(rs.getInt("role_id"), rs.getString("role"));
				r.add(m);
			}
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getRoleIdByName(String rolename) {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select role_id from tbl_role where role = ?");
			pst.setString(1, rolename);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt("role_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getRoleById(int id) {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select role from tbl_role where role_id = " + id);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getString("role");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
