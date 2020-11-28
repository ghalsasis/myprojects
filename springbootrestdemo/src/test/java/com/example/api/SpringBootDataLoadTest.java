package com.example.api;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.example.exception.IncorrectApiException;
import com.sun.tools.sjavac.Log;


@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootDataLoadTest {
	  
	  @Autowired MunroService munroService;	 
	  
  
	  @Test	  
	  public void testLoadDataForTestClass() {
		  assertEquals(602, ((List<Munro>) munroService.findAll()).size());//all munro list including blanks in POST1997 column
		  assertEquals(509, ((List<Munro>) munroService.findAllNonBlankCategory()).size());// munro list excluding blanks in POST1997 column
	  }
	    

	  
	  //Unit tests below require the SpringBoot application to be up 
	  /*
	  
	  @Test
	  public void testGetMunroListSuccess() throws URISyntaxException 
	  {

	      RestTemplate restTemplate = new RestTemplate();
	       
	      final String baseUrl = "http://localhost:8080" + "/munro/findAllSortByNameAsc";
	      URI uri = new URI(baseUrl);
	   
	      ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
	       
	      //Verify request succeed
	      assertEquals(200, result.getStatusCodeValue());
		  
	      
	  }
	  
	  @Test
	  public void testGetMunroSortedListSuccess() throws URISyntaxException 
	  {
		      RestTemplate restTemplate = new RestTemplate();
		       
		      final String baseUrl = "http://localhost:8080" + "/munro/findallSorted?sortByField=name&sortDirection=desc&maxHeight=1200.0&minHeight=900&size=15";
		      URI uri = new URI(baseUrl);
		   
		      ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		       
		      //Verify request succeed
		      assertEquals(200, result.getStatusCodeValue());
	      
	  }
	  */
	 
}
