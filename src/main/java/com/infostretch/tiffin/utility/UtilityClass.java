
package com.infostretch.tiffin.utility;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.infostretch.tiffin.dto.UserDTO;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.model.UserType;
import com.infostretch.tiffin.repository.UserRepository;
import com.infostretch.tiffin.repository.VendorItemRepository;
import com.infostretch.tiffin.security.JwtProvider;
import com.infostretch.tiffin.service.Constants;

@Component
public class UtilityClass {
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	VendorItemRepository vendorItemRepository;

	public ResponseEntity<User> setUserData(UserDTO newUser, HttpServletRequest request) {
		String userName = forToken(request);
		Optional<User> user1 = userRepository.findByUserName(userName);
		if (user1.isPresent()) {
			User oldUser = user1.get();
			oldUser.setUserName(newUser.getUserName());
			oldUser.setEmailId(newUser.getEmailId());
			oldUser.setName(newUser.getName());
			oldUser.setContactNo(newUser.getContactNo());
			oldUser.setAddress(newUser.getAddress());
			oldUser.setPassword(encoder.encode(newUser.getPassword()));
			userRepository.save(oldUser);
			return new ResponseEntity<>(oldUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	public String forToken(HttpServletRequest request) {
		String replaceToken = request.getHeader(Constants.AUTHORIZATION);
		String token = replaceToken.replace(Constants.BEARER, "");
		return jwtProvider.getUserNameFromJwtToken(token);

	}

	public User registerUser(List<UserType> userTypes, UserDTO signUpRequest) {
		User user = new User();
		user.setUserType(userTypes);

		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setUserName(signUpRequest.getUserName());
		user.setEmailId(signUpRequest.getEmailId());
		user.setName(signUpRequest.getName());
		user.setContactNo(signUpRequest.getContactNo());
		user.setAddress(signUpRequest.getAddress());

		userRepository.save(user);
		return user;
	}

}
