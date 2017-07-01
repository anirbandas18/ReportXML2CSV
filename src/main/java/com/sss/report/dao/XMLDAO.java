package com.sss.report.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.sss.report.core.NamespaceFilter;
import com.sss.report.entity.FieldPermission;
import com.sss.report.entity.LayoutAssignment;
import com.sss.report.entity.ObjectPermission;
import com.sss.report.entity.Profile;
import com.sss.report.entity.RecordTypeVisibility;
import com.sss.report.entity.TabVisibility;
import com.sss.report.entity.UserPermission;
import com.sss.report.model.ProfileMetadataModel;

public class XMLDAO implements Callable<Profile>{
	
	private String xmlFilePath;
	
	private ProfileMetadataModel metadata;
	
	public XMLDAO(String xmlFilePath, ProfileMetadataModel metadata) {
		this.metadata = metadata;
		this.xmlFilePath = xmlFilePath;
	}
	
	private static NamespaceFilter getNameSpaceFilter(String namespace) throws SAXException {
		XMLReader xmlreader = XMLReaderFactory.createXMLReader();
		NamespaceFilter inFilter = new NamespaceFilter(namespace, true);
		inFilter.setParent(xmlreader);
		return inFilter;
	}
	
	private static InputSource getInputSource(File file) throws FileNotFoundException, UnsupportedEncodingException {
		InputStream inputStream = new FileInputStream(file);
		Reader reader = new InputStreamReader(inputStream,"UTF-8");
		InputSource is = new InputSource(reader);
		return is;
	}

	@Override
	public Profile call() throws Exception {
		Profile profile = new Profile();
		Path filePath = Paths.get(xmlFilePath);
		File xmlFile = filePath.toFile();
		NamespaceFilter inFilter = getNameSpaceFilter(null);
		InputSource is = getInputSource(xmlFile);
		SAXSource xmlSource = new SAXSource(inFilter, is);
		JAXBContext context = JAXBContext.newInstance(Profile.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		profile = (Profile) unmarshaller.unmarshal(xmlSource);
		metadata.addFields(profile.getFieldPermissions().stream().map(FieldPermission::getField).collect(Collectors.toSet()));
		metadata.addLayouts(profile.getLayoutAssignments().stream().map(LayoutAssignment::getLayout).collect(Collectors.toSet()));
		metadata.addObjects(profile.getObjectPermissions().stream().map(ObjectPermission::getObject).collect(Collectors.toSet()));
		metadata.addRecordTypes(profile.getRecordTypeVisibilities().stream().map(RecordTypeVisibility::getRecordType).collect(Collectors.toSet()));
		metadata.addTabs(profile.getTabVisibilities().stream().map(TabVisibility::getTab).collect(Collectors.toSet()));
		metadata.addNames(profile.getUserPermissions().stream().map(UserPermission::getName).collect(Collectors.toSet()));
		profile.setFileName(xmlFile.getName());
		return profile;
	}

}
