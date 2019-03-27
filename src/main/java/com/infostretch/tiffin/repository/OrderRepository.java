package com.infostretch.tiffin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infostretch.tiffin.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
