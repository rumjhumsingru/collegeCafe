package com.cafeteria.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cafeteria.domain.CartItem;
import com.cafeteria.domain.Order;
import com.cafeteria.domain.ShoppingCart;

public interface CartItemRepository extends CrudRepository<CartItem, Long>{
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	List<CartItem> findByOrder(Order order);
}
