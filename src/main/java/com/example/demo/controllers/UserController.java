package com.example.demo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.obj.Municipio;
import com.example.demo.obj.user.User;
import com.example.demo.obj.user.UserDto;
import com.example.demo.service.MunicipioService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

     UserService userService;

    @Autowired
 	MunicipioService municipio_service;

     
     public UserController(UserService userService) {
         this.userService = userService;
     }
	
	@GetMapping("/register")
    public String showRegistrationForm(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName == "anonymousUser") {
			model.addAttribute("authenticated","");
		}
		else {
			model.addAttribute("authenticated","logado");
		}
        UserDto user = new UserDto();
		model.addAttribute("title","Register");
        model.addAttribute("user", user);
        return "User/register";
    }
	
	
	@PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName == "anonymousUser") {
			model.addAttribute("authenticated","");
		}
		else {
			model.addAttribute("authenticated","logado");
		}
       
		User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/message/1";
    }
	
	
	@GetMapping("/login")
    public String login(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName == "anonymousUser") {
			model.addAttribute("authenticated","");
		}
		else {
			model.addAttribute("authenticated","logado");
		}
        return "User/login";
    }
	
	@GetMapping("/favorite/{id}")
    public String goToFavorite(@PathVariable(value = "id") String id,Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName == "anonymousUser") {
			model.addAttribute("authenticated","");
		}
		else {
			model.addAttribute("authenticated","logado");
		}
        return "Favorites/favorite";
    }
	
	@GetMapping("/confirmfavorite/{id}")
    public String addFavorite(@PathVariable(value = "id") String id,Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName == "anonymousUser"){
			model.addAttribute("authenticated","");
		}
		else{
			model.addAttribute("authenticated","logado");
		}
		User user = this.userService.findUserByEmail(currentPrincipalName);
		Municipio m = this.municipio_service.findByID(Integer.parseInt(id));
		user.addFavorite(m);
		this.userService.updateUser(user);
        return "redirect:/message/4";
    }
	
	@GetMapping("/listfavorites")
	public String listOfFavorite(Model model) {
		model.addAttribute("title","list of Favorites");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User user = this.userService.findUserByEmail(currentPrincipalName);
		List<Municipio> list = user.getFavorites();
		model.addAttribute("list", list);
		return "Favorites/favoritelist";
	}
	
	@GetMapping("/remove/{id}")
	public String removeFavorite(@PathVariable(value = "id") String id,Model model) {
		model.addAttribute("title","list of Favorites");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User user = this.userService.findUserByEmail(currentPrincipalName);
		Municipio m = this.municipio_service.findByID(Integer.parseInt(id));
		user.getFavorites().remove(m);
		this.userService.updateUser(user);
		return "redirect:/message/6";
	}

}
