package com.cafeteria.service;

import com.cafeteria.domain.BillingAddress;
import com.cafeteria.domain.Order;
import com.cafeteria.domain.Payment;
import com.cafeteria.domain.ShippingAddress;
import com.cafeteria.domain.ShoppingCart;
import com.cafeteria.domain.User;

public interface OrderService {
	Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment,
			User user);
	
	Order findOne(Long id);
}
