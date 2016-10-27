package com.lgit.stockmgmt.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.domain.PartsItem;

public class ReadExcelFileToList {

	/**
	 * 엑셀의 표 헤더 순서대로 정의. 이 라인이후부터 데이터로 읽음..
	 */
	public static boolean isTitle(List<String> lst) {

		if (lst.get(0).trim().equals("프로젝트코드") && lst.get(1).trim().equals("LGIT P/N")
				&& lst.get(2).trim().equals("Desc") && lst.get(3).trim().equals("Maker")) {
			return true;
		}

		return false;
	}

	public static boolean isBlankLine(List<String> lst) {
		int blankcnt = 0;
		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).equals(""))
				blankcnt = blankcnt + 1;
		}
		return (lst.size() == blankcnt);
	}

	public static List<PartsItem> readExcelPartsData(String fileName, List<String> errorlog) {
		List<PartsItem> partsItems = new ArrayList<PartsItem>();

		List<String> lststr = new ArrayList<String>();
		boolean xlsDataLine = false;
		int numRows = 1;
		try {
			FileInputStream fis = new FileInputStream(fileName);

			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			}

			// Get the first sheet from the workbook
			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				lststr.clear();
				String cellRawData = "";

				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell _cell = cellIterator.next();

					switch (_cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						cellRawData = _cell.getStringCellValue().trim();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
						decimalFormatSymbols.setDecimalSeparator('.');
						decimalFormatSymbols.setGroupingSeparator(',');
						DecimalFormat decimalFormat = new DecimalFormat("#,###.#####", decimalFormatSymbols);
						cellRawData = decimalFormat.format(_cell.getNumericCellValue()); // 1,237,516.25

						break;
					case Cell.CELL_TYPE_BLANK:
						cellRawData = "";
						break;
					default:
						cellRawData = _cell.getStringCellValue().trim();
						break;
					}

					// System.out.println("[" + numRows + "] " + cellRawData);
					lststr.add(cellRawData);

				} // end of cell iterator
				System.out.println("[" + numRows + "] " + lststr.toString());
				if (!xlsDataLine && isTitle(lststr)) {
					System.out.println(">title line");
					xlsDataLine = true;
				} else if (xlsDataLine && isBlankLine(lststr)) {
					System.out.println(">blank line.. skip");
				} else if (xlsDataLine) {
					// process lststr [start]
					System.out.println(">process line");

					PartsItem item = new PartsItem();
					item.setPartProjectCode(lststr.get(0).toString());
					item.setPartName(lststr.get(1).toString());
					item.setPartDesc(lststr.get(2).toString());
					item.setPartMemo(lststr.get(3).toString());// maker

					if (isNum(lststr.get(4).toString())) {						
						String str = lststr.get(4).trim().replaceAll(",", "").toString();
						int _n = Integer.valueOf(str);						
						item.setPartStock(_n);
					} else {
						System.out.println("[exception] setPartStock parsing failed!!");
						String err = "Error: 숫자변환에 실패했습니다. 엑셀값:" +  lststr.get(4).toString();
						System.out.println(err);
						errorlog.add(err);
						item.setPartStock(0);

					}
					item.setPartLocation(lststr.get(5).toString());

					if (isNum(lststr.get(6).toString())) {
						item.setPartCost(Float.valueOf(lststr.get(6).toString()));
					} else {
						System.out.println("[exception] setPartLocation parsing failed!!");
						String err = "Error: 숫자변환에 실패했습니다. 엑셀값:" +  lststr.get(6).toString();
						System.out.println(err);
						errorlog.add(err);
						item.setPartCost(Float.valueOf(0));
					}

					partsItems.add(item);

					// process lststr [end]

				}
				numRows = numRows + 1;
			} // end of rows iterator

			// close file input stream
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return partsItems;
	}


	public static boolean isNum(String instr) {
		// 콤마삭제
		String str  = instr.replaceAll(",", "").trim();

		try {
			NumberFormat ukFormat = NumberFormat.getNumberInstance(Locale.UK);
			ukFormat.parse(str).intValue();
		} catch (ParseException e) {
			System.out.println("[exception] not a number : " + instr + "(parsing exception)");
			return false;
		}

		return true;
	}

	
}
