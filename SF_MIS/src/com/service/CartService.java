package com.service;

import java.sql.ResultSet;

import com.model.CartModel;

public interface CartService {
	boolean addToCart(CartModel m);

	ResultSet fetchCart();

	ResultSet fetchCartById(int id);

	float fetchCartTotalById(int id);

	boolean deleteFromCartById(int id);

	void setCartOrder(int i);

	String getCartOrderStat(int id);
}
