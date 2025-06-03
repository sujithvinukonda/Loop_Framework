package com.loop.framework.core;

import java.util.HashMap;
import java.util.Map;

public class DataHandle {

	public static Map<String, String> testData = new HashMap<>();
	private static ThreadLocal<String> currentCaseId = new ThreadLocal<>();
	private static String currentCaseID;

	public static void setCurrentCaseID(String caseID) {
		currentCaseID = caseID;
	}

	public static String getCurrentCaseID() {
		return currentCaseID;
	}

	public static String readFromDataSheet(String key) {
		if (!testData.containsKey(key)) {
			throw new RuntimeException("❌ Key not found in Excel data: " + key);
		}
		return testData.get(key);
	}

	public static void loadTestData(String caseId) {
		currentCaseID = caseId;
		// setCurrentCaseId(caseId); // store for global use
		testData = ExcelReader.getDataByCaseID(caseId); // load data into static map
		if (testData == null || testData.isEmpty()) {
			throw new RuntimeException("❌ No test data found for Case_ID: " + caseId);
		}
	}

}
