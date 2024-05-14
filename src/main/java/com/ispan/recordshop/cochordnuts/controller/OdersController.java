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

import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.service.impl.OrdersServiceImpl;

@RestController
@CrossOrigin
public class OdersController {

	@Autowired
	private OrdersServiceImpl ordersServiceImpl;

	@PostMapping("/orders/insert") // 前台下單 User輸入運送方式、付款方式等等
	public String create(@RequestBody Orders od) {
		JSONObject responseJson = new JSONObject();
		Orders order = ordersServiceImpl.insert(od);
		if (order != null) {
			responseJson.put("success", true);
			responseJson.put("message", "新增成功");
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "新增失敗");
		}

		return responseJson.toString();
	}

	@GetMapping("/orders/findAll") // 後台訂單搜尋全部
	public String findAll() {
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

	@PutMapping("/orders/update") // 後台新增備貨、出貨等等OR修改訂單
	public String update(@RequestBody Orders od) {
		JSONObject responseJson = new JSONObject();
		Orders order = ordersServiceImpl.update(od);
		if (order != null) {
			responseJson.put("success", true);
			responseJson.put("message", "修改成功");
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "修改失敗");
		}
		return responseJson.toString();
	}

	@DeleteMapping("/orders/deleteById/{odNo}")//後台刪除訂單
	public String deleteById(@PathVariable Integer odNo) {
		JSONObject responseJson = new JSONObject();
		boolean rs = ordersServiceImpl.deleteById(odNo);
		if (rs) {
			responseJson.put("success", true);
			responseJson.put("message", "刪除成功");
		}else {
			responseJson.put("success", false);
			responseJson.put("message", "刪除失敗");
		}
		

		return responseJson.toString();
	}

}
