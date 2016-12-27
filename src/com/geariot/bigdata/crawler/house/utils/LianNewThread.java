package com.geariot.bigdata.crawler.house.utils;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.geariot.bigdata.crawler.house.entities.House;
import com.geariot.bigdata.crawler.house.service.HouseService;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class LianNewThread extends Thread{

	private int start;
	private int end;
	private HouseService houseService;
	
	public LianNewThread(int start, int end, HouseService houseService)
	{
		this.start = start;
		this.end = end;
		this.houseService= houseService;
	}
	
	public void run()
	{
		for(int i = start; i<= end; i++)
		{
			System.out.println(i+"页开始");
			String url = "http://nj.fang.lianjia.com/loupan/pg"+i+"/";
			
			   HttpUnitOptions.setScriptingEnabled(false);  
		        // 建立一个WebConversation实例    
		        WebConversation wc = new WebConversation();    
		        // 向指定的URL发出请求，获取响应    
		        WebResponse wr;
				try {
					wr = wc.getResponse(url);
					String htmlText=wr.getText();
					Document doc=Jsoup.parse(htmlText);
					Elements divs=doc.select("div.info-panel");
					for(Element div:divs){
						House h = new House();
						h.setType(0);
						Element titleEle=div.select("span.region").first();
						h.setLoc(titleEle.text());
						Element areaEle=div.select("div.area").first();
						String area=areaEle.text();
						String[] areaArr=area.split("-");
						if(areaArr.length==2){
							h.setHouseType(areaArr[0]);
							String area0=areaArr[1].trim().replace("平米", "");
							String[] arArr=area0.split("～");
							if(arArr.length==2){
								h.setLowarea(Double.parseDouble(arArr[0]));
								h.setHigharea(Double.parseDouble(arArr[1]));
							}
						}
						Element other=div.select("div.other").first();
						if(other!=null){
							h.setFeatures(other.text());
						}
						Element aEle=div.select("a").first();
						h.setName(aEle.text());
						double [] data = GetLoc.addressToGPS(aEle.text());
						if(data==null||data.length<2){
							double [] locdata = GetLoc.addressToGPS(titleEle.text());
							h.setLongtitude(locdata[0]);
							h.setLatitude(locdata[1]);
						}else{
							h.setLongtitude(data[0]);
							h.setLatitude(data[1]);
						}
						Element onsold=div.select("span.onsold").first();
						h.setOnsold(onsold.text());
						Element live=div.select("span.live").first();
						h.setLive(live.text());
						Element priceEle=div.select("div.average").first();
						String price=priceEle.text();
						if(price.contains("均价")){
							h.setPrice(Double.parseDouble(price.replace("均价 ", "").split(" ")[0]));
						}else if(price.contains("总价")){
							h.setTotalPrice(Double.parseDouble(price.replace("总价 ", "").split(" ")[0]));
						}
						h.setSource("lianjia");
						h.setCreateTime(new Date());
						String link=aEle.attr("href");
						String[] arr=link.split("/");
						String url1="http://nj.fang.lianjia.com/loupan/"+arr[2];
						String picUrl=url1+"/xiangce/";
						getDetail(h,url1);
						System.out.println("lian "+h.toString());
						houseService.saveHouse(h,picUrl,0);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}  
			System.out.println(i+"页结束");
		}
	}
	
	public void getDetail(House h,String url){

		   HttpUnitOptions.setScriptingEnabled(false);  
	        // 建立一个WebConversation实例    
	        WebConversation wc = new WebConversation();    
	        // 向指定的URL发出请求，获取响应    
	        WebResponse wr;
			try {
				wr = wc.getResponse(url);
				String htmlText=wr.getText();
				Document doc=Jsoup.parse(htmlText);
				Element phone=doc.select("span.phone-s").first();
				h.setPhone(phone.text());
				Element bread=doc.select("div.breadcrumbs>a").get(3);
				String region=bread.text().substring(0, bread.text().length()-2);
				h.setRegion(region);
				Elements details=doc.select("p.desc-p");
				if(details.size()==21){
				h.setDevelopers(details.get(3).text().replace("开发商： ", ""));
				h.setProCompany(details.get(4).text().replace("物业公司： ", ""));
				h.setOpenTime(details.get(5).text().replace("最新开盘： ", ""));
				
				h.setDeliveryTime(details.get(7).text().replace("最早交房： ", ""));
				h.setVolumeRate(details.get(8).text().replace("容积率：", "").trim());
				h.setPropertyYear(details.get(9).text().replace("产权年限：", "").trim().replace("年", ""));
				h.setGreenRate(details.get(10).text().replace("绿化率： ", ""));
				h.setProMoney(details.get(12).text().replace("物业费用：", "").trim());
				h.setParkingSpace(details.get(13).text().replace("车位情况： ", ""));
				h.setDecoration(details.get(15).text().replace("装修状况： ", ""));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}   
		
	}
	
	
}
