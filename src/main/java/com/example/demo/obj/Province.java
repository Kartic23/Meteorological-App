package com.example.demo.obj;

import jakarta.persistence.*;


@Entity
@Table(name= "province")
public class Province {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	int codProv;
	String nombrePronvicia;
	int codaution;
	String communidadeProvincia;
	String capitalProvincia;
	
	public Province() {
		
	}
	
	public Province(int cod, String name,int c, String comunidade,String capital) {
		this.codProv = cod;
		this.nombrePronvicia = name;
		this.communidadeProvincia = comunidade;
		this.capitalProvincia = capital;
		this.codaution = c;
	}
	
	  public int getCodProv() {
	        return codProv;
	    }

	    public void setCodProv(int codProv) {
	        this.codProv = codProv;
	    }

	    public String getNombrePronvicia() {
	        return nombrePronvicia;
	    }

	    public void setNombrePronvicia(String nombrePronvicia) {
	        this.nombrePronvicia = nombrePronvicia;
	    }

	    public int getCodaution() {
	        return codaution;
	    }

	    public void setCodaution(int codaution) {
	        this.codaution = codaution;
	    }

	    public String getCommunidadeProvincia() {
	        return communidadeProvincia;
	    }

	    public void setCommunidadeProvincia(String communidadeProvincia) {
	        this.communidadeProvincia = communidadeProvincia;
	    }

	    public String getCapitalProvincia() {
	        return capitalProvincia;
	    }

	    public void setCapitalProvincia(String capitalProvincia) {
	        this.capitalProvincia = capitalProvincia;
	    }
	
	
	

	@Override
	public String toString() {
		return "\n Province: " +
		"this.codProv = " + this.codProv + " ;\r\n"
		+ "		this.nombrePronvicia =  " + this.nombrePronvicia + " ;\r\n"
		+ "		this.communidadeProvincia = " +  this.communidadeProvincia +" ;\r\n"
		+ "		this.capitalProvincia = " + this.capitalProvincia + " ;\r\n"
		+ "		this.codaution = "+ this.codaution +" ;\n";
	}
	
}
