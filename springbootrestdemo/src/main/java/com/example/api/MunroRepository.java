package com.example.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

 
@Repository
public interface MunroRepository extends PagingAndSortingRepository<Munro, String>{

	public Page<Munro> findByHeightMGreaterThanEqualAndHeightMLessThanEqual(double minHt, double maxHt, Pageable paging);
 
}