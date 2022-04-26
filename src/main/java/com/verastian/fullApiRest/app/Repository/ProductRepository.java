package com.verastian.fullApiRest.app.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.verastian.fullApiRest.app.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.presentation")
	public List<Product> findAll(Sort sort);
	
	@Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.presentation", 
			countQuery ="SELECT COUNT(p) FROM Product p LEFT JOIN p.presentation" )
	public Page<Product> findAll(Pageable pageable);
	
	@Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.presentation WHERE p.id = :id")
	public Product findById(long id);
}
