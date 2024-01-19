package com.example.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.obj.Municipio;
import com.example.demo.obj.Province;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MunicipioService {

	private List<Municipio> allMunicipios = new ArrayList<Municipio>();
	
	private URL url_municipios;

	
	public void defineURLForMunicipios(String id) throws MalformedURLException {
		this.url_municipios = new URL("https://www.el-tiempo.net/api/json/v2/provincias/" + id + "/municipios");
	}
	
	
	public List<Municipio> getAllMunicipios(){
		return this.allMunicipios;
	}
	
	public void readerForMunicipios(List<Province> list) throws IOException {
		ObjectMapper mapper = new ObjectMapper();		
		for(int j = 0; j < list.size(); j++) {
			int id = list.get(j).getCodProv();
			String string_id = "";
			if(id < 10)
				string_id += "0";
			string_id += id;
			this.defineURLForMunicipios(string_id);
			JsonNode data = mapper.readTree(this.url_municipios);
			data = data.get("municipios");
			for(int i = 0; i < data.size(); i++) {
				JsonNode iterator = data.get(i);
				int INE = Integer.parseInt(iterator.get("CODIGOINE").asText().substring(0,5));
				Municipio mun = new Municipio(
						INE,
						iterator.get("CODPROV").asInt(),
						iterator.get("NOMBRE_PROVINCIA").asText(),
						iterator.get("NOMBRE").asText(),
						iterator.get("POBLACION_CAPITAL").asText());
				this.allMunicipios.add(mun);
			}
		}
	}
	
	public void getDataMunicipios(List<Province> list) {
		try {
			readerForMunicipios(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

}
