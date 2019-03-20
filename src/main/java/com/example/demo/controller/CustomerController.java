package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.CustomerService;

@RestController
public class CustomerController {
	@Autowired
	CustomerService customerService;

	@PreAuthorize("hasRole('ROLE_customer')")
	@PostMapping("/customer")
	public ResponseEntity<?> EditCustomer(@RequestBody User user, HttpServletRequest request) {

		return customerService.updateImpl(user, request);

	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/vendor") // to show all the vendors
	public ResponseEntity<?> showVendors() {
		return customerService.ListOfVendors();
	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/vendorItems/{vendorId}") // show items to Customer
	public ResponseEntity<?> showVendorItem(@PathVariable("vendorId") int vendorId) {
		return customerService.showVendorItems(vendorId);
	}

}
