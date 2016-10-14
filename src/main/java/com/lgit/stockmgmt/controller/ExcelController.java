package com.lgit.stockmgmt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.service.ItemService;
import com.lgit.stockmgmt.util.ReadExcelFileToList;



@Controller
public class ExcelController {
	/*
	 * Controller - Service 연결
	 */
	@Autowired
	private ItemService itemService;
	
	 
	/*
	 * 엑셀 처음 접근 
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.GET)
	public String showUploadForm(Model model, HttpServletRequest request) {
		  List<Item> noticelist= new ArrayList<Item>();
		  noticelist.add(new Item("1","aa"));
		  noticelist.add(new Item("2","bb"));
		  noticelist.add(new Item("3","cc"));
		  noticelist.add(new Item("4","dd"));
		  
		  
	        try {
				//WriteListToExcelFile.writeNoticeListToFile("cordova1.xls", noticelist);
				//WriteListToExcelFile.writeNoticeListToFile("cordova2.xlsx", noticelist);
	        	ReadExcelFileToList.readExcelData("cordova2.xlsx");
	        	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage().toString());
			}
	        System.out.println("/excel finish");
	
	
		return "uploadform";
	}
	

}
