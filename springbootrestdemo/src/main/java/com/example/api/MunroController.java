package com.example.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.IncorrectApiException;



@RestController
public class MunroController 
{
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired  
	private IMunroService munroService;  
	
	//Get all munro from the h2 database  
	@GetMapping(value = "/munro/findall", produces= "application/vnd.jcg.api.v1+json")  
	public ResponseEntity<List<Munro>> getMunro()   
	{  
		log.info("Getting munro details from the database.");
		List<Munro> list = munroService.findAll();  
		return (ResponseEntity<List<Munro>>) new ResponseEntity<List<Munro>>(list, HttpStatus.OK);
		
	}  
	
	
	//Get all munro from the h2 database  
	@GetMapping(value = "/munro/findallnonblankcategory", produces= "application/vnd.jcg.api.v1+json")  
	public ResponseEntity<List<Munro>> getMunroNonBlank()   
	{  
		log.info("Getting munro details from the database.");
		List<Munro> list = munroService.findAllNonBlankCategory();  
		return (ResponseEntity<List<Munro>>) new ResponseEntity<List<Munro>>(list, HttpStatus.OK);
		
	}  
	
	//Get all munro from the h2 database  
	@GetMapping(value = "/munro/findallSorted", params = {"sortByField", "sortDirection", "maxHeight", "minHeight", "size"}, produces= "application/vnd.jcg.api.v1+json")  
	public ResponseEntity<List<Munro>> getMunroSortByNameAndHeight(@RequestParam(defaultValue = "name", required=false)  String sortByField, 
																   @RequestParam(defaultValue = "asc", required=false) String sortDirection, 
																   @RequestParam(defaultValue = ""+Double.MAX_VALUE, required=false) String maxHeight, 
																   @RequestParam(defaultValue = "0.0", required=false) String minHeight, 
																   @RequestParam(defaultValue = "10", required=false) String size) throws IncorrectApiException   
	{  
		log.info("Getting munro details from the database.");
		List<Munro> list = munroService.findAllSortByNameAndHeightM(sortByField, sortDirection, maxHeight, minHeight, size);  
		return (ResponseEntity<List<Munro>>) new ResponseEntity<List<Munro>>(list, HttpStatus.OK);
		
	}  
	
	@GetMapping("/munro/findallSorted")
	public String getMunroSortByNameAndHeight() throws IncorrectApiException {
		throw new IncorrectApiException("Incorrect use of api. Provide parameters with or without values");
	}

	@GetMapping("/munro")  
	public void handleMunroRequest() throws IncorrectApiException  {
		throw new IncorrectApiException("Incorrect use of api. Do you want to use /munro/findall or /munro/findallnonblankcategory ?");
	}
	
	@GetMapping("/")
	public void handleRequest() throws IncorrectApiException {
		throw new  IncorrectApiException("Incorrect use of api. Do you want to use /munro/findall or /munro/findallnonblankcategory ?");
	}
	
	
	
	//Get all munro from the h2 database sorted by name asc 
	@GetMapping(value = "/munro/findAllSortByNameAsc", produces= "application/vnd.jcg.api.v1+json")  
	public ResponseEntity<List<Munro>> getAllSortByNameAsc()   
	{  
		log.info("Getting munro details from the database.");
		List<Munro> list = munroService.findAllSortByNameAsc();  
		return (ResponseEntity<List<Munro>>) new ResponseEntity<List<Munro>>(list, HttpStatus.OK);
	}
	
	//Get all munro from the h2 database sorted by name desc 
	@GetMapping(value = "/munro/findAllSortByNameDesc", produces= "application/vnd.jcg.api.v1+json")  
	public ResponseEntity<List<Munro>> getAllSortByNameDesc()   
	{  
		log.info("Getting munro details from the database.");
		List<Munro> list = munroService.findAllSortByNameDesc();  
		return (ResponseEntity<List<Munro>>) new ResponseEntity<List<Munro>>(list, HttpStatus.OK);
	}
	
	
	//Get all munro from the h2 database sorted by Height M asc 
	@GetMapping(value = "/munro/findAllSortByHeightMAsc", produces= "application/vnd.jcg.api.v1+json")  
	public ResponseEntity<List<Munro>> getAllSortByHeightMAsc()   
	{  
		log.info("Getting munro details from the database.");
		List<Munro> list = munroService.findAllSortByHeightMAsc();  
		return (ResponseEntity<List<Munro>>) new ResponseEntity<List<Munro>>(list, HttpStatus.OK);
	}
	
	//Get all munro from the h2 database sorted by Height in M desc 
	@GetMapping(value = "/munro/findAllSortByHeightMDesc", produces= "application/vnd.jcg.api.v1+json")  
	public ResponseEntity<List<Munro>> getAllSortByHeightMDesc()   
	{  
		log.info("Getting munro details from the database.");
		List<Munro> list = munroService.findAllSortByHeightMDesc();  
		return (ResponseEntity<List<Munro>>) new ResponseEntity<List<Munro>>(list, HttpStatus.OK);
	}
	
   
    
}
