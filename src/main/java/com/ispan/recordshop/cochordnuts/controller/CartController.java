package com.ispan.recordshop.cochordnuts.controller;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.Cart;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;
import com.ispan.recordshop.cochordnuts.service.CartService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/cart/add")
    public String addToCart(HttpSession session, @RequestBody String obj) {
        JSONObject responseObj = new JSONObject();
        JSONObject Objj = new JSONObject(obj);

        // Integer memberId = (Integer) session.getAttribute("memberNo");

        // Integer productId = Integer.parseInt(Objj.getString("id"));
        Integer memberId = Integer.parseInt(Objj.getString("memberNo"));
        Integer productId = Objj.getInt("id");

        // String productId1 = productId.substring(0, productId.length() - 1);
        // Integer productId2 = Integer.parseInt(productId1);

        if (memberId == null) {
            responseObj.put("message", "請登入會員");
            return responseObj.toString();
        }
        // int memberId = 1;
        cartService.addToCartService(memberId, productId);

        // List<Product> allProducts = productRepository.findAll();

        responseObj.put("message", "成功");
        return responseObj.toString();

    }

    @PostMapping("/cart/list")
    public String listCart(HttpSession session, @RequestBody String memberIdObj) {
        JSONObject responseObj = new JSONObject();
        JSONObject memberObj = new JSONObject(memberIdObj);

        Integer memberId = Integer.parseInt(memberObj.getString("memberNo"));

        if (memberId == null) {
            responseObj.put("message", "請登入會員");
            return responseObj.toString();
        } else {

            JSONArray array = new JSONArray();
            List<Cart> cartList = cartService.findUsersCartService(memberId);
            for (Cart item : cartList) {

                Product itemProduct = item.getProduct();
                JSONObject itemObj = new JSONObject()
                        .put("photo", itemProduct.getPhoto())
                        .put("price", itemProduct.getUnitPrice())
                        .put("discount", itemProduct.getDiscount())
                        .put("count", item.getCount());
                array.put(itemObj);
            }
            responseObj.put("list", array);
            return responseObj.toString();
        }
    }

    @GetMapping("/cart/addOne")
    public String addOneCount(Integer productId, HttpSession session) {
        JSONObject responseObj = new JSONObject();
        Integer loginUserId = (Integer) session.getAttribute("loggedInUser");

        if (loginUserId == null) {
            responseObj.put("message", "請登入會員");
            return responseObj.toString();
        }

        cartService.addOneVolumn(loginUserId, productId);

        List<Cart> cartList = cartService.findUsersCartService(loginUserId);

        return cartList.toString();
    }

    @GetMapping("/cart/minusOne")
    public String minusOneCount(Integer productId, HttpSession session) {
        JSONObject responseObj = new JSONObject();
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            responseObj.put("message", "請登入會員");
            return responseObj.toString();
        }

        cartService.minusOneVolumn(loginUserId, productId);

        List<Cart> cartList = cartService.findUsersCartService(loginUserId);

        return cartList.toString();
    }

}