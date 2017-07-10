package com.sss.test.report;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.sss.report.core.NamespaceFilter;
import com.sss.test.report.entity.CustomObject;
import com.sss.test.report.entity.Field;

public class Indranil {
	
	private static NamespaceFilter getNameSpaceFilter(String namespace) throws SAXException {
		XMLReader xmlreader = XMLReaderFactory.createXMLReader();
		NamespaceFilter inFilter = new NamespaceFilter(namespace, true);
		inFilter.setParent(xmlreader);
		return inFilter;
	}
	
	private static InputSource getInputSource(InputStream inputStream) throws IOException {
		String defaultEncoding = "UTF-8";
		BOMInputStream bOMInputStream = new BOMInputStream(inputStream);
	    ByteOrderMark bom = bOMInputStream.getBOM();
		String charsetName = bom == null ? defaultEncoding : bom.getCharsetName();
		Reader reader = new InputStreamReader(inputStream,charsetName);
		InputSource is = new InputSource(reader);
		return is;
	}
	
	public static CustomObject unmarshall(InputStream inputStream) throws JAXBException, SAXException, IOException {
		CustomObject object = new CustomObject();
		NamespaceFilter inFilter = getNameSpaceFilter(null);
		InputSource is = getInputSource(inputStream);
		SAXSource xmlSource = new SAXSource(inFilter, is);
		JAXBContext context = JAXBContext.newInstance(CustomObject.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		object = (CustomObject) unmarshaller.unmarshal(xmlSource);
		return object;
	}

	public static void main(String[] args) throws IOException, JAXBException, SAXException {
		String xmlRepositoryPath = args[0];
		//String outputPath = args[1];
		Path xml = Paths.get(xmlRepositoryPath);
		DirectoryStream<Path> xmlFiles = Files.newDirectoryStream(xml);
		for (Path xmlFile : xmlFiles) {
			Path xmlFilePath = Paths.get(xmlFile.toString());
			InputStream inputStream = new FileInputStream(xmlFilePath.toFile());
			CustomObject object = unmarshall(inputStream);
			List<Field> fields = object.getFields();
			System.out.println(object.getFileName() + " " + fields.size());
		}
	}
	
}
