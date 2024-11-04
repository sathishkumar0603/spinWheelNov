package com.wf.spinnify.model;

import java.util.List;

import lombok.Data;

@Data
public class StoreWinnersResponse {
	
	private String empCode;
	private String url;
	private String rmName;
	private List<String> storeName;
	private String storeCount;
	private List<String> storeWinners;
}
