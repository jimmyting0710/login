package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userinfo")
public class ThisEntity {
	@Id
	 String userid;
	 String userpw;
	 String lastlogindatetime;
	
	public ThisEntity() {
		super();
	}
	
	public ThisEntity(String userid, String userpw, String lastlogindatetime) {
		super();
		this.userid = userid;
		this.userpw = userpw;
		this.lastlogindatetime = lastlogindatetime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public String getLastlogindatetime() {
		return lastlogindatetime;
	}
	public void setLastlogindatetime(String lastlogindatetime) {
		this.lastlogindatetime = lastlogindatetime;
	}



}
