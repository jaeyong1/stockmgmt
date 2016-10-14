package com.lgit.stockmgmt.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lgit.stockmgmt.domain.Item;

public class ReadExcelFileToList {

	public static List<Item> readExcelData(String fileName) {
		List<Item> countriesList = new ArrayList<Item>();

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
			for (int i = 0; i < numberOfSheets; i++) {

				// Get the nth sheet from the workbook
				Sheet sheet = workbook.getSheetAt(i);

				// every sheet has rows, iterate over them
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					String notice_id = "";
					String member_id = "";

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
							if (notice_id.equalsIgnoreCase("")) {
								notice_id = cell.getStringCellValue().trim();
								System.out.println("1 " + notice_id);
							} else						if (member_id.equalsIgnoreCase("")) {
								member_id = cell.getStringCellValue().trim();
								System.out.println("2 " + member_id);
							} // else if...순차적으로 들어감
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (notice_id.equalsIgnoreCase("")) {
								// random data, leave it
								notice_id = Double.toString(cell.getNumericCellValue());

								System.out.println("1 " + notice_id);
							}
							break;

						}
					} // end of cell iterator
					//notice_id = notice_id.replace(".0", "");
					//System.out.println(" / " + notice_id);
//					int nid = Integer.parseInt(notice_id);
//					Item c = new Item(nid + "", member_id);
//					countriesList.add(c);
				} // end of rows iterator

			} // end of sheets for loop

			// close file input stream
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return countriesList;
	}
}
