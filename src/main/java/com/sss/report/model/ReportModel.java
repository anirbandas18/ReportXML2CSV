package com.sss.report.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ReportModel {
	
	private String propertyKey;
	
	private String reportNameWithoutExt;
	
	private List<? extends Object> content;
	
	private Collection<? extends Object> properties;

	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}
	
	public String getReportNameWithoutExt() {
		return reportNameWithoutExt;
	}

	public void setReportNameWithoutExt(String reportNameWithoutExt) {
		this.reportNameWithoutExt = reportNameWithoutExt;
	}

	public List<? extends Object> getContent() {
		return content;
	}

	public void setContent(List<? extends Object> content) {
		this.content = content;
	}

	public Collection<? extends Object> getProperties() {
		return properties;
	}

	public void setProperties(Collection<? extends Object> properties) {
		this.properties = properties;
	}
	
	

}
