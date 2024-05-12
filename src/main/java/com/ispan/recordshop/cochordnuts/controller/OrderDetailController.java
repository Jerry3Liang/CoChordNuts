package com.ispan.recordshop.cochordnuts.controller;



import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.service.impl.OrderDetailServiceImpl;


@RestController
@CrossOrigin
public class OrderDetailController {
	
	@Autowired
	private OrderDetailServiceImpl orderDetailServiceImpl;
	
	
	@DeleteMapping("/orderDetail/deleteById/{odNo}")//後台刪除orderDetail
	public String deleteById(@PathVariable Integer odNo) {
		boolean rs =  orderDetailServiceImpl.deleteById(odNo);	
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
	
	@GetMapping("/orderDetail/deleteById/{odNo}")
	public String findByOdNo(@PathVariable Integer odNo) {//查詢單筆orderDetail
		JSONObject responseJson = new JSONObject();
		OrderDetail orderDetail =orderDetailServiceImpl.findByOdNo(odNo);
		responseJson.put("orderDetail",orderDetail);

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
	
	@PutMapping("/orderDetail/update") // 後台新增備貨、出貨等等OR修改訂單
	public String update(@RequestBody OrderDetail od) {
		JSONObject responseJson = new JSONObject();
		OrderDetail orderDetail = orderDetailServiceImpl.update(od);
		if (orderDetail != null) {
			responseJson.put("success", true);
			responseJson.put("message", "修改成功");
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "修改失敗");
		}
		return responseJson.toString();
	}
}
