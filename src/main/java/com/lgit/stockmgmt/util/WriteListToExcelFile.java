package com.lgit.stockmgmt.util;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lgit.stockmgmt.domain.Item;
 
public class WriteListToExcelFile {
 
    public static void writeNoticeListToFile(String fileName, List<Item> noticeList) throws Exception{
        Workbook workbook = null;
         
        if(fileName.endsWith("xlsx")){
            workbook = new XSSFWorkbook();
        }else if(fileName.endsWith("xls")){
            workbook = new HSSFWorkbook();
        }else{
            throw new Exception("invalid file name, should be xls or xlsx");
        }
         
        Sheet sheet = workbook.createSheet("cordova");
         
        Iterator<Item> iterator = noticeList.iterator();
         
        int rowIndex = 0;
        int excelname=0; // 처음에는 ID 학번등 고정값을 넣기 위해 사용한 변수
        do{
        	Item notice = iterator.next();
            Row row = sheet.createRow(rowIndex++);
           
            
            if(excelname==0){ // 처음에 고정값 
                 
                 Cell cell0 = row.createCell(0);
                 cell0.setCellValue("ID");
                 Cell cell1 = row.createCell(1);
                 cell1.setCellValue("학번");
                 excelname++;
                
            }else{  // 다음부터는 순차적으로 값이 들어감 
                 
                 Cell cell0 = row.createCell(0);
                 cell0.setCellValue(notice.getDevId());
                 Cell cell1 = row.createCell(1);
                 cell1.setCellValue(notice.getProjecName());
                
                
            }
            
            
        }while(iterator.hasNext());
     
         
        //lets write the excel data to file now
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        
        System.out.println(fileName + " written successfully");
    }
     
   
}