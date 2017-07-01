package com.sss.report.model;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ProfileMetadataModel {
	
	private static final String DELIMITTER = ",";
	
	private final ReentrantReadWriteLock rwl;
	
	private final Set<String> fileNames;
	
	private final Set<String> fields;
	
	private final Set<String> layouts;
	
	private final Set<String> objects;
	
	private final Set<String> recordTypes;
	
	private final Set<String> tabs;
	
	private final Set<String> names;

	public ProfileMetadataModel() {
		this.rwl = new ReentrantReadWriteLock();
		this.fields = new TreeSet<>();
		this.fileNames = new TreeSet<>();
		this.layouts  = new TreeSet<>();
		this.objects = new TreeSet<>();
		this.recordTypes = new TreeSet<>();
		this.tabs = new TreeSet<>();
		this.names = new TreeSet<>();
	}
	
	public void addFileNames(Set<String> fileNames) {
		this.rwl.writeLock().lock();
		this.fileNames.addAll(fileNames);
		this.rwl.writeLock().unlock();
	}
	
	public Set<String> getFileNames() {
		return fileNames;
	}

	public void addFields(Set<String> fields) {
		this.rwl.writeLock().lock();
		this.fileNames.addAll(fields);
		this.rwl.writeLock().unlock();
	}
	
	public Set<String> getFields() {
		return fields;
	}

	public void addLayouts(Set<String> layouts) {
		this.rwl.writeLock().lock();
		this.layouts.addAll(layouts);
		this.rwl.writeLock().unlock();
	}

	public Set<String> getLayouts() {
		return layouts;
	}

	public void addObjects(Set<String> objects) {
		this.rwl.writeLock().lock();
		this.objects.addAll(objects);
		this.rwl.writeLock().unlock();
	}

	public Set<String> getObjects() {
		return objects;
	}

	public void addRecordTypes(Set<String> recordTypes) {
		this.rwl.writeLock().lock();
		this.recordTypes.addAll(recordTypes);
		this.rwl.writeLock().unlock();
	}

	public Set<String> getRecordTypes() {
		return recordTypes;
	}

	public void addTabs(Set<String> tabs) {
		this.rwl.writeLock().lock();
		this.tabs.addAll(tabs);
		this.rwl.writeLock().unlock();
	}

	public Set<String> getTabs() {
		return tabs;
	}

	public void addNames(Set<String> names) {
		this.rwl.writeLock().lock();
		this.names.addAll(names);
		this.rwl.writeLock().unlock();
	}
 
	public Set<String> getNames() {
		return names;
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
