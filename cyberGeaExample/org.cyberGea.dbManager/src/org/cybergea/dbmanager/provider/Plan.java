package org.cybergea.dbmanager.provider;

public class Plan {

private int id_plan;
private String season1;
private String season2;
private String season3;
private String season4;
private String date;
private int environment;


public Plan(int id,String s1,String s2,String s3,String s4,String dt,int env) {
	id_plan=id;
	season1=s1;
	season2=s2;
	season3=s3;
	season4=s4;
	date=dt;
}

public int getIDPlan() {
	return id_plan;
	
}

public String getSeason1() {
	return season1;
	
}
public String getSeason2()  {
	return season2;
	
}
public String getSeason3()  {
	return season3;
	
}
public String getSeason4()  {
	return season4;
	
}
public String getDate()  {
	return date;
	
}
public int getEnv() {
	return environment;
	
}

public void setID(int id_plan) {
	this.id_plan=id_plan;
}

public void setSeason1(String season1) {
	this.season1=season1;
}
public void setSeason2(String season2) {
	this.season2=season2;
}
public void setSeason3(String season3) {
	this.season3=season3;
}
public void setSeason4(String season4) {
	this.season4=season4;
}
public void setDate(String date) {
	this.date=date;
}
public void setEnv(int environment) {
	this.environment=environment;
}
}
