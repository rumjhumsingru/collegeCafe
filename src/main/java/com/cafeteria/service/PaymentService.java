package com.cafeteria.service;

import com.cafeteria.domain.Payment;
import com.cafeteria.domain.UserPayment;

public interface PaymentService {
	Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
