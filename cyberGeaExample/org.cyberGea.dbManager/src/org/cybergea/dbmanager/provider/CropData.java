package org.cybergea.dbmanager.provider;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.cybergea.dbmanager.DataExchange;


public class CropData implements DataExchange{
	private String dbname;
	private String user;
	private String pwd;
	private String host;
	private String port;
	private Connection conn;
	
	public CropData(String db,String us,String pass,String h,String p ) {
		dbname=db;
		user=us;
		pwd=pass;
		host=h;
		port=p;
		String connectionStr= "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?user=" + user + "&password=" + pwd;
		conn=null;
		try {
			conn = DriverManager.getConnection(connectionStr);
		} 
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}			
	}

	public Map<String,Integer> queryManager(String key){
		
		ResultSet rs=null;
		Map<String,Integer> product=new HashMap<>();
		String query="SELECT IDCrop,Name FROM crops WHERE Season = ?";
		PreparedStatement pd=null;
		String clean=key.replace("\"","");
		
			try {
				pd=conn.prepareStatement(query);
				pd.setString(1,clean);
			
				rs = pd.executeQuery();
				
				
					while(rs.next()) {	
						
						product.put(rs.getString("Name"),rs.getInt("IDCrop"));				
				
					}
			}
					
			catch(SQLException ex) {
				ex.printStackTrace();
				return null;
			}
			
			return product;
					
	}
	@Override
	public String  retrieveBasicConditions(String season, int plan)  {
		ResultSet rs=null;		
		PreparedStatement pd=null;
		
		Map<String,Map<String,ArrayList<Integer>>> map=new HashMap<String,Map<String,ArrayList<Integer>>>();
		Map<String,ArrayList<Integer>> crop=new HashMap<String,ArrayList<Integer>>();
		Map<String,ArrayList<Integer>> soil=new HashMap<String,ArrayList<Integer>>();
		
		
		
		
		
		String query="SELECT * FROM soils,crops WHERE AnnualPlan = ? AND Season = ?";
		
		try {
			pd=conn.prepareStatement(query);
			
			pd.setInt(1, plan);
			pd.setString(2, season);
			
			
			
			rs=pd.executeQuery();
			
			
			int i=0;
				
				ArrayList<Integer> soilChar=new ArrayList<>();
				while (rs.next()) {
					
					
					ArrayList<Integer> prodChar=new ArrayList<>();
					
					prodChar.add(rs.getInt("Water"));
					prodChar.add(rs.getInt("NeededSpace"));				
					crop.put(rs.getString("Name"),prodChar);
					
					if(i == 0)
					{
						soilChar.add(rs.getInt("Phosphorous"));
						soilChar.add(rs.getInt("Nitrogen"));
						soilChar.add(rs.getInt("Potassium"));
						soilChar.add(rs.getInt("PH"));
						crop.put("Terreno"+rs.getInt("IDSoil"), soilChar);
					}
					
					i+=1;
				
				}
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
			
			map.put(season, crop);
			
			
			String finalMap=map.toString().replaceAll("=", ":");	
			
			return finalMap;
			
		
		
			
		
		
		
		
	}
	
	
	
	
	@Override	
	public String getProductbySeason(ArrayList<String> seas) throws SQLException {
		
		Map<String,Map<String,Integer>>f=new HashMap<>();		
	
		String clean="";	
		
		seas.remove(seas.get(0));
		for(int i=0; i<seas.size();i++)
	    {
			clean=seas.get(i).replace("\"","");					
			f.put(seas.get(i), (queryManager(clean)));
			}
				
		String f3=f.toString().replaceAll("=", ":");				
			
		return f3;
		
		
	}
	
	
	
	public void updateEnvironmentConditions(int idEnv,int currentTemp,int currentHumdity,
			int currentLight, int currentAir,int idPlan) throws SQLException {
		
		String query="UPDATE environments, plans SET CurrentHumidity = ?, CurrentTemperature = ?,"
				+ " CurrentLight= ?,   CurrentSeason=?, CurrentAir=?  WHERE  IDEnv= ? AND IDPlan = ? ";
		
		
		CropData db=new CropData("cybergea", "root", "", "localhost", "3306");
		Plan p=db.getPlanData(2);
		
		
		PreparedStatement prepSt = null;
		try {
			prepSt = conn.prepareStatement(query);
			prepSt.setInt(1, currentHumdity);
			prepSt.setInt(2, currentTemp );
			prepSt.setInt(3, currentLight);
			prepSt.setString(4,p.getSeason3());
			prepSt.setInt(5, currentAir);
			prepSt.setInt(6, idEnv);
			prepSt.setInt(7, idPlan);			
			prepSt.executeUpdate();
			
		} 
		catch (SQLException e1) {
			
			
			e1.printStackTrace();
			
		}
		
		
		
	}
	
	
	
	@Override
	public void updateAnnualPlan(ArrayList<String> list, int plan) {
		
		String query2="UPDATE crops SET AnnualPlan = CASE ";	
		
		for (int i=0; i<list.size();i++) {
			
			String name=list.get(i)	;			
			query2+=" WHEN Name = '"+name+"' THEN '"+plan+"'";
			
					
			
		}
		query2+=" ELSE AnnualPlan END";
		
		PreparedStatement prepSt = null;
		
		try {
			prepSt = conn.prepareStatement(query2);
			
			
			prepSt.executeUpdate();
		} catch (SQLException e1) {			
			e1.printStackTrace();
			return;
		}
	}
	
	@Override
	public Environment insertIdealConditions(ArrayList<Float> news,String season,int id) throws SQLException {
		PreparedStatement pd=null;
		ResultSet re=null;
		
		System.out.println(news.toString());
		String query="INSERT INTO environments (CurrentHumidity,CurrentTemperature,CurrentLight,CurrentSeason,"
				+ "CurrentAir) VALUES(?,?,?,?,?)";
		pd = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);	
		pd.setFloat(1, news.get(0));
		pd.setFloat(2, news.get(1));
		pd.setFloat(3, news.get(2));
		pd.setString(4, season);
		pd.setFloat(5,news.get(2));		
		pd.executeUpdate();
		System.out.println(pd.toString());
		int num=0;
		re=pd.getGeneratedKeys();
		if(re.next()) {
			
			 num=re.getInt(1);
			
		}
		
		query="UPDATE plans SET Environment = ? WHERE IDPlan = ?";
		
		pd=conn.prepareStatement(query);
		pd.setInt(1, num);
		pd.setInt(2, id);
		pd.executeUpdate();
		
		return new Environment(news);
		
	}
	
	
	
	
	
	
	
	@Override
	public int insertNewPlan(ArrayList<String> seasons) throws SQLException {
		
		PreparedStatement pd=null;
		ResultSet re=null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		String date=dtf.format(localDate).toString();		
		//String query="INSERT INTO plans ( ";	
		
		StringJoiner joiner = new StringJoiner(",");
	    StringJoiner values = new StringJoiner(",");
	    String query = "INSERT INTO plans (";

	   
	    //seasons.remove(seasons.get(0));
	    for(int i=0; i<seasons.size();i++)
	    {
	     
	      
	      joiner.add("Season"+(i+1));
	      values.add("?");
	    }

	    query += joiner.toString() + ",Date) VALUES (" + values.toString() + ",?);";

	    
	    pd = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);	
	    int i=0;
	    for(; i < seasons.size(); i++)
	    {
	      pd.setString(i+1,seasons.get(i).replaceAll("\"", ""));
	    }
		
		pd.setString(i+1, date);
		
		//query+=",?)";
		
		
		pd.executeUpdate();
		int num=0;
		re=pd.getGeneratedKeys();
		if(re.next()) {
			
			 num=re.getInt(1);
			
		}
		
		
		
		
		
		return num;
		
		
		
	}
	
	
	
	public ArrayList<Crop> getCropData(int plan) throws SQLException {
		
		Crop crop=null;
		ResultSet rs = null;
		PreparedStatement pd=null;
		String query="SELECT * FROM crops,plans WHERE Season=Season1 AND AnnualPlan = ? AND IDPlan=AnnualPlan ";
		ArrayList<Crop> list=new ArrayList<>();
		pd=conn.prepareStatement(query);
		
		pd.setInt(1, plan);
		try {
			
			rs = pd.executeQuery();
			
				while(rs.next()) {
					
					
					
					crop = new Crop(
						rs.getInt("IDCrop"),
						rs.getString("Name"),
						rs.getString("AlternateName"),
						rs.getInt("Humidity"),
						rs.getInt("Water"),
						rs.getFloat("Temperature"),
						rs.getInt("MaturityTime"),
						rs.getInt("NeededSpace"),						
						rs.getString("Season"),
						rs.getString("Disease"),
						rs.getString("Parasites"),
						rs.getString("AvoidIstruction"),
						rs.getString("CompatiblePlants"),
						rs.getFloat("Air"),
						rs.getFloat("Light"),
						rs.getInt("AnnualPlan"),
						rs.getInt("Soil")
					
					);
					list.add(crop);
		
				}
			
		}
			catch(SQLException ex) {
				ex.printStackTrace();
				return null;
			}
	
		return list;
	}
	
	
	
	public ArrayList<Crop> getCropByGrowth(int plan,String season) {
		Crop r=null;
		ResultSet rs=null;
		PreparedStatement pd=null;
		String query="SELECT * FROM crops,plans WHERE IDPlan = ? AND Season = ?";
		ArrayList<Crop> listCrop=new ArrayList<>();
		
		
		try {
			pd=conn.prepareStatement(query);
			pd.setInt(1,plan);
			pd.setString(2, season);
			
			rs = pd.executeQuery();
				while(rs.next()) {
					r= new Crop(
							rs.getInt("IDCrop"),
							rs.getString("Name"),
							rs.getString("AlternateName"),
							rs.getInt("Humidity"),
							rs.getInt("Water"),
							rs.getFloat("Temperature"),
							rs.getInt("MaturityTime"),
							rs.getInt("NeededSpace"),						
							rs.getString("Season"),
							rs.getString("Disease"),
							rs.getString("Parasites"),
							rs.getString("AvoidIstruction"),
							rs.getString("CompatiblePlants"),
							rs.getFloat("Air"),
							rs.getFloat("Light"),
							rs.getInt("AnnualPlan"),
							rs.getInt("Soil")	
					);
					listCrop.add(r);
									
				}
			
		}
			catch(SQLException ex) {
				ex.printStackTrace();
				return null;
			}
	
		return listCrop;
		
	}
	
	
	
	public Environment getEnviromentData(int id) throws SQLException {
		Environment env=null;
		ResultSet rs = null;
		PreparedStatement pd=null;
		String query="SELECT * FROM environments,plans WHERE IDPlan = ? AND IDEnv=Environment";
		
		pd=conn.prepareStatement(query);
		pd.setInt(1,id);
		try {
			
			rs = pd.executeQuery();
				while(rs.next()) {
					env= new Environment(
						rs.getInt("IDEnv"),
						rs.getFloat("CurrentHumidity"),
						rs.getFloat("CurrentTemperature"),
						rs.getFloat("CurrentLight"),
						rs.getString("CurrentSeason"),
						rs.getFloat("CurrentAir")				
					);
									
				}
			
		}
			catch(SQLException ex) {
				ex.printStackTrace();
				return null;
			}
	
		return env;
	}
	
	
	public Plan getPlanData(int id) throws SQLException {
		Plan pl=null;
		ResultSet rs = null;
		PreparedStatement pd=null;
		String query="SELECT * FROM plans WHERE IDPlan = ?";
		
		pd=conn.prepareStatement(query);
		pd.setInt(1,id);
		try {
			
			rs = pd.executeQuery();
				while(rs.next()) {
					pl=new Plan(
						rs.getInt("IDPlan"),
						rs.getString("Season1"),
						rs.getString("Season2"),
						rs.getString("Season3"),
						rs.getString("Season4"),
						rs.getString("Date"),
						rs.getInt("Environment")			
					
					);
				}
			
		}
			catch(SQLException ex) {
				ex.printStackTrace();
				return null;
			}
	
		return pl;
	}
	
	
	public ArrayList<Sensor> getSensorData() throws SQLException {
		Sensor sens=null;
		ResultSet rs = null;
		PreparedStatement pd=null;
		ArrayList<Sensor> listSens= new ArrayList<Sensor>();
		String query="SELECT * FROM sensors ";
		
		pd=conn.prepareStatement(query);
		
		try {
			
			rs = pd.executeQuery();
				while(rs.next()) {
					sens=new Sensor(
						rs.getInt("IDSensor"),
						rs.getString("Type"),
						rs.getString("Data"),
						rs.getInt("Soil")						
					
					);	
					
					listSens.add(sens);
		
				}
			
		}
			catch(SQLException ex) {
				ex.printStackTrace();
				return null;
			}
	
		return listSens;
	  }
	
	
	public Soil getSoilData(int id) throws SQLException {
		Soil sl=null;
		ResultSet rs = null;
		PreparedStatement pd=null;
		String query="SELECT * FROM soils WHERE IDSoil = ?";
		
		pd=conn.prepareStatement(query);
		pd.setInt(1,id);
		try {
			
				rs = pd.executeQuery();
					while(rs.next()) {
						sl=new Soil(
							rs.getInt("IDSoil"),
							rs.getInt("Nitrogen"),
							rs.getInt("Potassium"),
							rs.getInt("Phosphorous"),
							rs.getInt("PH")
						
						);					
			
					}				
		}
			catch(SQLException ex) {
				ex.printStackTrace();
				return null;
			}
	
		return sl;
	  }
	
	
	
	
	
	@Override
	public String getJsonData() throws SQLException {
		// TODO Auto-generated method stub
		
	
		CropData db=new CropData("cybergea", "root", "", "localhost", "3306");
		//Crop c=db.getCropData(2,plan);		
		Environment env=db.getEnviromentData(1);		
		Plan pl=db.getPlanData(2);		
		
		Soil terr=db.getSoilData(1);		
		
				
		
		db.updateEnvironmentConditions(1,40,0,0,34,2);
		
		
		
		return "";
		
		
		
	}
	
	
	public String parseTemperature(String temp) {
		ArrayList<String> arr;
	    String app;	   
	    int value; 	    
	    String clean=temp.replaceAll("Â", "");
	    Pattern pattern = Pattern.compile("\\d+°F");
	    Matcher matcher = pattern.matcher(clean);

	    arr = new ArrayList<String>();
	    while(matcher.find())
	    {
	      app = matcher.group(0);
	      value = (Integer.parseInt(app.replaceAll("[\\D]", "")) - 32) * 5 / 9;
	      arr.add(Integer.toString(value));
	    }
	    
	    return arr.toString();
	    
	}
	
	
	
	
	
	public int parseSpaceMeasure(String space2convert) {
		
		
		int res=0;
		
		String s = new String(space2convert);
	    // This part is needed to find the first number
		try {
			  res = new Scanner(s).useDelimiter("\\D+").nextInt();
			
		}
		catch(NoSuchElementException e) {
			return 0;
		}
	   
	    // inches or cm? 
	    if(s.toLowerCase().contains("inches"))
	    {
	      res *= 2.54;
	    }
	    
	    return res;
	    
	}
	@Override
	public void insertCSVData() throws IOException {
		Reader rd=new FileReader("C:\\Users\\claudio\\Desktop\\CSVfiles\\plantDataSet.csv");
		String name;
		String altName;
		String  wrong;
		String comp;
		String temp;
		String strTemp;
		int space;
		int humidity;
		int temperature;
		int water;
		
		PreparedStatement pr=null;
		
		String query;
		
		
		
		Iterable<CSVRecord> it=CSVFormat.EXCEL.withHeader().parse(rd);
		
		
		
		for (CSVRecord r: it) {
			name=r.get("Name");
			altName=r.get("alternateName");
			wrong=r.get("avoidInstructions");
			comp=r.get("compatiblePlants");
			temp=r.get("spaceInstructions");
			strTemp=r.get("sowInstructions");
			parseTemperature(strTemp);
			
			//System.out.println(parseTemperature(strTemp));
			
			//System.out.println(strTemp);
			
			//System.out.println(temperature);
			 //space=parseSpaceMeasure(temp);
			
			
			//non cancellare 
			/*
			query="INSERT INTO crops (Name,AlternateName,AvoidIstruction,CompatiblePlants,NeededSpace) VALUES (?,?,?,?,?)";
			//System.out.println(name);
			
			try {
				pr=conn.prepareStatement(query);				
				pr.setString(1, name);
				pr.setString(2, altName);
				pr.setString(3, wrong);
				pr.setString(4, comp);
				pr.setInt(5, space);
				pr.executeUpdate();
				System.out.println("Query success");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			
		}
		
		
	}
	
	
		
		
		
	
}
