package com.sss.report.dao;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.sss.report.core.Utility;
import com.sss.report.entity.Profile;

public class DIRDAO implements Callable<Long>{
	
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
	
	@Override
	public Long call() throws Exception {
		int nThreads = profiles.size();
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		long start = System.currentTimeMillis();
		Path childDirPath = Utility.createDir(childDir);
		for(Profile profile : profiles) {
			String csvFileName = getEquivalentCSVFileName(profile.getFileName());
			String csvFile = childDirPath.toString() + File.separator + csvFileName;
			List<Object> content = Utility.getFieldByName(profile, Utility.getChildDirName(childDirPath.toString()));
			Path csvFilePath = Paths.get(csvFile);
			String propertyKey = Utility.getChildDirName(csvFilePath.getParent().toString());
			CSVDAO csvDAO = new CSVDAO(csvFile, propertyKey, content, propertyValues);
			FutureTask<Long> csvTask = new FutureTask<>(csvDAO);
			executor.submit(csvTask);
			Long duration = csvTask.get();
			System.out.println(csvFile + " processing took " + duration + " miliseconds");
		}
		executor.shutdown();
		long end = System.currentTimeMillis();
		long duration = end - start;
		return duration;
	}
	
}
