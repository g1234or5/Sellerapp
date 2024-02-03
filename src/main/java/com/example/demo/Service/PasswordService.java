package com.example.demo.Service;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import com.example.demo.entity.GdmsApiUsers;
import com.example.demo.entity.OtpEntity;
import com.example.demo.repository.GdmsApiRepository;
import com.example.demo.repository.UserRepository;
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.rest.api.v2010.account.MessageCreator;
//import com.twilio.type.PhoneNumber;
@Service
public class PasswordService {
	
	
	private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PasswordService.class);
	
	@Autowired
    private GdmsApiRepository gdmsRepository;
	
	
	
	
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	 @Autowired
	private UserRepository userRepository;
  
	public String resetpassword(String userCode, String originalPassword, String newPassword, String confirmPassword)
	{
		try
		{
			if(!newPassword.equals(confirmPassword)) {
				return "pass word did not match";
			}
			
			
			GdmsApiUsers us=gdmsRepository.findByUserCode(userCode);
			if(us==null)
			{
				return "User not found";
			}else {
				String originalHashPassword=bcryptEncoder.encode(originalPassword);
				String newHashPassword=bcryptEncoder.encode(newPassword);
				
				if(originalHashPassword.equals(us.getPassword())) {
					us.setPassword(newHashPassword);
				     us=gdmsRepository.saveAndFlush(us);
				    System.out.println("Password resent successfully" +userCode);
				    log.info("password succes changed for the user .{}",userCode);
				    return "Success";
					
				}else {
					return "old password does not match";
				}
			}
		     
			
			
	   } catch(Exception e) {
		log.error("There is error for reset the password"+ e.getMessage());
		   return "Error";
	   }
	}
	public String otpSignin(String userCode, String username, String password,String otpSend,String otpExpiry)
	{    
		try
		{
		  String otp=generateRandomOtp();
		
		 OtpEntity oe=new OtpEntity();
		 if(oe!=null)
		 {
			 oe.setUserCode(userCode);
			 oe.setUsername(username);
			 String encodedPassword=bcryptEncoder.encode(password);
			 
			 oe.setPassword(encodedPassword);
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
			 //sendOtpByEmail(email,otp);
			 //sendAttachByEmail(email,otp);
			 //sendVerificationEmail(email);
			 //sendPasswordOtp(username,otp);
		 }
		 System.out.println("Otp sign in " +userCode+","+username+","+password+","+otpSend+","+otpExpiry);
		 return "Success";
		}  catch(Exception e)
		{
			log.error("Error in otpsign: " + e.getMessage());
			 return "Error";
		}
		 
		
		
		
	}
	 public String generateRandomOtp() {
	        
         Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
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
	 
	/* public void  sendPasswordOtp(String username, String otp)
	  {  
		Twilio.init(twilioAccountSid,twilioAuthToken);
		try
		
		{
			
			String messageBody="Your otp number is" +otp;
			Message message=Message.creator(new PhoneNumber(username),new PhoneNumber(twilioPhoneNumber), messageBody).create();
			//System.out.println("SMS sent successfully with SID: " + message.getSid());
			System.out.println("Sending OTP number " +username + ":" +otp);
			
		} catch(Exception e)
		{
			log.error("Error sending SMS: " + e.getMessage());
		}
		
		// System.out.println("Sending OTP number " +username + ":" +otp);
	  }*/
	  
	
}

	
		

	
	
	
	


