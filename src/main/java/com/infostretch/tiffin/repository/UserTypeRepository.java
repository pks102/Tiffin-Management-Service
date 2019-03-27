package com.infostretch.tiffin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infostretch.tiffin.model.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
	public UserType findByUserTypeName(String name);
}
