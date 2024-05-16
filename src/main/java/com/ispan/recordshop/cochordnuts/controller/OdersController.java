package com.ispan.recordshop.cochordnuts.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.dto.CartForOrdersDto;
import com.ispan.recordshop.cochordnuts.model.Cart;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.repository.MemberRepository;
import com.ispan.recordshop.cochordnuts.service.CartService;
import com.ispan.recordshop.cochordnuts.service.impl.OrdersServiceImpl;


@RestController
@CrossOrigin
public class OdersController {

	@Autowired
	private OrdersServiceImpl ordersServiceImpl;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private CartService cartService;

	@PostMapping("/orders/insert/{MemberNo}") // 前台下單 User輸入運送方式、付款方式等等
	public String create(@RequestBody Orders od,@PathVariable Integer MemberNo) {
		JSONObject responseJson = new JSONObject(od);
		System.out.println(responseJson);
//		od.setMemberNo((Member)httpSession.getAttribute("UserId"));//之後再打開	
		od.setMemberNo(memberRepository.findById(MemberNo).get());
		od.setAddress();
		od.setCreateDate();
		od.setStatus();	
		od.setLastModifiedDate();
		od.setFreight();

		Orders order = ordersServiceImpl.insert(od);
		if(order!=null) {
			order.setReceiptNo();
		}
		ordersServiceImpl.insert(order);
		
		if (order != null) {
			responseJson.put("success", true);
			responseJson.put("message", "新增成功");
			
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "新增失敗");
		}
		

		return responseJson.toString();
	}
	@PutMapping("/orders/update/{orderNo}") // orders修改
	public String update(@RequestBody Orders od,@PathVariable Integer orderNo) {
		JSONObject responseJson = new JSONObject();
		Orders order = ordersServiceImpl.update(orderNo,od);
		if (order != null) {
			responseJson.put("success", true);
			responseJson.put("message", "修改成功");
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "修改失敗");
		}
		return responseJson.toString();
	}
	
	@DeleteMapping("/orders/deleteById/{odNo}") // 後台刪除訂單
	public String deleteById(@PathVariable Integer odNo) {
		JSONObject responseJson = new JSONObject();
		boolean rs = ordersServiceImpl.deleteById(odNo);
		if (rs) {
			responseJson.put("success", true);
			responseJson.put("message", "刪除成功");
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "刪除失敗");
		}

		return responseJson.toString();
	}


	@GetMapping("/orders/findAll") // 後台訂單搜尋全部
	public String findAll() {
		System.out.println("findAll");
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		List<Orders> orders = ordersServiceImpl.selectAll();
		
		for (Orders order : orders) {
			
			JSONObject item = new JSONObject(order);
			array.put(item);
		}
		responseJson.put("list", array);
		System.out.println(responseJson.toString());
		return responseJson.toString();
	}

	@GetMapping("/orders/findByOrderNo/{odNo}") //依訂單編號，單筆搜尋
	public String findByOrderNo(@PathVariable Integer odNo) {
		JSONObject responseJson = new JSONObject();
		
		Orders order = ordersServiceImpl.findByOrderNo(odNo).get();
		JSONObject obj = new JSONObject(order);
		responseJson.put("order", obj);
		return responseJson.toString();

	}


	@GetMapping("/orders/findBymemberNo/{memberNo}")
	public String findBymemberNo(@PathVariable Integer memberNo) {// 依會員編號
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		List<Orders> orders = ordersServiceImpl.findBymemberNo(memberNo);
		for (Orders Order : orders) {
			JSONObject item = new JSONObject(Order);
			array.put(item);
		}
		responseJson.put("memberOrders", array);
		return responseJson.toString();
	}
	
	
	@GetMapping("/orders/findCartByMemberNo/{memberNo}")
	public String findCartByMemberNo(@PathVariable Integer memberNo) {
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		List<CartForOrdersDto> carts=ordersServiceImpl.findCartByMember(memberNo);
		for(CartForOrdersDto cart :carts) {
			JSONObject item = new JSONObject(cart);
			array.put(item);
		}
		responseJson.put("cartList", array);
		return responseJson.toString();
	}
	
}
