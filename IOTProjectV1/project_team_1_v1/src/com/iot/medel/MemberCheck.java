package com.iot.medel;

import java.io.Serializable;

public class MemberCheck implements Serializable{
	
	private boolean id_ok=false;
	private boolean pw_ok=false;
	private String name=null;
	
	
	public boolean isId_ok() {
		return id_ok;
	}
	public void setId_ok(boolean id_ok) {
		this.id_ok = id_ok;
	}
	public boolean isPw_ok() {
		return pw_ok;
	}
	public void setPw_ok(boolean pw_ok) {
		this.pw_ok = pw_ok;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
