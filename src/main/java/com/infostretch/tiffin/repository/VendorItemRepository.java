package com.infostretch.tiffin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.infostretch.tiffin.model.VendorItem;

public interface VendorItemRepository extends CrudRepository<VendorItem, Integer> {

Optional<List<VendorItem>> findByUserUserId(int userId);
Optional<VendorItem> findByVendorItemIdAndUserUserId(int vendorItemId,int userId);	
}
