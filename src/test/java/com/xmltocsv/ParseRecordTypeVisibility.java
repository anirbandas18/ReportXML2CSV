package com.xmltocsv;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseRecordTypeVisibility {

	public static void main(String[] args) {
		/* Reading a file from a queue */
		File folder=new File("InputFiles/Profile");
		List<String> profileNames=new ArrayList<String>();
		List<String> permissionSetNames=new ArrayList<String>();
		File[] listOfFiles=folder.listFiles();
		for(int i=0;i<listOfFiles.length;i++){
			if(listOfFiles[i].isFile()){
				profileNames.add(listOfFiles[i].getName().split("\\.")[0]);
			}
		}
		folder=new File("InputFiles/PermissionSets");
		listOfFiles=folder.listFiles();
		for(int i=0;i<listOfFiles.length;i++){
			if(listOfFiles[i].isFile()){
				permissionSetNames.add(listOfFiles[i].getName().split("\\.")[0]);
			}
		}		
		try{	
			File inputPro,inputPer;
			String inputFilePro,inputFilePer;
			for(String pro:profileNames){						
				Set set;
				Iterator it;
				String outputFileName="";																
				for(String per:permissionSetNames){
					Set<String> fields=new HashSet<String>();
					inputFilePro="InputFiles/Profile/"+pro+".profile";
					inputPro=new File(inputFilePro);
					inputFilePer="InputFiles/PermissionSets/"+per+".permissionset";					
					inputPer=new File(inputFilePer);	
					Map<String,String> profileMap=setLayoutMap(inputPro, "recordTypeVisibilities");
					Map<String,String> psMap=setLayoutMap(inputPer, "recordTypeVisibilities");
					outputFileName=pro+"_"+per;
					File outputFile=new File("OutputFiles/Recordtype Visibility/"+outputFileName+"_recordtype.csv");
					FileWriter fw=new FileWriter(outputFile);										
					
					set=profileMap.entrySet();
					it=set.iterator();
					while(it.hasNext()){
						Map.Entry en=(Map.Entry)it.next();
						fields.add((String) en.getKey());
					}
					
					set=psMap.entrySet();
					it=set.iterator();
					while(it.hasNext()){
						Map.Entry en=(Map.Entry)it.next();
						fields.add((String)en.getKey());
					}
					
					String str="Record Type,Profile Visibility,Permission Set Visibility\n";
					fw.write(str);
					fw.flush();	
					List<String> sortedList=new ArrayList(fields);
					Collections.sort(sortedList);
					//System.out.println(sortedList);
					for(String s:sortedList){
						String nodeStr="";
						if(profileMap.containsKey(s) && psMap.containsKey(s)){
							nodeStr=s+","+profileMap.get(s)+","+psMap.get(s)+"\n";
						}else if(profileMap.containsKey(s) && !psMap.containsKey(s)){
							nodeStr=s+","+profileMap.get(s)+","+"-"+"\n";
						}else if(!profileMap.containsKey(s) && psMap.containsKey(s)){
							nodeStr=s+","+"-"+","+psMap.get(s)+"\n";
						}
						fw.write(nodeStr);
						fw.flush();
					}
					fw.close();
					System.out.println(outputFile+" is generated." );
				}
						
						
				
			}
				
				
		}catch(Exception ex){
				ex.printStackTrace();
		}
	}
	
	public static Map<String,String> setLayoutMap(File input, String tabFieldName){
		Map<String,String> tabMap=new HashMap<String,String>();
		try{
			DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
			Document doc=dBuilder.parse(input);
			doc.getDocumentElement().normalize();
			//System.out.println("Root Element: "+doc.getDocumentElement().getNodeName());
			NodeList nodeList=doc.getElementsByTagName(tabFieldName);		
					
			for(int i=0;i<nodeList.getLength();i++){
				Node node=nodeList.item(i);
				Element element=(Element)node;				
				tabMap.put(element.getElementsByTagName("recordType").item(0).getTextContent(),element.getElementsByTagName("visible").item(0).getTextContent());
			}			
			
		}catch(Exception ex){ex.printStackTrace();}
		return tabMap;
	}

}
