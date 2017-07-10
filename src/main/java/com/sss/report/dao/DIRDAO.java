package com.sss.report.dao;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import com.sss.report.core.Utility;
import com.sss.report.entity.Profile;

public class DIRDAO {
	
	private String childDir;
	private Set<String> propertyValues;
	private List<Profile> profiles;
	
	public DIRDAO(String childDir, Set<String> propertyValues, List<Profile> profiles) {
		this.childDir = childDir;
		this.propertyValues = propertyValues;
		this.profiles = profiles;
	}

	private String getEquivalentCSVFileName(String fileName) {
		String tokens[] = fileName.split("\\.");
		String csvFileName = tokens[0];
		csvFileName = csvFileName + ".csv";
		return csvFileName;
	}
	
	public Long call() throws Exception {
		Path childDirPath = Utility.createDir(childDir);
		long start = System.currentTimeMillis();
		for(Profile profile : profiles) {
			String csvFileName = getEquivalentCSVFileName(profile.getFileName());
			String csvFile = childDirPath.toString() + File.separator + csvFileName;
			List<Object> content = Utility.getFieldByName(profile, Utility.getChildDirName(childDirPath.toString()));
			Path csvFilePath = Paths.get(csvFile);
			String propertyKey = Utility.getChildDirName(csvFilePath.getParent().toString());
			CSVDAO csvDAO = new CSVDAO(csvFile, propertyKey, content, propertyValues);
			Long duration = csvDAO.call();
			System.out.println(csvFile + " processing took " + duration + " miliseconds");
		}
		long end = System.currentTimeMillis();
		long duration = end - start;
		return duration;
	}
	
}
