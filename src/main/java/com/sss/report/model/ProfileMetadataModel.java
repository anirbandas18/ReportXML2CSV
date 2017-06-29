package com.sss.report.model;

import java.util.Set;

public class ProfileMetadataModel {
	
	private Set<String> fileNames;
	
	private Set<String> fields;
	
	private Set<String> layouts;
	
	private Set<String> objects;
	
	private Set<String> recordTypes;
	
	private Set<String> tabs;
	
	private Set<String> names;

	public Set<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(Set<String> fileNames) {
		this.fileNames = fileNames;
	}

	public Set<String> getFields() {
		return fields;
	}

	public void setFields(Set<String> fields) {
		this.fields = fields;
	}

	public Set<String> getLayouts() {
		return layouts;
	}

	public void setLayouts(Set<String> layouts) {
		this.layouts = layouts;
	}

	public Set<String> getObjects() {
		return objects;
	}

	public void setObjects(Set<String> objects) {
		this.objects = objects;
	}

	public Set<String> getRecordTypes() {
		return recordTypes;
	}

	public void setRecordTypes(Set<String> recordTypes) {
		this.recordTypes = recordTypes;
	}

	public Set<String> getTabs() {
		return tabs;
	}

	public void setTabs(Set<String> tabs) {
		this.tabs = tabs;
	}

	public Set<String> getNames() {
		return names;
	}

	public void setNames(Set<String> names) {
		this.names = names;
	}

	@Override
	public String toString() {
		return "ProfileMetadata [fileNames=" + fileNames + ", fields=" + fields + ", layouts=" + layouts + ", objects="
				+ objects + ", recordTypes=" + recordTypes + ", tabs=" + tabs + ", names=" + names + "]";
	}
	
	

}
