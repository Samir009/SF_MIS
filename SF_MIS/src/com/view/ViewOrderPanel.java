package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.model.CustomerModel;
import com.model.PaymentModel;
import com.model.TransactionModel;
import com.service.CustomerService;
import com.service.CustomerServiceImpl;
import com.service.OrderService;
import com.service.OrderServiceImpl;
import com.service.PaymentService;
import com.service.PaymentServiceImpl;
import com.service.TransactionService;
import com.service.TransactionServiceImpl;

import net.proteanit.sql.DbUtils;
import util.CustomColors;
import util.CustomFonts;
import util.GlobalVariable;
import util.ScreenDimension;
import util.Utilities;

public class ViewOrderPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Utilities utilities = new Utilities();

	private JPanel headerPanel, eastPanel, customerPanel, productPanel;

	private static JTextField searchBar, amttxt, duetxt, advancetxt, cNametxt, cAddresstxt, cContacttxt, cEmailtxt; // =
																													// new
	// JTextField();
	private JButton searchBtn, payBtn, orderReadyBtn, deliverBtn;
	private JLabel advancelbl, paymentlbl, totallbl, duelbl, cName, cAddress, cContact, cEmail, customerDetails,
			orderProdlbl, orderStat, deliStat, ordProdDetlbl;
	private JTable productTable, ordDetTbl;

	private JCheckBox chOrderReady, chDelivered;

	// implementations of services
	private OrderService orderService = new OrderServiceImpl();
	private CustomerService customerService = new CustomerServiceImpl();
	private PaymentService paymentService = new PaymentServiceImpl();
	private TransactionService transactionService = new TransactionServiceImpl();

	private static ResultSet rs, rset;

	// customer id;
	private int cid = 0;
	private float sumOfTOPNpaid = 0;

	public ViewOrderPanel() {
		setBackground(Color.decode(CustomColors.bgColor));
		setLayout(new BorderLayout());

		add(getHeader(), BorderLayout.NORTH);
		add(createPaymentPanel(), BorderLayout.EAST);
		add(createCustomerPanel(), BorderLayout.WEST);
		add(createProductPanel(), BorderLayout.CENTER);

		payBtn.addActionListener(this);
		orderReadyBtn.addActionListener(this);
		deliverBtn.addActionListener(this);

		setVisible(true);
		repaint();
		revalidate();
	}

	private JPanel createPaymentPanel() {

		eastPanel = new JPanel();
		eastPanel.setBackground(Color.decode(CustomColors.bgColor));
		eastPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setPreferredSize(new Dimension(200, 0));

		paymentlbl = new JLabel("Payment Details");
		paymentlbl.setFont(CustomFonts.font1);
		paymentlbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		totallbl = new JLabel("Insert amount");
		totallbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		duelbl = new JLabel("Due");
		duelbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		advancelbl = new JLabel("Advance");
		advancelbl.setAlignmentX(Component.CENTER_ALIGNMENT);

		amttxt = new JTextField();
		amttxt.setMaximumSize(new Dimension(150, 25));
		amttxt.setAlignmentX(Component.CENTER_ALIGNMENT);
		duetxt = new JTextField();
		duetxt.setMaximumSize(new Dimension(150, 25));
		duetxt.setEditable(false);
		duetxt.setAlignmentX(Component.CENTER_ALIGNMENT);
		advancetxt = new JTextField();
		advancetxt.setMaximumSize(new Dimension(150, 25));
		advancetxt.setEditable(false);
		advancetxt.setAlignmentX(Component.CENTER_ALIGNMENT);

		orderStat = new JLabel("Order Status");
		orderStat.setFont(CustomFonts.font1);
		orderStat.setAlignmentX(Component.CENTER_ALIGNMENT);
		chOrderReady = new JCheckBox("Order/s ready");
		chOrderReady.setAlignmentX(Component.CENTER_ALIGNMENT);

		deliStat = new JLabel("Delivery Status");
		deliStat.setFont(CustomFonts.font1);
		deliStat.setAlignmentX(Component.CENTER_ALIGNMENT);
		chDelivered = new JCheckBox("Delivered");
		chDelivered.setAlignmentX(Component.CENTER_ALIGNMENT);

		// pay btn
		payBtn = new JButton("Pay", new ImageIcon(utilities.getMoneyIcon()));
		payBtn.setBackground(Color.white);
		payBtn.setForeground(Color.decode(CustomColors.blackColor));
		payBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

		orderReadyBtn = new JButton("Ready", new ImageIcon(utilities.getReadyIcon()));
		orderReadyBtn.setBackground(Color.white);
		orderReadyBtn.setForeground(Color.decode(CustomColors.blackColor));
		orderReadyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

		deliverBtn = new JButton("Deliver", new ImageIcon(utilities.getDeliverIcon()));
		deliverBtn.setBackground(Color.white);
		deliverBtn.setForeground(Color.decode(CustomColors.blackColor));
		deliverBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

		eastPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		eastPanel.add(paymentlbl);
		eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		eastPanel.add(advancelbl);
		eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		eastPanel.add(advancetxt);
		eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		eastPanel.add(duelbl);
		eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		eastPanel.add(duetxt);
		eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		eastPanel.add(totallbl);
		eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		eastPanel.add(amttxt);
		eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		eastPanel.add(payBtn);
		eastPanel.add(Box.createRigidArea(new Dimension(10, 20)));

		eastPanel.add(orderStat);
		eastPanel.add(chOrderReady);
		eastPanel.add(Box.createRigidArea(new Dimension(10, 5)));
		eastPanel.add(orderReadyBtn);
		eastPanel.add(Box.createRigidArea(new Dimension(10, 10)));

		eastPanel.add(deliStat);
		eastPanel.add(chDelivered);
		eastPanel.add(Box.createRigidArea(new Dimension(10, 5)));
		eastPanel.add(deliverBtn);
		eastPanel.add(Box.createRigidArea(new Dimension(10, 20)));

		eastPanel.add(Box.createRigidArea(new Dimension(10, 10)));

		return eastPanel;

	}

	private JPanel createProductPanel() {
		// product details panel
		productPanel = new JPanel();
		productPanel.setBackground(Color.decode(CustomColors.bgColor));
		productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
		productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		orderProdlbl = new JLabel("Ordered product/s (Not paid)");
		orderProdlbl.setFont(CustomFonts.font1);
		orderProdlbl.setAlignmentX(CENTER_ALIGNMENT);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(getOrderListTable());

		scrollPane.setBackground(Color.decode(CustomColors.bgColor));

		ordProdDetlbl = new JLabel("Ordered product details");
		ordProdDetlbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		ordProdDetlbl.setFont(CustomFonts.font1);

		JScrollPane scrollPan = new JScrollPane();
		scrollPan.setViewportView(getOrderDetailsTable());
		scrollPan.setMaximumSize(
				new Dimension(ScreenDimension.getScreenWidth() - (ScreenDimension.getScreenWidth() / 6), 250));
		scrollPan.setBackground(Color.decode(CustomColors.bgColor));

		productPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		productPanel.add(orderProdlbl);
		productPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		productPanel.add(scrollPane);
		productPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		productPanel.add(ordProdDetlbl);
		productPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		productPanel.add(scrollPan);

		return productPanel;
	}

	private JPanel getHeader() {
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagConstraints gbcBtn = new GridBagConstraints();
		int top = 5, left = 3, bottom = 0, right = 3;
		Insets i = new Insets(top, left, bottom, right);

		headerPanel = new JPanel();
		headerPanel.setLayout(new GridBagLayout());
		headerPanel.setPreferredSize(new Dimension(0, 50));
		headerPanel.setBackground(Color.decode(CustomColors.bgColor));

		searchBar = new JTextField(25);
		Border empty = new EmptyBorder(5, 5, 5, 5);
		Border compound = new CompoundBorder(searchBar.getBorder(), empty);
		searchBar.setBorder(compound);
		gbc.insets = i;
		gbc.gridx = 0;
		gbc.gridy = 1;

		headerPanel.add(searchBar, gbc);

		searchBtn = new JButton("Search", new ImageIcon(utilities.getSearchIcon()));
		searchBtn.setBackground(Color.white);
		searchBtn.setForeground(Color.decode(CustomColors.blackColor));

		gbcBtn.insets = i;
		gbcBtn.gridx = 1;
		gbcBtn.gridy = 1;
		headerPanel.add(searchBtn, gbcBtn);

		searchBar.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				searchData(searchBar.getText().toString());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		return headerPanel;
	}

	private JPanel createCustomerPanel() {
		// customer details panel
		customerPanel = new JPanel();
		customerPanel.setBackground(Color.decode(CustomColors.bgColor));
		customerPanel.setPreferredSize(new Dimension(200, 0));
		customerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));

		customerDetails = new JLabel("Customer Details");
		customerDetails.setFont(CustomFonts.font1);

		cName = new JLabel("Full Name: ");
		cAddress = new JLabel("Address: ");
		cContact = new JLabel("Contact: ");
		cEmail = new JLabel("Email: ");

		cNametxt = new JTextField();
		cNametxt.setEditable(false);
		cNametxt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		cAddresstxt = new JTextField();
		cAddresstxt.setEditable(false);
		cAddresstxt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		cContacttxt = new JTextField();
		cContacttxt.setEditable(false);
		cContacttxt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		cEmailtxt = new JTextField();
		cEmailtxt.setEditable(false);
		cEmailtxt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

		customerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		customerPanel.add(customerDetails);
		customerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		customerPanel.add(cName);
		customerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		customerPanel.add(cNametxt);
		customerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		customerPanel.add(cAddress);
		customerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		customerPanel.add(cAddresstxt);
		customerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		customerPanel.add(cContact);
		customerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		customerPanel.add(cContacttxt);
		customerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		customerPanel.add(cEmail);
		customerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		customerPanel.add(cEmailtxt);
		customerPanel.add(Box.createRigidArea(new Dimension(10, 10)));

		return customerPanel;
	}

	private JTable getOrderListTable() {

		if (productTable == null) {
			productTable = new JTable();
			productTable.setEnabled(false);

			productTable.getTableHeader().setFont(CustomFonts.textFont);
			Utilities.setCellsAlignment(productTable, SwingConstants.CENTER);
		}
		return productTable;
	}

	private JTable getOrderDetailsTable() {

		if (ordDetTbl == null) {
			ordDetTbl = new JTable();
			ordDetTbl.setEnabled(false);

			ordDetTbl.getTableHeader().setFont(CustomFonts.textFont);
			Utilities.setCellsAlignment(ordDetTbl, SwingConstants.CENTER);
		}
		return ordDetTbl;
	}

	private void searchData(String s) {
		CustomerModel model = customerService.fetchCustByNum(s);
		if (model != null) {
			System.err.println("Customer exist");
			cid = model.getId();
			cNametxt.setText(model.getName());
			cAddresstxt.setText(model.getAddress());
			cContacttxt.setText(model.getContact());
			cEmailtxt.setText(model.getEmail());

			rs = orderService.fetchOrdersToDisplayInTable(model.getId());
			rset = orderService.fetchAllOrdersById(cid);

			try {
				if (rs != null) {
					productTable.setModel(DbUtils.resultSetToTableModel(rs));
					productTable.getTableHeader().setFont(CustomFonts.textFont);
					Utilities.setCellsAlignment(productTable, SwingConstants.CENTER);

					displayRemainingAmt();
				}

				if (rset != null) {
					ordDetTbl.setModel(DbUtils.resultSetToTableModel(rset));
					ordDetTbl.getTableHeader().setFont(CustomFonts.textFont);
					Utilities.setCellsAlignment(ordDetTbl, SwingConstants.CENTER);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			cNametxt.setText("");
			cAddresstxt.setText("");
			cContacttxt.setText("");
			cEmailtxt.setText("");
			productTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "null" }));
			ordDetTbl.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "null" }));
		}
	}

	private void displayRemainingAmt() {
		sumOfTOPNpaid = orderService.sumOfTotalOrderPNpaid(cid);
		duetxt.setText(sumOfTOPNpaid + "");
	}

	private void refreshOrderTable() {
		rs = orderService.fetchOrdersToDisplayInTable(cid);
		productTable.setModel(DbUtils.resultSetToTableModel(rs));
		productTable.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(productTable, SwingConstants.CENTER);
	}

	private void refreshAllOrderTable() {
		rset = orderService.fetchAllOrdersById(cid);
		ordDetTbl.setModel(DbUtils.resultSetToTableModel(rset));
		ordDetTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(ordDetTbl, SwingConstants.CENTER);
	}

	private void transaction(float amt) {

		TransactionModel model = new TransactionModel(cid, GlobalVariable.currentUserId, amt,
				Utilities.getCurrentDateTime());
		transactionService.addTransaction(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == orderReadyBtn) {
			boolean b;
			if (chOrderReady.isSelected()) {
				b = orderService.setOrderStatusReady(cid, GlobalVariable.OrderReady);
				if (b) {
					refreshAllOrderTable();
				} else {
					JOptionPane.showMessageDialog(null, "Order state failed to update ", "Order Ready",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		if (e.getSource() == deliverBtn) {
			boolean b;
			if (chDelivered.isSelected()) {
				b = orderService.setDeliveryStatsReady(cid, GlobalVariable.Delivered);
				if (b) {
					System.out.println("Delivery success");
					refreshAllOrderTable();
				} else {
					JOptionPane.showMessageDialog(null, "Delivery state failed to update ", "Ready to Deliver",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		if (e.getSource() == payBtn) {
			String amt = amttxt.getText().toString();
			float floatamt = 0;// amount inserted by user

			if (!amt.isEmpty()) {
				floatamt = Float.parseFloat(amt);

				// fetch all orders from order table
				rs = orderService.fetchOrders(cid);

				if (sumOfTOPNpaid == floatamt) {
					try {
						if (rs != null) {
							// all the transactions will be recorded
							transaction(floatamt);
							while (rs.next()) {
								PaymentModel model = new PaymentModel(cid, GlobalVariable.fullPaid, rs.getInt("o_id"),
										GlobalVariable.defaultDiscount, rs.getFloat("total"),
										Utilities.getCurrentDate());
								boolean b = paymentService.insertPayment(model);
								if (b) {
									System.out.println("Inserted into tbl_payment");
									boolean n = orderService.orderStatusPaid(cid);
									if (n) {
										amttxt.setText("");
										refreshAllOrderTable();
										System.out.println("tbl_order payment status updated successfuly");
									} else {
										System.err.println("tbl_order payment status not udpated ");
									}
								} else {
									System.err.println("Failed to insert tbl_payment");
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "Without order payment is not done ", "Payment",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Insert full amount ", "Payment", JOptionPane.ERROR_MESSAGE);
				}

				refreshOrderTable();
				displayRemainingAmt();
				searchBar.setText("");
//				duetxt.setText("");

			} else {
				JOptionPane.showMessageDialog(null, "Please insert amount !", "Empty field", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
