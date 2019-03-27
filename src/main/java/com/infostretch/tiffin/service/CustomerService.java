package com.infostretch.tiffin.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infostretch.tiffin.dto.UserDTO;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.repository.UserRepository;
import com.infostretch.tiffin.repository.VendorItemRepository;
import com.infostretch.tiffin.security.JwtProvider;
import com.infostretch.tiffin.utility.UtilityClass;

@Service
public class CustomerService {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	UserRepository userRepository;
	@Autowired
	VendorItemRepository vendorItemRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	UtilityClass utilityClass;
	public ResponseEntity<User> updateImpl(UserDTO newUser, HttpServletRequest request) {
		return utilityClass.setUserData(newUser, request);
	}

	
}
