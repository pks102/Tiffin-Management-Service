package com.infostretch.tiffin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infostretch.tiffin.dto.LoginForm;
import com.infostretch.tiffin.dto.UserDTO;
import com.infostretch.tiffin.model.Response;
import com.infostretch.tiffin.model.Token;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.model.UserType;
import com.infostretch.tiffin.repository.JwtTokenRepository;
import com.infostretch.tiffin.repository.UserRepository;
import com.infostretch.tiffin.repository.UserTypeRepository;
import com.infostretch.tiffin.security.JwtProvider;
import com.infostretch.tiffin.utility.UtilityClass;

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
	@Autowired
	UtilityClass utilityclass;

	public Response<User> signInUserImpl(LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);

		Optional<User> user = userRepository.findByUserName(loginRequest.getUserName());
		if (user.isPresent()) {
			if (user.get().getToken() != null) {
				User user1 = user.get();
				int tokenId = user1.getToken().getTokenId();
				Optional<Token> token11 = tokenRepo.findById(tokenId);

				if (token11.isPresent()) {
					Token token1 = token11.get();
					token1.setJwtToken(jwt);
					tokenRepo.save(token1);
					user1.setToken(token1);
					userRepository.save(user1);
					return new Response<>(user1,"User logged In successfully" ,HttpStatus.OK);
				} else {
					return new Response<>("Invalid Details",HttpStatus.BAD_REQUEST);

				}
			} else {
				User user1 = user.get();
				Token token = new Token();
				token.setJwtToken(jwt);
				tokenRepo.save(token);
				user1.setToken(token);
				userRepository.save(user1);
				return new Response<>(user1,"User logged In successfully",HttpStatus.OK);
			}
		} else {
			return new Response<>("Invalid Details",HttpStatus.BAD_REQUEST);
		}
	}

	public Response<User> registerUserImpl(UserDTO signUpRequest, String userTypeName) {
		if (userTypeName.equalsIgnoreCase("customer")) {
			UserType userType = userTypeRepository.findByUserTypeName("customer");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			User user = utilityclass.registerUser(userTypes, signUpRequest);
			return new Response<>(user,"Customer register successfully" ,HttpStatus.OK);

		} else if (userTypeName.equalsIgnoreCase("vendor")) {
			UserType userType = userTypeRepository.findByUserTypeName("vendor");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			User user = new User();

			user.setUserType(userTypes);
			user.setPassword(encoder.encode(signUpRequest.getPassword()));
			user.setUserName(signUpRequest.getUserName());
			user.setEmailId(signUpRequest.getEmailId());
			user.setName(signUpRequest.getName());
			user.setContactNo(signUpRequest.getContactNo());
			user.setAddress(signUpRequest.getAddress());

			userRepository.save(user);
			return new Response<>(user,"Vendor registered successfully" ,HttpStatus.OK);
		} else {
			return new Response<>("Something went wrong Try Again!",HttpStatus.BAD_REQUEST);
		}
	}
}
