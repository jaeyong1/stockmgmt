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
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.domain.JoinDBItem;
import com.lgit.stockmgmt.domain.PartsItem;
import com.lgit.stockmgmt.domain.ProjectItem;
import com.lgit.stockmgmt.domain.UserItem;
import com.lgit.stockmgmt.service.ItemService;

@Controller
public class WebController {
	/*
	 * Test Controller
	 */

	// @RequestMapping("/")
	// public String getIndex(Model model) { //
	// model.addAttribute("name", "John Lee"); System.out.println("GET Index2");
	// return "index"; //WebController.java를 참고해서 해당경로에서 파일 호출
	// }

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
		return showAdminProject1("0", model);
	}

	@RequestMapping(value = "/admin/project/{seq}", method = RequestMethod.GET)
	public String showAdminProject1(@PathVariable String seq, Model model) {
		if (seq.equalsIgnoreCase("") || seq.equalsIgnoreCase("-1")) {
			seq = "0";

		}
		final int rowsPer1Page = 15;

		// Get List
		List<ProjectItem> items = itemService.getProjectItems();
		System.out.println("/admin/project process");
		// model.addAttribute("items", items);

		// Choose current page data
		PagedListHolder<ProjectItem> paging = new PagedListHolder<ProjectItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		// System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		// 인덱스..

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
		return showAdminProject1("0", model);/* adminproj.jsp */
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
		return showAdminProject1("0", model); /* adminuser.jsp */
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
		return showAdminProject1("0", model); /* adminuser.jsp */
	}

	/*
	 * 
	 * ================================= PARTS =================================
	 * /adminparts Parts 관리 화면
	 */

	@RequestMapping(value = "/admin/parts", method = RequestMethod.GET)
	public String showAdminParts2(Model model) {
		return showAdminParts1("0", model);
	}

	@RequestMapping(value = "/admin/parts/{seq}", method = RequestMethod.GET)
	public String showAdminParts1(@PathVariable String seq, Model model) {
		if (seq.equalsIgnoreCase("") || seq.equalsIgnoreCase("-1")) {
			seq = "0";

		}
		final int rowsPer1Page = 15;

		/////////////////// List View
		List<PartsItem> items = itemService.getPartsItems();
		System.out.println("/adminparts process");
		// model.addAttribute("items", items);

		// Choose current page data
		PagedListHolder<PartsItem> paging = new PagedListHolder<PartsItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		// System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		// 인덱스..

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

		return showAdminParts1("0", model);/* adminparts.jsp */
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
		return showAdminParts1("0", model);/* adminparts.jsp */
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
		return showAdminParts1("0", model);/* adminparts.jsp */
	}

	/*
	 * 
	 * ================================= USER =================================
	 * /adminuser 사용자 List, 가입, 수정 관리화면
	 */
	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public String showAdminUser2(Model model) {
		return showAdminUser("0", model);
	}

	@RequestMapping(value = "/admin/user/{seq}", method = RequestMethod.GET)
	public String showAdminUser(@PathVariable String seq, Model model) {
		if (seq.equalsIgnoreCase("") || seq.equalsIgnoreCase("-1")) {
			seq = "0";

		}
		final int rowsPer1Page = 25;

		///////////////////
		// Query DB List
		List<UserItem> items = itemService.getUserItems();
		System.out.println("/adminuser process");
		// model.addAttribute("items", items);

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
		return showAdminUser("0", model); /* adminuser.jsp */
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
		return showAdminUser("0", model); /* adminuser.jsp */
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
		return showAdminUser("0", model); /* adminuser.jsp */
	}

	/*
	 * /mylist 내정보
	 */
	@RequestMapping(value = "/mylist", method = RequestMethod.GET)
	public String myListProcess2(Model model, HttpServletRequest request) {
		return myListProcess("0", model, request);
	}

	@RequestMapping(value = "/mylist/{seq}", method = RequestMethod.GET)
	public String myListProcess(@PathVariable String seq, Model model, HttpServletRequest request) {
		if (seq.equalsIgnoreCase("") || seq.equalsIgnoreCase("-1")) {
			seq = "0";
		}
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/mylist process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println(
				"[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] /mylist process. req pagenum:" + seq);

		final int rowsPer1Page = 15;

		/////////////////// List View
		List<JoinDBItem> items = itemService.getMyItemsByOwnerName(loginUser.getUserName());

		// Choose current page data
		PagedListHolder<JoinDBItem> paging = new PagedListHolder<JoinDBItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		// System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		// 인덱스..

		model.addAttribute("PageTitleInfoFromerver", "나의 재고 관리");
		model.addAttribute("PostPageUrl", "/reqshippartsadd");
		model.addAttribute("requestedURL", "/mylist");
		return "mylist";// mylist.jsp
	}

	/*
	 * /otherslist 파트너 재고정보
	 */
	@RequestMapping(value = "/otherslist", method = RequestMethod.GET)
	public String othersListProcess2(Model model, HttpServletRequest request) {
		return othersListProcess("0", model, request);
	}

	@RequestMapping(value = "/otherslist/{seq}", method = RequestMethod.GET)
	public String othersListProcess(@PathVariable String seq, Model model, HttpServletRequest request) {
		if (seq.equalsIgnoreCase("") || seq.equalsIgnoreCase("-1")) {
			seq = "0";
		}
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/otherslist process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName()
				+ "] /otherslist process. req pagenum:" + seq);

		final int rowsPer1Page = 15;

		/////////////////// List View
		List<JoinDBItem> items = itemService.getOthersItemsByOwnerName(loginUser.getUserName());

		// Choose current page data
		PagedListHolder<JoinDBItem> paging = new PagedListHolder<JoinDBItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		// System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		// 인덱스..

		model.addAttribute("PageTitleInfoFromerver", "파트너 재고 관리");
		model.addAttribute("PostPageUrl", "/reqothersshippartsadd");
		model.addAttribute("requestedURL", "/otherslist");
		return "mylist";// mylist.jsp
	}

	/*
	 * 
	 * ============================== 사용자 프로젝트 관리 ==============================
	 */

	/*
	 * /addmyproject 프로젝트 생성
	 */
	@RequestMapping(value = "/myproject", method = RequestMethod.GET)
	public String showMyProject2(Model model, HttpServletRequest request) {
		return showMyProject1("0", model, request);
	}

	@RequestMapping(value = "/myproject/{seq}", method = RequestMethod.GET)
	public String showMyProject1(@PathVariable String seq, Model model, HttpServletRequest request) {
		if (seq.equalsIgnoreCase("") || seq.equalsIgnoreCase("-1")) {
			seq = "0";
		}

		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqlist process. no session info.  ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqlist process");

		final int rowsPer1Page = 15;

		// Get List
		// List<ProjectItem> items = itemService.getProjectItems();
		List<ProjectItem> items = itemService.getMyProjectItems(loginUser.getUserId());
		System.out.println("/myproject process");

		// Choose current page data
		PagedListHolder<ProjectItem> paging = new PagedListHolder<ProjectItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		// System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		// 인덱스..

		return "myproject"; /* myproject.jsp */
	}

	@RequestMapping(value = "/addmyproject", method = RequestMethod.POST)
	public String addMyProject(ProjectItem item, Model model, HttpServletRequest request) {
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/addmyproject process. no session info.  ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /addmyproject process");

		// Get data from Webbrowser
		item.setProjectCode(request.getParameter("project-Code"));
		item.setProjectName(request.getParameter("project-Name"));
		item.setProjectOwnerId(loginUser.getUserId());
		item.setProjectShipperId(request.getParameter("project-shipper-Id"));
		System.out.println("[" + loginUser.getUserId() + "]" + item.toString());

		// isvalid shipper id?
		String shipperId = itemService.getUserNamebyID(item.getProjectShipperId());
		if (shipperId == null) {
			// not valid shipper id
			System.out.println("not valid shipper id" + item.getProjectShipperId());
			model.addAttribute("reqresult", "[항목추가실패]<br>" + item.getProjectShipperId() + "는 유효하지않은 출고담당자 ID입니다.");
		} else {
			// insert DB query
			itemService.setProjectItem(item);
			model.addAttribute("reqresult", item.getProjectCode() + " is added");
		}
		// Get DB List
		return showMyProject1("0", model, request);
	}

	/*
	 * /reqmyprojectmodify 프로젝트 수정
	 */
	@RequestMapping(value = "/reqmyprojectmodify", method = RequestMethod.POST)
	public String modifyMyProject(ProjectItem item, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		item.setProjectCode(request.getParameter("project-Code"));
		item.setProjectName(request.getParameter("project-Name"));
		item.setProjectOwnerId(request.getParameter("project-Owner-Id"));
		item.setProjectShipperId(request.getParameter("project-shipper-Id"));

		// isvalid shipper id?
		String shipperId = itemService.getUserNamebyID(item.getProjectShipperId());
		if (shipperId == null) {
			// not valid shipper id
			System.out.println("not valid shipper id" + item.getProjectShipperId());
			model.addAttribute("reqresult", "[항목수정실패]<br>" + item.getProjectShipperId() + "는 유효하지않은 출고담당자 ID입니다.");
		} else {
			// Change DB query
			itemService.changeProjectItem(item);
			model.addAttribute("reqresult", item.getProjectCode() + "'s datas are changed");
		}

		System.out.println("/reqmyprojectmodify processed.. Req ID:" + item.getProjectCode());

		// Get DB List
		return showMyProject1("0", model, request);
	}

	/*
	 * /reqmyprojectremove 프로젝트 제거
	 * 
	 */
	@RequestMapping(value = "/reqmyprojectremove", method = RequestMethod.POST)
	public String removeMyProject(ProjectItem item, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		item.setProjectCode(request.getParameter("project-Code"));

		// Change DB query
		itemService.removeProjectItem(item);
		model.addAttribute("reqresult", item.getProjectCode() + " is removed");
		System.out.println("/reqmyprojectremove processed.. Req ID:" + item.getProjectCode());

		// Get DB List
		return showMyProject1("0", model, request);
	}

	// =================================================

	/*
	 * /addmyparts 파츠 추가
	 */
	@RequestMapping(value = "/addmyparts", method = RequestMethod.POST)
	public String addMyParts(PartsItem item, Model model, HttpServletRequest request) {
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/addmyproject process. no session info.  ");
			return "login";
		}
		// Get data from Webbrowser
		item.setPartProjectCode(request.getParameter("part-Project-Code"));
		item.setPartName(request.getParameter("part-Name"));
		item.setPartDesc(request.getParameter("part-Desc"));
		item.setPartLocation(request.getParameter("part-Location"));
		item.setPartCost(Float.valueOf(request.getParameter("part-Cost")));
		item.setPartStock(Integer.valueOf(request.getParameter("part-Stock")));
		item.setPartMemo(request.getParameter("part-Memo"));

		// isvalid projectcode
		List<ProjectItem> lstPrj = itemService.getMyProjectItems(loginUser.getUserId());
		boolean prjFinded = false;
		for (ProjectItem prj : lstPrj) {
			if (prj.getProjectCode().equals(item.getPartProjectCode())) {
				prjFinded = true;
			}
		}

		if (prjFinded) {
			// insert DB query
			itemService.setPartsItem(item);
			model.addAttribute("reqresult", item.getPartName() + " is added");

		} else {
			model.addAttribute("reqresult", "[항목생성실패] " + item.getPartProjectCode() + "는 나의 프로젝트 Code가 아닙니다.");
		}

		return showMyParts1("0", model, request);
	}

	/*
	 * /myparts 파츠 조회
	 */
	@RequestMapping(value = "/myparts", method = RequestMethod.GET)
	public String showMyParts2(Model model, HttpServletRequest request) {
		return showMyParts1("0", model, request);
	}

	@RequestMapping(value = "/myparts/{seq}", method = RequestMethod.GET)
	public String showMyParts1(@PathVariable String seq, Model model, HttpServletRequest request) {
		if (seq.equalsIgnoreCase("") || seq.equalsIgnoreCase("-1")) {
			seq = "0";
		}
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/myparts process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println(
				"[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] /myparts process. req pagenum:" + seq);

		final int rowsPer1Page = 15;

		/////////////////// List View
		List<PartsItem> items = itemService.getMyItemsByID(loginUser.getUserId());
		System.out.println("/adminparts process");
		// model.addAttribute("items", items);

		// Choose current page data
		PagedListHolder<PartsItem> paging = new PagedListHolder<PartsItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		// System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		// 인덱스..

		return "myparts"; /* adminparts.jsp */
	}

	/*
	 * /reqmypartsmodify 파츠 수정
	 */
	@RequestMapping(value = "/reqmypartsmodify", method = RequestMethod.POST)
	public String modifyMyParts(PartsItem item, Model model, HttpServletRequest request) {
	
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
		System.out.println("/reqmypartsmodify processed.. Req:" + item.getPartName());

		// Get DB List
		return showMyParts1("0", model, request);
	}

	/*
	 * /reqmypartsremove 파츠 제거
	 */
	@RequestMapping(value = "/reqmypartsremove", method = RequestMethod.POST)
	public String removeMyParts(PartsItem item, Model model, HttpServletRequest request) {
		// Get data from Webbrowser
		item.setPartId(Integer.valueOf(request.getParameter("part-Id")));
		item.setPartName(request.getParameter("part-Name"));

		// Change DB query
		itemService.removePartsItem(item);
		model.addAttribute("reqresult", item.getPartName() + " is removed");
		System.out.println("/admin/reqprojectremove processed.. Req ID:" + item.getPartName());

		// Get DB List
		return showMyParts1("0", model, request);
	}

	/*
	 * /shipperlist 출고담당자리스트
	 */
	@RequestMapping(value = "/shipperlist", method = RequestMethod.GET)
	public String showShipperUser(Model model, HttpServletRequest request) {

		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipperlist process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] /shipperlist process. ");

		///////////////////
		// Query DB List
		List<UserItem> items = itemService.getShipperUserItems();
		System.out.println("/shipperlist process");
		model.addAttribute("items", items);

		return "popup-shipperlist"; /* popup-shipperlist.jsp */
	}

	/*
	 * /helppage 도움말 페이지
	 */
	@RequestMapping(value = "/helppage", method = RequestMethod.GET)
	public String showHelpPage(Model model, HttpServletRequest request) {

		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/helppage process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] /shipperlist process. ");

		return "helppage"; /* helppage.jsp */
	}

	/*
	 * index 페이지
	 */
	@RequestMapping("/")
	public String getIndex(Model model) {
		return "redirect:mylist";
	}

}
