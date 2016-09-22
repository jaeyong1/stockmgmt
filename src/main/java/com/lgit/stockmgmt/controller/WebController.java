/**
 * [Controller] 
 * 최초 웹브라우저에서 접근시 주소로 처리시작하는 부분
 * 
 * @author jaeyong1.park
 */
package com.lgit.stockmgmt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.domain.PartsItem;
import com.lgit.stockmgmt.domain.ProjectItem;
import com.lgit.stockmgmt.domain.UserItem;
import com.lgit.stockmgmt.service.ItemService;

@Controller
public class WebController {
	/*
	 * Test Controller
	 */
	@RequestMapping("/")
	public String getIndex(Model model) {
		// model.addAttribute("name", "John Lee");
		System.out.println("GET Index2");
		return "index"; /* WebController.java를 참고해서 해당경로에서 파일 호출 */
	}

	/*
	 * Test Controller
	 */
	@RequestMapping("/hello")
	public String helloProcess(Model model) {
		model.addAttribute("name", "John Lee");
		System.out.println("hello process -John lee");
		return "itemlist_test";
	}

	/*
	 * Controller - Service 연결
	 */
	@Autowired
	private ItemService itemService;

	/*
	 * /stocklist 재고 조회 화면
	 */
	@RequestMapping(value = "/stocklist", method = RequestMethod.GET)
	public String getItemList(Model model) {
		List<Item> items = itemService.getItems(); // 위에서 Autowired로 연결=객체생성
		System.out.println("stocklist process");

		model.addAttribute("name", "John Lee");
		model.addAttribute("items", items);
		return "stocklist"; /* stocklist.jsp */
	}

	/*
	 * /adminproject 프로젝트 관리 화면
	 */
	@RequestMapping(value = "/admin/project", method = RequestMethod.GET)
	public String showAdminProject(Model model) {
		List<ProjectItem> items = itemService.getProjectItems(); // 위에서
																	// Autowired로
																	// 연결=객체생성
		System.out.println("/adminproject process");
		model.addAttribute("items", items);
		return "adminproj"; /* adminproj.jsp */
	}

	/*
	 * /adminparts Parts 관리 화면
	 */
	@RequestMapping(value = "/admin/parts", method = RequestMethod.GET)
	public String showAdminParts(Model model) {
		List<PartsItem> items = itemService.getPartsItems(); // 위에서 Autowired로
																// 연결=객체생성
		System.out.println("/adminparts process");
		model.addAttribute("items", items);
		return "adminparts"; /* adminparts.jsp */
	}

	/*
	 * /adminuser 사용자관리 화면
	 */
	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public String showAdminUser(Model model) {
		List<UserItem> items = itemService.getUserItems(); // 위에서 Autowired로
															// 연결=객체생성
		System.out.println("/adminuser process");
		model.addAttribute("items", items);
		return "adminuser"; /* adminuser.jsp */
	}
	

	/*
	 * /adminuser 사용자 신규 등록처리
	 */
	@RequestMapping(value = "/admin/adduser", method = RequestMethod.POST)
	public String addAdminUser(UserItem userdata, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		userdata.setUserId(request.getParameter("user-Id"));
		userdata.setUserName(request.getParameter("user-Name"));
		userdata.setUserEmail(request.getParameter("user-Email"));
		userdata.setUserPassword(request.getParameter("user-Password"));
		userdata.setUserTeamname(request.getParameter("user-Teamname"));
		userdata.setUserLevel(Integer.valueOf(request.getParameter("user-Level")));

		// insert DB query
		itemService.setUserItem(userdata);
		model.addAttribute("reqresult", userdata.getUserId() + " is added");

		// Get DB List
		List<UserItem> items = itemService.getUserItems();
		model.addAttribute("items", items);
		System.out.println("/admin/adduser process ID:" + userdata.getUserId());
		return "adminuser"; /* adminuser.jsp */
	}

	/*
	 * /admin/reqresetpassword 사용자 비밀번호 리셋
	 */
	@RequestMapping(value = "/admin/reqresetpassword", method = RequestMethod.POST)
	public String adminResetPassword(UserItem userdata, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		userdata.setUserId(request.getParameter("user-Id"));
		userdata.setUserPassword("defaultPassWord123");

		// Change DB query
		itemService.changeUserPassword(userdata);
		model.addAttribute("reqresult", userdata.getUserId() + "'s Password Reseted.");
		System.out.println("/admin/reqresetpassword processed.. Req ID:" + userdata.getUserId());

		// Get DB List
		List<UserItem> items = itemService.getUserItems();
		model.addAttribute("items", items);
		return "adminuser"; /* adminuser.jsp */
	}
	

	/*
	 * /admin/reqmodify 사용자 정보변경 
	 */
	@RequestMapping(value = "/admin/reqmodify", method = RequestMethod.POST)
	public String addAdminUserModify(UserItem userdata, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		userdata.setUserId(request.getParameter("user-Id"));
		userdata.setUserName(request.getParameter("user-Name"));
		userdata.setUserEmail(request.getParameter("user-Email"));
		userdata.setUserTeamname(request.getParameter("user-Teamname"));
		userdata.setUserLevel(Integer.valueOf(request.getParameter("user-Level")));

		// Change DB query
		itemService.changeUserItem(userdata);
		model.addAttribute("reqresult", userdata.getUserId() + "'s datas are changed");
		System.out.println("/admin/reqmodify processed.. Req ID:" + userdata.getUserId());
		
		// Get DB List
		List<UserItem> items = itemService.getUserItems();
		model.addAttribute("items", items);
		return "adminuser"; /* adminuser.jsp */
	}
	

}