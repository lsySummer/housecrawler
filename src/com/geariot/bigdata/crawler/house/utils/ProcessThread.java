package com.geariot.bigdata.crawler.house.utils;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.geariot.bigdata.crawler.house.entities.SecHandBean;
import com.geariot.bigdata.crawler.house.service.HouseService;


public class ProcessThread extends Thread{

	private int start;
	private int end;
	private HouseService houseService;
	
	public ProcessThread(int start, int end, HouseService houseService)
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
			String url = "http://nj.lianjia.com/ershoufang/pg" + i + "/";
			
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(20000).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Elements blocks = doc.select("ul.listContent >li.clear>div.info ");
			for(Element blk: blocks)
			{
				SecHandBean sec = new SecHandBean();
				sec.setType(1);
				sec.setIntro(blk.select("div.title").text());
				sec.setDetail(blk.select("div.address>div.houseInfo").text());
				sec.setMaterial(blk.select("div.flood>div.positionInfo").text());
				String price=blk.select("div.priceInfo>div.totalPrice").text();
				sec.setTotalPrice(Double.parseDouble(price.substring(0,price.length()-1)));
				String unitPrice=blk.select("div.priceInfo>div.unitPrice>span").text();
				String priceArr=unitPrice.substring(2, unitPrice.length()-4);
				sec.setPrice(Double.parseDouble(priceArr));
				sec.setSource("lianjia");
				sec.setCreateTime(new Date());
				String link=blk.select("div.title>a").attr("href");
				getDetail(link,sec);
				houseService.httpSecondHouse(sec,link,0);
				System.out.println("lian "+sec.toString());
			}
			System.out.println(i+"页结束");
		}
	}
	
	public void getDetail(String link, SecHandBean sec) {
		Document doc = null;
		try {
			doc = Jsoup.connect(link).timeout(20000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sec.setArea(Double.parseDouble(doc.select("div.houseInfo>div.area>div.mainInfo").text().replace("平米", "")));
		sec.setSchool(doc.select("div.aroundInfo>div.schoolName>span.info").text());
		String community=doc.select("div.aroundInfo>div.communityName>a").text().replace(" 地图", "");
		sec.setCommunity(community);
		double [] data = GetLoc.addressToGPS(community);
		if(data==null||data.length<2){
		}else{
			sec.setLongtitude(data[0]);
			sec.setLatitude(data[1]);
		}
		String region=doc.select("div.aroundInfo>div.areaName>span.info>a").first().text();
		String street=doc.select("div.aroundInfo>div.areaName>span.info>a").get(1).text();
		sec.setRegion(region);
		sec.setSubRegion(street);
		String property=doc.select("div.transaction>div.content>ul>li").get(1).text().replace("交易权属", "");
		sec.setProperty(property);
		String remark0=doc.select("div.transaction>div.content>ul>li").get(4).text().replace("房本年限", "");
		String remark1=doc.select("div.transaction>div.content>ul>li").get(6).text().replace("是否唯一", "");
		String remark=remark0+remark1;
		sec.setRemark(remark);
		sec.setEasyTraffic(doc.select("div.aroundInfo>div.areaName>a.supplement").text());
		sec.setPerson(doc.select("div.brokerInfoText>div.brokerName>a ").text().replace(" 沟通", ""));
		sec.setPhone(doc.select("div.brokerInfoText>div.phone").text());
		sec.setSellingPoint(doc.select("div.baseinform>div.introContent ").text());
	}

}
