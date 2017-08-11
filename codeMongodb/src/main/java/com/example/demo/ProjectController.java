package com.example.demo;


import java.util.List;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datastore.alerts;
import com.datastore.metrics;
import com.datastore.ruleAlert;
import com.mongodb.MongoClient;


@RestController
public class ProjectController {
	
	private static final Logger log = LoggerFactory
			.getLogger(ProjectController.class);
	
	private static String dbName = "EgenSolutions";
	
	public static int BASE_WEIGHT=150;
	private static MongoClient mongoClient=new MongoClient( "localhost", 27017 );
	
	private static Morphia morphia= new Morphia();
	
	private static Morphia mDR=morphia.map(metrics.class).map(alerts.class);
	
	private static Datastore datastore= mDR.createDatastore( mongoClient, dbName );
	
	@RequestMapping(value="/storedata", method= RequestMethod.POST)
	public void create(@RequestBody  String data) throws JSONException {
		
		JSONObject js=new JSONObject(data);
		System.out.println(js.get("timeStamp")+"value"+js.get("value"));
		metrics m=new metrics();
		m.setTimerange(js.getLong("timeStamp"));
		m.setWeight(js.getInt("value"));
		
		datastore.save(m);
		
		ruleAlert rulealert=new ruleAlert();
		rulealert.setValue(js.getInt("value"));
		rulealert.setBase_value(BASE_WEIGHT);
		
		//// creating the rule engine
		RulesEngineBuilder reb=RulesEngineBuilder.aNewRulesEngine();
		RulesEngine rulesEngine = reb.build();
		rulesEngine.registerRule(rulealert);
		
		rulesEngine.fireRules();
		
		String stat=rulealert.getStatus();
		
		if(!stat.equals("")) {
			alerts al=new alerts();
			al.setStatus(stat);
			al.setTimerange(js.getLong("timeStamp"));
			datastore.save(al);
			//System.out.println("i am stored ");
		}
	}
	
	@RequestMapping(value="/readdata", method= RequestMethod.GET)
	public List<metrics> read(){
	
		List<metrics> l = datastore.find(metrics.class).asList();
		return l;
	}
	
	@RequestMapping(value="/readTimerangeData", method= RequestMethod.GET)
	public List<metrics> readBYTimeRage(@RequestParam long startTimeStamp,@RequestParam long endTimeStamp)  {
	Query<metrics> query=datastore.createQuery(metrics.class).field("timerange").lessThan(endTimeStamp).field("timerange").greaterThan(startTimeStamp);
	return query.asList();
	}
	@RequestMapping(value="/readalert", method= RequestMethod.GET)
	public List<alerts> readAlert() {
		
		List<alerts> l = datastore.find(alerts.class).asList();
		return l;
	}
	
	@RequestMapping(value="/readTimerangeAlert", method= RequestMethod.GET)
	public List<alerts>  readBYTimeRageAlert(@RequestParam long startTimeStamp,@RequestParam long endTimeStamp) {
		Query<alerts> query=datastore.createQuery(alerts.class).field("timerange").lessThan(endTimeStamp).field("timerange").greaterThan(startTimeStamp);
		return query.asList();	
	}
	}


