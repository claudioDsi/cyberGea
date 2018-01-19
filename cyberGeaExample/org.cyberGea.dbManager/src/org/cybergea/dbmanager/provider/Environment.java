package org.cybergea.dbmanager.provider;

import java.util.ArrayList;

public class Environment {
	
	private int id;
	private Float humidity;
	private Float temperature;
	private Float light;
	private String season;
	private Float air;
	
	
	public Environment()
	{
		
	}
	
	public Environment(ArrayList<Float> list) {
		this.humidity = list.get(0);
		this.temperature = list.get(1);
		this.light = list.get(2);
		this.air = list.get(3);
	}
	
	public Environment(int id,Float humidity,Float temperature,Float light,String season,Float air) {
		this.id=id;
		this.humidity=humidity;
		this.temperature=temperature;
		this.light=light;
		this.season=season;
		this.air=air;
	}
	
	public void compareEnvs(Environment i) {
		if((this.temperature-i.temperature)<=3) {
			this.temperature=null;
		}
		if((this.humidity-i.humidity)<=5) {
			this.humidity=null;
		}
		if((this.light-i.light)<=5) {
			this.light=null;
		}
		if((this.air-i.air)<=0.1) {
			this.air=null;
		}
		
		
		
		
	}
	
	
	
	@Override
	public String toString() {
		return "Environment [id=" + id + ", humidity=" + humidity + ", temperature=" + temperature + ", light=" + light
				+ ", season=" + season + ", air=" + air + "]";
	}
	
	public String toStringSensor() {
		return "Environment [humidity=" + humidity + ", temperature=" + temperature + ", light=" + light
				+ " air=" + air + "]";
	}


	public int getId() {
		return id;
	}

	public Float getHumidity() {
		return humidity;
	}

	public Float getTemperature() {
		return temperature;
	}

	public Float getLight() {
		return light;
	}

	public String getSeason() {
		return season;
	}

	public Float getAir() {
		return air;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public void setLight(Float light) {
		this.light = light;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public void setAir(Float air) {
		this.air = air;
	}
		
}
