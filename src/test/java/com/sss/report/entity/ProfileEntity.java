package com.sss.report.entity;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
public class ProfileEntity implements Comparable<ProfileEntity>{

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profile")
	private Set<FieldPermissionsEntity> fieldPermissions;
	@Id
	private String name;

	public Set<FieldPermissionsEntity> getFieldPermissions() {
		return fieldPermissions;
	}

	public void setFieldPermissions(Set<FieldPermissionsEntity> fieldPermissions) {
		this.fieldPermissions = fieldPermissions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		try {
			bw.write("ProfileEntity : " + name);
			bw.newLine();
			for(FieldPermissionsEntity fpe : fieldPermissions) {
				bw.write(fpe.toString());
				bw.newLine();
			}
			bw.flush();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return sw.toString();
	}

	@Override
	public int compareTo(ProfileEntity o) {
		// TODO Auto-generated method stub
		return this.name.compareTo(o.getName());
	}
	
	
	
}
