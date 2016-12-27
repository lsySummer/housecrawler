package com.geariot.bigdata.crawler.house.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)  
@Table(name = "house",uniqueConstraints=@UniqueConstraint(columnNames = {"loc", "live", "name"}))
public class House extends HouseBase{
	private String name;
	private String loc;
	private double lowarea;//最小面积
	private double higharea;//最大面积
	private String houseType;//四居、五居等等
	private String onsold;
	private String live;
	private String propertyYear;//产权
	private String developers;//开发商
	private String features;//特色
	private String decoration;//装修状况
	private String openTime;//开盘时间
	private String deliveryTime;//交房时间
	private String traffic;//交通状况
	private String support;//配套设施(预留)
	private String volumeRate;//容积率
	private String greenRate;//绿化率
	private String parkingSpace;//停车位
	private String proCompany;//物业公司
	private String proMoney;//物业费



	public double getLowarea() {
		return lowarea;
	}


	public void setLowarea(double lowarea) {
		this.lowarea = lowarea;
	}


	public double getHigharea() {
		return higharea;
	}


	public void setHigharea(double higharea) {
		this.higharea = higharea;
	}


	public String getPropertyYear() {
		return propertyYear;
	}


	public void setPropertyYear(String property) {
		this.propertyYear = property;
	}


	public String getDevelopers() {
		return developers;
	}


	public void setDevelopers(String developers) {
		this.developers = developers;
	}


	public String getFeatures() {
		return features;
	}


	public void setFeatures(String features) {
		this.features = features;
	}


	public String getDecoration() {
		return decoration;
	}


	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}


	public String getOpenTime() {
		return openTime;
	}


	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}


	public String getDeliveryTime() {
		return deliveryTime;
	}


	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}


	@Basic(fetch = FetchType.LAZY)   
	@Type(type="text")  
	@Column(name="traffic", nullable=true)   
	public String getTraffic() {
		return traffic;
	}


	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}


	public String getSupport() {
		return support;
	}


	public void setSupport(String support) {
		this.support = support;
	}


	public String getVolumeRate() {
		return volumeRate;
	}


	public void setVolumeRate(String volumeRate) {
		this.volumeRate = volumeRate;
	}


	public String getGreenRate() {
		return greenRate;
	}


	public void setGreenRate(String greenRate) {
		this.greenRate = greenRate;
	}


	public String getParkingSpace() {
		return parkingSpace;
	}


	public void setParkingSpace(String parkingSpace) {
		this.parkingSpace = parkingSpace;
	}


	public String getProCompany() {
		return proCompany;
	}


	public void setProCompany(String proCompany) {
		this.proCompany = proCompany;
	}


	public String getProMoney() {
		return proMoney;
	}


	public void setProMoney(String proMoney) {
		this.proMoney = proMoney;
	}

	
	public String getHouseType() {
		return houseType;
	}


	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}


	public String getOnsold() {
		return onsold;
	}


	public void setOnsold(String onsold) {
		this.onsold = onsold;
	}


	public String getLive() {
		return live;
	}


	public void setLive(String live) {
		this.live = live;
	}

	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	@Column(name = "loc")
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}

	
	
	
	@Override
	public String toString() {
		return "House [id=" + id + ", name=" + name + ", loc=" + loc
				+ ", area=" + lowarea +","+higharea+ ", onsold=" + onsold+ ", live=" + live+", houseType=" + houseType
				+ ", price=" + price +", totalPrice=" + totalPrice+", createTime=" + createTime
				+ ", source=" + source + ", property=" + propertyYear+ ", developers=" + developers+ ", features=" + features
				+ ", decoration=" + decoration+ ", openTime=" + openTime+  ", deliveryTime=" + deliveryTime
				+ ", phone=" + phone+ ", traffic=" + traffic+ ", detail=" + detail+ ", support=" + support+ ", region=" + region
				+ ", volumeRate=" + volumeRate+ ", greenRate=" + greenRate+ ", parkingSpace=" + parkingSpace
				+ ", proCompany=" + proCompany+ ", proMoney=" + proMoney+ "]";
	}

	public House() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
