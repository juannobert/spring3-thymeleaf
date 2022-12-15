package com.juannobert.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juannobert.store.dtos.requests.ProductRequest;
import com.juannobert.store.dtos.response.ProductResponse;
import com.juannobert.store.mappers.ProductMapper;
import com.juannobert.store.models.Product;
import com.juannobert.store.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private ProductMapper mapper;
	
	public List<ProductResponse> findAll(){
		return repository.findAll()
				.stream()
				.map(mapper::toResponse)
				.toList();
	}
	
	@Transactional
	public Product insert(ProductRequest request) {
		Product entity = mapper.toModel(request);
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		Product entity = repository.getReferenceById(id);
		repository.delete(entity);
	}
	
}
