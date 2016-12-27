package com.geariot.bigdata.crawler.house.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetLoc {
	 private static String KEY = "aa4a48297242d22d2b3fd6eddfe62217";  
     
	    private static Pattern pattern = Pattern.compile("\"location\":\"(\\d+\\.\\d+),(\\d+\\.\\d+)\"");  
	   
	    
	    
	    public static double[] addressToGPS(String address) {  
	        try {  
	        	address=address.replace(" ", "");
	            String url = String .format("http://restapi.amap.com/v3/geocode/geo?&s=rsv3&city=025&address=%s&key=%s", address, KEY); 
	    	URL myURL = null; 
	    	URLConnection httpsConn = null; 
	    	try { 
	    	myURL = new URL(url); 
	    	} catch (MalformedURLException e) { 
	    	e.printStackTrace(); 
	    	} 
	    	InputStreamReader insr = null;
	    	BufferedReader br = null;
	    	httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
	    	if (httpsConn != null) { 
	    	insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
	    	br = new BufferedReader(insr); 
	    	String data = "";
	    	String line = null; 
	    	while((line= br.readLine())!=null){
	    	data+=line;
	    	} 
	    	            Matcher matcher = pattern.matcher(data);  
	    	            if (matcher.find() && matcher.groupCount() == 2) {  
	    	                double[] gps = new double[2];  
	    	                gps[0] = Double.valueOf(matcher.group(1));  
	    	                gps[1] = Double.valueOf(matcher.group(2));  
	    	                return gps;  
	    	            }
	    	}
	        }catch (Exception e) {
	        	e.printStackTrace(); 
	return null;
	}
	        return null;
	    }
	    
	    public static void main(String[] args) {  
	    	double [] data = GetLoc.addressToGPS("二条巷");
	        System.out.println("经度:"+data[0]);
	        System.out.println("纬度:"+data[1]);
	    } 
}
