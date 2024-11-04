package com.wf.spinnify.model;

import java.util.List;

import lombok.Data;

@Data
public class AmWinnersResponse {
	private String empCode;
	private String url;
	private String rmName;
	private List<String> amDetails;
	private String amCount;
	private List<AmDetails> amWinners;
}
