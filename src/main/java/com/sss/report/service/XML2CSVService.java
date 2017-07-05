package com.sss.report.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

	private ProfileMetadataModel pmd;

	public XML2CSVService() {
		this.pmd = new ProfileMetadataModel();
	}

	public List<Profile> parseXML(String xmlFileRepositoryPath) throws ReportException {
		List<Profile> profiles = new ArrayList<>();
		try {
			Path xmlFileRepository = Paths.get(xmlFileRepositoryPath);
			DirectoryStream<Path> xmlFiles = Files.newDirectoryStream(xmlFileRepository);
			for (Path xmlFile : xmlFiles) {
				String xmlFilePath = xmlFile.toString();
				Profile profile = XMLDAO.unmarshall(xmlFilePath);
				// System.out.println(profile.getFileName());
				profiles.add(profile);
			}
			getProfileMetadata(profiles);
		} catch (IOException | JAXBException | SAXException e) {
			// TODO Auto-generated catch block
			throw new ReportException(e);
		}
		return profiles;
	}

	private void getProfileMetadata(List<Profile> profiles) {
		Set<String> fileNames = new TreeSet<>();
		Set<String> fields = new TreeSet<>();
		Set<String> layouts = new TreeSet<>();
		Set<String> objects = new TreeSet<>();
		Set<String> recordTypes = new TreeSet<>();
		Set<String> tabs = new TreeSet<>();
		Set<String> names = new TreeSet<>();
		Set<String> set = new TreeSet<>();
		fileNames.add("\t");
		for (Profile p : profiles) {
			System.out.println(p.getFileName());
			fileNames.add(p.getFileName());
			List<FieldPermission> fieldPermissions = p.getFieldPermissions();
			set.clear();
			set = fieldPermissions.stream().map(FieldPermission::getField).collect(Collectors.toSet());
			fields.addAll(set);
			List<LayoutAssignment> layoutAssignments = p.getLayoutAssignments();
			set.clear();
			set = layoutAssignments.stream().map(LayoutAssignment::getLayout).collect(Collectors.toSet());
			layouts.addAll(set);
			System.out.println(p.getObjectPermissions().size());
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
	}

	public void persistCSV(List<Profile> profiles, String csvReportRepositoryPath) throws IOException {
		File csvReportDir = new File(csvReportRepositoryPath);
		if (!csvReportDir.exists()) {
			csvReportDir.mkdir();
		}
		Long start = null, end = null;
		String c = ",";
		String fileExt = ".csv";
		String line = "";
		// Header
		String header = pmd.getFileNames().toString();
		header = header.substring(1, header.length() - 1);

		// Write header
		File fieldPermissionReport = new File(csvReportRepositoryPath + File.separator + "FieldPermissions" + fileExt);
		start = System.currentTimeMillis();
		BufferedWriter fpw = new BufferedWriter(new FileWriter(fieldPermissionReport));
		fpw.write(header);
		fpw.newLine();
		line = "";
		// Write Each tag type
		for (String str : pmd.getFields()) {
			line = str + c;
			for (Profile p : profiles) {
				List<FieldPermission> fpList = p.getFieldPermissions();
				FieldPermission fp = fpList.stream().filter(x -> x.getField().equals(str)).findAny().orElse(null);
				String value = fp != null ? fp.toString() : "";
				line = line + value + c;
			}
			line = line.substring(0, line.length() - 1);
			fpw.write(line);
			fpw.newLine();
		}
		fpw.close();
		end = System.currentTimeMillis();
		System.out.println(fieldPermissionReport.getName() + " " + (end - start) + " ms");

		// Write header
		File layoutAssignmentReport = new File(csvReportRepositoryPath + File.separator + "LayoutAssignment" + fileExt);
		start = System.currentTimeMillis();
		BufferedWriter law = new BufferedWriter(new FileWriter(layoutAssignmentReport));
		law.write(header);
		law.newLine();
		line = "";
		// Write Each tag type
		for (String str : pmd.getLayouts()) {
			line = str + c;
			for (Profile p : profiles) {
				List<LayoutAssignment> laList = p.getLayoutAssignments();
				LayoutAssignment la = laList.stream().filter(x -> x.getLayout().equals(str)).findAny().orElse(null);
				String value = la != null ? la.toString() : "";
				line = line + value + c;
			}
			line = line.substring(0, line.length() - 1);
			law.write(line);
			law.newLine();
		}
		law.close();
		end = System.currentTimeMillis();
		System.out.println(layoutAssignmentReport.getName() + " " + (end - start) + " ms");

		// Write header
		File objectPermissionReport = new File(csvReportRepositoryPath + File.separator + "ObjectPermission" + fileExt);
		start = System.currentTimeMillis();
		BufferedWriter opw = new BufferedWriter(new FileWriter(objectPermissionReport));
		opw.write(header);
		opw.newLine();
		line = "";
		// Write Each tag type
		for (String str : pmd.getObjects()) {
			line = str + c;
			for (Profile p : profiles) {
				List<ObjectPermission> opList = p.getObjectPermissions();
				ObjectPermission op = opList.stream().filter(x -> x.getObject().equals(str)).findAny().orElse(null);
				String value = op != null ? op.toString() : "";
				line = line + value + c;
			}
			line = line.substring(0, line.length() - 1);
			opw.write(line);
			opw.newLine();
		}
		opw.close();
		end = System.currentTimeMillis();
		System.out.println(objectPermissionReport.getName() + " " + (end - start) + " ms");

		// Write header
		File recordTypeVisibilityReport = new File(
				csvReportRepositoryPath + File.separator + "RecordTypeVisibility" + fileExt);
		start = System.currentTimeMillis();
		BufferedWriter rtvw = new BufferedWriter(new FileWriter(recordTypeVisibilityReport));
		rtvw.write(header);
		rtvw.newLine();
		line = "";
		// Write Each tag type
		for (String str : pmd.getRecordTypes()) {
			line = str + c;
			for (Profile p : profiles) {
				List<RecordTypeVisibility> rtvList = p.getRecordTypeVisibilities();
				RecordTypeVisibility rtv = rtvList.stream().filter(x -> x.getRecordType().equals(str)).findAny()
						.orElse(null);
				String value = rtv != null ? rtv.toString() : "";
				line = line + value + c;
			}
			line = line.substring(0, line.length() - 1);
			rtvw.write(line);
			rtvw.newLine();
		}
		rtvw.close();
		end = System.currentTimeMillis();
		System.out.println(recordTypeVisibilityReport.getName() + " " + (end - start) + " ms");

		// Write header
		File tabVisibilityReport = new File(csvReportRepositoryPath + File.separator + "TabVisibility" + fileExt);
		start = System.currentTimeMillis();
		BufferedWriter tvw = new BufferedWriter(new FileWriter(tabVisibilityReport));
		tvw.write(header);
		tvw.newLine();
		line = "";
		// Write Each tag type
		for (String str : pmd.getTabs()) {
			line = str + c;
			for (Profile p : profiles) {
				List<TabVisibility> tvList = p.getTabVisibilities();
				TabVisibility tv = tvList.stream().filter(x -> x.getTab().equals(str)).findAny().orElse(null);
				String value = tv != null ? tv.toString() : "";
				line = line + value + c;
			}
			line = line.substring(0, line.length() - 1);
			tvw.write(line);
			tvw.newLine();
		}
		tvw.close();
		end = System.currentTimeMillis();
		System.out.println(tabVisibilityReport.getName() + " " + (end - start) + " ms");

		// Write header
		File userPermissionReport = new File(csvReportRepositoryPath + File.separator + "UserPermission" + fileExt);
		start = System.currentTimeMillis();
		BufferedWriter upw = new BufferedWriter(new FileWriter(userPermissionReport));
		upw.write(header);
		upw.newLine();
		line = "";
		// Write Each tag type
		for (String str : pmd.getNames()) {
			line = str + c;
			for (Profile p : profiles) {
				List<UserPermission> upList = p.getUserPermissions();
				UserPermission up = upList.stream().filter(x -> x.getName().equals(str)).findAny().orElse(null);
				String value = up != null ? up.toString() : "";
				line = line + value + c;
			}
			line = line.substring(0, line.length() - 1);
			upw.write(line);
			upw.newLine();
		}
		upw.close();
		end = System.currentTimeMillis();
		System.out.println(userPermissionReport.getName() + " " + (end - start) + " ms");
	}

}
