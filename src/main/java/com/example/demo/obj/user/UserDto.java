package com.example.demo.obj.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;





public class UserDto {

	 private Long id;
	 
	    
	 @NotEmpty
	 private String firstName;
	 
	 @NotEmpty
	 private String lastName;
	 
	 
	 @NotEmpty(message = "Email should not be empty")
	 @Email
	 private String email;
	 
	 @NotEmpty(message = "Password should not be empty")
	 private String password;

	 public UserDto() {}
	 
	 public UserDto(String first,String last,String mail ,String psw) {
		this.firstName = first;
		this.lastName = last;
		this.email = mail;
		this.password = psw;
	 }
	 
	public String getEmail() {
		return email;
	}	 

	public String getFirstName() {
		return firstName;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
