package com.cafeteria.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafeteria.domain.Announcement;
import com.cafeteria.domain.CartItem;
import com.cafeteria.domain.Item;
import com.cafeteria.domain.Order;
import com.cafeteria.domain.ShoppingCart;
import com.cafeteria.domain.User;
import com.cafeteria.domain.UserBilling;
import com.cafeteria.domain.UserPayment;
import com.cafeteria.domain.UserShipping;
import com.cafeteria.domain.security.PasswordResetToken;
import com.cafeteria.domain.security.Role;
import com.cafeteria.domain.security.UserRole;
import com.cafeteria.service.AnnouncementService;
import com.cafeteria.service.CartItemService;
import com.cafeteria.service.ItemService;
import com.cafeteria.service.OrderService;
import com.cafeteria.service.UserPaymentService;
import com.cafeteria.service.UserService;
import com.cafeteria.service.UserShippingService;
import com.cafeteria.service.impl.UserSecurityService;
import com.cafeteria.utility.MailConstructor;
import com.cafeteria.utility.SecurityUtility;
import com.cafeteria.utility.USConstants;

@Controller
public class HomeController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSecurityService userSecurityService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private UserPaymentService userPaymentService;

	@Autowired
	private UserShippingService userShippingService;

	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AnnouncementService announcementService;
	
	@RequestMapping("/")
	public String index(Model model) {
		List<Announcement> announcementList = announcementService.findAll();
		model.addAttribute("announcementList", announcementList);
		return "index";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("classActiveSignup", true);
		return "myAccount";
	}
	
	@RequestMapping("/forget")
	public String forget(Model model) {
		model.addAttribute("classActiveForget", true);
		return "myAccount";
	}
	

	@RequestMapping("/menu")
	public String bookshelf(Model model, Principal principal) {

		if (principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}

		List<Item> itemList = itemService.findAll();
		model.addAttribute("itemList", itemList);
		model.addAttribute("activeAll", true);
		

		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);

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
		
		return "menu";
	}


	@RequestMapping("/forgetPassword")
	public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String email, Model model) {

		model.addAttribute("classActiveForgetPassword", true);

		User user = userService.findByEmail(email);

		if (user == null) {
			model.addAttribute("emailNotExist", true);
			model.addAttribute("classActiveForget", true);
			return "myAccount";
		}

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);

		userService.save(user);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

