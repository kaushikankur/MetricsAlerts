package com.datastore;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

/*import org.mongodb.morphia.annotations.Embedded;

@Embedded*/
import org.mongodb.morphia.annotations.Entity;

@Entity
public class metrics {
	
	@Id
	 ObjectId id;
	
	private long timerange;

	private int weight;

	public long getTimerange() {
		return timerange;
	}

	public void setTimerange(long timerange) {
		this.timerange = timerange;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
