//package com.example.demo.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.example.demo.model.User;
//import com.example.demo.model.UserType;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.repository.UserTypeRepository;
//import com.example.demo.security.JwtProvider;
//import com.example.demo.security.JwtResponse;
//import com.example.demo.service.UserService;
//
//
//
//
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/auth")
//public class AuthRestAPIs {
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserTypeRepository userTypeRepository;
//
//    @Autowired
//    PasswordEncoder encoder;
//
//    @Autowired
//    JwtProvider jwtProvider;
//    @Autowired
//    UserService userService;
//
////    @GetMapping("/login")
////	public ModelAndView login() {
////		return new ModelAndView("login");
////	}
//    
//    @PostMapping("/signin")
//    public ModelAndView authenticateUser(@Valid @RequestParam("userName") String userName,@RequestParam("password") String password) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        userName,password
//                )
//                
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
////        String jwt = jwtProvider.generateJwtToken(authentication);
////        return ResponseEntity.ok(new JwtResponse(jwt));
//        return new ModelAndView("welcome");
//    }
//
//    @PostMapping("/signup")
//    public ModelAndView registerUser(@Valid @RequestBody User signUpRequest,@RequestParam("userTypeName")String userTypeName) {
//        // Creating user's account
////        User user = new User(signUpRequest.getName(), signUpRequest.getUserName(),
////                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
//    	User user = new User(signUpRequest);
////        List<String> strRoles = signUpRequest.getUserType().
////        Set<UserType> roles = new HashSet<>();
//
//        if(userTypeName.equals("customer")) {
//        	
//        	UserType userType =userTypeRepository.findByUserTypeName("customer");
//		userType.setUserTypeId(userType.getUserTypeId());
//		List<UserType> userTypes = new ArrayList<>();
//		userTypes.add(userType);
//		user.setUserType(userTypes);
//		userService.addCustomer(user);
//
//	} else if (userTypeName.equalsIgnoreCase("vendor")) {
//		UserType userType =userTypeRepository.findByUserTypeName("customer");
//		userType.setUserTypeId(userType.getUserTypeId());
//		List<UserType> userTypes = new ArrayList<>();
//		userTypes.add(userType);
//		user.setUserType(userTypes);
//		userService.addVendor(user);
//	}
//        
////        strRoles.forEach(role -> {
////        	switch(role) {
////	    		case "admin":
////	    			UserType adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
////	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
////	    			roles.add(adminRole);
////	    			
////	    			break;
////	    		case "customer":
////	    			UserType pmRole = roleRepository.findByName(RoleName.ROLE_)
////	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
////	            	roles.add(pmRole);
////	            	
////	    			break;
////	    		default:
////	    			UserType userRole = roleRepository.findByName(RoleName.ROLE_USER)
////	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
////	        		roles.add(userRole);        			
////        	}
////        });
//        
////        user.setRoles(roles);
////        userRepository.save(user);
//
////        return ResponseEntity.ok().body("User registered successfully!");
//        return new ModelAndView("authenticate");
//    }
//}