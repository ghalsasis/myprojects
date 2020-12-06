/**
 * 
 */
package com.example.xchgratedemo.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.xchgratedemo.model.XchgRate;

/**
 * @author supri
 *
 */
@Component
public interface IXchgRateService {
	
	List<XchgRate> findAll();

	/**
	 * @return
	 */
	List<String> findAllCurrencies();

	/**
	 * @param currency
	 * @return
	 */
	XchgRate findECBRefXchangeRate(String currency);




}
