package com.tecsup.app.micro.catalogo.infrastructure.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tecsup.app.micro.catalogo.domain.model.Catalog;
import com.tecsup.app.micro.catalogo.infrastructure.persistence.entity.CatalogEntity;

/**
 * Mapper entre entidades JPA y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface CatalogPersistenceMapper {

    /**
     * Convierte ProductEntity a Product de dominio
     */
    @Mapping(target = "createdByUser", ignore = true)
    Catalog toDomain(CatalogEntity entity);

    /**
     * Convierte Product de dominio a ProductEntity
     */
    @Mapping(target = "createdBy", ignore = true)
    CatalogEntity toEntity(Catalog product);

    /**
     * Convierte lista de ProductEntity a lista de Product
     */
    List<Catalog> toDomainList(List<CatalogEntity> entities);
}
