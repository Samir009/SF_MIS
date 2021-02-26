package com.service.emp;

import com.model.EmpModel;

public interface EmpService {

	boolean insertEmployee(EmpModel emp);

	boolean updateEmployee(EmpModel e);

	EmpModel getEmployeeByNumber(String num);

	EmpModel getEmployeeById(int id);

	int getEmpStatus(int id);

	boolean setEmpStatusActive(int id);

	boolean setEmpStatusLeaved(int id);
}
