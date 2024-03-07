package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.obj.*;
import com.example.demo.obj.user.User;
import com.example.demo.repository.*;
import com.example.demo.service.*;


@Controller
public class MeteoAppController {

	ProvinceService province_service;
	
	MunicipioService municipio_service;
	
	WeatherService weather_service;
	
	@Autowired
	UserService user_service;
		
	
	public MeteoAppController(ProvinceService province_service, MunicipioService municipio_service, WeatherService weather_service) {
		this.province_service = province_service;
		this.municipio_service = municipio_service;
		this.weather_service = weather_service;
	}

	int n_gets = 1;
	
	
	@GetMapping("/")
	 public String viewHome(Model model){
		if(n_gets == 0) {
			this.province_service.createProvincesDB();
			this.municipio_service.createMunicipiosDB(this.province_service.getAllCommunities());
		}
		if(n_gets == 1) {
			this.province_service.getDataProvince();
			this.municipio_service.getDataMunicipios(this.province_service.getAllCommunities());
		}
		
		this.n_gets++;
	    return "welcome";
	 }
	
	public void Checklogin(String currentPrincipalName, Model model) {
		if(currentPrincipalName == "anonymousUser") {
			model.addAttribute("authenticated","");
		}
		else {
			model.addAttribute("authenticated","logado");
		}
	}
	
	@GetMapping("/provinces")
	public String getAllProvinces(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName == "anonymousUser") {
			model.addAttribute("authenticated","");
		}
		else {
			model.addAttribute("authenticated","logado");
			User user = getUser();
			model.addAttribute("user",user);
		}
		model.addAttribute("title","List of Provinces");
		model.addAttribute("list", this.province_service.getAllCommunities());
		return "Province/province";
	}
	
	@GetMapping("/showSelectProvince/{id}")
    public String selectProvinceId( @PathVariable(value = "id") long id,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		List<Municipio> list = this.municipio_service.findByCod((int)id);
		if(currentPrincipalName == "anonymousUser") {
			model.addAttribute("authenticated","");
		}
		else {
			model.addAttribute("authenticated","logado");
			User user = getUser();
			model.addAttribute("user",user);
		}
		model.addAttribute("title","List of Municipios");
		model.addAttribute("province",list.get(0).getNombreProvincia());
		model.addAttribute("municipios", list);
		return "Municipio/municipio";
    }
	
	
	@GetMapping("/show/{id}/{cod}")
    public String selectMunicipio( @PathVariable(value = "id") String id, @PathVariable(value = "cod") String cod,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName == "anonymousUser") {
			model.addAttribute("authenticated","");
		}
		else {
			model.addAttribute("authenticated","logado");
		}
		int id_int = Integer.parseInt(id);
		int cod_int = Integer.parseInt(cod);
		Municipio m = this.municipio_service.findByCodANDCodINE(id_int, cod_int);
		this.weather_service.getWeatherForMunicipio(id_int,cod_int);
		model.addAttribute("title","Weather");
		model.addAttribute("weather", this.weather_service.getWeather());
		model.addAttribute("codProv", id);
		model.addAttribute("name_municipio",m.getNombre());
		return "Weather/weather";
    }
	
	
	@GetMapping("/message/{id}")
    public String message(@PathVariable(value = "id") String id, Model model) {
		if(id.equals("1")) {
			model.addAttribute("title","Registo efetuado com Sucesso");
			model.addAttribute("message","You have been register successufullty!");
		}
		else if(id.equals("2")) {
			model.addAttribute("title","Login efetuado com Sucesso");
			model.addAttribute("message","Login efetuado com Sucesso");
		}
		else if(id.equals("3")) {
			model.addAttribute("title","Logout efetuado com Sucesso");
			model.addAttribute("message","Logout efetuado com Sucesso");
		}
		else if(id.equals("6")) {
			model.addAttribute("title","Remove Favorite");
			model.addAttribute("message","Remove successufully");
		}
		return "Message/message";
    }
	
	
	@PostMapping("/search")
	public String searchAll(@RequestParam("tipo") String type, @RequestParam("nameforsearch")  String search, Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName == "anonymousUser") {
			model.addAttribute("authenticated","");
		}
		else {
			model.addAttribute("authenticated","logado");
		}
		model.addAttribute("title","List of Provinces with search");
		List<Province> list = new ArrayList<Province>();
		if(type.equals("provinces") && !search.equals("")){
			list = this.province_service.searchProvince(search);
		}
		else if(type.equals("communities") && !search.equals("") ) {
			list = this.province_service.searchCommunity(search);
		}
		else if(type.equals("all") && !search.equals("")) {
			
		}
		else {
			list = this.province_service.getAllCommunities();
		}		
		model.addAttribute("list", list);
		model.addAttribute("title","List of Provinces");
		return "Search/search";
	}
	
	@GetMapping("/logout")
	public String logout(Model model){
		return "Message/message/3";
	}
	
	
	
	public User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User user = this.user_service.findUserByEmail(currentPrincipalName);
		return user;
	}
	
	
	
	
	
	
	
	
	

	
	
}

