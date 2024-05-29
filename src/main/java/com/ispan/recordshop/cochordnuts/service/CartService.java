package com.ispan.recordshop.cochordnuts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.recordshop.cochordnuts.model.Cart;
import com.ispan.recordshop.cochordnuts.model.CartId;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.repository.CartRepository;
import com.ispan.recordshop.cochordnuts.repository.MemberRepository;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private ProductRepository productRepo;

	@Transactional
	public Cart addToCartService(Integer loggedInUser, Integer productId, Integer count) {

		Cart exist = cartRepo.findByMemberAndProducts(loggedInUser, productId);

		if (exist != null) {
			exist.setCount(exist.getCount() + count);
			return exist;
		}

		Optional<Member> op = memberRepo.findById(loggedInUser);
		Member member1 = op.get();

		Optional<Product> op2 = productRepo.findById(productId);
		Product product1 = op2.get();

		CartId cartId = new CartId();
		cartId.setMemberId(loggedInUser);
		cartId.setProductId(productId);

		Cart cart = new Cart();
		cart.setCartId(cartId);
		cart.setCount(count);
		cart.setMember(member1);
		cart.setProduct(product1);

		return cartRepo.save(cart);
	}
	// @Transactional
	// public Cart addToCartService(Integer loggedInUser, Integer productId){

	// Cart exist = cartRepo.findByMemberAndProducts(loggedInUser, productId);

	// if(exist!= null){
	// exist.setCount(exist.getCount() + 1);
	// return exist;
	// }

	// Optional<Member> op = memberRepo.findById(loggedInUser);
	// Member member1 = op.get();

	// Optional<Product> op2 = productRepo.findById(productId);
	// Product product1 = op2.get();

	// CartId cartId = new CartId();
	// cartId.setMemberId(loggedInUser);
	// cartId.setProductId(productId);

	// Cart cart = new Cart();
	// cart.setCartId(cartId);
	// cart.setCount(1);
	// cart.setMember(member1);
	// cart.setProduct(product1);

	// return cartRepo.save(cart);
	// }

	// 找到對的購物車
	public List<Cart> findUsersCartService(Integer loggedInUser) {

		return cartRepo.findMemberCartProducts(loggedInUser);
	}

	public Cart findTheCartItemAndProduct(Integer loggedInUser, Integer productId) {
		return cartRepo.findByMemberAndProducts(loggedInUser, productId);
	}

	@Transactional
	public Cart addOneVolumn(Integer loggedInUser, Integer productId) {

		Cart cart = cartRepo.findByMemberAndProducts(loggedInUser, productId);
		cart.setCount(cart.getCount() + 1);

		return cart;
	}

	@Transactional
	public void minusOneVolumn(Integer loggedInUser, Integer photoId) {

		Cart cart = cartRepo.findByMemberAndProducts(loggedInUser, photoId);

		if (cart.getCount() == 1) {
			cartRepo.delete(cart);
		} else {
			cart.setCount(cart.getCount() - 1);
		}
	}

//	public void cartList(List<Cart> cart) {
//		cartRepo.saveAllAndFlush(cart);
//	}
	
	public Cart cartAdd(Cart cart) {
		return cartRepo.save(cart);
	}

}
