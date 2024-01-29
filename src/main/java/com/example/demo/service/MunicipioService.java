package com.example.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.obj.Municipio;
import com.example.demo.obj.Province;
import com.example.demo.repository.MunicipioRepository;
import com.example.demo.repository.ProvinceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MunicipioService {

	private List<Municipio> allMunicipios = new ArrayList<Municipio>();
	
	private URL url_municipios;

	
	MunicipioRepository municipio_repository;
	
	

	public MunicipioService(MunicipioRepository municipio_repository) {
	      this.municipio_repository = municipio_repository;
	}
	
	public void defineURLForMunicipios(String id) throws MalformedURLException {
		this.url_municipios = new URL("https://www.el-tiempo.net/api/json/v2/provincias/" + id + "/municipios");
	}
	
	
	public List<Municipio> getAllMunicipios(){
		return this.allMunicipios;
	}
	
	
	public List<Municipio> findByCod(int id){
		return this.municipio_repository.findByCodProv(id);
	}
	
	public Municipio findByCodANDCodINE(int id, int cod){
		return this.municipio_repository.findByCodProvAndCodINE(id,cod);
	}
	
	public Municipio findByID(int id){
		return this.municipio_repository.findById(id);
	}
	
	public void createMunicipiosDB(List<Province> provinces) {
		getDataMunicipios(provinces);
		List<Municipio> list = getAllMunicipios();
		for(int i = 0 ; i < list.size() ; i++) {
			this.municipio_repository.save(list.get(i));
		}
		
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
