package com.sss.test.report.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Field {

	@XmlElement
	private String fullName;
	@XmlElement
	private Boolean externalId;
	@XmlElement
	private String label;
	@XmlElement
	private String referenceTo;
	@XmlElement
	private String relationshipName;
	@XmlElement
	private Integer relationshipOrder;
	@XmlElement
	private Boolean reparentableMasterDetail;
	@XmlElement
	private Boolean trackTrending;
	@XmlElement
	private String type;
	@XmlElement
	private Boolean writeRequiresMasterRead;
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Boolean getExternalId() {
		return externalId;
	}
	public void setExternalId(Boolean externalId) {
		this.externalId = externalId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getReferenceTo() {
		return referenceTo;
	}
	public void setReferenceTo(String referenceTo) {
		this.referenceTo = referenceTo;
	}
	public String getRelationshipName() {
		return relationshipName;
	}
	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}
	public Integer getRelationshipOrder() {
		return relationshipOrder;
	}
	public void setRelationshipOrder(Integer relationshipOrder) {
		this.relationshipOrder = relationshipOrder;
	}
	public Boolean getReparentableMasterDetail() {
		return reparentableMasterDetail;
	}
	public void setReparentableMasterDetail(Boolean reparentableMasterDetail) {
		this.reparentableMasterDetail = reparentableMasterDetail;
	}
	public Boolean getTrackTrending() {
		return trackTrending;
	}
	public void setTrackTrending(Boolean trackTrending) {
		this.trackTrending = trackTrending;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getWriteRequiresMasterRead() {
		return writeRequiresMasterRead;
	}
	public void setWriteRequiresMasterRead(Boolean writeRequiresMasterRead) {
		this.writeRequiresMasterRead = writeRequiresMasterRead;
	}

	
	
}
