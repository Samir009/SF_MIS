package com.service.emp;

import java.sql.Connection;
import java.sql.ResultSet;

import com.db.DB;
import com.mysql.jdbc.PreparedStatement;

public class EmpSalaryService {

	private static Connection con;
	private static PreparedStatement pst;
	private static ResultSet rs;

	public static ResultSet getSalaryList() {

		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_z_emp_salary");
			rs = pst.executeQuery();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	public boolean insertSalary(float sal) {

		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("insert into tbl_z_emp_salary(salary) value (?)");
			pst.setFloat(1, sal);
			pst.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public float getSalaryById(int id) {

		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement(
					"select tbl_z_emp_post.salary from tbl_z_emp inner join tbl_z_emp_post on tbl_z_emp.post "
							+ "= tbl_z_emp_post.post_id where tbl_z_emp.emp_id = " + id);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getFloat("salary");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
}
