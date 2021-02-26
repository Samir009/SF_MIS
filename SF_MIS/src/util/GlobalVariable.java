package util;

public class GlobalVariable {

	public static int OrderNotReady = 2; // when order is not ready
	public static int OrderReady = 1; // when order is ready
	public static int NotDelivered = 2; // when order is not delivered
	public static int Delivered = 1; // when order is delivered
	public static int fullPaid = 1;
	public static int notPaid = 2;
	public static int partialPaid = 3;

	public static int defaultDiscount = 0;

	public static String cartOrdered = "y";
	public static String cartNotOrdered = "n";

	public static int emp_status_active = 1;
	public static int emp_status_leaved = 2;

	public static int user_guest_role = 4;

	public static String role_admin = "admin";
	public static String role_manager = "manager";
	public static String role_cashier = "cashier";
	public static String role_guest = "guest";

	public static int currentUserId = 0;
	public static String currentUser = "";
	public static String userRole = "";

}
