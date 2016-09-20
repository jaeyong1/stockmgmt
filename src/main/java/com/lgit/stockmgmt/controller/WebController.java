/**
 * [Controller] 
 * 최초 웹브라우저에서 접근시 주소로 처리시작하는 부분
 * 
 * @author jaeyong1.park
 */
package com.lgit.stockmgmt.controller;

import java.util.List;

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
	@RequestMapping(value = "/adminproject", method = RequestMethod.GET)
	public String showAdminProject(Model model) {
		List<ProjectItem> items = itemService.getProjectItems(); // 위에서 Autowired로 연결=객체생성
		System.out.println("/adminproject process");		
		model.addAttribute("items", items);
		return "adminproj"; /* adminproj.jsp */
	}
	

	/*
	 * /adminparts Parts 관리 화면
	 */	
	@RequestMapping(value = "/adminparts", method = RequestMethod.GET)
	public String showAdminParts(Model model) {
		List<PartsItem> items = itemService.getPartsItems(); // 위에서 Autowired로 연결=객체생성
		System.out.println("/adminparts process");		
		model.addAttribute("items", items);
		return "adminparts"; /* adminparts.jsp */
	}
	

	/*
	 * /adminuser 사용자관리 화면
	 */	
	@RequestMapping(value = "/adminuser", method = RequestMethod.GET)
	public String showAdminUser(Model model) {
		List<UserItem> items = itemService.getUserItems(); // 위에서 Autowired로 연결=객체생성
		System.out.println("/adminuser process");		
		model.addAttribute("items", items);
		return "adminuser"; /* adminuser.jsp */
	}
	
	
	

}