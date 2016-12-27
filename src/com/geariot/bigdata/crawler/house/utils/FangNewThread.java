package com.geariot.bigdata.crawler.house.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.geariot.bigdata.crawler.house.entities.House;
import com.geariot.bigdata.crawler.house.service.HouseService;

public class FangNewThread extends Thread {
	private int start;
	private int end;
	private HouseService houseService;

	public FangNewThread(int start, int end, HouseService houseService) {
		this.start = start;
		this.end = end;
		this.houseService = houseService;
	}

	@SuppressWarnings("rawtypes")
	public void run() {
		for (int i = start; i <= end; i++) {
			System.out.println(i+"页开始");
			String url = "http://newhouse.nanjing.fang.com/house/s/b9" + i
					+ "/";
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(20000).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Elements blocks = doc.select("div.clearfix >ul>li>div.clearfix>div.nlc_details");
			for (Element blk : blocks) {
				House sec = new House();
				sec.setType(0);
				String houseType=blk.select("div.house_type >a").text();
				if(!houseType.equals("楼层平面图")){
					sec.setHouseType(houseType);
				}
				String[] areaArr = blk.select("div.house_type ").text()
						.split("－");
				if (areaArr.length >1) {
					String area=areaArr[1].substring(1).replace("平米", "");
					String[] arArr=area.split("~");
					if(arArr.length==2){
						sec.setLowarea(Double.parseDouble(arArr[0]));
						sec.setHigharea(Double.parseDouble(arArr[1]));
					}
				}
				String name=blk.select("div.house_value >div.nlcd_name>a")
						.text();
				sec.setName(name);
				String loc=blk.select(
						"div.relative_message >div.address>a").attr("title");
				sec.setLoc(loc);
				double [] data = GetLoc.addressToGPS(name);
				if(data==null||data.length<2){
					double [] locdata = GetLoc.addressToGPS(loc);
					if(locdata==null||locdata.length<2){
						break;
					}else{
						sec.setLongtitude(locdata[0]);
						sec.setLatitude(locdata[1]);
					}
				}else{
				sec.setLongtitude(data[0]);
				sec.setLatitude(data[1]);
				}
				sec.setRegion(blk.select("div.relative_message >div.address>a").text().split("]")[0].substring(1));
				String insale = blk.select("div.fangyuan>span.inSale").text();
				sec.setOnsold(insale);
				if (insale == null || insale.equals("")) {
					sec.setOnsold("待售");
				} else {
				}
				sec.setLive(blk.select("div.fangyuan>a").text());
				String priceType = blk.select("div.nhouse_price>em").text();
				if (priceType.contains("套")) {
					sec.setTotalPrice(Double.parseDouble(blk.select("div.nhouse_price>span")
							.text()));
					sec.setPrice(-1);
				} else {
					sec.setPrice(Double.parseDouble(blk.select("div.nhouse_price>span").text()));
					sec.setTotalPrice(-1);
				}
				sec.setSource("fangtianxia");
				sec.setCreateTime(new Date());
				String link = blk.select("div.house_value >div.nlcd_name>a")
						.attr("abs:href");
				Document newdoc = null;
				try {
					newdoc = Jsoup.connect(link).timeout(20000).get();
					Elements es = newdoc.select("a");
					for (Iterator it = es.iterator(); it.hasNext();) {
						Element e = (Element) it.next();
						if (e.text().contains("更多详细信息")||(e.text().contains("详细信息")&&e.text().contains("查看"))) {
							link = e.attr("href");
							getDetail(link, sec);
							break;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("fang "+sec.toString());
			}
			System.out.println(i+"页结束");
		}
	}

	public void getDetail(String link, House sec) {
		Document doc = null;
		try {
			doc = Jsoup.connect(link).timeout(20000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element navbox=doc.select("div.navbox").first();
		Elements aEles=navbox.select("a");
		String url="";
		for(Element ele:aEles){
			String info=ele.text();
			if(info.contains("楼盘相册")){
				url=ele.attr("href");
				break;
			}
		}
		Elements blocks = doc.select("div.main-left");
		if(blocks.size()==0){
			Elements tds = doc.select("table>tbody>tr>td");
			for (Element td : tds) {
				if(td.select("strong").text().equals("装修状况")){
					String decoration=td.text().replace("装修状况 ", "").replace(" [装修相册][建材卖场]", "");
					sec.setDecoration(decoration);
				}else if(td.select("strong").text().equals("项目特色")){
					sec.setFeatures(td.text().replace("项目特色 ", ""));
				}else if(td.select("strong").text().equals("容 积 率")){
					sec.setVolumeRate(td.text().replace("容 积 率 ", "").replace(" [容积率详情]", ""));
				}else if(td.select("strong").text().equals("绿 化 率")){
					sec.setGreenRate(td.text().replace("绿 化 率 ", "").replace(" [绿化率详情]", ""));
				}else if(td.select("strong").text().equals("产权年限")){
					sec.setPropertyYear(td.text().replace("产权年限 ", ""));
				}else if(td.select("strong").text().equals("开盘时间")){
					String openTime=td.text().replace("开盘时间 ", "").replace("[开盘时间详情]", "");
					sec.setOpenTime(openTime);
				}else if(td.select("strong").text().equals("入住时间")){
					sec.setDeliveryTime(td.text().replace("入住时间 ", "").replace(" [交房时间详情]", ""));
				}else if(td.select("strong").text().equals("物业管理费")){
					sec.setProMoney(td.text().replace("物业管理费 ", ""));
				}
				
			}
			Elements others=doc.select("div.lineheight");
			sec.setTraffic(others.get(2).text());
			sec.setParkingSpace(others.get(5).text().replace("停车位描述：", ""));
			sec.setDetail(others.get(6).text());
			Elements lasts=doc.select("div.lineheight").get(7).select("strong");
			for(Element last:lasts){
				if(last.text().equals("开发商:")){
					sec.setDevelopers(last.nextElementSibling().text());
				}else if(last.text().equals("物业管理公司:")){
					sec.setProCompany(last.nextElementSibling().text());
				}
			}
		}else{
			Elements detail = blocks.select("div.main-item >ul.list>li");
			sec.setFeatures(detail.get(1).select("div.list-right").text());
			String decoration=detail.get(3).select("div.list-right").text();
			if(decoration.contains("[")){
				decoration=decoration.split("\\[")[0];
			}
			sec.setDecoration(decoration);
			sec.setPropertyYear(detail.get(4).select("div.list-right").text().replace("年", ""));
			sec.setDevelopers(detail.get(6).select("div.list-right>a").text());
			sec.setOpenTime(detail.get(10).select("div.list-right").text().replace("[开盘时间详情]", ""));
			sec.setDeliveryTime(detail.get(11).select("div.list-right").text());
			sec.setPhone(detail.get(13).select("div.list-right").text());
			sec.setVolumeRate(detail.get(18).select("div.list-right").text());
			sec.setGreenRate(detail.get(19).select("div.list-right").text());
			sec.setParkingSpace(detail.get(20).select("div.list-right").text());
			sec.setProCompany(detail.get(23).select("div.list-right").text());
			sec.setProMoney(detail.get(24).select("div.list-right").text());
			Element traffic=blocks.select("div.main-item").get(2);
			sec.setTraffic(traffic.select("div.bd-1>p").text());
			Element detailInfo=blocks.select("div.main-item").get(blocks.select("div.main-item").size()-1);
			sec.setDetail(detailInfo.select("div>p").text());
		}
		houseService.saveHouse(sec,url,1);
	}
	
}
