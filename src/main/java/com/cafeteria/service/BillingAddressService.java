package com.cafeteria.service;

import com.cafeteria.domain.BillingAddress;
import com.cafeteria.domain.UserBilling;

public interface BillingAddressService {
	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
