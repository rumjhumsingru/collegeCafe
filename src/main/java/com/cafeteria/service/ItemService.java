package com.cafeteria.service;


import java.util.List;

import com.cafeteria.domain.Item;


public interface ItemService {
	List<Item> findAll();
	Item findOne(Long id);
	List<Item> findByCategory(String category);
	List<Item> findByType(String type);

}
