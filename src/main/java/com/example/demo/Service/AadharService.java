package com.example.demo.Service;

import java.io.IOException;
import java.util.Base64;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.AadharEntity;
import com.example.demo.model.AadharDto;
import com.example.demo.repository.AadharRepository;
@Service
public class AadharService {

	private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AadharService.class);
	
	@Autowired
	AadharRepository aadharRepo;
	
	public String saveAadharDetails(AadharDto aadharDto, MultipartFile[] frontPages)

	{
		try
		{
			for(MultipartFile frontPage:frontPages)
			{
			if(frontPage!=null && !frontPage.isEmpty())
			{
			
		    AadharEntity aadharentity=new AadharEntity();
		    aadharentity.setAadharNumber(aadharDto.getAadharNumber());
		    aadharentity.setName(aadharDto.getName());
		    aadharentity.setMobileNumber(aadharDto.getMobileNumber());
		
			byte[] bytes=frontPage.getBytes();
			String base64Image= Base64.getEncoder().encodeToString(bytes);
			aadharentity.setFrontPage(base64Image);
			aadharRepo.save(aadharentity);
			}
			}
		     log.info("Aadhar details saved successfully");
			 return "Success";
		} catch(IOException e) {
		   log.warn("Error processing aadhar details: " +e.getMessage(),e);
		   return "Error";
	  }
	}
	public String saveAadharBackDetails(AadharDto aadharDto, MultipartFile[] backPages)

	{
		try
		{
			for(MultipartFile backPage:backPages)
			{
			if(backPage!=null && !backPage.isEmpty())
			{
			
		    AadharEntity aadharentity=new AadharEntity();
		    aadharentity.setAadharNumber(aadharDto.getAadharNumber());
		    aadharentity.setName(aadharDto.getName());
		    aadharentity.setMobileNumber(aadharDto.getMobileNumber());
		
			byte[] bytes=backPage.getBytes();
			String base64Image= Base64.getEncoder().encodeToString(bytes);
			aadharentity.setFrontPage(base64Image);
			aadharRepo.save(aadharentity);
			}
			}
		     log.info("Aadhar details saved successfully");
			 return "Success";
		} catch(IOException e) {
		   log.warn("Error processing aadhar details: " +e.getMessage(),e);
		   return "Error";
	  }
	}
	
	
	
}
