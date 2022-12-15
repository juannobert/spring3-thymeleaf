package com.juannobert.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juannobert.store.dtos.response.ProductResponse;
import com.juannobert.store.mappers.ProductMapper;
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
	
}
