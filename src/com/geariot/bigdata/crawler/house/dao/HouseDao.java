package com.geariot.bigdata.crawler.house.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.bigdata.crawler.house.entities.House;
import com.geariot.bigdata.crawler.house.entities.HouseExcel;
import com.geariot.bigdata.crawler.house.entities.HousePrice;
import com.geariot.bigdata.crawler.house.entities.School;
import com.geariot.bigdata.crawler.house.entities.SchoolMap;
import com.geariot.bigdata.crawler.house.entities.SecHandBean;
import com.geariot.bigdata.crawler.house.utils.GetLoc;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

@Transactional
@Repository("houseDao")
public class HouseDao {

	private static final Logger log = Logger.getLogger(HouseDao.class);

	static SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");    
	private static final String version = tempDate.format(new java.util.Date());   
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public String saveHouse(House house) {

		return getSession().save(house).toString();
	}

	public String saveHouseWithPrice(House house,String url,int i) {
		String prevId = "";
		List<House> prevLs = isHouseExist(house);
		if (prevLs != null && !prevLs.isEmpty()) {
			House prevBean = prevLs.get(0);
			prevId = prevBean.getId();
		} else {
			prevId = getSession().save(house).toString();
			if(i==0){
				getPic1(url,prevId);
			}else{
				getFangPic1(url,prevId);
			}
		}
		HousePrice pr = new HousePrice();
			try {
				double unitStr = house.getPrice();
				pr.setUnitPrice(unitStr);
			} catch (Exception e) {
				pr.setUnitPrice(-1);
			}

			try {
				double totalStr = house.getTotalPrice();
				pr.setTotalPrice(totalStr);
			} catch (Exception e) {
				pr.setTotalPrice(-1);
			}
		pr.setCreateTime(new Date());
		pr.setHouseId(prevId);
		pr.setVersion(version);
		pr.setType(0);
		if (!isSecondPriceExist(pr))
			getSession().save(pr);

		return prevId;
	}

