
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.EmailService;
import com.example.demo.Service.OtpService;
//import com.example.demo.entity.OtpEntity;
//import com.example.demo.model.OtpRequest;
import com.example.demo.model.Response2;

import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@Tag(name = "EmailOTP-API")
public class OtpController {

	@Autowired
	 OtpService otpService;
	@Autowired
	 EmailService emailService;
	 
	 @PostMapping("Verify-otp")
	 public ResponseEntity<?>  verifyotp(@RequestParam String userCode, @RequestParam String otp)
	 {
		 String result=otpService.Verifyotp(userCode, otp);
		 if("Success".equals(result))
		 {
			 return Response2.generateResponse(result, HttpStatus.OK, "200");
			 
		 }
		 else if("Invalid OTP".equals(result))
		 {
			 return Response2.generateResponse(result, HttpStatus.BAD_REQUEST, "400");
			 
		 }
		 else
		 {
			 return  Response2.generateResponse(result, HttpStatus.INTERNAL_SERVER_ERROR, "500");
		 }
	 }
	 @PostMapping("/OTPSIGNIN")
	 public ResponseEntity<?> otpsignin(@RequestParam String userCode, @RequestParam String username,@RequestParam String email,@RequestParam String otpSend,@RequestParam String otpExpiry) {
		    
		    
		    String savedotp = otpService.otpSignin(userCode, username, email,otpSend,otpExpiry);
		    
		    if (savedotp!= null) {
		        return Response2.generateResponse(savedotp, HttpStatus.OK, "200");
		    } else {
		        return Response2.generateResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, "500");
		    }
		}
	 @PostMapping("/VerifEmail")
	 public ResponseEntity<?> verificationEmail(@RequestParam String email) {
	     // Assuming you have a method named sendVerificationEmail in your emailService
		 boolean verificationsuccess=sendVerificationEmail(email);
		 if(verificationsuccess)
		 {
		  return Response2.generateResponse("Email verified successfully", HttpStatus.OK, "200");
		 }
		 else
		 {
			 return Response2.generateResponse("Email is not verified successfully", HttpStatus.BAD_REQUEST, "400");
		 }
	     
	 }
	 private boolean  sendVerificationEmail(String email)
	 {
		 String subject = "Verify your email";
		 String message = "<html>" +
				    "<body>" +
				    "<p>Hello,</p>" +
				    
				    "<p>You receive this message either because you recently applied to, registered on our website, or are considered as a potential candidate for a job offered through our portal.</p>" +
				    
				    "<p>Please validate your email address by clicking <a href=\"YOUR_VALIDATION_LINK\">here</a> (please log in using your existing credentials).<br>" +
				    "This will take only a few seconds and is to make sure that the recruiters can safely reach you through email.</p>" +
				    
				    "<p>Kind regards,<br>" +
				    "Recruitment Team<br>" +
				    "Venture Consultancy Services, Lucknow</p>" +
				    
				    "</body>" +
				    "</html>";
				    
		    //emailService.sendEmail(subject, message, email);
		 emailService.sendEmail(subject, message, email);
		 return true;
	 }
}
