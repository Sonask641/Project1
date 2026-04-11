package utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;

    public static void setExcelFile() throws Exception {

        String excelPath = System.getProperty("user.dir")
                + "/src/test/resources/TestData/LoginData.xlsx";

        FileInputStream fis = new FileInputStream(excelPath);

        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheetAt(0);
    }

    public static String getCellData(int rowNum, int colNum) {

        if (sheet.getRow(rowNum) == null) {
            return "";
        }

        if (sheet.getRow(rowNum).getCell(colNum) == null) {
            return "";
        }

        return sheet.getRow(rowNum)
                .getCell(colNum)
                .toString();
    }

    public static void closeWorkbook() throws IOException {

        if (workbook != null) {
            workbook.close();
        }
    }
}