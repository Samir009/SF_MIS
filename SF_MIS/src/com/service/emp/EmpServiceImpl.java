package com.service.emp;

import java.sql.Connection;
import java.sql.ResultSet;

import com.db.DB;
import com.model.EmpModel;
import com.mysql.jdbc.PreparedStatement;

public class EmpServiceImpl implements EmpService {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public boolean insertEmployee(EmpModel emp) {
		con = DB.getDBObject();
		try {

			try {
				con = DB.getDBObject();
				pst = (PreparedStatement) con.prepareStatement("select * from tbl_z_emp where contact = ?");
				pst.setString(1, emp.getContact());
				rs = pst.executeQuery();

				if (rs.next()) {
					System.err.println("Customer with this contact already exist");
				} else {
					pst = (PreparedStatement) con
							.prepareStatement("insert into tbl_z_emp(name, address, contact, email, post, status) "
									+ "values (?, ?, ?, ?, ?, ?);");
					pst.setString(1, emp.getName());
					pst.setString(2, emp.getAddress());
					pst.setString(3, emp.getContact());
					pst.setString(4, emp.getEmail());
					pst.setInt(5, emp.getPost());
					pst.setInt(6, emp.getStatus());

					pst.execute();
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
	public boolean updateEmployee(EmpModel e) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement(
					"update tbl_z_emp set name = ?, address = ?, contact = ?, email = ?, post = ? where emp_id = ?");
			pst.setString(1, e.getName());
			pst.setString(2, e.getAddress());
			pst.setString(3, e.getContact());
			pst.setString(4, e.getEmail());
			pst.setInt(5, e.getPost());
			pst.setInt(6, e.getId());

			pst.execute();
			return true;

		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean setEmpStatusActive(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setEmpStatusLeaved(int id) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("update tbl_z_emp set status = 2 where emp_id = " + id);
			pst.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getEmpStatus(int id) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("select status from tbl_z_emp where emp_id = " + id);
			rs = pst.executeQuery();
			if (rs.next()) {
				int stat = 0;
				stat = rs.getInt("status");
				if (stat > 0) {
					return stat;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public EmpModel getEmployeeByNumber(String num) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_z_emp where contact = ?");
			pst.setString(1, num);

			rs = pst.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("emp_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String contact = rs.getString("contact");
				String email = rs.getString("email");
				int post = rs.getInt("post");
				int status = rs.getInt("status");

				EmpModel model = new EmpModel(id, name, address, contact, email, post, status);
				return model;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public EmpModel getEmployeeById(int id) {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_z_emp where emp_id = " + id);
			rs = pst.executeQuery();

			if (rs.next()) {
				EmpModel m = new EmpModel(rs.getString("name"), rs.getString("address"), rs.getString("contact"),
						rs.getString("email"), rs.getInt("post"), rs.getInt("status"));

				return m;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
