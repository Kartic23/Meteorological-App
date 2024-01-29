package com.example.demo.obj;

import java.util.List;

import com.example.demo.obj.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="municipio")
public class Municipio{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	int codINE;
	
	int codProv;
	
	String nombreProvincia;
	
	String nombre;
	
	String poblacionCapital;
	
	@ManyToMany(mappedBy = "favorites")
    List<User> users;
	
	
	public Municipio() {
     
    }
	
	  public Municipio(int codINE, int codProv, String nombreProvincia, String nombre, String poblacionCapital) {
	        this.codINE = codINE;
	        this.codProv = codProv;
	        this.nombreProvincia = nombreProvincia;
	        this.nombre = nombre;
	        this.poblacionCapital = poblacionCapital;
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

	    public String getNombreProvincia() {
	        return nombreProvincia;
	    }

	    public void setNombreProvincia(String nombreProvincia) {
	        this.nombreProvincia = nombreProvincia;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getPoblacionCapital() {
	        return poblacionCapital;
	    }

	    public void setPoblacionCapital(String poblacionCapital) {
	        this.poblacionCapital = poblacionCapital;
	    }
	    
	    
		public List<User> getUsers() {
			return users;
		}
		public void setUsers(List<User> users) {
			this.users = users;
		}

}
