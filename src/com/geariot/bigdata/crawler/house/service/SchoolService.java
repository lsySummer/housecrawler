package com.geariot.bigdata.crawler.house.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.geariot.bigdata.crawler.house.dao.SchoolDao;
import com.geariot.bigdata.crawler.house.entities.School;
import com.geariot.bigdata.crawler.house.model.RESCODE;
import com.geariot.bigdata.crawler.house.utils.Constants;

@Service
public class SchoolService {
	@Autowired
	private SchoolDao schoolDao;
	
	@SuppressWarnings("resource")
	public String saveSchool(School s,ArrayList<String> snames,String url)
	{
		String id = "";
		JSONObject obj = new JSONObject();
		try
		{
			id = schoolDao.saveSchool(s,snames);
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(20000).get();
				Elements pics=doc.select("ul.smallpic>li");
				int j=1;
				for(Element pic:pics){
					if(j>5){
						break;
					}
					String src=pic.attr("data-src");
					URL picurl   =   new   URL(src); 
					URLConnection   uc   =   picurl.openConnection(); 
					InputStream   is   =   uc.getInputStream(); 
					File   file   =   new   File( "E:\\mapAPP\\schoolPic\\"+id+"_"+j+".jpg"); 
					FileOutputStream   out   =   new   FileOutputStream(file); 
					int   i=0; 
					while   ((i=is.read())!=-1)   { 
					out.write(i); 
					} 
					is.close();
					j++;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		catch(DataIntegrityViolationException e)
		{
			obj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DUPLICATED_ERROR);
			String msg = e.getMessage();
			msg = msg.substring(0, msg.indexOf(";"));
			obj.put(Constants.RESPONSE_MSG_KEY, "生成school " + msg + ":" + RESCODE.DUPLICATED_ERROR.getMsg());
			return obj.toString();
		}
		
		if(id == null || id.isEmpty())
		{
			obj.put(Constants.RESPONSE_CODE_KEY, RESCODE.CREATE_ERROR);
			obj.put(Constants.RESPONSE_MSG_KEY, "生成school " + id + ":" + RESCODE.CREATE_ERROR.getMsg());
			return obj.toString();
		}
		s.setId(id);
		obj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		obj.put(Constants.RESPONSE_MSG_KEY, "");
		JSONObject tmpObj = new JSONObject(s);
		obj.put(Constants.RESPONSE_DATA_KEY, tmpObj);
		return obj.toString();
	}

	
}
