package com.ispan.recordshop.cochordnuts.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ispan.recordshop.cochordnuts.dto.CartForOrdersDto;
import com.ispan.recordshop.cochordnuts.dto.OrderDetailDto;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.model.OrderDetail;
import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.repository.MemberRepository;
import com.ispan.recordshop.cochordnuts.service.impl.CustomerCaseServiceImpl;
import com.ispan.recordshop.cochordnuts.service.impl.OrderDetailServiceImpl;
import com.ispan.recordshop.cochordnuts.service.impl.OrdersServiceImpl;

@RestController
@CrossOrigin
public class OdersController {

	@Autowired
	private OrdersServiceImpl ordersServiceImpl;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private OrderDetailServiceImpl orderDetailServiceImpl;

	@Autowired
	private CustomerCaseServiceImpl customerCaseServiceImpl;

	@Autowired
	private JavaMailSender mailSender;

	@SuppressWarnings("unused")
	@PostMapping("/orders/insert/{MemberNo}") // 前台下單 User輸入運送方式、付款方式等等
	public String create(@RequestBody Orders od, @PathVariable Integer MemberNo) {

		JSONObject responseJson = new JSONObject(od);
		System.out.println(responseJson);
		od.setMemberNo(memberRepository.findById(MemberNo).get());
		od.setAddress();
		od.setCreateDate();
		od.setStatus();
		od.setLastModifiedDate();
		od.setFreight();
		od.setPaymentStatus("未付款");
		Orders order = ordersServiceImpl.insert(od);
		if (order != null) {
			od.setReceiptNo();
		}
		System.out.println(order.getOrderNo());
		if (order != null) {
			responseJson.put("success", true);
			responseJson.put("message", "新增成功");
			responseJson.put("orderNo", od.getOrderNo());
			responseJson.put("memberNo", MemberNo);
			List<CartForOrdersDto> carts = ordersServiceImpl.findCartByMember2(MemberNo, order);// 依會員編號找到Cart

			for (CartForOrdersDto cart : carts) {// 取得carts陣列資料
				orderDetailServiceImpl.insert(cart);// 新增至orderDetail
				ordersServiceImpl.updateProductByProductNo(cart.getProductNo(), cart.getCount());
			}

			ordersServiceImpl.deleteCartByMemberNo(MemberNo);// 下單後刪除Cart內容
			
			//下單成功寄出Email
			Integer orderNumber = od.getOrderNo();
			String email = od.getMemberEmail();
			Long totalPay = od.getTotalPay();
			String Emailmessage= "尊敬的客户，\r\n" +
                    "感謝您選擇在 CoChordsNuts 購物！\r\n\r\n" +
                    "以下是您的訂單詳情：\r\n" +
                    "訂單編號：" + orderNumber + "\r\n" +
                    "消費金額：$" + totalPay + "\r\n\r\n" +
                    "如果您有任何疑問或需要幫助，請隨時聯繫我們。\r\n" +
                    "再次感謝您的光臨！\r\n" +
                    "祝您生活愉快，\r\n" +
                    "\r\n\r\n\r\n\r\n\r\nCoChordNuts唱片行";
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject("CoChordNuts下單成功");
			message.setText(Emailmessage);
			mailSender.send(message);

		} else {
			responseJson.put("success", false);
			responseJson.put("message", "新增失敗");
		}

		return responseJson.toString();
	}

	@PutMapping("/orders/update/{MemberNo}") // orders修改
	public String update(@RequestBody Orders od, @PathVariable Integer MemberNo) {
		JSONObject responseJson = new JSONObject();
		Orders order = ordersServiceImpl.update(MemberNo, od);
		if (order != null) {
			responseJson.put("success", true);
			responseJson.put("message", "修改成功");
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "修改失敗");
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
		if (obj.getString("num") != null && obj.getString("num") != "") {
			Integer num = Integer.parseInt(obj.getString("num"));
			count = ordersServiceImpl.findOrderCount(num);
		} else {
			count = ordersServiceImpl.findAllOrderCount();
		}
		List<Orders> orders = ordersServiceImpl.selectAll(obj);

		if (orders != null) {
			for (Orders order : orders) {
				JSONObject item = new JSONObject(order);
				array.put(item);
			}
			responseJson.put("list", array);
			responseJson.put("count", count);
		} else {
			responseJson.put("error", false);
		}

		return responseJson.toString();

	}

