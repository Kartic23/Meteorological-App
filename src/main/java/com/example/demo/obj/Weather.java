package com.example.demo.obj;

import java.util.List;

public class Weather{
	

	int id;
	
	int codINE;
	
	int codProv;
	
	int currentTemp;
	
	int maxTemp;
	
	//@Column(name="minTemp")
	int minTemp;

	String date;
	
	public List<Rain> valuesOfRain;
	
  
	List<Day> nextDays;
     
    
	public Weather() {
		
	}

	public Weather(int codINE, int codProv, int currentTemp, int maxTemp, int minTemp, String date,List<Rain> rains, List<Day> nextDays) {
        this.codINE = codINE;
        this.codProv = codProv;
        this.currentTemp = currentTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.date = date;
        this.valuesOfRain = rains;
        this.nextDays = nextDays;
    }
	
	public int getId() {
        return id;
	}

    public void setId(int id) {
        this.id = id;
    }

    public int getCodINE() {
        return codINE;
    }

    public void setCodINE(int codINE) {
        this.codINE = codINE;
    }

    public int getCodProv() {
        return codProv;
    }

    public void setCodProv(int codProv) {
        this.codProv = codProv;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Rain> getValuesOfRain() {
		return valuesOfRain;
	}
    
	public void setValuesOfRain(List<Rain> valuesOfRain) {
		this.valuesOfRain = valuesOfRain;
	}    
    
    public List<Day> getNextDays() {
        return nextDays;
    }

    public void setNextDays(List<Day> nextDays) {
        this.nextDays = nextDays;
    }
}
