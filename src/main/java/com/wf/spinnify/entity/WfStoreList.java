package com.wf.spinnify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name= "WF_STORE_LIST")
public class WfStoreList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	@Column(name = "STORE_NAME")
	private String storeName;
	@Column(name = "STORE_CODE")
	private String storeCode;
	@Column(name = "ACHIEVEMENT")
	private String achievement;
	@Column(name = "RM_NAME")
	private String rmName;
	@Column(name = "CREATED_DATE")
	private String createdDate;
}
