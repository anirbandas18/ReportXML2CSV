package com.sss.report.service;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import com.sss.report.dao.XMLDAO;
import com.sss.report.entity.Profile;
import com.sss.report.model.Pair;
import com.sss.report.model.ProfileMetadataModel;

public class XMLService implements Callable<Pair<List<Profile>,ProfileMetadataModel>> {

	private String xmlFileRepositoryPath;
	
	private ProfileMetadataModel metadata;
	
	private List<Profile> profiles;
	
	public XMLService(String xmlFileRepositoryPath) {
		this.xmlFileRepositoryPath = xmlFileRepositoryPath;
		this.metadata = new ProfileMetadataModel();
		this.profiles  = new ArrayList<>();
	}

	@Override
	public Pair<List<Profile>,ProfileMetadataModel> call() throws Exception {
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
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		Pair<List<Profile>,ProfileMetadataModel> pair = new Pair<>();
		pair.setA(profiles);
		pair.setB(metadata);
		return pair;
	}

}
