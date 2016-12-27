package com.geariot.bigdata.crawler.house.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class LianjiaPageProcessor implements PageProcessor {

    private static Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(http://nj.fang.lianjia.com/loupan/\\w+)").all());
        
        page.putField("name", page.getHtml().xpath("//ul[@class='house-lst']/li/div[@class='info-panel']/div[@class='col-1']/h2/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        
       
        
        
        page.putField("loc", page.getHtml().xpath("//ul[@class='house-lst']/li/div[@class='info-panel']/div[@class='col-1']/div[@class='where']/span[@class='region']/text()").toString());
        page.putField("area", page.getHtml().xpath("//ul[@class='house-lst']/li/div[@class='info-panel']/div[@class='col-1']/div[@class='area']/span/text()").toString());
        page.putField("sold", page.getHtml().xpath("//ul[@class='house-lst']/li/div[@class='info-panel']/div[@class='col-1']/div[@class='type']/span[@class='onsold']/text()").toString());
        page.putField("live", page.getHtml().xpath("//ul[@class='house-lst']/li/div[@class='info-panel']/div[@class='col-1']/div[@class='type']/span[@class='live']/text()").toString());
        page.putField("price", page.getHtml().xpath("//ul[@class='house-lst']/li/div[@class='info-panel']/div[@class='col-2']/div[@class='price']/div[@class='average']/span[@class='num']/text()").toString());
        // page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
      //  Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
    	
    	//Spider.create(new LianjiaPageProcessor()).addUrl("http://nj.fang.lianjia.com/loupan/").addPipeline(new HousePipeline()).thread(5).run();
    	
    }
}
