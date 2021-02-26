package com.model;

public class ProductModel {
	private int prod_id, category;
	private float rate;
	private String name, color;

	public int getProd_id() {
		return prod_id;
	}

	public void setProd_id(int prod_id) {
		this.prod_id = prod_id;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public ProductModel() {
		super();
	}

	public ProductModel(int category, float rate, String name, String color) {
		super();
		this.category = category;
		this.rate = rate;
		this.name = name;
		this.color = color;
	}

	public ProductModel(int prod_id, int category, float rate, String name, String color) {
		super();
		this.prod_id = prod_id;
		this.category = category;
		this.name = name;
		this.rate = rate;
		this.color = color;
	}

	public ProductModel(float rate, String name) {
		super();
		this.rate = rate;
		this.name = name;
	}

	@Override
	public String toString() {
		return "ProductModel [prod_id=" + prod_id + ", category=" + category + ", name=" + name + ", rate=" + rate
				+ ", color=" + color + "]";
	}

}
