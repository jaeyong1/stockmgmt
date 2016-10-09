package com.lgit.stockmgmt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgit.stockmgmt.domain.PartsItem;
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

	// ================================= 출고 장바구니 관련
	// =================================

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
		System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName()
				+ "] /shipparts process. req pagenum:" + seq);

		final int rowsPer1Page = 15;

		///////////////////
		// Query DB List
		List<ShipReqPartsItem> items = itemService.getShipPartsListItems(-1, loginUser.getUserId());
		System.out.println("[" + loginUser.getUserId() +"] /shipparts process");
		model.addAttribute("items", items);
		/*
		 * // Choose current page data PagedListHolder<UserItem> paging = new
		 * PagedListHolder<UserItem>(items); paging.setPageSize(rowsPer1Page);
		 * paging.setPage(Integer.parseInt(seq)); model.addAttribute("items",
		 * paging.getPageList());
		 * 
		 * // Add Page number information model.addAttribute("pageNum",
		 * paging.getPageCount()); model.addAttribute("start",
		 * paging.getFirstLinkedPage()); model.addAttribute("end",
		 * paging.getLastLinkedPage()); //
		 * System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		 * // 인덱스..
		 */
		return "myreqpartlist"; /* myreqpartlist.jsp */
	}

	/*
	 * /shipparts/remove
	 */

	@RequestMapping(value = "/reqshippartsremove", method = RequestMethod.POST)
	public String removeShipParts(ShipReqPartsItem shippartsdata, Model model, HttpServletRequest request) {
		// Get data from Web browser
		shippartsdata.setItemlistId(Integer.valueOf(request.getParameter("itemlist-Id")));

		// Change DB query
		itemService.removeShipPartsItem(shippartsdata);
		model.addAttribute("reqresult", shippartsdata.getItemlistId() + " is removed");
		System.out.println("/reqshippartsremove processed.. Req ID:" + shippartsdata.getItemlistId());

		// Get DB List
		return showShipparts("0", model, request); /* adminuser.jsp */
	}

	/*
	 * /shipparts/modify
	 */
	@RequestMapping(value = "/reqshippartsmodify", method = RequestMethod.POST)
	public String modifyShipParts(ShipReqPartsItem shippartsdata, Model model, HttpServletRequest request) {
		// Get data from Web browser
		shippartsdata.setItemlistId(Integer.valueOf(request.getParameter("itemlist-Id")));
		shippartsdata.setItemlistAmount(Integer.valueOf(request.getParameter("itemlist-Amount")));

		// Change DB query
		itemService.changeShipPartsItem(shippartsdata);
		model.addAttribute("reqresult", shippartsdata.getItemlistId() + "'s data is changed");
		System.out.println("/reqshippartsmodify processed.. Req ID:" + shippartsdata.getItemlistId());

		// Get DB List
		return showShipparts("0", model, request); /* adminuser.jsp */
	}

	/*
	 * /shipparts/add
	 */
	@RequestMapping(value = "/reqshippartsadd", method = RequestMethod.POST)
	public String addShipParts(ShipReqPartsItem shippartsdata, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/reqshippartsadd process. no session info. return login.jsp ");
			return "login";
		}
		System.out
				.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] /reqshippartsadd process. ");

		// Get data from Web browser
		shippartsdata.setUserId(loginUser.getUserId());
		shippartsdata.setItemlistShipId(-1); // -1 은 아직 장바구니안에 아직대기
		shippartsdata.setItemlistPartId(Integer.valueOf(request.getParameter("part-Id")));
		shippartsdata.setItemlistAmount(Integer.valueOf(request.getParameter("reqnum[]")));

		// Change DB query
		itemService.addShipPartsItem(shippartsdata);
		model.addAttribute("reqresult", shippartsdata.getItemlistId() + "'s data is added");
		System.out.println("/reqshippartsadd processed.. Req ID:" + shippartsdata.getItemlistId() + " reqNum:"
				+ shippartsdata.getItemlistAmount());

		// Change DB query
		// itemService.removeShipPartsItem(shippartsdata);
		// model.addAttribute("reqresult", shippartsdata.getItemlistId() + " is
		// removed");
		System.out.println("/reqshippartsadd processed.. "); // Req ID:" +
																// shippartsdata.getItemlistId());

		// Get DB List
		return showShipparts("0", model, request); /* adminuser.jsp */
	}

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
