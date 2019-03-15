package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    	 User user =null;
    	if(userRepository.findByUserName(username)!=null) {
    		System.out.println("-----------userName-- loadByUsername------------"+username);
         user = userRepository.findByUserName(username);
    	}else {
         new UsernameNotFoundException("User Not Found with -> username or email : " + username);
    	}
        return new UserPrinciple(user);
        }
}