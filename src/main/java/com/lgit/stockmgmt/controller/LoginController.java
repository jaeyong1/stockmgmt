package com.lgit.stockmgmt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lgit.stockmgmt.domain.EUserLevel;
import com.lgit.stockmgmt.domain.UserItem;
import com.lgit.stockmgmt.service.ItemService;

@Controller
public class LoginController {
	/*
	 * Controller - Service 연결
	 */
	@Autowired
	private ItemService itemService;

	/*
	 * 
	 * ================================= Login =================================
	 * /login 사용자 로그인
	 */

	// 세션사용 화면
	@RequestMapping("page1")
	public String page1() {
		return "page1";
	}

	// 세션 사용 안하는 화면
	@RequestMapping("page2")
	public String page2() {
		return "page2";
	}

	// 로그인 화면
	@RequestMapping("login")
	public String login() {
		System.out.println("/login");
		return "login";
	}

	// 로그아웃
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.setAttribute("userLoginInfo", null);
		System.out.println("/logout");
		return "redirect:login";
	}

	// 로그인 처리
	@RequestMapping(value = "loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(UserItem user, HttpSession session, HttpServletRequest request) {
		String reqID = request.getParameter("login-id"); // <html.input>name='login-id'
		String reqPW = request.getParameter("login-pw");

		System.out.println("/loginProcess  try(" + reqID + " / " + reqPW + ")");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:login");

		UserItem loginUser = itemService.findByUserIdAndPassword(reqID, reqPW);

		if (loginUser != null) {
			session.setAttribute("userLoginInfo", loginUser);
		}

		if (loginUser.getUserLevel() == EUserLevel.Lv3_SHIPPER.getLevelInt()) {
			mav.setViewName("redirect:shipreqlist");
		} else {
			mav.setViewName("redirect:mylist");
		}

		return mav;
	}
}