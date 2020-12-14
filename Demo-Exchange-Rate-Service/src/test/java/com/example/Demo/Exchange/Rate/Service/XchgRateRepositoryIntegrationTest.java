/**
 * 
 */
package com.example.Demo.Exchange.Rate.Service;



import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.example.xchgratedemo.DemoExchangeRateServiceApplication;
import com.example.xchgratedemo.model.XchgRate;
import static org.hamcrest.core.StringContains.containsStringIgnoringCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.MatcherAssert.assertThat; 





/**
 * @author supriya ghalsasi
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoExchangeRateServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:application.properties")
@EnableWebMvc
@AutoConfigureMockMvc


class XchgRateRepositoryIntegrationTest {
    
    @Before
    public void setUp() throws Exception {
    	//RestAssured.baseURI = "http://127.0.0.1";
    }
    
    @Test
    public void testFindAll()
      throws Exception {

    	XchgRate[] responseString = given().
    	        when().request("GET", "/ecbrefxchgrate/findall").
    	
        then().assertThat().statusCode(200).
        extract()
        .as(XchgRate[].class);
    	
    	assertEquals(responseString.length, 33);
    	 
    }
    
	
    @Test 
    public void testFindAllCurrencies() throws Exception {
  
    	String[] responseString = given().
    	        when().request("GET", "/ecbrefxchgrate/findallcurrencies").
    	
        then().assertThat().statusCode(200).
        extract()
        .as(String[].class);
    	
    	assertEquals(responseString.length, 33);
  
    }
	 
    
    @Test
    public void testGetHealth()
      throws Exception {

    	String responseString = given().
    	        when().request("GET", "/actuator/health").
    	
        then().assertThat().statusCode(200).
        extract().
        asString();
        assertThat(responseString, containsStringIgnoringCase("UP"));
	
    }
    

	@Test 
	public void testGetCurrencyCounter() throws Exception {
	  
	    String responseString = given(). when().request("GET",
	    "/ecbrefxchgrate/findallcurrenciesCounter").
	    then().assertThat().statusCode(200). extract(). asString();
	    assertThat(responseString, containsStringIgnoringCase("1"));
	  
	}
	  
	  
	@Test 
	public void testGetExchangeRate() throws Exception { String currency =
	    "usd"; String responseString = given(). pathParam("currency", currency).
	    when().request("GET", "/ecbrefxchgrate/{currency}").
	    then().assertThat().statusCode(200). extract(). asString();
	    assertThat(responseString, containsStringIgnoringCase("1.2159"));
    }
	 
    
    //
    
	
	  @Test public void testGetExchangeRateForPair() throws Exception { String
	  currency1 = "usd"; String currency2 = "gbp"; String responseString = given().
	  pathParam("currency1", currency1). pathParam("currency2", currency2).
	  when().request("GET", "/ecbfxrate/{currency1}-{currency2}").
	  
	  then().assertThat().statusCode(200). extract(). asString();
	  assertThat(responseString, containsStringIgnoringCase("1.346809924678777"));
	  
	  }
	 
    
    
	
	  @Test public void testGetAmountForCurrency() throws Exception { String
	  fromCur = "eur"; String fromAmount = "10"; String toCur = "gbp"; String
	  responseString = given(). param("fromCur", fromCur). param("fromAmount",
	  fromAmount). param("toCur", toCur). when().request("GET",
	  "/ecbrefxchgrate/ConvertAmountByCurrency").
	  
	  then().assertThat().statusCode(200). extract(). asString();
	  assertThat(responseString, containsStringIgnoringCase("9.028"));
	  
	  }
	 
    
    @Test
    public void testGetLinkToFxCharts()
    	      throws Exception {
    	String currency1 = "USD";
    	String currency2 = "GBP";
    	
    	String msg1 = " Please check if your currency pair is listed here: https://www.dailyfx.com/forex-rates. ";
		String msg2 =  " If your currency pair is listed above, the link to see FX Chart for given currency pair could be: ";
		String link = " https://www.dailyfx.com/"+currency1.toLowerCase()+ "-" + currency2.toLowerCase();
    	
    	String responseString = given().
    			pathParam("currency1", currency1).
    			pathParam("currency2", currency2).
    	    	when().request("GET", "/getLinktoFxCharts/{currency1}-{currency2}").
    	    	then().assertThat().statusCode(200).
    	        extract().
    	        asString();
    			assertThat(responseString, containsStringIgnoringCase(msg1+msg2+link));
    		
    	    }
    

}
