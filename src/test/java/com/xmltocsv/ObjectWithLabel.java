package com.xmltocsv;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ObjectWithLabel {

	public static void main(String[] args) {
		List<String> objectFileNames=new ArrayList<String>();	
		List<String> fileNames=new ArrayList<String>();
		try{							
			File objectFolder=new File("InputFiles/Objects/");
		   	File[] listOfObjectFiles=objectFolder.listFiles();
			for(int i=0;i<listOfObjectFiles.length;i++){
				if(listOfObjectFiles[i].isFile()){
					objectFileNames.add(listOfObjectFiles[i].getName());
				}
			}
			Map<String,String> objectMap=new TreeMap<String,String>();
			ArrayList<String> objectNames=new ArrayList<String>();
			for(int i=0;i<objectFileNames.size();i++){
				File input=new File("InputFiles/Objects/"+objectFileNames.get(i));
				DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
				Document doc=dBuilder.parse(input);
				doc.getDocumentElement().normalize();
				NodeList nodeList=doc.getDocumentElement().getChildNodes();	
					for(int j=0;j<nodeList.getLength();j++){
					Node node=nodeList.item(j);	
					if(node.getNodeType()==Node.ELEMENT_NODE){
						if(node.getNodeName()=="label"){
							objectMap.put(objectFileNames.get(i).split("\\.")[0], node.getTextContent());	
						}
					}					
				}											
			}
			System.out.println(objectMap);
			Set set=objectMap.entrySet();
			Iterator it=set.iterator();
			while(it.hasNext()){
				Map.Entry en=(Map.Entry)it.next();
				objectNames.add((String) en.getKey());
			}
			Collections.sort(objectNames);			
			//File folder=new File("InputFiles/Profile/");
			File folder=new File("InputFiles/PermissionSets/");
		   	File[] listOfFiles=folder.listFiles();
			for(int i=0;i<listOfFiles.length;i++){
				if(listOfFiles[i].isFile()){
					fileNames.add(listOfFiles[i].getName());
				}
			}
			for(int i=0;i<fileNames.size();i++){
				//File input=new File("InputFiles/Profile/"+fileNames.get(i));				
				File input=new File("InputFiles/PermissionSets/"+fileNames.get(i));
				String outputName=fileNames.get(i).split("\\.")[0];
				//File output=new File("OutputFiles/Profile Output/"+outputName+"_tab_setting.csv");				
				File output=new File("OutputFiles/PermissionSet Output/"+outputName+"_tab_setting.csv");
				FileWriter fw=new FileWriter(output);
				String str="TabName,LabelName,Access\n";
				fw.write(str);
				fw.flush();				
				//--Profile Tab Visibility:
				//Map<String,String> fileMap=setTabMap(input,"tabVisibilities");
				//--PermissionSet Tab Setting:
				Map<String,String> fileMap=setTabMap(input,"tabSettings"); 
				
				for(String s:objectNames){
					String nodeStr="";
					if(objectMap.containsKey(s) && fileMap.containsKey(s)){
						nodeStr=s+","+objectMap.get(s)+","+fileMap.get(s)+"\n";
					}else if(objectMap.containsKey(s) && !fileMap.containsKey(s)){
						nodeStr=s+","+objectMap.get(s)+","+" "+"\n";
					}
					fw.write(nodeStr);
					fw.flush();
				}
				set=fileMap.entrySet();
				it=set.iterator();
				while(it.hasNext()){
					Map.Entry en=(Map.Entry)it.next();					
					String key=(String)en.getKey();					
					if(key.split("-")[0].equals("standard")){
						String st=key+","+key.split("-")[1]+","+fileMap.get(key)+"\n";
						fw.write(st);
						fw.flush();
					}
				}
				fw.close();
				System.out.println(output+" is generated." );
			}
			
			}catch(Exception ex){
				ex.printStackTrace();
		}

	}
	
	public static Map<String,String> setTabMap(File input, String tabFieldName){
		Map<String,String> tabMap=new TreeMap<String,String>();
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
				tabMap.put(element.getElementsByTagName("tab").item(0).getTextContent(),element.getElementsByTagName("visibility").item(0).getTextContent());
			}			
			
		}catch(Exception ex){System.out.println("Exception inside function: "+ex);}
		return tabMap;
	}

}
