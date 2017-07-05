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
public class Profile {
	
	@XmlTransient
	private String fileName;
	@XmlElement
	private Boolean custom;
	@XmlElement
	private String userLicense;
	@XmlElement
	private List<FieldPermission> fieldPermissions;
	@XmlElement
	private List<LayoutAssignment> layoutAssignments;
	@XmlElement
	private List<ObjectPermission> objectPermissions;
	@XmlElement
	private List<RecordTypeVisibility> recordTypeVisibilities;
	@XmlElement
	private List<TabVisibility> tabVisibilities;
	@XmlElement
	private List<UserPermission> userPermissions;
	
	public Profile() {
		this.fieldPermissions = new ArrayList<>();
		this.layoutAssignments = new ArrayList<>();
		this.objectPermissions = new ArrayList<>();
		this.recordTypeVisibilities = new ArrayList<>();
		this.tabVisibilities = new ArrayList<>();
		this.userPermissions = new ArrayList<>();
	}
	
	public Boolean getCustom() {
		return custom;
	}
	public void setCustom(Boolean custom) {
		this.custom = custom;
	}
	public List<FieldPermission> getFieldPermissions() {
		return fieldPermissions;
	}
	public void setFieldPermissions(List<FieldPermission> fieldPermissions) {
		this.fieldPermissions = fieldPermissions;
	}
	@Override
	public String toString() {
		StringWriter sw = new StringWriter();
		try {
			BufferedWriter bw = new BufferedWriter(sw);
			bw.write("Profile [custom=" + custom + ", fileName=" + fileName + ", fieldPermissions=");
			bw.newLine();
			for(FieldPermission fp : fieldPermissions) {
				bw.write(fp.toString());
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
	public List<LayoutAssignment> getLayoutAssignments() {
		return layoutAssignments;
	}
	public void setLayoutAssignments(List<LayoutAssignment> layoutAssignments) {
		this.layoutAssignments = layoutAssignments;
	}
	public List<ObjectPermission> getObjectPermissions() {
		return objectPermissions;
	}
	public void setObjectPermissions(List<ObjectPermission> objectPermissions) {
		this.objectPermissions = objectPermissions;
	}
	public List<RecordTypeVisibility> getRecordTypeVisibilities() {
		return recordTypeVisibilities;
	}
	public void setRecordTypeVisibilities(List<RecordTypeVisibility> recordTypeVisibilities) {
		this.recordTypeVisibilities = recordTypeVisibilities;
	}
	public String getUserLicense() {
		return userLicense;
	}
	public void setUserLicense(String userLicense) {
		this.userLicense = userLicense;
	}
	public List<TabVisibility> getTabVisibilities() {
		return tabVisibilities;
	}
	public void setTabVisibilities(List<TabVisibility> tabVisibilities) {
		this.tabVisibilities = tabVisibilities;
	}
	public List<UserPermission> getUserPermissions() {
		return userPermissions;
	}
	public void setUserPermissions(List<UserPermission> userPermissions) {
		this.userPermissions = userPermissions;
	}
	

}
