package com.lgit.stockmgmt.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lgit.stockmgmt.domain.JoinDBItem;
import com.lgit.stockmgmt.domain.PartsItem;
import com.lgit.stockmgmt.domain.ShipReqPartsItem;
import com.lgit.stockmgmt.domain.UserItem;
import com.lgit.stockmgmt.service.ItemService;
import com.lgit.stockmgmt.util.ReadExcelFileToList;
import com.lgit.stockmgmt.util.WriteListToExcelFile;

@Controller
public class ExcelController {
	/*
	 * Controller - Service 연결
	 */
	@Autowired
	private ItemService itemService;

	@Autowired
	ServletContext context;

	/*
	 * File upload 할수 있는 창
	 */

	@RequestMapping(value = "/fileupload", method = RequestMethod.GET)
	public String showUploadForm(Model model, HttpServletRequest request) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		return "fileupload";
	}

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public String fileUpload(@RequestParam("file1") MultipartFile multipartFile, Model model,
			HttpServletRequest request) throws IOException {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipreqlist process. no session info.  ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /shipreqlist process");

		String requestedURL = request.getParameter("requestedURL");
		System.out.println("File upload. requestedURL : " + requestedURL);
		String popupclosemsg = "";
		List<String> errorlog = new ArrayList<String>();
		boolean dbProcessSuccess = false;

		// 임시파일 저장경로
		System.out.println(request.getContextPath());

		if (multipartFile == null)
			return "fileupload";

		String fileExt = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf(".") + 1, multipartFile.getOriginalFilename().length());
		System.out.println("file ext : " + fileExt);

		// 넘어온 파일을 임시의 폴더에 둔다.
		// 임시 폴더는 C:\ 로 잡기로 한다.
		File uploadFile = File.createTempFile("/uploadeddata/", "." + fileExt);
		multipartFile.transferTo(uploadFile);

		System.out.println("File path: " + uploadFile.getAbsolutePath()); // 실제저장
																			// 풀경로
		uploadFile.deleteOnExit(); // deletes file when the virtual machine
									// terminate

		try {

			if (requestedURL.equals("/myparts")) {
				/*
				 * /myparts 에서 Excel import 경우
				 */
				List<PartsItem> lst = ReadExcelFileToList.readExcelPartsData(uploadFile.getAbsolutePath(), errorlog);
				System.out.println("Loaded Excel rows : " + lst.size());
				popupclosemsg = lst.size() + "건 로딩되었습니다.";

				if (lst.size() > 0) {
					dbProcessSuccess = itemService.addMyNewPartsXls(loginUser, lst, errorlog);
				} else {
					dbProcessSuccess = false;
				}
			} else if (requestedURL.equals("/shipparts")) {
				/*
				 * /shipparts(나의 출고요청 리스트) 에서 Excel import 경우
				 */
				ArrayList<String[]> lst = ReadExcelFileToList.readExcelCartData(uploadFile.getAbsolutePath(), errorlog);
				// ArrayList<String[]> addresses = new ArrayList<String[]>();

				System.out.println("Loaded Excel rows : " + lst.size());
				popupclosemsg = lst.size() + "건 로딩되었습니다.";

				if (lst.size() > 0) {
					dbProcessSuccess = itemService.addMyCartXls(loginUser, lst, errorlog);
					for (String[] strings : lst) {
						System.out.println(strings);
					}
				} else {
					dbProcessSuccess = false;
				}
			} else if (requestedURL.equals("/shipothersparts")) {
				/*
				 * /shipothersparts(파트너출고요청리스트) 에서 Excel import 경우
				 */
				ArrayList<String[]> lst = ReadExcelFileToList.readExcelCartData(uploadFile.getAbsolutePath(), errorlog);
				// ArrayList<String[]> addresses = new ArrayList<String[]>();

				System.out.println("Loaded Excel rows : " + lst.size());
				popupclosemsg = lst.size() + "건 로딩되었습니다.";

				if (lst.size() > 0) {
					dbProcessSuccess = itemService.addOthersCartXls(loginUser, lst, errorlog);
					for (String[] strings : lst) {
						System.out.println(strings);
					}
				} else {
					dbProcessSuccess = false;
				}
			} else {
				errorlog.add("JAVA system 에서 Excel 읽는중 장애발생");
				errorlog.add("요청된 경로를 해석하지 못했습니다. 관리자에게 연락주세요~");
				dbProcessSuccess = false;
				// ReadExcelFileToList.readExcelPartsData(uploadFile.getAbsolutePath(),
				// errorlog);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage().toString());
			errorlog.add("JAVA system 에서 Excel 읽는중 장애발생");
			errorlog.add("정상적으로 처리되지 못했습니다.");
			errorlog.add("[exception error]");
			errorlog.add(e.getMessage().toString());
			dbProcessSuccess = false;
		}

		System.out.println("/excel finish");

		// return page
		if (!dbProcessSuccess) {
			// === error finish ===
			String err = "[에러발생내역]<br>\n";
			for (int en = 0; en < errorlog.size(); en++) {
				err = err + errorlog.get(en) + "<br>\n";

			}
			model.addAttribute("errormsg", err);
			model.addAttribute("requestedURL", requestedURL); /* "/mylist" */
			return "errorpopupviewmoveto";
		} else {
			// === success finish ===
			model.addAttribute("popupclosemsg", popupclosemsg); // 없으면바로닫음
			model.addAttribute("requestedURL", requestedURL); /* "/mylist" */
			return "closememoveto";
		}

	}

	/*
	 * /mypartsimport
	 * 
	 * DataBase관리 - Parts관리 - Excel upload
	 */
	@RequestMapping(value = "/mypartsimport", method = RequestMethod.GET)
	public String addAdminParts(PartsItem item, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/ mypartsimport process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /mypartsimport process");
		model.addAttribute("requestedURL", "/myparts");
		return "fileupload";/* fileupload.jsp */
	}

	/*
	 * /shippartsimport 출고요청하기 - 출고요청 부품리스트 - Excel upload
	 */
	@RequestMapping(value = "/shippartsimport", method = RequestMethod.GET)
	public String addExcelMyCartItem(PartsItem item, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/ mypartsimport process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /mypartsimport process");
		model.addAttribute("requestedURL", "/shipparts");
		return "fileupload";/* fileupload.jsp */
	}

	/*
	 * /shipotherspartsimport 출고요청하기 - 출고요청 부품리스트 - Excel upload
	 */
	@RequestMapping(value = "/shipotherspartsimport", method = RequestMethod.GET)
	public String addExcelOthersCartItem(PartsItem item, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/shipotherspartsimport process. no session info. return login.jsp");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /mypartsimport process");
		model.addAttribute("requestedURL", "/shipothersparts");

		List<ShipReqPartsItem> items = itemService.getShipPartsListItems(-2, loginUser.getUserId());

		if (items.size() > 0) {
			List<String> errorlog = new ArrayList<String>();
			errorlog.add("파트리스트가 비어있지 않습니다.");
			errorlog.add("이미 들어있는 부품리스트를 출고요청 또는 삭제후에");
			errorlog.add("엑셀업로드를 진행해주세요.");
			////////
			String err = "[에러발생내역]<br>\n";
			for (int en = 0; en < errorlog.size(); en++) {
				err = err + errorlog.get(en) + "<br>\n";

			}
			model.addAttribute("errormsg", err);
			model.addAttribute("requestedURL", "/shipothersparts"); /* "/mylist" */
			return "errorpopupviewmoveto";

		}

		return "fileupload";/* fileupload.jsp */
	}

	/*
	 * /mylistexport
	 * 
	 * 나의자재 - Excel download
	 */
	@RequestMapping(value = "/mylistexport/{requrl}", method = RequestMethod.GET)
	public String exportMyParts(PartsItem item, @PathVariable String requrl, Model model, HttpServletRequest request) {
		// session 확인
		UserItem loginUser = (UserItem) request.getSession().getAttribute("userLoginInfo");
		if (loginUser == null) {
			System.out.println("/mylistexport process. no session info. return login.jsp ");
			return "login";
		}
		System.out.println("[" + loginUser.getUserId() + "] /mylistexport process");
		model.addAttribute("requestedURL", "/mylist");
		String requestedURL = request.getParameter("requestedURL");

		List<String> errorlog = new ArrayList<String>();

		List<JoinDBItem> items = null;
		
		if (requrl.equals("mylist")) {
			///////////////// List View
			items = itemService.getMyItemsByOwnerName(loginUser.getUserName());

		} else if (requrl.equals("otherslist")) {
			///////////////// List View
			items = itemService.getOthersItemsByOwnerName(loginUser.getUserName());

		} else {
			System.out.println("모르는 주소에서 요청됨");
			model.addAttribute("errormsg", "[에러발생]<br>\n 모르는 주소에서 요청됨 <br>\n");
			return "errorpopupviewmoveto";
		}

		/////////////////// Write Excel file
		String templatefile = context.getRealPath("") + "resources/xls/shipreqdata.xlsx"; // hard
																							// coding
		String exportFileName = context.getRealPath("") + "resources/tempxls/" + loginUser.getUserName() + ".xlsx";
		String exporturl = "/" + "resources/tempxls/" + loginUser.getUserName() + ".xlsx";
		// System.out.println(templatefile);
		// System.out.println(exportFileName);

		WriteListToExcelFile.writeMyPartsToFile(templatefile, exportFileName, items, errorlog);

		model.addAttribute("requestedURL", exporturl);
		return "filedownload";/* fileupload.jsp */
	}
}
