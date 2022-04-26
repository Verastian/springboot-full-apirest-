package com.verastian.fullApiRest.app.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.verastian.fullApiRest.app.models.Product;

public interface ProductService {

	public List<Product> findAll(Sort sort);//lista completa ordenada
	public Page<Product> findAll(Pageable pageable);//paginacion 
	public Product findById(long id);//
	public void delete(long id);
	public Product save(Product producto);
}