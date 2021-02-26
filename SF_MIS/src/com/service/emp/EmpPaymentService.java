package com.service.emp;

import java.sql.ResultSet;

import com.model.EmpPaymentModel;

public interface EmpPaymentService {
	boolean paySalary(EmpPaymentModel model);

	ResultSet getPaymentTable();
}
