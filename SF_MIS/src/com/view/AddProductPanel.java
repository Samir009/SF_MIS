package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.model.ProductModel;
import com.service.ProductService;
import com.service.ProductServiceImpl;

import net.proteanit.sql.DbUtils;
import util.CustomColors;
import util.CustomFonts;
import util.DatabaseUtil;
import util.Utilities;

public class AddProductPanel extends JPanel implements ActionListener {

	private Utilities utilities = new Utilities();

	private DatabaseUtil db = new DatabaseUtil();
	private ArrayList<String> catList = new ArrayList<>();

	private static final long serialVersionUID = 1L;

	private JPanel productAdd, categoryAdd;
	private JLabel productDetails, pName, pRate, pColor, pCat, pCategory, availabeProducts, cCatlbl, avlCat, lblEdit,
			lblEditRate;
	private JTextField pNametxt, pRatetxt, pColortxt, pCategorytxt, idtxt, ratetxt, nametxt, cattxt, colortxt;
	private JComboBox<Object> categoryCb;

	private JButton btnAddProduct, btnAddCategory, btnEditRate, btnEditName, btnEditCat, btnEditColor;

	private JScrollPane productScrollPane, categoryScrollPane;
	private JTable productTable, avlCatTbl;

	private static ProductService productService = new ProductServiceImpl();

	AddProductPanel() {
		setLayout(new BorderLayout());
		if (db.retreiveCategoryName() != null) {
			catList = db.retreiveCategoryName();
			for (String string : catList) {
				System.out.println(string);
			}
		} else {
			System.out.println("Category table is empty");
		}

		addProductPanel();

		addCategoryPanel();

		add(productAdd, BorderLayout.CENTER);
		add(categoryAdd, BorderLayout.EAST);

		btnAddProduct.addActionListener(this);
		btnAddCategory.addActionListener(this);
		btnEditRate.addActionListener(this);

		setVisible(true);
	}

	private void addCategoryPanel() {
		// category add panel
		categoryAdd = new JPanel();
		categoryAdd.setBackground(Color.decode(CustomColors.bgColor));
		categoryAdd.setLayout(null);
		categoryAdd.setPreferredSize(new Dimension(300, 0));

		pCategory = new JLabel("Category");
		pCategory.setBounds(10, 10, 100, 25);
		pCategory.setFont(CustomFonts.font1);
		cCatlbl = new JLabel("Insert category name");
		cCatlbl.setBounds(10, 40, 300, 25);
		pCategorytxt = new JTextField();
		pCategorytxt.setBounds(10, 70, 120, 25);
		btnAddCategory = new JButton("", new ImageIcon(utilities.getInsertIcon()));
		btnAddCategory.setBackground(Color.white);
		btnAddCategory.setBounds(150, 60, 80, 40);
		btnAddCategory.setForeground(Color.white);

		// available categories
		avlCat = new JLabel("Available Categories");
		avlCat.setFont(CustomFonts.font1);
		avlCat.setBounds(10, 120, 200, 25);

		categoryScrollPane = new JScrollPane(getCategoryListTable());
		categoryScrollPane.setBounds(10, 150, 200, 400);
		categoryAdd.add(categoryScrollPane);

		categoryAdd.add(avlCat);
		categoryAdd.add(pCategory);
		categoryAdd.add(cCatlbl);
		categoryAdd.add(pCategorytxt);
		categoryAdd.add(btnAddCategory);

	}

