package com.infostretch.tiffin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infostretch.tiffin.dto.LoginForm;
import com.infostretch.tiffin.dto.UserDTO;
import com.infostretch.tiffin.model.Response;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	LoginService loginService;

	@PostMapping("/signin")
	public Response<User> signInUser(@RequestBody LoginForm loginRequest) {

		return loginService.signInUserImpl(loginRequest);
	}

	@PostMapping("/signup/{userType}")
	public Response<User> registerUser(@RequestBody UserDTO signUpRequest,
			@PathVariable("userType") String userTypeName) {

		return loginService.registerUserImpl(signUpRequest, userTypeName);
	}

}
