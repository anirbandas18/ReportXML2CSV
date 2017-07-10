package com.sss.report;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.sss.report.core.Utility;
import com.sss.report.entity.Profile;
import com.sss.report.model.Pair;
import com.sss.report.model.ProfileMetadataModel;
import com.sss.report.model.CSVModel;
import com.sss.report.service.CSVService;
import com.sss.report.service.XMLService;

public class ReportGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String mode = args[0]; // mode = profile or properties
		String xmlRepositoryPath = args[1];// input directory
		String csvRepositoryPath = args[2];// output directory 
		try {
			System.out.println("PARSING : ");
			XMLService xmlService = new XMLService(xmlRepositoryPath);
			ExecutorService xmlExecutor = Executors.newSingleThreadExecutor();
			FutureTask<Pair<List<Profile>,ProfileMetadataModel>> xmlTask = new FutureTask<Pair<List<Profile>,ProfileMetadataModel>>(xmlService);
			xmlExecutor.submit(xmlTask);
			Pair<List<Profile>,ProfileMetadataModel> pair = xmlTask.get();
			xmlExecutor.shutdown();
			System.out.println("PERSISTING by " + mode + " : ");
			ExecutorService csvExecutor = Executors.newSingleThreadExecutor();
			CSVModel reportModel = new CSVModel();
			reportModel.setCsvRepository(csvRepositoryPath);
			reportModel.setMetadata(pair.getB());
			reportModel.setProfiles(pair.getA());
			reportModel.setMode(mode);
			CSVService profileService = new CSVService(reportModel);
			FutureTask<Long> csvTask = new FutureTask<>(profileService);
			csvExecutor.submit(csvTask);
			Long duration = csvTask.get();
			System.out.println("CSV Report generation took " + Utility.milisecondsToSeconds(duration) + " seconds");
			csvExecutor.shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
