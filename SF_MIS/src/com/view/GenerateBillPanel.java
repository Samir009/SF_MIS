package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.model.BillModelFinal;
import com.service.OrderService;
import com.service.OrderServiceImpl;
import com.toedter.calendar.JDateChooser;

import util.CustomColors;
import util.CustomFonts;
import util.DatabaseUtil;
import util.GlobalVariable;
import util.Utilities;

public class GenerateBillPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Utilities utilities = new Utilities();

	private OrderService orderService;

	private List<BillModelFinal> productBillList;

	// get customer details from order id;
	private int order_id = 0;

	int billNo;

	private JPanel centerPanel, eastPanel;
	private JDateChooser dateChooser;
	private JTextField nametxt, contacttxt, prodDistxt, oIdtxt;
	private JButton addToBillBtn, printBtn, pdfBtn;

	private JTextArea receiptBill;
	private JLabel totalAmtlbl = new JLabel("");
	private float totalAmt = 0;
	private JLabel discountlbl = new JLabel("");
	private float discountAmt = 0;

	private JTable itemListTable;

	// Generates random integers 0 to 999

	private String custName = "";
	private boolean custNameAdd = false;
	private boolean isFirstCust = true;
	private int customer_id = 0;

	private String name, qty, rate, total;

	GenerateBillPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.decode(CustomColors.bgColor));

		add(EastPanel(), BorderLayout.EAST);
		add(CenterPanel(), BorderLayout.CENTER);

		addToBillBtn.addActionListener(this);
		pdfBtn.addActionListener(this);
		printBtn.addActionListener(this);
	}

	private JPanel EastPanel() {
		eastPanel = new JPanel();
		eastPanel.setBackground(Color.decode(CustomColors.bgColor));
		eastPanel.setLayout(null);
		eastPanel.setPreferredSize(new Dimension(300, 0));
		receiptBill = new JTextArea();
		receiptBill.setText("");
		receiptBill.setEditable(false);
		receiptBill.setBounds(0, 0, 300, 600);

		eastPanel.add(receiptBill);
		return eastPanel;
	}

	private JPanel CenterPanel() {
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.decode(CustomColors.bgColor));
		centerPanel.setLayout(null);

		JLabel d = new JLabel("Date:");
		d.setBounds(10, 20, 100, 25);
		dateChooser = new JDateChooser();
		dateChooser.setBounds(150, 20, 150, 25);
		dateChooser.setDate(Utilities.getCurrentDate());

		JLabel cName = new JLabel("Customer Name:");
		cName.setBounds(10, 60, 100, 25);

		nametxt = new JTextField();
		nametxt.setBounds(150, 60, 150, 25);
		nametxt.setEditable(false);

		JLabel cNumber = new JLabel("Customer Contact:");
		cNumber.setBounds(10, 100, 130, 25);

		contacttxt = new JTextField();
		contacttxt.setBounds(150, 100, 150, 25);
		contacttxt.setEditable(false);

		JLabel prodDiscount = new JLabel("Discount:");
		prodDiscount.setBounds(10, 140, 150, 25);

		prodDistxt = new JTextField();
		prodDistxt.setBounds(150, 140, 150, 25);

		JLabel orderId = new JLabel("Order Id:");
		orderId.setBounds(10, 180, 150, 25);

		oIdtxt = new JTextField();
		oIdtxt.setBounds(150, 180, 150, 25);

		addToBillBtn = new JButton("ADD TO BILL", new ImageIcon(utilities.getAddIcon()));
		addToBillBtn.setBounds(150, 220, 150, 40);
		addToBillBtn.setBackground(Color.decode(CustomColors.bgColor));
		addToBillBtn.setForeground(Color.decode(CustomColors.blueColor));

		printBtn = new JButton("PRINT", new ImageIcon(utilities.getPrinterIcon()));
		printBtn.setBounds(150, 320, 150, 40);
		printBtn.setBackground(Color.decode(CustomColors.bgColor));
		printBtn.setForeground(Color.decode(CustomColors.blueColor));

		pdfBtn = new JButton("PDF", new ImageIcon(utilities.getPdfIcon()));
		pdfBtn.setBounds(150, 270, 150, 40);
		pdfBtn.setBackground(Color.decode(CustomColors.bgColor));
		pdfBtn.setForeground(Color.decode(CustomColors.blueColor));

		// TODO add button here

		JLabel soldP = new JLabel("Sold Products");
		soldP.setBounds(350, 10, 150, 25);
		soldP.setFont(CustomFonts.font1);

		JLabel clearTblBill = new JLabel("(Clear table)");
		clearTblBill.setForeground(Color.decode(CustomColors.redColor));
		clearTblBill.setBounds(350, 365, 100, 25);

		JLabel discount = new JLabel("Discount (Rs) : ");
		discount.setBounds(580, 365, 100, 25);
		discount.setForeground(Color.decode(CustomColors.blackColor));

		discountlbl.setBounds(680, 365, 100, 25);
		discountlbl.setForeground(Color.decode(CustomColors.blackColor));

		JLabel totallbl = new JLabel("Total payable (Rs) : ");
		totallbl.setBounds(750, 365, 130, 25);
		totallbl.setForeground(Color.decode(CustomColors.greenColor));

		totalAmtlbl.setBounds(880, 365, 100, 25);
		totalAmtlbl.setFont(CustomFonts.textFont);
		totalAmtlbl.setForeground(Color.decode(CustomColors.greenColor));

		clearTblBill.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				clearTableBill();
				clearBill();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(ItemListTable());
		scrollPane.setBounds(350, 50, 600, 315);

		centerPanel.add(d);
		centerPanel.add(dateChooser);
		centerPanel.add(cName);
		centerPanel.add(nametxt);
		centerPanel.add(cNumber);
		centerPanel.add(contacttxt);
		centerPanel.add(prodDiscount);
		centerPanel.add(prodDistxt);
		centerPanel.add(orderId);
		centerPanel.add(oIdtxt);

		centerPanel.add(addToBillBtn);
		centerPanel.add(soldP);
		centerPanel.add(discount);
		centerPanel.add(discountlbl);
		centerPanel.add(totallbl);
		centerPanel.add(totalAmtlbl);
		centerPanel.add(clearTblBill);
		centerPanel.add(scrollPane);
		centerPanel.add(printBtn);
		centerPanel.add(pdfBtn);

		return centerPanel;
	}

	protected void clearBill() {
		receiptBill.selectAll();
		receiptBill.replaceSelection("");
		totalAmt = 0;
		discountAmt = 0;

	}

	protected void clearTableBill() {
		productBillList = null;
		DefaultTableModel model = (DefaultTableModel) itemListTable.getModel();
		model.setRowCount(0);
		clearCustomerDetails();
		isFirstCust = true;
		totalAmt = 0;
		totalAmtlbl.setText("");
		discountlbl.setText("0");
	}

	protected void clearCustomerDetails() {
		nametxt.setText("");
		contacttxt.setText("");
		prodDistxt.setText("");
	}

	protected void serachOrdersById(String text) {

		// creating an object of Random class
		int y = 5;
		Random random = new Random();
		y = random.nextInt(100000);

		billNo = 587 + (y * 3) - 1;

		System.err.println("Random number is: " + billNo);

		productBillList = new ArrayList<BillModelFinal>();
		orderService = new OrderServiceImpl();

		String[] orderIds = text.split(" ");
		System.out.println(Arrays.toString(orderIds));

		if (orderIds != null) {
			productBillList = orderService.fetchAllOrdersforBill(orderIds);

			DefaultTableModel tblModel = (DefaultTableModel) itemListTable.getModel();
			Object[] columnNames = new Object[8];
			columnNames[0] = "o_id";
			columnNames[1] = "product";
			columnNames[2] = "qty";
			columnNames[3] = "rate";
			columnNames[4] = "total";
			columnNames[5] = "o_date";
			columnNames[6] = "d_date";
			columnNames[7] = "pay_status";

			tblModel.setColumnIdentifiers(columnNames);

			Object[] rowData = new Object[20];

			for (int i = 0; i < productBillList.size(); i++) {
				order_id = productBillList.get(i).getO_id();
				getCustDetByOrderId(order_id);
				rowData[0] = productBillList.get(i).getO_id();
				rowData[1] = productBillList.get(i).getProduct();
				rowData[2] = productBillList.get(i).getQty();
				rowData[3] = productBillList.get(i).getRate();
				rowData[4] = productBillList.get(i).getTotal();
				rowData[5] = productBillList.get(i).getO_date();
				rowData[6] = productBillList.get(i).getD_date();
				rowData[7] = productBillList.get(i).getPayment_status();

				tblModel.addRow(rowData);

				totalAmt += productBillList.get(i).getTotal();

				name = productBillList.get(i).getProduct();
				qty = String.valueOf(productBillList.get(i).getQty());
				rate = String.valueOf(productBillList.get(i).getRate());
				total = String.valueOf(productBillList.get(i).getTotal());

				receiptBill.append(" " + name + "         " + qty + "         " + rate + "          " + "         "
						+ total + "\n ");
			}

			totalAmt -= discountAmt;
			totalAmtlbl.setText((totalAmt - discountAmt) + "");
			receiptBill.append("\n ************************************************************" + "\n Discount (Rs) : "
					+ discountAmt + "    Total payable (Rs) : " + (totalAmt - discountAmt) + "\n \n Generated By: "
					+ GlobalVariable.currentUser + "  Role: " + GlobalVariable.userRole);

			storeBill(customer_id, totalAmt, discountAmt, GlobalVariable.currentUserId);
		} else {
			System.err.println("Order id is null!");
		}
	}

	private void storeBill(int cust_id, float total, float dis, int gen_by) {

		DatabaseUtil util = new DatabaseUtil();
		boolean b = util.storeBill(billNo, cust_id, total, dis, gen_by);
		if (b) {
			System.err.println("Bill saved in database.");
		} else {
			JOptionPane.showMessageDialog(null, "Failed to insert bill in database", "Bill", JOptionPane.ERROR_MESSAGE);
		}
	}

	// list all the items according to bill number in table
	private JTable ItemListTable() {
		itemListTable = new JTable();

		itemListTable.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(itemListTable, SwingConstants.CENTER);
		itemListTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "o_id", "product", "qty", "rate", "total", "o_date", "d_date", "pay_status" }));

		return itemListTable;
	}

	private void getCustDetByOrderId(int orderId) {

		orderService = new OrderServiceImpl();
		ResultSet rs = orderService.getCustomerDetailsByOrderId(orderId);
		try {
			if (rs.next()) {
				customer_id = rs.getInt("cust_id");
				custName = rs.getString("name");
				nametxt.setText(rs.getString("name"));
				contacttxt.setText(rs.getString("contact"));
				custNameAdd = true;
			}
			System.err.println("Customer name: " + custName);
			if (custNameAdd) {

				if (isFirstCust == true) {
					receiptBill.append(" \n \t Shankar furniture " + "\n  Bill No: " + billNo + " \n  DateTime:  "
							+ Utilities.getCurrentDateTime() + " \n  Customer Name: " + custName
							+ "\n ************************************************************"
							+ "\n Name                    qty            rate            total \n"
							+ " ************************************************************" + "\n  ");
					isFirstCust = false;
				} else {

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == addToBillBtn) {

			if (oIdtxt.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Insert all bill numbers", "Bill", JOptionPane.ERROR_MESSAGE);
			} else {
				if (prodDistxt.getText().isEmpty()) {
					discountlbl.setText("0");
				} else {
					discountAmt = Float.parseFloat(prodDistxt.getText());
					discountlbl.setText(discountAmt + "");
				}
				serachOrdersById(oIdtxt.getText());
				System.out.println(receiptBill.getText());

			}
		}
		if (e.getSource() == pdfBtn) {
			if (receiptBill.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Bill is emtpy!", "PDF", JOptionPane.ERROR_MESSAGE);
			} else {

				String path = "";
				JFileChooser j = new JFileChooser();
				j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int x = j.showSaveDialog(this);

				if (x == JFileChooser.APPROVE_OPTION) {
					path = j.getSelectedFile().getPath();
				}
				convertBillToPdf(path);
			}
		}
		if (e.getSource() == printBtn) {

			if (!receiptBill.getText().toString().trim().isEmpty()) {
				System.err.println("Has data in receipt bill");
				try {
					receiptBill.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Add items to bill first ", "Bill", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void convertBillToPdf(String path) {
//		String file_name = path;
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();

			Paragraph para = new Paragraph(receiptBill.getText());

			document.add(para);

			document.close();

			System.err.println("Writing in pdf finished. ");
			clearCustomerDetails();
			clearTableBill();
			clearBill();
			oIdtxt.setText("");
			JOptionPane.showMessageDialog(null, "bill saved", "Bill", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
