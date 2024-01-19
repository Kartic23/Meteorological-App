package com.example.demo.obj;



public class Day{
	
	
	int id;
		
	String date;
	
	int maxTemp;
	
	int minTemp;
	
	int maxRain;
	
		
	public Day() {
	}
	
	public Day(String date, int maxTemp, int minTemp, int maxRain) {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.maxRain = maxRain;
    }
	

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getMaxRain() {
        return maxRain;
    }

    public void setMaxRain(int maxRain) {
        this.maxRain = maxRain;
    }


}
