package com.cafeteria.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafeteria.domain.CartItem;
import com.cafeteria.domain.Item;
import com.cafeteria.domain.ShoppingCart;
import com.cafeteria.domain.User;
import com.cafeteria.service.CartItemService;
import com.cafeteria.service.ItemService;
import com.cafeteria.service.UserService;

@Controller
public class SearchController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@RequestMapping("/searchByCategory")
	public String searchByCategory(
			@RequestParam("category") String category, Model model, Principal principal
			) {
		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);
		
		if(principal!=null)
		{
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		

		
		String classActiveCategory = "active"+category;
		classActiveCategory = classActiveCategory.replaceAll("\\s+", "");
		classActiveCategory = classActiveCategory.replaceAll("&", "");
		model.addAttribute(classActiveCategory, true);
		
		List<Item> itemList = itemService.findByCategory(category);	
		
		if(principal!=null)
		{
		String username = principal.getName();
		User user = userService.findByUsername(username);
		
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		model.addAttribute("shoppingCart", shoppingCart);
		model.addAttribute("cartItemList", cartItemList);
		
		if(cartItemList.isEmpty())
		{
			model.addAttribute("cartEmpty",true);
		}
		else
		{
			model.addAttribute("cartFull",true);
		}
		} else
		{
			model.addAttribute("cartEmpty",true);
			List<String> cart = new ArrayList<>();
			model.addAttribute("cartItemList", cart);
		}
		
		if(itemList.isEmpty())
		{
			model.addAttribute("emptyList", true);
			return "menu";
		}
		
		model.addAttribute("itemList", itemList);
		
		return "menu";
	}

	@RequestMapping("/searchByType")
	public String searchByType(
			@RequestParam("type") String type, Model model, Principal principal
			) {
		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);
		
		if(principal!=null)
		{
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		String classActiveType = "active"+type;
		classActiveType = classActiveType.replaceAll("\\s+", "");
		classActiveType = classActiveType.replaceAll("&", "");
		model.addAttribute(classActiveType, true);
		List<Item> itemList = itemService.findByType(type);
		
		if(principal!=null)
		{
		String username = principal.getName();
		User user = userService.findByUsername(username);
		
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		model.addAttribute("shoppingCart", shoppingCart);

		model.addAttribute("cartItemList", cartItemList);
		if(cartItemList.isEmpty())
		{
			model.addAttribute("cartEmpty",true);
		}
		else
		{
			model.addAttribute("cartFull",true);
		}
		} else
		{
			model.addAttribute("cartEmpty",true);
			List<String> cart = new ArrayList<>();
			model.addAttribute("cartItemList", cart);
		}
		
		if(itemList.isEmpty())
		{
			model.addAttribute("emptyList", true);
			return "menu";
		}
		
		model.addAttribute("itemList", itemList);
		
		
		return "menu";
	}
}
