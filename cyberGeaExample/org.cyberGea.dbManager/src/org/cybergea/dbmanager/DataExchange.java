package org.cybergea.dbmanager;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import org.cybergea.dbmanager.provider.Environment;



public interface DataExchange {
	 String getJsonData() throws SQLException;	
	 String getProductbySeason(ArrayList<String> seas) throws SQLException;
	 int insertNewPlan(ArrayList<String> seasons) throws SQLException;
	 void updateAnnualPlan(ArrayList<String> list, int plan);
	 void insertCSVData() throws IOException;
	 String  retrieveBasicConditions(String season, int plan) throws SQLException;
	 Environment insertIdealConditions(ArrayList<Float> news,String season,int id) throws SQLException;
	 
	

}
  