/**
 * [Controller] 
 * 최초 웹브라우저에서 접근시 주소로 처리시작하는 부분
 * 
 * @author jaeyong1.park
 */
package com.lgit.stockmgmt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgit.stockmgmt.domain.EUserLevel;
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
		item.setPartMsllevel(request.getParameter("part-Msllevel"));

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
		item.setPartMsllevel(request.getParameter("part-Msllevel"));

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

		// add user operation log
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		itemService.addUserLog(userdata.getUserId(), ip, "관리자메뉴에서 신규회원 추가됨");

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

		// add user operation log
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		itemService.addUserLog(userdata.getUserId(), ip, "관리자메뉴에서 비밀번호 초기화됨");

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

		// add user operation log
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		itemService.addUserLog(userdata.getUserId(), ip, "관리자메뉴에서 회원정보 변경됨");

		// Get DB List
		return showAdminUser("0", model); /* adminuser.jsp */
	}

	/*
	 * /myinventorycontrol 출고담당자 재고관리메뉴(view는 재활용 가져감)
	 */
	@RequestMapping(value = "/myinventorycontrol", method = RequestMethod.GET)
	public String myListProcess3(Model model, HttpServletRequest request) {
		return myListProcess4("0", model, request);
	}

	@RequestMapping(value = "/myinventorycontrol/{seq}", method = RequestMethod.GET)
	public String myListProcess4(@PathVariable String seq, Model model, HttpServletRequest request) {
		myListProcess(seq, model, request);
		model.addAttribute("PageTitleInfoFromerver", "재고수량관리");
		model.addAttribute("requestedURL", "/myinventorycontrol");
		return "myinventorycontrol"; /* myinventorycontrol.jsp */
	}

	/*
	 * /mylist 내 자재정보
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
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}
		System.out.println(
				"[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] /mylist process. req pagenum:" + seq);

		final int rowsPer1Page = 15;

		/////////////////// List View
		List<JoinDBItem> items = null;
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			items = itemService.getMyItemsByOwnerName(loginUser.getUserName());
		} else if (loginUser.getUserLevel() == EUserLevel.Lv3_SHIPPER.getLevelInt()) {
			items = itemService.getShipperItemsByShipperName(loginUser.getUserName());
		} else {
			items = new ArrayList<JoinDBItem>();
		}

		// Choose current page data
		PagedListHolder<JoinDBItem> paging = new PagedListHolder<JoinDBItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		model.addAttribute("seq", seq);
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
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
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
		model.addAttribute("seq", seq);
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
	 * /myproject 개발담당자 - DB관리 - 프로젝트관리
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
			System.out.println("/myproject process. no session info.  ");
			return "login";
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}
		System.out.println("[" + loginUser.getUserId() + "] /myproject process");

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

	/*
	 * /myproject4ship 출고담당자 - DB관리 - 프로젝트관리
	 */
	@RequestMapping(value = "/myproject4ship", method = RequestMethod.GET)
	public String showMyProject4ship2(Model model, HttpServletRequest request) {
		return showMyProject4ship("0", model, request);
	}

	@RequestMapping(value = "/myproject4ship/{seq}", method = RequestMethod.GET)
	public String showMyProject4ship(@PathVariable String seq, Model model, HttpServletRequest request) {
		if (seq.equalsIgnoreCase("") || seq.equalsIgnoreCase("-1")) {
			seq = "0";
		}

		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/myproject4ship process. no session info.  ");
			return "login";
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}
		System.out.println("[" + loginUser.getUserId() + "] /myproject4ship process");

		final int rowsPer1Page = 15;

		// Get List
		// List<ProjectItem> items = itemService.getProjectItems();
		List<ProjectItem> items = itemService.getMyProjectItems4shipper(loginUser.getUserId());
		System.out.println("/myproject4ship process");

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

		return "myproject4ship"; /* myproject.jsp */
	}

	@RequestMapping(value = "/addmyproject", method = RequestMethod.POST)
	public String addMyProject(ProjectItem item, Model model, HttpServletRequest request) {
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/addmyproject process. no session info.  ");
			return "login";
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}
		System.out.println("[" + loginUser.getUserId() + "] /addmyproject process");

		// Get data from Webbrowser
		item.setProjectCode(request.getParameter("project-Code"));
		item.setProjectName(request.getParameter("project-Name"));
		item.setProjectOwnerId(request.getParameter("project-Owner-Id"));
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
		// return showMyProject1("0", model, request);
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			return showMyProject1("0", model, request);
		} else // lv3. lv6
		{
			return showMyProject4ship("0", model, request);
		}

	}

	/*
	 * /reqmyprojectmodify 프로젝트 수정
	 */
	@RequestMapping(value = "/reqmyprojectmodify", method = RequestMethod.POST)
	public String modifyMyProject(ProjectItem item, Model model, HttpServletRequest request) {
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/reqmyprojectmodify process. no session info.  ");
			return "login";
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}

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
		// return showMyProject1("0", model, request);
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			return showMyProject1("0", model, request);
		} else // lv3. lv6
		{
			return showMyProject4ship("0", model, request);
		}
	}

	/*
	 * /reqmyprojectremove 프로젝트 제거
	 * 
	 */
	@RequestMapping(value = "/reqmyprojectremove", method = RequestMethod.POST)
	public String removeMyProject(ProjectItem item, Model model, HttpServletRequest request) {
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/reqmyprojectremove process. no session info.  ");
			return "login";
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}

		// Get data from Webbrowser
		item.setProjectCode(request.getParameter("project-Code"));

		// Change DB query
		itemService.removeProjectItem(item);
		model.addAttribute("reqresult", item.getProjectCode() + " is removed");
		System.out.println("/reqmyprojectremove processed.. Req ID:" + item.getProjectCode());

		// Get DB List
		// return showMyProject1("0", model, request);
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			return showMyProject1("0", model, request);
		} else // lv3. lv6
		{
			return showMyProject4ship("0", model, request);
		}
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
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);

		}
		// Get data from Webbrowser
		item.setPartProjectCode(request.getParameter("part-Project-Code"));
		item.setPartName(request.getParameter("part-Name"));
		item.setPartDesc(request.getParameter("part-Desc"));
		item.setPartLocation(request.getParameter("part-Location"));
		item.setPartCost(Float.valueOf(request.getParameter("part-Cost")));
		item.setPartStock(Integer.valueOf(request.getParameter("part-Stock")));
		item.setPartMemo(request.getParameter("part-Memo"));
		item.setPartMsllevel(request.getParameter("part-Msllevel"));

		// isvalid projectcode
		List<ProjectItem> lstPrj;
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			lstPrj = itemService.getMyProjectItems(loginUser.getUserId());
		} else if ((loginUser.getUserLevel() == EUserLevel.Lv3_SHIPPER.getLevelInt())
				|| ((loginUser.getUserLevel() == EUserLevel.Lv6_SHIPPERADMIN.getLevelInt()))) {
			lstPrj = itemService.getMyProjectItems4shipper(loginUser.getUserId());
		} else {
			// dummy
			lstPrj = itemService.getMyProjectItems(loginUser.getUserId());
		}
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

		// return showMyParts1("0", model, request);
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			// lv2. dev
			return showMyParts1("0", model, request);
		} else // lv3. lv6 shipper
		{
			return showMyParts4ship("0", model, request);
		}

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
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}

		// SPECIAL CODE . for share web page.. fowarding..
		if ((loginUser.getUserLevel() == EUserLevel.Lv3_SHIPPER.getLevelInt())
				|| (loginUser.getUserLevel() == EUserLevel.Lv6_SHIPPERADMIN.getLevelInt())) {
			System.out.println("redirect to /myparts4ship");
			return showMyParts4ship(seq, model, request);
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
	 * /myparts4ship 출고담당자 - DB관린 - 파츠관리
	 */
	@RequestMapping(value = "/myparts4ship", method = RequestMethod.GET)
	public String showMyParts4ship2(Model model, HttpServletRequest request) {
		return showMyParts4ship("0", model, request);
	}

	@RequestMapping(value = "/myparts4ship/{seq}", method = RequestMethod.GET)
	public String showMyParts4ship(@PathVariable String seq, Model model, HttpServletRequest request) {
		if (seq.equalsIgnoreCase("") || seq.equalsIgnoreCase("-1")) {
			seq = "0";
		}
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/myparts4ship process. no session info. return login.jsp ");
			return "login";
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}
		System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName()
				+ "] /myparts4ship process. req pagenum:" + seq);

		final int rowsPer1Page = 15;

		/////////////////// List View
		List<PartsItem> items = itemService.getMyItemsByID4Shipper(loginUser.getUserId());
		System.out.println("/myparts4ship process");
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
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/reqmypartsmodify process. no session info. return login.jsp ");
			return "login";
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}

		// Get data from Webbrowser
		item.setPartId(Integer.valueOf(request.getParameter("part-Id")));
		item.setPartProjectCode(request.getParameter("part-Project-Code"));
		item.setPartName(request.getParameter("part-Name"));
		item.setPartDesc(request.getParameter("part-Desc"));
		item.setPartLocation(request.getParameter("part-Location"));
		item.setPartCost(Float.valueOf(request.getParameter("part-Cost")));
		item.setPartStock(Integer.valueOf(request.getParameter("part-Stock")));
		item.setPartMemo(request.getParameter("part-Memo"));
		item.setPartMsllevel(request.getParameter("part-Msllevel"));
		System.out.println("reqmypartsmodify msl:" + item.getPartMsllevel());

		// Change DB query
		itemService.changePartsItem(item);
		model.addAttribute("reqresult", item.getPartName() + "'s datas are changed");
		System.out.println("/reqmypartsmodify processed.. Req:" + item.getPartName());

		// Get DB List
		// return showMyParts1("0", model, request);
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			// lv2. dev
			return showMyParts1("0", model, request);
		} else // lv3. lv6 shipper
		{
			return showMyParts4ship("0", model, request);
		}
	}

	/*
	 * /reqmypartsremove 파츠 제거
	 */
	@RequestMapping(value = "/reqmypartsremove", method = RequestMethod.POST)
	public String removeMyParts(PartsItem item, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/reqmypartsremove process. no session info. return login.jsp ");
			return "login";
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}

		// Get data from Webbrowser
		item.setPartId(Integer.valueOf(request.getParameter("part-Id")));
		item.setPartName(request.getParameter("part-Name"));

		// Change DB query
		itemService.removePartsItem(item);
		model.addAttribute("reqresult", item.getPartName() + " is removed");
		System.out.println("/reqmypartsremove processed.. Req ID:" + item.getPartName());

		// Get DB List
		// return showMyParts1("0", model, request);
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			// lv2. dev
			return showMyParts1("0", model, request);
		} else // lv3. lv6 shipper
		{
			return showMyParts4ship("0", model, request);
		}
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
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}
		System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] /shipperlist process. ");

		///////////////////
		// Query DB List
		List<UserItem> items = itemService.getShipperUserItems();
		System.out.println("/shipperlist process");
		model.addAttribute("items", items);

		model.addAttribute("title", "가입되어있는 시작담당자(출고담당자) 리스트");
		return "popup-userlist"; /* popup-shipperlist.jsp */
	}

	/*
	 * /devuserlist 개발담당자리스트
	 */
	@RequestMapping(value = "/devuserlist", method = RequestMethod.GET)
	public String showDevuserUser(Model model, HttpServletRequest request) {

		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipperlist process. no session info. return login.jsp ");
			return "login";
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
		}
		System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] /devuserlist process. ");

		///////////////////
		// Query DB List
		List<UserItem> items = itemService.getDevUserItems();
		System.out.println("/devuserlist process");
		model.addAttribute("items", items);

		model.addAttribute("title", "가입되어있는 개발담당자 리스트");
		return "popup-userlist"; /* popup-shipperlist.jsp */
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
		} else {
			// update bagde counter(cart items)
			int cartItemsCounter1 = itemService.getShipPartsListItemsCounter1(loginUser.getUserId());
			int cartItemsCounter2 = itemService.getShipPartsListItemsCounter2(loginUser.getUserId());
			System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] cart items : "
					+ cartItemsCounter1 + "/ " + cartItemsCounter2);
			loginUser.setCartItems(cartItemsCounter1);
			loginUser.setCartItemsOthers(cartItemsCounter2);
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

	/*
	 * dbcheck 페이지
	 */
	@RequestMapping("/dbcheck")
	public String doDBcheck(Model model) {
		String err = "[DB 연결성 확인됨]<br>\n";
		String r = itemService.getPartsItemsRow();
		model.addAttribute("errormsg", err + "(" + r + ")");
		return "dbcheck";
	}
}
