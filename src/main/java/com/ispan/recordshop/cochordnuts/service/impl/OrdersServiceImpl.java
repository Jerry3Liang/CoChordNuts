package com.ispan.recordshop.cochordnuts.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.Orders;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;
import com.ispan.recordshop.cochordnuts.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService {
	
	@Autowired
	private OrderRepository orderRepository;

	public List<Orders>selectAll() {		
		return orderRepository.findAll();		
	}

	public Orders insert(String Json) {
		JSONObject obj = new JSONObject(Json);


//		Integer freight= obj.isNull("freight")?null: obj.getInt("freight");
//		Integer totalCount= obj.isNull("totalCount")?null: obj.getInt("totalCount");	
//		String payment= obj.isNull("payment")?null: obj.getString("payment");
//		String creditCardNo= obj.isNull("creditCardNo")?null: obj.getString("creditCardNo");
//		String receiptType= obj.isNull("receiptType")?null: obj.getString("receiptType");
//		String receiptNo= obj.isNull("receiptNo")?null: obj.getString("receiptNo");
//		String recipient= obj.isNull("recipient")?null: obj.getString("recipient");
//		String recipientAddress= obj.isNull("recipientAddress")?null: obj.getString("recipientAddress");
//		String recipientPhone= obj.isNull("recipientPhone")?null: obj.getString("recipientPhone");
//		String note= obj.isNull("note")?null: obj.getString("note");
		
		try {
			String createDateStr= obj.isNull("createDate")?null: obj.getString("createDate");
			String preparationDateStr= obj.isNull("preparationDate")?null: obj.getString("preparationDate");
			String dispatchDateStr= obj.isNull("dispatchDate")?null: obj.getString("dispatchDate");
			String completeDateStr= obj.isNull("completeDate")?null: obj.getString("completeDate");
			String cancelDateStr= obj.isNull("cancelDate")?null: obj.getString("cancelDate");
			String returnDateStr= obj.isNull("returnDate")?null: obj.getString("returnDate");			
			Integer totalPay= obj.isNull("totalPay")?null: obj.getInt("totalPay");
			String deliverType= obj.isNull("deliverType")?null: obj.getString("deliverType");
						
			Date createDate = new SimpleDateFormat("yyyy-MM-dd").parse(createDateStr);
			Date preparationDate = new SimpleDateFormat("yyyy-MM-dd").parse(preparationDateStr);
			Date dispatchDate = new SimpleDateFormat("yyyy-MM-dd").parse(dispatchDateStr);
			Date completeDate = new SimpleDateFormat("yyyy-MM-dd").parse(completeDateStr);
			Date cancelDate = new SimpleDateFormat("yyyy-MM-dd").parse(cancelDateStr);
			Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDateStr);
				
			Orders insert=new Orders();
			insert.setDeliverType(deliverType);
			insert.setTotalPay(totalPay);
			insert.setCreateDate(createDate);
			insert.setPreparationDate(preparationDate);
			insert.setDispatchDate(dispatchDate);
			insert.setCompleteDate(completeDate);
			insert.setCnacelDate(cancelDate);
			insert.setReturnDate(returnDate);
			
			return orderRepository.save(insert);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		try {

//			if(orderNo!=null) {
//				Optional<Orders> optional = orderRepository.findById(orderNo);
//				if(optional.isEmpty()) {
//					Orders insert=new Orders();

//					insert.setPreparationDate(preparationDate);
//					insert.setPreparationDate(preparationDate);
//					insert.setPreparationDate(preparationDate);
//				}
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
		return null;
	}	
	public Orders update(String Json) {
		return null;
	}	
	public void deleteById(Integer ordersNo) {
		orderRepository.deleteById(ordersNo);	
	}

	

}
