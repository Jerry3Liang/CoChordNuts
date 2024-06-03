package ecpay.payment.integration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ecpay.payment.integration.ecpayOperator.EcpayFunction;

public class PaymentURL {
	public static String URL;
	protected static Document verifyDoc;
	
	public PaymentURL() {
		
		Document doc;
		
		String paymentConfPath = "./src/main/resources/payment_conf.xml";
		doc = EcpayFunction.xmlParser(paymentConfPath);
		
		Element ele = (Element)doc.getElementsByTagName("URL").item(0);
		URL = ele.getTextContent();
		
	}

	public static String getURL() {
		return URL;
	}

	
	
	

}
