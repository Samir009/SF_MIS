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

import com.model.RoleModel;
import com.model.UserModel;
import com.service.user.RoleService;
import com.service.user.UserService;
import com.service.user.UserServiceImpl;

import net.proteanit.sql.DbUtils;
import util.CustomColors;
import util.CustomFonts;
import util.GlobalVariable;
import util.Utilities;

public class UserPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Utilities utilities = new Utilities();

	private JPanel centerPanel, leftPanel, rightPanel;
	private JLabel userDetails, userName, actPaslbl, userAddress, userContact, userEmail, rolelbl, userPassLbl,
			addRolelbl;

	private JTextField userNametxt, userAddresstxt, userContacttxt, userEmailtxt, searchtxt, passTxt, roleTxt;

	private JButton saveUser, clearUser, leaveUser, editUser, addRole;

	private Border searchBorder = BorderFactory.createTitledBorder("Search by contact number");

	private JComboBox<String> roleJC;

	private JTable avlUserTbl, roleTbl;

	// services that is being used;
	private RoleService roleService;
	private UserService userService;

	private int user_id;

	UserPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.decode(CustomColors.bgColor));

		add(LeftPanel(), BorderLayout.WEST);
		add(CenterPanel(), BorderLayout.CENTER);
		add(RightPanel(), BorderLayout.EAST);

		saveUser.addActionListener(this);
		clearUser.addActionListener(this);
		editUser.addActionListener(this);
		leaveUser.addActionListener(this);
		addRole.addActionListener(this);
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

		userDetails = new JLabel("<html><u>Insert User Details</u></html>");
		userDetails.setFont(CustomFonts.font1);
		userDetails.setBounds(10, 10, 300, 25);

		userName = new JLabel("Full Name");
		userName.setBounds(10, 40, 200, 25);
		userAddress = new JLabel("Address");
		userAddress.setBounds(10, 80, 230, 25);
		userContact = new JLabel("Contact");
		userContact.setBounds(10, 120, 230, 25);
		userEmail = new JLabel("Email");
		userEmail.setBounds(10, 160, 230, 25);
		userPassLbl = new JLabel("Password");
		userPassLbl.setBounds(10, 200, 230, 25);
		rolelbl = new JLabel("Role");
		rolelbl.setBounds(10, 240, 230, 25);

		userNametxt = new JTextField();
		userNametxt.setBounds(10, 60, 230, 25);
		userAddresstxt = new JTextField();
		userAddresstxt.setBounds(10, 100, 230, 25);
		userContacttxt = new JTextField();
		userContacttxt.setBounds(10, 140, 230, 25);
		userEmailtxt = new JTextField();
		userEmailtxt.setBounds(10, 180, 230, 25);
		passTxt = new JTextField();
		passTxt.setBounds(10, 220, 230, 25);

		saveUser = new JButton("Save", new ImageIcon(utilities.getSaveIcon()));
		saveUser.setBounds(70, 300, 100, 40);
		saveUser.setBackground(Color.white);
		saveUser.setForeground(Color.decode(CustomColors.blackColor));
		clearUser = new JButton("Clear", new ImageIcon(utilities.getClearIcon()));
		clearUser.setBounds(70, 345, 100, 40);
		clearUser.setBackground(Color.white);
		clearUser.setForeground(Color.decode(CustomColors.blackColor));

		// for searching
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		searchPanel.setBounds(10, 390, 290, 50);
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
					user_id = 0;
					clearUserDetails();
					roleJC.setModel(new DefaultComboBoxModel<String>(getUserRoles().toArray(new String[1])));
				} else {
					searchUser(searchtxt.getText().toString());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		editUser = new JButton("Update", new ImageIcon(utilities.getUpdateIcon()));
		editUser.setBounds(50, 450, 150, 40);
		editUser.setBackground(Color.decode(CustomColors.blackColor));
		editUser.setForeground(Color.white);

		leaveUser = new JButton("Leave", new ImageIcon(utilities.getLeaveIcon()));
		leaveUser.setBounds(50, 495, 150, 40);
		leaveUser.setBackground(Color.white);
		leaveUser.setForeground(Color.decode(CustomColors.blackColor));

		leftPanel.add(userDetails);
		leftPanel.add(userName);
		leftPanel.add(userNametxt);
		leftPanel.add(userAddress);
		leftPanel.add(userAddresstxt);
		leftPanel.add(userContact);
		leftPanel.add(userContacttxt);
		leftPanel.add(userEmail);
		leftPanel.add(userEmailtxt);
		leftPanel.add(userPassLbl);
		leftPanel.add(passTxt);
		leftPanel.add(rolelbl);
		leftPanel.add(getRoleCB());

		leftPanel.add(saveUser);
		leftPanel.add(clearUser);
		leftPanel.add(searchPanel);
		leftPanel.add(editUser);
		leftPanel.add(leaveUser);
		leftPanel.add(actPaslbl);

		return leftPanel;
	}

	private JPanel CenterPanel() {
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.decode(CustomColors.bgColor));
		centerPanel.setLayout(null);

		JLabel auser = new JLabel("<html><u>Available users</u></html>");
		auser.setFont(CustomFonts.font1);
		auser.setBounds(30, 10, 300, 25);

		JScrollPane scrollPane = new JScrollPane(availableUserTable());
		scrollPane.setBounds(30, 50, 700, 300);

		centerPanel.add(scrollPane);
		centerPanel.add(auser);

		return centerPanel;
	}

	private JPanel RightPanel() {
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.decode(CustomColors.bgColor));
		rightPanel.setPreferredSize(new Dimension(250, 0));
		rightPanel.setLayout(null);

		addRolelbl = new JLabel("<html><u>Role</u></html>");
		addRolelbl.setFont(CustomFonts.font1);
		addRolelbl.setBounds(10, 10, 300, 25);

		JScrollPane pane = new JScrollPane(displayUserRoles());
		pane.setBounds(10, 50, 200, 300);

		roleTxt = new JTextField();
		roleTxt.setBounds(10, 360, 200, 25);
		roleTxt.setEditable(false);

		addRole = new JButton("Add role");
		addRole.setBackground(Color.white);
		addRole.setForeground(Color.white);
		addRole.setBounds(60, 395, 100, 25);
		addRole.setEnabled(false);

		rightPanel.add(addRolelbl);
		rightPanel.add(pane);
		rightPanel.add(roleTxt);
		rightPanel.add(addRole);
		return rightPanel;
	}

	private void refreshRoleTable() {
		ResultSet rs = roleService.getAllRoles();
		if (rs != null) {
			roleTbl.setModel(DbUtils.resultSetToTableModel(rs));
		} else {
			roleTbl.setModel(null);
			System.err.println("User role has no data");
		}
		roleTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(roleTbl, SwingConstants.CENTER);
	}

	private JTable displayUserRoles() {
		roleTbl = new JTable();
		ResultSet rs = roleService.getAllRoles();

		if (rs != null) {
			roleTbl.setModel(DbUtils.resultSetToTableModel(rs));
		} else {
			roleTbl.setModel(null);
			System.err.println("User role has no data");
		}
		roleTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(roleTbl, SwingConstants.CENTER);

		return roleTbl;
	}

	private void refreshAvailableUserTable() {
		ResultSet rs = userService.getAllUsers();
		if (rs != null) {
			avlUserTbl.setModel(DbUtils.resultSetToTableModel(rs));
		} else {
			avlUserTbl.setModel(null);
			System.err.println("User table has no data");
		}
		avlUserTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(avlUserTbl, SwingConstants.CENTER);
	}

	// populate available uses in table
	private JTable availableUserTable() {
		avlUserTbl = new JTable();
		userService = new UserServiceImpl();
		ResultSet rs = userService.getAllUsers();
		if (rs != null) {
			avlUserTbl.setModel(DbUtils.resultSetToTableModel(rs));
		} else {
			avlUserTbl.setModel(null);
			System.err.println("User table has no data");
		}
		avlUserTbl.getTableHeader().setFont(CustomFonts.textFont);
		Utilities.setCellsAlignment(avlUserTbl, SwingConstants.CENTER);

		return avlUserTbl;
	}

	// search users information according to contact number
	protected void searchUser(String num) {
		userService = new UserServiceImpl();
		UserModel m = userService.searchUserByNum(num);

		if (m != null) {
			user_id = m.getUserId();
			userNametxt.setText(m.getName());
			userAddresstxt.setText(m.getAddress());
			userContacttxt.setText(m.getContact());
			userEmailtxt.setText(m.getEmail());
			roleJC.setModel(new DefaultComboBoxModel<String>(new String[] { getRoleNameById(m.getRole()) }));
			setUserStatusOnScreen();

		} else {
			clearUserDetails();
			roleJC.setModel(new DefaultComboBoxModel<String>(getUserRoles().toArray(new String[1])));

		}
	}

	// retrieves the role according to id
	private String getRoleNameById(int id) {
		String role = roleService.getRoleById(id);
		if (role != null) {
			return role;
		} else {
			return "null";
		}
	}

	// retrieves all the roles from tbl_role
	private List<String> getUserRoles() {
		List<String> roles = new ArrayList<String>();
		roleService = new RoleService();
		List<RoleModel> rm = roleService.getRoles();

		if (rm != null) {
			for (RoleModel roleModel : rm) {
				roles.add(roleModel.getRole());
			}
			return roles;
		} else {
			System.err.println("List of role is null;");
			return null;
		}
	}

	// inserts the role into roleCHeckbox
	private JComboBox<String> getRoleCB() {
		if (roleJC == null) {
			roleJC = new JComboBox<>();
			roleJC.setBounds(10, 260, 230, 25);

			if (getUserRoles() != null) {
				roleJC.setModel(new DefaultComboBoxModel<String>(getUserRoles().toArray(new String[1])));
			} else {
				roleJC.setModel(new DefaultComboBoxModel<String>(new String[] { "No data" }));
			}
		}
		return roleJC;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == saveUser) {
			saveUserDetails();
			refreshAvailableUserTable();
		}
		if (e.getSource() == clearUser) {
			clearUserDetails();

		}
		if (e.getSource() == editUser) {
			editUserDetail();
		}
		if (e.getSource() == leaveUser) {
			userLeaved();
		}
		if (e.getSource() == addRole) {
			insertNewRole();
		}
	}

	private void insertNewRole() {
		boolean b;
		if (!roleTxt.getText().isEmpty()) {
			b = roleService.insertNewRole(roleTxt.getText());
			if (b) {
				JOptionPane.showMessageDialog(null, "Role inserted successfully ", "Role insertion",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to insert role ", "Role insertion",
						JOptionPane.ERROR_MESSAGE);
			}
			refreshRoleTable();
		} else {
			JOptionPane.showMessageDialog(null, "Please insert role ", "Empty field", JOptionPane.ERROR_MESSAGE);
		}
	}

	// when user leaves
	private void userLeaved() {
		if (user_id != 0) {
			boolean b = userService.userLeaved(user_id);
			if (b) {
				clearUserDetails();
				JOptionPane.showMessageDialog(null, "Successfully removed user !", "User Remove",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				clearUserDetails();
				JOptionPane.showMessageDialog(null, "Failed to remove user !", "User Remove",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Select user by using contact number", "Select User",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// update user details
	private void editUserDetail() {
		if (userNametxt.getText().isEmpty() || userAddresstxt.getText().isEmpty() || userContacttxt.getText().isEmpty()
				|| userEmailtxt.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Field may be empty !", "Empty field", JOptionPane.ERROR_MESSAGE);
		} else {
			int roleid = roleService.getRoleIdByName(roleJC.getSelectedItem().toString());
			UserModel m = new UserModel(user_id, userNametxt.getText(), userAddresstxt.getText(),
					userContacttxt.getText(), userEmailtxt.getText(), roleid);
			boolean b;
			b = userService.editUser(m);
			if (b) {
				clearUserDetails();
				JOptionPane.showMessageDialog(null, "User updated successfully", "Update",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				clearUserDetails();
				JOptionPane.showMessageDialog(null, "Failed to update user", "Update", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// displays user is active or leaved
	private void setUserStatusOnScreen() {
		int user_stat = userService.getUserStatus(user_id);

		if (user_stat != 0) {
			if (user_stat == GlobalVariable.emp_status_active) {
				actPaslbl.setText("<html><i>(Active)<i><html>");
				actPaslbl.setForeground(Color.decode(CustomColors.greenColor));
				actPaslbl.setVisible(true);
			}
			if (user_stat == GlobalVariable.emp_status_leaved) {
				actPaslbl.setText("<html><i>(Leaved)<i><html>");
				actPaslbl.setForeground(Color.decode(CustomColors.redColor));
				actPaslbl.setVisible(true);
			}
		} else {
			actPaslbl.setVisible(false);
		}
	}

	// clear user information from the textfield
	private void clearUserDetails() {
		userNametxt.setText("");
		userAddresstxt.setText("");
		userContacttxt.setText("");
		userEmailtxt.setText("");
		passTxt.setText("");
		user_id = 0;
		actPaslbl.setVisible(false);
	}

	// saves user information to db
	private void saveUserDetails() {
		if (userNametxt.getText().isEmpty() || userAddresstxt.getText().isEmpty() || userContacttxt.getText().isEmpty()
				|| userEmailtxt.getText().isEmpty() || passTxt.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Field may be empty !", "Empty field", JOptionPane.ERROR_MESSAGE);
		} else {
			int roleId = roleService.getRoleIdByName(roleJC.getSelectedItem().toString());
			UserModel m = new UserModel(userNametxt.getText(), userAddresstxt.getText(), userContacttxt.getText(),
					userEmailtxt.getText(), passTxt.getText(), roleId, Utilities.getCurrentDateTime(),
					GlobalVariable.emp_status_active);

			System.out.println(m.toString());
			boolean b;
			userService = new UserServiceImpl();
			b = userService.addUser(m);
			if (b) {
				clearUserDetails();
				JOptionPane.showMessageDialog(null, "User added successfully !", "Add User",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				clearUserDetails();
				JOptionPane.showMessageDialog(null, "Failed to add user ! User may exist.", "Add User",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
