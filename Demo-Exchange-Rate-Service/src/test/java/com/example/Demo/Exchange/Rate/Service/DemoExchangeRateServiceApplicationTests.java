package com.example.Demo.Exchange.Rate.Service;

import static org.junit.Assert.assertEquals;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import com.example.xchgratedemo.DemoExchangeRateServiceApplication;
import com.example.xchgratedemo.exception.IncorrectApiException;
import com.example.xchgratedemo.model.XchgRate;
import com.example.xchgratedemo.service.XchgRateController;
import com.example.xchgratedemo.service.XchgRateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoExchangeRateServiceApplication.class)
//@WebMvcTest
class DemoExchangeRateServiceApplicationTests {
	
    @Autowired
	private XchgRateService xchgRateService;
    
    @Autowired
   	private XchgRateController xchgRateController;
    
   // protected MockMvc mvc;
    
    @Autowired
    WebApplicationContext webApplicationContext;

	/*
	 * protected void setUp() { mvc =
	 * MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); }
	 */

	@Test
	public void testFindAll() 
	{
		  assertEquals(33, ((List<XchgRate>) xchgRateService.findAll()).size());
	}
	
	@Test
	public void testFindAllCurrencies() throws Exception {
		/*
		 * String uri = "http://localhost:8080/ecbrefxchgrate/findallcurrencies";
		 * MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
		 * .accept(org.springframework.http.MediaType.APPLICATION_JSON_VALUE)).andReturn
		 * ();
		 * 
		 * int status = mvcResult.getResponse().getStatus(); assertEquals(200, status);
		 * String content = mvcResult.getResponse().getContentAsString(); String[]
		 * currencyList = mapFromJson(content, String[].class); assertEquals(33,
		 * currencyList.length);
		 */
		 assertEquals(33, ((List<String>) xchgRateService.findAllCurrencies()).size());
	}
	
	
	@Test
	public void testXchgRateForCurrency() {		
		assertEquals(1.2159, xchgRateService.findECBRefXchangeRate("USD").getXchgrate().doubleValue(), 0.01);		
	}
	
	
	@Test
	public void testFxRate() throws IncorrectApiException {
		String currency1 = "EUR";
		String currency2 = "GBP";
		double PairExchangeRate = xchgRateController.getPairExchangeRate(currency1, currency2);
		String responseStr = "The ECB Exchange rate for the given currency pair: " + currency1.toUpperCase() + " and " + currency2.toUpperCase() + " =  " + PairExchangeRate + 
			      ".\n\n This means 1 " + currency1.toUpperCase() + " = " + (1/PairExchangeRate) + " " + currency2.toUpperCase() ;
		assertEquals(HttpStatus.OK, xchgRateController.getXchgRateForCurrencyPair("EUR", "GBP").getStatusCode());
		assertEquals(responseStr, xchgRateController.getXchgRateForCurrencyPair("EUR", "GBP").getBody());
	}
	
	
    protected String mapToJson(Object obj) throws JsonProcessingException {
	   ObjectMapper objectMapper = new ObjectMapper();
	   return objectMapper.writeValueAsString(obj);
	}
	protected <T> T mapFromJson(String json, Class<T> clazz)
	      throws JsonParseException, JsonMappingException, IOException {
	      
	   ObjectMapper objectMapper = new ObjectMapper();
	   return objectMapper.readValue(json, clazz);
	}
	

}