package com.infostretch.tiffin.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infostretch.tiffin.model.Cart;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.model.VendorItem;
import com.infostretch.tiffin.repository.AddToCartRepository;
import com.infostretch.tiffin.repository.UserRepository;
import com.infostretch.tiffin.repository.VendorItemRepository;
import com.infostretch.tiffin.security.JwtProvider;
import com.infostretch.tiffin.utility.UtilityClass;

@Service
public class CartService {

	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	UserRepository userRepository;
	@Autowired
	VendorItemRepository vendorItemRepository;
	@Autowired
	AddToCartRepository cartRepository;
	@Autowired
	UtilityClass utilityClass;

		public ResponseEntity<Cart> addToCartImpl(int vendorItemId, long quantity, HttpServletRequest request) {
			
			String userName = utilityClass.forToken(request);
			Optional<VendorItem> vendorItem = vendorItemRepository.findById(vendorItemId);
			
			Optional<User> user1 = userRepository.findByUserName(userName);
			if (user1.isPresent() && vendorItem.isPresent()) {
				User oldUser = user1.get();
				VendorItem vendorItem1 = vendorItem.get();
				Cart cart = new Cart();
				cart.setUser(oldUser);
				cart.setVendorItem(vendorItem1);
				cart.setQuantity(quantity);
				cart.setOrderStatus(Constants.PENDING);
				cartRepository.save(cart);
				return new ResponseEntity<>(cart, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
	}

	public ResponseEntity<List<Cart>> viewCartImpl(HttpServletRequest request) {
		String userName = utilityClass.forToken(request);
		Optional<User> customer = userRepository.findByUserName(userName);
		if(customer.isPresent()) {
		Optional<List<Cart>> cart1=cartRepository.findByUserUserIdAndOrderStatus(customer.get().getUserId(),Constants.PENDING);
		if (cart1.isPresent()) {
			List<Cart> cart = cart1.get();
			double totalAmount=0;
			
			for(Cart ls:cart) {
				double price=0;
				double quantity=1;
			quantity=quantity * (ls.getQuantity());
			price=price+ls.getVendorItem().getPrice();
			totalAmount= totalAmount+(price*quantity);
				
			}
			return new ResponseEntity<>(cart, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Cart> deleteCartImpl(int cartId, HttpServletRequest request) {
		String userName = utilityClass.forToken(request);
		Optional<Cart> cart1 = cartRepository.findById(cartId);
		Optional<User> user=userRepository.findByUserName(userName);
		if (cart1.isPresent() && user.isPresent()) {
			Cart cart = cart1.get();
			cartRepository.delete(cart);
			return new ResponseEntity<>(cart, HttpStatus.OK);
		} else {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
	}
}
