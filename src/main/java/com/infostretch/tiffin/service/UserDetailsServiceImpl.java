package com.infostretch.tiffin.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infostretch.tiffin.exception.UserNameNotFoundException;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	 private static final Logger logger1 = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username){
		Optional<User> user = userRepository.findByUserName(username);
		User getUser=null;
		if (user.isPresent() && user.get() != null) {
			 getUser = user.get();
		} else {
			try {
				throw new UserNameNotFoundException("User Not Found with -> username or email : " + username);
			} catch (UserNameNotFoundException e) {
				logger1.error("UserNotFoundException");
			}
		}
		return new UserPrinciple(getUser);
	}
}