package com.example.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.exception.IncorrectApiException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Service
@Repository
@Transactional
public class MunroService implements IMunroService{
	
	@PersistenceContext
	private EntityManager em;
	
    // @Autowired annotation provides the automatic dependency injection.
    @Autowired
    MunroRepository repository;
    
    @Override
    public List<Munro> findAll() {
      return (List<Munro>) repository.findAll();
    }
	
    public List<Munro> findAllNonBlankCategory() {
        final List<Munro> munroList = new ArrayList<>();
        repository.findAll().forEach(munro -> munroList.add(munro));        
        //remove those from list which have blank value in POST1997
        List<Munro> NonBlankMunroCategoryList = munroList.stream().filter(s -> StringUtils.isNotBlank(s.getcategory())).collect(Collectors.toList());
        return NonBlankMunroCategoryList;
    }
    
    @Override
    public List<Munro> findAllSortByNameAsc() {
        return (List<Munro>) repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public List<Munro> findAllSortByNameDesc() {
        return (List<Munro>) repository.findAll(Sort.by(Sort.Direction.DESC, "name"));
    }
    
    @Override
    public List<Munro> findAllSortByHeightMAsc() {
        return (List<Munro>) repository.findAll(Sort.by(Sort.Direction.ASC, "heightM"));
    }
    
    @Override
	public List<Munro> findAllSortByHeightMDesc() {
		return (List<Munro>) repository.findAll(Sort.by(Sort.Direction.DESC, "heightM"));
	}

    @Override
    public List<Munro> findAllSortByNameAndHeightM(String sortByField, String sortDirection, String maxHeight, String minHeight, String size) throws IncorrectApiException {
    	int limit = 0;
    	double min = 0.0;
    	double max = 0.0;
    	try {
    		limit = Integer.parseInt(size);
    		min = Double.parseDouble(minHeight);
    		max = Double.parseDouble(maxHeight);
    	} catch (NumberFormatException e) {
    		throw new IncorrectApiException("Incorrect use of api. MinHeight, MaxHeight, size parameters should be numeric");
    	}
    	Sort sortOrder = Sort.by(Sort.Direction.ASC, sortByField); // default sorting direction Ascending
    	
		
		if ( sortDirection.equalsIgnoreCase("desc"))
			sortOrder = Sort.by(Sort.Direction.DESC, sortByField); 
    	else {
    		if ( sortDirection.equalsIgnoreCase("asc"))
    			sortOrder = Sort.by(Sort.Direction.ASC, sortByField); 
    		else
    			throw new IncorrectApiException("Incorrect use of api. Sort direction should be asc or desc");
    	}
    		
		Pageable paging = PageRequest.of(0, limit, sortOrder );        
        Page<Munro> pagedResult = (Page<Munro>) repository.findByHeightMGreaterThanEqualAndHeightMLessThanEqual(min, max, paging);
         
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Munro>();
        }

    }
    
    

}
