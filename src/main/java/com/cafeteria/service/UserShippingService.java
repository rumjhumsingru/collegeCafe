package com.cafeteria.service;

import com.cafeteria.domain.UserShipping;

public interface UserShippingService {
	UserShipping findById(Long id);
	
	void removeById(long id);
}
