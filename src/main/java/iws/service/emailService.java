package iws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class emailService {
	@Autowired
    private JavaMailSender mailSender;
	
	public void sendEmail(String to,String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("18742011168@163.com");
		message.setTo(to);
		message.setSubject("修改密码，验证码");
		message.setText(text);
		mailSender.send(message);
	}
}
