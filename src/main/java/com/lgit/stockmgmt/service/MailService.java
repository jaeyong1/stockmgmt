package com.lgit.stockmgmt.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.lgit.stockmgmt.dao.ItemDao;
import com.lgit.stockmgmt.domain.EShipState;
import com.lgit.stockmgmt.domain.ShipReqItem;

@Service("mailService")
public class MailService {

	/*
	 * Controller - Service 연결
	 */
	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemDao itemDao;

	@Value("${email.username}")
	private String username;

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * 메일서버 테스트
	 */
	public String doMailCheck(String rcvAddr, Model model) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			message.setSubject("[안내] 자재관리시스템 발신메일서버 동작점검메일 <자동발신>", "euc-kr");
			String htmlContent = "메일 발송 테스트... <br>";
			message.setText(htmlContent, "euc-kr", "html");
			message.setFrom(new InternetAddress(username));
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
			message.setSubject("[안내] 자재관리시스템 임시 비밀번호 발신 <자동발신>", "euc-kr");
			String htmlContent = "비밀번호가 초기화 되었습니다. 임시 비밀번호는 <Strong>" + newPassword
					+ "</Strong> 입니다. 사이트에 접속해서 로그인 후 비밀번호를 변경하세요.<br>";
			message.setText(htmlContent, "euc-kr", "html");
			message.setFrom(new InternetAddress(username));
			message.addRecipient(RecipientType.TO, new InternetAddress(rcvAddr));
			System.out.println("[비밀번호초기화 메일발송]" + rcvAddr);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/*
	 * state 변경 건
	 */
	public void stateChangeNotiMail(String rcvAddr, String devUserName, String newState, String memo) {
		if (memo == null) {
			memo = "";
		}
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			message.setSubject("[안내] 자재관리시스템에서 " + newState + " 알림 <자동발신>", "euc-kr");
			String htmlContent = devUserName + "님의 요청건 상태 : " + newState + "<br>" + memo + "<br>";
			message.setText(htmlContent, "euc-kr", "html");
			message.setFrom(new InternetAddress(username));
			message.addRecipient(RecipientType.TO, new InternetAddress(rcvAddr));
			System.out.println("[State Changed Mail]" + devUserName + "의 " + newState + "상태를" + rcvAddr + "로..");
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void notiMail1to2(String partnerId, String owenerId) {
		String notistate = EShipState.getShipStateKor(2);
		String partnerEmail = itemService.getUserEmailbyID(partnerId);
		String OwerName = itemService.getUserNamebyID(owenerId);
		stateChangeNotiMail(partnerEmail, OwerName, notistate, "로그인후 - 파트너출고요청 - 파트너승인하기 를 통해 확인해 주세요.."); // toPartner
	}

	public void notiMail2to3(int shipId) {
		String notistate = EShipState.getShipStateKor(3);
		String shipperEmail = itemService.getShipperEmailbyShipId(shipId);
		String ownerName = itemService.getOwnerNamebyShipId(shipId);
		stateChangeNotiMail(shipperEmail, ownerName, notistate, "로그인후 - 출고요청처리를 통해 확인해 주세요.."); // toShipper

		String ownerEmail = itemService.getOwnerEmailbyShipId(shipId);
		stateChangeNotiMail(ownerEmail, ownerName, notistate,
				"협의출고요청건을 파트너가 승인했습니다. 출고담당자의 출고접수를 대기중입니다. <br>진행상황은 로그인후 - 출고진행상황을 통해 볼수 있습니다."); // toOwner

	}

	public void notiMail1to3(int partsShipreqId, String userId) {
		String notistate = EShipState.getShipStateKor(3);
		String shipperEmail = itemService.getShipperEmailbyShipId(partsShipreqId);
		String OwerName = itemService.getUserNamebyID(userId);
		stateChangeNotiMail(shipperEmail, OwerName, notistate, "로그인후 - 출고요청처리를 통해 확인해 주세요.."); // toShipper

	}

	public void notiMail3to4(int shipId) {
		String notistate = EShipState.getShipStateKor(4);
		String ownerEmail = itemService.getOwnerEmailbyShipId(shipId);
		String ownerName = itemService.getOwnerNamebyShipId(shipId);
		stateChangeNotiMail(ownerEmail, ownerName, notistate, "출고담당자가 요청내용을 확인했습니다.<br>진행상황은 로그인후 - 출고진행상황을 통해 볼수 있습니다."); // toOwner

	}

	public void notiMail4to5(int shipId, String shipDeliveredDateMethod) {
		String notistate = EShipState.getShipStateKor(5);
		String ownerEmail = itemService.getOwnerEmailbyShipId(shipId);
		String ownerName = itemService.getOwnerNamebyShipId(shipId);
		stateChangeNotiMail(ownerEmail, ownerName, notistate, "출고담당자 남김말(요청결과) : " + shipDeliveredDateMethod +"<br>진행상황은 로그인후 - 출고진행상황을 통해 볼수 있습니다."); // toOwner
	}

	public void notiMail4to6(int shipId, String shipRejectCause) {
		String notistate = EShipState.getShipStateKor(6);
		String ownerEmail = itemService.getOwnerEmailbyShipId(shipId);
		String ownerName = itemService.getOwnerNamebyShipId(shipId);
		stateChangeNotiMail(ownerEmail, ownerName, notistate, "반려사유 : " + shipRejectCause +"<br>진행상황은 로그인후 - 출고진행상황을 통해 볼수 있습니다."); // toOwner
	}

}
