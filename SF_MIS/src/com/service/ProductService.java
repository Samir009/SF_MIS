package com.service;

import java.sql.ResultSet;
import java.util.List;

import com.model.ProductModel;

public interface ProductService {

	boolean addProduct(ProductModel m);

	List<ProductModel> fetchProductList();

	ProductModel fetchProductByName(String name);

	ResultSet fetchProduct();

	boolean editProductRate(int id, float rate);

	List<ProductModel> fetchProductNameAndRateByCatId(int id);

	float fetchProductRateByName(String nam);
}
