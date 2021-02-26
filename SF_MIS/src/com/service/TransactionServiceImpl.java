package com.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.db.DB;
import com.model.TransactionModel;
import com.mysql.jdbc.PreparedStatement;

public class TransactionServiceImpl implements TransactionService {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public boolean addTransaction(TransactionModel model) {

		con = DB.getDBObject();

		System.out.println(
				"Transaction model: " + model.getAmt() + "\n " + model.getDatetime() + "\n" + model.getCust_id());
		try {
			pst = (PreparedStatement) con.prepareStatement(
					"insert into tbl_transaction_record(customer_id, amount, date_time, transacted_by) values (?, ?, ?, ?)");
			pst.setInt(1, model.getCust_id());
			pst.setFloat(2, model.getAmt());
			pst.setString(3, model.getDatetime());
			pst.setInt(4, model.getTransacted_by());
			int rs = pst.executeUpdate();
			if (rs == 1) {
				JOptionPane.showMessageDialog(null, "Transaction saved successfully :)", "Saved",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to save Transaction :(", "Failed",
						JOptionPane.ERROR_MESSAGE);
			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