	@GetMapping("/orders/findByOrderNo/{odNo}") // 依訂單編號，單筆搜尋訂單及訂單詳細內容
	public String findByOrderNo(@PathVariable Integer odNo) {
		JSONObject responseJson = new JSONObject();
		Orders order = ordersServiceImpl.findByOrderNo(odNo).get();// 依訂單編號找到order
		JSONObject obj = new JSONObject(order);
		JSONArray array = new JSONArray();
		List<OrderDetail> orderDetail = orderDetailServiceImpl.findByOdNo(odNo);// 依訂單編號找到orderDetail
		for (OrderDetail anOd : orderDetail) {
			OrderDetailDto Dto = new OrderDetailDto();// 將orderDetail放入DTO傳至前端
			Dto.setCount(anOd.getProductBoughtCount());
			Dto.setProductName(anOd.getProductName());
			Dto.setPrice(anOd.getProductUnitPrice());
			Dto.setTotal(anOd.getProductTotalPay());
			Dto.setDiscount(anOd.getDiscount());
			JSONObject item = new JSONObject(Dto);
			array.put(item);
		}
		boolean isCustomerCaseExist = customerCaseServiceImpl.caseExitByOrderNo(odNo);
		responseJson.put("isCustomerCaseExist", isCustomerCaseExist);
		responseJson.put("orderDetailDto", array);
		responseJson.put("order", obj);
		return responseJson.toString();

	}

	@PostMapping("/orders/findBymemberNo")
	public String findBymemberNo(@RequestBody String json) {// 依會員編號
		JSONObject responseJson = new JSONObject();
		JSONObject obj = new JSONObject(json);
		JSONArray array = new JSONArray();
		Integer count;
		if (obj.getString("num") != null && obj.getString("num") != "") {

			count = ordersServiceImpl.findMemberByMemberNoAndOrderNoCount(obj);

		} else {
			count = ordersServiceImpl.findfindBymemberNoCount(obj);

		}
		List<Orders> orders = ordersServiceImpl.findBymemberNo(obj);
		if (orders != null) {
			for (Orders Order : orders) {
				JSONObject item = new JSONObject(Order);
				array.put(item);
			}
			responseJson.put("count", count);
			responseJson.put("memberOrders", array);
			responseJson.put("result", true);
		} else {
			responseJson.put("result", false);
		}

		return responseJson.toString();
	}

	@GetMapping("/orders/findCartByMemberNo/{memberNo}") // 依會員編號找到Cart 將cart及member傳到前端
	public String findCartByMemberNo(@PathVariable Integer memberNo) {
		JSONObject responseJson = new JSONObject();

		Member member = memberRepository.findById(memberNo).get();
//		JSONObject memberItem = new JSONObject(member);
		String name = member.getName();
		String email = member.getEmail();
		String phone = member.getPhone();
		String address = member.getAddress();
		String recipientAddress;
		String recipientPhone;
		String recipient;
		if (member.getRecipientPhone() == null) {
			recipientPhone = "";
		} else {
			recipientPhone = member.getRecipientPhone();
		}

		if (member.getRecipient() == null) {
			recipient = "";
		} else {
			recipient = member.getRecipient();
		}

		if (member.getRecipientAddress() == null) {
			recipientAddress = "";
		} else {
			recipientAddress = member.getRecipientAddress();
		}

		// 只取前端所需屬性
		responseJson.put("name", name);
		responseJson.put("email", email);
		responseJson.put("phone", phone);
		responseJson.put("address", address);
		responseJson.put("recipientPhone", recipientPhone);
		responseJson.put("recipient", recipient);
		responseJson.put("recipientAddress", recipientAddress);

		return responseJson.toString();
	}

	@PostMapping("/makePayment")
	public ResponseEntity<String> makePayment(@RequestBody String requestBody) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		headers.add("X-LINE-ChannelId", "2005446685");
		headers.add("X-LINE-ChannelSecret", "63941849e2107100e64609b4f2e1cde6");

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		String linePayApiUrl = "https://sandbox-api-pay.line.me/v2/payments/request";

		ResponseEntity<String> response = restTemplate.postForEntity(linePayApiUrl, entity, String.class);

		return new ResponseEntity<>(response.getBody(), response.getStatusCode());
	}

	@PostMapping("/makeConfirm/{transactionId}/{orderNo}")
	public ResponseEntity<String> makeConfirm(@PathVariable String transactionId, @PathVariable String orderNo,
			@RequestBody String requestBody) {
		HttpHeaders headers = new HttpHeaders();
		Integer ordernum = Integer.valueOf(orderNo);
		headers.add("Content-Type", "application/json; charset=UTF-8");
		headers.add("X-LINE-ChannelId", "2005446685");
		headers.add("X-LINE-ChannelSecret", "63941849e2107100e64609b4f2e1cde6");

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		String linePayConfirm = "https://sandbox-api-pay.line.me/v2/payments/" + transactionId + "/confirm";

		ResponseEntity<String> response = restTemplate.postForEntity(linePayConfirm, entity, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			Orders order = ordersServiceImpl.findByOrderNo(ordernum).get();
			order.setPaymentStatus("已付款");
			ordersServiceImpl.insert(order);
		}
		return new ResponseEntity<>(response.getBody(), response.getStatusCode());
	}

}
