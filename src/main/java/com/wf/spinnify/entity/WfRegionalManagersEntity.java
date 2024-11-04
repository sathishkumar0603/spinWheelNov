package com.wf.spinnify.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="WF_REGIONAL_MANAGERS")
public class WfRegionalManagersEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "REGIONAL_MANAGERS_NAME")
	private String name;

	@Column(name = "RM_EMP_CODE")
	private String rmEmpCode;
	
	@Column(name = "URL")
	private String url;

	@Column(name = "CREATED_TIME")
	private String createdTime;
}
