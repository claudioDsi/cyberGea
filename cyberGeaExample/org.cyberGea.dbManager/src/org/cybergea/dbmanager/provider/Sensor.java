package org.cybergea.dbmanager.provider;

public class Sensor {
	private int id_sensor;
	private String type;
	private String data;
	private int cod_soil;
	
	
	public Sensor(int id,String t,String dt,int cd) {
		id_sensor=id;
		type=t;
		data=dt;
		cod_soil=cd;
	}
	
	public int getSensorID() {
		return id_sensor;
		
	}
	public String getType() {
		return type;
	}
		
	
	public String getSensData() {
		return data;
		
	}
	public int getCodSoil() {
		return cod_soil;
		
	}
	
	public void setSensorID(int id_sensor) {
		this.id_sensor=id_sensor;
	}
	public void setSensorType(String type) {
		this.type=type;
	}
	public void setSensData(String data) {
		this.data=data;
	}
	public void setCodSoil(int cod_soil) {
		this.cod_soil=cod_soil;
	}
	

}
