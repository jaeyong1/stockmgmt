package com.lgit.stockmgmt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgit.stockmgmt.domain.ShipReqPartsItem;
import com.lgit.stockmgmt.domain.UserItem;
import com.lgit.stockmgmt.service.ItemService;

@Controller
public class ShipController {
	/*
	 * Controller - Service 연결
	 */
	@Autowired
	private ItemService itemService;

	//================================= 출고 장바구니 관련 =================================
	 
	/*
	 * /shipparts 사용자 List, 가입, 수정 관리화면
	 */
	@RequestMapping(value = "/shipparts", method = RequestMethod.GET)
	public String showShipparts2(Model model, HttpServletRequest request) {
		return showShipparts("0", model, request);
	}

	@RequestMapping(value = "/shipparts/{seq}", method = RequestMethod.GET)
	public String showShipparts(@PathVariable String seq, Model model, HttpServletRequest request) {
		if (seq.equalsIgnoreCase("")) {
			seq = "0";
		}
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipparts process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] /shipparts process. req pagenum:" + seq);

		final int rowsPer1Page = 15;

		///////////////////
		// Query DB List
		List<ShipReqPartsItem> items = itemService.getShipPartsListItems(/*shipID*/1);
		System.out.println("/shipparts process");
		model.addAttribute("items", items);
/*
		// Choose current page data
		PagedListHolder<UserItem> paging = new PagedListHolder<UserItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		// System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		// 인덱스..
*/
		return "myreqpartlist"; /* myreqpartlist.jsp*/
	}
		
		
	/*
	 * /shipparts/add
	 */

	
	/*
	 * /shipparts/remove
	 */
	/*
	 * /shipparts/modify
	 */
	
	
	/*
	 * 
	 * ================================= 출고 관련 =================================
	 * /shipreq 사용자 List, 가입, 수정 관리화면
	 */
	/*
	 * /shipreq/add
	 */

	/*
	 * /shipparts/modify
	 */

}
