package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.OtpEntity;

public interface UserRepository extends	JpaRepository<OtpEntity,Long> {

	
	
     Optional<OtpEntity> findByUserCodeAndOtp(String userCode, String otp);
}
