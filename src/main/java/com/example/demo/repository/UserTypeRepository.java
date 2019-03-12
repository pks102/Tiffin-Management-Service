package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
	public UserType findByUserTypeName(String name);
}
