package com.example.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.obj.Province;
import com.example.demo.repository.ProvinceRepository;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ProvinceService {
	

	ProvinceRepository province_repository;

	private List<Province> allCommunities = new ArrayList<Province>();	

	private URL url_communities;
	
	
	public ProvinceService(ProvinceRepository province_repository) {
	      this.province_repository = province_repository;
	}
	 
	public List<Province> getAllCommunities(){
		return this.allCommunities;
	}
	
	public void defineURL() throws MalformedURLException {
		this.url_communities = new URL("https://www.el-tiempo.net/api/json/v2/provincias");
	}
	
	
	public void createProvincesDB(){
		getDataProvince();
		List<Province> list = getAllCommunities();
		for(int i = 0 ; i < list.size() ; i++) {			
			this.province_repository.save(list.get(i));
		}
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
		try{
			reader();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Province> searchProvince(String pattern){
		return this.province_repository.findByNombrePronviciaContains(pattern);
	}
	
	public List<Province> searchCommunity(String pattern){
		return this.province_repository.findByCommunidadeProvinciaContains(pattern);
	}
	
	
}
