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
import com.sss.report.model.ProfileMetadataModel;
import com.sss.report.model.Properties;
import com.sss.report.model.ProfileReportModel;

public class CSVService implements Callable<Long>{
	
	private ProfileReportModel reportModel;
	
	public CSVService(ProfileReportModel reportModel) {
		this.reportModel = reportModel;
	}

	@Override
	public Long call() throws Exception {
		long start = System.currentTimeMillis();
		Path csvRepositoryPath = Utility.createDir(reportModel.getCsvRepository());
		ProfileMetadataModel metadata = reportModel.getMetadata();
		Map<Properties,Set<String>> properties = metadata.getPropertiesWithValues();
		int nThreads = properties.size();
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		for(Properties key : properties.keySet()) {
			String childDirPath = csvRepositoryPath.toString() + File.separator + key.toString();
			System.out.println(childDirPath);
			DIRDAO dirDAO = new DIRDAO(childDirPath, metadata.getPropertyValues(key), reportModel.getProfiles());
			FutureTask<Long> dirTask = new FutureTask<>(dirDAO);
			executor.submit(dirTask);
			Long duration = dirTask.get();
			System.out.println(childDirPath + " processing took " + duration + " miliseconds");
		}
		executor.shutdown();
		long end = System.currentTimeMillis();
		long duration = end - start;
		return duration;
	}

}