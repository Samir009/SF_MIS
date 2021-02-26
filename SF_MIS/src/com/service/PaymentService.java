package com.service;

import java.sql.ResultSet;

import com.model.PaymentModel;

public interface PaymentService {

	boolean insertPayment(PaymentModel m);

	ResultSet getPaymentDetails(int id);

}
