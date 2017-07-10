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

public class TabNoCondition {

	public static void main(String[] args) {
		List<String> fileNames=new ArrayList<String>();		
		try{				
			//File folder=new File("InputFiles/PermissionSets");
			File folder=new File("InputFiles/Profile");
		   	File[] listOfFiles=folder.listFiles();
			for(int i=0;i<listOfFiles.length;i++){
				if(listOfFiles[i].isFile()){
					fileNames.add(listOfFiles[i].getName());
				}
			}
			//System.out.println(fileNames);
			for(int i=0;i<fileNames.size();i++){
				File input=new File("InputFiles/Profile/"+fileNames.get(i));
				//File input=new File("InputFiles/PermissionSets/"+fileNames.get(i));
				String outputName=fileNames.get(i).split("\\.")[0];
				File output=new File("OutputFiles/Profile Output/"+outputName+"_tab_setting.csv");
				//File output=new File("OutputFiles/PermissionSet Output/"+outputName+"_tab_setting.csv");
				FileWriter fw=new FileWriter(output);
				DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
				Document doc=dBuilder.parse(input);
				doc.getDocumentElement().normalize();
				//System.out.println("Root Element: "+doc.getDocumentElement().getNodeName());
				//--PermissionSet Tab Setting:
				//NodeList nodeList=doc.getElementsByTagName("tabSettings");
				//--Profile Tab Setting:
				NodeList nodeList=doc.getElementsByTagName("tabVisibilities");
				String str="TabName,Access\n";
				fw.write(str);
				fw.flush();
				for(int j=0;j<nodeList.getLength();j++){
					Node node=nodeList.item(j);
					Element element=(Element)node;					
					str=element.getElementsByTagName("tab").item(0).getTextContent()+","+element.getElementsByTagName("visibility").item(0).getTextContent()+"\n";
					fw.write(str);
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
