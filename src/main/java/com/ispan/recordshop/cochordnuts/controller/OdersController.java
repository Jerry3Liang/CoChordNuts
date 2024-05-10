package com.ispan.recordshop.cochordnuts.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.service.impl.OrdersServiceImpl;

@RestController
@CrossOrigin
public class OdersController {
	
	@Autowired
	private OrdersServiceImpl ordersServiceImpl;
	
	 @PostMapping("/orders/insert")
	 public String create(@RequestBody String json) {
		    JSONObject responseJson = new JSONObject();		    
	        Orders order =ordersServiceImpl.insert(json);
	        if(order!=null) {
	        responseJson.put("success", true);
	        responseJson.put("message", "新增成功");
	        }else {
	        	 responseJson.put("success", false);
	             responseJson.put("message", "新增失敗");
	        }
	        
	        return responseJson.toString();
	 }
	 @GetMapping("/orders/findAll")
	    public String findAll() {
		 JSONObject responseJson = new JSONObject();
		 JSONArray array = new JSONArray();
		 List<Orders> orders = ordersServiceImpl.selectAll();
		 for(Orders order:orders) {
			 JSONObject item = new JSONObject().put("orderNo",order.getOrderNo())
					 						   .put("totalPay",order.getTotalPay())
			                                   .put("deliverType",order.getDeliverType())
			                                   .put("dispatchDate",order.getDispatchDate())			                                   
			                                   .put("createDate",order.getCreateDate())
			                                   .put("completeDate",order.getCompleteDate())
			                                   .put("cancelDate",order.getCnacelDate())
			                                   .put("returnDate", order.getReturnDate())
			                                   .put("preparationDate", order.getPreparationDate());			                                   
			 array.put(item);
			 
		 }		
		 responseJson.put("list", array);
		 System.out.println(responseJson.toString());
		 return responseJson.toString();
	 }
	
	

}
