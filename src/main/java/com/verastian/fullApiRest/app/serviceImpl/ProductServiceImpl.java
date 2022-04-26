package com.verastian.fullApiRest.app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.verastian.fullApiRest.app.Repository.PresentationRepository;
import com.verastian.fullApiRest.app.Repository.ProductRepository;
import com.verastian.fullApiRest.app.models.Presentation;
import com.verastian.fullApiRest.app.models.Product;
import com.verastian.fullApiRest.app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PresentationRepository presentationRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Product findById(long id) {
		return productRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(long id) {
		productRepository.deleteById(id);
	}
	@Transactional
	@Override
	public Product save(Product product) {
		Presentation presentation = presentationRepository.
				findById(product.getPresentation().getId()).orElse(null);
		product.setPresentation(presentation);
		
		return productRepository.save(product);
	}

}
