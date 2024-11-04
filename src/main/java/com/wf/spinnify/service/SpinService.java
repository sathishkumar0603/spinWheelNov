package com.wf.spinnify.service;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wf.spinnify.entity.WfAreaManagerWinners;
import com.wf.spinnify.entity.WfAreaManagersList;
import com.wf.spinnify.entity.WfRegionalManagersEntity;
import com.wf.spinnify.entity.WfStoreList;
import com.wf.spinnify.entity.WfStoreWinners;
import com.wf.spinnify.model.AmDetails;
import com.wf.spinnify.model.AmWinnersResponse;
import com.wf.spinnify.model.StoreWinnersResponse;
import com.wf.spinnify.repository.WfAmWinnersRepository;
import com.wf.spinnify.repository.WfAreaManagersListRepository;
import com.wf.spinnify.repository.WfRegionalManagersRepository;
import com.wf.spinnify.repository.WfStoreListRepository;
import com.wf.spinnify.repository.WfStoreWinnersRepository;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpinService {

	private WfRegionalManagersRepository regionalManagersRepository;
	private WfAreaManagersListRepository areaManagersListRepository;
	private WfStoreListRepository wfStoreListRepository;
	private WfStoreWinnersRepository wfStoreWinnersRepository;
	private WfAmWinnersRepository wfAreaManagerWinnersRepository;

	@Autowired
	public SpinService(WfRegionalManagersRepository regionalManagersRepository,
			WfAreaManagersListRepository areaManagersListRepository, WfStoreListRepository wfStoreListRepository,
			WfStoreWinnersRepository wfStoreWinnersRepository, WfAmWinnersRepository wfAreaManagerWinnersRepository) {
		this.regionalManagersRepository = regionalManagersRepository;
		this.areaManagersListRepository = areaManagersListRepository;
		this.wfStoreListRepository = wfStoreListRepository;
		this.wfStoreWinnersRepository = wfStoreWinnersRepository;
		this.wfAreaManagerWinnersRepository = wfAreaManagerWinnersRepository;
	}

	public String extractData(MultipartFile rm, MultipartFile am, MultipartFile store) {
		StringBuilder response = new StringBuilder();
		try (InputStream rmStream = rm.getInputStream();
				InputStream amStream = am.getInputStream();
				InputStream storeStream = store.getInputStream()) {

			response.append("RM File Data: ").append(processRmFile(rmStream));
			response.append("AM File Data: ").append(processAmFile(amStream));
			response.append("Store File Data: ").append(processStoreFile(storeStream));

		} catch (Exception e) {
			e.printStackTrace();
			return "Error while processing the files.";
		}
		return response.toString();
	}

	private Object processStoreFile(InputStream storeStream) {
		DataFormatter dataFormatter = new DataFormatter();
		List<WfStoreList> entities = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(storeStream)) {
			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {
				if (row.getRowNum() == 0)
					continue;

				WfStoreList entity = new WfStoreList();

				// Reading Name column (assuming column 0)
				if (row.getCell(0) != null) {
					String rmName = row.getCell(0).getStringCellValue();
					entity.setRmName(rmName);
				}

				// Reading RM Employee Code column (assuming column 1)
				if (row.getCell(1) != null) {
					String storeName = row.getCell(1).getStringCellValue();
					entity.setStoreName(storeName);
				}

				if (row.getCell(2) != null) {
					String achievement = dataFormatter.formatCellValue(row.getCell(2));
					entity.setAchievement(achievement);
				}

				if (row.getCell(3) != null) {
					String storeCode = dataFormatter.formatCellValue(row.getCell(3));
					entity.setStoreCode(storeCode);
				}

				entity.setCreatedDate(LocalDateTime.now().toString());
				entities.add(entity);
			}

			wfStoreListRepository.saveAll(entities);

		} catch (Exception e) {
			e.printStackTrace();
			return "Error processing STORE file: " + e.getMessage();
		}
		return "STORE data processed successfully.";

	}

	private Object processAmFile(InputStream amStream) {
		DataFormatter dataFormatter = new DataFormatter();
		List<WfAreaManagersList> entities = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(amStream)) {
			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {
				if (row.getRowNum() == 0)
					continue;

				WfAreaManagersList entity = new WfAreaManagersList();

				// Reading Name column (assuming column 0)
				if (row.getCell(0) != null) {
					String rmName = row.getCell(0).getStringCellValue();
					entity.setRmName(rmName);
				}

				// Reading RM Employee Code column (assuming column 1)
				if (row.getCell(1) != null) {
					String amName = row.getCell(1).getStringCellValue();
					entity.setAmName(amName);
				}

				if (row.getCell(2) != null) {
					String achievement = dataFormatter.formatCellValue(row.getCell(2));
					entity.setAchievement(achievement);
				}

				if (row.getCell(3) != null) {
					String empId = row.getCell(3).getStringCellValue();
					entity.setAmEmpCode(empId);
				}

				if (row.getCell(4) != null) {
					String url = row.getCell(4).getStringCellValue();
					entity.setUrl(url);
				}

				entity.setCreatedDate(LocalDateTime.now().toString());
				entities.add(entity);
			}

			areaManagersListRepository.saveAll(entities);

		} catch (Exception e) {
			e.printStackTrace();
			return "Error processing AM file: " + e.getMessage();
		}
		return "AM data processed successfully.";

	}

	@SuppressWarnings("deprecation")
	private Object processRmFile(InputStream rmStream) {
		List<WfRegionalManagersEntity> entities = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(rmStream)) {
			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {
				if (row.getRowNum() == 0)
					continue;

				WfRegionalManagersEntity entity = new WfRegionalManagersEntity();

				// Reading Name column (assuming column 0)
				if (row.getCell(0) != null) {
					String name = row.getCell(0).getStringCellValue();
					entity.setName(name);
				}

				// Reading RM Employee Code column (assuming column 1)
				if (row.getCell(1) != null) {
					row.getCell(1).setCellType(CellType.STRING); // Force the cell type to STRING
					String rmEmpCode = row.getCell(1).getStringCellValue();
					entity.setRmEmpCode(rmEmpCode);
				}
				if (row.getCell(2) != null) {
					row.getCell(1).setCellType(CellType.STRING); // Force the cell type to STRING
					String url = row.getCell(2).getStringCellValue();
					entity.setUrl(url);
				}
				entity.setCreatedTime(LocalDateTime.now().toString());
				entities.add(entity);
			}

			regionalManagersRepository.saveAll(entities);

		} catch (Exception e) {
			e.printStackTrace();
			return "Error processing RM file: " + e.getMessage();
		}
		return "RM data processed successfully.";
	}

	public List<StoreWinnersResponse> getAllWinners() {
		List<StoreWinnersResponse> responseList = new ArrayList<>();

		try {
			List<WfRegionalManagersEntity> entities = regionalManagersRepository.findAll();
			List<WfStoreWinners> storeWinnersList = wfStoreWinnersRepository.findAll();
			if (!storeWinnersList.isEmpty()) {
				wfStoreWinnersRepository.deleteAll();
			}

			for (WfRegionalManagersEntity wfRegionalManagersEntity : entities) {
				List<WfStoreList> allStores = wfStoreListRepository
						.findAllByRmNameIgnoreCase(wfRegionalManagersEntity.getName());

				int storeCount = allStores.size();
				int winnerCount = (int) Math.round(storeCount * 0.2);

				// Map store names to store codes
				Map<String, String> storeNameToCodeMap = allStores.stream()
						.collect(Collectors.toMap(WfStoreList::getStoreName, WfStoreList::getStoreCode));

				// Randomly select 20% of the store names
				List<String> allStoreNames = new ArrayList<>(storeNameToCodeMap.keySet());
				Collections.shuffle(allStoreNames);
				List<String> storeWinners = allStoreNames.subList(0, Math.min(winnerCount, allStoreNames.size()));

				for (String winnerStoreName : storeWinners) {
					WfStoreWinners winnerEntity = new WfStoreWinners();
					winnerEntity.setWinnerStoreName(winnerStoreName);
					winnerEntity.setStoreCode(storeNameToCodeMap.get(winnerStoreName));
					winnerEntity.setRmName(wfRegionalManagersEntity.getName());
					winnerEntity.setCreatedDate(LocalDate.now().toString());

					wfStoreWinnersRepository.save(winnerEntity);
				}

				// Create response object
				StoreWinnersResponse response = new StoreWinnersResponse();
				response.setEmpCode(wfRegionalManagersEntity.getRmEmpCode());
				response.setUrl(wfRegionalManagersEntity.getUrl());
				response.setRmName(wfRegionalManagersEntity.getName());
				response.setStoreName(new ArrayList<>(storeNameToCodeMap.keySet()));
				response.setStoreCount(String.valueOf(storeCount));
				response.setStoreWinners(storeWinners);

				responseList.add(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	public List<AmWinnersResponse> getAllAmWinners() {
		List<AmWinnersResponse> responses = new ArrayList<>();

		try {
			List<WfRegionalManagersEntity> entities = regionalManagersRepository.findAll();
			List<WfAreaManagerWinners> allAmNamesList = wfAreaManagerWinnersRepository.findAll();

			if (!allAmNamesList.isEmpty()) {
				wfAreaManagerWinnersRepository.deleteAll();
			}

			for (WfRegionalManagersEntity wfRegionalManagersEntity : entities) {
				List<WfAreaManagersList> areaManagersLists = areaManagersListRepository
						.findAllByRmNameIgnoreCase(wfRegionalManagersEntity.getName());

				int amCount = areaManagersLists.size();
				int winnerCount = (int) Math.ceil(amCount * 0.2); // Calculate 20%

				// Collect all AM names for the "amnames" field
				List<String> allAmNames = areaManagersLists.stream().map(WfAreaManagersList::getAmName)
						.collect(Collectors.toList());

				// Randomly select 20% winners and map to AmDetails for "amWinners"
				List<AmDetails> amWinners = areaManagersLists.stream().map(am -> {
					AmDetails amDetail = new AmDetails();
					amDetail.setAmName(am.getAmName());
					amDetail.setAmUrl(am.getUrl());
					return amDetail;
				}).collect(Collectors.toList());

				Collections.shuffle(amWinners); // Shuffle for randomness
				amWinners = amWinners.subList(0, Math.min(winnerCount, amWinners.size()));

				// Save winners in WfAreaManagerWinners entity
				for (AmDetails winner : amWinners) {
					WfAreaManagerWinners winnerEntity = new WfAreaManagerWinners();
					winnerEntity.setWinnerAmName(winner.getAmName());

					// Get the matching WfAreaManagersList object to retrieve empCode
					areaManagersLists.stream().filter(am -> am.getAmName().equals(winner.getAmName())).findFirst()
							.ifPresent(am -> winnerEntity.setAmEmpCode(am.getAmEmpCode()));

					winnerEntity.setRmName(wfRegionalManagersEntity.getName());
					winnerEntity.setCreatedDate(LocalDate.now().toString());

					// Save the winner entity
					wfAreaManagerWinnersRepository.save(winnerEntity);
				}

				// Create response object
				AmWinnersResponse response = new AmWinnersResponse();
				response.setEmpCode(wfRegionalManagersEntity.getRmEmpCode());
				response.setUrl(wfRegionalManagersEntity.getUrl());
				response.setRmName(wfRegionalManagersEntity.getName());
				response.setAmDetails(allAmNames); // Full AM names list
				response.setAmCount(String.valueOf(amCount));
				response.setAmWinners(amWinners); // Selected winners

				// Add to response list
				responses.add(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responses;
	}

}
