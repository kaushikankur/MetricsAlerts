package com.datastore;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.example.demo.ProjectController;

@Rule
public class ruleAlert {
	
	private int base_value;
	
	private int value;
	
	private String status;
	
	
	@Condition
    public boolean when() {
		if(base_value*0.1>value) {
			status="Under Weight";
			return true;
		}
		else if (base_value*1.1<value) {
			status="Over Weight";
			return true;
		}
		else {
		status="";
			return false;
		}
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBase_value(int base_value) {
		this.base_value = base_value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Action
    public void then(){
        //System.out.println("Hey, I'm managed by Spring");
    }

}
