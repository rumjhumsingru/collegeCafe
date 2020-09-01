package com.cafeteria.service;

import java.util.Set;

import com.cafeteria.domain.User;
import com.cafeteria.domain.UserBilling;
import com.cafeteria.domain.UserPayment;
import com.cafeteria.domain.UserShipping;
import com.cafeteria.domain.security.PasswordResetToken;
import com.cafeteria.domain.security.UserRole;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail (String email);
	
	User findById(Long id);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
	
	void updateUserBilling(UserBilling userBilling, UserPayment userPayment,User user);
	
	void updateUserShipping(UserShipping userShipping, User user);

	void setUserDefaultPayment(Long userPaymentId, User user);
	
	void setUserDefaultShipping(Long userShippingId, User user);

}