	@SuppressWarnings("resource")
	public void getFangPic1(String url,String id){
		System.out.println("url"+url);
		Document doc0 = null;
		String detailUrl="";
		String typeUrl="";
		try {
			doc0 = Jsoup.connect(url).timeout(20000).get();
			Element xc=doc0.select("div.xc_xmdl").first();
			Elements dds=xc.select("dl>dd");
			for(Element dd:dds){
				String spanText=dd.select("a>span").text();
				if(spanText.contains("样板间")){
					detailUrl=dd.select("a").attr("href");
					break;
				}else if(spanText.contains("户型图")){
					typeUrl=dd.select("a").attr("href");
				}
			}
			if(detailUrl==""){
				detailUrl=typeUrl;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(detailUrl==""){
			return;
		}
		Document doc2 = null;
		try {
			doc2 = Jsoup.connect(detailUrl).timeout(20000).get();
			Element gundong=doc2.select("div.gundong>ul").first();
			Elements lis=gundong.select("li");
			int j=0;
			for(Element li:lis){
				if(j>8){
					break;
				}
				String src=li.select("a>img").attr("src");
				src=src.replace("124x82", "496x328");
				URL picurl   =   new   URL(src); 
				URLConnection   uc   =   picurl.openConnection(); 
				InputStream   is   =   uc.getInputStream(); 
				File   file   =   new   File( "E:\\mapAPP\\housePic\\"+id+"_"+j+".jpg"); 
				FileOutputStream   out   =   new   FileOutputStream(file); 
				int   i=0; 
				while   ((i=is.read())!=-1)   { 
				out.write(i); 
				} 
				is.close();
				j++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("resource")
	public void getPic1(String url,String id){
		  HttpUnitOptions.setScriptingEnabled(false);  
	        // 建立一个WebConversation实例    
	        WebConversation wc = new WebConversation();    
	        // 向指定的URL发出请求，获取响应    
	        WebResponse wr;
			try {
				wr = wc.getResponse(url);
				String htmlText=wr.getText();
				Document doc=Jsoup.parse(htmlText);
				Elements tabs=doc.select("div.tab-group");
				for(Element tab:tabs){
					String h4=tab.select("h4>a").text();
					if(h4.contains("样板间")){
						Elements lis=tab.select("ul>li>a>img");
						int j=1;
						for(Element li:lis){
							if(j>8){
								break;
							}
							String getPic=li.attr("src");
							getPic=getPic.replace("235x178","470x356");
							//Open a URL Stream
							System.out.println(getPic);
							URL picurl   =   new   URL(getPic); 
							URLConnection   uc   =   picurl.openConnection(); 
							InputStream   is   =   uc.getInputStream(); 
							File   file   =   new   File( "E:\\mapAPP\\housePic\\"+id+"_"+j+".jpg"); 
							FileOutputStream   out   =   new   FileOutputStream(file); 
							int   i=0; 
							while   ((i=is.read())!=-1)   { 
							out.write(i); 
							} 
							is.close();
							j++;
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	}

	
	public void saveFangSecHandHouse(SecHandBean bean, String url, int type) {
		String prevId = "";
		List<SecHandBean> prevLs = isFangSecondExist(bean);
		if (prevLs != null && !prevLs.isEmpty()) {
			SecHandBean prevBean = prevLs.get(0);
			prevId = prevBean.getId();
		} else {
			prevId = getSession().save(bean).toString();
			if (type == 0) {
				getLianPic(prevId, url);
			} else {
				getFangPic(prevId, url);
			}
		}
		String unitStr = bean.getPrice()+"";

		HousePrice pr = new HousePrice();
		pr.setCreateTime(new Date());
		pr.setHouseId(prevId);
		try {
			pr.setUnitPrice(Double.parseDouble(unitStr));
		} catch (Exception e) {
			pr.setUnitPrice(-1);
		}
		pr.setType(1);
		log.debug(pr.getHouseId());
		if (!isSecondPriceExist(pr))
			getSession().save(pr);

	}

	@SuppressWarnings("resource")
	private void getFangPic(String prevId, String url) {
		Document doc = null;
		int j = 1;
		System.out.println("url" + url);
		try {
			doc = Jsoup.connect(url).timeout(20000).get();
			Element slider = doc.select("div.sliderbox").first();
			Elements lis = slider.select("div.slider>ul>li");
			for (Element li : lis) {
				if (j > 8) {
					break;
				}
				String src = li.select("img").attr("src");
				src = src.replace("84x60", "420x300");
				System.out.println(src);
				URL picurl = new URL(src);
				URLConnection uc = picurl.openConnection();
				InputStream is = uc.getInputStream();
				File file = new File("E:\\mapAPP\\oldPic\\" + prevId + "_" + j
						+ ".jpg");
				FileOutputStream out = new FileOutputStream(file);
				int i = 0;
				while ((i = is.read()) != -1) {
					out.write(i);
				}
				is.close();
				// System.out.println(src);
				j++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private void getLianPic(String prevId, String url) {
		Document doc = null;
		int j = 1;
		try {
			doc = Jsoup.connect(url).timeout(20000).get();
			Elements lis = doc.select("ul.smallpic>li");
			for (Element li : lis) {
				if (j > 8) {
					break;
				}
				String src = li.select("img").attr("src");
				src = src.replace("120x80", "480x320");
				URL picurl = new URL(src);
				URLConnection uc = picurl.openConnection();
				InputStream is = uc.getInputStream();
				File file = new File("E:\\mapAPP\\oldPic\\" + prevId + "_" + j
						+ ".jpg");
				FileOutputStream out = new FileOutputStream(file);
				int i = 0;
				while ((i = is.read()) != -1) {
					out.write(i);
				}
				is.close();
				// System.out.println(src);
				j++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<House> isHouseExist(House bean) {
		String hql = "from House where loc = :loc and name = :name";
		Query query = getSession().createQuery(hql);
		query.setString("loc", bean.getLoc());
		query.setString("name", bean.getName());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<SecHandBean> isFangSecondExist(SecHandBean bean) {
		String hql = "from SecHandBean where intro = :intro and detail = :detail";
		Query query = getSession().createQuery(hql);
		query.setString("intro", bean.getIntro());
		query.setString("detail", bean.getDetail());
		return query.list();
	}

	public boolean isSecondPriceExist(HousePrice pr) {
		String hql = "from HousePrice where houseId = :houseId and version = :version";
		Query query = getSession().createQuery(hql);
		query.setString("houseId", pr.getHouseId());
		query.setString("version", pr.getVersion());
		@SuppressWarnings("unchecked")
		List<HousePrice> ls = query.list();
		if (ls == null || ls.isEmpty())
			return false;
		log.debug("********duplicated:" + pr.getHouseId());
		return true;
	}

	@SuppressWarnings("unchecked")
	public void insert() {
		String hql = "from SchoolMap";
		Query query = getSession().createQuery(hql);
		List<SchoolMap> mapList = query.list();
		for (SchoolMap sm : mapList) {
			HouseExcel he = new HouseExcel();
			String cname = sm.getCname();
			String sname = sm.getSname();
			he.setName(cname);
			int range = getSrange(sname);
			if (range != 999) {
				he.setSchool(sname);
				double[] data = GetLoc.addressToGPS(cname);
				if (data != null) {
					double longtitude = data[0];
					double latitude = data[1];
					he.setLongtitude(longtitude);
					he.setLatitude(latitude);
				}
				getNew(cname, he);
				getSec(cname, he);
			}
		}
		System.out.println("over");
	}

	@SuppressWarnings("unchecked")
	public int getSrange(String sname) {
		String hql = "from School where name=:sname";
		Query query = getSession().createQuery(hql);
		query.setString("sname", sname);
		List<School> ls = query.list();
		if (ls == null || ls.isEmpty()) {
			return 0;
		} else {
			return ls.get(0).getSchoolRange();
		}
	}

	@SuppressWarnings("unchecked")
	public void getNew(String name, HouseExcel he) {
		String hql = "from House where name=:name";
		Query query = getSession().createQuery(hql);
		query.setString("name", name);
		double low = -1;
		double high = -1;
		List<House> ls = query.list();
		if (ls == null || ls.isEmpty()) {

		} else {
			House h = ls.get(0);
			he.setType(0);
			double lowArea = h.getLowarea();
			double highArea = h.getHigharea();
			double priceStr = h.getPrice();
			he.setPriceScope(priceStr+"");
			try {
				low = lowArea;
				high = highArea;
				List<Double> list=new ArrayList<Double>();
				list.add(low);
				list.add(high);
				String result = getAreaLevel(removeSame(list));
				he.setArea(result);
			} catch (Exception e) {
				he.setArea("未知");
			}
			try {
				double price = priceStr;
				if (price < 10000) {
					he.setPrice("10000以下 ");
				} else if (price >= 10000 && price < 15000) {
					he.setPrice("10000-15000 ");
				} else if (price >= 15000 && price < 20000) {
					he.setPrice("15000-20000 ");
				} else if (price >= 20000 && price < 25000) {
					he.setPrice("15000-25000 ");
				} else if (price >= 25000 && price < 30000) {
					he.setPrice("25000-30000 ");
				} else if (price >= 30000) {
					he.setPrice("30000以上 ");
				}
			} catch (Exception e) {
				he.setPrice("待定");
			}
			System.out.println(he.toString());
			 getSession().save(he);
		}
	}

	@SuppressWarnings("unchecked")
	public void getSec(String name, HouseExcel he) {
		String hql = "from SecHandBean where community=:name";
		Query query = getSession().createQuery(hql);
		query.setString("name", name);
		List<SecHandBean> ls = query.list();
		if (ls == null || ls.isEmpty()) {
		} else {
			he.setType(1);
			List<Double> areaList = new ArrayList<Double>();
			List<Double> priceList = new ArrayList<Double>();
			for (SecHandBean sec : ls) {
				areaList.add(sec.getArea());
				priceList.add(sec.getPrice());
			}
			areaList=removeSame(areaList);
			priceList=removeSame(priceList);
			
			he.setArea(getAreaLevel(areaList));
			double plow = getLow(priceList);
			double phigh = getHigh(priceList);
			String priceScope = plow + "-" + phigh;
			he.setPriceScope(priceScope);
			String priceLevel = getPriceLevel(priceList);
			he.setPrice(priceLevel);
			System.out.println(he.toString());
			 getSession().save(he);
		}
	}

	public double getLow(List<Double> list) {
		double min = Double.MAX_VALUE;
		for (double d : list) {
			if (d < min) {
				min = d;
			}
		}
		return min;
	}

	public double getHigh(List<Double> list) {
		double max = Double.MIN_VALUE;
		for (double d : list) {
			if (d > max) {
				max = d;
			}
		}
		return max;
	}

	public List<Double> removeSame(List<Double> list) {
		List<Double> listTemp = new ArrayList<Double>();
		Iterator<Double> it = list.iterator();
		while (it.hasNext()) {
			double a = it.next();
			if (listTemp.contains(a)) {
				it.remove();
			} else {
				listTemp.add(a);
			}
		}
		Collections.sort(listTemp);
		return listTemp;
	}

	public String getAreaLevel(List<Double> list) {
		String arr[] = { "低于60 ", "60-90 ", "90-110 ", "110-130 ", "130-150 ",
				"150-200 ", "高于200 " };
		String result="";
		int signal0=0,signal1=0,signal2=0,signal3=0,signal4=0,signal5=0,signal6=0;
		for(int i=0;i<list.size();i++){
			double low=list.get(i);
			if (low > 0 && low < 60) {
				if(signal0==0){
				result=result+arr[0];
				signal0=1;
				}
			} else if (low >= 60 && low < 90) {
				if(signal1==0){
				result=result+arr[1];
				signal1=1;
				}
			} else if (low >= 90 && low < 110) {
				if(signal2==0){
				result=result+arr[2];
				signal2=1;
				}
			} else if (low >= 110 && low < 130) {
				if(signal3==0){
				result=result+arr[3];
				signal3=1;
			}
			} else if (low >= 130 && low < 150) {
				if(signal4==0){
				result=result+arr[4];
				signal4=1;
			}
			} else if (low >= 150 && low < 200) {
				if(signal5==0){
				result=result+arr[5];
				signal5=1;
			}
			} else if (low >= 200) {
				if(signal6==0){
				result=result+arr[6];
				signal6=1;
			}
			}
		}

		return result.trim();
	}

	public String getPriceLevel(List<Double> list) {
		String arr[] = { "1万以下 ", "1-1.5万 ", "1.5-2万 ", "2-2.5万 ", "2.5-3万 ",
				"3万以上 "};
		String result="";
		int signal0=0,signal1=0,signal2=0,signal3=0,signal4=0,signal5=0;
		for(int i=0;i<list.size();i++){
			double low=list.get(i);
			if (low > 0 && low < 10000) {
				if(signal0==0){
					result=result+arr[0];
					signal0=1;
				}
			} else if (low >= 10000 && low < 15000) {
				if(signal1==0){
				result=result+arr[1];
				signal1=1;
				}
			} else if (low >= 15000 && low < 20000) {
				if(signal2==0){
				result=result+arr[2];
				signal2=1;
				}
			} else if (low >= 20000 && low < 25000) {
				if(signal3==0){
				result=result+arr[3];
				signal3=1;
				}
			} else if (low >= 25000 && low < 30000) {
				if(signal4==0){
				result=result+arr[4];
				signal4=1;
				}
			} else if (low >= 30000) {
				if(signal5==0){
				result=result+arr[5];
				signal5=1;
				}
			}
		}
		return result.trim();
	}
}