//		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
//				password);
//		mailSender.send(newEmail);

		//new added202010
		mailSender.send(mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password));


		model.addAttribute("forgetPasswordEmailSent", "true");
		model.addAttribute("classActiveForget", true);

		return "myAccount";
	}

	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		List<Order> orderList = user.getOrderList();
		Collections.reverse(orderList);
		model.addAttribute("orderList", orderList);

		UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping", userShipping);

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("listOfShippingAddresses", true);

		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("classActiveEdit", true);

		return "myProfile";
	}

	@RequestMapping("/listOfCreditCards")
	public String listOfCreditCards(Model model, Principal principal, HttpServletRequest request) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		List<Order> orderList = user.getOrderList();
		Collections.reverse(orderList);
		model.addAttribute("orderList", orderList);

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		return "myProfile";
	}

	@RequestMapping("/listOfShippingAddresses")
	public String listOfShippingAddresses(Model model, Principal principal, HttpServletRequest request) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		List<Order> orderList = user.getOrderList();
		Collections.reverse(orderList);
		model.addAttribute("orderList", orderList);

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);

		return "myProfile";
	}

	@RequestMapping("/addNewCreditCard")
	public String addNewCreditCard(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		model.addAttribute("addNewCreditCard", true);
		model.addAttribute("classActiveBilling", true);
    	model.addAttribute("listOfShippingAddresses", true);

		UserBilling userBilling = new UserBilling();
		UserPayment userPayment = new UserPayment();

		model.addAttribute("userBilling", userBilling);
		model.addAttribute("userPayment", userPayment);

		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		List<Order> orderList = user.getOrderList();
		Collections.reverse(orderList);
		model.addAttribute("orderList", orderList);

		return "myProfile";

	}

	@RequestMapping("/addNewShippingAddress")
	public String addNewShippingAddress(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		model.addAttribute("addNewShippingAddress", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfCreditCards", true);

		UserShipping userShipping = new UserShipping();

		model.addAttribute("userShipping", userShipping);

		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList()); 
		List<Order> orderList = user.getOrderList();
		Collections.reverse(orderList);
		model.addAttribute("orderList", orderList);

		return "myProfile";

	}

	@RequestMapping(value = "/addNewCreditCard", method = RequestMethod.POST)
	public String addNewCreditCard(@ModelAttribute("userPayment") UserPayment userPayment,
			@ModelAttribute("userBilling") UserBilling userBilling, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		userService.updateUserBilling(userBilling, userPayment, user);

		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		List<Order> orderList = user.getOrderList();
		Collections.reverse(orderList);
		model.addAttribute("orderList", orderList);

		return "myProfile";

	}

	@RequestMapping(value = "/addNewShippingAddress", method = RequestMethod.POST)
	public String addNewShippingAddressPost(@ModelAttribute("userShipping") UserShipping userShipping,
			Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		userService.updateUserShipping(userShipping, user);

		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfCreditCards", true);
		List<Order> orderList = user.getOrderList();
		Collections.reverse(orderList);
		model.addAttribute("orderList", orderList);

		return "myProfile";

	}

	@RequestMapping("/updateCreditCard")
	public String updateCreditCard(@ModelAttribute("id") Long creditCardId, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(creditCardId);

		if (user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			UserBilling userBilling = userPayment.getUserBilling();
			model.addAttribute("userPayment", userPayment);
			model.addAttribute("userBilling", userBilling);

			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);

			model.addAttribute("addNewCreditCard", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			List<Order> orderList = user.getOrderList();
			Collections.reverse(orderList);
			model.addAttribute("orderList", orderList);

			return "myProfile";
		}
	}

	@RequestMapping("/updateUserShipping")
	public String updateUserShipping(@ModelAttribute("id") Long shippingAddressId, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(shippingAddressId);

		if (user.getId() != userShipping.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);

			model.addAttribute("userShipping", userShipping);

			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);

			model.addAttribute("addNewShippingAddress", true);
			model.addAttribute("classActiveShipping", true);
			model.addAttribute("listOfCreditCards", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			List<Order> orderList = user.getOrderList();
			Collections.reverse(orderList);
			model.addAttribute("orderList", orderList);

			return "myProfile";
		}
	}

	@RequestMapping(value = "/setDefaultPayment", method = RequestMethod.POST)
	public String setDefaultPayment(@ModelAttribute("defaultUserPaymentId") Long defaultPaymentId, Principal principal,
			Model model) {
		User user = userService.findByUsername(principal.getName());
				
		userService.setUserDefaultPayment(defaultPaymentId, user);

		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		List<Order> orderList = user.getOrderList();
		Collections.reverse(orderList);
		model.addAttribute("orderList", orderList);

		return "myProfile";

	}

	@RequestMapping(value = "/setDefaultShippingAddress", method = RequestMethod.POST)
	public String setDefaultShippingAddress(@ModelAttribute("defaultShippingAddressId") Long defaultShippingId,
			Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultShipping(defaultShippingId, user);

		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		List<Order> orderList = user.getOrderList();
		Collections.reverse(orderList);
		model.addAttribute("orderList", orderList);

		return "myProfile";

	}

	@RequestMapping("/removeCreditCard")
	public String removeCreditCard(@ModelAttribute("id") Long creditCardId, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(creditCardId);

		if (user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			userPaymentService.removeById(creditCardId);

			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			List<Order> orderList = user.getOrderList();
			Collections.reverse(orderList);
			model.addAttribute("orderList", orderList);

			return "myProfile";
		}

	}

	@RequestMapping("/removeUserShipping")
	public String removeUserShipping(

			@ModelAttribute("id") Long userShippingId, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(userShippingId);

		if (user.getId() != userShipping.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);

			userShippingService.removeById(userShippingId);

			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveShipping", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			List<Order> orderList = user.getOrderList();
			Collections.reverse(orderList);
			model.addAttribute("orderList", orderList);

			return "myProfile";
		}

	}

	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username, Model model) throws Exception {
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);

		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			model.addAttribute("classActiveSignup", true);
			return "myAccount";
		}

		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);
			model.addAttribute("classActiveSignup", true);
			return "myAccount";
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);

		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

