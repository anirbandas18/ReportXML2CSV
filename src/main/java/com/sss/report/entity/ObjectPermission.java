package com.sss.report.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectPermission {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectPermission other = (ObjectPermission) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}
	@XmlElement
	private Boolean allowCreate;
	@XmlElement
	private Boolean allowRead;
	@XmlElement
	private Boolean allowEdit;
	@XmlElement
	private Boolean allowDelete;
	@XmlElement
	private Boolean modifyAllrecords;
	@XmlElement
	private String object;
	@XmlElement
	private Boolean viewAllRecords;
	public Boolean getAllowCreate() {
		return allowCreate;
	}
	public void setAllowCreate(Boolean allowCreate) {
		this.allowCreate = allowCreate;
	}
	public Boolean getAllowRead() {
		return allowRead;
	}
	public void setAllowRead(Boolean allowRead) {
		this.allowRead = allowRead;
	}
	public Boolean getAllowEdit() {
		return allowEdit;
	}
	public void setAllowEdit(Boolean allowEdit) {
		this.allowEdit = allowEdit;
	}
	public Boolean getAllowDelete() {
		return allowDelete;
	}
	public void setAllowDelete(Boolean allowDelete) {
		this.allowDelete = allowDelete;
	}
	public Boolean getModifyAllrecords() {
		return modifyAllrecords;
	}
	public void setModifyAllrecords(Boolean modifyAllrecords) {
		this.modifyAllrecords = modifyAllrecords;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public Boolean getViewAllRecords() {
		return viewAllRecords;
	}
	public void setViewAllRecords(Boolean viewAllRecords) {
		this.viewAllRecords = viewAllRecords;
	}
	@Override
	public String toString() {
		return "ObjectPermission [allowCreate=" + allowCreate + ", allowRead=" + allowRead + ", allowEdit=" + allowEdit
				+ ", allowDelete=" + allowDelete + ", modifyAllrecords=" + modifyAllrecords + ", object=" + object
				+ ", viewAllRecords=" + viewAllRecords + "]";
	}

	
}
