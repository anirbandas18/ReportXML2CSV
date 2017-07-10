package com.sss.report.service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sss.report.core.Utility;
import com.sss.report.dao.DIRDAO;
import com.sss.report.entity.Profile;
import com.sss.report.model.ProfileMetadataModel;
import com.sss.report.model.Properties;
import com.sss.report.model.ReportModel;

public class CSVServiceByProfile {
	
	private String csvRepository;
	
	private ProfileMetadataModel metadata;
	
	private List<Profile> profiles;
	
	public CSVServiceByProfile(String csvRepository, List<Profile> list, ProfileMetadataModel profileMetadataModel) {
		this.metadata = profileMetadataModel;
		this.profiles  = list;
		this.csvRepository = csvRepository;
	}

	public Long call() throws Exception {
		long start = System.currentTimeMillis();
		Path csvRepositoryPath = Utility.createDir(csvRepository);
		Map<Properties,Set<String>> properties = metadata.getPropertiesWithValues();
		for(Properties key : properties.keySet()) {
			String childDirPath = csvRepositoryPath.toString() + File.separator + key.toString();
			System.out.println(childDirPath);
			DIRDAO dirDAO = new DIRDAO(childDirPath, metadata.getPropertyValues(key), profiles);
			Long duration = dirDAO.call();
			System.out.println(childDirPath + " processing took " + duration + " miliseconds");
		}
		long end = System.currentTimeMillis();
		long duration = end - start;
		return duration;
	}

}
