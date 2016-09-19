package com.lgit.stockmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lgit.stockmgmt.domain.Item;
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
	 * Controller - Service 연결 예시
	 */
	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "/itemlist", method = RequestMethod.GET)
	public String getItemList(Model model) {
		List<Item> items = itemService.getItems(); // 위에서 Autowired로 연결=객체생성
		model.addAttribute("name", "itemlist John Lee"); // simple value. jsp코드 : ${name}
		System.out.println("itemlist process");
		
		if (!items.isEmpty()) {
			System.out.println(items.get(0).getDevId());
			System.out.println(items.get(0).getProjecName());
		}

		return "itemlist"; /* itemlist.jsp */

	}

	@RequestMapping("/listtest")
	public ModelAndView listProcess() {

		List<String> list = null;// = getList();
		ModelAndView model = new ModelAndView("itemlist");
		System.out.println(list.toString());

		model.addObject("lists", list);

		return model;
	}

}