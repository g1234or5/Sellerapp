package com.example.demo.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
@Service
public class MobileService {

	
	private Map<String,String> mp=new HashMap<>();
	
	  public void  sendOtp(String username)
	  {  
		 String otp=generateOtp();
		 mp.put(username, otp);
		 System.out.println("Sending OTP number " +username + ":" +otp);
	  }
	  
	  
	  public boolean verifyOtp(String mobileNumber, String enteredOtp)
	  {
		   String storedotp=mp.get(mobileNumber);
		   if(storedotp!=null && storedotp.equals(enteredOtp))
		   {
			   return true;
		   }
		   else
		   {
			   return false;
		   }

	  }
	
	  
	  private String generateOtp() {
	        Random random = new Random();
	        int otp = 100000 + random.nextInt(900000);
	        return String.valueOf(otp);
	    }
}
