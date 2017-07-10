package com.sss.report.model;

import java.util.List;
import java.util.Set;

public class ReportModel {
	
	private String propertyKey;
	
	private String csvFileNameWithoutExt;
	
	private List<Object> content;
	
	private Set<String> properties;

	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getCsvFileNameWithoutExt() {
		return csvFileNameWithoutExt;
	}

	public void setCsvFileNameWithoutExt(String csvFileNameWithoutExt) {
		this.csvFileNameWithoutExt = csvFileNameWithoutExt;
	}

	public List<Object> getContent() {
		return content;
	}

	public void setContent(List<Object> content) {
		this.content = content;
	}

	public Set<String> getProperties() {
		return properties;
	}

	public void setProperties(Set<String> properties) {
		this.properties = properties;
	}
	
	

}
