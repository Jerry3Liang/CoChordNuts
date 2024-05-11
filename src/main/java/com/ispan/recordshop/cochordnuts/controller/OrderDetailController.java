package com.ispan.recordshop.cochordnuts.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RestController;


import com.ispan.recordshop.cochordnuts.service.impl.OrderDetailServiceImpl;

@RestController
@CrossOrigin
public class OrderDetailController {
	
	@Autowired
	private OrderDetailServiceImpl orderDetailServiceImpl;
	
	
	
	
	


}
