package com.cafeteria.repository;

import org.springframework.data.repository.CrudRepository;

import com.cafeteria.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

}
