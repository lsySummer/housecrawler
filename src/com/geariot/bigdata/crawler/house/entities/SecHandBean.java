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
@Table(name = "sec_hand",uniqueConstraints=@UniqueConstraint(columnNames = {"community", "intro"}))
public class SecHandBean extends HouseBase  implements Cloneable{
	private String intro;//简介
	private String easyTraffic;//附近的地铁站信息等
	private double area;//面积
	private String remark;//备注
	private String person;//发布人
	private String material;//材质
	private String property;//产权性质
	private String school;//学校
	private String sellingPoint;//卖点(包含一系列具体信息)
	private String subRegion;//街道
	private String community;//小区
	
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getEasyTraffic() {
		return easyTraffic;
	}
	public void setEasyTraffic(String easyTraffic) {
		this.easyTraffic = easyTraffic;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getSubRegion() {
		return subRegion;
	}
	public void setSubRegion(String subRegion) {
		this.subRegion = subRegion;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	@Basic(fetch = FetchType.LAZY)   
	@Type(type="text")  
	@Column(name="sellingPoint", nullable=true)   
	public String getSellingPoint() {
		return sellingPoint;
	}
	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	
	@Override
	public String toString() {
		return "SecHandHouseDetail [id=" + id + ", intro=" + intro + ", detail="
				+ detail  
				+ ", easyTraffic=" + easyTraffic + ", area=" + area
				+ ", price=" + price + ", totalprice=" + totalPrice +", remark=" + remark +", source=" + source +", "
						+ "createTime=" + createTime +", person=" + person 
						+", material=" + material+", property=" + property 
						+", region=" + region +", subRegion=" + subRegion +", community=" + community +
						", school=" + school +", sellingPoint=" + sellingPoint +", phone=" + phone
						+"]";
	}
	
	 @Override  
	    public Object clone() {  
	        SecHandBean stu = null;  
	        try{  
	            stu = (SecHandBean)super.clone();  
	        }catch(CloneNotSupportedException e) {  
	            e.printStackTrace();  
	        }  
	        return stu;  
	    }  
	
}
