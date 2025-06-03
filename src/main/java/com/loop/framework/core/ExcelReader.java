package com.loop.framework.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private static final String EXCEL_PATH = "src\\test\\resources\\data\\testData.xlsx"; // adjust
																																												// path

	public static Map<String, String> getDataByCaseID(String caseID) {
		Map<String, String> dataMap = new HashMap<>();

		try (FileInputStream fis = new FileInputStream(new File(EXCEL_PATH));
				Workbook workbook = new XSSFWorkbook(fis)) {

			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				Sheet sheet = workbook.getSheetAt(sheetIndex);
				Row headerRow = sheet.getRow(0);
				if (headerRow == null) {
					continue;
				}

				// validateExcelHeaders(sheet); // ✅ Optional: validate headers

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}

					Cell caseCell = row.getCell(0);
					if (caseCell == null) {
						continue;
					}

					String cellValue = getCellValueAsString(caseCell).trim();

					if (caseID.equalsIgnoreCase(cellValue)) {
						for (int j = 1; j < headerRow.getLastCellNum(); j++) {
							Cell headerCell = headerRow.getCell(j);
							Cell valueCell = row.getCell(j);

							if (headerCell != null) {
								String key = headerCell.getStringCellValue().trim();
								String value = (valueCell != null) ? getCellValueAsString(valueCell) : "";
								dataMap.put(key, value);
							}
						}

						if (dataMap.isEmpty()) {
							throw new RuntimeException("Data is empty for Case_ID: " + caseID);
						}

						return dataMap; // ✅ RETURN only when exact caseID matched
					}
				}
			}

			throw new RuntimeException("❌ Case_ID not found in any sheet: " + caseID);

		} catch (Exception e) {
			throw new RuntimeException("❌ Error reading Excel data for Case_ID: " + caseID, e);
		}
	}

	private static String getCellValueAsString(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			} else {
				return String.valueOf((long) cell.getNumericCellValue());
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		case BLANK:
			return "";
		default:
			return "";
		}
	}
}
