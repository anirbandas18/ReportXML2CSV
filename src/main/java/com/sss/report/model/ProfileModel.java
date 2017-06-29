package com.sss.report.model;

import java.util.List;
import java.util.Map;

public class ProfileModel {
	
	private Map<String,List<String>> fieldPermissions;

	public Map<String, List<String>> getFieldPermissions() {
		return fieldPermissions;
	}

	public void setFieldPermissions(Map<String, List<String>> fieldPermissions) {
		this.fieldPermissions = fieldPermissions;
	}
	
	

}
