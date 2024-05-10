package com.ispan.recordshop.cochordnuts.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.OrderDetail;

import com.ispan.recordshop.cochordnuts.service.impl.OrderDetailServiceImpl;

@RestController
@CrossOrigin
public class OrderDetailController {
	
	@Autowired
	private OrderDetailServiceImpl orderDetailServiceImpl;
	
	
	 @PostMapping("/orderDetail/insert")
	 public String create(@RequestBody String json) {
		 JSONObject responseJson = new JSONObject();		    
	        OrderDetail orderDetail =orderDetailServiceImpl.insert(json);
	        if(orderDetail!=null) {
	        responseJson.put("success", true);
	        responseJson.put("message", "新增成功");
	        }else {
	        	 responseJson.put("success", false);
	             responseJson.put("message", "新增失敗");
	        }
	        
	        return responseJson.toString();
	
	 }

}
