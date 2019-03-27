package com.infostretch.tiffin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infostretch.tiffin.model.Token;


public interface JwtTokenRepository extends JpaRepository<Token, Integer>{
Optional<Token> findByJwtToken(String token);
}
