package com.sss.report.model;

import java.util.List;
import java.util.Set;

import com.sss.report.entity.Profile;

public class ProfileReportModel {
	
	private String mode;
	
	private String csvRepository;
	
	private ProfileMetadataModel metadata;
	
	private List<Profile> profiles;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCsvRepository() {
		return csvRepository;
	}

	public void setCsvRepository(String csvRepository) {
		this.csvRepository = csvRepository;
	}

	public ProfileMetadataModel getMetadata() {
		return metadata;
	}

	public void setMetadata(ProfileMetadataModel metadata) {
		this.metadata = metadata;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
	

}
