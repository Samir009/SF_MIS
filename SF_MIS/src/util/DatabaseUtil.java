package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.db.DB;

public class DatabaseUtil {
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs; // to fetch and display data

	public DatabaseUtil() {
		con = DB.getDBObject();
	}

	// inserts category of products
	public void insertCategory(String cat) {
		try {
			pst = con.prepareStatement("insert into tbl_category(category)values (?)");
			pst.setString(1, cat);

			int r = pst.executeUpdate();

			if (r == 1) {
				JOptionPane.showMessageDialog(null, "Category saved successfully :)", "Saved",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to save Category :(", "Failed", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getCatId(String cat) {
		try {
			pst = con.prepareStatement("select * from tbl_category where category = ?");
			pst.setString(1, cat);

			rs = pst.executeQuery();
			if (rs.next() == true) {
				int id = rs.getInt("cat_id");
				return id;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<String> retreiveCategoryName() {
		ArrayList<String> cat = new ArrayList<>();
		try {
			pst = con.prepareStatement("select category from tbl_category");
			rs = pst.executeQuery();

			while (rs.next()) {
				cat.add(rs.getString("category"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cat;
	}

	public ResultSet fetchCategory() {
		try {
			pst = con.prepareStatement("select * from tbl_category");
			rs = pst.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public boolean storeBill(int bid, int cid, float t, float dis, int gen_by) {

		System.err.println("Bill no:" + bid);
		System.err.println("Custom5er id: " + cid);
		System.err.println("Total: " + t);
		System.err.println("Discount:" + dis);
		System.err.println("Generated by: " + gen_by);

		try {
			Connection con = DB.getDBObject();
			pst = con.prepareStatement(
					"insert into tbl_bill (bill_id, customer_id, total, discount, date_time, generated_by) "
							+ "values (?, ?, ?, ?, ?, ?)");
			pst.setInt(1, bid);
			pst.setInt(2, cid);
			pst.setFloat(3, t);
			pst.setFloat(4, dis);
			pst.setString(5, Utilities.getCurrentDateTime());
			pst.setInt(6, gen_by);

			boolean r = pst.execute();

			if (r) {
				System.out.println("Bill inserted========================================");
			} else {
				System.err.println("Bill not inserted==============================================");
			}

			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

//	// insertion of customer data
//	public void addCustomer(String name, String address, String contact, String email) throws SQLException {
//
//		int rs = doesCustomerExist(contact);
//
//		if (rs > 0) {
//			System.out.println("customer already exists !!!");
//			JOptionPane.showMessageDialog(null, "Customer is already saved !!!", "Customer Exists",
//					JOptionPane.ERROR_MESSAGE);
//		} else {
//			System.out.println("False");
//			pst = con.prepareStatement("insert into tbl_customer(name, address, contact, email) values (?, ?, ?, ?)");
//			pst.setString(1, name);
//			pst.setString(2, address);
//			pst.setString(3, contact);
//			pst.setString(4, email);
//
//			int r = pst.executeUpdate();
//
//			if (r == 1) {
//				JOptionPane.showMessageDialog(null, "Customer saved successfully :)", "Saved",
//						JOptionPane.INFORMATION_MESSAGE);
//			} else {
//				JOptionPane.showMessageDialog(null, "Failed to save Customer :(", "Failed", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//	}
//
//	public int doesCustomerExist(String number) throws SQLException {
//		pst = con.prepareStatement("select cust_id from tbl_customer where contact = ?");
//		pst.setString(1, number);
//
//		rs = pst.executeQuery();
//
//		if (rs.next() == true) {
//			return rs.getInt("cust_id");
//		}
//		return 0;
//	}

//	public CustomerModel searchThisNumber(String number) throws SQLException {
//
//		pst = con.prepareStatement("select * from tbl_customer where contact = ?");
//		pst.setString(1, number);
//
//		rs = pst.executeQuery();
//
//		if (rs.next() == true) {
//			int id = rs.getInt("cust_id");
//			String name = rs.getString("name");
//			String address = rs.getString("address");
//			String contact = rs.getString("contact");
//			String email = rs.getString("email");
//			CustomerModel customerModel = new CustomerModel(id, name, address, contact, email);
////			CustomerModel customerModel = new CustomerModel(id, name, address, contact, email);
//			return customerModel;
//		}
//		return null;
//	}

//	public ArrayList<ProductModel> fetchProductList() throws SQLException {
//
//		ArrayList<ProductModel> productList = new ArrayList<ProductModel>();
//		pst = con.prepareStatement("select * from tbl_product");
//		rs = pst.executeQuery();
//
//		try {
//
//			while (rs.next()) {
//				id = rs.getInt("prod_id");
//				name = rs.getString("name");
//				rate = rs.getFloat("rate");
//				color = rs.getString("color");
//				category = rs.getInt("category");
//
//				ProductModel productModel = new ProductModel(id, name, rate, color, category);
//				productList.add(productModel);
//
//			}
////			System.out.println(productList.size());
////			for (ProductModel productModel : productList) {
////				System.out.println(productModel.getName());
////			}
//		} catch (Exception ex) {
//			System.out.println(ex);
//		}
//		return productList;
//
//	}

//	public ProductModel fetchProductByName(String n) throws SQLException {
//
//		ProductModel productModel = null;
//		pst = con.prepareStatement("select * from tbl_product where name = ?");
//		pst.setString(1, n);
//		rs = pst.executeQuery();
//
//		try {
//			if (rs.next()) {
//				int id = rs.getInt("prod_id");
//				String name = rs.getString("name");
//				float rate = rs.getFloat("rate");
//				String color = rs.getString("color");
//				int category = rs.getInt("category");
//
//				productModel = new ProductModel(id, name, rate, color, category);
//
//			}
//			System.out.println(productModel.toString());
//
//		} catch (Exception ex) {
//			System.out.println(ex);
//		}
////		con.close();
//		return productModel;
//	}

//	public void addToCart(CartModel model) {
//		System.out.println(model.toString());
//		try {
//			pst = con.prepareStatement(
//					"insert into tbl_cart(product_id, customer_id, qty, total, addedDate, finishingDate) values "
//							+ "(?, ?, ?, ?, ? ,?)");
//			pst.setInt(1, model.getProduct_id());
//			pst.setInt(2, model.getCustomer_id());
//			pst.setInt(3, model.getQty());
//			pst.setFloat(4, model.getTotal());
//			pst.setDate(5, (java.sql.Date) model.getAddedDate());
//			pst.setDate(6, (java.sql.Date) model.getFinishingDate());
//
//			int r = pst.executeUpdate();
//
//			if (r == 1) {
//				JOptionPane.showMessageDialog(null, "Product add to Cart !", "Saved", JOptionPane.INFORMATION_MESSAGE);
//			} else {
//				JOptionPane.showMessageDialog(null, "Failed to add Product too cart !", "Failed",
//						JOptionPane.ERROR_MESSAGE);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	// fetch all the products from tbl_product
//	public ResultSet fetchProduct() {
//		try {
//			pst = con.prepareStatement("select * from tbl_product");
//			rs = pst.executeQuery();
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return rs;
//	}

//	// insert the data into tbl_product column
//	public void addToProduct(String name, String rate, String color, int category) {
//		try {
////			System.out.println("add to product called");
//			pst = con.prepareStatement("insert into tbl_product(name, rate, color, category) values (?, ?, ?, ?)");
//			pst.setString(1, name);
//			pst.setFloat(2, Float.parseFloat(rate));
//			pst.setString(3, color);
//			pst.setInt(4, category);
//
//			int r = pst.executeUpdate();
//
//			if (r == 1) {
//				JOptionPane.showMessageDialog(null, "Product saved successfully :)", "Saved",
//						JOptionPane.INFORMATION_MESSAGE);
//			} else {
//				JOptionPane.showMessageDialog(null, "Failed to save Product :(", "Failed", JOptionPane.ERROR_MESSAGE);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

//	public int fetchCustomerId(String number) {
//		try {
//			pst = con.prepareStatement("select * from tbl_customer where contact = ?");
//			pst.setString(1, number);
//			rs = pst.executeQuery();
//
//			if (rs.next() == true) {
//				int id = rs.getInt("cust_id");
//				return id;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return 0;
//	}

//	public void insertOrder(int id, float total, int customer_id, Date oDate, Date dDate, int oStatus, int dStatus) {
//		try {
//			pst = con.prepareStatement("insert into tbl_order"
//					+ "(cart_id, total, ordered_by, ordered_date, delivery_date, order_status, delivery_status) "
//					+ "values (?, ?, ?, ?, ?, ?, ?)");
//
//			pst.setInt(1, id);
//			pst.setFloat(2, total);
//			pst.setInt(3, customer_id);
//			pst.setDate(4, (java.sql.Date) oDate);
//			pst.setDate(5, (java.sql.Date) dDate);
//			pst.setInt(6, oStatus);
//			pst.setInt(7, dStatus);
//
//			int r = pst.executeUpdate();
//
//			if (r == 1) {
//				JOptionPane.showMessageDialog(null, "Ordered successful :)", "Order", JOptionPane.INFORMATION_MESSAGE);
//			} else {
//				JOptionPane.showMessageDialog(null, "Order failed :(", "Order", JOptionPane.ERROR_MESSAGE);
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	public void recordTransaction(int cid, float amt, String datetime) {
//		try {
//			pst = con.prepareStatement(
//					"insert into tbl_transaction_record(customer_id, amount, date_time) values (?, ?, ?)");
//			pst.setInt(1, cid);
//			pst.setFloat(2, amt);
//			pst.setString(3, datetime);
//
//			int rs = pst.executeUpdate();
//			if (rs == 1) {
//				JOptionPane.showMessageDialog(null, "Transaction saved successfully :)", "Saved",
//						JOptionPane.INFORMATION_MESSAGE);
//			} else {
//				JOptionPane.showMessageDialog(null, "Failed to save Transaction :(", "Failed",
//						JOptionPane.ERROR_MESSAGE);
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}