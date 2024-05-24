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
import com.ispan.recordshop.cochordnuts.dto.OrderDetailDto;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.repository.MemberRepository;
import com.ispan.recordshop.cochordnuts.service.impl.OrderDetailServiceImpl;
import com.ispan.recordshop.cochordnuts.service.impl.OrdersServiceImpl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;



@RestController
@CrossOrigin
public class OdersController {

	@Autowired
	private OrdersServiceImpl ordersServiceImpl;
	
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private OrderDetailServiceImpl orderDetailServiceImpl;
	

	@SuppressWarnings("unused")
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
			od.setReceiptNo();
		}
		System.out.println(order.getOrderNo());
		if (order != null) {
			responseJson.put("success", true);
			responseJson.put("message", "新增成功");
			responseJson.put("orderNo", od.getOrderNo());
			responseJson.put("memberNo", MemberNo);
			List<CartForOrdersDto> carts=ordersServiceImpl.findCartByMember2(MemberNo,order);//依會員編號找到Cart
			Integer i =1;
			for(CartForOrdersDto cart :carts) {
				orderDetailServiceImpl.insert(cart);
				i++;			
			}
			responseJson.put("orderDetailInsert", i);
	
			
			
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "新增失敗");
		}
		

		return responseJson.toString();
	}
	@PutMapping("/orders/update/{MemberNo}") // orders修改
	public String update(@RequestBody Orders od,@PathVariable Integer MemberNo) {
		JSONObject responseJson = new JSONObject();
		Orders order = ordersServiceImpl.update(MemberNo,od);
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


	@PostMapping("/orders/findAll") // 後台訂單搜尋全部
	public String findAll(@RequestBody String json) {
		System.out.println("findAll");
		JSONObject responseJson = new JSONObject();
		JSONObject obj = new JSONObject(json);
		Integer count;
		JSONArray array = new JSONArray();
		if(obj.getString("num")!=null && obj.getString("num")!="") {
			Integer num =Integer.parseInt(obj.getString("num")) ;
			count = ordersServiceImpl.findOrderCount(num);
		}else {
			count=ordersServiceImpl.findAllOrderCount();
		}
		
		List<Orders> orders = ordersServiceImpl.selectAll(obj);
		
		for (Orders order : orders) {			
			JSONObject item = new JSONObject(order);
			array.put(item);
		}
		responseJson.put("list", array);
		responseJson.put("count", count);
		System.out.println(responseJson.toString());
		return responseJson.toString();
	}

	@GetMapping("/orders/findByOrderNo/{odNo}") //依訂單編號，單筆搜尋訂單及訂單詳細內容
	public String findByOrderNo(@PathVariable Integer odNo) {
		JSONObject responseJson = new JSONObject();		
		Orders order = ordersServiceImpl.findByOrderNo(odNo).get();//依訂單編號找到order
		JSONObject obj = new JSONObject(order);
		JSONArray array = new JSONArray();
		List<OrderDetail> orderDetail = orderDetailServiceImpl.findByOdNo(odNo);//依訂單編號找到orderDetail
		for(OrderDetail anOd : orderDetail) {
			OrderDetailDto Dto = new OrderDetailDto();//將orderDetail放入DTO傳至前端
			Dto.setCount(anOd.getProductBoughtCount());
			Dto.setProductName(anOd.getProductName());
			Dto.setPrice(anOd.getProductUnitPrice());
			Dto.setTotal(anOd.getProductTotalPay());
			Dto.setDiscount(anOd.getDiscount());	
			JSONObject item = new JSONObject(Dto);
			array.put(item);
		}
		responseJson.put("orderDetailDto", array);
		responseJson.put("order", obj);
		return responseJson.toString();

	}

	@PostMapping("/orders/findBymemberNo")
	public String findBymemberNo(@RequestBody String json) {// 依會員編號
		JSONObject responseJson = new JSONObject();
		JSONObject obj = new JSONObject(json);
		JSONArray array = new JSONArray();
		Integer count = ordersServiceImpl.findfindBymemberNoCount(obj);
		List<Orders> orders = ordersServiceImpl.findBymemberNo(obj);
		for (Orders Order : orders) {
			JSONObject item = new JSONObject(Order);
			array.put(item);
		}
		responseJson.put("count", count);
		responseJson.put("memberOrders", array);
		return responseJson.toString();
	}
	
	@GetMapping("/orders/findCartByMemberNo/{memberNo}")//依會員編號找到Cart 將cart及member傳到前端
	public String findCartByMemberNo(@PathVariable Integer memberNo) {
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		List<CartForOrdersDto> carts=ordersServiceImpl.findCartByMember(memberNo);
		Member member = memberRepository.findById(memberNo).get();
		String name = member.getName();
		String email = member.getEmail();
		String phone = member.getPhone();
		String address = member.getAddress();
		for(CartForOrdersDto cart :carts) {
			JSONObject item = new JSONObject(cart);
			array.put(item);
		}
		responseJson.put("cartList", array);//將Cart以CartDto物件傳到前端
		//只取前端所需屬性
		responseJson.put("name", name);
		responseJson.put("email", email);
		responseJson.put("phone", phone);
		responseJson.put("address", address);
		
		
		return responseJson.toString();
	}
//	@GetMapping("/orders/findOrderPage/{pageNum}")
//	 public String findOrderPage(@PathVariable Integer pageNum) {
//		JSONObject responseJson = new JSONObject();
//		JSONArray array =new JSONArray();
//		Page<Orders> order = ordersServiceImpl.findOrdersPage(pageNum);
//		for(Orders o:order) {
//			JSONObject item = new JSONObject(o);
//			array.put(item);
//		}
//		responseJson.put("orderPage",array);
//		return responseJson.toString();
//		
//		 
//	 }
}
