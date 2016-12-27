package com.geariot.bigdata.crawler.house.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "house_price",uniqueConstraints=@UniqueConstraint(columnNames = {"version", "house_id"}))
public class HousePrice {

	private String id;
	
	private String houseId;
	
	
	private double unitPrice;

	private double totalPrice;
	private String version;
	
	private Date createTime;

	private int type;//0代表新房，1代表二手房
	
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

	@Column(name = "house_id")
	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}


	
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "unit_price")
	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@Column(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public HousePrice() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "HousePrice [id=" + id + ", houseId=" + houseId + ", unitPrice="
				+ unitPrice + ", createTime=" + createTime + "]";
	}

	
	
}
