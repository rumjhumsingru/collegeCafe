package com.cafeteria.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cafeteria.domain.Item;


public interface ItemRepository extends CrudRepository<Item, Long> {
	
	List<Item> findByCategory(String category);
	List<Item> findByType(String type);


}
