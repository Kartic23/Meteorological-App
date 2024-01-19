package com.example.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



import com.example.demo.obj.Province;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProvinceService {

	private List<Province> allCommunities = new ArrayList<Province>();	
	

	private URL url_communities;
	
	
	public List<Province> getAllCommunities(){
		return this.allCommunities;
	}
	
	public void defineURL() throws MalformedURLException {
		this.url_communities = new URL("https://www.el-tiempo.net/api/json/v2/provincias");
	}
	
	
	public void reader() throws IOException {
		this.allCommunities.clear();
		defineURL();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode data = mapper.readTree(this.url_communities);
		data = data.get("provincias");
		for(int i = 0; i < data.size(); i++) {
			JsonNode iterator = data.get(i);
			Province province = new Province(iterator.get("CODPROV").asInt(),iterator.get("NOMBRE_PROVINCIA").asText(),
							iterator.get("CODAUTON").asInt(),iterator.get("COMUNIDAD_CIUDAD_AUTONOMA").asText(),
							iterator.get("CAPITAL_PROVINCIA").asText());
			this.allCommunities.add(province);
		}
	}
	
	public void getDataProvince(){
		try {
			reader();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
