package com.juannobert.store.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.juannobert.store.dtos.response.ProductResponse;
import com.juannobert.store.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	
	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	ProductResponse toResponse(Product model);

}
