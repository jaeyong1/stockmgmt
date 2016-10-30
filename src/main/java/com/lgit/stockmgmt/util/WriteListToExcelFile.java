package com.lgit.stockmgmt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.domain.JoinDBItem;

public class WriteListToExcelFile {
	public static void writeNoticeListToFile(String fileName, List<Item> noticeList) throws Exception {
		Workbook workbook = null;

		if (fileName.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (fileName.endsWith("xls")) {
			workbook = new HSSFWorkbook();
		} else {
			throw new Exception("invalid file name, should be xls or xlsx");
		}

		Sheet sheet = workbook.createSheet("cordova");

		Iterator<Item> iterator = noticeList.iterator();

		int rowIndex = 0;
		int excelname = 0; // 처음에는 ID 학번등 고정값을 넣기 위해 사용한 변수
		do {
			Item notice = iterator.next();
			Row row = sheet.createRow(rowIndex++);

			if (excelname == 0) { // 처음에 고정값

				Cell cell0 = row.createCell(0);
				cell0.setCellValue("ID");
				Cell cell1 = row.createCell(1);
				cell1.setCellValue("학번");
				excelname++;

			} else { // 다음부터는 순차적으로 값이 들어감

				Cell cell0 = row.createCell(0);
				cell0.setCellValue(notice.getDevId());
				Cell cell1 = row.createCell(1);
				cell1.setCellValue(notice.getProjecName());

			}

		} while (iterator.hasNext());

		// lets write the excel data to file now
		FileOutputStream fos = new FileOutputStream(fileName);
		workbook.write(fos);
		fos.close();

		System.out.println(fileName + " written successfully");
	}

	public static void writeMyPartsToFile(String tempFileName, String exportfileName, List<JoinDBItem> lstitems, List<String> errorlog) {
		int i;

		FileInputStream fsIP = null;
		Workbook workbook = null;
		Sheet worksheet = null;

		if (exportfileName.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (exportfileName.endsWith("xls")) {
			workbook = new HSSFWorkbook();
		} else {
			try {
				throw new Exception("invalid file name, should be xls or xlsx");
			} catch (Exception e) {
				errorlog.add("excel download 파일이름 설정오류.");
				errorlog.add("관리자에게 연락주세요.");
				errorlog.add(e.getMessage().toString());
				e.printStackTrace();
			}
		}

		////// Load template excel file
		try {
			//fsIP = new FileInputStream(new File("D:\\shipreqdata.xlsx"));// 템플릿파일
			fsIP = new FileInputStream(new File(tempFileName));// 템플릿파일

			/****
			 * d d d d
			 * 
			 */

			workbook = new XSSFWorkbook(fsIP); // endsWith"xlsx"
			worksheet = workbook.getSheetAt(0);

		} catch (IOException e1) {
			errorlog.add("excel download 템플릿파일 열기오류(0). filename:" + exportfileName);
			errorlog.add("관리자에게 연락주세요.");
			errorlog.add(e1.getMessage().toString());
			e1.printStackTrace();
		}

		// Sheet worksheet = workbook.createSheet("재고확인");
		Row row = null;
		int rowIndex = 4; // [A5 셀부터 값 추가]

		for (i = 0; i < lstitems.size(); i++) {
			// new data
			JoinDBItem item = lstitems.get(i);
			row = worksheet.createRow(rowIndex++);
			// 데이터
			row.createCell(0).setCellValue(item.getPartProjectCode()); // A
			row.createCell(1).setCellValue(item.getUserOwnerName()); // B
			row.createCell(2).setCellValue(item.getUserTeamname()); // C
			row.createCell(3).setCellValue(item.getUserShipperName()); // D
			row.createCell(4).setCellValue(item.getPartName()); // E
			row.createCell(5).setCellValue(item.getPartDesc()); // F
			row.createCell(6).setCellValue(item.getPartMemo()); // G
			row.createCell(7).setCellValue(item.getPartCost()); // H
			row.createCell(8).setCellValue(item.getPartStock()); // I
			row.createCell(9).setCellValue(item.getPartLocation()); // J
			row.createCell(10).setCellValue("");// K

		} // for i

		// lets write the excel data to file now
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(exportfileName);
		} catch (FileNotFoundException e) {
			errorlog.add("excel download 파일열기 오류(1). filename:" + exportfileName);
			errorlog.add("관리자에게 연락주세요.");
			errorlog.add(e.getMessage().toString());
			e.printStackTrace();
		}
		try {
			workbook.write(fos);
		} catch (IOException e) {
			errorlog.add("excel download 파일 쓰기 오류(2). filename:" + exportfileName);
			errorlog.add("관리자에게 연락주세요.");
			errorlog.add(e.getMessage().toString());
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			errorlog.add("excel download 파일 닫기 오류 (3). filename:" + exportfileName);
			errorlog.add("관리자에게 연락주세요.");
			errorlog.add(e.getMessage().toString());
			e.printStackTrace();
		}

		System.out.println(exportfileName + " written successfully");

	}

}