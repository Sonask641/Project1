package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;

    // Set Excel file and sheet
    public static void setExcelFile() throws Exception {
        FileInputStream file = new FileInputStream(
            "C:\\latestEclipse\\ContactManagementAutomation\\TestData\\LoginData.xlsx"
        );
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheet("Sheet1"); // make sure sheet name matches your Excel
    }

    // Get data from Excel cell
    public static String getCellData(int rowNum, int colNum) throws Exception {
        Row row = sheet.getRow(rowNum);
        if(row == null) {
            return "";
        }
        Cell cell = row.getCell(colNum);
        if(cell == null) {
            return "";
        }
        return cell.toString();
    }

    // Write data to Excel cell
    public static void setCellData(String value, int rowNum, int colNum) throws Exception {
        Row row = sheet.getRow(rowNum);
        if(row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(colNum);
        if(cell == null) {
            cell = row.createCell(colNum);
        }
        cell.setCellValue(value);

        FileOutputStream fileOut = new FileOutputStream(
            "C:\\latestEclipse\\ContactManagementAutomation\\TestData\\LoginData.xlsx"
        );
        workbook.write(fileOut);
        fileOut.close();
    }

    // Close workbook
    public static void closeWorkbook() throws Exception {
        if(workbook != null) {
            workbook.close();
        }
    }
}