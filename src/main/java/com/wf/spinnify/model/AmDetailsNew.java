package com.wf.spinnify.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class AmDetailsNew {
	private String amName;
	private String amUrl;
	private String code;
}
