package com.sss.report.dao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
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

	@Override
	public Long call() throws Exception {
		int nThreads = directoryModel.getProfiles().size();
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		long start = System.currentTimeMillis();
		Path childDirPath = Utility.createDir(directoryModel.getChildDir());
		for(Profile profile : directoryModel.getProfiles()) {
			Path csvFileNameWithoutExt = Paths.get(childDirPath.toString(), profile.getFileName());
			String fieldName = Utility.getChildDirName(childDirPath.toString());
			List<Object> content = Utility.getFieldByName(profile, fieldName);
			String propertyKey = Utility.getChildDirName(csvFileNameWithoutExt.getParent().toString());
			ReportModel reportModel = new ReportModel();
			reportModel.setContent(content);
			reportModel.setCsvFileNameWithoutExt(csvFileNameWithoutExt.toString());
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
	
}
