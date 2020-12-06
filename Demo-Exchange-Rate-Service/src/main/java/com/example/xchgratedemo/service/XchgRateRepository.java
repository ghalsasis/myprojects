/**
 * 
 */
package com.example.xchgratedemo.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.xchgratedemo.model.XchgRate;

/**
 * @author supriya ghalsasi
 *
 */
@Repository
public interface XchgRateRepository extends JpaRepository<XchgRate, String>{
	
	@Query("select x.currency from XchgRate x")
    List<String> findAllCurrencies();

	/**
	 * @return
	 */
	List<XchgRate> findAll();

	/**
	 * @return
	 */
	@Query("select x from XchgRate x where x.currency = :currency")
	XchgRate findByCurrency(@Param("currency") String currency);
	

	

}
