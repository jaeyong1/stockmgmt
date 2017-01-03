package com.lgit.stockmgmt.service;

import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service("mailService")
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * 메일서버 테스트
	 */
	public String doMailCheck(String rcvAddr, Model model) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			message.setSubject("[안내] 자재관리시스템 발신메일서버 동작점검메일 <자동발신>", "euc-kr");
			String htmlContent = "당신의 임시 비밀번호는 <Strong>" + "jyp1234567"
					+ "</Strong> 입니다. 사이트에 접속해서 로그인 후 비밀번호를 변경하세요.<br>";
			htmlContent += "<a href='http://localhost:9999/index.choon' target='_blank'>홈으로 이동</a>";
			message.setText(htmlContent, "euc-kr", "html");
			message.setFrom(new InternetAddress("pjaeyong1@gmail.com"));
			message.addRecipient(RecipientType.TO, new InternetAddress(rcvAddr));
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		String err = "[테스트메일 발송됨]<br>수신:\n" + rcvAddr + "<br>\n";
		model.addAttribute("errormsg", err);
		return "dbcheck";
	}

	/*
	 * 관리자 비번 초기화 후 메일 발송
	 */
	public void adminResetMail(String rcvAddr, String newPassword) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			message.setSubject("[안내] 자재관리시스템 임시 비밀번호 발신 <시스템 자동발신>", "euc-kr");
			String htmlContent = "비밀번호가 초기화 되었습니다. 임시 비밀번호는 <Strong>" + newPassword
					+ "</Strong> 입니다. 사이트에 접속해서 로그인 후 비밀번호를 변경하세요.<br>";
			message.setText(htmlContent, "euc-kr", "html");
			message.setFrom(new InternetAddress("pjaeyong1@gmail.com"));
			message.addRecipient(RecipientType.TO, new InternetAddress(rcvAddr));
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
