package com.example.demo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.model.VendorItem;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VendorItemRepository;
import com.example.demo.security.JwtProvider;

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

	public ResponseEntity<?> updateImpl(User newUser, HttpServletRequest request) {

		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		if (userRepository.findByUserName(username) != null) {
			User oldUser = userRepository.findByUserName(username);
			newUser.setUserId(oldUser.getUserId());
			newUser.setUserType(oldUser.getUserType());
			Token tempToken = oldUser.getToken();
			oldUser = newUser;
			oldUser.setPassword(encoder.encode(newUser.getPassword()));
			oldUser.setToken(tempToken);
			userRepository.save(oldUser);
			return new ResponseEntity<>(new Response("200", "Succesfully updated", oldUser), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<?> showVendorItems(int vendorId) {
		if (userRepository.findById(vendorId) != null && vendorItemRepository.findByUserUserId(vendorId) != null
				&& !vendorItemRepository.findByUserUserId(vendorId).isEmpty()) {

			List<VendorItem> listOfVendorItem = vendorItemRepository.findByUserUserId(vendorId);
			return new ResponseEntity(new Response("200", "Succesfully updated", listOfVendorItem), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> ListOfVendors() {

		List<User> vendors = userRepository.findByUserTypeUserTypeId(2);
		System.out.println("in show vendor service");
		return new ResponseEntity(new Response("200", "List of Vendors", vendors), HttpStatus.OK);
	}

}
