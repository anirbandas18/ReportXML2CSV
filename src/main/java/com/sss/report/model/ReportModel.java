package com.sss.report.model;

import java.util.List;
import java.util.Set;

import com.sss.report.entity.Profile;

public class ReportModel {
	
	private String childDirPath;
	
	private Set<String> properties;
	
	private List<Profile> profiles;

	public String getChildDirPath() {
		return childDirPath;
	}

	public void setChildDirPath(String childDirPath) {
		this.childDirPath = childDirPath;
	}

	public Set<String> getProperties() {
		return properties;
	}

	public void setProperties(Set<String> properties) {
		this.properties = properties;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	

}
