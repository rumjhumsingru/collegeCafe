package com.cafeteria.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Item {

	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	
	private String category;
	private String type;
	
	private double ourPrice;
	private boolean active=true;
	
	@Column(columnDefinition="text")
	private String description;
	private int inStockNumber;
	
	@Transient
	private MultipartFile foodImage;
	
	@OneToMany(mappedBy = "item")
	@JsonIgnore
	private List<ItemToCartItem> itemToCartItemList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	public double getOurPrice() {
		return ourPrice;
	}

	public void setOurPrice(double ourPrice) {
		this.ourPrice = ourPrice;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getInStockNumber() {
		return inStockNumber;
	}

	public void setInStockNumber(int inStockNumber) {
		this.inStockNumber = inStockNumber;
	}

	public MultipartFile getFoodImage() {
		return foodImage;
	}

	public void setFoodImage(MultipartFile foodImage) {
		this.foodImage = foodImage;
	}

	public List<ItemToCartItem> getItemToCartItemList() {
		return itemToCartItemList;
	}

	public void setItemToCartItemList(List<ItemToCartItem> itemToCartItemList) {
		this.itemToCartItemList = itemToCartItemList;
	}
	
	
	
}
