package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.model.EmpModel;
import com.model.EmpPaymentModel;
import com.model.EmpPostModel;
import com.service.emp.EmpPaymentService;
import com.service.emp.EmpPaymentServiceImpl;
import com.service.emp.EmpPostService;
import com.service.emp.EmpPostServiceImpl;
import com.service.emp.EmpSalaryService;
import com.service.emp.EmpService;
import com.service.emp.EmpServiceImpl;

import net.proteanit.sql.DbUtils;
import util.CustomColors;
import util.CustomFonts;
import util.GlobalVariable;
import util.Utilities;

public class EmpPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Utilities utilities = new Utilities();

	private JPanel centerPanel, leftPanel, rightPanel;

	private JLabel empDetails, empName, actPaslbl, empAddress, empContact, empEmail, postlbl, sallbl, paymentlbl, empId,
			emppost, sal, adv, payd, salPayDetails;

	private JTextField empIdtxt, empNametxt, empAddresstxt, empContacttxt, empEmailtxt, searchtxt, posttxt, postSaltxt,
			saltxt, advtxt, insertSaltxt, salPaidDate;

	private JButton saveEmp, clearEmp, leaveEmp, editEmp, insertPost, btnSalPay, insertSalBtn;

	private Border searchBorder = BorderFactory.createTitledBorder("Search by contact number");

	private JTable postTbl, salTbl, salPaymentTbl;

	private JComboBox<String> postJC;

	private EmpService empService = new EmpServiceImpl();
	private EmpPostService empPostService = new EmpPostServiceImpl();
	private EmpSalaryService empSalaryService = new EmpSalaryService();
	private EmpPaymentService empPaymentService = new EmpPaymentServiceImpl();

	private EmpModel empModel;

	private static int emp_id = 0;

	EmpPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.decode(CustomColors.bgColor));

		add(LeftPanel(), BorderLayout.WEST);
		add(CenterPanel(), BorderLayout.CENTER);
		add(RightPanel(), BorderLayout.EAST);

		setEmpStatusOnScreen();

		saveEmp.addActionListener(this);
		clearEmp.addActionListener(this);
		leaveEmp.addActionListener(this);
		editEmp.addActionListener(this);
		insertPost.addActionListener(this);
		btnSalPay.addActionListener(this);
		insertSalBtn.addActionListener(this);
	}

	private List<String> getEmpPosts() {

		List<String> posts = new ArrayList<String>();

		List<EmpPostModel> empList = empPostService.getAvailablePost();

		if (empList != null) {
			for (EmpPostModel empPostModel : empList) {
				posts.add(empPostModel.getPost());
			}
			return posts;
		} else {
			System.err.println("List of employee's post is null");
			return null;
		}
	}

	private JPanel LeftPanel() {
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.decode(CustomColors.bgColor));
		leftPanel.setPreferredSize(new Dimension(250, 0));
		leftPanel.setLayout(null);

		actPaslbl = new JLabel("<html><i>(Active)<i><html>");
		actPaslbl.setBounds(180, 40, 60, 25);
		actPaslbl.setForeground(Color.decode(CustomColors.greenColor));
		actPaslbl.setVisible(false);

		empDetails = new JLabel("<html><u>Insert employee Details</u></html>");
		empDetails.setFont(CustomFonts.font1);
		empDetails.setBounds(10, 10, 300, 25);

		empName = new JLabel("Full Name");
		empName.setBounds(10, 40, 200, 25);
		empAddress = new JLabel("Address");
		empAddress.setBounds(10, 80, 230, 25);
		empContact = new JLabel("Contact");
		empContact.setBounds(10, 120, 230, 25);
		empEmail = new JLabel("Email");
		empEmail.setBounds(10, 160, 230, 25);
		emppost = new JLabel("Post");
		emppost.setBounds(10, 200, 230, 25);

		saveEmp = new JButton("Save", new ImageIcon(utilities.getSaveIcon()));
		saveEmp.setBounds(70, 260, 100, 40);
		saveEmp.setBackground(Color.white);
		saveEmp.setForeground(Color.decode(CustomColors.blackColor));
		clearEmp = new JButton("Clear", new ImageIcon(utilities.getClearIcon()));
		clearEmp.setBounds(70, 305, 100, 40);
		clearEmp.setBackground(Color.white);
		clearEmp.setForeground(Color.decode(CustomColors.blackColor));

		empNametxt = new JTextField();
		empNametxt.setBounds(10, 60, 230, 25);
		empAddresstxt = new JTextField();
		empAddresstxt.setBounds(10, 100, 230, 25);
		empContacttxt = new JTextField();
		empContacttxt.setBounds(10, 140, 230, 25);
		empEmailtxt = new JTextField();
		empEmailtxt.setBounds(10, 180, 230, 25);

		// for searching
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		searchPanel.setBounds(10, 350, 290, 50);
		searchPanel.setBorder(searchBorder);
		searchtxt = new JTextField();
		searchPanel.add(searchtxt);
		searchtxt.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (searchtxt.getText().isEmpty()) {
					clearEmpDetails();
					actPaslbl.setVisible(false);
				} else {
					searchEmp(searchtxt.getText().toString());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		editEmp = new JButton("Update", new ImageIcon(utilities.getUpdateIcon()));
		editEmp.setBounds(50, 405, 150, 40);
		editEmp.setBackground(Color.decode(CustomColors.blackColor));
		editEmp.setForeground(Color.white);

		leaveEmp = new JButton("Leave", new ImageIcon(utilities.getLeaveIcon()));
		leaveEmp.setBounds(50, 450, 150, 40);
		leaveEmp.setBackground(Color.white);
		leaveEmp.setForeground(Color.decode(CustomColors.blackColor));

		leftPanel.add(empDetails);
		leftPanel.add(empName);
		leftPanel.add(empNametxt);
		leftPanel.add(empAddress);
		leftPanel.add(empAddresstxt);
		leftPanel.add(empContact);
		leftPanel.add(empContacttxt);
		leftPanel.add(empEmail);
		leftPanel.add(empEmailtxt);
		leftPanel.add(emppost);
		leftPanel.add(getPostCB());
		leftPanel.add(saveEmp);
		leftPanel.add(clearEmp);
		leftPanel.add(searchPanel);
		leftPanel.add(editEmp);
		leftPanel.add(leaveEmp);
		leftPanel.add(actPaslbl);

		return leftPanel;
	}

	private void searchEmp(String number) {
		System.out.println("Contact number: " + number);
		EmpModel model = empService.getEmployeeByNumber(number);
		try {
			if (model != null) {
				emp_id = model.getId();
				empNametxt.setText(model.getName());
				empAddresstxt.setText(model.getAddress());
				empContacttxt.setText(model.getContact());
				empEmailtxt.setText(model.getEmail());

				setEmpStatusOnScreen();
			} else {
				clearEmpDetails();
				actPaslbl.setVisible(false);
				System.out.println("Contact not found !!!");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private JComboBox<String> getPostCB() {
		if (postJC == null) {
			postJC = new JComboBox<>();
			postJC.setBounds(10, 220, 230, 25);

			if (getEmpPosts() != null) {
				postJC.setModel(new DefaultComboBoxModel<String>(getEmpPosts().toArray(new String[1])));
			} else {
				postJC.setModel(new DefaultComboBoxModel<String>(getEmpPosts().toArray(new String[] { "No data" })));
			}
		}
		return postJC;
	}

	private JPanel CenterPanel() {
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.decode(CustomColors.bgColor));
		centerPanel.setLayout(null);

		paymentlbl = new JLabel("<html><u>Add Payment</u><html>");
		paymentlbl.setFont(CustomFonts.font1);
		paymentlbl.setBounds(30, 10, 300, 25);

		empId = new JLabel("Employee Id");
		empId.setBounds(30, 40, 100, 25);
		empIdtxt = new JTextField();
		empIdtxt.setBounds(30, 60, 80, 25);
		empIdtxt.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (empIdtxt.getText().isEmpty()) {
					saltxt.setText("");
					clearEmpDetails();
					postJC.setModel(new DefaultComboBoxModel<String>(getEmpPosts().toArray(new String[1])));
					actPaslbl.setVisible(false);
				} else {
					getEmployeeDetails(empIdtxt.getText().toString());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		sal = new JLabel("Salary");
		sal.setBounds(150, 40, 100, 25);
		saltxt = new JTextField();
		saltxt.setBounds(150, 60, 80, 25);
		saltxt.setEditable(false);

		adv = new JLabel("Advance");
		adv.setBounds(250, 40, 100, 25);
		advtxt = new JTextField();
		advtxt.setBounds(250, 60, 80, 25);
		advtxt.setEditable(false);
		advtxt.setText("0");

		payd = new JLabel("Payment Date");
		payd.setBounds(350, 40, 100, 25);
		salPaidDate = new JTextField();
		salPaidDate.setText(Utilities.getCurrentDateTime());
		salPaidDate.setBounds(350, 60, 100, 25);

		btnSalPay = new JButton("Pay Salary", new ImageIcon(utilities.getMoneyIcon()));
		btnSalPay.setBounds(480, 50, 150, 40);
		btnSalPay.setBackground(Color.white);
		btnSalPay.setForeground(Color.decode(CustomColors.blackColor));

		salPayDetails = new JLabel("<html><u>Payment History</u><html>");
		salPayDetails.setFont(CustomFonts.font1);
		salPayDetails.setBounds(30, 100, 200, 25);

		JScrollPane sp = new JScrollPane(payHistoryTbl());
		sp.setBounds(30, 130, 600, 200);

		centerPanel.add(paymentlbl);
		centerPanel.add(empId);
		centerPanel.add(empIdtxt);

		centerPanel.add(sal);
		centerPanel.add(saltxt);
		centerPanel.add(adv);
		centerPanel.add(advtxt);
		centerPanel.add(payd);
		centerPanel.add(salPaidDate);
		centerPanel.add(btnSalPay);
		centerPanel.add(salPayDetails);
		centerPanel.add(sp);
		return centerPanel;
	}

	protected void getEmployeeDetails(String id) {
		EmpModel m = empService.getEmployeeById(Integer.parseInt(id));
		if (m != null) {
			emp_id = Integer.parseInt(id);
			empNametxt.setText(m.getName());
			empAddresstxt.setText(m.getAddress());
			empContacttxt.setText(m.getContact());
			empEmailtxt.setText(m.getEmail());

			// set post combo box by employee id
			String s = empPostService.getPostById(m.getPost());
			for (int i = 0; i < getEmpPosts().size(); i++) {
				if (getEmpPosts().get(i).toString().equals(s)) {
					postJC.setModel(new DefaultComboBoxModel<String>(new String[] { s }));
				}
			}
			// sets employee salary in JTextfield
			setSalary(empIdtxt.getText().toString());
			setEmpStatusOnScreen();

		} else {
			emp_id = 0;
			postJC.setModel(new DefaultComboBoxModel<String>(getEmpPosts().toArray(new String[1])));
			clearEmpDetails();
			actPaslbl.setVisible(false);
		}
	}

	protected void setSalary(String string) {
		float sal = empSalaryService.getSalaryById(Integer.parseInt(string));
		saltxt.setText(String.valueOf(sal));
	}

	private JPanel RightPanel() {
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.decode(CustomColors.bgColor));
		rightPanel.setPreferredSize(new Dimension(250, 0));
		rightPanel.setLayout(null);

		postlbl = new JLabel("<html><u>Posts</u></html>");
		postlbl.setFont(CustomFonts.font1);
		postlbl.setBounds(10, 10, 300, 25);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 200, 250);
		scrollPane.setViewportView(postTable());

		JLabel postname = new JLabel("Post name");
		postname.setBounds(10, 300, 100, 25);
		JLabel postsal = new JLabel("Salary");
		postsal.setBounds(160, 300, 50, 25);

		posttxt = new JTextField();
		posttxt.setBounds(10, 320, 150, 25);

		postSaltxt = new JTextField();
		postSaltxt.setBounds(160, 320, 50, 25);

		insertPost = new JButton("Add Post", new ImageIcon(utilities.getAddIcon()));
		insertPost.setBackground(Color.white);
		insertPost.setForeground(Color.decode(CustomColors.blackColor));
		insertPost.setBounds(35, 360, 150, 40);

		sallbl = new JLabel("<html><u>Salary</u></html>");
		sallbl.setFont(CustomFonts.font1);
		sallbl.setBounds(10, 400, 200, 25);

		JScrollPane scrollPan = new JScrollPane();
		scrollPan.setBounds(10, 430, 200, 250);
		scrollPan.setViewportView(salTbl());

		insertSaltxt = new JTextField();
		insertSaltxt.setBounds(35, 690, 150, 25);

		insertSalBtn = new JButton("Add Salary", new ImageIcon(utilities.getAddIcon()));
		insertSalBtn.setBounds(35, 730, 150, 40);
		insertSalBtn.setBackground(Color.white);
		insertSalBtn.setForeground(Color.decode(CustomColors.blackColor));

		rightPanel.add(postlbl);
		rightPanel.add(scrollPane);
		rightPanel.add(postname);
		rightPanel.add(postsal);
		rightPanel.add(posttxt);
		rightPanel.add(postSaltxt);
		rightPanel.add(insertPost);
		rightPanel.add(sallbl);
		rightPanel.add(scrollPan);
		rightPanel.add(insertSaltxt);
		rightPanel.add(insertSalBtn);

		return rightPanel;
	}

	private JTable postTable() {
		postTbl = new JTable();
		postTbl.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Post" }));
		List<EmpPostModel> list = empPostService.getAvailablePost();

		DefaultTableModel model = (DefaultTableModel) postTbl.getModel();
		model.setRowCount(0);

		for (EmpPostModel e : list) {
			System.out.println("Post: " + e.getPost());
			model.addRow(new Object[] { e.getId(), e.getPost() });
		}

		postTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(postTbl, SwingConstants.CENTER);

		return postTbl;
	}

	private JTable salTbl() {

		salTbl = new JTable();
		salTbl.setModel(DbUtils.resultSetToTableModel(EmpSalaryService.getSalaryList()));
		salTbl.setEnabled(false);

		salTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(salTbl, SwingConstants.CENTER);

		return salTbl;
	}

	private ResultSet getPaymentTable() {
		ResultSet r = empPaymentService.getPaymentTable();

		if (r != null) {
			return r;
		} else {
			return null;
		}
	}

	private JTable payHistoryTbl() {

		salPaymentTbl = new JTable();

		if (getPaymentTable() != null) {
			salPaymentTbl.setModel(DbUtils.resultSetToTableModel(getPaymentTable()));
		} else {
			salPaymentTbl.setModel(new DefaultTableModel(new Object[][] {},
					new String[] { "Id", "Employee", "Post", "Salary", "Paid Date" }));
		}
		salPaymentTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(salPaymentTbl, SwingConstants.CENTER);
		return salPaymentTbl;
	}

	private boolean isEmpFieldFilled() {
		if (empNametxt.getText().isEmpty() || empAddresstxt.getText().isEmpty() || empContacttxt.getText().isEmpty()
				|| empEmailtxt.getText().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	private void clearEmpDetails() {
		empNametxt.setText("");
		empAddresstxt.setText("");
		empContacttxt.setText("");
		empEmailtxt.setText("");
		emp_id = 0;
	}

	private void setEmpStatusOnScreen() {
		int emp_stat = empService.getEmpStatus(emp_id);

		if (emp_stat != 0) {
			if (emp_stat == GlobalVariable.emp_status_active) {
				actPaslbl.setText("<html><i>(Active)<i><html>");
				actPaslbl.setForeground(Color.decode(CustomColors.greenColor));
				actPaslbl.setVisible(true);
			}
			if (emp_stat == GlobalVariable.emp_status_leaved) {
				actPaslbl.setText("<html><i>(Leaved)<i><html>");
				actPaslbl.setForeground(Color.decode(CustomColors.redColor));
				actPaslbl.setVisible(true);
			}
		} else {
			actPaslbl.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// saves employee details
		if (e.getSource() == saveEmp) {
			if (isEmpFieldFilled()) {
				saveEmployeeDetails();
			} else {
				System.out.println(postJC.getSelectedIndex());
				System.out.println(postJC.getSelectedItem());
				JOptionPane.showMessageDialog(null, "Field may be empty", "Empty field", JOptionPane.ERROR_MESSAGE);
			}
		}

		// clears employee detail fields
		if (e.getSource() == clearEmp) {
			clearEmpDetails();
			actPaslbl.setVisible(false);
		}

		// update employee details
		if (e.getSource() == editEmp) {
			if (isEmpFieldFilled()) {
				updateEmployeeDetails();

			} else {
				JOptionPane.showMessageDialog(null, "Field may be empty", "Empty field", JOptionPane.ERROR_MESSAGE);
			}
		}

		// if employee leaves the company
		if (e.getSource() == leaveEmp) {
			employeeLeaved();
		}

		// pay the salary of the employee
		if (e.getSource() == btnSalPay) {
			payEmployeeSalary();
		}
		if (e.getSource() == insertPost) {
			if (posttxt.getText().isEmpty() || postSaltxt.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Field may be empty", "Insert Post", JOptionPane.ERROR_MESSAGE);
			} else {
				boolean b = empPostService.addEmpPost(posttxt.getText(), Float.parseFloat(postSaltxt.getText()));
				if (b) {
					refreshPostTable();
					posttxt.setText("");
					postSaltxt.setText("");
					JOptionPane.showMessageDialog(null, "Post inserted successfully", "Insert Post",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Failed to insert post", "Insert Post",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		// insert new range of salary
		if (e.getSource() == insertSalBtn) {
			if (!insertSaltxt.getText().isEmpty()) {
				boolean b = empSalaryService.insertSalary(Float.parseFloat(insertSaltxt.getText().toString()));
				if (b) {
					refreshSalaryTable();
					JOptionPane.showMessageDialog(null, "Salary Inserted", "Salary Insertion",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Failed to insert salary", "Salary Insertion",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please insert amount", "Salary Insertion",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void payEmployeeSalary() {
		if (empIdtxt.getText().isEmpty() || saltxt.getText().isEmpty() || salPaidDate.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Fields may be empty", "Empty field", JOptionPane.ERROR_MESSAGE);
		} else {
			EmpPaymentModel model = new EmpPaymentModel(Integer.parseInt(empIdtxt.getText()),
					Float.parseFloat(saltxt.getText()), Float.parseFloat(advtxt.getText()), salPaidDate.getText());
			boolean b = empPaymentService.paySalary(model);

			if (b) {
				refreshPaymentTableHistory();
				empIdtxt.setText("");
				saltxt.setText("");
				JOptionPane.showMessageDialog(null, "Salary paid", "Salary Payment", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Salary paid failed :(", "Salary Payment",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void refreshPaymentTableHistory() {

		salPaymentTbl.setModel(DbUtils.resultSetToTableModel(getPaymentTable()));
	}

	private void refreshSalaryTable() {
		salTbl.setModel(DbUtils.resultSetToTableModel(EmpSalaryService.getSalaryList()));
		salTbl.setEnabled(false);

		salTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(salTbl, SwingConstants.CENTER);
	}

	private void refreshPostTable() {
		postTbl.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Post" }));
		List<EmpPostModel> list = empPostService.getAvailablePost();

		DefaultTableModel model = (DefaultTableModel) postTbl.getModel();
		model.setRowCount(0);

		for (EmpPostModel e : list) {
			System.out.println("Post: " + e.getPost());
			model.addRow(new Object[] { e.getId(), e.getPost() });
		}

		postTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(postTbl, SwingConstants.CENTER);
	}

	// when employee leaved
	private void employeeLeaved() {
		boolean b = empService.setEmpStatusLeaved(emp_id);
		if (b) {
			setEmpStatusOnScreen();
			JOptionPane.showMessageDialog(null, "Employee left from our company", "Employee leaved",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Employee may already leaved from our company", "Employee leaved",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// updates employee details
	private void updateEmployeeDetails() {
		int post_id = 0;
		post_id = empPostService.getAvailablePostIdByName(getPostCB().getSelectedItem().toString());

		if (post_id == 0) {
			System.err.println("Update Employee: Post id is empty");
		} else {
			System.out.println("Post id is: " + post_id);

			empModel = new EmpModel(emp_id, empNametxt.getText(), empAddresstxt.getText(), empContacttxt.getText(),
					empEmailtxt.getText(), post_id, 1);
			boolean b;
			b = empService.updateEmployee(empModel);
			if (b) {
				clearEmpDetails();
				JOptionPane.showMessageDialog(null, "Employee updated successfully :)", "Information saved",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to update employee :(", "Insertion failed",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	// saves employee details
	private void saveEmployeeDetails() {
		int post_id = 0;
		post_id = empPostService.getAvailablePostIdByName(getPostCB().getSelectedItem().toString());

		if (post_id == 0) {
			System.out.println("Post id is empty");
		} else {
			System.out.println("Post id is: " + post_id);

			empModel = new EmpModel(empNametxt.getText(), empAddresstxt.getText(), empContacttxt.getText(),
					empEmailtxt.getText(), post_id, 1);
			boolean b;
			b = empService.insertEmployee(empModel);
			if (b) {
				clearEmpDetails();
				JOptionPane.showMessageDialog(null, "Employee saved successfully :)", "Information saved",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to save employee :( Employee may exist!",
						"Insertion failed", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
