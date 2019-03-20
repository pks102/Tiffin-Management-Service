package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.model.VendorItem;
import com.example.demo.service.VendorService;

@RestController
public class VendorController {

	@Autowired
	VendorService vendorService;

	@PreAuthorize("hasRole('ROLE_vendor')")
	@PostMapping("/vendor")
	public ResponseEntity<?> EditVendor(@RequestBody User user, HttpServletRequest request) {

		return vendorService.updateImpl(user, request);

	}

	@PreAuthorize("hasRole('ROLE_vendor')")
	@PostMapping("/vendor/item") // add items for vendor
	public ResponseEntity<?> addItem(@RequestBody VendorItem vendorItem, HttpServletRequest request) {
		return vendorService.addItemImpl(vendorItem, request);

	}

	@PreAuthorize("hasRole('ROLE_vendor')")
	@PostMapping("/vendor/{vendorItemId}") // edit items for vendor
	public ResponseEntity<?> editItem(@PathVariable("vendorItemId") int vendorItemId,
			@RequestBody VendorItem vendorItem, HttpServletRequest request) {
		return vendorService.editItem(vendorItemId, vendorItem, request);
	}

	@PreAuthorize("hasRole('ROLE_vendor')")
	@DeleteMapping("vendor/{vendorItemId}") // delete items for vendor
	public ResponseEntity<?> deleteItem(@PathVariable("vendorItemId") int vendorItemId, HttpServletRequest request) {
		return vendorService.deleteItems(vendorItemId, request);

	}
}
