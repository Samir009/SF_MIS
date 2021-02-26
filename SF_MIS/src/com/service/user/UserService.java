package com.service.user;

import java.sql.ResultSet;

import com.model.UserModel;

public interface UserService {
	boolean addUser(UserModel m);

	ResultSet loginUser(String u, String p);

	UserModel searchUserByNum(String num);

	boolean editUser(UserModel m);

	int getUserStatus(int id);

	boolean userLeaved(int id);

	ResultSet getAllUsers();
}
