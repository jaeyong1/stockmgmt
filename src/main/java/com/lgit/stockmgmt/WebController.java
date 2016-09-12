package com.lgit.stockmgmt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.service.ItemService;

@Controller
public class WebController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/")
	public String getIndex(Model model) {
		//model.addAttribute("name", "John Lee");
		System.out.println("GET Index2");
		return "index";  /*WebController.java를 참고해서 해당경로에서 파일 호출 */
	}
	
	
	@RequestMapping("/hello")
	public String helloProcess(Model model) {
		//model.addAttribute("name", "John Lee");
		System.out.println("hello process");
		return "layout";
	}
	
	
	@RequestMapping(value = "/itemlist", method = RequestMethod.GET)
	public String itemListProcess(Model model) {
		List<Item> items = itemService.getItems();
		
		//model.addAttribute("name", "John Lee");
		System.out.println("itemlist process");
		return "itemlist"; /* itemlist.jsp */
	}
}