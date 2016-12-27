package com.geariot.bigdata.crawler.house.model;

public class UserCredentials {
	public String username;
	public String password;
	public String[] SiteIDs;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String[] getSiteIDs() {
		return SiteIDs;
	}
	public void setSiteIDs(String[] siteIDs) {
		SiteIDs = siteIDs;
	}
	
	
}
