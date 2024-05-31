package com.ispan.recordshop.cochordnuts.controller;

import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.service.PaymentService;

@RestController
@CrossOrigin
public class PaymentController {
	
	
	@Autowired
	private PaymentService paymentService;

	@PostMapping("/ecpayCheckout")
	public String ecpayCheckout() {
		String aioCheckOutALLForm = paymentService.ecpayCheckout();

		return aioCheckOutALLForm;
	}
	
	@Controller
	public class PaymentReturnController {
		@PostMapping("/ecpayReturn")
		public String ecpayReturn(
//				@RequestBody String obj
				@RequestParam(value = "MerchantID", required = false) String merchantID,
				@RequestParam(value = "MerchantTradeNo", required = false) String merchantTradeNo,
				@RequestParam(value = "PaymentDate", required = false) String paymentDate,
				@RequestParam(value = "PaymentType", required = false) String paymentType,
				@RequestParam(value = "PaymentTypeChargeFee", required = false) String paymentTypeChargeFee,
				@RequestParam(value = "RtnCode", required = false) String rtnCode,
				@RequestParam(value = "RtnMsg", required = false) String rtnMsg,
				@RequestParam(value = "TradeAmt", required = false) String tradeAmt,
				@RequestParam(value = "TradeDate", required = false) String tradeDate,
				@RequestParam(value = "TradeNo", required = false) String tradeNo,
				@RequestParam(value = "CheckMacValue", required = false) String checkMacValue
				) {
			System.out.println("Controller IN");
//			System.out.println(obj);
			System.out.println(rtnCode);
			System.out.println(checkMacValue);
			
			Hashtable<String, String> params = new Hashtable<>();
			
			params.put("MerchantID", merchantID);
			params.put("MerchantTradeNo", merchantTradeNo);
			params.put("PaymentDate", paymentDate);
			params.put("PaymentType", paymentType);
			params.put("PaymentTypeChargeFee", paymentTypeChargeFee);
			params.put("RtnCode", rtnCode);
			params.put("RtnMsg", rtnMsg);
			params.put("TradeAmt", tradeAmt);
			params.put("TradeDate", tradeDate);
			params.put("TradeNo", tradeNo);
			params.put("CheckMacValue", checkMacValue);
			paymentService.compareCheckMacValue(params);
			
			return "redirect:http://localhost:5173/";
			
		}
	
	}

}
