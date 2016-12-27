package com.geariot.bigdata.crawler.house.utils;

import java.io.File;

/**
 * 常量类，存储项目中需要用的常�?
 * @author haizhe
 *
 */
public class Constants {

	//存储session中id的key名称
	public static final String SESSION_SHOW_ID = "_showId";
	//存储session中name的key名称
	public static final String SESSION_SHOW_NAME = "_showName";
	
	
	public static final String VERSION_KEY = "version"; //version的key名称
	public static final String URL_KEY = "url"; //url的key名称
	public static final String COMMENT_KEY = "comment"; //comment的key名称
	
	public static final String RESPONSE_CODE_KEY = "code"; //返回对象里的code的key名称
	public static final String RESPONSE_MSG_KEY = "msg"; //返回对象里的msg的key名称
	public static final String RESPONSE_DATA_KEY = "data"; //返回对象里的data的key名称
	public static final String RESPONSE_ADDITION_KEY = "addition"; //返回对象里的addition的key名称
	public static final String RESPONSE_OPTIONAL_KEY = "optional"; //返回对象里的optional的key名称

	public static final String RESPONSE_CITY_KEY = "city"; //返回对象里的city的key名称
	public static final String RESPONSE_TOKEN_KEY = "token"; //返回对象里的token的key名称

	public static final String RESPONSE_CREATE_TIME_KEY = "createTime";
	public static final String RESPONSE_LOGIN_TIME_KEY = "loginTime";

	public static final String REQUEST_SIG_KEY = "sig"; //请求里的sig的key名称
	
	public static final String REQUEST_DEVICE_KEY = "dev"; //请求里的dev的key名称
	
	//public static final String PORTRAIT_KEY = "portrait_"; //传个人头像时的关键字
	
	public static final String PORTRAIT_DIR = File.separator + "portraitdir";  //存个人头像的文件夹部分地�?
	
	//public static final String CRASH_KEY = "crash_"; //传客户端崩溃文件时的关键�?
	
	public static final String CRASH_DIR = File.separator + "crashdir";  //存客户端崩溃文件的文件夹
	
	public static final String ESSAY_DIR = File.separator + "essaydir";  //存各种文章的总文件夹

	public static final String NEWS_DIR = ESSAY_DIR + File.separator + "newsdir";  //存各种新闻信息的总文件夹
	
	public static final String DOCTOR_DIR = ESSAY_DIR + File.separator + "docdir";  //存各种医生信息的总文件夹

	public static final String OP_DIR = ESSAY_DIR + File.separator + "opdir";  //存各种项目信息的总文件夹

	
	public static final String HOS_ACT_DIR = ESSAY_DIR + File.separator + "hosactdir";  //存各种医院活动信息的总文件夹

	
	public static final String HOS_DIR = ESSAY_DIR + File.separator + "hosdir";  //存各种医院信息的总文件夹
	
	public static final String FAQ_DIR = ESSAY_DIR + File.separator + "faqdir";  //存各种faq信息的�?文件�?
	
	public static final String DEFAULT_HEADER_DIR = File.separator + "default_image"; //默认个人头像的文件夹
	
	public static final String DEFAULT_HEADER_IMAGE = File.separator + "lich.png"; //默认个人头像文件�?
	
	public static final String COMMON_SPLI_MARK = ",";			//约定的数据分隔符
	
	public static final int NEWS_BANNER_LIMIT_NUM = 5;			//移动端首页展现资讯时banner的限定个�?
	
	public static final int NEWS_BODY_LIMIT_NUM = 20;		//移动端首页展现资讯时banner下方新闻条数的限定个�?
	
	public static final String ERROR_PAGE = "/error";		//错误页面
	public static final String ERROR_ATTR = "/error.jsp";		//错误页面

}
