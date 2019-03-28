package iws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class emailService {
	@Autowired
    private JavaMailSender mailSender;
	
	public void sendEmail(String username) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("18742011168@163.com");
		message.setTo("dluter_wjh@163.com");
		message.setSubject("修改密码，验证码");
		message.setText("成功");
		mailSender.send(message);
	}
}
