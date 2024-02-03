package com.example.demo.Service;



import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.GdmsApiUsers;
import com.example.demo.repository.GdmsApiRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	

	@Autowired
        GdmsApiRepository gdmsRepository;

	Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		try {
			 GdmsApiUsers us = gdmsRepository.findByEmail(email);
			if (us == null) {
				throw new UsernameNotFoundException("Ecommerce  not found with email: " + email);

            } else {
				return new User(us.getEmail(), us.getPassword(), new ArrayList<>());
			}

		} catch (Exception e) {
			throw new UsernameNotFoundException("Ecommerce not found with email: " + email);
		}
 }
}