	private void addProductPanel() {
		productAdd = new JPanel();
		productAdd.setLayout(null);
		productAdd.setBackground(Color.decode(CustomColors.bgColor));

		productDetails = new JLabel("Insert Product Details");
		productDetails.setFont(CustomFonts.font1);
		productDetails.setBounds(10, 10, 350, 25);

		pName = new JLabel("Product name");
		pRate = new JLabel("Rate");
		pColor = new JLabel("Color");
		pCat = new JLabel("Category");

		pName.setBounds(10, 40, 100, 25);
		pRate.setBounds(240, 40, 50, 25);
		pColor.setBounds(340, 40, 50, 25);
		pCat.setBounds(440, 40, 100, 25);

		pNametxt = new JTextField();
		pRatetxt = new JTextField();
		pColortxt = new JTextField("-");
		categoryCb = null;

		pNametxt.setBounds(10, 70, 200, 25);
		pRatetxt.setBounds(240, 70, 50, 25);
		pColortxt.setBounds(340, 70, 50, 25);

		btnAddProduct = new JButton("Add product", new ImageIcon(utilities.getAddIcon()));
		btnAddProduct.setBounds(540, 60, 150, 40);
		btnAddProduct.setBackground(Color.white);
		btnAddProduct.setForeground(Color.decode(CustomColors.blackColor));

		availabeProducts = new JLabel("Available Products");
		availabeProducts.setFont(CustomFonts.font1);
		availabeProducts.setBounds(10, 120, 300, 25);

		productScrollPane = new JScrollPane(getProductListTable());
		productScrollPane.setBounds(10, 150, 700, 400);

		productAdd.add(productDetails);
		productAdd.add(pName);
		productAdd.add(pRate);
		productAdd.add(pColor);
		productAdd.add(pCat);
		productAdd.add(pNametxt);
		productAdd.add(pRatetxt);
		productAdd.add(pColortxt);
		productAdd.add(loadCatJCB());
		productAdd.add(btnAddProduct);
		productAdd.add(availabeProducts);
		productAdd.add(productScrollPane);

		// edit price of product
		lblEdit = new JLabel("Edit Product");
		lblEdit.setFont(CustomFonts.font1);
		lblEdit.setBounds(750, 120, 200, 25);

		lblEditRate = new JLabel("Edit according to need");
		lblEditRate.setBounds(750, 150, 200, 25);
		JLabel id = new JLabel("Id:");
		id.setBounds(750, 180, 30, 25);
		idtxt = new JTextField(25);
		idtxt.setBounds(820, 180, 50, 25);

		JLabel name = new JLabel("Name:");
		name.setBounds(750, 210, 100, 25);
		nametxt = new JTextField();
		nametxt.setEditable(false);
		nametxt.setBounds(820, 210, 50, 25);
		btnEditName = new JButton("Ok");
		btnEditName.setEnabled(false);
		btnEditName.setBounds(880, 210, 50, 25);

		JLabel rate = new JLabel("Rate:");
		rate.setBounds(750, 240, 100, 25);
		ratetxt = new JTextField();
		ratetxt.setBounds(820, 240, 50, 25);
		btnEditRate = new JButton("Ok");
		btnEditRate.setBounds(880, 240, 50, 25);
//		btnEditRate.setBackground(Color.decode(CustomColors.greenColor));
//		btnEditRate.setForeground(Color.white);

		JLabel color = new JLabel("Color:");
		color.setBounds(750, 270, 100, 25);
		colortxt = new JTextField();
		colortxt.setEditable(false);
		colortxt.setBounds(820, 270, 50, 25);
		btnEditColor = new JButton("Ok");
		btnEditColor.setEnabled(false);
		btnEditColor.setBounds(880, 270, 50, 25);

		JLabel category = new JLabel("Category:");
		category.setBounds(750, 300, 100, 25);
		cattxt = new JTextField();
		cattxt.setEditable(false);
		cattxt.setBounds(820, 300, 50, 25);
		btnEditCat = new JButton("Ok");
		btnEditCat.setEnabled(false);
		btnEditCat.setBounds(880, 300, 50, 25);

//		productAdd.add(avlCat);
		productAdd.add(lblEdit);
		productAdd.add(lblEditRate);
		productAdd.add(id);
		productAdd.add(idtxt);
		productAdd.add(rate);
		productAdd.add(ratetxt);
		productAdd.add(btnEditRate);
		productAdd.add(name);
		productAdd.add(nametxt);
		productAdd.add(btnEditName);
		productAdd.add(color);
		productAdd.add(colortxt);
		productAdd.add(btnEditColor);
		productAdd.add(category);
		productAdd.add(cattxt);
		productAdd.add(btnEditCat);
	}

