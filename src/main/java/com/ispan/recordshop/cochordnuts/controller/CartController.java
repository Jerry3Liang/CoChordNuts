package com.ispan.recordshop.cochordnuts.controller;

import java.util.ArrayList;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.dto.ProductDTO;
import com.ispan.recordshop.cochordnuts.model.Cart;
import com.ispan.recordshop.cochordnuts.model.CartId;
import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;
import com.ispan.recordshop.cochordnuts.service.CartService;
import com.ispan.recordshop.cochordnuts.service.ProductService;
import com.ispan.recordshop.cochordnuts.service.impl.MemberService;
import com.ispan.recordshop.cochordnuts.service.impl.OrdersServiceImpl;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrdersServiceImpl ordersServiceImpl;
    
    @Autowired
    private MemberService memberService ;
    // @Autowired
    // private Product product;

    // @Autowired
    // private ProductDTO productDTO;

    @PostMapping("/cart/add")
    public String addToCart(HttpSession session, @RequestBody String obj) {
        JSONObject responseObj = new JSONObject();
        JSONObject Objj = new JSONObject(obj);

        // Integer memberId = (Integer) session.getAttribute("memberNo");
        // Integer productId = Integer.parseInt(Objj.getString("id"));

        Integer memberId = Integer.parseInt(Objj.getString("memberNo"));
        Integer productId = Objj.getInt("productId");
        Integer count = Objj.getInt("count");
        // String productId1 = productId.substring(0, productId.length() - 1);
        // Integer productId2 = Integer.parseInt(productId1);

        if (memberId == 0) {
            responseObj.put("message", "請登入會員");
            return responseObj.toString();
        }
        // int memberId = 1;
        cartService.addToCartService(memberId, productId, count);

        // List<Product> allProducts = productRepository.findAll();
        String productName = productService.findById(productId).getProductName();
        responseObj
                .put("message", "加入購物車 :)")
                .put("productName", productName);
        return responseObj.toString();

    }

    @PostMapping("/cart/list")
    public String listCart(HttpSession session, @RequestBody String memberIdObj) {

        JSONObject responseObj = new JSONObject();
        JSONObject memberObj = new JSONObject(memberIdObj);
        Integer memberId = Integer.parseInt(memberObj.getString("memberNo"));
        // String memberId = memberObj.getString("memberNo");
        if (memberId == 0) {
            responseObj.put("message", "請登入會員");
            return responseObj.toString();
        } else {

            JSONArray array = new JSONArray();
            List<Cart> cartList = cartService.findUsersCartService(memberId);
            for (Cart item : cartList) {

                Product itemProduct = item.getProduct();
                JSONObject itemObj = new JSONObject()
                        .put("productId", itemProduct.getProductNo())
                        .put("productName", itemProduct.getProductName())
                        // .put("photo", itemProduct.getPhoto())
                        .put("price", itemProduct.getUnitPrice())
                        .put("discount", itemProduct.getDiscount())
                        .put("count", item.getCount())
                        .put("artist", itemProduct.getArtist().getArtistName());
                array.put(itemObj);
            }
            responseObj.put("list", array);
            return responseObj.toString();
        }
    }

    @PostMapping("/cart/addOne")
    public String addOneCount(@RequestBody String obj, HttpSession session) {
        JSONObject responseObj = new JSONObject();
        JSONObject cartItemObj = new JSONObject(obj);

        Integer memberId = Integer.parseInt(cartItemObj.getString("memberNo"));
        Integer productId = cartItemObj.getInt("productId");
        Integer productStock = productService.findById(productId).getStock();

        if (memberId == 0) {
            responseObj.put("message", "請登入會員");
            return responseObj.toString();
        } else if (productStock != 0) {

            cartService.addOneVolumn(memberId, productId);

            ProductDTO itemProduct = productService.findById(productId);
            Cart item = cartService.findTheCartItemAndProduct(memberId, productId);
            responseObj
                    .put("message", "+ 1")
                    .put("price", itemProduct.getUnitPrice());
            // .put("count", item.getCount());
            return responseObj.toString();
        }
        {
            responseObj.put("message", "目前已無庫存");
            return responseObj.toString();
        }
    }

    @PostMapping("/cart/minusOne")
    public String minusOneCount(@RequestBody String obj, HttpSession session) {
        JSONObject responseObj = new JSONObject();
        JSONObject cartItemObj = new JSONObject(obj);

        // Integer loginUserId = (Integer) session.getAttribute("loginUserId");
        Integer memberId = Integer.parseInt(cartItemObj.getString("memberNo"));
        Integer productId = cartItemObj.getInt("productId");
        // Integer productStock = productService.findById(productId).getStock();

        if (memberId == 0) {
            responseObj.put("message", "請登入會員");
            return responseObj.toString();
        }

        cartService.minusOneVolumn(memberId, productId);

        responseObj.put(obj, productId);
        // List<Cart> cartList = cartService.findUsersCartService(memberId);

        return responseObj.toString();
    }
    @PostMapping("/cart/buyAgain/{orderNo}")
    public String buyAgain(@PathVariable String orderNo) {
    	
    	List<Cart> cartArray = new ArrayList<>();
    	Orders orders = ordersServiceImpl.findByOrderNo(Integer.valueOf(orderNo)).get();
    	Integer member = orders.getMemberNo();
    	List<OrderDetail> orderDetail=orders.getOrderDetail();
    	System.out.println(member);
    	
    	for(OrderDetail anDetail : orderDetail) {
    		Cart cart =new Cart();
    		CartId cartId= new CartId();
    		
    		cart.setMember(memberService.findById(member));
        	cartId.setMemberId(member);
//        	  cart.setCartId(cartId)  		
    		cart.setCount(anDetail.getProductBoughtCount());
    		
    		cart.setProduct(productRepository.findById(anDetail.getProductNo()).get());
    		cartId.setProductId(anDetail.getProductNo());
    		
    		cart.setCartId(cartId);
    		
    		cartService.cartAdd(cart);
    		System.out.println(cartService.cartAdd(cart));
//    		cartArray.add(cart);
    		
    	}
//    	cartService.cartList(cartArray);
        
      
        return null;
    }
    

    @PostMapping("/cart/deleteItem")
    public String deleteItemFromCart(@RequestBody String obj) {
        JSONObject responseObj = new JSONObject();
        JSONObject cartItemObj = new JSONObject(obj);

        Integer memberId = Integer.parseInt(cartItemObj.getString("memberNo"));
        Integer productId = cartItemObj.getInt("productId");

        cartService.deleteItemInCart(memberId, productId);

        responseObj.put("message", "刪除成功");
        return responseObj.toString();
    }
    
  
    

}