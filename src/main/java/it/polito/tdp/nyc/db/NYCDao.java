package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.nyc.model.Adiacenza;
import it.polito.tdp.nyc.model.Borough;
import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.NTACode;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<Borough> getAllBoroughs(){
		
		String sql = "SELECT DISTINCT borough "
					+ "FROM nyc_wifi_hotspot_locations";
		List<Borough> result = new ArrayList<Borough>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add(new Borough(res.getString("borough")));
			}
			conn.close();
			return result;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void getNTACodes (Borough borough, Map<String, NTACode> idMap){
		
		String sql = "SELECT DISTINCT n1.NTACode "
				+ "FROM nyc_wifi_hotspot_locations n1, nyc_wifi_hotspot_locations n2 "
				+ "WHERE n1.Borough = n2.Borough AND n1.Borough = ?";
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, borough.getNome());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				idMap.put(res.getString("NTACode"), new NTACode(res.getString("NTACode")));
			}
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
	}
	public List<NTACode> getNTACodes (Borough borough){
		
		String sql = "SELECT DISTINCT NTACode "
				+ "FROM nyc_wifi_hotspot_locations "
				+ "WHERE Borough = ?";
		
		List<NTACode> result = new LinkedList<NTACode>();
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, borough.getNome());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add(new NTACode(res.getString("NTACode")));
			}
			conn.close();
			return result;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Integer getPeso(NTACode n1, NTACode n2){
		
		String sql="SELECT COUNT(*) as cnt "
				+ "FROM ( "
				+ "SELECT DISTINCT SSID "
				+ "FROM nyc_wifi_hotspot_locations "
				+ "WHERE NTACode = ? "
				+ "UNION "
				+ "SELECT DISTINCT SSID "
				+ "FROM nyc_wifi_hotspot_locations "
				+ "WHERE NTACode = ? ) AS T";

		
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, n1.getNome());
			st.setString(2, n2.getNome());
			ResultSet res = st.executeQuery();
			int peso = 0;
			res.first();
			peso = res.getInt("cnt");
			
			conn.close();
			return peso;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
}
