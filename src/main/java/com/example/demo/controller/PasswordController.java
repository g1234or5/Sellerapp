package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.Service.PasswordService;
import com.example.demo.model.Response2;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@Tag(name = "ForgetPassword-API")
public class PasswordController {
	    
	    @Autowired
	  private PasswordService passwordService;

       @PostMapping("/reset")
       public ResponseEntity<Object> resetPassword(@RequestParam String userCode,@RequestParam String originalPassword,
    		   @RequestParam String newPassword,@RequestParam String confirmPassword)
       {
    	   String r=passwordService.resetpassword(userCode,originalPassword,newPassword,confirmPassword);
    	   if("Success".equals(r))
    	   {
    		   return Response2.generateResponse(r,HttpStatus.OK, "200");
   		   }
    	   else
    	   {
    		   return  Response2.generateResponse("Error", HttpStatus.BAD_REQUEST, "400");
   		   }
    	 }
       @PostMapping("/Forgetpassword-OTPSIGNIN")
  	 public ResponseEntity<?> otpsignin(@RequestParam String userCode, @RequestParam String username,@RequestParam String password,@RequestParam String otpSend,@RequestParam String otpExpiry) {
  		    
  		    
  		    String savedotp = passwordService.otpSignin(userCode, username, password,otpSend,otpExpiry);
  		    
  		    if (savedotp!= null) {
  		        return Response2.generateResponse(savedotp, HttpStatus.OK, "200");
  		    } else {
  		        return Response2.generateResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, "500");
  		    }
  		}
       @PostMapping("/password verify-otp")
  	 public ResponseEntity<?>  verifyotp(@RequestParam String userCode, @RequestParam String otp)
  	 {
  		 String result=passwordService.Verifyotp(userCode, otp);
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
    /*@PostMapping("/send-password-otp")
       public ResponseEntity<?> sendPasswordOtp(@RequestParam String username,@RequestParam String otp) {
    	  
           passwordService.sendPasswordOtp(username,otp);
           return  Response2.generateResponse("Otp send successfully", HttpStatus.OK, "200");
           } */
       
       
}
