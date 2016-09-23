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
	 * 
	 * =============================== PROJECT ===============================
	 * /adminproject 프로젝트 관리 화면
	 */
	@RequestMapping(value = "/admin/project", method = RequestMethod.GET)
	public String showAdminProject2(Model model) {
		return showAdminProject1("1", model);
	}

	@RequestMapping(value = "/admin/project/{seq}", method = RequestMethod.GET)
	public String showAdminProject1(@PathVariable String seq, Model model) {

		if (seq.equalsIgnoreCase("")) {
			seq = "1";

		}
		System.out.println("seq:" + seq);

		model.addAttribute("pnum", seq);
		int startPage = 0;
		int endPage = 0;
		int page = 0;
		int rowsPer1Page = 15;

		try {
			// 시작페이지 설정 1~5 페이지 일경우 1​​
			startPage = (Integer.parseInt(seq) - 1) / 5 * 5 + 1;
			// ex) 현재 6페이지 일경우 (6-1) /5 * 5 +1 = 1 -> 6 페이지 부터 시작​​

			endPage = startPage + 5 - 1;

			if (seq != null && seq != "") {
				if (!seq.equals("1")) {
					// 첫페이지가 아닐경우 그 페이지에 맞는 목록 뽑아옴​
					int temp = (Integer.parseInt(seq) - 1) * rowsPer1Page;
					page = temp;

				} else if (seq.equals("1")) {
					// 페이지 번호가 1이면 처음부터 15개​(rowsPer1Page)
					page = 0;
				}

			}
		} catch (Exception e) {
			// 이상한 페이지 번호 들어오면 해당 게시판 처음으로 리다이렉트​
			System.out.println("=== admin project , Page number error!! ===");
			return "redirect:/admin/project/1";
		}
		// 전체 게시물 갯수 뽑아옴 ​

		String rownum = itemService.getPrjectsItemsRow();
		System.out.println("item rows : " + rownum);
		// pageNum 변수는 전체 페이지의 수​
		int pageNum = Integer.parseInt(rownum) / rowsPer1Page + 1;
		// 게시물이 딱 15개일 경우 다음페이지가 생기지 않게 -1 해줌​

		if (Integer.parseInt(rownum) % rowsPer1Page == 0) {
			pageNum--;
		}

		if (endPage > pageNum) {
			// 예를 들어 마지막페이지가 12페이지인 경우 endPage가 15페이지(rowsPer1Page) 까지 출력되기때문에
			// 12페이지로 바꿔줌​

			endPage = pageNum;

		}

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("start", startPage);
		model.addAttribute("end", endPage);
		///////////////////
		List<ProjectItem> items = itemService.getProjectItems();

		System.out.println("/admin/project process");
		model.addAttribute("items", items);
		return "adminproj"; /* adminproj.jsp */
	}

	/*
	 * /admin/addproject 프로젝트 신규 등록
	 */
	@RequestMapping(value = "/admin/addproject", method = RequestMethod.POST)
	public String addAdminProject(ProjectItem item, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		item.setProjectCode(request.getParameter("project-Code"));
		item.setProjectName(request.getParameter("project-Name"));
		item.setProjectOwnerId(request.getParameter("project-Owner-Id"));
		item.setProjectShipperId(request.getParameter("project-shipper-Id"));

		// insert DB query
		itemService.setProjectItem(item);
		model.addAttribute("reqresult", item.getProjectCode() + " is added");

		// Get DB List
		return showAdminProject1("1", model);/* adminproj.jsp */
	}

	/*
	 * /admin/reqprojectmodify 프로젝트 정보변경
	 */
	@RequestMapping(value = "/admin/reqprojectmodify", method = RequestMethod.POST)
	public String modifyAdminProject(ProjectItem item, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		item.setProjectCode(request.getParameter("project-Code"));
		item.setProjectName(request.getParameter("project-Name"));
		item.setProjectOwnerId(request.getParameter("project-Owner-Id"));
		item.setProjectShipperId(request.getParameter("project-shipper-Id"));

		// Change DB query
		itemService.changeProjectItem(item);
		model.addAttribute("reqresult", item.getProjectCode() + "'s datas are changed");
		System.out.println("/admin/reqprojectmodify processed.. Req ID:" + item.getProjectCode());

		// Get DB List
		return showAdminProject1("1", model); /* adminuser.jsp */
	}

	/*
	 * /admin/reqprojectremove 프로젝트 아이템 제거
	 */
	@RequestMapping(value = "/admin/reqprojectremove", method = RequestMethod.POST)
	public String removeAdminProject(ProjectItem item, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		item.setProjectCode(request.getParameter("project-Code"));

		// Change DB query
		itemService.removeProjectItem(item);
		model.addAttribute("reqresult", item.getProjectCode() + " is removed");
		System.out.println("/admin/reqprojectremove processed.. Req ID:" + item.getProjectCode());

		// Get DB List
		return showAdminProject1("1", model); /* adminuser.jsp */
	}

	/*
	 * 
	 * ================================= PARTS =================================
	 * /adminparts Parts 관리 화면
	 */

	@RequestMapping(value = "/admin/parts", method = RequestMethod.GET)
	public String showAdminParts2(Model model) {
		return showAdminParts1("1", model);
	}

	@RequestMapping(value = "/admin/parts/{seq}", method = RequestMethod.GET)
	public String showAdminParts1(@PathVariable String seq, Model model) {
		if (seq.equalsIgnoreCase("")) {
			seq = "1";
		}
		System.out.println("seq:" + seq);

		model.addAttribute("pnum", seq);
		int startPage = 0;
		int endPage = 0;
		int page = 0;
		int rowsPer1Page = 15;

		try {
			// 시작페이지 설정 1~5 페이지 일경우 1​​
			startPage = (Integer.parseInt(seq) - 1) / 5 * 5 + 1;
			// ex) 현재 6페이지 일경우 (6-1) /5 * 5 +1 = 1 -> 6 페이지 부터 시작​​

			endPage = startPage + 5 - 1;

			if (seq != null && seq != "") {
				if (!seq.equals("1")) {
					// 첫페이지가 아닐경우 그 페이지에 맞는 목록 뽑아옴​
					int temp = (Integer.parseInt(seq) - 1) * rowsPer1Page;
					page = temp;

				} else if (seq.equals("1")) {
					// 페이지 번호가 1이면 처음부터 15개​(rowsPer1Page)
					page = 0;
				}

			}
		} catch (Exception e) {
			// 이상한 페이지 번호 들어오면 해당 게시판 처음으로 리다이렉트​
			System.out.println("=== admin parts , Page number error!! ===");
			return "redirect:/admin/parts/1";
		}
		// 전체 게시물 갯수 뽑아옴 ​

		String rownum = itemService.getPartsItemsRow();
		System.out.println("item rows : " + rownum);
		// pageNum 변수는 전체 페이지의 수​
		int pageNum = Integer.parseInt(rownum) / rowsPer1Page + 1;
		// 게시물이 딱 15개일 경우 다음페이지가 생기지 않게 -1 해줌​

		if (Integer.parseInt(rownum) % rowsPer1Page == 0) {
			pageNum--;
		}

		if (endPage > pageNum) {
			// 예를 들어 마지막페이지가 12페이지인 경우 endPage가 15페이지(rowsPer1Page) 까지 출력되기때문에
			// 12페이지로 바꿔줌​

			endPage = pageNum;

		}

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("start", startPage);
		model.addAttribute("end", endPage);
		/////////////////// Page - Item class

		/////////////////// List View
		List<PartsItem> items = itemService.getPartsItems();
		System.out.println("/adminparts process");
		model.addAttribute("items", items);
		return "adminparts"; /* adminparts.jsp */
	}

	/*
	 * /admin/addparts 프로젝트 신규 등록
	 */
	@RequestMapping(value = "/admin/addparts", method = RequestMethod.POST)
	public String addAdminParts(PartsItem item, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		item.setPartProjectCode(request.getParameter("part-Project-Code"));
		item.setPartName(request.getParameter("part-Name"));
		item.setPartDesc(request.getParameter("part-Desc"));
		item.setPartLocation(request.getParameter("part-Location"));
		item.setPartCost(Float.valueOf(request.getParameter("part-Cost")));
		item.setPartStock(Integer.valueOf(request.getParameter("part-Stock")));
		item.setPartMemo(request.getParameter("part-Memo"));

		// insert DB query
		itemService.setPartsItem(item);
		model.addAttribute("reqresult", item.getPartName() + " is added");

		// Get DB List

		return showAdminParts1("1", model);/* adminparts.jsp */
	}

	/*
	 * /admin/reqpartsmodify 파츠 정보변경
	 */
	@RequestMapping(value = "/admin/reqpartsmodify", method = RequestMethod.POST)
	public String modifyAdminParts(PartsItem item, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		item.setPartId(Integer.valueOf(request.getParameter("part-Id")));
		item.setPartProjectCode(request.getParameter("part-Project-Code"));
		item.setPartName(request.getParameter("part-Name"));
		item.setPartDesc(request.getParameter("part-Desc"));
		item.setPartLocation(request.getParameter("part-Location"));
		item.setPartCost(Float.valueOf(request.getParameter("part-Cost")));
		item.setPartStock(Integer.valueOf(request.getParameter("part-Stock")));
		item.setPartMemo(request.getParameter("part-Memo"));

		// Change DB query
		itemService.changePartsItem(item);
		model.addAttribute("reqresult", item.getPartName() + "'s datas are changed");
		System.out.println("/admin/reqprojectmodify processed.. Req:" + item.getPartName());

		// Get DB List
		return showAdminParts1("1", model);/* adminparts.jsp */
	}

	/*
	 * /admin/reqprojectremove 프로젝트 아이템 제거
	 */
	@RequestMapping(value = "/admin/reqpartsremove", method = RequestMethod.POST)
	public String removeAdminParts(PartsItem item, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		item.setPartId(Integer.valueOf(request.getParameter("part-Id")));
		item.setPartName(request.getParameter("part-Name"));

		// Change DB query
		itemService.removePartsItem(item);
		model.addAttribute("reqresult", item.getPartName() + " is removed");
		System.out.println("/admin/reqprojectremove processed.. Req ID:" + item.getPartName());

		// Get DB List
		return showAdminParts1("1", model);/* adminparts.jsp */
	}

	/*
	 * 
	 * ================================= USER =================================
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
		int rowsPer1Page = 15;

		try {
			// 시작페이지 설정 1~5 페이지 일경우 1​​
			startPage = (Integer.parseInt(seq) - 1) / 5 * 5 + 1;
			// ex) 현재 6페이지 일경우 (6-1) /5 * 5 +1 = 1 -> 6 페이지 부터 시작​​

			endPage = startPage + 5 - 1;

			if (seq != null && seq != "") {
				if (!seq.equals("1")) {
					// 첫페이지가 아닐경우 그 페이지에 맞는 목록 뽑아옴​
					int temp = (Integer.parseInt(seq) - 1) * rowsPer1Page;
					page = temp;

				} else if (seq.equals("1")) {
					// 페이지 번호가 1이면 처음부터 15개​(rowsPer1Page)
					page = 0;
				}

			}
		} catch (Exception e) {
			// 이상한 페이지 번호 들어오면 해당 게시판 처음으로 리다이렉트​
			System.out.println("=== admin user , Page number error!! ===");
			return "redirect:/admin/user/1";
		}
		// 전체 게시물 갯수 뽑아옴 ​

		String rownum = itemService.getUserItemsRow();
		System.out.println("item rows : " + rownum);
		// pageNum 변수는 전체 페이지의 수​
		int pageNum = Integer.parseInt(rownum) / rowsPer1Page + 1;
		// 게시물이 딱 15개일 경우 다음페이지가 생기지 않게 -1 해줌​

		if (Integer.parseInt(rownum) % rowsPer1Page == 0) {
			pageNum--;
		}

		if (endPage > pageNum) {
			// 예를 들어 마지막페이지가 12페이지인 경우 endPage가 15페이지(rowsPer1Page) 까지 출력되기때문에
			// 12페이지로 바꿔줌​

			endPage = pageNum;

		}

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("start", startPage);
		model.addAttribute("end", endPage);
		///////////////////
		// Query DB List
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
		return showAdminUser("1", model); /* adminuser.jsp */
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
		return showAdminUser("1", model); /* adminuser.jsp */
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
		return showAdminUser("1", model); /* adminuser.jsp */
	}

}