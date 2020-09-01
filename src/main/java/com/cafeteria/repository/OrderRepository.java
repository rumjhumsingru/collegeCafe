package com.cafeteria.repository;

import org.springframework.data.repository.CrudRepository;

import com.cafeteria.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
