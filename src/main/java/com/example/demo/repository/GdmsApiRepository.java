package com.example.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.GdmsApiUsers;
public interface GdmsApiRepository extends JpaRepository<GdmsApiUsers, String>{

	GdmsApiUsers findByEmail(String email);
	GdmsApiUsers findByUserCode(String userCode);
	
	Optional<GdmsApiUsers> findByMobileNumber(String mobileNumber);
	
	
}
