package com.verastian.fullApiRest.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verastian.fullApiRest.app.models.Product;
import com.verastian.fullApiRest.app.service.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	// Mostrar todos en pagina y tamaño de lista
	@GetMapping
	public ResponseEntity<List<Product>> findAll(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {// numero de la pagina y el largo del las lista

		Sort sortByName = Sort.by("name");

		ResponseEntity<List<Product>> entity = null;

		List<Product> products = null;
//Si tiene numero de pagina y numero de tamaño de la lista o no
		if (page != null && size != null) {
			// con paginacion
			Pageable pageable = PageRequest.of(page, size, sortByName);

			products = productService.findAll(pageable).getContent();// getContent de la Interfaz Pageable

		} else {
			// sin paginacion
			products = productService.findAll(sortByName);
		}
//Si la lista producto tiene contenido o no
		if (products.size() > 0) {

			entity = new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		} else {
			entity = new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		}

		return entity;

	}

//	mostrar por Id
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {

		Product product = productService.findById(id);

		ResponseEntity<Product> responseEntity = null;

		if (product != null) {
			responseEntity = new ResponseEntity<Product>(product, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Product>(HttpStatus.NO_CONTENT);

		}

		return responseEntity;

	}
//Crear un Producto
	@PostMapping
	public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Product product, BindingResult result) {

		Map<String, Object> responseAsMap = new HashMap<String, Object>();

		ResponseEntity<Map<String, Object>> responseEntity = null;

		List<String> errors = null;

//		gestion de errores 
		if (result.hasErrors()) {
			errors = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}

			responseAsMap.put("errors", errors);
			responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
			return responseEntity;
		}

//		crear Producto
		try {

			Product productFromDb = productService.save(product);

			if (productFromDb != null) {
				responseAsMap.put("product", product);
				responseAsMap.put("message", "El producto con ID:" + " Se ha creado con exito");
				responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("message", "El producto No se ha creado con correctamente");
				responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (DataAccessException e) {
			responseAsMap.put("message", "El producto NO se ha creado con exito:" + e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
	
//	Actualizar un Producto
	@PutMapping(value = "{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable long id,@Valid @RequestBody Product product, BindingResult result) {
		
		Map<String, Object> responseAsMap = new HashMap<String, Object>();
		
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		List<String> errors = null;
		
//		gestion de errores 
		if (result.hasErrors()) {
			errors = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}
			
			responseAsMap.put("errors", errors);
			responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
			return responseEntity;
		}
		
//		crear Producto
		try {
			product.setId(id);
			Product productFromDb = productService.save(product);
			
			if (productFromDb != null) {
				responseAsMap.put("product", product);
				responseAsMap.put("message", "El producto con ID:" + " Se ha Actualizado con exito");
				responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("message", "El producto NO se ha Actualizado con correctamente");
				responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (DataAccessException e) {
			responseAsMap.put("message", "El producto NO se ha Actualizado con exito:" + e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
		
	}

}
