package com.wf.spinnify.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wf.spinnify.entity.WfAreaManagerWinners;
import com.wf.spinnify.entity.WfStoreWinners;
import com.wf.spinnify.repository.WfAmWinnersRepository;
import com.wf.spinnify.repository.WfStoreWinnersRepository;

@Component
public class SpinHelper {

	private WfStoreWinnersRepository storeWinnersRepository;
	private WfAmWinnersRepository amWinnersRepository;

	@Autowired
	public SpinHelper(WfStoreWinnersRepository storeWinnersRepository, WfAmWinnersRepository amWinnersRepository) {
		this.storeWinnersRepository = storeWinnersRepository;
		this.amWinnersRepository = amWinnersRepository;
	}

	public String convertToStoreCsvFile() throws IOException {
		List<WfStoreWinners> storeWinners = storeWinnersRepository.findAll();

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Winners");

			// Header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("RM Name");
			headerRow.createCell(1).setCellValue("Store Code");
			headerRow.createCell(2).setCellValue("Winner Store Name");

			// Fill data rows
			int rowIndex = 1;
			for (WfStoreWinners winner : storeWinners) {
				Row row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(winner.getRmName());
				row.createCell(1).setCellValue(winner.getStoreCode());
				row.createCell(2).setCellValue(winner.getWinnerStoreName());
			}

			// Write to a ByteArrayOutputStream and encode to Base64
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				workbook.write(out);
				byte[] excelBytes = out.toByteArray();
				return Base64.getEncoder().encodeToString(excelBytes);
			}
		}
	}

	public String convertToAmCsvFile() throws IOException {
		List<WfAreaManagerWinners> areaManagerWinners = amWinnersRepository.findAll();

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Winners");

			// Header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("RM Name");
			headerRow.createCell(1).setCellValue("Area Manager EMP Code");
			headerRow.createCell(2).setCellValue("Area manager Name");

			// Fill data rows
			int rowIndex = 1;
			for (WfAreaManagerWinners winner : areaManagerWinners) {
				Row row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(winner.getRmName());
				row.createCell(1).setCellValue(winner.getAmEmpCode());
				row.createCell(2).setCellValue(winner.getWinnerAmName());
			}

			// Write to a ByteArrayOutputStream and encode to Base64
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				workbook.write(out);
				byte[] excelBytes = out.toByteArray();
				return Base64.getEncoder().encodeToString(excelBytes);
			}
		}
	}

}
