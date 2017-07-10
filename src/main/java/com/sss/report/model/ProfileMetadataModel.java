package com.sss.report.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ProfileMetadataModel {
	
	private final ReentrantReadWriteLock rwl;
	
	private final Map<Properties,Set<String>> properties;

	public ProfileMetadataModel() {
		this.properties = new TreeMap<>();
		this.rwl = new ReentrantReadWriteLock();
		/*this.fields = new TreeSet<>();
		this.fileNames = new TreeSet<>();
		this.layouts  = new TreeSet<>();
		this.objects = new TreeSet<>();
		this.recordTypes = new TreeSet<>();
		this.tabs = new TreeSet<>();
		this.names = new TreeSet<>();*/
	}
	
	public void setPropertyValues(Properties key, Set<String> value) {
		this.rwl.writeLock().lock();
		Set<String> values = this.properties.containsKey(key) ? this.properties.get(key) : new TreeSet<>();
		values.addAll(value);
		this.properties.put(key, values);
		this.rwl.writeLock().unlock();
	}
	
	public Map<Properties,Set<String>> getPropertiesWithValues() {
		return this.properties;
	}
	
	public Set<String> getPropertyValues(Properties key) {
		this.rwl.readLock().lock();
		Set<String> values = this.properties.containsKey(key) ? this.properties.get(key) : new TreeSet<>();
		this.rwl.readLock().unlock();
		return values;
	}

}
