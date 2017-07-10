package com.sss.report.service;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.sss.report.core.Utility;
import com.sss.report.dao.DIRDAO;
import com.sss.report.model.CSVModel;
import com.sss.report.model.DirectoryModel;
import com.sss.report.model.ProfileMetadataModel;
import com.sss.report.model.Properties;

public class CSVService implements Callable<Long>{
	
	private CSVModel reportModel;
	
	public CSVService(CSVModel reportModel) {
		this.reportModel = reportModel;
	}

	@Override
	public Long call() throws Exception {
		long start = System.currentTimeMillis();
		Path csvRepositoryPath = Utility.createDir(reportModel.getCsvRepository());
		reportModel.getMode();
		ProfileMetadataModel metadata = reportModel.getMetadata();
		Map<Properties,Set<String>> properties = metadata.getPropertiesWithValues();
		int nThreads = properties.size();
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		for(Properties key : properties.keySet()) {
			String childDirPath = csvRepositoryPath.toString() + File.separator + key.toString();
			Set<String> propertyValues = metadata.getPropertyValues(key);
			DirectoryModel directoryModel = new DirectoryModel();
			directoryModel.setChildDir(childDirPath);
			directoryModel.setMode(reportModel.getMode());
			directoryModel.setProfiles(reportModel.getProfiles());
			directoryModel.setPropertyValues(propertyValues);
			DIRDAO dirDAO = new DIRDAO(directoryModel);
			FutureTask<Long> dirTask = new FutureTask<>(dirDAO);
			executor.submit(dirTask);
			Long duration = dirTask.get();
			System.out.println(childDirPath + " with " + propertyValues.size() + " files processing took " + Utility.milisecondsToSeconds(duration) + " seconds");
		}
		executor.shutdown();
		long end = System.currentTimeMillis();
		long duration = end - start;
		return duration;
	}

}
