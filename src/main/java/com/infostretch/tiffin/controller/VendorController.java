package com.infostretch.tiffin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infostretch.tiffin.dto.UserDTO;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.service.VendorService;

@RestController
public class VendorController {

	@Autowired
	VendorService vendorService;

	@PreAuthorize("hasRole('ROLE_vendor')")
	@PostMapping("/vendor")
	public ResponseEntity<User> editVendor(@RequestBody UserDTO user, HttpServletRequest request) {

		return vendorService.updateImpl(user, request);

	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/vendor") // to show all the vendors
	public ResponseEntity<List<User>> showVendors() {
		return vendorService.listOfVendors();
	}

}
