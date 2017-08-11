package com.datastore;

import org.bson.types.ObjectId;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class alerts {
	@Id
	 ObjectId id;
	
	private long timerange;
	
	private String status;
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTimerange() {
		return timerange;
	}

	public void setTimerange(long timerange) {
		this.timerange = timerange;
	}
		
}
