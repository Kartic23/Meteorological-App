package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	 http
         .authorizeHttpRequests((auth) -> auth
	             .requestMatchers("/","/register","/login","/register/save","/message").permitAll()
	             .requestMatchers("/provinces","/showSelectProvince/{id}","/show/{id}/{cod}").permitAll()
	             .requestMatchers("/search").permitAll()
	             .requestMatchers("/favorite","/confirmfavorite","/listfavorites","/remove").permitAll()
	             .requestMatchers("/img/**").permitAll()
	             .anyRequest().authenticated())   
         .formLogin(
             form -> form
                     .loginPage("/login")
                     .loginProcessingUrl("/login")
                     .defaultSuccessUrl("/message/2",true)
                     .permitAll()
                     )
         .logout(
                 logout -> logout
                         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                         .logoutSuccessUrl("/message/3")
                         .permitAll()
                         );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
