package com.sss.report.model;

import java.util.Set;

public class ProfileMetadataModel {
	
	private static final String DELIMITTER = ",";
	
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
		String md = "fields" + DELIMITTER;
		System.out.println("fields");
		for(String field : fields) {
			md = md + field + DELIMITTER;
			System.out.println(field);
		}
		md = md + "layouts" + DELIMITTER;
		System.out.println("layouts");
		for(String layout : layouts) {
			md = md + layout + DELIMITTER;
			System.out.println(layout);
		}
		md = md + "names" + DELIMITTER;
		System.out.println("names");
		for(String name : names) {
			md = md + name + DELIMITTER;
			System.out.println(name);
		}
		md = md + "objects" + DELIMITTER;
		System.out.println("objects");
		for(String object : objects) {
			md = md + object + DELIMITTER;
			System.out.println(object);
		}
		md = md + "recordTypes" + DELIMITTER;
		System.out.println("recordTypes");
		for(String rt : recordTypes) {
			md = md + rt + DELIMITTER;
			System.out.println(rt);
		}
		md = md + "tabs" + DELIMITTER;
		System.out.println("tabs");
		for(String tab : tabs) {
			md = md + tab + DELIMITTER;
			System.out.println(tab);
		}
		md = md.substring(0, md.length() - 1);
		return md;
	}
	
	

}
