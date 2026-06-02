package org.example.utilExcels;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilExcel {

    public static String SHEET_PATH = System.getProperty("user.dir") + "/src/test/resources/TestData.xlsx";
    static Workbook book;
    static Sheet sheet;
    private static final Logger logger = LogManager.getLogger(UtilExcel.class);

    public static Object[][] getTestDataFromExcel(String sheetName) {

        // Apache POI
        // Read the File - TestData.xlsX
        //  Workbook Create
        // Sheet
        // Row and Cell
        // 2D Object  - getData()

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(SHEET_PATH);
            book = WorkbookFactory.create(fileInputStream);
            sheet = book.getSheet(sheetName);

        } catch (IOException e) {
            logger.error("Excel file not found at {}: {}", SHEET_PATH, e.getMessage(), e);
        }


        Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {

                // First row email, password -> column name - skip - header
                data[i][j] = sheet.getRow(i + 1).getCell(j).toString();


            }
        }


        return data;
    }
}
