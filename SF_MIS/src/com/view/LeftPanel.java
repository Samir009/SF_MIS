package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import util.CustomColors;
import util.CustomFonts;
import util.ScreenDimension;
import util.Utilities;

public class LeftPanel extends JPanel implements ActionListener, Runnable {

	private Utilities utilities = new Utilities();

	private static final long serialVersionUID = 1L;

	private JPanel panel;

	private JLabel timelbl = new JLabel("Current time");
	private JLabel datelbl = new JLabel("Current date");

	private JButton btnTakeOrder, btnViewOrder, btnAddProduct, btnEmployee, btnUser;

	private Border blackline = BorderFactory.createTitledBorder("Actions");

	public LeftPanel() {
		setLayout(new BorderLayout());
		Thread t = new Thread(this);
		t.start();
		init();
		setVisible(true);
	}

	public void init() {
		panel = new JPanel();
		panel.setBackground(Color.decode("0xF5EFE1"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// for JSeparator in left panel
		JSeparator separat = new JSeparator(SwingConstants.HORIZONTAL);
		Dimension ddd = separat.getPreferredSize();
		ddd.width = separat.getMaximumSize().width;
		separat.setMaximumSize(ddd);

		JSeparator separate = new JSeparator(SwingConstants.HORIZONTAL);
		Dimension d = separate.getPreferredSize();
		d.width = separate.getMaximumSize().width;
		separate.setMaximumSize(d);

		JSeparator separater = new JSeparator(SwingConstants.HORIZONTAL);
		Dimension dd = separate.getPreferredSize();
		dd.width = separate.getMaximumSize().width;
		separate.setMaximumSize(dd);

		panel.add(Box.createRigidArea(new Dimension(10, 15)));
		btnTakeOrder = new JButton("Take Order", new ImageIcon(utilities.getOrderIcon()));
		btnTakeOrder.setBackground(Color.white);
		btnTakeOrder.setForeground(Color.decode(CustomColors.blackColor));
		btnViewOrder = new JButton("View Order", new ImageIcon(utilities.getViewIcon()));
		btnViewOrder.setBackground(Color.white);
		btnViewOrder.setForeground(Color.decode(CustomColors.blackColor));
		btnAddProduct = new JButton("Add Product", new ImageIcon(utilities.getProductIcon()));
		btnAddProduct.setBackground(Color.white);
		btnAddProduct.setForeground(Color.decode(CustomColors.blackColor));
		btnEmployee = new JButton("Employee", new ImageIcon(utilities.getEmployeeIcon()));
		btnEmployee.setBackground(Color.white);
		btnEmployee.setForeground(Color.decode(CustomColors.blackColor));
		btnUser = new JButton("     User     ", new ImageIcon(utilities.getUserIcon()));
		btnUser.setBackground(Color.white);
		btnUser.setForeground(Color.decode(CustomColors.blackColor));

//		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		btnTakeOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnViewOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAddProduct.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEmployee.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnUser.setAlignmentX(Component.CENTER_ALIGNMENT);

		timelbl.setFont(CustomFonts.font1);
		timelbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		datelbl.setFont(CustomFonts.font1);
		datelbl.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(timelbl);
		panel.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(datelbl);
		panel.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(separat);
		panel.add(Box.createRigidArea(new Dimension(10, 20)));
		panel.add(btnTakeOrder);
		panel.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(btnViewOrder);
		panel.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(btnAddProduct);
		panel.add(Box.createRigidArea(new Dimension(10, 20)));
		panel.add(separate);
		panel.add(Box.createRigidArea(new Dimension(10, 20)));
		panel.add(btnEmployee);
		panel.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(btnUser);
		panel.add(Box.createRigidArea(new Dimension(10, 20)));
		panel.add(separater);
		panel.add(Box.createRigidArea(new Dimension(10, 20)));

		setBorder(blackline);
		setPreferredSize(new Dimension(ScreenDimension.getScreenWidth() / 6, 0));
		add(panel, BorderLayout.CENTER);

		btnTakeOrder.addActionListener(this);
		btnViewOrder.addActionListener(this);
		btnAddProduct.addActionListener(this);
		btnEmployee.addActionListener(this);
		btnUser.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnViewOrder) {
			CenterPanel.viewOrderPane();
			repaint();
			revalidate();
		}
		if (e.getSource() == btnTakeOrder) {
			CenterPanel.displayVerticalPane();
			repaint();
			revalidate();
		}
		if (e.getSource() == btnAddProduct) {
			CenterPanel.displayAddProductPane();
			repaint();
			revalidate();
		}
		if (e.getSource() == btnEmployee) {
			CenterPanel.displayEmpPane();
			repaint();
			revalidate();
		}
		if (e.getSource() == btnUser) {
			CenterPanel.displayUserPane();
			repaint();
			revalidate();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			Calendar cal = Calendar.getInstance();

			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa");
			Date date = cal.getTime();
			String time = sdf.format(date);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date fulldate = cal.getTime();
			String currentDate = dateFormat.format(fulldate);

			try {
				Thread.sleep(1000);

				timelbl.setText(time);
				datelbl.setText(currentDate);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
