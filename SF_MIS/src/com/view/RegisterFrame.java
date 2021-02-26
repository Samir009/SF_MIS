package com.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.model.UserModel;
import com.service.user.UserService;
import com.service.user.UserServiceImpl;

import util.CustomColors;
import util.CustomFonts;
import util.GlobalVariable;
import util.Utilities;

public class RegisterFrame extends JFrame {

	private static final long serialVersionUID = 2256117827883683276L;

	private Utilities utilities = new Utilities();

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField fullnameTxt;
	private JLabel lblNewLabel_2;
	private JTextField addressTxt;
	private JLabel lblNewLabel_3;
	private JTextField contactTxt;
	private JLabel lblNewLabel_4;
	private JTextField emailTxt;
	private JLabel lblNewLabel_5;
	private JPasswordField passTxt;
	private JLabel lblNewLabel_6;
	private JPasswordField repassTxt;
	private JButton btnRegister;
	private JButton btnLogin;
	private JLabel lblNewLabel_7;

	private UserService userService;

	public static void main(String[] args) {
		new RegisterFrame();
	}

	public RegisterFrame() {
		setBounds(0, 0, 500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBackground(Color.decode(CustomColors.bgColor));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		contentPane.add(getLblNewLabel());
		contentPane.add(getLblNewLabel_1());
		contentPane.add(getFullnameTxt());
		contentPane.add(getLblNewLabel_2());
		contentPane.add(getAddressTxt());
		contentPane.add(getLblNewLabel_3());
		contentPane.add(getContactTxt());
		contentPane.add(getLblNewLabel_4());
		contentPane.add(getEmailTxt());
		contentPane.add(getLblNewLabel_5());
		contentPane.add(getPassTxt());
		contentPane.add(getLblNewLabel_6());
		contentPane.add(getRepassTxt());
		contentPane.add(getBtnRegister());
		contentPane.add(getBtnLogin());
		contentPane.add(getLblNewLabel_7());

		setVisible(true);
		this.repaint();
		this.revalidate();
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Register");
			lblNewLabel.setBounds(195, 74, 88, 26);
			lblNewLabel.setFont(CustomFonts.font1);
		}
		return lblNewLabel;
	}

	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Full name");
			lblNewLabel_1.setBounds(122, 115, 123, 15);
		}
		return lblNewLabel_1;
	}

	private JTextField getFullnameTxt() {
		if (fullnameTxt == null) {
			fullnameTxt = new JTextField();
			fullnameTxt.setBounds(122, 130, 235, 25);
			fullnameTxt.setColumns(10);
		}
		return fullnameTxt;
	}

	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Address");
			lblNewLabel_2.setBounds(122, 155, 123, 15);
		}
		return lblNewLabel_2;
	}

	private JTextField getAddressTxt() {
		if (addressTxt == null) {
			addressTxt = new JTextField();
			addressTxt.setBounds(122, 170, 235, 25);
			addressTxt.setColumns(10);
		}
		return addressTxt;
	}

	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("Contact");
			lblNewLabel_3.setBounds(122, 195, 134, 15);
		}
		return lblNewLabel_3;
	}

	private JTextField getContactTxt() {
		if (contactTxt == null) {
			contactTxt = new JTextField();
			contactTxt.setBounds(122, 210, 235, 25);
			contactTxt.setColumns(10);
		}
		return contactTxt;
	}

	private JLabel getLblNewLabel_4() {
		if (lblNewLabel_4 == null) {
			lblNewLabel_4 = new JLabel("Email");
			lblNewLabel_4.setBounds(122, 235, 123, 14);
		}
		return lblNewLabel_4;
	}

	private JTextField getEmailTxt() {
		if (emailTxt == null) {
			emailTxt = new JTextField();
			emailTxt.setBounds(122, 250, 235, 25);
			emailTxt.setColumns(10);
		}
		return emailTxt;
	}

	private JLabel getLblNewLabel_5() {
		if (lblNewLabel_5 == null) {
			lblNewLabel_5 = new JLabel("Password");
			lblNewLabel_5.setBounds(122, 275, 123, 15);
		}
		return lblNewLabel_5;
	}

	private JPasswordField getPassTxt() {
		if (passTxt == null) {
			passTxt = new JPasswordField();
			passTxt.setBounds(122, 290, 235, 25);
		}
		return passTxt;
	}

	private JLabel getLblNewLabel_6() {
		if (lblNewLabel_6 == null) {
			lblNewLabel_6 = new JLabel("Re-enter password");
			lblNewLabel_6.setBounds(122, 315, 134, 14);
		}
		return lblNewLabel_6;
	}

	private JPasswordField getRepassTxt() {
		if (repassTxt == null) {
			repassTxt = new JPasswordField();
			repassTxt.setBounds(122, 330, 235, 25);
		}
		return repassTxt;
	}

	private JButton getBtnRegister() {
		if (btnRegister == null) {
			btnRegister = new JButton("Register", new ImageIcon(utilities.getRegisterIcon()));
			btnRegister.setBackground(Color.decode(CustomColors.bgColor));
			btnRegister.setForeground(Color.decode(CustomColors.blackColor));
			btnRegister.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void actionPerformed(ActionEvent e) {

					if (!fullnameTxt.getText().isEmpty() || !addressTxt.getText().isEmpty()
							|| !contactTxt.getText().isEmpty() || !emailTxt.getText().isEmpty()) {
						if ((passTxt != null && repassTxt != null)) {
							if ((passTxt.getText().equals(repassTxt.getText()))) {
								addUser();
							} else {
								JOptionPane.showMessageDialog(null, "Password field not matched ", "Password",
										JOptionPane.ERROR_MESSAGE);
							}

						} else {
							JOptionPane.showMessageDialog(null, "Password field empty", "Password",
									JOptionPane.ERROR_MESSAGE);
						}

					} else {
						JOptionPane.showMessageDialog(null, "Fields may be empty", "Empty field",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			});
			btnRegister.setBounds(122, 370, 235, 40);
		}
		return btnRegister;
	}

	private void addUser() {
		userService = new UserServiceImpl();
		@SuppressWarnings("deprecation")
		UserModel model = new UserModel(fullnameTxt.getText(), addressTxt.getText(), contactTxt.getText(),
				emailTxt.getText(), passTxt.getText(), GlobalVariable.user_guest_role, Utilities.getCurrentDateTime(),
				GlobalVariable.emp_status_active);

		boolean b;
		b = userService.addUser(model);

		if (b) {
			JOptionPane.showMessageDialog(null, "User Registered successfully", "Registration successful",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "User may exist", "Registration Failed", JOptionPane.ERROR_MESSAGE);
		}
	}

	private JButton getBtnLogin() {
		if (btnLogin == null) {
			btnLogin = new JButton("Login", new ImageIcon(utilities.getLoginIcon()));
			btnLogin.setBackground(Color.decode(CustomColors.bgColor));
			btnLogin.setForeground(Color.decode(CustomColors.blackColor));
			btnLogin.setBounds(122, 430, 235, 40);

			btnLogin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new LoginFrame().setVisible(true);
					dispose();
				}
			});
		}
		return btnLogin;
	}

	private JLabel getLblNewLabel_7() {
		if (lblNewLabel_7 == null) {
			lblNewLabel_7 = new JLabel("Have an account? click on Login Button");
			lblNewLabel_7.setBounds(130, 480, 235, 14);
		}
		return lblNewLabel_7;
	}
}
