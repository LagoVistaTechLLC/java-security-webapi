package com.lagovistatech.security.dto;

import java.io.InputStream;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

public class VersionDto {
	public static final VersionDto instance = new VersionDto();
	
	public VersionDto() {
		try {
			InputStream is = getClass().getResourceAsStream("/META-INF/maven/com.lagovistatech/security.webapi/pom.xml");
	        MavenXpp3Reader reader = new MavenXpp3Reader();
	        Model model = reader.read(is);

        	this.number = model.getVersion();
        	this.copyright = "(c) Copyright Lago Vista Technologies LLC";
        	this.name = "Security WebAPI";
        	this.license = "GNU Affero General Public License v3";
        	
        	is.close();
		}
		catch(Exception ex) { /* do nothing */ }
	}
	public VersionDto(String name, String number, String copyright, String license) {
		this.name = name;
		this.number = number;
		this.copyright = copyright;
		this.license = license;
	}
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private String number;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	private String copyright;
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	private String license;
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}

}
