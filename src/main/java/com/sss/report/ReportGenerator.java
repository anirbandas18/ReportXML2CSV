package com.sss.report;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.sss.report.core.Utility;
import com.sss.report.entity.Profile;
import com.sss.report.model.Pair;
import com.sss.report.model.ProfileMetadataModel;
import com.sss.report.service.CSVServiceByProfile;
import com.sss.report.service.CSVServiceByProperties;
import com.sss.report.service.XMLService;

public class ReportGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String mode = args[0]; // mode = profile or properties
		String xmlRepositoryPath = args[1];// input directory
		String csvRepositoryPath = args[2];// output directory 
		try {
			XMLService xmlService = new XMLService(xmlRepositoryPath);
			ExecutorService xmlExecutor = Executors.newSingleThreadExecutor();
			FutureTask<Pair<List<Profile>,ProfileMetadataModel>> xmlTask = new FutureTask<Pair<List<Profile>,ProfileMetadataModel>>(xmlService);
			xmlExecutor.submit(xmlTask);
			Pair<List<Profile>,ProfileMetadataModel> pair = xmlTask.get();
			xmlExecutor.shutdown();
			System.out.println(pair.getA().size());
			System.out.println(pair.getB().getPropertiesWithValues().size());
			ExecutorService csvExecutor = Executors.newSingleThreadExecutor();
			FutureTask<Long> csvTask = null;
			if(mode.equalsIgnoreCase(Utility.PROFILE)) {
				CSVServiceByProfile profileService = new CSVServiceByProfile(csvRepositoryPath, pair.getA(), pair.getB());
				csvTask = new FutureTask<>(profileService);
			} else if(mode.equalsIgnoreCase(Utility.PROPERTIES)) {
				CSVServiceByProperties propertiesService = new CSVServiceByProperties();
				csvTask = new FutureTask<>(propertiesService);
			}
			csvExecutor.submit(csvTask);
			Long duration = csvTask.get();
			System.out.println("CSV Report generation took " + duration + " miliseconds");
			csvExecutor.shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
