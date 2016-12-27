package com.geariot.bigdata.crawler.house.dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.bigdata.crawler.house.entities.School;
import com.geariot.bigdata.crawler.house.entities.SchoolMap;

@Transactional
@Repository("schoolDao")
public class SchoolDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public String saveSchool(School s,ArrayList<String> cnames) {
		String prevId = getSession().save(s).toString();
		if(cnames!=null){
		for(int i=0;i<cnames.size();i++){
			String cname=cnames.get(i);
			SchoolMap smap=new SchoolMap();
			smap.setSid(prevId);
			smap.setSname(s.getName());
			smap.setCname(cname);
			getSession().save(smap).toString();
		}
		}
		return prevId;
	}
	
}
