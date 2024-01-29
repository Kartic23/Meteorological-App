package com.example.demo.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.obj.Municipio;
import com.example.demo.obj.user.User;
import com.example.demo.obj.user.UserDto;
import com.example.demo.repository.UserRepository;


@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    
     
  
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
    
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
    }
      
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
   
    
    public void updateUser(User user){
    	this.userRepository.save(user);
    }
    
}
