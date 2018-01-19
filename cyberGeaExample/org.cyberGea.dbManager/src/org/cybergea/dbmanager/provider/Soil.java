package org.cybergea.dbmanager.provider;

public class Soil {
	private int id_soil;
	private int nitrogen;
	private int potassium;
	private int phosphorous;
	private int ph;
	
	public Soil(int id,int nit,int pot,int phos,int p) {
		id_soil=id;
		nitrogen=nit;
		potassium=pot;
		phosphorous=phos;
		ph=p;
	}
	
	
	public int getIDSoil() {
		return id_soil;
	}
	public int getNitrogen() {
		return nitrogen;
	}
	public int getPotassium() {
		return potassium;
	}
	public int getPhosphorous() {
		return phosphorous;
	}
	public int getPh() {
		return ph;
	}
	
	public void setIDSoil(int id_soil) {
		this.id_soil=id_soil;
	}
	public void setNitrogen(int nitrogen) {
		this.nitrogen=nitrogen;
	}
	public void setPotassium(int potassium) {
		this.potassium=potassium;
	}
	public void setPhosphorous(int phosphorous) {
		this.phosphorous=phosphorous;
	}
	public void setPh(int ph) {
		this.ph=ph;
	}
}
