package com.example.demo.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.AadharService;
import com.example.demo.model.AadharDto;
import com.example.demo.model.Response2;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@Tag(name = "Aadhar-API")
public class AadharController {
	
	@Autowired
	AadharService aadharService;
	@PostMapping("/uploadfrontaadhar")
	public ResponseEntity<Object> addAadharDetails(@RequestBody AadharDto aadharDto, @RequestParam("file") MultipartFile[] frontPage)
	{
          
	String result=aadharService.saveAadharDetails(aadharDto, frontPage);
	if("Success".equals(result))
	{
		return Response2.generateResponse("Aaadhar card  front details are get upload", HttpStatus.OK, "200");
	}
	else
	{
		return Response2.generateResponse("Aadhar Card front details are not upload", HttpStatus.INTERNAL_SERVER_ERROR, "500");
	}
           
	}
   @PostMapping("/uploadbackaadhar")
	public ResponseEntity<Object> addAadharBackDetails(@RequestBody AadharDto aadharDto, @RequestParam("file") MultipartFile[] backPage)
	{
          
		String result=aadharService.saveAadharBackDetails(aadharDto, backPage);
		if("Success".equals(result))
		{
		return Response2.generateResponse("Aaadhar card back details are get upload", HttpStatus.OK, "200");
		}
		else
		{
			return Response2.generateResponse("Aadhar Card back details are not upload", HttpStatus.INTERNAL_SERVER_ERROR, "500");
		}
           
	}
	
}
