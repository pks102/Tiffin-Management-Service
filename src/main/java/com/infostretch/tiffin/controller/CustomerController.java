package com.infostretch.tiffin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infostretch.tiffin.dto.UserDTO;
import com.infostretch.tiffin.model.Response;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.service.CustomerService;

@RestController
public class CustomerController {
	@Autowired
	CustomerService customerService;

	@PreAuthorize("hasRole('ROLE_customer')")
	@PostMapping("/customer")
	public Response<User> editCustomer(@RequestBody UserDTO user, HttpServletRequest request) {

		return customerService.updateImpl(user, request);

	}

	

}
