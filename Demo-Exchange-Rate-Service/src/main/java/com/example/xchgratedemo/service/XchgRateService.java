/**
 * 
 */
package com.example.xchgratedemo.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.xchgratedemo.model.XchgRate;

/**
 * @author supri
 *
 */
@Service
public class XchgRateService implements IXchgRateService {
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
    XchgRateRepository repository;
    
    @Override
    public List<XchgRate> findAll() {
        return (List<XchgRate>) repository.findAll();
    }
    
	@Override
	public List<String> findAllCurrencies() {
		return (List<String>) repository.findAllCurrencies();
	}
	
	@Override
	public XchgRate findECBRefXchangeRate(String currency) {
		return repository.findByCurrency(currency);
	}





}
