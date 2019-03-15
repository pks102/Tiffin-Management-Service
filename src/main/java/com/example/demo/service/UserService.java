package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.LoginForm;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.model.VendorItem;
import com.example.demo.repository.JwtTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserTypeRepository;
import com.example.demo.repository.VendorItemRepository;
import com.example.demo.security.JwtProvider;

@Service
public class UserService {
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
	VendorItemRepository vendorItemRepository;
	@Autowired
	private PasswordEncoder encoder;

	public ResponseEntity<?> signInVerify(LoginForm loginRequest) {

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
		return ResponseEntity.ok(new Token(jwt));
	}

	public void registerUserImpl(User signUpRequest, String userTypeName) {
		if (userTypeName.equals("customer")) {
			UserType userType = userTypeRepository.findByUserTypeName("customer");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			signUpRequest.setUserType(userTypes);
			signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
			userRepository.save(signUpRequest);

			System.out.println("-------" + signUpRequest);

		} else if (userTypeName.equalsIgnoreCase("vendor")) {
			UserType userType = userTypeRepository.findByUserTypeName("vendor");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			signUpRequest.setUserType(userTypes);
			signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
			userRepository.save(signUpRequest);

		}

	}

	public ResponseEntity<?> updateImpl(User newUser,HttpServletRequest request) {

		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		 if(userRepository.findByUserName(username)!=null)
		 {
			User oldUser  =userRepository.findByUserName(username); 
			newUser.setUserId(oldUser.getUserId());
			Token tempToken = oldUser.getToken();
			oldUser=newUser;
			oldUser.setPassword(encoder.encode(newUser.getPassword()));
			oldUser.setToken(tempToken);
			userRepository.save(oldUser);
			return ResponseEntity.ok().body("User successfully Updated");
		} else {
			return ResponseEntity.ok().body("User id not found");
		}

	}

	public ResponseEntity<?> showVendorItems(int vendorId) {
		if (userRepository.findById(vendorId) != null && vendorItemRepository.findByUserUserId(vendorId) != null
				&& !vendorItemRepository.findByUserUserId(vendorId).isEmpty())  {
			
				List<VendorItem> listOfVendorItem = vendorItemRepository.findByUserUserId(vendorId);
				return ResponseEntity.ok().body(listOfVendorItem);
			
		} else {
			return ResponseEntity.ok().body("Enter valid vendorID");
		}
	}

	public ResponseEntity<?> addItemImpl(VendorItem vendorItem,HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		 System.out.println("username----------------------------------"+username);
		 if(userRepository.findByUserName(username)!=null)
		 {
			 vendorItem.setUser(userRepository.findByUserName(username));
		vendorItemRepository.save(vendorItem);
		return ResponseEntity.ok().body("successfully added Item");
		}else {
		return ResponseEntity.ok().body("Enter valid vendorId");
		}
	}

	public ResponseEntity<?> editItem(String itemName, VendorItem vendorItem,HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		 System.out.println("username----------------------------------"+username);
		 if(userRepository.findByUserName(username)!=null)
		 {
			 User oldUser=userRepository.findByUserName(username);
			 VendorItem oldVendorItem=vendorItemRepository.findByUserUserIdAndItemName(oldUser.getUserId(),itemName);
		vendorItem.setVendorItemId(oldVendorItem.getVendorItemId());
		vendorItem.setUser(oldUser);
		vendorItemRepository.save(vendorItem);
		return ResponseEntity.ok().body("successfully edited item");
		 }else {
			return ResponseEntity.ok().body("Enter valid itemName");
			}
	}

	public ResponseEntity<?> deleteItems(String itemName,HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		 System.out.println("username----------------------------------"+username);
		 if(userRepository.findByUserName(username)!=null)
		 {
		 User oldUser=userRepository.findByUserName(username);
		 VendorItem vendorItem=vendorItemRepository.findByUserUserIdAndItemName(oldUser.getUserId(), itemName);
		vendorItemRepository.deleteById(vendorItem.getVendorItemId());
		return ResponseEntity.ok().body("deleted");
		}else {
			return ResponseEntity.ok().body("Enter valid itemName");
			}
	}

	public ResponseEntity<?> ListOfVendors() {

		List<User> vendors = userRepository.findByUserTypeUserTypeId(2);
		System.out.println("in show vendor service");
		return ResponseEntity.ok().body(vendors);
	}

	public ModelAndView addToCartImpl(int userId, int vendorItemId) {
		List<VendorItem> listOfVendorItem = vendorItemRepository.findByUserUserId(userId);// for displaying specific
																							// vendors item
		User vendor = userRepository.findById(userId).get();// get selected vendor
		List<VendorItem> cart = new ArrayList<VendorItem>();
		cart.add(vendorItemRepository.findById(vendorItemId).get());// add to cart
		ModelAndView mv = new ModelAndView("displayItem", "vendorItem", listOfVendorItem).addObject("vendor", vendor)
				.addObject("cart", cart);
		System.out.println(cart);
		return mv;
	}

	public ModelAndView viewCartImpl(int vendorItemId) {
		VendorItem vendorItem = vendorItemRepository.findById(vendorItemId).get();
		ModelAndView mv = new ModelAndView("displayCart", "vendorItem", vendorItem);
		return mv;
	}

}
