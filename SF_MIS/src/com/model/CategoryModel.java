package com.model;

public class CategoryModel {
	private int cat_id;
	private String category;

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public CategoryModel(int cat_id, String category) {
		super();
		this.cat_id = cat_id;
		this.category = category;
	}

	public CategoryModel(String category) {
		super();
		this.category = category;
	}

	@Override
	public String toString() {
		return "CategoryModel [cat_id=" + cat_id + ", category=" + category + "]";
	}

}
