package com.ispan.recordshop.cochordnuts.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.dto.ProductDTO;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.model.WishList;
import com.ispan.recordshop.cochordnuts.repository.WishListRepository;
import com.ispan.recordshop.cochordnuts.service.impl.MemberService;

@Service
public class WishListService {
	
	@Autowired
	private WishListRepository wishListRepo;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ProductService productService;

	// 加入願望清單
	public WishList addWishList(JSONObject json) {
		Integer memberId = json.isNull("memberId")? null : json.getInt("memberId");
		Integer productNo = json.isNull("productNo")? null : json.getInt("productNo");
		WishList result = checkExistByProductNoWithMember(memberId, productNo);
		
		if(result == null) {
			System.out.println("not null");
			Member member = memberService.findById(memberId);
			Product product = productService.findByIdReturnProduct(productNo);
			WishList wishList = new WishList();
			wishList.setProduct(product);
			wishList.setMember(member);
			return wishListRepo.save(wishList);
		} else {
			System.out.println("null");
			return null;
		}
		
	}
	
	// 顯示願望清單
	public List<ProductDTO> findWishList(Integer memberId){
		List<WishList> wishlists =wishListRepo.findByMemberId(memberId);
		List<ProductDTO> products = new ArrayList<>();
		ProductDTO productDTO = null;
		for (WishList wish : wishlists) {
			productDTO = new ProductDTO();
			productDTO.setProductNo(wish.getProduct().getProductNo());
			productDTO.setProductName(wish.getProduct().getProductName());
			productDTO.setPhoto(wish.getProduct().getPhoto());
			productDTO.setUnitPrice(wish.getProduct().getUnitPrice());
			productDTO.setDiscount(wish.getProduct().getDiscount());
			products.add(productDTO);
		}
		return products;
		
	}
	
	// 確認該會員是不是已經加過商品
	public WishList checkExistByProductNoWithMember(Integer memberId, Integer productNo) {
		
		if(memberId != null && memberId != 0) {
			WishList wishlist = wishListRepo.findByMemberIdAndproductNo(memberId, productNo);
			if(wishlist != null) {
				return wishlist;
			}
		}
		return null;
	}
	
	
	// 刪除
	public boolean removeWishList(Integer memberId,Integer productNo){
		
		WishList result = checkExistByProductNoWithMember(memberId, productNo);
		if(result != null) {
			wishListRepo.deleteById(result.getWishListNo());
			return true;
		} 
		return false;
		
	}
	
	
	
	
	
	
}
