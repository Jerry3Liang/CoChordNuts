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
import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;
import com.ispan.recordshop.cochordnuts.service.impl.OrderDetailServiceImpl;

@RestController
@CrossOrigin
public class OrderDetailController {

	@Autowired
	private OrderDetailServiceImpl orderDetailServiceImpl;
	
	@Autowired
	private OrderRepository orderRepository;
	

	@DeleteMapping("/orderDetail/deleteById/{orderDetailNo}") // 後台刪除orderDetail
	public String deleteById(@PathVariable Integer orderDetailNo) {
		boolean rs = orderDetailServiceImpl.deleteById(orderDetailNo);
		JSONObject responseJson = new JSONObject();

		if (rs) {
			responseJson.put("success", true);
			responseJson.put("message", "刪除成功");
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "刪除失敗");
		}
		return responseJson.toString();
	}

	

	@GetMapping("/orderDetail/findByOdNo/{odNo}")
	public String findByOdNo(@PathVariable Integer odNo) {// 依訂單編號 查詢單筆orderDetail
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		List<OrderDetail> orderDetail = orderDetailServiceImpl.findByOdNo(odNo);
		for(OrderDetail anOd : orderDetail) {
			OrderDetailDto Dto = new OrderDetailDto();
			Dto.setCount(anOd.getProductBoughtCount());
			Dto.setProductName(anOd.getProductName());
			Dto.setPrice(anOd.getProductUnitPrice());
			Dto.setTotal(anOd.getProductTotalPay());
			Dto.setDiscount(anOd.getDiscount());	
			JSONObject item = new JSONObject(Dto);
			array.put(item);
		}
		responseJson.put("orderDetailDto", array);

		return responseJson.toString();
	}

	@GetMapping("/orderDetail/findAll") // 查詢全部orderDetail
	public String findAll() {
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		List<OrderDetail> orderDetails = orderDetailServiceImpl.selectAll();
		for (OrderDetail orderDetail : orderDetails) {
			JSONObject item = new JSONObject(orderDetail);
			array.put(item);
		}
		responseJson.put("list", array);
		return responseJson.toString();
	}

	@PutMapping("/orderDetail/update/{orderDetailNo}") 
	public String update(@PathVariable Integer orderDetailNo ,@RequestBody String count) {
		JSONObject responseJson = new JSONObject();
		JSONObject item = new JSONObject(count);		
		Integer counttttt =	item.getInt("count");
		OrderDetail orderDetail = orderDetailServiceImpl.update(orderDetailNo,counttttt);
		if (orderDetail != null) {
			responseJson.put("success", true);
			responseJson.put("message", "修改成功");
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "修改失敗");
		}
		return responseJson.toString();
	}
	
	@PostMapping("/orderDetail/insert") 
	public String insert(@RequestBody CartForOrdersDto cartDto) {
		JSONObject responseJson = new JSONObject();
		
		OrderDetail rs = orderDetailServiceImpl.insert(cartDto);
		if(rs!=null) {
			responseJson.put("success", true);
			responseJson.put("message", "新增成功");
		}else {
			responseJson.put("success", false);
			responseJson.put("message", "新增失敗");
		}
		

		return responseJson.toString();
	}

}
