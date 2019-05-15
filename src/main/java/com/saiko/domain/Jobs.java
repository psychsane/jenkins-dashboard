package com.saiko.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(includeFieldNames = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Jobs {

	@JsonProperty("_class")
	private String type;

	@JsonProperty("jobs")
	private Job[] allJobs;

	private String totalDuration;
	private String totalPassed;
	private String totalFailed;
	private int count;
}
