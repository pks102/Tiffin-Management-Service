package com.infostretch.tiffin.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infostretch.tiffin.dto.VendorItemDTO;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.model.VendorItem;
import com.infostretch.tiffin.repository.UserRepository;
import com.infostretch.tiffin.repository.VendorItemRepository;
import com.infostretch.tiffin.utility.UtilityClass;

@Service
public class ItemService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	VendorItemRepository vendorItemRepository;
	@Autowired
	UtilityClass utilityClass;

	public ResponseEntity<List<VendorItem>> showVendorItems(int vendorId) {
		Optional<List<VendorItem>> vendorItem = vendorItemRepository.findByUserUserId(vendorId);
		if (userRepository.findById(vendorId).isPresent() && vendorItem.isPresent()) {
			List<VendorItem> listOfVendorItem = vendorItem.get();
			return new ResponseEntity<>(listOfVendorItem, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	public ResponseEntity<VendorItem> addItemImpl(VendorItemDTO vendorItemDTO, HttpServletRequest request) {
		String userName = utilityClass.forToken(request);
		Optional<User> vendor = userRepository.findByUserName(userName);
		VendorItem oldVendorItem = new VendorItem();
			if (vendor.isPresent()) {
				oldVendorItem.setItemName(vendorItemDTO.getItemName());
				oldVendorItem.setPrice(vendorItemDTO.getPrice());
				oldVendorItem.setUser(vendor.get());
				vendorItemRepository.save(oldVendorItem);
			return new ResponseEntity<>(oldVendorItem, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<VendorItem> editItem(int vendorItemId, VendorItemDTO vendorItemDTO, HttpServletRequest request) {
		String userName=utilityClass.forToken(request);
		Optional<User> vendor=userRepository.findByUserName(userName);
		if(vendor.isPresent()) {
		Optional<VendorItem> vendorItem=vendorItemRepository.findByVendorItemIdAndUserUserId(vendorItemId, vendor.get().getUserId());
		if(vendorItem.isPresent()) {
			VendorItem oldVendorItem=vendorItem.get();
			oldVendorItem.setItemName(vendorItemDTO.getItemName());
			oldVendorItem.setPrice(vendorItemDTO.getPrice());
			oldVendorItem.setUser(vendor.get());
			vendorItemRepository.save(oldVendorItem);
			return new ResponseEntity<>(oldVendorItem, HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<VendorItem> deleteItems(int vendorItemId, HttpServletRequest request) {
		String userName = utilityClass.forToken(request);
		Optional<VendorItem> vendorItem = vendorItemRepository.findById(vendorItemId);
		Optional<User> vendor = userRepository.findByUserName(userName);
		if (vendor.isPresent() && vendorItem.isPresent()) {
			vendorItemRepository.deleteById(vendorItemId);
			return new ResponseEntity<>(vendorItem.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
