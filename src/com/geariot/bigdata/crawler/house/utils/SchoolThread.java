package com.geariot.bigdata.crawler.house.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.geariot.bigdata.crawler.house.entities.School;
import com.geariot.bigdata.crawler.house.service.SchoolService;

public class SchoolThread extends Thread{
	private SchoolService schoolService;
	private int start;
	private int end;
	
	public SchoolThread(int start, int end,SchoolService schoolService)
	{
		this.start = start;
		this.end = end;
		this.schoolService= schoolService;
	}
	
	public void run(){
		for(int i = start; i<= end; i++)
		{
			System.out.println(i+"页开始");
			updateFromFang();
//			String url = "http://nj.lianjia.com/xuequfang/pg"+i+"/";
//			
//			Document doc = null;
//			try {
//				doc = Jsoup.connect(url).timeout(20000).get();
//				Elements blocks = doc.select("div.wrapper");
//				Element wrapper=blocks.get(2);
//				Elements lis=wrapper.select("ul>li");
//				for(int j=0;j<25;j++){
//					Element li=lis.get(j);
//					School s = new School();
//					String name=li.select("div.name").text();
//					s.setName(name);
//					String school=li.select("div.school").text();
//					s.setIntro(school);
//					String feature=li.select("div.featured").text();
//					s.setWay(feature);
//					String address=li.select("div.addres").text();
//					String[] arr=address.split("\\|");
//					s.setRegion(arr[0].trim());
//					s.setLoc(arr[1].trim());
//					String total=li.select("p.total-price").text();
//					s.setTotalPrice(total.replace("万起", ""));
//					Element hrefEle=li.select("a").first();
//					String link=hrefEle.attr("href");
//					String linkUrl="http://nj.lianjia.com"+link;
//					String unit=li.select("p.unit-price").text();
//					String[] priceArr=unit.split("-");
//					if(priceArr.length==2){
//						s.setLowPrice(priceArr[0].replace("元/平", "").trim());
//						s.setHighPrice(priceArr[1].replace("元/平", "").trim());
//						String num=li.select("p.num>a").text();
//						String[] nArr=num.split(" ");
//						String nstr=nArr[0].replace("个", "");
//						s.setScribing(Integer.parseInt(nstr));
//						getDetail(linkUrl,s,0);
//					}else{
//						getDetail(linkUrl,s,1);
//					}
//					System.out.println(s.toString());
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			System.out.println(i+"页结束");
		}
		}
	
	public void updateFromFang(){
		String url = "http://m.fang.com/schoolhouse/nanjing/";
		
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(20000).get();
			Elements blocks = doc.select("section.houseList>ul>li");
			System.out.println("size"+blocks.size());
			for(Element block:blocks){
				String link=block.select("a").attr("href");
				System.out.println(link);
			}
		}catch(Exception e){
			
		}
	}
	
	public void getDetail(String url,School s,int signal){
		Document doc = null;
		ArrayList<String> nameArr=new ArrayList<String>();
		try {
			doc = Jsoup.connect(url).timeout(20000).get();
			Element right=doc.select("div.m-right").first();
			Elements lis=right.select("ul>li");
			for(Element li:lis){
				Element label=li.select("label").first();
				if(label.text().equals("办学性质")){
					s.setProperty(label.text());
				}else if(label.text().equals("名额限制")){
					s.setLimitNum(label.text());
				}
			}
			if(signal==0){
				
				Element school=doc.select("div.school-list").first();
				Elements snames=school.select("div.li");
				for(Element sname:snames){
					String name=sname.select("span.names>a").text();
					nameArr.add(name);
				}
				schoolService.saveSchool(s,nameArr,url);
			}else{
				schoolService.saveSchool(s,null,url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
