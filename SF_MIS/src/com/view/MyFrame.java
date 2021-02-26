package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import util.CustomColors;
import util.GlobalVariable;
import util.ScreenDimension;

public class MyFrame extends JFrame implements ActionListener {
	JMenuBar mb = new JMenuBar();
	JMenu menu;
	JMenuItem logoutMenuItem;

	private static final long serialVersionUID = 1L;

	MyFrame() {
		System.err.println("Current user role is: " + GlobalVariable.currentUserId);

		setLayout(new BorderLayout());
		setSize(ScreenDimension.getScreenWidth(), ScreenDimension.getScreenHeight());
		setBackground(Color.decode(CustomColors.bgColor));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu = new JMenu("User");
		logoutMenuItem = new JMenuItem("Log out");
		menu.add(logoutMenuItem);
		mb.add(menu);

		add(new LeftPanel(), BorderLayout.WEST);
		add(new CenterPanel(), BorderLayout.CENTER);

		setJMenuBar(mb);
		setVisible(true);
		this.repaint();
		this.revalidate();

		logoutMenuItem.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logoutMenuItem) {
			GlobalVariable.currentUserId = 0;
			GlobalVariable.currentUser = "";
			GlobalVariable.userRole = "";
			new LoginFrame().setVisible(true);
			dispose();
		}
	}

	public static void main(String[] args) {
		new MyFrame();
	}
}