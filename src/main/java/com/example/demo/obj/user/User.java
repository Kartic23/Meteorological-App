package com.example.demo.obj.user;

import java.util.List;

import com.example.demo.obj.Municipio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;



@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    
    @ManyToMany
    @JoinTable(
      name = "favorites", 
      joinColumns = @JoinColumn(name = "user_id"), 
      inverseJoinColumns = @JoinColumn(name = "municipio_id"))
    List<Municipio> favorites;

    public String getEmail() {
		return email;
	}
    
    public void setEmail(String email) {
		this.email = email;
	}
    
    public int getId() {
		return id;
	}

    public void setId(int id) {
		this.id = id;
	}
    
    public String getName() {
		return name;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
    public String getPassword() {
		return password;
	}
    
    public void setPassword(String password) {
		this.password = password;
	}
    
    public List<Municipio> getFavorites() {
		return favorites;
	}
    
    public void setFavorites(List<Municipio> favorites) {
		this.favorites = favorites;
	}
    
    public void addFavorite(Municipio favorite) {
		this.favorites.add(favorite);
	}
}