	private JComboBox<Object> loadCatJCB() {

		if (catList != null && categoryCb == null) {
			categoryCb = new JComboBox<Object>();
			categoryCb.setModel(new DefaultComboBoxModel<>(catList.toArray()));
			categoryCb.setBounds(440, 70, 80, 25);
		}
		return categoryCb;
	}

	private void checkProductDetails() {
		String name = pNametxt.getText().toString();
		String rate = pRatetxt.getText().toString();
		String color = pColortxt.getText().toString();
		String cat = categoryCb.getSelectedItem().toString();
		int cid = db.getCatId(cat);
		System.out.println("cid : " + cid + cat);
		if (name.isEmpty() || rate.isEmpty() || color.isEmpty() || cat.isEmpty()) {
			JOptionPane.showMessageDialog(null, "All fields are required", "Empty fields", JOptionPane.ERROR_MESSAGE);
		} else {
			if (cid > 0) {
				System.err.println("Product inserted: " + name + rate + color + cid);
				ProductModel model = new ProductModel(cid, Float.valueOf(rate), name, color);
				boolean rs = productService.addProduct(model);
				if (rs)
					refreshProductTables();
				else
					System.err.println("Failed to refresh prouduct table");

			} else {
				System.err.println("Category not found to insert into product table");
			}

		}
	}

	private void refreshProductTables() {

		ResultSet rs = productService.fetchProduct();
		if (rs != null) {

			productTable.setModel(DbUtils.resultSetToTableModel(rs));
			productTable.setEnabled(false);

			productTable.getTableHeader().setFont(CustomFonts.textFont);
			Utilities.setCellsAlignment(productTable, SwingConstants.CENTER);
		} else {
			System.out.println("Fetch prouct result set is null");
		}
	}

	private void refreshCategoryTable() {
		avlCatTbl.setModel(DbUtils.resultSetToTableModel(db.fetchCategory()));
		avlCatTbl.setEnabled(false);

		avlCatTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(avlCatTbl, SwingConstants.CENTER);
	}

	private JTable getProductListTable() {

		ResultSet rs = productService.fetchProduct();
		if (rs != null) {
			productTable = new JTable();
			productTable.setModel(DbUtils.resultSetToTableModel(rs));
			productTable.setEnabled(false);

			productTable.getTableHeader().setFont(CustomFonts.textFont);
			Utilities.setCellsAlignment(productTable, SwingConstants.CENTER);

			return productTable;
		} else {
			System.out.println("Fetch prouct result set is null");
			return null;
		}
	}

	private void refreshCatList() {
		catList = db.retreiveCategoryName();
		categoryCb.setModel(new DefaultComboBoxModel<>(catList.toArray()));
	}

	private JTable getCategoryListTable() {

		if (avlCatTbl == null) {
			avlCatTbl = new JTable();
			avlCatTbl.setModel(DbUtils.resultSetToTableModel(db.fetchCategory()));
			avlCatTbl.setEnabled(false);

			avlCatTbl.getTableHeader().setFont(CustomFonts.textFont);
			Utilities.setCellsAlignment(avlCatTbl, SwingConstants.CENTER);
		}
		return avlCatTbl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddProduct) {
			System.out.println("Add product clicked");
			checkProductDetails();
		}
		if (e.getSource() == btnAddCategory) {
			System.out.println("Add category clicked");
			if (!pCategorytxt.getText().toString().isEmpty()) {
				db.insertCategory(pCategorytxt.getText().toString());
				pCategorytxt.setText("");
				refreshCategoryTable();
				refreshCatList();
			} else {
				JOptionPane.showMessageDialog(null, "Category field is empty", "Empty field",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		if (e.getSource() == btnEditRate) {

			if (idtxt.getText().isEmpty() || ratetxt.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Field may be empty", "Empty field", JOptionPane.ERROR_MESSAGE);
			} else {

				int id = Integer.parseInt(idtxt.getText());
				float rate = Float.parseFloat(ratetxt.getText());

				boolean rs = productService.editProductRate(id, rate);
				if (rs)
					refreshProductTables();
				else
					System.err.println("Failed to refresh prouduct table");

				idtxt.setText("");
				ratetxt.setText("");
			}
		}
	}
}
