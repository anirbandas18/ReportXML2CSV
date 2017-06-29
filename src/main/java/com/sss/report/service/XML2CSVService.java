package com.sss.report.service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import com.sss.report.dao.XMLDAO;
import com.sss.report.entity.Profile;
import com.sss.report.exception.ReportException;

public class XML2CSVService {

	public List<Profile>  parseXML(String xmlFileRepositoryPath) throws ReportException {
		List<Profile> profiles = new ArrayList<>();
		try {
			Path xmlFileRepository = Paths.get(xmlFileRepositoryPath);
			DirectoryStream<Path> xmlFiles = Files.newDirectoryStream(xmlFileRepository);
			for(Path xmlFile : xmlFiles) {
				String xmlFilePath = xmlFile.toString();
				Profile profile = XMLDAO.unmarshall(xmlFilePath);
				profiles.add(profile);
			}
		} catch (IOException | JAXBException | SAXException e) {
			// TODO Auto-generated catch block
			throw new ReportException(e);
		} 
		return profiles;
	}
	
	
	
	public String persistCSV(List<Profile> profiles) {
		String csvFilePath = "";
		
		return csvFilePath;
	}
	
}
