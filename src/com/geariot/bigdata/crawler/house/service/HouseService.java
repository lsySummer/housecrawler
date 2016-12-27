package com.geariot.bigdata.crawler.house.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.geariot.bigdata.crawler.house.dao.HouseDao;
import com.geariot.bigdata.crawler.house.entities.House;
import com.geariot.bigdata.crawler.house.entities.SecHandBean;
import com.geariot.bigdata.crawler.house.model.RESCODE;
import com.geariot.bigdata.crawler.house.utils.Constants;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;



@Service
public class HouseService {

	@Autowired
	private HouseDao houseDao;
	

	public String saveHouse(House house,String url,int i)
	{
		String id = "";
		JSONObject obj = new JSONObject();
		try
		{
			id = houseDao.saveHouseWithPrice(house,url,i);
		}
		catch(DataIntegrityViolationException e)
		{
			obj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DUPLICATED_ERROR);
			String msg = e.getMessage();
			msg = msg.substring(0, msg.indexOf(";"));
			obj.put(Constants.RESPONSE_MSG_KEY, "生成house " + msg + ":" + RESCODE.DUPLICATED_ERROR.getMsg());
			return obj.toString();
		}
		
		if(id == null || id.isEmpty())
		{
			obj.put(Constants.RESPONSE_CODE_KEY, RESCODE.CREATE_ERROR);
			obj.put(Constants.RESPONSE_MSG_KEY, "生成house " + id + ":" + RESCODE.CREATE_ERROR.getMsg());
			return obj.toString();
		}
		house.setId(id);
		obj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		obj.put(Constants.RESPONSE_MSG_KEY, "");
		JSONObject tmpObj = new JSONObject(house);
		obj.put(Constants.RESPONSE_DATA_KEY, tmpObj);
		return obj.toString();
	}
	
	
	
	public void httpSecondHouse(SecHandBean ls,String url,int type)
	{
		try
		{
			houseDao.saveFangSecHandHouse(ls,url,type);
		}
		catch(DataIntegrityViolationException e)
		{
			
		}
	}
	
	public void insert(){
		houseDao.insert();
	}
}
