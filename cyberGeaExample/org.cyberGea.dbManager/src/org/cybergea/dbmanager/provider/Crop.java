package org.cybergea.dbmanager.provider;

public class Crop {
	
	private int id_crop;
	private String name;
	private String altName;	
	private float humidity;
	private int water;	
	private float temp;
	private int maturity;
	private int space;	
	private String disease;
	private String parasite;
	private String season;
	private String avoidInstr;
	private String compatiblePl;
	private Integer annualPlan;
	private float air;
	private float light;
	private int soil;
	
	
	
	public Crop(int id, String n,String alt,float hum,int w,Float  t,int mat,int sp 
			,String s,String dis,String par,String avd,String comp,float a,float l,int pl,int sl) {
		id_crop=id;
		name=n;
		altName=alt;
		humidity=hum;
		water=w;
		temp=t;
		maturity=mat;
		space=sp;		
		season=s;
		disease=dis;
		parasite=par;
		avoidInstr=avd;
		compatiblePl=comp;
		air=a;
		light=l;
		annualPlan=pl;
		soil=sl;
		
		
	}
	
	public Crop(String name,int maturity) {
		this.name=name;
		this.maturity=maturity;
	}
	
	public int getCropID() {
		return id_crop;
	}
	
	
	public String getName() {
		return name;
	}
	public String getAltName() {
		return altName;
	}
	public Float getHumidity() {
		return humidity;
	}
	public int getWater() {
		return water;
	
	}
	public Float getTemperature() {
		return temp;
	}
	public int getMaturityTime() {
		return maturity;
	}
	
	public int getSpace() {
		return space;
	}
	
	public String getDisease() {
		return disease;
	}
	public String geSeason() {
		return season;
	}
	public String getParasite(){
		return parasite;
	}
	
	public String getAvoidInstr() {
		return avoidInstr;
	}
	public String getCompatiblePlants() {
		return compatiblePl;
	}
	public Float getAir(){
		return air;
	}
	
	public Float getLight(){
		return light;
	}
	
	public Integer getAnnualPlan() {
		return annualPlan;
	}
	public int getSoil() {
		return soil;
	}
	
	
	public void setID(int id_crop) {
		this.id_crop=id_crop;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	public void setAtlName(String altName) {
		this.altName=altName;
	}
	
	public void setHumidity(int humidity) {
		this.humidity=humidity;
	}
	
	public void setWater(int water) {
		this.water=water;
	}
	
	public void setTeperature(Float temp) {
		this.temp=temp;
	}
	
	public void setMaturity(int maturity) {
		this.maturity=maturity;
	}
	
	public void setSpace(int space) {
		this.space=space;
	}
	public void setSeason(String season) {
		this.season=season;
	}
	public void setDisease(String disease) {
		this.disease=disease;
	}
	public void setParasite(String parasite) {
		this.parasite=parasite;
	}
	public void setAvoidInstr(String avoidInstr) {
		this.avoidInstr=avoidInstr;
	}
	public void setCompatibleInstr(String compatiblePl ) {
		this.compatiblePl=compatiblePl;
	}
	
	public void setAnnualPlan(Integer annualPlan) {
		this.annualPlan=annualPlan;
	}
	public void setSoil(int soil) {
		this.soil=soil;
	}
	
	public void setAir(float air) {
		this.air=air;
	}

	public void setLight(float light) {
		this.light=light;
	}
	
	

	
	
	

}
