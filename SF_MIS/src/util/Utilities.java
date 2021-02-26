package util;

import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class Utilities {

//	Image addIcon = new ImageIcon(this.getClass().getResource("/plus.png")).getImage();
//	Image printIcon = new ImageIcon(this.getClass().getResource("/printer.png")).getImage();
//	Image pdfIcon = new ImageIcon(this.getClass().getResource("/file.png")).getImage();

//	Image loginIcon = new ImageIcon(this.getClass().getResource("/login.png")).getImage();
//	Image registerIcon = new ImageIcon(this.getClass().getResource("/register.png")).getImage();

	public Image getLoginIcon() {
		return new ImageIcon(this.getClass().getResource("/login.png")).getImage();
	}

	public Image getRegisterIcon() {
		return new ImageIcon(this.getClass().getResource("/register.png")).getImage();
	}

	public Image getAddIcon() {
		return new ImageIcon(this.getClass().getResource("/plus.png")).getImage();
	}

	public Image getInsertIcon() {
		return new ImageIcon(this.getClass().getResource("/insert.png")).getImage();
	}

	public Image getLeaveIcon() {
		return new ImageIcon(this.getClass().getResource("/leave.png")).getImage();
	}

	public Image getUpdateIcon() {
		return new ImageIcon(this.getClass().getResource("/update.png")).getImage();
	}

	public Image getPrinterIcon() {
		return new ImageIcon(this.getClass().getResource("/printer.png")).getImage();
	}

	public Image getPdfIcon() {
		return new ImageIcon(this.getClass().getResource("/file.png")).getImage();
	}

	public Image getOrderIcon() {
		Image orderIcon = new ImageIcon(this.getClass().getResource("/sent.png")).getImage();
		return orderIcon;
	}

	public Image getCartIcon() {
		Image cartIcon = new ImageIcon(this.getClass().getResource("/carts.png")).getImage();
		return cartIcon;
	}

	public Image getClearIcon() {
		Image clearIcon = new ImageIcon(this.getClass().getResource("/eraser.png")).getImage();
		return clearIcon;
	}

	public Image getSaveIcon() {
		Image saveIcon = new ImageIcon(this.getClass().getResource("/save.png")).getImage();
		return saveIcon;
	}

	public Image getViewIcon() {
		Image viewIcon = new ImageIcon(this.getClass().getResource("/viewicon.png")).getImage();
		return viewIcon;
	}

	public Image getProductIcon() {
		Image pIcon = new ImageIcon(this.getClass().getResource("/product.png")).getImage();
		return pIcon;
	}

	public Image getEmployeeIcon() {
		Image empIcon = new ImageIcon(this.getClass().getResource("/employee.png")).getImage();
		return empIcon;
	}

	public Image getUserIcon() {
		Image userIcon = new ImageIcon(this.getClass().getResource("/user.png")).getImage();
		return userIcon;
	}

	public Image getSearchIcon() {
		Image searchIcon = new ImageIcon(this.getClass().getResource("/search.png")).getImage();
		return searchIcon;
	}

	public Image getMoneyIcon() {
		Image moneyIcon = new ImageIcon(this.getClass().getResource("/salary.png")).getImage();
		return moneyIcon;
	}

	public Image getReadyIcon() {
		Image moneyIcon = new ImageIcon(this.getClass().getResource("/approval.png")).getImage();
		return moneyIcon;
	}

	public Image getDeliverIcon() {
		Image moneyIcon = new ImageIcon(this.getClass().getResource("/delivery-truck.png")).getImage();
		return moneyIcon;
	}

	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public static Date getCurrentDate() {

		Date date = new Date();
		String cd = formatter.format(date);

//		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
		try {
			java.util.Date d = formatter.parse(cd);
			java.sql.Date sqlStartDate = new java.sql.Date(d.getTime());

			System.out.println("Current sql date: " + sqlStartDate);
			return sqlStartDate;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static Date getSqlDate(Date date) {
		String cd = formatter.format(date);

		try {
			java.util.Date d = formatter.parse(cd);
			java.sql.Date sqlStartDate = new java.sql.Date(d.getTime());

			System.out.println("finishign date: " + cd);

			return sqlStartDate;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getCurrentDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println("currente dateTime: " + formatter.format(date));
		return formatter.format(date);
	}

	// aligns text center JTable
	public static void setCellsAlignment(JTable table, int alignment) {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(alignment);

		TableModel tableModel = table.getModel();

		for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
			table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
		}
	}
}
