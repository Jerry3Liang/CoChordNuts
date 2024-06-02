package com.ispan.recordshop.cochordnuts.controller;

import java.util.Hashtable;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.service.PaymentService;

@RestController
@CrossOrigin
public class PaymentController {
	
	
	@Autowired
	private PaymentService paymentService;

	@PostMapping("/ecpayCheckout")
	public String ecpayCheckout(@RequestBody String obj) {
		JSONObject json = new JSONObject(obj);
		String aioCheckOutALLForm = paymentService.ecpayCheckout(json);
		System.out.println(aioCheckOutALLForm);
		return aioCheckOutALLForm;
	}
	
	@Controller
	public class PaymentReturnController {
		@PostMapping("/ecpayReturn")
		public String ecpayReturn(
				@RequestParam(value = "CustomField1", required = false) String customField1,
				@RequestParam(value = "CustomField2", required = false) String customField2,
				@RequestParam(value = "CustomField3", required = false) String customField3,
				@RequestParam(value = "CustomField4", required = false) String customField4,
				@RequestParam(value = "MerchantID", required = false) String merchantID,
				@RequestParam(value = "MerchantTradeNo", required = false) String merchantTradeNo,
				@RequestParam(value = "PaymentDate", required = false) String paymentDate,
				@RequestParam(value = "PaymentType", required = false) String paymentType,
				@RequestParam(value = "PaymentTypeChargeFee", required = false) String paymentTypeChargeFee,
				@RequestParam(value = "RtnCode", required = false) String rtnCode,
				@RequestParam(value = "RtnMsg", required = false) String rtnMsg,
				@RequestParam(value = "SimulatePaid", required = false) String simulatePaid,
				@RequestParam(value = "StoreID", required = false) String storeID,
				@RequestParam(value = "TradeAmt", required = false) String tradeAmt,
				@RequestParam(value = "TradeDate", required = false) String tradeDate,
				@RequestParam(value = "TradeNo", required = false) String tradeNo,
				@RequestParam(value = "CheckMacValue", required = false) String checkMacValue
				) {
			
			Hashtable<String, String> params = new Hashtable<>();
			
			params.put("CustomField1", customField1);
			params.put("CustomField2", customField2);
			params.put("CustomField3", customField3);
			params.put("CustomField4", customField4);
			params.put("MerchantID", merchantID);
			params.put("MerchantTradeNo", merchantTradeNo);
			params.put("PaymentDate", paymentDate);
			params.put("PaymentType", paymentType);
			params.put("PaymentTypeChargeFee", paymentTypeChargeFee);
			params.put("RtnCode", rtnCode);
			params.put("RtnMsg", rtnMsg);
			params.put("SimulatePaid", simulatePaid);
			params.put("StoreID", storeID);
			params.put("TradeAmt", tradeAmt);
			params.put("TradeDate", tradeDate);
			params.put("TradeNo", tradeNo);
			params.put("CheckMacValue", checkMacValue);
			boolean checkResult = paymentService.compareCheckMacValue(params);
			
			if(rtnCode.equals("1") && checkResult == true) {
				Integer orderNo = Integer.parseInt(customField1);
				Integer memberNo = Integer.parseInt(customField2);
				System.out.println(orderNo);
				System.out.println(memberNo);
				return "redirect:http://localhost:5173/order/ECPaymentSuccess";
			} else {
				return "redirect:http://localhost:5173/order/ECPaymentFail";
			}
			
		}
	
	}

}
