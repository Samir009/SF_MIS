package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import util.ScreenDimension;
import util.Utilities;

public class CashierFrame extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private Utilities utilities = new Utilities();

	private JMenuBar mb = new JMenuBar();
	private JMenu menu;
	private JMenuItem logoutMenuItem;

	private GridBagConstraints gbcTakeOrder = new GridBagConstraints();// TODO make other gcb for buttons
	private GridBagConstraints gbcViewOrder = new GridBagConstraints();
	private GridBagConstraints gbcGenBill = new GridBagConstraints();
	private int top = 3, left = 20, bottom = 3, right = 20;
	private Insets i = new Insets(top, left, bottom, right);

	private Image orderIcon = new ImageIcon(this.getClass().getResource("/order.png")).getImage();
	private Image viewOrderIcon = new ImageIcon(this.getClass().getResource("/view.png")).getImage();
	private Image genBillIcon = new ImageIcon(this.getClass().getResource("/bill.png")).getImage();

	private static JPanel layerPanel = new JPanel();// to make changeable layer this panel is created.
	private JPanel northPane, westPane, eastPane;

	// view order panel object
	private static ViewOrderPanel viewOrderPanel = new ViewOrderPanel();
	private static GenerateBillPanel generateBillPanel = new GenerateBillPanel();

	private static JPanel addOrderPane;

	private JPanel customerPane;

	private JPanel productPane;

	private JPanel paymentPane;

	private JPanel productCartPane;

	private JPanel cartPanel;

	private JPanel searchPanel;
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
	private JButton btnTakeOrder, btnGenBill, btnViewOrder;

	private static JTable cartTable;

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

	public static void main(String[] args) {
		new CashierFrame();
	}

	public CashierFrame() {

		System.err.println("Current user role is: " + GlobalVariable.currentUserId);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(ScreenDimension.getScreenWidth(), ScreenDimension.getScreenHeight());

		menu = new JMenu("User");
		logoutMenuItem = new JMenuItem("Logout");
		menu.add(logoutMenuItem);
		mb.add(menu);

		addOrderPane = new JPanel();
		addOrderPane.setLayout(new BorderLayout());

		addOrderPane.add(createCustomerPanel(), BorderLayout.WEST);
		addOrderPane.add(createProductPanel(), BorderLayout.CENTER);
		addOrderPane.add(createPayment(), BorderLayout.EAST);
		addOrderPane.add(createCartListPanel(), BorderLayout.SOUTH);

		layerPanel.setLayout(new BorderLayout());
		layerPanel.add(EastPanel(), BorderLayout.EAST);
		layerPanel.add(WestPanel(), BorderLayout.WEST);
		layerPanel.add(NorthPanel(), BorderLayout.NORTH);
		layerPanel.add(addOrderPane, BorderLayout.CENTER);

		add(mb);
		add(layerPanel);
		setJMenuBar(mb);
		setVisible(true);
		repaint();
		revalidate();

		btnTakeOrder.addActionListener(this);
		btnViewOrder.addActionListener(this);
		btnGenBill.addActionListener(this);
		logoutMenuItem.addActionListener(this);
	}

	// displays order detail pane
	public static void viewOrderPane() {
		layerPanel.remove(addOrderPane);
		layerPanel.remove(generateBillPanel);
		layerPanel.add(viewOrderPanel);
		layerPanel.repaint();
		layerPanel.revalidate();
		return;
	}

	// take order detail pane
	public static void takeOrderPane() {
		layerPanel.remove(viewOrderPanel);
		layerPanel.remove(generateBillPanel);
		layerPanel.add(addOrderPane);
		layerPanel.repaint();
		layerPanel.revalidate();
		return;
	}

	// generate bills pane
	public static void generateBillPane() {
		layerPanel.remove(viewOrderPanel);
		layerPanel.remove(addOrderPane);
		layerPanel.add(generateBillPanel);
		layerPanel.repaint();
		layerPanel.revalidate();
		return;
	}

	// creates customer registration form
	private JPanel createCustomerPanel() {
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
		saveCustomer.setBounds(55, 210, 130, 40);
		saveCustomer.setBackground(Color.white);
		saveCustomer.setForeground(Color.decode(CustomColors.blackColor));
		clearCustomer.setBounds(55, 260, 130, 40);
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

		return customerPane;

	}

	// retrieves product details
	private JPanel createProductPanel() {

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
		addToCartBtn.setBounds(480, 100, 150, 40);
		addToCartBtn.setBackground(Color.white);
		addToCartBtn.setForeground(Color.decode(CustomColors.blueColor));

		// remove all the items from cart when this button is clicked
		clearCartBtn.setBounds(480, 150, 150, 40);
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

		return productPane;
	}

	private JPanel createPayment() {
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
		orderBtn.setBounds(48, 90, 130, 40);
		orderBtn.setBackground(Color.white);
		orderBtn.setForeground(Color.decode(CustomColors.blackColor));

		JLabel afterCartlbl = new JLabel("<html>After items added to cart click on <br/>'Order' Button</html>");
		afterCartlbl.setBounds(10, 150, 200, 25);

		paymentPane.add(paymentDetails);
		paymentPane.add(advPayment);
		paymentPane.add(advPaymenttxt);
		paymentPane.add(orderBtn);
		paymentPane.add(afterCartlbl);

		return paymentPane;
	}

	private JPanel createCartListPanel() {
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

		return productCartPane;
	}

	private void bindData() {

		for (ProductModel iterable_element : getProductList()) {
			defaultListModel.addElement(iterable_element.getName());
		}
		jList.setModel(defaultListModel);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

	public void setProdDetailsBlank() {
		pNametxt.setText("");
		pQtytxt.setText("");
		pRatetxt.setText("");
		pColortxt.setText("-");
		pCattxt.setText("");
	}

	// panel of top navigating buttons
	private JPanel NorthPanel() {
		northPane = new JPanel();
		northPane.setPreferredSize(new Dimension(0, 100));
		northPane.setLayout(new GridBagLayout());

		btnTakeOrder = new JButton("Take Order", new ImageIcon(orderIcon));
		btnTakeOrder.setFont(CustomFonts.font1);
		btnTakeOrder.setBackground(Color.decode(CustomColors.bgColor));
		gbcTakeOrder.insets = i;
		gbcTakeOrder.gridx = 0;
		gbcTakeOrder.gridy = 0;
		northPane.add(btnTakeOrder, gbcTakeOrder);

		btnViewOrder = new JButton("View Order", new ImageIcon(viewOrderIcon));
		btnViewOrder.setFont(CustomFonts.font1);
		btnViewOrder.setBackground(Color.decode(CustomColors.bgColor));
		gbcViewOrder.insets = i;
		gbcViewOrder.gridx = 2;
		gbcViewOrder.gridy = 0;
		northPane.add(btnViewOrder, gbcViewOrder);

		btnGenBill = new JButton("Generate Bill", new ImageIcon(genBillIcon));
		btnGenBill.setFont(CustomFonts.font1);
		btnGenBill.setBackground(Color.decode(CustomColors.bgColor));
		gbcGenBill.insets = i;
		gbcGenBill.gridx = 4;
		gbcGenBill.gridy = 0;
		northPane.add(btnGenBill, gbcGenBill);

		return northPane;
	}

	private JPanel WestPanel() {
		westPane = new JPanel();
		westPane.setPreferredSize(new Dimension(150, 0));
		westPane.setBackground(Color.decode(CustomColors.bgColor));
		return westPane;
	}

	private JPanel EastPanel() {
		eastPane = new JPanel();
		eastPane.setPreferredSize(new Dimension(150, 0));
		eastPane.setBackground(Color.decode(CustomColors.bgColor));
		return eastPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnViewOrder) {
			viewOrderPane();
		}
		if (e.getSource() == btnTakeOrder) {
			takeOrderPane();
		}
		if (e.getSource() == btnGenBill) {
			generateBillPane();
		}
		if (e.getSource() == logoutMenuItem) {
			GlobalVariable.currentUserId = 0;
			GlobalVariable.currentUser = "";
			GlobalVariable.userRole = "";
			new LoginFrame().setVisible(true);
			dispose();
		}

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
