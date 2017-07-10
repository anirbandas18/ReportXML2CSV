package com.sss.report.dao;

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
import com.sss.report.model.PersistenceReport;

public class DIRDAO implements Callable<Long>{
	
	private String childDir;
	private Set<String> propertyValues;
	private List<Profile> profiles;
	
	public DIRDAO(String childDir, Set<String> propertyValues, List<Profile> profiles) {
		this.childDir = childDir;
		this.propertyValues = propertyValues;
		this.profiles = profiles;
	}

	@Override
	public Long call() throws Exception {
		int nThreads = profiles.size();
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		long start = System.currentTimeMillis();
		Path childDirPath = Utility.createDir(childDir);
		for(Profile profile : profiles) {
			Path csvFileNameWithoutExt = Paths.get(childDirPath.toString(), profile.getFileName());
			String fieldName = Utility.getChildDirName(childDirPath.toString());
			List<Object> content = Utility.getFieldByName(profile, fieldName);
			String propertyKey = Utility.getChildDirName(csvFileNameWithoutExt.getParent().toString());
			CSVDAO csvDAO = new CSVDAO(csvFileNameWithoutExt.toString(), propertyKey, content, propertyValues);
			FutureTask<PersistenceReport> csvTask = new FutureTask<>(csvDAO);
			executor.submit(csvTask);
			PersistenceReport pr = csvTask.get();
			String resultantFileName = Utility.getEquivalentCSVFileName(csvFileNameWithoutExt.toString());
			System.out.println(resultantFileName + " of size " + Utility.humanReadableByteCount(pr.getSize()) + " bytes took " + Utility.milisecondsToSeconds(pr.getDuration()) + " miliseconds to process");
		}
		executor.shutdown();
		long end = System.currentTimeMillis();
		long duration = end - start;
		return duration;
	}
	
}
