package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.app.entities.User;
import com.app.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class MainController {

	@Autowired
	UserService userService;

	@ResponseBody
	@PostMapping(path = "/loginUser", consumes = "*")
	public void loginUser(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			String userEmail, String userPassword) {
		User validUser = userService.getUserByEmailAndPassword(userEmail, userPassword);
		System.out.println(userEmail);
		System.out.println(userPassword);
		System.out.println(validUser);

		try {
			if (validUser == null) {
				response.sendRedirect("index");
				return;
			}
			session.setAttribute("activeUser", validUser);
			response.sendRedirect("dashboard");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@PostMapping(path = "/signupUser", consumes = "*")
	public void signupUser(HttpServletRequest request,HttpServletResponse response,User user) {
		User registerUser = userService.saveUser(user);
		System.out.println(registerUser);
		try {
			response.sendRedirect("login");
			return;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
