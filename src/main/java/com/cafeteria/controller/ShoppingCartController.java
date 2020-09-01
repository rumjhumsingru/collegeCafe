package com.cafeteria.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafeteria.domain.CartItem;
import com.cafeteria.domain.Item;
import com.cafeteria.domain.ShoppingCart;
import com.cafeteria.domain.User;
import com.cafeteria.service.CartItemService;
import com.cafeteria.service.ItemService;
import com.cafeteria.service.ShoppingCartService;
import com.cafeteria.service.UserService;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@RequestMapping("/cart")
	public String shoppingCart(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		shoppingCartService.updateShoppingCart(shoppingCart);
		
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);
		
		List<Item> itemList = itemService.findAll();
		model.addAttribute("itemList", itemList);
		model.addAttribute("activeAll", true);

		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);
		model.addAttribute("cartFull",true);
		return "menu";
	}

	@RequestMapping("/addItem")
	public String addItem(
			@ModelAttribute("item") Item item,
			@ModelAttribute("qty") String qty,
			Model model, Principal principal
			) {
		User user = userService.findByUsername(principal.getName());
		item = itemService.findOne(item.getId());
		
		/*
		 * if (Integer.parseInt(qty) > item.getInStockNumber()) {
		 * model.addAttribute("notEnoughStock", true); //return
		 * "forward:/bookDetail?id="+item.getId(); return
		 * "forward:/shoppingCart/cart?id="+item.getId(); }
		 */
		
		CartItem cartItem = cartItemService.addItemToCartItem(item, user, Integer.parseInt(qty));
		model.addAttribute("addBookSuccess", true);
		model.addAttribute("cartFull",true);
		model.addAttribute("cartEmpty",false);
		
		//return "forward:/bookDetail?id="+item.getId();
		//return "forward:/shoppingCart/cart?id="+item.getId();
		List<Item> itemList = itemService.findAll();
		model.addAttribute("itemList", itemList);
		model.addAttribute("activeAll", true);

		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);
		
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		shoppingCartService.updateShoppingCart(shoppingCart);
		
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);
		
		return "redirect:/shoppingCart/cart";
		
	}
	
	@RequestMapping("/updateCartItem")
	public String updateShoppingCart(
			@ModelAttribute("id") Long cartItemId,
			@ModelAttribute("qty") int qty
			) {
		CartItem cartItem = cartItemService.findById(cartItemId);
		cartItem.setQty(qty);
		cartItemService.updateCartItem(cartItem);
		
		return "forward:/shoppingCart/cart";
	}
	
	@RequestMapping("/removeItem")
	public String removeItem(@RequestParam("id") Long id) {
		cartItemService.removeCartItem(cartItemService.findById(id));
		
		return "forward:/shoppingCart/cart";
	}
}
