package com.cafeteria.utility;

import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.cafeteria.domain.Order;
import com.cafeteria.domain.User;



@Component
public class MailConstructor {
	@Autowired
	private Environment env;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public SimpleMailMessage constructnewResetTokenEmail(
			String contextPath, Locale locale, String token, User user, String password
			) {
	
		
		String url = contextPath + "/newUser?token="+token;
		String message = "\nPlease click on this link to verify your email and edit your personal information. Your password is: \n"+password;
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Welcome to Cafe 9 - New User");
		email.setText(url+message);
		email.setFrom(env.getProperty("support.email"));
		return email;
		
	}
	
	//Test Password Template
	public MimeMessagePreparator constructResetTokenEmail(
			String contextPath, Locale locale, String token, User user, String password
			) {
	
		Context context= new Context();
		String url = contextPath + "/newUser?token="+token;
		
		//Passing Variables for email
		context.setVariable("username", user.getUsername());
		context.setVariable("password",password);
		context.setVariable("url",url);

		
		
		String emailTemplate = templateEngine.process("signupTemplateEmail", context);
		
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setTo(user.getEmail());
				email.setSubject("Welcome to Cafe 9!");
				email.setText(emailTemplate, true);
				email.setFrom(new InternetAddress("eat.cafe.9@gmail.com"));
			}
		};
		
		return messagePreparator;
		
	}
	//////////////////////////////////////
	
	
	public MimeMessagePreparator constructOrderConfirmationEmail (User user, Order order, Locale locale) {
		Context context = new Context();
		context.setVariable("order", order);
		context.setVariable("user", user);
		context.setVariable("cartItemList", order.getCartItemList());
		String text = templateEngine.process("orderConfirmationEmailTemplate", context);
		
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setTo(user.getEmail());
				email.setSubject("Order Confirmation - "+order.getId());
				email.setText(text, true);
				email.setFrom(new InternetAddress("eat.cafe.9@gmail.com"));
			}
		};
		
		return messagePreparator;
	}
}
