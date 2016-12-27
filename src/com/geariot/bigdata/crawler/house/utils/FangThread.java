package com.geariot.bigdata.crawler.house.utils;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.geariot.bigdata.crawler.house.entities.SecHandBean;
import com.geariot.bigdata.crawler.house.service.HouseService;

public class FangThread extends Thread{
	private int start;
	private int end;
	private HouseService houseService;
	
	public FangThread(int start, int end, HouseService houseService)
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
			String url = "http://esf.nanjing.fang.com/house/i3" + i + "/";
			
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(20000).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Elements blocks = doc.select("div.houseList >dl.list >dd.info");
			for(Element blk: blocks)
			{
				SecHandBean sec = new SecHandBean();
				sec.setType(1);
				sec.setIntro(blk.select("p.title").text());
				String houseInfo=blk.select("p.mt12").text();
				sec.setDetail(houseInfo);
				String oldEasyTra=blk.select("div.mt8>div.pt4>span").text();
				if(oldEasyTra!=""){
					String green=blk.select("div.mt8>div.pt4>span.colorGreen").text();
					String easyTraffic=blk.select("div.mt8>div.pt4>span.train").text();
						sec.setRemark(green);
						sec.setEasyTraffic(easyTraffic);
				}
				sec.setPerson(blk.select("p.gray6>a").text());
				String oldArea=blk.select("div.area>p").text();
				sec.setArea(Double.parseDouble(oldArea.substring(0,oldArea.length()-6)));
				String oldPrice=blk.select("div.moreInfo>p.danjia").text();
				sec.setPrice(Double.parseDouble(oldPrice.substring(0,oldPrice.length()-3)));
				sec.setTotalPrice(Double.parseDouble(blk.select("div.moreInfo>p.mt5>span.price").text()));
				sec.setSource("fangtianxia");
				sec.setCreateTime(new Date());
				String link=blk.select("p.title>a").attr("abs:href");
				Document newdoc = null;
				try {
					newdoc = Jsoup.connect(link).timeout(20000).get();
					String phone = newdoc.select("div.phone_top>p>label").text();
					sec.setPhone(phone);
					String describe=newdoc.select("div.leftBox>div.describe").get(0).select("div").text();
					sec.setSellingPoint(describe);
					Elements ddInfo = newdoc.select("div.inforTxt>dl ").get(1).select("dd");
					for(Element dd: ddInfo)
					{
						if(dd.select("span.gray6").text().contains("建筑类别：")){
							String[] arr=dd.text().split("：");
							if(arr.length>0){
								sec.setMaterial(arr[1]);
							}
						}
						else if(dd.select("span.gray6").text().contains("产权性质：")){
							String[] arr=dd.text().split("：");
							if(arr.length>0){
								sec.setProperty(arr[1]);
							}
						}
					}
					Elements dtInfo=newdoc.select("div.inforTxt>dl ").get(1).select("dt");
					for(Element dt: dtInfo)
					{
						if(dt.select("span.gray6").text().contains("学")){
							sec.setSchool(dt.select("a").text());
						}
						else if(dt.select("span.gray6").text().contains("楼盘名称：")){
							String[] arr=dt.text().split("\\s+");
							sec.setRegion(arr[1]);
							sec.setSubRegion(arr[2]);
							String[] arr2=dt.text().split("\\(");
							String community=arr2[0].split("：")[1];
							sec.setCommunity(community);
							double [] data = GetLoc.addressToGPS(community);
							if(data==null||data.length<2){
							}else{
								sec.setLongtitude(data[0]);
								sec.setLatitude(data[1]);
							}
						}
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(sec.toString());
				houseService.httpSecondHouse(sec,link,1);
			}
			System.out.println(i+"页结束");
		}
	}
}
