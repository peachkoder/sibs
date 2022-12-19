package com.peachkoder.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.peachkoder.model.entity.Order;
import com.peachkoder.model.entity.User;

import lombok.AllArgsConstructor;

@PropertySource(value = { "mail.properties" })
@Service
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;

	public void sendMail(User user, Order order) {
		Thread sendEmalThread = new Thread(new EmailService().new RunnableImpl(user, order, javaMailSender));
		sendEmalThread.setName("EMAIL THREAD - " + sendEmalThread.getId() );
		sendEmalThread.start();

	}

	@AllArgsConstructor
	private class RunnableImpl implements Runnable {

		private User user;

		private Order order;

		JavaMailSender javaMailSender;

		@Override
		public void run() {
//			MimeMessage message = javaMailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(message);
//			try {
//				helper.setFrom("sibscustomerservice@sibs.com");
//				helper.setTo("user.getEmail()");
//				helper.setText(
//		            "Dear " + user.getName() 
//		                + ", thank you for placing order. Your order number is "
//		                + order.getId());
//				helper.setSubject("Your Order is Completd");
//				javaMailSender.send(message);	
				writeLog(user, order);
//			} catch (MessagingException e) { 
//				throw new RuntimeException(e)
//			} 	
		}
		
		private synchronized void writeLog(User user, Order order) {
			Logger log = LogManager.getLogger("com.peachkoder.email" );
			log.warn("ORDEM COMPLETE - EMAIL MESSAGE SENT. ORDERID={} CUSTOMERID={} SENDTO={}", order.getId(), user.getId(), user.getEmail());
			
		}
	}

}
