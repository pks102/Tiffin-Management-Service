package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByUserName(String username);
	List<User> findByUserTypeUserTypeId(int userId);
}
