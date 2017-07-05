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
	
	public static Profile unmarshall(String xmlFilePath) throws JAXBException, SAXException, FileNotFoundException, UnsupportedEncodingException {
		Profile profile = new Profile();
		Path filePath = Paths.get(xmlFilePath);
		//System.out.println(filePath);
		File xmlFile = filePath.toFile();
		NamespaceFilter inFilter = getNameSpaceFilter(null);
		InputSource is = getInputSource(xmlFile);
		SAXSource xmlSource = new SAXSource(inFilter, is);
		JAXBContext context = JAXBContext.newInstance(Profile.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		profile = (Profile) unmarshaller.unmarshal(xmlSource);
		profile.setFileName(xmlFile.getName());
		return profile;
	}

}
