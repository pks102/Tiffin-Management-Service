package com.infostretch.tiffin.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infostretch.tiffin.dto.UserDTO;
import com.infostretch.tiffin.model.Response;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.repository.UserRepository;
import com.infostretch.tiffin.repository.VendorItemRepository;
import com.infostretch.tiffin.security.JwtProvider;
import com.infostretch.tiffin.utility.UtilityClass;

@Service
public class VendorService {
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

	public Response<User> updateImpl(UserDTO newUser, HttpServletRequest request) {

		
		return utilityClass.setUserData(newUser, request);
	}

	public Response<List<User>> listOfVendors() {

		Optional<List<User>> vendors = userRepository.findByUserTypeUserTypeId(2);
		if (vendors.isPresent()) {
			return new Response<>(vendors.get(),"Vendor list", HttpStatus.OK);
		} else {
			return new Response<>("No Vendor available",HttpStatus.NOT_FOUND);
		}

	}

}
