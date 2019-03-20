package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.LoginForm;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.repository.JwtTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserTypeRepository;
import com.example.demo.security.JwtProvider;

@Service
public class LoginService {

	@Autowired
	JwtTokenRepository tokenRepo;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserTypeRepository userTypeRepository;
	@Autowired
	PasswordEncoder encoder;

	public ResponseEntity<?> signInUserImpl(LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);

		User user1 = userRepository.findByUserName(loginRequest.getUserName());

		Token token = new Token();
		if (user1.getToken() != null) {
			int tokenId = user1.getToken().getTokenId();
			token = tokenRepo.findById(tokenId).get();
			token.setJwtToken(jwt);
			tokenRepo.save(token);

			user1.setToken(token);
			userRepository.save(user1);
			System.out.println("token-----------------------" + token);

		} else {
			token.setJwtToken(jwt);
			tokenRepo.save(token);
			user1.setToken(token);
			userRepository.save(user1);
			System.out.println("token  else-----------------------" + token);
		}
		return new ResponseEntity<>(new Response("200", "Succesfully logged in", user1), HttpStatus.OK);
	}

	public ResponseEntity<?> registerUserImpl(User signUpRequest, String userTypeName) {
		if (userTypeName.equalsIgnoreCase("customer")) {
			UserType userType = userTypeRepository.findByUserTypeName("customer");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			signUpRequest.setUserType(userTypes);
			signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
			userRepository.save(signUpRequest);
			return new ResponseEntity<>(new Response("200", "Succesfully registered", signUpRequest), HttpStatus.OK);

		} else if (userTypeName.equalsIgnoreCase("vendor")) {
			UserType userType = userTypeRepository.findByUserTypeName("vendor");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			signUpRequest.setUserType(userTypes);
			signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
			userRepository.save(signUpRequest);
			return new ResponseEntity<>(new Response("200", "Succesfully registered ", signUpRequest), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid details"), HttpStatus.BAD_REQUEST);
		}
	}
}
