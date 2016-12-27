package com.lgit.stockmgmt.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgit.stockmgmt.domain.EShipState;
import com.lgit.stockmgmt.domain.EUserLevel;
import com.lgit.stockmgmt.domain.ShipReqItem;
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
		System.out.println("[" + loginUser.getUserId() + "] /shipparts process");
		model.addAttribute("items", items);

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
	 * /reqshippartsmodify_others 타인 장바구니 - 값수정
	 * 
	 */
	@RequestMapping(value = "/reqshippartsmodify_others", method = RequestMethod.POST)
	public String modifyShipPartsOthers(ShipReqPartsItem shippartsdata, Model model, HttpServletRequest request) {
		// Get data from Web browser
		shippartsdata.setItemlistId(Integer.valueOf(request.getParameter("itemlist-Id")));
		shippartsdata.setItemlistAmount(Integer.valueOf(request.getParameter("itemlist-Amount")));

		// Change DB query
		itemService.changeShipPartsItem(shippartsdata);
		model.addAttribute("reqresult", shippartsdata.getItemlistId() + "'s data is changed");
		System.out.println("/reqshippartsmodify_others processed.. Req ID:" + shippartsdata.getItemlistId());

		// Get DB List
		return showShippartsOthers("0", model, request); /* adminuser.jsp */
	}

	/*
	 * /reqshippartsremove_others 타인 장바구니 - 아이템삭제
	 */

	@RequestMapping(value = "/reqshippartsremove_others", method = RequestMethod.POST)
	public String removeShipPartsOthers(ShipReqPartsItem shippartsdata, Model model, HttpServletRequest request) {
		// Get data from Web browser
		shippartsdata.setItemlistId(Integer.valueOf(request.getParameter("itemlist-Id")));

		// Change DB query
		itemService.removeShipPartsItem(shippartsdata);
		model.addAttribute("reqresult", shippartsdata.getItemlistId() + " is removed");
		System.out.println("/reqshippartsremove processed.. Req ID:" + shippartsdata.getItemlistId());

		// Get DB List
		return showShippartsOthers("0", model, request); /* adminuser.jsp */
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

	// == 파트너 출고부품 추가
	/*
	 * /reqothersshippartsadd
	 */
	@RequestMapping(value = "/reqothersshippartsadd", method = RequestMethod.POST)
	public String addShipPartsOthers(ShipReqPartsItem shippartsdata, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/reqothersshippartsadd process. no session info. return login.jsp ");
			return "login";
		}
		System.out
				.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName() + "] /reqshippartsadd process. ");

		// Get data from Web browser
		shippartsdata.setUserId(loginUser.getUserId());
		shippartsdata.setItemlistShipId(-2); // -2 은 아직 장바구니안에 아직대기. 파트너용 부품
		shippartsdata.setItemlistPartId(Integer.valueOf(request.getParameter("part-Id")));
		shippartsdata.setItemlistAmount(Integer.valueOf(request.getParameter("reqnum[]")));

		// 해당 PartsId를 카트에담을수 있는지
		boolean isValidItem = itemService.isVaildOthersCartItem(Integer.valueOf(request.getParameter("part-Id")),
				loginUser.getUserId());

		if (!isValidItem) {
			model.addAttribute("reqresult",
					"[담기실패]<br> 현재 출고요청 준비중인 아이템과 같은 개발담당자의 아이템만 담을 수 있습니다.<br> 현재 담겨있는 Item을 출고요청 후에 다른 개발담당자의 아이템을 담아주세요.<br>");
			return showShippartsOthers("0", model, request);
		}

		// Change DB query
		itemService.addShipPartsItem(shippartsdata);
		model.addAttribute("reqresult", "(" + shippartsdata.getItemlistPartId() + ")아이템 담기완료");
		System.out.println("/reqothersshippartsadd processed.. Req ID:" + shippartsdata.getItemlistId() + " reqNum:"
				+ shippartsdata.getItemlistAmount());

		// Change DB query
		// itemService.removeShipPartsItem(shippartsdata);
		// model.addAttribute("reqresult", shippartsdata.getItemlistId() + " is
		// removed");
		System.out.println("/reqothersshippartsadd processed.. "); // Req ID:" +
																	// shippartsdata.getItemlistId());

		// Get DB List
		return showShippartsOthers("0", model, request); /* adminuser.jsp */

		/// test
	}

	/*
	 * /shipparts 파트너출고요청 부품리스트(장바구니)
	 */
	@RequestMapping(value = "/shipothersparts", method = RequestMethod.GET)
	public String showShippartsOthers2(Model model, HttpServletRequest request) {
		return showShippartsOthers("0", model, request);
	}

	@RequestMapping(value = "/shipothersparts/{seq}", method = RequestMethod.GET)
	public String showShippartsOthers(@PathVariable String seq, Model model, HttpServletRequest request) {
		if (seq.equalsIgnoreCase("")) {
			seq = "0";
		}
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipothersparts process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "/" + loginUser.getUserName()
				+ "] /shipothersparts process. req pagenum:" + seq);

		final int rowsPer1Page = 15;

		///////////////////
		// Query DB List
		List<ShipReqPartsItem> items = itemService.getShipPartsListItems(-2, loginUser.getUserId());
		System.out.println("[" + loginUser.getUserId() + "] /shipparts process");
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

		return "othersreqpartlist"; /* myreqpartlist.jsp */
	}

	/*
	 * 
	 * ================================= 출고 관련 =================================
	 * /shipreq 출고 요청서 작성
	 */
	@RequestMapping(value = "/shipreq", method = RequestMethod.GET)
	public String showShipReq(Model model, HttpServletRequest request) {

		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreq process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreq process");

		///////////////////
		// Get Parts List (Query DB List)
		showShipparts("0", model, request);

		//////////////////
		// Default info
		ShipReqItem iam = new ShipReqItem();

		iam.setShipId(-1); // new item
		iam.setShipRequestorId(loginUser.getUserId());

		iam.setShipDestination("");

		LocalDateTime ldt = LocalDateTime.now(); // today
		iam.setShipToday(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

		// +3일
		LocalDateTime time4 = LocalDateTime.now().plusDays(3);
		iam.setShipTargetdate(time4.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

		// 대표프로젝트 선택
		String firstItemPrjCode = "";
		List<ShipReqPartsItem> list = itemService.getShipPartsListItems(-1, loginUser.getUserId());
		if (list.size() > 0) {
			firstItemPrjCode = list.get(0).getPartProjectCode();
		}
		iam.setShipProjectCode(firstItemPrjCode);
		iam.setShipMemo("");
		iam.setShipStateId(EShipState.STATE1_ADDCART.getStateInt());

		iam.setShipIsmyproject(1); // 내 자산이면 1로 .. 남의자산이면 0으로
		iam.setShipCoworkerUserid("");

		model.addAttribute("reqshipinfo", iam);

		// more web info
		String userName = itemService.getUserNamebyID(loginUser.getUserId());
		String userTeamName = itemService.getTeamNamebyID(loginUser.getUserId());
		// System.out.println("id:" + loginUser.getUserId() + " name:" +
		// userName + " teamname:" + userTeamName);
		model.addAttribute("dbUserName", userName);
		model.addAttribute("dbUserTeamName", userTeamName);
		model.addAttribute("shipstatekor", EShipState.STATE1_ADDCART.getStateKor());

		return "shipreq";

	}

	/*
	 * /shipothersreq 타인자재 출고요청서 작성
	 */

	@RequestMapping(value = "/shipothersreq", method = RequestMethod.GET)
	public String showShipReq_Others(Model model, HttpServletRequest request) {

		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipothersreq process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipothersreq process");

		///////////////////
		// Get Parts List (Query DB List)
		// showShipparts("0", model, request); //내재고
		showShippartsOthers("0", model, request);

		//////////////////
		// Default info
		ShipReqItem iam = new ShipReqItem();

		iam.setShipId(-1); // new item
		iam.setShipRequestorId(loginUser.getUserId());

		iam.setShipDestination("");

		LocalDateTime ldt = LocalDateTime.now(); // today
		iam.setShipToday(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

		// +3일
		LocalDateTime time4 = LocalDateTime.now().plusDays(3);
		iam.setShipTargetdate(time4.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

		// 대표프로젝트 선택
		String firstItemPrjCode = "";
		List<ShipReqPartsItem> list = itemService.getShipPartsListItems(-2, loginUser.getUserId());
		if (list.size() > 0) {
			firstItemPrjCode = list.get(0).getPartProjectCode();
		}
		iam.setShipProjectCode(firstItemPrjCode);
		iam.setShipMemo("");
		iam.setShipStateId(EShipState.STATE1_ADDCART.getStateInt());

		iam.setShipIsmyproject(0); // 내 자산이면 1로 .. 남의자산이면 0으로

		// 자산협의자
		String coworkerId = "";
		List<UserItem> list2 = itemService.getUserItemInOthersCart(loginUser.getUserId());
		if (list2.size() > 0) {
			coworkerId = list2.get(0).getUserId();
		}
		iam.setShipCoworkerUserid(coworkerId);

		model.addAttribute("reqshipinfo", iam);

		// more web info
		String userName = itemService.getUserNamebyID(loginUser.getUserId());
		String userTeamName = itemService.getTeamNamebyID(loginUser.getUserId());
		// System.out.println("id:" + loginUser.getUserId() + " name:" +
		// userName + " teamname:" + userTeamName);
		model.addAttribute("dbUserName", userName);
		model.addAttribute("dbUserTeamName", userTeamName);
		model.addAttribute("shipstatekor", EShipState.STATE1_ADDCART.getStateKor());

		return "shipothersreq";

	}

	/*
	 * /shipreqlist 요청서 리스트 뷰
	 */

	@RequestMapping(value = "/shipreqlist", method = RequestMethod.GET)
	public String getShipReqItems2(Model model, HttpServletRequest request) {
		return getShipReqItems("0", model, request);
	}

	@RequestMapping(value = "/shipreqlist/{seq}", method = RequestMethod.GET)
	public String getShipReqItems(@PathVariable String seq, Model model, HttpServletRequest request) {

		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqlist process. no session info.  ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqlist process");
		final int rowsPer1Page = 15;

		///////////////////
		// Query DB List
		List<ShipReqItem> items;
		if (loginUser.getUserLevel() == EUserLevel.Lv3_SHIPPER.getLevelInt()) {
			System.out.println("query for 출고담당자");
			items = itemService.getShipReqItemsForShipper(loginUser.getUserId());
		} else {
			items = itemService.getShipReqItems(loginUser.getUserId());
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqlist process");
		model.addAttribute("items", items);

		// Choose current page data
		PagedListHolder<ShipReqItem> paging = new PagedListHolder<ShipReqItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		// System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		// 인덱스..

		// jsp에서 직접 한글화
		List<String> eshipstate = EShipState.getKorList();
		model.addAttribute("eshipstate", eshipstate);

		return "shipreqlist"; /* myreqpartlist.jsp */
	}

	/*
	 * 출고요청 리스트. 개발자&출고자 공용
	 */
	@RequestMapping(value = "/viewshipreq", method = RequestMethod.POST)
	public String viewShipReqItems(Model model, HttpServletRequest request) {

		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqlist process. no session info.  ");
			return "login";
		}

		int reqshipid = Integer.valueOf(request.getParameter("ship-Id"));

		///////////////////
		// Query DB List
		List<ShipReqItem> main_items = itemService.getShipReqItems(reqshipid);
		model.addAttribute("reqshipinfo", main_items.get(0));
		// more web info
		String userName = itemService.getUserNamebyID(main_items.get(0).getShipRequestorId());
		String userTeamName = itemService.getTeamNamebyID(main_items.get(0).getShipRequestorId());
		model.addAttribute("dbUserName", userName);
		model.addAttribute("dbUserTeamName", userTeamName);
		model.addAttribute("shipstatekor", EShipState.getShipStateKor(main_items.get(0).getShipStateId()));

		///////////////////
		// Query DB List
		List<ShipReqPartsItem> items = itemService.getShipPartsListItems(reqshipid,
				main_items.get(0).getShipRequestorId());
		System.out.println("[" + loginUser.getUserId() + "] /shipparts process");
		model.addAttribute("items", items);

		return "viewshipreq";

	}

	/*
	 * /shipreqprocess/state2 파트너 협의요청
	 */
	@RequestMapping(value = "/shipreqprocess/state2", method = RequestMethod.POST)
	public String processPartenerConfirmReq(ShipReqItem shipreqdata, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqprocess/state2 process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state2 process");

		// check state
		int curstate = Integer.valueOf(request.getParameter("ship-StateId"));

		// 이전 상태에 따른 분기
		if (curstate == EShipState.STATE1_ADDCART.getStateInt()) {
			// Get data from Web browser
			shipreqdata.setShipId(Integer.valueOf(request.getParameter("ship-Id")));
			shipreqdata.setShipRequestorId(request.getParameter("ship-RequestorId"));
			shipreqdata.setShipDestination(request.getParameter("ship-Destination"));
			shipreqdata.setShipToday(request.getParameter("ship-Today"));
			shipreqdata.setShipTargetdate(request.getParameter("ship-Targetdate"));
			shipreqdata.setShipProjectCode(request.getParameter("ship-ProjectCode"));
			shipreqdata.setShipMemo(request.getParameter("ship-Memo"));
			shipreqdata.setShipIsmyproject(Integer.valueOf(request.getParameter("ship-Ismyproject")));
			shipreqdata.setShipStateId(EShipState.STATE2_REQCOWORKSHIPPING.getStateInt()); // move_to_state_2
			shipreqdata.setShipCoworkerUserid(request.getParameter("ship-CoworkerUserid"));
			shipreqdata.setShipReqDeliveryMethod(request.getParameter("ship-ReqDeliveryMethod"));
			shipreqdata.setShipDeliveredDateMethod(request.getParameter("ship-DeliveredDateMethod"));
			shipreqdata.setShipRejectCause(request.getParameter("ship-RejectCause"));

			// Change DB query
			itemService.stateMove1to2(shipreqdata, shipreqdata.getShipRequestorId());
			model.addAttribute("reqresult", shipreqdata.getShipId() + "'s data is added");
			System.out.println("/shipreqprocess/state2 processed..");

			// Get DB List
			return "redirect:../shipreqlist";
		}

		// State ERROR
		System.out.println("[" + loginUser.getUserId() + "]reqstate is not 1(요청서작성중)  !!");

		return "shipreqlist";
	}

	/*
	 * /shipreqprocess/state3 요청서 작성중 처리
	 */
	@RequestMapping(value = "/shipreqprocess/state3", method = RequestMethod.POST)
	public String processShipReq(ShipReqItem shipreqdata, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqprocess/state3 process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state3 process");

		// check state
		int curstate = Integer.valueOf(request.getParameter("ship-StateId"));

		// 이전 상태에 따른 분기
		if (curstate == EShipState.STATE1_ADDCART.getStateInt()) {
			// Get data from Web browser
			shipreqdata.setShipId(Integer.valueOf(request.getParameter("ship-Id")));
			shipreqdata.setShipRequestorId(request.getParameter("ship-RequestorId"));
			shipreqdata.setShipDestination(request.getParameter("ship-Destination"));
			shipreqdata.setShipToday(request.getParameter("ship-Today"));
			shipreqdata.setShipTargetdate(request.getParameter("ship-Targetdate"));
			shipreqdata.setShipProjectCode(request.getParameter("ship-ProjectCode"));
			shipreqdata.setShipMemo(request.getParameter("ship-Memo"));
			shipreqdata.setShipIsmyproject(Integer.valueOf(request.getParameter("ship-Ismyproject")));
			shipreqdata.setShipStateId(EShipState.STATE3_REQSHIPPING.getStateInt()); // move_to_state_3
			shipreqdata.setShipCoworkerUserid(request.getParameter("ship-CoworkerUserid"));
			shipreqdata.setShipReqDeliveryMethod(request.getParameter("ship-ReqDeliveryMethod"));
			shipreqdata.setShipDeliveredDateMethod(request.getParameter("ship-DeliveredDateMethod"));
			shipreqdata.setShipRejectCause(request.getParameter("ship-RejectCause"));

			// Change DB query
			itemService.stateMove1to3(shipreqdata, shipreqdata.getShipRequestorId());
			model.addAttribute("reqresult", shipreqdata.getShipId() + "'s data is added");
			System.out.println("/shipreqprocess/state3 processed..");

			// Get DB List
			return "redirect:../shipreqlist";
		} else if (curstate == EShipState.STATE2_REQCOWORKSHIPPING.getStateInt()) {
			// Get data from Web browser
			shipreqdata.setShipId(Integer.valueOf(request.getParameter("ship-Id")));
			shipreqdata.setShipStateId(EShipState.STATE3_REQSHIPPING.getStateInt()); // move_to_3
			shipreqdata.setShipRejectCause(request.getParameter("ship-RejectCause"));
			shipreqdata.setShipDeliveredDateMethod(request.getParameter("ship-DeliveredDateMethod"));			

			// Change DB query
			itemService.stateMove2to3(shipreqdata.getShipId(), shipreqdata.getShipStateId(),
					shipreqdata.getShipRejectCause(), shipreqdata.getShipDeliveredDateMethod());
			System.out.println("[" + loginUser.getUserId()
					+ "] /shipreqprocess/state3 (from state2, coworker confirmed)processed..");

			// Get DB List
			return "redirect:../myconfirmshipreqlist";
		}

		// State ERROR
		System.out.println("[" + loginUser.getUserId() + "]reqstate is not 1(요청서작성중) or 2(합의요청중) !!");

		return "shipreqlist";
	}

	/*
	 * /shipreqprocess/state4 요청서 작성중 처리
	 */
	@RequestMapping(value = "/shipreqprocess/state4", method = RequestMethod.POST)
	public String processShipReq4(ShipReqItem shipreqdata, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqprocess/state4 process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state4 process");

		// check state
		int curstate = Integer.valueOf(request.getParameter("ship-StateId"));

		// 이전 상태에 따른 분기
		if (curstate == EShipState.STATE3_REQSHIPPING.getStateInt()) {
			// Get data from Web browser
			shipreqdata.setShipId(Integer.valueOf(request.getParameter("ship-Id")));
			shipreqdata.setShipStateId(EShipState.STATE4_LISTPRINTED.getStateInt()); // move_to_4
			shipreqdata.setShipRejectCause(request.getParameter("ship-RejectCause"));
			shipreqdata.setShipDeliveredDateMethod(request.getParameter("ship-DeliveredDateMethod"));	

			// Change DB query
			itemService.stateMove3to4(shipreqdata.getShipId(), shipreqdata.getShipStateId(), shipreqdata.getShipRejectCause(), shipreqdata.getShipDeliveredDateMethod());
			// model.addAttribute("reqresult", shipreqdata.getShipId() + "'s
			// data is added");
			System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state4 processed..");

			// Get DB List
			return "redirect:../shipreqlist";
		}

		// State ERROR
		System.out.println("[" + loginUser.getUserId() + "]reqstate is not 3(출고접수중)!!");
		return "shipreq";

	}

	/*
	 * /shipreqprocess/state5 요청서 작성중 처리
	 */
	@RequestMapping(value = "/shipreqprocess/state5", method = RequestMethod.POST)
	public String processShipReq5(ShipReqItem shipreqdata, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqprocess/state5 process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state5 process");

		// check state
		int curstate = Integer.valueOf(request.getParameter("ship-StateId"));

		// 이전 상태에 따른 분기
		if (curstate == EShipState.STATE4_LISTPRINTED.getStateInt()) {
			// Get data from Web browser
			shipreqdata.setShipId(Integer.valueOf(request.getParameter("ship-Id")));
			shipreqdata.setShipStateId(EShipState.STATE5_SHIPPINGFINISHED.getStateInt()); // move_to_5
			shipreqdata.setShipRejectCause(request.getParameter("ship-RejectCause"));
			shipreqdata.setShipDeliveredDateMethod(request.getParameter("ship-DeliveredDateMethod"));

			// Change DB query
			itemService.stateMove4to5(shipreqdata.getShipId(), shipreqdata.getShipStateId(), shipreqdata.getShipRejectCause(), shipreqdata.getShipDeliveredDateMethod());
			System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state5 processed..");

			// Get DB List
			return "redirect:../shipreqlist";
		}

		// State ERROR
		System.out.println("[" + loginUser.getUserId() + "]reqstate is not 4(출고접수완료)!!");
		return "shipreq";

	}

	/*
	 * /shipreqprocess/state6 요청서 작성중 처리
	 */
	@RequestMapping(value = "/shipreqprocess/state6", method = RequestMethod.POST)
	public String processShipReq6(ShipReqItem shipreqdata, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqprocess/state6 reject process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state6 process");

		// check state
		int curstate = Integer.valueOf(request.getParameter("ship-StateId"));

		// 이전 상태에 따른 분기
		if ((curstate == EShipState.STATE4_LISTPRINTED.getStateInt())) {
			// Get data from Web browser
			shipreqdata.setShipId(Integer.valueOf(request.getParameter("ship-Id")));
			shipreqdata.setShipStateId(EShipState.STATE6_REJSHIPPING.getStateInt()); // move_to_6
			shipreqdata.setShipRejectCause(request.getParameter("ship-RejectCause"));
			shipreqdata.setShipDeliveredDateMethod(request.getParameter("ship-DeliveredDateMethod"));	

			// Change DB query
			itemService.stateMove4to6(shipreqdata.getShipId(), shipreqdata.getShipStateId(), shipreqdata.getShipRejectCause(), shipreqdata.getShipDeliveredDateMethod());
			System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state6 processed..");

			// Get DB List
			return "redirect:../shipreqlist";
		} else if (curstate == EShipState.STATE2_REQCOWORKSHIPPING.getStateInt()) {
			// Get data from Web browser
			shipreqdata.setShipId(Integer.valueOf(request.getParameter("ship-Id")));
			shipreqdata.setShipStateId(EShipState.STATE6_REJSHIPPING.getStateInt()); // move_to_6
			shipreqdata.setShipRejectCause(request.getParameter("ship-RejectCause"));
			shipreqdata.setShipDeliveredDateMethod(request.getParameter("ship-DeliveredDateMethod"));
			
			// Change DB query
			itemService.stateMove4to6(shipreqdata.getShipId(), shipreqdata.getShipStateId(),shipreqdata.getShipRejectCause(), shipreqdata.getShipDeliveredDateMethod()); // sql_reuse
			System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state6 processed..");

			// Get DB List
			return "redirect:../myconfirmshipreqlist";

		} else if (curstate == EShipState.STATE3_REQSHIPPING.getStateInt()) {
			// self rej
			// Get data from Web browser
			shipreqdata.setShipId(Integer.valueOf(request.getParameter("ship-Id")));
			shipreqdata.setShipStateId(EShipState.STATE6_REJSHIPPING.getStateInt()); // move_to_6

			// Change DB query
			itemService.stateMove4to6(shipreqdata.getShipId(), shipreqdata.getShipStateId(),shipreqdata.getShipRejectCause(), shipreqdata.getShipDeliveredDateMethod()); // sql_reuse
			System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state6 processed..");

			// Get DB List
			return "redirect:../shipreqlist";
		}

		// State ERROR
		System.out.println("[" + loginUser.getUserId() + "]reqstate is not 4(출고접수완료) or 2(협의출고요청)!!");
		return "shipreq";

	}

	/*
	 * /shipreqlist 요청서 리스트 뷰
	 */

	@RequestMapping(value = "/myconfirmshipreqlist", method = RequestMethod.GET)
	public String getMyConfirmShipReqItems2(Model model, HttpServletRequest request) {
		return getMyConfirmShipReqItems("0", model, request);
	}

	@RequestMapping(value = "/myconfirmshipreqlist/{seq}", method = RequestMethod.GET)
	public String getMyConfirmShipReqItems(@PathVariable String seq, Model model, HttpServletRequest request) {

		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqlist process. no session info.  ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqlist process");
		final int rowsPer1Page = 15;

		///////////////////
		// Query DB List
		List<ShipReqItem> items;
		items = itemService.getMyConfirmShipReqItems(loginUser.getUserId());

		System.out.println("[" + loginUser.getUserId() + "] /myconfirmshipreqlist process");
		model.addAttribute("items", items);

		// Choose current page data
		PagedListHolder<ShipReqItem> paging = new PagedListHolder<ShipReqItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		// System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		// 인덱스..

		// jsp에서 직접 한글화
		List<String> eshipstate = EShipState.getKorList();
		model.addAttribute("eshipstate", eshipstate);

		return "shipreqlist"; /* myreqpartlist.jsp */
	}

	@RequestMapping(value = "/shipparts_removeall", method = RequestMethod.POST)
	public String doMyCartRemoveAll(Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipparts_removeall process. no session info.  ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipparts_removeall process");

		///////////////////
		// Query DB List
		itemService.deleteAllMyCartItems(loginUser.getUserId(), -1);
		System.out.println("[" + loginUser.getUserId() + "] /shipparts_removeall process");

		// Get DB List
		return "redirect:/shipparts";

	}

	@RequestMapping(value = "/shipothersparts_removeall", method = RequestMethod.POST)
	public String doOthersCartRemoveAll(Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipothersparts_removeall process. no session info.  ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipothersparts_removeall process");

		///////////////////
		// Query DB List
		itemService.deleteAllMyCartItems(loginUser.getUserId(), -2);
		System.out.println("[" + loginUser.getUserId() + "] /shipothersparts_removeall process");

		// Get DB List
		return "redirect:/shipothersparts";

	}

	/*
	 * /shipreqprocess/state1 파트너 협의요청
	 */
	@RequestMapping(value = "/shipreqprocess/state1", method = RequestMethod.POST)
	public String processBackToCartReq(ShipReqItem shipreqdata, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqprocess/state1 process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqprocess/state1 process");

		// check state
		int curstate = Integer.valueOf(request.getParameter("ship-StateId"));

		// 이전 상태에 따른 분기
		if (curstate == EShipState.STATE3_REQSHIPPING.getStateInt()) {
			// Get data from Web browser
			shipreqdata.setShipId(Integer.valueOf(request.getParameter("ship-Id")));
			shipreqdata.setShipRequestorId(request.getParameter("ship-RequestorId"));

			// 1. 현재 장바구니에 있으면 되돌리기 불가
			List<ShipReqPartsItem> lst = itemService.getShipPartsListItems(-1, shipreqdata.getShipRequestorId());
			if (lst.size() != 0) {
				String ret = showShipparts("0", model, request);
				model.addAttribute("reqresult", "[되돌리기실패]<br>현재대기중인 항목이 없어야 되돌릴 수 있습니다.");
				return ret;

			}

			itemService.stateMove3to1(shipreqdata, shipreqdata.getShipRequestorId());
			model.addAttribute("reqresult", shipreqdata.getShipId() + "'s data is added");
			System.out.println("/shipreqprocess/state1 processed..");

			// Get DB List
			return "redirect:../shipparts";
		}

		// State ERROR
		System.out.println("[" + loginUser.getUserId() + "]reqstate is not 1(요청서작성중)  !!");

		return "shipreqlist";
	}

}
