package com.service.emp;

import java.sql.Connection;
import java.sql.ResultSet;

import com.db.DB;
import com.model.EmpPaymentModel;
import com.mysql.jdbc.PreparedStatement;

public class EmpPaymentServiceImpl implements EmpPaymentService {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public boolean paySalary(EmpPaymentModel m) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement(
					"insert into tbl_z_emp_payment(emp_id, salary, advance, date_time) values (?, ?, ?, ?)");
			pst.setInt(1, m.getEmp_id());
			pst.setFloat(2, m.getSalary());
			pst.setFloat(3, m.getAdvance());
			pst.setString(4, m.getDate_time());

			pst.execute();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultSet getPaymentTable() {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_z_emp_payment");
			rs = pst.executeQuery();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
