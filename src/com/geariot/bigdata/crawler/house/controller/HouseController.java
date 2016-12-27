package com.geariot.bigdata.crawler.house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geariot.bigdata.crawler.house.service.HouseService;
import com.geariot.bigdata.crawler.house.service.SchoolService;
import com.geariot.bigdata.crawler.house.utils.FangNewThread;
import com.geariot.bigdata.crawler.house.utils.FangThread;
import com.geariot.bigdata.crawler.house.utils.ProcessThread;


@Controller
@RequestMapping("/house")
public class HouseController {

	@Autowired
	private HouseService houseService;
	
	@Autowired
	private SchoolService schoolService;
	
	@ResponseBody
	@RequestMapping(value = "/secondsoup")
	public String secondcrawl()
	{
//		FangThread t1=new FangThread(1,10,houseService);
//		t1.start();
//		ProcessThread t2 = new ProcessThread(1,10,houseService);
//		t2.start();
		
//		SchoolThread s=new SchoolThread(4,4,schoolService);
//		s.start();
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/fangCrawl")
	public String FangNewcrawl()
	{
		FangNewThread t1=new FangNewThread(1,10,houseService);
		t1.start();
//		LianNewThread t2=new LianNewThread(1,10,houseService);
//		t2.start();
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert")
	public String insert()
	{
		houseService.insert();
		return "{\"success\"}";
	}
	
	
}
