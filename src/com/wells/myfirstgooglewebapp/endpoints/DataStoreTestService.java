package com.wells.myfirstgooglewebapp.endpoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;

@Api(
	    name = "dataStoreService",
	    version = "v1",
	    scopes = {Constants.EMAIL_SCOPE},
	    clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
	        Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
	    audiences = {Constants.ANDROID_AUDIENCE}
	)
public class DataStoreTestService {
	
	public static Map<String,Commodity> commodities = new HashMap<String,Commodity>();

	  static {
		  Commodity commodity=new Commodity();
		  commodity.setId("001");
		  commodity.setName("iPhone V");
		  commodity.setPrice(200);		  
		  commodities.put(commodity.getId(), commodity);
	    
	  }
	  
	 public Commodity getCommodity(@Named("id") String id) {
		    return commodities.get(id);
		  }
}
