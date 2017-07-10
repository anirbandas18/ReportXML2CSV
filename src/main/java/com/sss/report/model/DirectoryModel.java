package com.sss.report.model;

import java.util.List;
import java.util.Set;

import com.sss.report.entity.Profile;

public class DirectoryModel {
	
	private String mode;
	private String childDir;
	private Set<String> propertyValues;
	private List<Profile> profiles;
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getChildDir() {
		return childDir;
	}
	public void setChildDir(String childDir) {
		this.childDir = childDir;
	}
	public Set<String> getPropertyValues() {
		return propertyValues;
	}
	public void setPropertyValues(Set<String> propertyValues) {
		this.propertyValues = propertyValues;
	}
	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
	
	

}
