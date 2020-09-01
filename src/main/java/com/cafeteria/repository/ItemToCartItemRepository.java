package com.cafeteria.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.cafeteria.domain.CartItem;
import com.cafeteria.domain.ItemToCartItem;

@Transactional
public interface ItemToCartItemRepository extends CrudRepository<ItemToCartItem, Long> {
	void deleteByCartItem(CartItem cartItem);
}
