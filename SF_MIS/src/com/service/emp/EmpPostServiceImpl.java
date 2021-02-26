package com.service.emp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.db.DB;
import com.model.EmpPostModel;
import com.mysql.jdbc.PreparedStatement;

public class EmpPostServiceImpl implements EmpPostService {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public List<EmpPostModel> getAvailablePost() {
		con = DB.getDBObject();
		List<EmpPostModel> list = new ArrayList<>();
		try {
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_z_emp_post");
			rs = pst.executeQuery();
			while (rs.next()) {

				EmpPostModel model = new EmpPostModel(rs.getInt("post_id"), rs.getString("post"));
				list.add(model);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addEmpPost(String post, float sal) {

		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("insert into tbl_z_emp_post (post, salary) values (?, ?)");
			pst.setString(1, post);
			pst.setFloat(2, sal);
			pst.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public int getAvailablePostIdByName(String post) {
		con = DB.getDBObject();

		try {
			pst = (PreparedStatement) con
					.prepareStatement("select post_id from tbl_z_emp_post where post = " + "'" + post + "'");
			rs = pst.executeQuery();

			if (rs.next()) {
				// System.out.println("Rs post: " + rs.getInt("post_id"));
				return rs.getInt("post_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getPostById(int id) {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select post from tbl_z_emp_post where post_id = " + id);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getString("post");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
