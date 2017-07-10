/*package com.sss.report.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.sss.report.dao.DIRDAO;
import com.sss.report.dao.XMLDAO;
import com.sss.report.entity.Profile;
import com.sss.report.exception.ReportException;
import com.sss.report.model.ProfileMetadataModel;
import com.sss.report.model.Properties;
import com.sss.report.model.ReportModel;

public class XML2CSVService {
	
	private ProfileMetadataModel metadata;
	
	private List<Profile> profiles;
	
	public XML2CSVService() {
		this.metadata = new ProfileMetadataModel();
		this.profiles  = new ArrayList<>();
	}

	public void parseXML(String xmlFileRepositoryPath) throws ReportException {
		//Pair<List<Profile>,ProfileMetadataModel> pair = new Pair<>();
		try {
			Path xmlFileRepository = Paths.get(xmlFileRepositoryPath);
			DirectoryStream<Path> xmlFiles = Files.newDirectoryStream(xmlFileRepository);
			int noOfFiles = xmlFileRepository.getNameCount();
			ExecutorService executor = Executors.newFixedThreadPool(noOfFiles);
			for(Path xmlFile : xmlFiles) {
				String xmlFilePath = xmlFile.toString();
				XMLDAO xmlDAO = new XMLDAO(xmlFilePath, metadata);
				FutureTask<Profile> unmarshallTask = new FutureTask<>(xmlDAO);
				executor.execute(unmarshallTask);
				Profile profile = unmarshallTask.get();
				//System.out.println(profile.getFileName());
				profiles.add(profile);
			}
			executor.shutdown();
			pair.setA(profiles);
			pair.setB(metadata);
		} catch (IOException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			throw new ReportException(e);
		} 
		//return pair;
	}
	
	public void persistCSVByProfile(String csvRepositoryPath) throws ReportException {
		try {
			int nThreads = metadata.getPropertiesWithValues().size();
			ExecutorService executor = Executors.newFixedThreadPool(nThreads);
			long start = System.currentTimeMillis();
			for(Properties key : metadata.getPropertiesWithValues().keySet()) {
				ReportModel rm = new ReportModel();
				rm.setChildDirPath(csvRepositoryPath + File.separator + key.toString());
				System.out.println(rm.getChildDirPath());
				rm.setProperties(metadata.getPropertyValues(key));
				rm.setProfiles(profiles);
				FutureTask<Long> dirTask = new FutureTask<>(new DIRDAO(rm));	
				executor.submit(dirTask);
				Long duration = dirTask.get();
				System.out.println(rm.getChildDirPath() + " processing took " + duration + " miliseconds");
			}
			executor.shutdown();
			long end = System.currentTimeMillis();
			long duration = end - start;
			System.out.println("Report generation took " + duration + " miliseconds");
		} catch (InterruptedException | ExecutionException e) {
			throw new ReportException(e);
		}
	}
		
	public void persistCSV(List<Profile> profiles, ProfileMetadataModel metadata) {
		
	}
	
}
*/