package com.wf.spinnify.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.wf.spinnify.model.AmWinnersResponse;
import com.wf.spinnify.model.StoreWinnersResponse;
import com.wf.spinnify.model.WfStoreWinnersListDownload;
import com.wf.spinnify.service.SpinService;

@RestController
@RequestMapping("/spinnify")
@CrossOrigin
public class SpinController {

	@Autowired
	SpinService spinService;

	@PostMapping("/")
	public String sample(@RequestBody String yh) {
		return "Working";
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("rm") MultipartFile rm, @RequestParam("am") MultipartFile am,
			@RequestParam("store") MultipartFile store,@RequestParam("rmForAm") MultipartFile rmForAm) {
		try {
			String listResponse = spinService.extractData(rm, am, store,rmForAm);
			if (!(listResponse == null)) {
				return new ResponseEntity<>(listResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(listResponse, HttpStatus.NOT_IMPLEMENTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getStoreWinners")
	public ResponseEntity<List<StoreWinnersResponse>> getStoreWinners() {
		try {
			List<StoreWinnersResponse> listResponse = spinService.getAllWinners();
			if (!(listResponse == null)) {
				return new ResponseEntity<>(listResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(listResponse, HttpStatus.NOT_IMPLEMENTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAmWinners")
	public ResponseEntity<List<AmWinnersResponse>> getAmWinners() {
		try {
			List<AmWinnersResponse> listResponse = spinService.getAllAmWinners();
			if (!(listResponse == null)) {
				return new ResponseEntity<>(listResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(listResponse, HttpStatus.NOT_IMPLEMENTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/downloadStoreWinners")
	public ResponseEntity<WfStoreWinnersListDownload> downloadStoreWinner() {
		WfStoreWinnersListDownload listResponse = null;
		try {
			listResponse = spinService.downloadStoreCsv();
			if (listResponse != null) {
				return new ResponseEntity<>(listResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(listResponse, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/downloadAmCsv")
	public ResponseEntity<WfStoreWinnersListDownload> download() {
		WfStoreWinnersListDownload listResponse = null;
		try {
			listResponse = spinService.downloadAmWinnersCsv();
			if (listResponse != null) {
				return new ResponseEntity<>(listResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(listResponse, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


}
