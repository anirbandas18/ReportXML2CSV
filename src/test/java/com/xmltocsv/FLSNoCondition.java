package com.xmltocsv;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FLSNoCondition {

	public static void main(String[] args) {
		List<String> fileNames=new ArrayList<String>();		
		try{				
			File folder=new File("InputFiles");
		   	File[] listOfFiles=folder.listFiles();
			for(int i=0;i<listOfFiles.length;i++){
				if(listOfFiles[i].isFile()){
					fileNames.add(listOfFiles[i].getName());
				}
			}			
			//System.out.println(fileNames);
			for(int i=0;i<fileNames.size();i++){
				File input=new File("InputFiles/"+fileNames.get(i));							
				String outputName=fileNames.get(i).split("\\.")[0];
				File output=new File("OutputFiles/"+outputName+".csv");
				FileWriter fw=new FileWriter(output);
				DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
				Document doc=dBuilder.parse(input);
				doc.getDocumentElement().normalize();
				//System.out.println("Root Element: "+doc.getDocumentElement().getNodeName());
				NodeList nodeList=doc.getElementsByTagName("fieldPermissions");		
				String str="FieldName,Access,\n";
				fw.write(str);
				fw.flush();
				for(int j=0;j<nodeList.getLength();j++){
					Node node=nodeList.item(j);
					Element element=(Element)node;				
					String accessFlag="";
					if(element.getElementsByTagName("readable").item(0).getTextContent().equals("true") && element.getElementsByTagName("editable").item(0).getTextContent().equals("true"))
						accessFlag+="RE";
					else if(element.getElementsByTagName("readable").item(0).getTextContent().equals("true") && element.getElementsByTagName("editable").item(0).getTextContent().equals("false"))
						accessFlag+="RN";
					else if(element.getElementsByTagName("readable").item(0).getTextContent().equals("false") && element.getElementsByTagName("editable").item(0).getTextContent().equals("false"))
					accessFlag+="NN";
					String nodeStr=element.getElementsByTagName("field").item(0).getTextContent()+","+accessFlag+"\n";
					fw.write(nodeStr);
					fw.flush();
				}
				fw.close();
				System.out.println(output+" is generated." );
			}
			  
	
			}catch(Exception ex){
				ex.printStackTrace();
		}
	}
}
