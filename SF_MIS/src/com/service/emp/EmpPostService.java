package com.service.emp;

import java.util.List;

import com.model.EmpPostModel;

public interface EmpPostService {

	List<EmpPostModel> getAvailablePost();

	int getAvailablePostIdByName(String n);

	String getPostById(int id);

	boolean addEmpPost(String post, float sal);

}
