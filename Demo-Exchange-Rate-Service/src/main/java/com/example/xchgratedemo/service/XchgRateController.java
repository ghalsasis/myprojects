/**
 * 
 */
package com.example.xchgratedemo.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.xchgratedemo.exception.IncorrectApiException;
import com.example.xchgratedemo.model.XchgRate;

/**
 * @author supriya Ghalsasi
 *
 */
@RestController
public class XchgRateController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired  
	private IXchgRateService xchgRateService;  
	
	private int findAllCurrenciesCounter = 0;
	
	//Healthcheck: http://localhost:8080/actuator/health
	
	//Get all exchange rate date from the h2 database  
	@GetMapping(value = "/ecbrefxchgrate/findall", produces= "application/json")  
	public ResponseEntity<List<XchgRate>> getEcbRefXchgRate()   
	{  
		log.info("Getting ECB Reference exchange rates from database.");
		List<XchgRate> list = xchgRateService.findAll();  
		return (ResponseEntity<List<XchgRate>>) new ResponseEntity<List<XchgRate>>(list, HttpStatus.OK);
		
	}
	
	
	//Get all ECB supported currencies  from the h2 database  
	@GetMapping(value = "/ecbrefxchgrate/findallcurrencies", produces= "application/json")  
	public ResponseEntity<List<String>> getEcbRefCurrencies()   
	{  
		log.info("Getting All ECB Reference exchange rate currencies from database.");
		List<String> xchgRateCrrencylist = xchgRateService.findAllCurrencies();  
		findAllCurrenciesCounter++;
		return (ResponseEntity<List<String>>) new ResponseEntity<List<String>>(xchgRateCrrencylist, HttpStatus.OK);
		
	}
	
	//Get the count for the number of times the api: /ecbrefxchgrate/findallcurrencies was called.
	@GetMapping(value = "/ecbrefxchgrate/findallcurrenciesCounter", produces= "text/plain")  
	public ResponseEntity<String> getFindAllCurrenciesCounter()   
	{  
		log.info("Getting counter for the api : /ecbrefxchgrate/findallcurrencies");
		String responseStr = "The number of times the api: /ecbrefxchgrate/findallcurrencies was called =  "+this.findAllCurrenciesCounter;
		return (ResponseEntity<String>) new ResponseEntity<String>(responseStr, HttpStatus.OK);
		
	}
	
	@GetMapping("/ecbrefxchgrate/{currency}")
	@ResponseBody
	public ResponseEntity<String> getXchgRateForCurrency(@PathVariable String currency) throws IncorrectApiException   
	{  
		BigDecimal ECBRefExchangeRate;
		String responseStr = "";
		Date dt = new Date(0);
		List<String> xchgRateCrrencylist = xchgRateService.findAllCurrencies();  
		if(!xchgRateCrrencylist.contains(currency.toUpperCase())) {
			
			String errormsg = "Provide valid 3 letter currency code. To find all supported currencies, use the api:/ecbrefxchgrate/findallcurrencies";
			log.error(errormsg);
			throw new IncorrectApiException(errormsg);
		}	
		
		log.info("Getting ECB Reference Exchange Rate for EURO from the database for input currency = "+currency);			 
		ECBRefExchangeRate = xchgRateService.findECBRefXchangeRate(currency.toUpperCase()).getXchgrate();
		dt = xchgRateService.findECBRefXchangeRate(currency.toUpperCase()).getDate();	
		responseStr = "The ECB EURO Reference rate for the given currency: " + currency + ", for the date: " + dt +  " =  " + ECBRefExchangeRate;
		return (ResponseEntity<String>) new ResponseEntity<String>(responseStr, HttpStatus.OK);
		
	}  
	

	@GetMapping("/ecbfxrate/{currency1}-{currency2}")
	@ResponseBody
	public ResponseEntity<String> getXchgRateForCurrencyPair(@PathVariable String currency1,
															 @PathVariable String currency2) throws IncorrectApiException   
	{  
		
		
		double PairExchangeRate = 0.0;
		String responseStr = "";
		
		List<String> xchgRateCrrencylist = xchgRateService.findAllCurrencies();  
		if(!(xchgRateCrrencylist.contains(currency1.toUpperCase()) && xchgRateCrrencylist.contains(currency2.toUpperCase()))) {		
			String errormsg = "Provide valid 3 letter currency code. To find all supported currencies, use the api:/ecbrefxchgrate/findallcurrencies";
			log.error(errormsg);
			throw new IncorrectApiException(errormsg);			
		}
		
		log.info("Getting ECB Reference Exchange Rate from the database for input currency pair : "+currency1 + " and " + currency2);	
		if(currency1.equalsIgnoreCase(currency2))
			PairExchangeRate = 1.0;
		else
			PairExchangeRate = getPairExchangeRate(currency1.toUpperCase(), currency2.toUpperCase());
		responseStr = "The ECB Exchange rate for the given currency pair: " + currency1.toUpperCase() + " and " + currency2.toUpperCase() + " =  " + PairExchangeRate + 
				      ".\n\n This means 1 " + currency1.toUpperCase() + " = " + (1/PairExchangeRate) + " " + currency2.toUpperCase() ;
		
		return (ResponseEntity<String>) new ResponseEntity<String>(responseStr, HttpStatus.OK);
		
	}  
	
	/**
	 * @param currency1
	 * @param currency2
	 * @return
	 */
	public double getPairExchangeRate(String currency1, String currency2) {
		double xchgrate1 = xchgRateService.findECBRefXchangeRate(currency1).getXchgrate().doubleValue();
		double xchgrate2 = xchgRateService.findECBRefXchangeRate(currency2).getXchgrate().doubleValue();
		return xchgrate1/xchgrate2;		
	}

	
	@GetMapping(value = "/ecbrefxchgrate/ConvertAmountByCurrency", params = {"fromCur", "fromAmount", "toCur"}, produces= "text/plain")  
	public ResponseEntity<String> getConvertedAmountForCurrencyPair(@RequestParam String fromCur,
															        @RequestParam String fromAmount,
															        @RequestParam String toCur) throws IncorrectApiException   
	{  
		double inputAmount;
		 try {  
		        inputAmount = Double.parseDouble(fromAmount);  
		    }catch (NullPointerException e){  
		    	throw new IncorrectApiException("Please provide valid numeric value in fromAmount field");
		    }catch (NumberFormatException ex){  
		    	throw new IncorrectApiException("Please provide valid numeric value in fromAmount field");
		    }  
		double convertedAmount = 0.0;
		String responseStr = "";
		if(fromCur.equalsIgnoreCase(toCur))
			convertedAmount = inputAmount;
		
		List<String> xchgRateCrrencylist = xchgRateService.findAllCurrencies();  
		if(xchgRateCrrencylist.contains(fromCur.toUpperCase()) && xchgRateCrrencylist.contains(toCur.toUpperCase())) {			
			log.info("Getting amount into output currency : " + toCur);	
			convertedAmount = inputAmount/getPairExchangeRate(fromCur.toUpperCase(), toCur.toUpperCase());
			responseStr = "The amount of: " + fromAmount + " " + fromCur + " in currency " + toCur + "  =  " + convertedAmount;
		}
		else {
			String errormsg = "Provide valid 3 letter currency code. To find all supported currencies, use the api:/ecbrefxchgrate/findallcurrencies";
			log.error(errormsg);
			throw new IncorrectApiException(errormsg);
		}
		
		return (ResponseEntity<String>) new ResponseEntity<String>(responseStr, HttpStatus.OK);
		
	}  
	
	
	
	
	@GetMapping("/getLinktoFxCharts/{currency1}-{currency2}")
	@ResponseBody
	public ResponseEntity<String> getLinkToFxCharts(@PathVariable  String currency1,
														  @PathVariable String currency2) throws IncorrectApiException     
	{  	
		
		String msg1 = " Please check if your currency pair is listed here: https://www.dailyfx.com/forex-rates. ";
		String msg2 =  " If your currency pair is listed above, the link to see FX Chart for given currency pair could be: ";
		String link = " https://www.dailyfx.com/"+currency1.toLowerCase()+ "-" + currency2.toLowerCase();
		
		return (ResponseEntity<String>) new ResponseEntity<String>(msg1+msg2+link, HttpStatus.OK);
		
	}  
	

	

}
