package com.sss.report.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.sss.report.core.NamespaceFilter;
import com.sss.report.entity.Profile;

public class XMLDAO {
	
	public static Profile unmarshall(String xmlFilePath) throws JAXBException, SAXException, FileNotFoundException {
		Profile profile = new Profile();
		Path filePath = Paths.get(xmlFilePath);
		File xmlFile = filePath.toFile();
		profile.setFileName(xmlFile.getName());
		XMLReader reader = XMLReaderFactory.createXMLReader();
		NamespaceFilter inFilter = new NamespaceFilter(null, true);
		inFilter.setParent(reader);
		InputSource is = new InputSource(new FileInputStream(xmlFile));
		SAXSource xmlSource = new SAXSource(inFilter, is);
		JAXBContext context = JAXBContext.newInstance(Profile.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		profile = (Profile) unmarshaller.unmarshal(xmlSource);
		return profile;
	}

}
