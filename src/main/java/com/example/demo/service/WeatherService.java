package com.example.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.obj.Day;
import com.example.demo.obj.Rain;
import com.example.demo.obj.Weather;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherService {

	private Weather actualWeather;

	private URL url_weatherMunicipio;

	public void defineURLForWeatherMunicipio(String id,String cod) throws MalformedURLException {
		this.url_weatherMunicipio = new URL("https://www.el-tiempo.net/api/json/v2/provincias/" + id + "/municipios/" + cod);
	}
	
	public Weather getWeather(){
		return this.actualWeather;
	}
	
	public String makeString(int id, int value) {
		String string_id = "";
		if(id < value)
			string_id += "0";
		string_id += id;
		return string_id;
	}
	
	public List<Rain> getRainValues(JsonNode data){
		List<Rain> list = new ArrayList<Rain>();
		data = data.get("hoy").get("prob_precipitacion");
		for(int i = 0; i < data.size(); i++) {
			Rain rain = new Rain(data.get(i).asInt());
			list.add(rain);

		}
		return list;
	}
	
	
	
	public int calculateMaxRain(JsonNode data) {
		if(data.size() == 0)
			return data.asInt();
		int max = data.get(0).asInt();
		for(int i = 1; i < data.size();i++){
			if(max < data.get(i).asInt()) {
				max = data.get(i).asInt();
			}
		}

		return max;
	}
	
	
	public List<Day> getNextDays(JsonNode data){
		List<Day> list = new ArrayList<Day>();
		data = data.get("proximos_dias");
		for(int i = 0; i < data.size(); i++) {
			JsonNode d = data.get(i);
			Day day = new Day();
			String[] date = d.get("@attributes").get("fecha").asText().split("-");
			day.setDate(date[2]+"-" + date[1]+"-"+date[0]);
			day.setMaxTemp(d.get("temperatura").get("maxima").asInt());
			day.setMinTemp(d.get("temperatura").get("minima").asInt());
			day.setMaxRain(calculateMaxRain(d.get("prob_precipitacion")));
			list.add(day);
		}
		return list;
	}
	
	
	public void readerWeatherForMunicipio(int prov, int mun) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		String codProv = makeString(prov,10);
		String codINE = makeString(mun,10000);
		this.defineURLForWeatherMunicipio( codProv, codINE );
		JsonNode data = mapper.readTree(this.url_weatherMunicipio);
		Weather weather = new Weather();
		weather.setCodProv(Integer.parseInt(codProv));
		weather.setCodINE(Integer.parseInt(codINE));
		weather.setMaxTemp(data.get("temperaturas").get("max").asInt());
		weather.setMinTemp(data.get("temperaturas").get("min").asInt());
		weather.setCurrentTemp(data.get("temperatura_actual").asInt());
		String[] date = data.get("fecha").asText().split("-");
		weather.setDate(date[2]+"-" + date[1]+"-"+date[0]);
		weather.setValuesOfRain(getRainValues(data.get("pronostico")));
		weather.setNextDays(getNextDays(data));
		this.actualWeather = weather;
	}
	
	public void getWeatherForMunicipio(int p,int m) {
		try{
			readerWeatherForMunicipio(p,m);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
