package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.MobileService;
import com.example.demo.model.Response2;

import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@Tag(name = "MobileOTP-API")
public class MobileController {

	
	@Autowired
	private MobileService mobileService;
	
	//private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MobileController.class);

	
	
	@PostMapping("/sendOtp")
	public ResponseEntity<Object> sendanOtp(@RequestParam String mobileNumber)

	{
		  mobileService.sendOtp(mobileNumber);
		  
		  return Response2.generateResponse(mobileNumber,HttpStatus.OK, "200");
		  
	}
	@PostMapping("/verifyotp")
	public ResponseEntity<Object>  verifyanotp(@RequestParam String mobileNumber, @RequestParam String enteredOtp)
	{
		 
		 boolean otpisValid=mobileService.verifyOtp(mobileNumber,enteredOtp);
		 if(otpisValid)
		 {
			 return Response2.generateResponse("OTP is valid",HttpStatus.OK, "200");
		 }
		 else
		 {
			 return Response2.generateResponse("Invalid OTP",HttpStatus.BAD_REQUEST, "400");
		 }
	}
}
