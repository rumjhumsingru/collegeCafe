package com.cafeteria.service;

import com.cafeteria.domain.ShippingAddress;
import com.cafeteria.domain.UserShipping;

public interface ShippingAddressService {
	ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
