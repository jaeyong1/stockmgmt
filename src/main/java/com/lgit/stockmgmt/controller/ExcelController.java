package com.lgit.stockmgmt.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.domain.PartsItem;
import com.lgit.stockmgmt.domain.UserItem;
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
	@RequestMapping(value = "/exceltest", method = RequestMethod.GET)
	public String showUploadFormTest(Model model, HttpServletRequest request) {
		List<Item> noticelist = new ArrayList<Item>();
		noticelist.add(new Item("1", "aa"));
		noticelist.add(new Item("2", "bb"));
		noticelist.add(new Item("3", "cc"));
		noticelist.add(new Item("4", "dd"));

		try {
			ReadExcelFileToList.readExcelData("cordova2.xlsx");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage().toString());
		}
		System.out.println("/excel finish");

		return "uploadform";
	}

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

		String requestedURL = request.getParameter("requestedURL");
		System.out.println("File upload. requestedURL : " + requestedURL);
		String popupclosemsg = "";

		// Set pathSet =
		// request.getSession().getServletContext().getResourcePaths("/");
		// System.out.println(pathSet); //[/WEB-INF/, /resources/]
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
			/*
			 * /myparts 에서 Excel import 경우
			 */
			if (requestedURL.equals("/myparts")) {
				List<PartsItem> lst = ReadExcelFileToList.readExcelPartsData(uploadFile.getAbsolutePath());
				System.out.println("Loaded Excel rows : " + lst.size());
				popupclosemsg = lst.size() + "건 로딩되었습니다.";
				for (int i = 0; i < lst.size(); i++) {
					itemService.setPartsItem(lst.get(i));
				}

			} else {
				ReadExcelFileToList.readExcelPartsData(uploadFile.getAbsolutePath());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage().toString());
		}
		System.out.println("/excel finish");

		/*
		 * == sample code == if ( multipartFile == null) return "home";
		 * 
		 * String fileExt = multipartFile.getOriginalFilename().substring(
		 * multipartFile.getOriginalFilename().lastIndexOf( ".") + 1,
		 * multipartFile.getOriginalFilename().length());
		 * 
		 * // 넘어온 파일을 임시의 폴더에 둔다. // 임시 폴더는 C:\ 로 잡기로 한다. File uploadFile =
		 * File.createTempFile( "c:\\", "." + fileExt);
		 * multipartFile.transferTo( uploadFile);
		 * 
		 * File thumbnail = File.createTempFile( "c:\\", "." + fileExt);
		 * 
		 * 
		 * // 이미지 파일만 썸네일 을 만든다. if ( ImageUtils.isImageFile ( fileExt)) {
		 * ThumbnailUtil.makeThumbnail( uploadFile, thumbnail, 100, 100); String
		 * imageBase64 = ImageUtils.encodeToString( thumbnail, fileExt);
		 * model.addAttribute("imageBase64", "data:image/png;base64," +
		 * imageBase64); }
		 * 
		 * model.addAttribute("targetFileInfo",
		 * multipartFile.getOriginalFilename());
		 * model.addAttribute("uploadFilePath", uploadFile.getAbsolutePath());
		 * 
		 */

		// return "fileUpload"; /* fileUpload.jsp */

		// === error finish ===

		// === success finish ===
		model.addAttribute("popupclosemsg", popupclosemsg); // 없으면바로닫음
		model.addAttribute("requestedURL", requestedURL); /* "/mylist" */
		return "closememoveto";

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

}
