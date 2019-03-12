package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.VendorItem;

public interface VendorItemRepository extends CrudRepository<VendorItem, Integer> {

	List<VendorItem> findByUserUserId(int userId);
	
}
