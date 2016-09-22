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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	 * /adminuser 사용자 List, 가입, 수정 관리화면
	 */
	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public String showAdminUser2(Model model) {
		return showAdminUser("1", model);
	}

	@RequestMapping(value = "/admin/user/{seq}", method = RequestMethod.GET)
	public String showAdminUser(@PathVariable String seq, Model model) {

		if (seq.equalsIgnoreCase("")) {
			seq = "1";

		}
		System.out.println("seq" + seq);

		model.addAttribute("pnum", seq);
		int startPage = 0;
		int endPage = 0;
		int page = 0;

		try {
			// 시작페이지 설정 1~5 페이지 일경우 1​​
			startPage = (Integer.parseInt(seq) - 1) / 5 * 5 + 1;
			// ex) 현재 6페이지 일경우 (6-1) /5 * 5 +1 = 1 -> 6 페이지 부터 시작​​

			endPage = startPage + 5 - 1;

			if (seq != null && seq != "") {
				if (!seq.equals("1")) {
					// 첫페이지가 아닐경우 그 페이지에 맞는 목록 뽑아옴​
					int temp = (Integer.parseInt(seq) - 1) * 15;
					page = temp;

				} else if (seq.equals("1")) {
					// 페이지 번호가 1이면 처음부터 15개​
					page = 0;
				}

			}
		} catch (Exception e) {
			// 이상한 페이지 번호 들어오면 해당 게시판 처음으로 리다이렉트​
			return "redirect:/admin/user/1";
		}
		// 전체 게시물 갯수 뽑아옴 ​

		String rownum = itemService.getUserItemsRow();
		System.out.println("item rows : " + rownum);
		// pageNum 변수는 전체 페이지의 수​
		int pageNum = Integer.parseInt(rownum) / 15 + 1;
		// 게시물이 딱 15개일 경우 다음페이지가 생기지 않게 -1 해줌​

		if (Integer.parseInt(rownum) % 15 == 0) {
			pageNum--;
		}

		if (endPage > pageNum) {
			// 예를 들어 마지막페이지가 12페이지인 경우 endPage가 15페이지 까지 출력되기때문에 12페이지로 바꿔줌​

			endPage = pageNum;

		}

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("start", startPage);
		model.addAttribute("end", endPage);
		///////////////////

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