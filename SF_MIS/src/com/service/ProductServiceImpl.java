package com.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.db.DB;
import com.model.ProductModel;
import com.mysql.jdbc.PreparedStatement;

public class ProductServiceImpl implements ProductService {
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public boolean addProduct(ProductModel m) {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con
					.prepareStatement("insert into tbl_product(name, rate, color, category) values (?, ?, ?, ?)");
			pst.setString(1, m.getName());
			pst.setFloat(2, m.getRate());
			pst.setString(3, m.getColor());
			pst.setInt(4, m.getCategory());

			int rs = pst.executeUpdate();

			if (rs == 1) {
				JOptionPane.showMessageDialog(null, "Product saved successfully :)", "Saved",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to save Product :(", "Failed", JOptionPane.ERROR_MESSAGE);
			}

			return true;

		} catch (Exception ex) {

			ex.printStackTrace();
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
	public ResultSet fetchProduct() {
		con = DB.getDBObject();
		try {
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_product");
			rs = pst.executeQuery();

//			con.close();
			return rs;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductModel> fetchProductList() {
		con = DB.getDBObject();
		List<ProductModel> productList = new ArrayList<ProductModel>();
		// product details
		int id;
		String name;
		float rate;
		String color;
		int category;

		try {
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_product");
			rs = pst.executeQuery();

			while (rs.next()) {
				id = rs.getInt("prod_id");
				name = rs.getString("name");
				rate = rs.getFloat("rate");
				color = rs.getString("color");
				category = rs.getInt("category");

				ProductModel productModel = new ProductModel(id, category, rate, name, color);
				productList.add(productModel);

//				System.out.println("Fetch product list: " + productModel.toString());
			}

			return productList;

		} catch (Exception ex) {
			System.out.println("Failed to fetch product list: " + ex.getLocalizedMessage());
			System.out.println(ex);
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
	public ProductModel fetchProductByName(String n) {
		con = DB.getDBObject();
		ProductModel productModel = null;

		try {
			pst = (PreparedStatement) con.prepareStatement("select * from tbl_product where name = ?");
			pst.setString(1, n);
			rs = pst.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("prod_id");
				String name = rs.getString("name");
				float rate = rs.getFloat("rate");
				String color = rs.getString("color");
				int category = rs.getInt("category");

				productModel = new ProductModel(id, category, rate, name, color);

			}
			System.out.println("Result set: " + rs.toString());

			return productModel;

		} catch (Exception ex) {
			System.out.println(ex);
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
	public boolean editProductRate(int id, float rate) {
		con = DB.getDBObject();
		try {

			pst = (PreparedStatement) con
					.prepareStatement("UPDATE tbl_product set rate = " + rate + " WHERE prod_id = " + id);
			int rs = pst.executeUpdate();

			if (rs == 1) {
				JOptionPane.showMessageDialog(null, "Product edited successfully :)", "Edited",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to edit Product :(", "Failed", JOptionPane.ERROR_MESSAGE);
			}

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
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
	public List<ProductModel> fetchProductNameAndRateByCatId(int id) {
		List<ProductModel> list = new ArrayList<ProductModel>();
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select name, rate from tbl_product where category = " + id);
			rs = pst.executeQuery();
			while (rs.next()) {
				ProductModel p = new ProductModel(rs.getFloat("rate"), rs.getString("name"));
//				System.out.println("Product name: " + rs.getString("name") + " and rate: " + rs.getFloat("rate"));
				list.add(p);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public float fetchProductRateByName(String nam) {
		try {
			con = DB.getDBObject();
			pst = (PreparedStatement) con.prepareStatement("select rate from tbl_product where name = ?");
			pst.setString(1, nam);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getFloat("rate");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

}
