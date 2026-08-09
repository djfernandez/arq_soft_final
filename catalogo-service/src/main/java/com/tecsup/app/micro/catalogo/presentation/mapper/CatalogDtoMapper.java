package com.tecsup.app.micro.catalogo.presentation.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tecsup.app.micro.catalogo.domain.model.Catalog;
import com.tecsup.app.micro.catalogo.presentation.dto.CatalogResponse;
import com.tecsup.app.micro.catalogo.presentation.dto.CreateCatalogRequest;
import com.tecsup.app.micro.catalogo.presentation.dto.UpdateCatalogRequest;

/**
 * Mapper entre DTOs de presentación y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface CatalogDtoMapper {

    /**
     * Convierte CreateProductRequest a Product de dominio
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "id", ignore = true)
    Catalog toDomain(CreateCatalogRequest request);

    /**
     * Convierte UpdateProductRequest a Product de dominio
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "id", ignore = true)
    Catalog toDomain(UpdateCatalogRequest request);

    /**
     * Convierte Product de dominio a ProductResponse
     */
    @Mapping(target = "available", expression = "java(product.isAvailable())")
    CatalogResponse toResponse(Catalog product);

    /**
     * Convierte lista de Products a lista de ProductResponse
     */
    List<CatalogResponse> toResponseList(List<Catalog> products);
}
