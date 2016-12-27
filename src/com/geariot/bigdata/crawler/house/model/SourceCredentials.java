package com.geariot.bigdata.crawler.house.model;

public class SourceCredentials {
	public String sourceName;
	public String password;
	public String[] siteIDs;
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String[] getSiteIDs() {
		return siteIDs;
	}
	public void setSiteIDs(String[] siteIDs) {
		this.siteIDs = siteIDs;
	}
	
	
}
