package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	OrderService orderService;

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/order/{paymentMode}") // placed order
	ResponseEntity<?> order(@PathVariable("paymentMode") String paymentMode, HttpServletRequest request) {
		return orderService.orderImpl(paymentMode, request);
	}
}
