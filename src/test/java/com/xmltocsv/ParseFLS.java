package com.xmltocsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
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

public class ParseFLS{

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
					Map<String,String> profileMap=setFieldMap(inputPro);
					Map<String,String> permissionMap=setFieldMap(inputPer);
					outputFileName=pro+"_"+per;
					File outputFile=new File("OutputFiles/FLS/"+outputFileName+"_fls.csv");
					FileWriter fw=new FileWriter(outputFile);										
					
					set=profileMap.entrySet();
					it=set.iterator();
					while(it.hasNext()){
						Map.Entry en=(Map.Entry)it.next();
						fields.add((String) en.getKey());
					}
					
					set=permissionMap.entrySet();
					it=set.iterator();
					while(it.hasNext()){
						Map.Entry en=(Map.Entry)it.next();
						fields.add((String)en.getKey());
					}
					
					String str="Object Name,Field Name,Profile Access,PermissonSet Access\n";
					fw.write(str);
					fw.flush();	
					List<String> sortedList=new ArrayList(fields);
					Collections.sort(sortedList,String.CASE_INSENSITIVE_ORDER);
					//System.out.println(sortedList);
					for(String s:sortedList){
						String nodeStr="";
						if(profileMap.containsKey(s) && permissionMap.containsKey(s)){							
							/*String status="";
							if(profileMap.get(s).equals("RE") && (permissionMap.get(s).equals("RN") || permissionMap.get(s).equals("NN"))){
								status="Downgrade";
							}else if(profileMap.get(s).equals("NN") && (permissionMap.get(s).equals("RN") || permissionMap.get(s).equals("RE"))){
								status="Upgrade";
							}else if(profileMap.get(s).equals("RN") && permissionMap.get(s).equals("RE")){
								status="Upgrade";
							}else if(profileMap.get(s).equals("RN") && permissionMap.get(s).equals("NN")){
								status="Downgrade";
							}*/
							//nodeStr=s+","+profileMap.get(s)+","+permissionMap.get(s)+","+status+"\n";
							nodeStr=s.split("\\.")[0]+","+s.split("\\.")[1]+","+profileMap.get(s)+","+permissionMap.get(s)+"\n";
						}else if(profileMap.containsKey(s) && !permissionMap.containsKey(s)){
							nodeStr=s.split("\\.")[0]+","+s.split("\\.")[1]+","+profileMap.get(s)+","+"-"+"\n";
							//nodeStr=s.split("\\.")[0]+","+s.split("\\.")[1]+","+profileMap.get(s)+","+"-"+","+"In Profile"+"\n";
						}else if(!profileMap.containsKey(s) && permissionMap.containsKey(s)){
							//nodeStr=s.split("\\.")[0]+","+s.split("\\.")[1]+","+"-"+","+permissionMap.get(s)+","+"In Permission Set"+"\n";
							nodeStr=s.split("\\.")[0]+","+s.split("\\.")[1]+","+"-"+","+permissionMap.get(s)+"\n";
						}
						fw.write(nodeStr);
						fw.flush();
					}
					fw.close();
					System.out.println(outputFile+" is generated." );
				}
						
						
				
			}
				
				
		}catch(Exception ex){
				System.out.println("Exception Occurs: "+ex);
		}
	}
	
	public static Map<String,String> setFieldMap(File input){
		Map<String,String> fieldMap=new HashMap<String,String>();
		try{
			DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
			Document doc=dBuilder.parse(input);
			doc.getDocumentElement().normalize();
			//System.out.println("Root Element: "+doc.getDocumentElement().getNodeName());
			NodeList nodeList=doc.getElementsByTagName("fieldPermissions");		
					
			for(int i=0;i<nodeList.getLength();i++){
				Node node=nodeList.item(i);
				Element element=(Element)node;				
				String accessFlag="";
				if(element.getElementsByTagName("readable").item(0).getTextContent().equals("true") && element.getElementsByTagName("editable").item(0).getTextContent().equals("true"))
					accessFlag+="RE";
				else if(element.getElementsByTagName("readable").item(0).getTextContent().equals("true") && element.getElementsByTagName("editable").item(0).getTextContent().equals("false"))
					accessFlag+="RN";
				else if(element.getElementsByTagName("readable").item(0).getTextContent().equals("false") && element.getElementsByTagName("editable").item(0).getTextContent().equals("false"))
					accessFlag+="NN";
				String nodeStr=element.getElementsByTagName("field").item(0).getTextContent()+","+accessFlag+"\n";				
				fieldMap.put(element.getElementsByTagName("field").item(0).getTextContent(), accessFlag);
			}			
			
		}catch(Exception ex){System.out.println("Exception inside function: "+ex);}
		return fieldMap;	
	}
}
