package com.infostretch.tiffin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infostretch.tiffin.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUserName(String userName);
	Optional<List<User>> findByUserTypeUserTypeId(int userId);
}
