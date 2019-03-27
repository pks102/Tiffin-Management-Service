package com.infostretch.tiffin.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.infostretch.tiffin.model.User;

public class UserPrinciple extends User implements UserDetails {
	private static final long serialVersionUID = 1L;

	public UserPrinciple(User user) {
		super(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getUserType().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getUserTypeName()))
				.collect(Collectors.toList());
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return super.getUserName();
	}
}