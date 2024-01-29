package com.example.demo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.obj.user.User;
import com.example.demo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
        	
        	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        	
        	
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    authorities);
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    
}
