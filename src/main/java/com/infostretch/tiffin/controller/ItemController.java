package com.infostretch.tiffin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infostretch.tiffin.dto.VendorItemDTO;
import com.infostretch.tiffin.model.Response;
import com.infostretch.tiffin.model.VendorItem;
import com.infostretch.tiffin.service.ItemService;

@RestController
public class ItemController {
	@Autowired
	ItemService itemService;

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/vendorItems/{vendorId}") // show items to Customer
	public Response<List<VendorItem>> showVendorItem(@PathVariable("vendorId") int vendorId) {
		return itemService.showVendorItems(vendorId);
	}
	
	@PreAuthorize("hasRole('ROLE_vendor')")
	@PostMapping("/vendor/item") // add items for vendor
	public Response<VendorItem> addItem(@RequestBody VendorItemDTO vendorItemDTO, HttpServletRequest request) {
		return itemService.addItemImpl(vendorItemDTO, request);

	}

	@PreAuthorize("hasRole('ROLE_vendor')")
	@PostMapping("/vendor/{vendorItemId}") // edit items for vendor
	public Response<VendorItem> editItem(@PathVariable("vendorItemId") int vendorItemId,
			@RequestBody VendorItemDTO vendorItemDTO, HttpServletRequest request) {
		return itemService.editItem(vendorItemId, vendorItemDTO, request);
	}

	@PreAuthorize("hasRole('ROLE_vendor')")
	@DeleteMapping("vendor/{vendorItemId}") // delete items for vendor
	public Response<VendorItem> deleteItem(@PathVariable("vendorItemId") int vendorItemId, HttpServletRequest request) {
		return itemService.deleteItems(vendorItemId, request);

	}
}
