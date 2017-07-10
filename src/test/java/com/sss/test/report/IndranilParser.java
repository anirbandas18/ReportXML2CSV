package com.sss.test.report;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class IndranilParser {

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
		// TODO Auto-generated method stub
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("DependentObjects.txt")), true));
		System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream("MasterObjects.txt")), true));
		String getFieldsWithTypeLookup = "/CustomObject/fields[type = 'Lookup' or type = 'MasterDetail']/fullName";
		String countTypesThatAreLookup = "count(/CustomObject/fields/type[text() = 'Lookup' or text() = 'MasterDetail'])";
		Path dirPath = Paths.get(args[0]);
		Path dependentDir = Paths.get(args[1]);
		Files.createDirectories(dependentDir);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression xPathCountTypesThatAreLookup = xpath.compile(countTypesThatAreLookup);
		XPathExpression xPathGetFieldsWithTypeLookup = xpath.compile(getFieldsWithTypeLookup);
		DirectoryStream<Path> xmlFiles = Files.newDirectoryStream(dirPath);
		for(Path xmlFile : xmlFiles) {
			Document doc = dBuilder.parse(xmlFile.toFile());
			Double count = (Double) xPathCountTypesThatAreLookup.evaluate(doc, XPathConstants.NUMBER);
			if(count != 0.0d) { // dependant object
				NodeList nodeList = (NodeList) xPathGetFieldsWithTypeLookup.evaluate(doc, XPathConstants.NODESET);
				String line = xmlFile.getFileName() + " <=> " + count.intValue() + " <=> ";
				List<String> fullNames = new ArrayList<>();
				for(int i = 0 ; i < nodeList.getLength() ; i++) {
					Node node = nodeList.item(i);
					fullNames.add(node.getTextContent());
				}
				line = line + fullNames.toString();
				System.out.println(line);
			} else { // master object
				Files.copy(xmlFile, dependentDir.resolve(xmlFile.getFileName()));
				System.err.println(xmlFile.getFileName());
			}
		}
	}

}
