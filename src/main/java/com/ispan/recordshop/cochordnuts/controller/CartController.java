package com.ispan.recordshop.cochordnuts.controller;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;
import com.ispan.recordshop.cochordnuts.service.CartService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin
@RestController
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductRepository productRepository;
 
	@PostMapping("/cart/add/")
	public String JSONObject (Integer productId, HttpSession session){
		JSONObject responseObj = new JSONObject();

		Integer memberId = (Integer) session.getAttribute("loggedInUser");

        if(memberId == null){
        	responseObj.put("message", "請登入會員");
        	return responseObj.toString();
//        	   responseObj.put("message", "請登入會員");
//            return responseObj.toString();
        }

        cartService.addToCartService(memberId, productId);
        
        List<Product> allProducts = productRepository.findAll();
		
		responseObj.put("message", "成功");
        return responseObj.toString();
	
    }
	
}
