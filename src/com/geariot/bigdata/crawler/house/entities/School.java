package com.geariot.bigdata.crawler.house.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "school",uniqueConstraints=@UniqueConstraint(columnNames = {"name", "region"}))
public class School {
	private String id;
	private String name;
	private String intro;
	private String region;//区
	private String way;	//升学方式
	private String loc;
	private int scribing;//划片
	private String totalPrice;	//总价(起价)
	private String lowPrice;//单价范围
	private String highPrice;
	private String property;//办学性质
	private String limitNum;//名额限制
	private String rules;//招生简章
	private int schoolRange;//排名
	private int photoNum;
	
	public int getSchoolRange() {
		return schoolRange;
	}
	public void setSchoolRange(int schoolRange) {
		this.schoolRange = schoolRange;
	}
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int getPhotoNum() {
		return photoNum;
	}
	public void setPhotoNum(int photoNum) {
		this.photoNum = photoNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public int getScribing() {
		return scribing;
	}
	public void setScribing(int scribing) {
		this.scribing = scribing;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}
	public String getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	
	public String getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(String limitNum) {
		this.limitNum = limitNum;
	}
	@Override
	public String toString() {
		return "House [id=" + id + ", name=" + name + ", intro=" + intro + ", region=" + region + ", way=" + way
				 + ", loc=" + loc + ", scribing=" + scribing + ", totalPrice=" + totalPrice + ", lowPrice=" + lowPrice +
				 ", highPrice=" + highPrice+ ", property=" + property+ ", limitNum=" + limitNum+ ", rules=" + rules+ ", schoolRange=" + schoolRange
				+ "]";
	}

}
