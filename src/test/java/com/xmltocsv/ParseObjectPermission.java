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

public class ParseObjectPermission {

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
					Map<String,List<String>> profileMap=setObjectPermissionMap(inputPro, "objectPermissions");
					Map<String,List<String>> psMap=setObjectPermissionMap(inputPer, "objectPermissions");
					outputFileName=pro+"_"+per;
					File outputFile=new File("OutputFiles/Object Permission/"+outputFileName+"_object_permission.csv");
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
					
					String str="Object Name,allowCreate Profile,allowCreate Permission,allowDelete Profile,allowDelete Permission,allowEdit Profile,allowEdit Permission,allowRead Profile,allowRead Permission, modifyAllRecord Profile, modifyAllRecord Permission,viewAllRecords Profile, viewAllRecords Permission\n";
					fw.write(str);
					fw.flush();	
					List<String> sortedList=new ArrayList(fields);
					Collections.sort(sortedList);
					//System.out.println(sortedList);
					for(String s:sortedList){
						String nodeStr="";
						ArrayList<String> profileAccessList;
						ArrayList<String> permissionAccessList;
						if(profileMap.containsKey(s) && psMap.containsKey(s)){
							//nodeStr=s+","+profileMap.get(s)+","+psMap.get(s)+"\n";
							profileAccessList=(ArrayList<String>) profileMap.get(s);
							permissionAccessList=(ArrayList<String>) psMap.get(s);
							String st="";
							for(int i=0;i<6;i++){
								st=st+","+profileAccessList.get(i)+","+permissionAccessList.get(i);
							}
							nodeStr=s+","+st+"\n";
							
						}else if(profileMap.containsKey(s) && !psMap.containsKey(s)){
							//nodeStr=s+","+profileMap.get(s)+","+"-"+"\n";
							profileAccessList=(ArrayList<String>) profileMap.get(s);
							permissionAccessList=(ArrayList<String>) psMap.get(s);
							String st="";
							for(int i=0;i<6;i++){
								st=st+","+profileAccessList.get(i)+","+"-";
							}
							nodeStr=s+","+st+"\n";
						}else if(!profileMap.containsKey(s) && psMap.containsKey(s)){
							//nodeStr=s+","+"-"+","+psMap.get(s)+"\n";
							profileAccessList=(ArrayList<String>) profileMap.get(s);
							permissionAccessList=(ArrayList<String>) psMap.get(s);
							String st="";
							for(int i=0;i<6;i++){
								st=st+","+"-"+","+permissionAccessList.get(i);
							}
							nodeStr=s+","+st+"\n";
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
	
	public static Map<String, List<String>> setObjectPermissionMap(File input, String objectPermission){
		Map<String,List<String>> objectMap=new HashMap<String,List<String>>();
		try{
			DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
			Document doc=dBuilder.parse(input);
			doc.getDocumentElement().normalize();
			//System.out.println("Root Element: "+doc.getDocumentElement().getNodeName());
			NodeList nodeList=doc.getElementsByTagName(objectPermission);		
					
			for(int i=0;i<nodeList.getLength();i++){
				Node node=nodeList.item(i);
				Element element=(Element)node;
				List<String> access=new ArrayList<String>();
				access.add(element.getElementsByTagName("allowCreate").item(0).getTextContent());
				access.add(element.getElementsByTagName("allowDelete").item(0).getTextContent());
				access.add(element.getElementsByTagName("allowEdit").item(0).getTextContent());
				access.add(element.getElementsByTagName("allowRead").item(0).getTextContent());
				access.add(element.getElementsByTagName("modifyAllRecords").item(0).getTextContent());
				access.add(element.getElementsByTagName("viewAllRecords").item(0).getTextContent());
				objectMap.put(element.getElementsByTagName("object").item(0).getTextContent(),access);
			}			
			
		}catch(Exception ex){ex.printStackTrace();}
		return objectMap;
	}
}

