package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Token;


public interface JwtTokenRepository extends JpaRepository<Token, Integer>{
Token findByJwtToken(String token);
}
