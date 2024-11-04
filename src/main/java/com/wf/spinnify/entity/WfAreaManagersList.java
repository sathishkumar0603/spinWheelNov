package com.wf.spinnify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name= "WF_AREA_MANAGER_LIST")
public class WfAreaManagersList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	@Column(name = "AM_NAME")
	private String amName;
	@Column(name = "AM_EMP_CODE")
	private String amEmpCode;
	@Column(name = "ACHIEVEMENT")
	private String achievement;
	@Column(name = "URL")
	private String url;
	@Column(name = "RM_NAME")
	private String rmName;
	@Column(name = "CREATED_DATE")
	private String createdDate;
}
