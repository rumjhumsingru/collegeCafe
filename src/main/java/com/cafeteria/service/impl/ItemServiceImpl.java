package com.cafeteria.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafeteria.domain.Item;
import com.cafeteria.repository.ItemRepository;
import com.cafeteria.service.ItemService;



@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> findAll() {
		List<Item> itemList  = (List<Item>) itemRepository.findAll();
		List<Item> activeItemList = new ArrayList<>();
		
		for(Item item: itemList) {
			if(item.isActive())
			{
				activeItemList.add(item);
			}
		}
		
		return activeItemList;	}
	
	public Item findOne(Long id) {
		return itemRepository.findOne(id);
	}
	
	@Override
	public List<Item> findByCategory(String category) {
		List<Item> itemList = itemRepository.findByCategory(category);
		
		List<Item> activeItemList = new ArrayList<>();
		
		for(Item item: itemList) {
			if(item.isActive())
			{
				activeItemList.add(item);
			}
		}
		
		return activeItemList;
	}
	
	@Override
	public List<Item> findByType(String type) {
		List<Item> itemList = itemRepository.findByType(type);
		
		List<Item> activeItemList = new ArrayList<>();
		
		for(Item item: itemList) {
			if(item.isActive())
			{
				activeItemList.add(item);
			}
		}
		
		return activeItemList;
	}

}
