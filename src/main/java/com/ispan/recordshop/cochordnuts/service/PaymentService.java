package com.ispan.recordshop.cochordnuts.service;

import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.service.impl.OrdersServiceImpl;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class PaymentService {
	
	@Autowired
	private OrdersServiceImpl ordersService;
	
	public String ecpayCheckout(JSONObject json) {
		
		Integer amountInt = json.isNull("totalPay")? null : json.getInt("totalPay");
		Integer orderNoInt = json.isNull("orderNo")? null : json.getInt("orderNo");
		Integer memberNoInt = json.isNull("memberNo")? null : json.getInt("memberNo");
		String amount = amountInt.toString();
		String orderNoStr = "CoChordNuts"+orderNoInt.toString();
		String orderNo = orderNoInt.toString();
		String memberNo = memberNoInt.toString();
		String date = DatetimeConverter.toString(new Date(), "yyyy/MM/dd HH:mm:ss");
		
		//產生隨機碼避免訂單重複
		String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4);

		AllInOne all = new AllInOne("");

		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(orderNoStr+uuId);
		obj.setMerchantTradeDate(date);
		obj.setTotalAmount(amount);
		obj.setTradeDesc("test Description");
		obj.setItemName("TestItem");
		obj.setCustomField1(orderNo);
		obj.setCustomField2(memberNo);
		// 交易結果回傳網址，只接受 https 開頭的網站，可以使用 ngrok
		obj.setReturnURL("http://localhost:8080/ecpayReturn");
		obj.setOrderResultURL("http://localhost:8080/ecpayReturn");
		obj.setNeedExtraPaidInfo("N");
		// 商店轉跳網址 (Optional)
		obj.setClientBackURL("http://localhost:5173/");
		String form = all.aioCheckOut(obj, null);

		return form;
	}
	
	public boolean compareCheckMacValue(Hashtable<String, String> params) {
		AllInOne all = new AllInOne("");
		return all.compareCheckMacValue(params);
	}
	
	//修改付款狀態
	public void updatePaymentStatus(Integer memberNo, Integer orderNo) {
		Orders order = ordersService.findByOrderNo(orderNo).get();
		order.setPaymentStatus("已付款");
		ordersService.update(memberNo, order);
		
	}
	
	
	
	

}
