package com.sss.report.dao;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import com.sss.report.core.Utility;

public class CSVDAO {
	
	private String propertyKey;
	
	private String csvFile;
	
	private List<Object> content;
	
	private Set<String> properties;
	
	public CSVDAO(String csvFile, String propertyKey, List<Object> content, Set<String> properties) {
		this.csvFile = csvFile;
		this.content = content;
		this.properties = properties;
		this.propertyKey = propertyKey;
	}

	public Long call() throws Exception {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		long start = System.currentTimeMillis();
		bw.write(Utility.DELIMITTER + Utility.getChildDirName(csvFile));
		bw.newLine();
		for(String property : properties) {
			Object item = null;
			for(Object z : content) {
				if(Utility.isFieldPresent(z, propertyKey, property)) {
					item = z;
					break;
				}
			}
			String line = property + Utility.DELIMITTER;
			if(item != null) {
				line = line + item.toString();
			} 
			bw.write(line);
			bw.newLine();
		}
		long end = System.currentTimeMillis();
		bw.flush();
		byte[] bytes = sw.toString().getBytes();
		Files.write(Paths.get(csvFile), bytes);
		long duration = end - start;
		return duration;
	}

}
