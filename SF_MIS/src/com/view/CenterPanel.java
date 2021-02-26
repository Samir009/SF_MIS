package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.model.CartModel;
import com.model.CustomerModel;
import com.model.ProductModel;
import com.service.CartService;
import com.service.CartServiceImpl;
import com.service.CustomerService;
import com.service.CustomerServiceImpl;
import com.service.OrderService;
import com.service.OrderServiceImpl;
import com.service.ProductService;
import com.service.ProductServiceImpl;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;
import util.CustomColors;
import util.CustomFonts;
import util.DatabaseUtil;
import util.GlobalVariable;
import util.Utilities;

public class CenterPanel extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private Utilities utilities = new Utilities();

	private static JPanel layerPanel = new JPanel();// to make changeable layer this panel is created.
	private static JSplitPane verticalPane;

	// view order panel object
	private static ViewOrderPanel viewOrderPanel = new ViewOrderPanel();
	private static AddProductPanel addProductPanel = new AddProductPanel();
	private static EmpPanel empPanel = new EmpPanel();
	private static UserPanel userPanel = new UserPanel();

	private JPanel upPane, downPane, customerPane, productPane, paymentPane, productCartPane, cartPanel, orderPanel,
			searchPanel;
	private JLabel customerDetails, productDetails, paymentDetails, finishingDate, cName, cAddress, cContact, cEmail,
			pName, pQty, pRate, pColor, pCat, productCartList, advPayment;
	private JTextField cNametxt, cAddresstxt, cContacttxt, cEmailtxt, pNametxt, pQtytxt, pRatetxt, pColortxt, pCattxt,
			advPaymenttxt, searchtxt;

	private JList<Object> jList = new JList<>();

	private JButton addToCartBtn = new JButton("Add to Cart", new ImageIcon(utilities.getCartIcon()));
	private JButton clearCartBtn = new JButton("Clear Cart", new ImageIcon(utilities.getClearIcon()));
	private JButton orderBtn = new JButton("Order", new ImageIcon(utilities.getOrderIcon()));
	private JButton saveCustomer = new JButton("Save", new ImageIcon(utilities.getSaveIcon()));
	private JButton clearCustomer = new JButton("Clear", new ImageIcon(utilities.getClearIcon()));
	private static JTable cartTable;

	private JTable orderedTable;

	Border searchBorder = BorderFactory.createTitledBorder("Search by contact number");

	// date chooser
	private JDateChooser dateChooser = new JDateChooser();

	// for database connection
	DatabaseUtil db = new DatabaseUtil();
	private CustomerModel customer;

	DefaultListModel<Object> defaultListModel = new DefaultListModel<>();
	private ProductModel productModel;

	// stores customer id for cart
	private int c_id;

	// implementation of services
	private static CustomerService customerService = new CustomerServiceImpl();
	private static CartService cartService = new CartServiceImpl();
	private static ProductService productService = new ProductServiceImpl();
	private static OrderService orderService = new OrderServiceImpl();

	public CenterPanel() {
		setLayout(new BorderLayout());
		upPane = new JPanel();
		upPane.setLayout(new BorderLayout());

		createCustomerPanel();
		createProductPanel();
		createPayment();
		createCartListPanel();

		upPane.add(customerPane, BorderLayout.WEST);
		upPane.add(productPane, BorderLayout.CENTER);
		upPane.add(paymentPane, BorderLayout.EAST);
		upPane.add(productCartPane, BorderLayout.SOUTH);

		downPane = new JPanel();
		downPane.setLayout(new BorderLayout());

		createOrderedProductPanel();

		downPane.add(orderPanel, BorderLayout.CENTER);

		// split panel vertically
		verticalPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalPane.setResizeWeight(0.8); // 1 equal weights to top and bottom
		verticalPane.setLeftComponent(upPane);
		verticalPane.setRightComponent(downPane);

		layerPanel.setLayout(new BorderLayout());
		layerPanel.add(verticalPane, BorderLayout.CENTER);
		add(layerPanel);

		layerPanel.repaint();
		layerPanel.revalidate();
		setVisible(true);
	}

	// displays view order pane
	public static void viewOrderPane() {
		layerPanel.remove(verticalPane);
		layerPanel.remove(addProductPanel);
		layerPanel.remove(empPanel);
		layerPanel.remove(userPanel);
		layerPanel.add(viewOrderPanel);
		layerPanel.repaint();
		layerPanel.revalidate();
		layerPanel.setVisible(true);
	}

	// displays order detail pane
	public static void displayVerticalPane() {
		layerPanel.remove(viewOrderPanel);
		layerPanel.remove(addProductPanel);
		layerPanel.remove(empPanel);
		layerPanel.remove(userPanel);
		layerPanel.add(verticalPane);
		layerPanel.repaint();
		layerPanel.revalidate();
		layerPanel.setVisible(true);
	}

	// displays panel that is used to add product
	public static void displayAddProductPane() {
		layerPanel.remove(viewOrderPanel);
		layerPanel.remove(verticalPane);
		layerPanel.remove(empPanel);
		layerPanel.remove(userPanel);
		layerPanel.add(addProductPanel);
		layerPanel.repaint();
		layerPanel.revalidate();
		layerPanel.setVisible(true);
	}

	// displays employee panel to insert and update employee details
	public static void displayEmpPane() {
		layerPanel.remove(viewOrderPanel);
		layerPanel.remove(verticalPane);
		layerPanel.remove(addProductPanel);
		layerPanel.remove(userPanel);
		layerPanel.add(empPanel);
		layerPanel.repaint();
		layerPanel.revalidate();
		layerPanel.setVisible(true);
	}

	public static void displayUserPane() {
		layerPanel.remove(viewOrderPanel);
		layerPanel.remove(verticalPane);
		layerPanel.remove(addProductPanel);
		layerPanel.remove(empPanel);
		layerPanel.add(userPanel);
		layerPanel.setVisible(true);
	}

	private void createOrderedProductPanel() {
		orderPanel = new JPanel();
		orderPanel.setBackground(Color.decode(CustomColors.bgColor));
		orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane(getOrderListTable());

		JLabel orderedProductLbl = new JLabel("Ordered Product");
		orderedProductLbl.setFont(CustomFonts.font1);
		orderedProductLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

		orderPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		orderPanel.add(orderedProductLbl);
		orderPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		orderPanel.add(scrollPane, BorderLayout.CENTER);
	}

	private void createCartListPanel() {
		productCartPane = new JPanel();
		productCartPane.setBackground(Color.decode(CustomColors.bgColor));
		productCartPane.setLayout(new BorderLayout());
		productCartPane.setPreferredSize(new Dimension(0, 200));

		productCartList = new JLabel("Products in Cart");
		productCartList.setAlignmentX(Component.CENTER_ALIGNMENT);
		productCartList.setFont(CustomFonts.font1);

		JScrollPane sp = new JScrollPane(getCartListTable());
		cartPanel = new JPanel();
		cartPanel.setBackground(Color.decode(CustomColors.bgColor));
		cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));

		cartPanel.add(productCartList);
		cartPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		cartPanel.add(sp, BorderLayout.CENTER);
		productCartPane.add(cartPanel);
	}

	private JTable getCartListTable() {

		ResultSet rs = cartService.fetchCart();
		if (rs != null) {
			if (cartTable == null) {
				cartTable = new JTable();
				cartTable.setModel(DbUtils.resultSetToTableModel(rs));
				cartTable.setEnabled(false);

				cartTable.getTableHeader().setFont(CustomFonts.textFont);
				Utilities.setCellsAlignment(cartTable, SwingConstants.CENTER);
			}
			return cartTable;
		} else {
			return null;
		}
	}

	private JTable getOrderListTable() {

		return orderedTable;
	}

	private void createPayment() {
		paymentPane = new JPanel();
		paymentPane.setBackground(Color.decode(CustomColors.bgColor));
		paymentPane.setPreferredSize(new Dimension(250, 0));
		paymentPane.setLayout(null);

		paymentDetails = new JLabel("Payment");
		paymentDetails.setFont(CustomFonts.font1);
		paymentDetails.setBounds(10, 10, 200, 30);

		advPayment = new JLabel("Partial/ Full Payment");
		advPayment.setBounds(10, 40, 200, 20);
		advPaymenttxt = new JTextField();
		advPaymenttxt.setEditable(false);
		advPaymenttxt.setBounds(10, 60, 200, 20);

		// order button
		orderBtn.setBounds(45, 100, 130, 40);
		orderBtn.setBackground(Color.white);
		orderBtn.setForeground(Color.decode(CustomColors.blackColor));

		JLabel afterCartlbl = new JLabel("<html>After items added to cart click on <br/>'Order' Button</html>");
		afterCartlbl.setBounds(10, 150, 200, 25);

		paymentPane.add(paymentDetails);
		paymentPane.add(advPayment);
		paymentPane.add(advPaymenttxt);
		paymentPane.add(orderBtn);
		paymentPane.add(afterCartlbl);
	}

	// retrieves product details
	private void createProductPanel() {

		productPane = new JPanel();
		productPane.setLayout(null);
		productPane.setBackground(Color.decode(CustomColors.bgColor));
		productDetails = new JLabel("Product Details");
		productDetails.setFont(CustomFonts.font1);
		productDetails.setBounds(30, 10, 200, 20);

		pName = new JLabel("Particulars");
		pName.setBounds(30, 40, 300, 20);
		pQty = new JLabel("Qty");
		pQty.setBounds(240, 40, 50, 20);
		pRate = new JLabel("Rate");
		pRate.setBounds(290, 40, 60, 20);
		pColor = new JLabel("Color");
		pColor.setBounds(350, 40, 60, 20);
		pCat = new JLabel("Category");
		pCat.setBounds(410, 40, 100, 20);
		finishingDate = new JLabel("Finishing Date");
		finishingDate.setBounds(520, 40, 100, 20);

		pNametxt = new JTextField();
		pQtytxt = new JTextField();
		pRatetxt = new JTextField();
		pRatetxt.setEditable(false);
		pColortxt = new JTextField();
		pColortxt.setEditable(false);
		pCattxt = new JTextField();
		pCattxt.setEditable(false);

		pNametxt.setBounds(30, 60, 200, 25);
		pQtytxt.setBounds(240, 60, 40, 25);
		pRatetxt.setBounds(290, 60, 50, 25);
		pColortxt.setBounds(350, 60, 50, 25);
		pCattxt.setBounds(410, 60, 100, 25);
		dateChooser.setBounds(520, 60, 100, 25);

		// add to cart button
		addToCartBtn.setBounds(480, 100, 140, 40);
		addToCartBtn.setBackground(Color.white);
		addToCartBtn.setForeground(Color.decode(CustomColors.blackColor));

		// remove all the items from cart when this button is clicked
		clearCartBtn.setBounds(480, 150, 140, 40);
		clearCartBtn.setBackground(Color.white);
		clearCartBtn.setForeground(Color.decode(CustomColors.blackColor));

		// JList
		jList.setBounds(30, 100, 450, 220);
		bindData();

		productPane.add(productDetails);
		productPane.add(pName);
		productPane.add(pQty);
		productPane.add(pRate);
		productPane.add(pColor);
		productPane.add(pCat);
		productPane.add(pNametxt);
		productPane.add(pQtytxt);
		productPane.add(pRatetxt);
		productPane.add(pColortxt);
		productPane.add(pCattxt);
		productPane.add(finishingDate);
		productPane.add(dateChooser);
		productPane.add(addToCartBtn);
		productPane.add(clearCartBtn);
		productPane.add(jList);

		// search product by name display information
		pNametxt.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				searchProductFilter(pNametxt.getText().toString());

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	// creates customer registration form
	private void createCustomerPanel() {
		// customers details
		customerPane = new JPanel();
		customerPane.setBackground(Color.decode(CustomColors.bgColor));
		customerPane.setPreferredSize(new Dimension(250, 0));
		customerPane.setLayout(null);

		customerDetails = new JLabel("Customer Details");
		customerDetails.setFont(CustomFonts.font1);
		customerDetails.setBounds(10, 10, 200, 20);

		cName = new JLabel("Full Name");
		cName.setBounds(10, 40, 200, 20);
		cAddress = new JLabel("Address");
		cAddress.setBounds(10, 80, 230, 20);
		cContact = new JLabel("Contact");
		cContact.setBounds(10, 120, 230, 20);
		cEmail = new JLabel("Email");
		cEmail.setBounds(10, 160, 230, 20);
		saveCustomer.setBounds(70, 210, 130, 40);
		saveCustomer.setBackground(Color.white);
		saveCustomer.setForeground(Color.decode(CustomColors.blackColor));
		clearCustomer.setBounds(70, 260, 130, 40);
		clearCustomer.setBackground(Color.white);
		clearCustomer.setForeground(Color.decode(CustomColors.blackColor));

		cNametxt = new JTextField();
		cNametxt.setBounds(10, 60, 230, 25);
		cAddresstxt = new JTextField();
		cAddresstxt.setBounds(10, 100, 230, 25);
		cContacttxt = new JTextField();
		cContacttxt.setBounds(10, 140, 230, 25);
		cEmailtxt = new JTextField();
		cEmailtxt.setBounds(10, 180, 230, 25);

		// for searching
		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		searchPanel.setBounds(10, 310, 290, 50);
		searchPanel.setBorder(searchBorder);
		searchtxt = new JTextField();
//		searchtxt.setMaximumSize(new Dimension(100, 25));

		searchPanel.add(searchtxt);

		// add components to customer panel
		customerPane.add(customerDetails);
		customerPane.add(cName);
		customerPane.add(cNametxt);
		customerPane.add(cAddress);
		customerPane.add(cAddresstxt);
		customerPane.add(cContact);
		customerPane.add(cContacttxt);
		customerPane.add(cEmail);
		customerPane.add(cEmailtxt);
		customerPane.add(saveCustomer);
		customerPane.add(clearCustomer);
		customerPane.add(searchPanel);

		// implement action listener
		searchtxt.addKeyListener(new KeyAdapter() { // search customer data according to given number
			@Override
			public void keyReleased(KeyEvent e) {
				String number = searchtxt.getText().toString();

				try {

					customer = customerService.fetchCustByNum(number);

					if (customer != null) {
						c_id = customer.getId();
						cNametxt.setText(customer.getName());
						cAddresstxt.setText(customer.getAddress());
						cContacttxt.setText(customer.getContact());
						cEmailtxt.setText(customer.getEmail());
					} else {
						cNametxt.setText("");
						cAddresstxt.setText("");
						cContacttxt.setText("");
						cEmailtxt.setText("");
						System.out.println("Contact not found !!!");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		clearCustomer.addActionListener(this);
		saveCustomer.addActionListener(this);
		addToCartBtn.addActionListener(this);
		clearCartBtn.addActionListener(this);
		orderBtn.addActionListener(this);

		jList.addMouseListener(this);

	}

	private ArrayList<ProductModel> getProductList() {

		List<ProductModel> list = productService.fetchProductList();

		if (list != null) {
			return (ArrayList<ProductModel>) list;
		} else {
			System.out.println("product list is null");
			return null;
		}
	}

	private void bindData() {

		for (ProductModel iterable_element : getProductList()) {
			defaultListModel.addElement(iterable_element.getName());
		}
		jList.setModel(defaultListModel);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void searchProductFilter(String searchTerm) {
		DefaultListModel<Object> filteredItems = new DefaultListModel<>();

		for (ProductModel iterable_element : getProductList()) {
			String starName = iterable_element.getName().toLowerCase();
			if (starName.contains(searchTerm.toLowerCase())) {
				filteredItems.addElement(iterable_element.getName());
			}
		}
		defaultListModel = filteredItems;
		jList.setModel(defaultListModel);

	}

	private void checkCart() {

		if (pNametxt.getText().toString().isEmpty() || pQtytxt.getText().toString().isEmpty()
				|| dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Field may be empty !", "Empty field", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			addToCart();
		}
	}

	private void addToCart() {
		int pid = Integer.valueOf(productModel.getProd_id());
		int qty = Integer.valueOf(pQtytxt.getText().toString());
		float rate = productModel.getRate();
		float total = qty * rate;

		if (cContacttxt.getText().isEmpty()) {

			cContacttxt.setBackground(Color.decode(CustomColors.errorTextFieldBg));

			JOptionPane.showMessageDialog(null, "Insert contact number of customer !", "Empty field",
					JOptionPane.ERROR_MESSAGE);
		} else {

			c_id = customerService.fetchCustomerId(cContacttxt.getText().toString());
			System.out.println(c_id);

			if (c_id == 0) {
				JOptionPane.showMessageDialog(null, "Customer not found according to contact number !",
						"Customer Not Found", JOptionPane.ERROR_MESSAGE);
			} else {
				CartModel mCartModel = new CartModel(pid, c_id, qty, total, Utilities.getCurrentDate(),
						Utilities.getSqlDate(dateChooser.getDate()), GlobalVariable.cartNotOrdered);

				cartService.addToCart(mCartModel);

				setProdDetailsBlank();
			}
		}
		refreshCartTable();
	}

	// it refreshes cart table when customer add product to cart
	public static void refreshCartTable() {
		try {
			ResultSet rs = cartService.fetchCart();
			if (rs != null) {
				cartTable.setModel(DbUtils.resultSetToTableModel(rs));
				cartTable.setEnabled(false);

				cartTable.getTableHeader().setFont(CustomFonts.textFont);
				Utilities.setCellsAlignment(cartTable, SwingConstants.CENTER);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setProdDetailsBlank() {
		pNametxt.setText("");
		pQtytxt.setText("");
		pRatetxt.setText("");
		pColortxt.setText("-");
		pCattxt.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// saves the details of customers
		if (e.getSource() == saveCustomer) {
			try {
				if (cNametxt.getText().isEmpty()) {
					cNametxt.setBackground(Color.decode(CustomColors.errorTextFieldBg));
				} else if (cAddresstxt.getText().isEmpty()) {
					cAddresstxt.setBackground(Color.decode(CustomColors.errorTextFieldBg));
				} else if (cContacttxt.getText().isEmpty()) {
					cContacttxt.setBackground(Color.decode(CustomColors.errorTextFieldBg));
				} else if (cEmailtxt.getText().isEmpty()) {
					cEmailtxt.setBackground(Color.decode(CustomColors.errorTextFieldBg));
				}

				if (cNametxt.getText().isEmpty() || cAddresstxt.getText().isEmpty() || cContacttxt.getText().isEmpty()
						|| cEmailtxt.getText().isEmpty()) {

					JOptionPane.showMessageDialog(null, "Field may be empty !", "Empty field",
							JOptionPane.ERROR_MESSAGE);

				} else {
					com.model.CustomerModel model = new com.model.CustomerModel(cNametxt.getText(),
							cAddresstxt.getText(), cContacttxt.getText(), cEmailtxt.getText());

					customerService.addCustomer(model);
				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		// clears all the fields of customer details
		if (e.getSource() == clearCustomer) {
			clearCustomerDetails();

		}

		if (e.getSource() == addToCartBtn) {
			System.out.println("add to cart ");
			checkCart();
		}

		// removes all the items from the cart
		if (e.getSource() == clearCartBtn) {
			if (c_id > 0) {
				cartService.deleteFromCartById(c_id);
				clearCustomerDetails();
				c_id = 0;
				refreshCartTable();
			} else {
				JOptionPane.showMessageDialog(null, "Cart is empty", "Empty", JOptionPane.ERROR_MESSAGE);
			}

		}

		// ORDER BUTTON
		if (e.getSource() == orderBtn) {

			try {
				if (c_id > 0) {

					String ostat = cartService.getCartOrderStat(c_id);
					int paystat = GlobalVariable.notPaid;

					if (ostat.equals(GlobalVariable.cartNotOrdered)) {
						fetchCartById(c_id, paystat);
					} else {
						JOptionPane.showMessageDialog(null, "Cart is empty", "Empty Cart", JOptionPane.ERROR_MESSAGE);
					}

				} else {
					System.err.println("Customer not found");
					JOptionPane.showMessageDialog(null, "Customer not found", "Unknown customer",
							JOptionPane.ERROR_MESSAGE);
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			advPaymenttxt.setText("");
			refreshCartTable();
		}

	}

	private void clearCustomerDetails() {
		cNametxt.setText("");
		cAddresstxt.setText("");
		cContacttxt.setText("");
		cEmailtxt.setText("");
		searchtxt.setText("");

		cNametxt.setBackground(Color.white);
		cAddresstxt.setBackground(Color.white);
		cContacttxt.setBackground(Color.white);
		cEmailtxt.setBackground(Color.white);
	}

	private void fetchCartById(int id, int pstat) throws SQLException {

		ResultSet res = cartService.fetchCartById(id);
		boolean b = false;
		while (res.next()) {

			if (res.getString("ordered").equals("n")) {
				b = orderService.insertOrder(res, pstat);
			}
		}
		if (b) {
			cartService.setCartOrder(c_id);
			JOptionPane.showMessageDialog(null, "Ordered successfully :)", "Successful",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Cart is empty", "Empty", JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JList<String> theList = (JList<String>) e.getSource();
		if (e.getClickCount() == 2) {

			int index = theList.locationToIndex(e.getPoint());
			if (index >= 0) {
				Object o = theList.getModel().getElementAt(index);
				try {

					productModel = productService.fetchProductByName(o.toString());

					pNametxt.setText(productModel.getName());
					pRatetxt.setText(String.valueOf(productModel.getRate()));
					pColortxt.setText(productModel.getColor());
					pCattxt.setText(String.valueOf(productModel.getCategory()));

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
