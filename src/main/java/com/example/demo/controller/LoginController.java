package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.LoginForm;
import com.example.demo.model.User;
import com.example.demo.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	LoginService loginService;

	@PostMapping("/signin")
	public ResponseEntity<?> signInUser(@Valid @RequestBody LoginForm loginRequest) {

		return loginService.signInUserImpl(loginRequest);
	}

	@PostMapping("/signup/{userType}")
	public ResponseEntity<?> registerUser(@RequestBody User signUpRequest,
			@PathVariable("userType") String userTypeName) {

		return loginService.registerUserImpl(signUpRequest, userTypeName);
	}

}
