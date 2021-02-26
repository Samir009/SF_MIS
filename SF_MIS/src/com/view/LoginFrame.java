package com.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.service.user.RoleService;
import com.service.user.UserService;
import com.service.user.UserServiceImpl;

import util.CustomColors;
import util.CustomFonts;
import util.GlobalVariable;
import util.Utilities;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Utilities utilities = new Utilities();

	private JPanel contentPane;
	private JLabel loginLabel;
	private JLabel lblNewLabel;
	private JTextField usernameTxt;
	private JLabel lblNewLabel_1;
	private JPasswordField passwordTxt;
	private JButton btnLogin;
	private JLabel lblNewLabel_2;
	private JButton btnRegister;

	private ResultSet userRs;

	public static void main(String[] args) {
		new LoginFrame();
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setBounds(0, 0, 500, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		setBackground(Color.decode(CustomColors.bgColor));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBackground(Color.decode(CustomColors.bgColor));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLoginLabel());
		contentPane.add(getLblNewLabel());
		contentPane.add(getUsernameTxt());
		contentPane.add(getLblNewLabel_1());
		contentPane.add(getPasswordTxt());
		contentPane.add(getBtnLogin());
		contentPane.add(getLblNewLabel_2());
		contentPane.add(getBtnRegister());

		setVisible(true);
		this.repaint();
		this.revalidate();

	}

	private JLabel getLoginLabel() {
		if (loginLabel == null) {
			loginLabel = new JLabel("Login");
			loginLabel.setBounds(218, 170, 60, 31);
			loginLabel.setFont(CustomFonts.font1);
		}
		return loginLabel;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Username");
			lblNewLabel.setBounds(148, 223, 89, 14);
		}
		return lblNewLabel;
	}

	private JTextField getUsernameTxt() {
		if (usernameTxt == null) {
			usernameTxt = new JTextField();
			usernameTxt.setText("test");
			usernameTxt.setBounds(148, 238, 188, 25);
			usernameTxt.setColumns(10);
		}
		return usernameTxt;
	}

	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Password");
			lblNewLabel_1.setBounds(148, 268, 89, 14);
		}
		return lblNewLabel_1;
	}

	private JTextField getPasswordTxt() {
		if (passwordTxt == null) {
			passwordTxt = new JPasswordField();
			passwordTxt.setText("qwerty");
			passwordTxt.setBounds(148, 283, 188, 25);
			passwordTxt.setColumns(10);
		}
		return passwordTxt;
	}

	private JButton getBtnLogin() {
		if (btnLogin == null) {
			btnLogin = new JButton("Login", new ImageIcon(utilities.getLoginIcon()));
			btnLogin.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void actionPerformed(ActionEvent e) {
					if (usernameTxt.getText().isEmpty() || passwordTxt.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "All fields are required !", "Empty field",
								JOptionPane.ERROR_MESSAGE);
					} else {

						UserService userService = new UserServiceImpl();
						userRs = userService.loginUser(usernameTxt.getText(), passwordTxt.getText());

						try {

							if (userRs != null) {
								GlobalVariable.currentUserId = userRs.getInt("user_id");
								GlobalVariable.currentUser = userRs.getString("name");
								findRole(userRs.getInt("role"));
							} else {
								System.err.println("User didn't found");
								JOptionPane.showMessageDialog(null, "Login failed :(", "Login failed",
										JOptionPane.ERROR_MESSAGE);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			btnLogin.setBounds(148, 320, 188, 40);
			btnLogin.setBackground(Color.decode(CustomColors.bgColor));
			btnLogin.setForeground(Color.decode(CustomColors.blackColor));
		}
		return btnLogin;
	}

	protected void findRole(int id) {
		RoleService roleService = new RoleService();
		String role = roleService.getRoleById(id);
		GlobalVariable.userRole = role;
		if (role != null) {
			if (role.equals(GlobalVariable.role_admin)) {
				new MyFrame().setVisible(true);
				dispose();
			}
			if (role.equals(GlobalVariable.role_manager)) {

			}
			if (role.equals(GlobalVariable.role_cashier)) {
				new CashierFrame().setVisible(true);
				dispose();
			}
			if (role.equals(GlobalVariable.role_guest)) {

			}
		} else {
			JOptionPane.showMessageDialog(null, "You are not authorized !", "User Not allowed",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Not register? click on register button");
			lblNewLabel_2.setBounds(150, 430, 185, 14);
		}
		return lblNewLabel_2;
	}

	private JButton getBtnRegister() {
		if (btnRegister == null) {
			btnRegister = new JButton("Register", new ImageIcon(utilities.getRegisterIcon()));
			btnRegister.setBounds(148, 370, 188, 40);
			btnRegister.setBackground(Color.decode(CustomColors.bgColor));
			btnRegister.setForeground(Color.decode(CustomColors.blackColor));
			btnRegister.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new RegisterFrame().setVisible(true);
					dispose();
				}
			});
		}
		return btnRegister;
	}

}
