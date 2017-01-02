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

		if (lst.get(0).trim().equals("프로젝트코드") && lst.get(1).trim().equals("개발담당자") && lst.get(2).trim().equals("부서")
				&& lst.get(3).trim().equals("출고담당자")) {
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

	public static List<PartsItem> readExcelInventoryCntlData(String fileName, List<String> errorlog) {

		List<PartsItem> partsItems = new ArrayList<PartsItem>();

		List<String> lststr = new ArrayList<String>(); // inner function process
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
					// 한줄 다 읽었으니, 처리하기..
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
					item.setPartName(lststr.get(4).toString());
					item.setPartDesc(lststr.get(5).toString());
					item.setPartMemo(lststr.get(6).toString());// maker

					if (isNum(lststr.get(7).toString())) {
						String str = lststr.get(7).trim().replaceAll(",", "").toString();
						float _f = Float.valueOf(str);
						item.setPartCost(_f);
					} else {
						System.out.println("[exception] setPartLocation parsing failed!!");
						String err = "Error: 숫자변환에 실패했습니다." + numRows + "라인, 엑셀값:" + lststr.get(7).toString();
						System.out.println(err);
						errorlog.add(err);
						item.setPartCost(Float.valueOf(0));
					}

					if (isNum(lststr.get(8).toString())) {
						String str = lststr.get(8).trim().replaceAll(",", "").toString();
						int _n = Integer.valueOf(str);
						item.setPartStock(_n);
					} else {
						System.out.println("[exception] setPartStock parsing failed!!");
						String err = "Error: 숫자변환에 실패했습니다. " + numRows + "라인, 엑셀값:" + lststr.get(8).toString();
						System.out.println(err);
						errorlog.add(err);
						item.setPartStock(0);

					}
					item.setPartLocation(lststr.get(9).toString());

					item.setPartMsllevel(lststr.get(10).toString());

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

	// 파츠정보조회/등록
	public static List<PartsItem> readExcelPartsData(String fileName, List<String> errorlog) {
		List<PartsItem> partsItems = new ArrayList<PartsItem>();

		List<String> lststr = new ArrayList<String>(); // inner function process
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
					// 한줄 다 읽었으니, 처리하기..
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
						String err = "Error: 숫자변환에 실패했습니다. " + numRows + "라인, 엑셀값:" + lststr.get(4).toString();
						System.out.println(err);
						errorlog.add(err);
						item.setPartStock(0);

					}
					item.setPartLocation(lststr.get(5).toString()); // F:Location

					if (isNum(lststr.get(6).toString())) { // G:단가
						String str = lststr.get(6).trim().replaceAll(",", "").toString();
						float _f = Float.valueOf(str);
						item.setPartCost(_f);
					} else {
						System.out.println("[exception] setPartLocation parsing failed!!");
						String err = "Error: 숫자변환에 실패했습니다. " + numRows + "라인,엑셀값:" + lststr.get(6).toString();
						System.out.println(err);
						errorlog.add(err);
						item.setPartCost(Float.valueOf(0));
					}

					item.setPartMsllevel(lststr.get(7).toString()); // H:MSL
																	// level

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
		String str = instr.replaceAll(",", "").trim();

		try {
			NumberFormat ukFormat = NumberFormat.getNumberInstance(Locale.UK);
			ukFormat.parse(str).intValue();
		} catch (ParseException e) {
			System.out.println("[exception] not a number : " + instr + "(parsing exception)");
			return false;
		}

		return true;
	}

	public static ArrayList<String[]> readExcelCartData(String fileName, List<String> errorlog) {
		ArrayList<String[]> xlsitems = new ArrayList<String[]>();

		List<String> lststr = new ArrayList<String>(); // inner function process
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
					// 한줄 다 읽었으니, 처리하기..
				System.out.println("[" + numRows + "] " + lststr.toString());

				// 사용자가 출고요청 값 안넣은 경우 0 처리
				if (lststr.size() >= 12) {
					if (lststr.get(11).toString().equals("")) {
						lststr.set(11, "0");
					}
				}

				// 엑셀 한줄처리
				if (!xlsDataLine && isTitle(lststr)) {
					System.out.println(">title line");
					xlsDataLine = true;
				} else if (xlsDataLine && isBlankLine(lststr)) {
					System.out.println(">blank line.. skip");
				} else if (xlsDataLine) {
					// process lststr [start]
					System.out.println(">process line");
					System.out.println(lststr.get(11).toString().trim());
					if (!lststr.get(11).toString().trim().equals("") && !lststr.get(11).toString().trim().equals("0")) {
						String[] item = new String[4];
						item[0] = lststr.get(0).toString(); // 프로젝트코드 A열(프로젝트코드)
						item[3] = lststr.get(1).toString(); // B열(개발담당자)
						item[1] = lststr.get(4).toString(); // E열(LGITP/N)

						if (isNum(lststr.get(11).toString())) {
							String str = lststr.get(11).trim().replaceAll(",", "").toString();
							int _n = Integer.valueOf(str);
							item[2] = String.valueOf(_n); // L열(출고요청수량)
						} else {
							System.out.println("[exception] setPartStock parsing failed!!");
							String err = "Error: 숫자변환에 실패했습니다. " + numRows + "라인, 엑셀값:" + lststr.get(11).toString();
							System.out.println(err);
							errorlog.add(err);
							item[2] = "0"; // L열(출고요청수량)
						}

						xlsitems.add(item);
					} else {
						System.out.println(" > readed, but no req ship amount, skip line");
					}
					// process lststr [end]

				}
				numRows = numRows + 1;
			} // end of rows iterator

			// close file input stream
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return xlsitems;
	}

}
