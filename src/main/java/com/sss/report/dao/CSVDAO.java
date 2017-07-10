package com.sss.report.dao;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import com.sss.report.core.Utility;
import com.sss.report.model.PersistenceReport;

public class CSVDAO implements Callable<PersistenceReport>{
	
	private String propertyKey;
	
	private String csvFileNameWithoutExt;
	
	private List<Object> content;
	
	private Set<String> properties;
	
	public CSVDAO(String csvFileNameWithoutExt, String propertyKey, List<Object> content, Set<String> properties) {
		this.csvFileNameWithoutExt = csvFileNameWithoutExt;
		this.content = content;
		this.properties = properties;
		this.propertyKey = propertyKey;
	}	

	@Override
	public PersistenceReport call() throws Exception {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		long start = System.currentTimeMillis();
		bw.write(Utility.DELIMITTER + Utility.getChildDirName(csvFileNameWithoutExt));
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
		String csvFilePath = Utility.getEquivalentCSVFileName(csvFileNameWithoutExt);
		Files.write(Paths.get(csvFilePath), bytes);
		long duration = end - start;
		PersistenceReport pr = new PersistenceReport();
		pr.setDuration(duration);
		pr.setSize(Utility.bytesToLong(bytes));
		return pr;
	}

}
