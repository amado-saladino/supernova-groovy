package helpers

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ExcelReader {
    private static XSSFSheet sheet

    private static void openSheet(String path,String sheetName) {
        File excelFile = new File(path)
        try {
            FileInputStream inputStream = new FileInputStream(excelFile)
            def workBook = new XSSFWorkbook(inputStream)
            sheet = workBook.getSheet(sheetName)
        } catch (FileNotFoundException e) {
            e.getMessage()
        } catch (IOException e) {
            e.getMessage()
        }
    }

    private static String readCell(int rowIndex, int colIndex) {
        sheet.getRow(rowIndex).getCell(colIndex).getStringCellValue()
    }

    static Iterator<Object> provider(workbook,sheetName){
        openSheet(workbook,sheetName)
        def first_row = sheet.getRow(0) as List  //row headers
        int rowCount = sheet.getLastRowNum()
        def all= new ArrayList()
        (1..rowCount).each {r->
            def cur_row = sheet.getRow(r) as List
            all+= [first_row,cur_row].transpose().collectEntries{[(it[0]).toString(),(it[1]).toString()]}
        }
        all.iterator()
    }
}
