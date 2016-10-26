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

	public static List<PartsItem> readExcelPartsData(String fileName) {
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

						String str = lststr.get(4).trim().toString();
						str.replaceAll(",", "");
						int _n = Integer.valueOf(str);						
						item.setPartStock(_n);
					} else {
						System.out.println("[exception] setPartStock parsing failed!!");
						item.setPartStock(0);

					}
					item.setPartLocation(lststr.get(5).toString());

					if (isNum(lststr.get(6).toString())) {
						item.setPartCost(Float.valueOf(lststr.get(6).toString()));
					} else {
						System.out.println("[exception] setPartLocation parsing failed!!");
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

	// 값이 맞는가?
	public static List<Item> readExcelData(String fileName) {
		List<Item> countriesList = new ArrayList<Item>();

		int numRows = 1;
		try {
			// Create the input stream from the xlsx/xls file
			FileInputStream fis = new FileInputStream(fileName);

			// Create Workbook instance for xlsx/xls file input stream
			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			}

			// Get the number of sheets in the xlsx file
			int numberOfSheets = workbook.getNumberOfSheets();

			// loop through each of the sheets
			// for (int i = 0; i < numberOfSheets; i++) {

			// Get the nth sheet from the workbook
			Sheet sheet = workbook.getSheetAt(0);// i=0

			// every sheet has rows, iterate over them
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {

				String notice_id = "";
				String member_id = "";

				String cellRawData = "";

				// Get the row object
				Row row = rowIterator.next();

				// Every row has columns, get the column iterator and
				// iterate over them
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					// Get the Cell object
					Cell cell = cellIterator.next();

					// check the cell type and process accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						/*
						 * if (notice_id.equalsIgnoreCase("")) { notice_id =
						 * cell.getStringCellValue().trim();
						 * System.out.println("1 " + notice_id); } else if
						 * (member_id.equalsIgnoreCase("")) { member_id =
						 * cell.getStringCellValue().trim();
						 * System.out.println("2 " + member_id); } // else
						 * if...순차적으로 들어감
						 */
						cellRawData = cell.getStringCellValue().trim();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (notice_id.equalsIgnoreCase("")) {
							// random data, leave it
							notice_id = Double.toString(cell.getNumericCellValue());
							cellRawData = Double.toString(cell.getNumericCellValue());

						}
						break;

					}

					System.out.println("[" + numRows + "] " + cellRawData);

				} // end of cell iterator
					// notice_id = notice_id.replace(".0", "");
					// System.out.println(" / " + notice_id);
					// int nid = Integer.parseInt(notice_id);
					// Item c = new Item(nid + "", member_id);
					// countriesList.add(c);
				numRows = numRows + 1;
			} // end of rows iterator

			// } // end of sheets for loop

			// close file input stream
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return countriesList;
	}

	public static boolean isNum(String instr) {
		// 콤마삭제
		instr.replaceAll(",", "");

		String str = instr.trim();

	asdf버그있음..	if (!str.matches("-?\\d+(\\.\\d+)?")) {
			// match a number with optional '-' and decimal.
			return false;
		}

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