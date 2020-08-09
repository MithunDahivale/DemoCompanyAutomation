package org.common;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel Utility to Read Data from Excel
 */
public class ExcelUtility {
	static XSSFWorkbook workBook;
	static XSSFSheet sheet;
	public Logger log;

	/**
	 * SetUp Excel to Read Data
	 * 
	 * @param excelPath
	 * @param sheetName
	 * @throws Exception
	 */
	public ExcelUtility(String excelPath, String sheetName) throws Exception {
		try {
			PropertyConfigurator.configure(System.getProperty("user.dir") + "/config/log4j.properties");
			log = Logger.getLogger(this.getClass().getSimpleName());
			workBook = new XSSFWorkbook(excelPath);
			sheet = workBook.getSheet(sheetName);

		} catch (Exception exception) {
			log.error(exception);
			throw exception;
		}
	}

	/**
	 * Returns excel row count
	 * 
	 * @return int
	 */
	public int getRowCount() {
		try {
			return sheet.getPhysicalNumberOfRows();
		} catch (Exception exception) {
			log.error(exception);
			throw exception;
		}
	}

	/**
	 * Returns excel column count
	 * 
	 * @return int
	 */
	public int getColumnCount() {
		try {
			return sheet.getRow(0).getPhysicalNumberOfCells();
		} catch (Exception exception) {
			log.error(exception);
			throw exception;
		}
	}

	/**
	 * Returns excel cell data at a row and cell specified
	 * 
	 * @param rownum
	 * @param cellnum
	 * @return String
	 */
	public String getCellData(int rownum, int cellnum) {
		try {
			XSSFCell cell = sheet.getRow(rownum).getCell(cellnum);
			return formatCell(cell);

		} catch (Exception exception) {
			log.error(exception);
			throw exception;
		}
	}

	/**
	 * Return formatted cell data
	 * 
	 * @param cell
	 * @return String
	 */
	private String formatCell(XSSFCell cell) {
		try {
			if (cell != null) {
				switch (cell.getCellType()) {
				case STRING:
					return cell.getRichStringCellValue().toString();

				case NUMERIC:
					DataFormatter formatter = new DataFormatter();
					return formatter.formatCellValue(cell);

				default:
					break;
				}
			}

		} catch (Exception exception) {
			log.error(exception);
			throw exception;
		}
		return null;
	}

}
