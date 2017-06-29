package com.sss.report.service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import com.sss.report.dao.XMLDAO;
import com.sss.report.entity.FieldPermission;
import com.sss.report.entity.LayoutAssignment;
import com.sss.report.entity.ObjectPermission;
import com.sss.report.entity.Profile;
import com.sss.report.entity.RecordTypeVisibility;
import com.sss.report.entity.TabVisibility;
import com.sss.report.entity.UserPermission;
import com.sss.report.exception.ReportException;
import com.sss.report.model.ProfileMetadataModel;

public class XML2CSVService {

	public List<Profile>  parseXML(String xmlFileRepositoryPath) throws ReportException {
		List<Profile> profiles = new ArrayList<>();
		try {
			Path xmlFileRepository = Paths.get(xmlFileRepositoryPath);
			DirectoryStream<Path> xmlFiles = Files.newDirectoryStream(xmlFileRepository);
			for(Path xmlFile : xmlFiles) {
				String xmlFilePath = xmlFile.toString();
				Profile profile = XMLDAO.unmarshall(xmlFilePath);
				System.out.println(profile.getFileName());
				profiles.add(profile);
			}
		} catch (IOException | JAXBException | SAXException e) {
			// TODO Auto-generated catch block
			throw new ReportException(e);
		} 
		return profiles;
	}
	
	
	public ProfileMetadataModel getProfileMetadata(List<Profile> profiles) {
		ProfileMetadataModel pmd = new ProfileMetadataModel();
		Set<String> fileNames = new TreeSet<>();
		Set<String> fields = new TreeSet<>();
		Set<String> layouts = new TreeSet<>();
		Set<String> objects = new TreeSet<>();
		Set<String> recordTypes = new TreeSet<>();
		Set<String> tabs = new TreeSet<>();
		Set<String> names = new TreeSet<>();
		Set<String> set = new TreeSet<>();
		for(Profile p : profiles) {
			fileNames.add(p.getFileName());
			List<FieldPermission> fieldPermissions = p.getFieldPermissions();
			set.clear();
			set = fieldPermissions.stream().map(FieldPermission::getField).collect(Collectors.toSet());
			fields.addAll(set);
			List<LayoutAssignment> layoutAssignments = p.getLayoutAssignments();
			set.clear();
			set = layoutAssignments.stream().map(LayoutAssignment::getLayout).collect(Collectors.toSet());
			layouts.addAll(set);
			List<ObjectPermission> objectPermissions = p.getObjectPermissions();
			set.clear();
			set = objectPermissions.stream().map(ObjectPermission::getObject).collect(Collectors.toSet());
			objects.addAll(set);
			List<RecordTypeVisibility> recordTypeVisibilities = p.getRecordTypeVisibilities();
			set.clear();
			set = recordTypeVisibilities.stream().map(RecordTypeVisibility::getRecordType).collect(Collectors.toSet());
			recordTypes.addAll(set);
			List<TabVisibility> tabVisibilities = p.getTabVisibilities();
			set.clear();
			set = tabVisibilities.stream().map(TabVisibility::getTab).collect(Collectors.toSet());
			tabs.addAll(set);
			List<UserPermission> userPermissions = p.getUserPermissions();
			set.clear();
			set = userPermissions.stream().map(UserPermission::getName).collect(Collectors.toSet());
			names.addAll(set);
		}
		pmd.setFields(fields);
		pmd.setFileNames(fileNames);
		pmd.setLayouts(layouts);
		pmd.setNames(names);
		pmd.setObjects(objects);
		pmd.setRecordTypes(recordTypes);
		pmd.setTabs(tabs);
		return pmd;
	}
	
	public void persistCSV(List<Profile> profiles, ProfileMetadataModel metadata) {
		
	}
	
}
