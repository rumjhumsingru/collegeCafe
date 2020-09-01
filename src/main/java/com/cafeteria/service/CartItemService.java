package com.cafeteria.service;

import java.util.List;

import com.cafeteria.domain.CartItem;
import com.cafeteria.domain.Item;
import com.cafeteria.domain.Order;
import com.cafeteria.domain.ShoppingCart;
import com.cafeteria.domain.User;

public interface CartItemService {
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	CartItem addItemToCartItem(Item item, User user, int qty);
	
	CartItem findById(Long id);
	
	void removeCartItem(CartItem cartItem);
	
	CartItem save(CartItem cartItem);
	
	List<CartItem> findByOrder(Order order); 
}
