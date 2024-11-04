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
@Table(name="WF_AREA_MAANGERS_WINNERS")
public class WfAreaManagerWinners {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	@Column(name = "WINNER_AM_NAME")
	private String winnerAmName;
	@Column(name = "AM_EMP_CODE")
	private String amEmpCode;
	@Column(name = "RM_NAME")
	private String rmName;
	@Column(name = "CREATED_DATE")
	private String createdDate;

}
