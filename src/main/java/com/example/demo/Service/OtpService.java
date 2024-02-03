package com.example.demo.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.entity.OtpEntity;
import com.example.demo.repository.UserRepository;
@Service
public class OtpService {
	
	
	private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OtpService.class);
	
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	
	public String otpSignin(String userCode, String username, String email,String otpSend,String otpExpiry)
	{    
		try
		{
		  String otp=generateRandomOtp();
		
		 OtpEntity oe=new OtpEntity();
		 if(oe!=null)
		 {
			 oe.setUserCode(userCode);
			 oe.setUsername(username);
			 oe.setEmail(email);
			 oe.setOtp(otp);
			 	LocalDateTime currentDateTime=LocalDateTime.now();
			 LocalDateTime otpSendStringFormatted = currentDateTime.plusMinutes(1);

		        
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		        String otpSendString= otpSendStringFormatted.format(formatter);

			 oe.setOtpSend(otpSendString);
			 LocalDateTime currentdateTime=LocalDateTime.now();
			 LocalDateTime otpExpiryStringFormatted = currentdateTime.plusMinutes(1);

			 
		        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		        String otpExpiryString= otpExpiryStringFormatted.format(formater);

			 oe.setOtpExpiry(otpExpiryString);
			 oe=userRepository.saveAndFlush(oe);
			 sendOtpByEmail(email,otp);
			 //sendAttachByEmail(email,otp);
			 //sendVerificationEmail(email);
		 }
		 
		 System.out.println("Otp sign in " +userCode+","+username+","+email+","+otpSend+","+otpExpiry);
		 return "Success";
		}  catch(Exception e)
		{
			log.error("Error in otpsign: " + e.getMessage());
			 return "Error";
		}
		 
		
		
		
	}
	public String  Verifyotp(String userCode, String otp)
	
	{     try
	       {  
		 	Optional<OtpEntity> otpOptional=userRepository.findByUserCodeAndOtp(userCode,otp);
		 	if (otpOptional.isPresent())
		 	{  
		 		 OtpEntity oe=otpOptional.get();
		 		 if(otp.equals(oe.getOtp()) && oe.getOtpExpiry()!=null)
		 		 {
                  oe.setOtp(otp);
		 		  String otpexpiry=oe.getOtpExpiry();
		 		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 		 LocalDateTime otpExpiry = LocalDateTime.parse(otpexpiry, formatter);

		         LocalDateTime currentDateTime = LocalDateTime.now();
		         if (otpExpiry.isAfter(currentDateTime)) {
		             
		             System.out.println("otpExpiry get expire after the current time");
		             return "Success";
		         }
		             else if(otpExpiry.isBefore(currentDateTime))
		             {
		            	 System.out.println("otpexpiry get expire before the current time");
		            	 return "Invalid OTP";
		             }
		          else {
		             
		             System.out.println("otpExpiry is not equal to current DateTime");
		             
		         }
		 		 oe.setOtpExpiry(otpexpiry);
		 		 userRepository.saveAndFlush(oe);
		 		 System.out.println("Verify otp" +userCode+","+otp);
		 		log.info("Verify otp : " + userCode + ", " + otp);
		 		 return "Success";
		 	   }
		 	  else
		 	   {
		 		return "It is wrong";
		 	   }
	       }
		 	else
		 	{
		 		return "User is not found";
		 	}
	       }catch(Exception e)
	         {
	    	  log.error("Error in sign in for otp" +e.getMessage()) ;
	    	     return "Error";
	          }
	}
	 private String generateRandomOtp() {
        
          Random random = new Random();
         int otpValue = 100000 + random.nextInt(900000);
         return String.valueOf(otpValue);
    }
	
	 private  void sendOtpByEmail(String email,String otp)
     	 { 
		  String subject="OTP verification";
		  String message ="<html><body>"
			        + "<p>Dear Gorank,</p>"
			        + "<p>Your OTP for verification is: <strong>" + otp + "</strong></p>"
			        + "<p>Please use this OTP within 1 minute to reset your password.</p>"
			        + "<p>If you are unable to change the password within 1 minute of OTP generation, please click on 'Forgot Password' and continue with the same process again.</p>"
			        + "<p>Wish you all the best!</p>"
			        + "<br>"
			        + "<p>Regards,<br>"
			        + "Campus Recruitment Team<br>"
			        + "Venture Consultancy Service, Lucknow</p>"
			        + "</body></html>";
		  emailService.sendEmail(subject,message,email);
		  	  
	 }
	
	
	 
	 
}