//		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
//				password);
//
//		mailSender.send(email);
		
		
		
		//new added 202010
		mailSender.send(mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password));


		model.addAttribute("emailSent", "true");
		model.addAttribute("orderList", user.getOrderList());
		model.addAttribute("classActiveSignup", true);
		return "myAccount";
	}

	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		User user = passToken.getUser();
		String username = user.getUsername();

		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("user", user);

		model.addAttribute("classActiveEdit", true);
		model.addAttribute("orderList", user.getOrderList());
		return "myProfile";
	}
	
	@RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
	public String updateUserInfo(
			@ModelAttribute("user") User user,
			@ModelAttribute("newPassword") String newPassword,
			Model model
			) throws Exception {
		User currentUser = userService.findById(user.getId());
		
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		
		/*check email already exists*/
		if (userService.findByEmail(user.getEmail())!=null) {
			if(userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
				model.addAttribute("emailExists", true);
//				model.addAttribute("listOfCreditCards", true);
//				model.addAttribute("listOfShippingAddresses", true);
//				model.addAttribute("userPaymentList", user.getUserPaymentList());
//				model.addAttribute("userShippingList", user.getUserShippingList());
				return "myProfile";
			}
		}
		
		/*check username already exists*/
		if (userService.findByUsername(user.getUsername())!=null) {
			if(userService.findByUsername(user.getUsername()).getId() != currentUser.getId()) {
				model.addAttribute("usernameExists", true);
//				model.addAttribute("listOfCreditCards", true);
//				model.addAttribute("listOfShippingAddresses", true);
//				model.addAttribute("userPaymentList", user.getUserPaymentList());
//				model.addAttribute("userShippingList", user.getUserShippingList());
				return "myProfile";
			}
		}
		
//		update password
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			if(passwordEncoder.matches(user.getPassword(), dbPassword)){
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);
				model.addAttribute("classActiveEdit", true);
//				model.addAttribute("listOfCreditCards", true);
//				model.addAttribute("listOfShippingAddresses", true);
//				model.addAttribute("userPaymentList", user.getUserPaymentList());
//				model.addAttribute("userShippingList", user.getUserShippingList());
				return "myProfile";
			}
		}
		
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		//currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		
		userService.save(currentUser);
		
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("classActiveEdit", true);
		
		UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		List<Order> orderList = user.getOrderList();
		if(!(orderList == null))
			Collections.reverse(orderList);
		model.addAttribute("orderList", orderList);
//		model.addAttribute("listOfCreditCards", true);
//		model.addAttribute("listOfShippingAddresses", true);
//		model.addAttribute("userPaymentList", user.getUserPaymentList());
//		model.addAttribute("userShippingList", user.getUserShippingList());
		
		return "myProfile";
	}
	
	@RequestMapping("/orderDetail")
	public String orderDetail(
			@RequestParam("id") Long orderId,
			Principal principal, Model model
			){
		User user = userService.findByUsername(principal.getName());
		Order order = orderService.findOne(orderId);
		
		if(order.getUser().getId()!=user.getId()) {
			return "badRequestPage";
		} else {
			List<CartItem> cartItemList = cartItemService.findByOrder(order);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("user", user);
			model.addAttribute("order", order);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			List<Order> orderList = user.getOrderList();
			Collections.reverse(orderList);
			model.addAttribute("orderList", orderList);
			
			UserShipping userShipping = new UserShipping();
			model.addAttribute("userShipping", userShipping);
			
			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveOrders", true);
			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("displayOrderDetail", true);
			
			return "myProfile";
		}
}
}
