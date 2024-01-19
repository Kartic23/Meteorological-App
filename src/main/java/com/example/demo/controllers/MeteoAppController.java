package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.obj.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;


@Controller
public class MeteoAppController {
	
	ProvinceService province_service = new ProvinceService();
	
	MunicipioService municipio_service = new MunicipioService();
	
	WeatherService weather_service = new WeatherService();
	
	
	@Autowired
	ProvinceRepository province_repository;
	
	@Autowired
	MunicipioRepository municipio_repository;
		

	int n_gets = 1;
	
	
	@GetMapping("/")
	 public String viewHome(Model model){
		if(n_gets == 0) {
			createProvincesDB();
			createMunicipiosDB();
			//createWeatherDB();
		}
		this.province_service.getDataProvince();
		this.municipio_service.getDataMunicipios(this.province_service.getAllCommunities());
		this.n_gets++;
	    return "welcome";
	 }
	
	@GetMapping("/provinces")
	public String getAllProvinces(Model model){
		model.addAttribute("title","List of Provinces");
		model.addAttribute("list", this.province_service.getAllCommunities());
		return "Province/province";
	}
	
	@GetMapping("/showSelectProvince/{id}")
    public String selectProvinceId( @PathVariable(value = "id") long id,Model model) {
		List<Municipio> list = this.municipio_repository.findByCodProv((int) id);
		model.addAttribute("title","List of Municipios");
		model.addAttribute("province",list.get(0).getNombreProvincia());
		model.addAttribute("municipios", list);
		return "Municipio/municipio";
    }
	
	
	@GetMapping("/show/{id}/{cod}")
    public String selectMunicipio( @PathVariable(value = "id") String id, @PathVariable(value = "cod") String cod,Model model) {
		int id_int = Integer.parseInt(id);
		int cod_int = Integer.parseInt(cod);
		Municipio m = this.municipio_repository.findByCodProvAndCodINE(id_int,cod_int);
		this.weather_service.getWeatherForMunicipio(id_int,cod_int);
		System.out.println("T: " + this.weather_service.getWeather().getNextDays().size());
		model.addAttribute("title","Weather");
		model.addAttribute("weather", this.weather_service.getWeather());
		model.addAttribute("codProv", id);
		model.addAttribute("name_municipio",m.getNombre());
		return "Weather/weather";
    }
	
	
	public void createProvincesDB(){
		this.province_service.getDataProvince();
		List<Province> list = this.province_service.getAllCommunities();
		for(int i = 0 ; i < list.size() ; i++) {			
			this.province_repository.save(list.get(i));
		}
	}
	
	public void createMunicipiosDB() {
		this.municipio_service.getDataMunicipios(this.province_service.getAllCommunities());
		List<Municipio> list = this.municipio_service.getAllMunicipios();
		for(int i = 0 ; i < list.size() ; i++) {
			this.municipio_repository.save(list.get(i));
		}
		
	}
	
	
	
	
	

	
	
}

