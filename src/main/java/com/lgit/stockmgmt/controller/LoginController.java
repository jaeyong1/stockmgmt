package com.lgit.stockmgmt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgit.stockmgmt.domain.EShipState;
import com.lgit.stockmgmt.domain.EUserLevel;
import com.lgit.stockmgmt.domain.LogUserItem;
import com.lgit.stockmgmt.domain.PartsItem;
import com.lgit.stockmgmt.domain.SecureUserItem;
import com.lgit.stockmgmt.domain.ShipReqItem;
import com.lgit.stockmgmt.domain.UserItem;
import com.lgit.stockmgmt.service.ItemService;

@Controller
public class LoginController {
	/*
	 * Controller - Service 연결
	 */
	@Autowired
	private ItemService itemService;

	/*
	 * 
	 * ================================= Login =================================
	 * /login 사용자 로그인
	 */

	// 세션사용 화면
	@RequestMapping("page1")
	public String page1() {
		return "page1";
	}

	// 세션 사용 안하는 화면
	@RequestMapping("page2")
	public String page2() {
		return "page2";
	}

	// 로그인 화면
	@RequestMapping("login")
	public String login() {
		System.out.println("/login");
		return "login";
	}

	// 로그아웃
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.setAttribute("userLoginInfo", null);
		System.out.println("/logout");
		return "redirect:login";
	}

	// 로그인 처리
	@RequestMapping(value = "loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(UserItem user, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		String reqID = request.getParameter("login-id"); // <html.input>name='login-id'
		String reqPW = request.getParameter("login-pw");

		System.out.println("/loginProcess  try(" + reqID + " / " + reqPW + ")");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:login");

		UserItem loginUser = itemService.findByUserIdAndPassword(reqID, reqPW);
		SecureUserItem secUserInfo = null;

		// secure check[start]
		if (loginUser != null) {
			secUserInfo = itemService.getSecureUserById(loginUser);
			
			// User ID is Locked...
			if (secUserInfo.getIsLocked() == 1) {
				response.setContentType("text/html; charset=UTF-8");
				loginUser = null;
				PrintWriter out;
				try {
					out = response.getWriter();
					out.println("<script>alert('로그인 ID 잠김.. 관리자에게 문의하세요..'); history.go(-1);</script>");
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if (secUserInfo.getIsReseted() == 1) {
				
			}
			/*
			 else if (secUserInfo.getIsReseted() == 1) {
				// +3개월
				LocalDateTime time4 = LocalDateTime.now().plusMonths(3);
				time4.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

			}
			*/

			else {
				// lastlogined_date,  pw error reset
				System.out.println("secure login info ok. update latest login date");
				itemService.updateLoginedSecureInfoById(loginUser);
			}

		}
		// secure check[end]

		if (loginUser == null) {
			response.setContentType("text/html; charset=UTF-8");

			PrintWriter out;
			try {
				out = response.getWriter();
				out.println("<script>alert('로그인 실패..'); history.go(-1);</script>");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (loginUser != null) {
			session.setAttribute("userLoginInfo", loginUser);

			if ((loginUser.getUserLevel() == EUserLevel.Lv3_SHIPPER.getLevelInt())
					|| (loginUser.getUserLevel() == EUserLevel.Lv6_SHIPPERADMIN.getLevelInt())) {
				mav.setViewName("redirect:shipreqlist");
			} else {
				mav.setViewName("redirect:mylist");
			}
		}
		return mav;
	}

	// 회원가입

	@RequestMapping(value = "/idregi", method = RequestMethod.GET)
	public String getIDRegiForm(Model model, HttpServletRequest request) {

		System.out.println("/idregi process. no session info.  ");

		return "idregistration";
	}

	/*
	 * /adminuser 사용자 신규 등록처리
	 */
	@RequestMapping(value = "/newuser", method = RequestMethod.POST)
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
		System.out.println("/newuser" + userdata.toString());

		// add user operation log
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		itemService.addUserLog(userdata.getUserId(), ip, "신규회원가입");

		// Get DB List
		return "redirect:login";
	}

	// 비밀번호 변경

	@RequestMapping(value = "/changepw", method = RequestMethod.GET)
	public String changePWForm(Model model, HttpServletRequest request) {
		System.out.println("/changepw process. no session info.");
		return "changepw";
	}

	/*
	 * /adminuser 사용자 비밀번호 변경(self)
	 */
	@RequestMapping(value = "/changetonewpw", method = RequestMethod.POST)
	public String modifyUserPW(UserItem userdata, Model model, HttpServletRequest request) {

		boolean validUser = false;
		boolean findedId = false;
		List<String> errorlog = new ArrayList<String>();
		String reqUserId = request.getParameter("user-Id");
		String reqUserOldPw = request.getParameter("user-Password");
		List<UserItem> items = itemService.getUserItems();
		for (UserItem p : items) {
			if (p.getUserId().equals(reqUserId)) {
				findedId = true;
				if (p.getUserPassword().equals(reqUserOldPw)) {
					validUser = true;
					System.out.println("ID&PW찾음");
				} else {
					String err = "Error: 기존 비밀번호가 잘못됐습니다. ";
					System.out.println(err);
					errorlog.add(err);
				}
			}

		}

		if (!validUser) {
			if (!findedId) {
				String err1 = "Error: 변경할 ID가 없습니다.";
				System.out.println(err1);
				errorlog.add(err1);
			}
			// === error finish ===
			String err = "[에러발생내역]<br>\n";
			for (int en = 0; en < errorlog.size(); en++) {
				err = err + errorlog.get(en) + "<br>\n";

			}
			// not valid user
			model.addAttribute("errormsg", err);
			model.addAttribute("requestedURL", "/"); /* "/mylist" */
			return "errorpopupviewmoveto_nologin";
		}

		// Get data from Webbrowser
		userdata.setUserId(request.getParameter("user-Id"));
		userdata.setUserPassword(request.getParameter("user-Password2"));

		// Change DB query
		itemService.changeUserPassword(userdata);
		System.out.println("/change pw user :" + userdata.toString());

		// === success finish ===

		model.addAttribute("popupclosemsg", "비밀번호가 변경되었습니다."); // 없으면바로닫음
		model.addAttribute("requestedURL", "/"); /* "/mylist" */

		// add user operation log
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		itemService.addUserLog(userdata.getUserId(), ip, "비밀번호 직접변경");

		return "closememoveto_nologin";
	}

	/*
	 * 탈퇴처리 필요
	 */

	// ============================= LOG

	/*
	 * /logview 요청서 작성중 처리
	 */
	@RequestMapping(value = "/logview", method = RequestMethod.GET)
	public String queryUserLog2(Model model, ShipReqItem shipreqdata, HttpServletRequest request) {
		return queryUserLog1("0", shipreqdata, model, request);
	}

	@RequestMapping(value = "/logview/{seq}", method = RequestMethod.GET)
	public String queryUserLog1(@PathVariable String seq, ShipReqItem shipreqdata, Model model,
			HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/logview process. no session info. return login.jsp ");
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
		System.out.println("[" + loginUser.getUserId() + "] /logview process");

		final int rowsPer1Page = 100;

		/////////////////// List View
		List<LogUserItem> items = itemService.getLogUserItems();
		System.out.println("/logview process");
		// model.addAttribute("items", items);

		// Choose current page data
		PagedListHolder<LogUserItem> paging = new PagedListHolder<LogUserItem>(items);
		paging.setPageSize(rowsPer1Page);
		paging.setPage(Integer.parseInt(seq));
		model.addAttribute("items", paging.getPageList());

		// Add Page number information
		model.addAttribute("pageNum", paging.getPageCount());
		model.addAttribute("start", paging.getFirstLinkedPage());
		model.addAttribute("end", paging.getLastLinkedPage());
		// System.out.println(paging.getFirstElementOnPage());//현 페이지 첫번째게시물의 DB
		// 인덱스..

		return "logview";
	}

}