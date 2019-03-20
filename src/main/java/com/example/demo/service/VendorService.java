package com.example.demo.service;

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
public class VendorService {
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

	public ResponseEntity<?> addItemImpl(VendorItem vendorItem, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		System.out.println("username----------------------------------" + username);
		if (userRepository.findByUserName(username) != null) {
			vendorItem.setUser(userRepository.findByUserName(username));
			vendorItemRepository.save(vendorItem);
			return new ResponseEntity(new Response("200", "successfully added Item", vendorItem), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> editItem(int vendorItemId, VendorItem vendorItem, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		if (userRepository.findByUserName(username) != null) {
			User oldUser = userRepository.findByUserName(username);
			VendorItem oldVendorItem = vendorItemRepository.findById(vendorItemId).get();
			System.out.println("----------------------" + oldVendorItem);
			vendorItem.setVendorItemId(oldVendorItem.getVendorItemId());
			vendorItem.setUser(oldUser);
			vendorItemRepository.save(vendorItem);
			return new ResponseEntity(new Response("200", "successfully Edited Item", vendorItem), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> deleteItems(int vendorItemId, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		System.out.println("username----------------------------------" + username);
		if (userRepository.findByUserName(username) != null) {
			User oldUser = userRepository.findByUserName(username);
			VendorItem vendorItem = vendorItemRepository.findById(vendorItemId).get();
			vendorItemRepository.deleteById(vendorItem.getVendorItemId());
			return new ResponseEntity(new Response("200", "successfully Deleted Item", vendorItem), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}

}
