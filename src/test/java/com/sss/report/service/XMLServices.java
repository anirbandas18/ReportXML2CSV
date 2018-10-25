package com.sss.report.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.xml.stream.XMLInputFactory;

import com.sss.report.core.XMLParser;
import com.sss.report.dao.ProfileDAO;
import com.sss.report.entity.FieldPermissionsEntity;
import com.sss.report.entity.ProfileEntity;

public class XMLServices {

	
	public Set<ProfileEntity> parseProfiles(String xmlRepositoryLocation) throws IOException, InterruptedException, ExecutionException {
		Set<ProfileEntity> profileSet = new TreeSet<>(); 
		Path xmlRepositoryPath = Paths.get(xmlRepositoryLocation);
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
		ExecutorService threadPool = Executors.newCachedThreadPool();
		ProfileDAO profileDAO = new ProfileDAO();
		DirectoryStream<Path> xmlFilesFromRepository = Files.newDirectoryStream(xmlRepositoryPath);
		for(Path xmlFile : xmlFilesFromRepository) {
			String xmlFilePath = xmlFile.toString();
			XMLParser xmlParser = new XMLParser(xmlInputFactory, xmlFilePath);
			FutureTask<Set<FieldPermissionsEntity>> xmlParsingTask = new FutureTask<>(xmlParser);
			threadPool.submit(xmlParsingTask);
			Set<FieldPermissionsEntity> fieldPermissionsSet = xmlParsingTask.get();
			ProfileEntity profile = new ProfileEntity();
			String xmlFileName = xmlFilePath.substring(xmlFilePath.indexOf(File.separatorChar) + 1);
			profile.setName(xmlFileName);
			profile.setFieldPermissions(fieldPermissionsSet);
			Boolean flag = profileDAO.create(profile);
			profileSet.add(profile);
			//System.err.println(flag + " " + profile);
		}
		threadPool.shutdown();
		return profileSet;
	}
	
}
