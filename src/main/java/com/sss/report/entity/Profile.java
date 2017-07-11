package com.sss.report.entity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="Profile"/*, namespace = "http://soap.sforce.com/2006/04/metadata"*/)
@XmlAccessorType(XmlAccessType.FIELD)
public class Profile implements Comparable<Profile>{
	
	@XmlTransient
	private String fileName;
	public Profile() {
		this.field = new ArrayList<>();
		this.layout = new ArrayList<>();
		this.object = new ArrayList<>();
		this.recordType = new ArrayList<>();
		this.tab = new ArrayList<>();
		this.name = new ArrayList<>();
	}
	@XmlElement
	private Boolean custom;
	@XmlElement
	private String userLicense;
	@XmlElement(name = "fieldPermissions")
	private List<FieldPermission> field;
	@XmlElement(name = "layoutAssignments")
	private List<LayoutAssignment> layout;
	@XmlElement(name = "objectPermissions")
	private List<ObjectPermission> object;
	@XmlElement(name = "recordTypeVisibilities")
	private List<RecordTypeVisibility> recordType;
	@XmlElement(name = "tabVisibilities")
	private List<TabVisibility> tab;
	@XmlElement(name = "userPermissions")
	private List<UserPermission> name;
	
	public Boolean getCustom() {
		return custom;
	}
	public void setCustom(Boolean custom) {
		this.custom = custom;
	}
	@Override
	public String toString() {
		StringWriter sw = new StringWriter();
		try {
			BufferedWriter bw = new BufferedWriter(sw);
			bw.write("Profile [custom=" + custom + ", fileName=" + fileName + ", layout=");
			bw.newLine();
			for(LayoutAssignment la : layout) {
				bw.write(la.toString());
				bw.newLine();
			}
			bw.write("]");
		} catch (IOException e) {
			sw.write(e.toString());
		}
		return sw.toString();
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUserLicense() {
		return userLicense;
	}
	public void setUserLicense(String userLicense) {
		this.userLicense = userLicense;
	}
	public List<FieldPermission> getField() {
		return field;
	}
	public void setField(List<FieldPermission> field) {
		this.field = field;
	}
	public List<LayoutAssignment> getLayout() {
		return layout;
	}
	public void setLayout(List<LayoutAssignment> layout) {
		this.layout = layout;
	}
	public List<ObjectPermission> getObject() {
		return object;
	}
	public void setObject(List<ObjectPermission> object) {
		this.object = object;
	}
	public List<RecordTypeVisibility> getRecordType() {
		return recordType;
	}
	public void setRecordType(List<RecordTypeVisibility> recordType) {
		this.recordType = recordType;
	}
	public List<TabVisibility> getTab() {
		return tab;
	}
	public void setTab(List<TabVisibility> tab) {
		this.tab = tab;
	}
	public List<UserPermission> getName() {
		return name;
	}
	public void setName(List<UserPermission> name) {
		this.name = name;
	}
	@Override
	public int compareTo(Profile o) {
		this.fileName.compareTo(o.getFileName());
		return 0;
	}
	
	

}
