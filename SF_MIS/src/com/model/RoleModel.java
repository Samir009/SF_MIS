package com.model;

public class RoleModel {
	private int role_id;
	private String role;

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public RoleModel(String role) {
		super();
		this.role = role;
	}

	public RoleModel(int role_id, String role) {
		super();
		this.role_id = role_id;
		this.role = role;
	}

	@Override
	public String toString() {
		return "RoleModel [role_id=" + role_id + ", role=" + role + "]";
	}

}
