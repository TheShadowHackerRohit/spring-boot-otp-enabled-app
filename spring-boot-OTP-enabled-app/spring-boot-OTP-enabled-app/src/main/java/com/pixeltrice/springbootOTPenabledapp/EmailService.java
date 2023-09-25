package com.pixeltrice.springbootOTPenabledapp;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	//we have autowired or injected the predefined class named JavaMailSender which contains the method to send the mail.
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendOtpMessage(String to, String subject, String message) throws MessagingException {
	
		 MimeMessage msg = javaMailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

	        helper.setFrom("rohitsahume0104@gmail.com");
	        
	        helper.setTo(to);

	        helper.setSubject(subject);

	        helper.setText(message, true);

	        javaMailSender.send(msg);
	        
   }
	
}
	
