package com.example.api;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.exception.IncorrectApiException;
@Component
public interface IMunroService {
	
	List<Munro> findAll();  
	List<Munro> findAllNonBlankCategory();
	List<Munro> findAllSortByNameAsc();
	List<Munro> findAllSortByNameDesc();
	List<Munro> findAllSortByHeightMAsc();
	List<Munro> findAllSortByHeightMDesc();
	List<Munro> findAllSortByNameAndHeightM(String orderBy, String direction, String maxht, String minht, String size) throws IncorrectApiException;
}
