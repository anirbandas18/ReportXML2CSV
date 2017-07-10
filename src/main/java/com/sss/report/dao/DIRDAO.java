package com.sss.report.dao;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.sss.report.core.Utility;
import com.sss.report.entity.Profile;
import com.sss.report.model.DirectoryModel;
import com.sss.report.model.PersistenceReport;
import com.sss.report.model.ReportModel;

public class DIRDAO implements Callable<Long>{
	
	private DirectoryModel directoryModel;
	
	public DIRDAO(DirectoryModel directoryModel) {
		this.directoryModel = directoryModel;
	}

	private Long persistByProperties() throws IOException, InterruptedException, ExecutionException {
		int nThreads = directoryModel.getPropertyValues().size();
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		long start = System.currentTimeMillis();
		Path childDirPath = Utility.createDir(directoryModel.getChildDir());
		for(String property : directoryModel.getPropertyValues()) {
			Path csvFileNameWithoutExt = Paths.get(childDirPath.toString(), property);
			//String fieldName = Utility.getChildDirName(childDirPath.toString());
			String propertyKey = Utility.getChildDirName(csvFileNameWithoutExt.getParent().toString());
			ReportModel reportModel = new ReportModel();
			reportModel.setContent(new ArrayList<>(directoryModel.getPropertyValues()));//
			reportModel.setReportNameWithoutExt(csvFileNameWithoutExt.toString());
			reportModel.setProperties(new TreeSet<>(directoryModel.getProfiles()));//
			reportModel.setPropertyKey(propertyKey);
			CSVDAO csvDAO = new CSVDAO(reportModel);
			FutureTask<PersistenceReport> csvTask = new FutureTask<>(csvDAO);
			executor.submit(csvTask);
			PersistenceReport pr = csvTask.get();
			String resultantFileName = Utility.getEquivalentCSVFileName(csvFileNameWithoutExt.toString());
			System.out.println(resultantFileName + " of size " + Utility.humanReadableByteCount(pr.getSize()) + " took " + Utility.milisecondsToSeconds(pr.getDuration()) + " seconds to process");
		}
		executor.shutdown();
		long end = System.currentTimeMillis();
		long duration = end - start;
		return duration;
	}
	
	private Long persistByProfile() throws IOException, IllegalArgumentException, IllegalAccessException, InterruptedException, ExecutionException {
		int nThreads = directoryModel.getProfiles().size();
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		long start = System.currentTimeMillis();
		Path childDirPath = Utility.createDir(directoryModel.getChildDir());
		for(Profile profile : directoryModel.getProfiles()) {
			Path csvFileNameWithoutExt = Paths.get(childDirPath.toString(), profile.getFileName());
			String fieldName = Utility.getChildDirName(childDirPath.toString());
			List<Object> content = Utility.getComplexFieldByName(profile, fieldName);
			String propertyKey = Utility.getChildDirName(csvFileNameWithoutExt.getParent().toString());
			ReportModel reportModel = new ReportModel();
			reportModel.setContent(content);
			reportModel.setReportNameWithoutExt(csvFileNameWithoutExt.toString());
			reportModel.setProperties(directoryModel.getPropertyValues());
			reportModel.setPropertyKey(propertyKey);
			CSVDAO csvDAO = new CSVDAO(reportModel);
			FutureTask<PersistenceReport> csvTask = new FutureTask<>(csvDAO);
			executor.submit(csvTask);
			PersistenceReport pr = csvTask.get();
			String resultantFileName = Utility.getEquivalentCSVFileName(csvFileNameWithoutExt.toString());
			System.out.println(resultantFileName + " of size " + Utility.humanReadableByteCount(pr.getSize()) + " took " + Utility.milisecondsToSeconds(pr.getDuration()) + " seconds to process");
		}
		executor.shutdown();
		long end = System.currentTimeMillis();
		long duration = end - start;
		return duration;
	}
	
	@Override
	public Long call() throws Exception {
		Long duration = null;
		switch (directoryModel.getMode()) {
		case Utility.PROFILE:
			persistByProfile();
			break;
		case Utility.PROPERTIES:
			persistByProperties();
			break;
		}
		return duration;
	}
	
}
