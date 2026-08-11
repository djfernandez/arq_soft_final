package com.tecsup.app.micro.entrega.application.usecase;

import com.tecsup.app.micro.entrega.domain.exception.InvalidCatalogDataException;
import com.tecsup.app.micro.entrega.domain.model.Catalog;
import com.tecsup.app.micro.entrega.domain.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Crear un nuevo producto
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateCatalogUseCase {

    private final CatalogRepository productRepository;

    public Catalog execute(Catalog product) {
        log.debug("Executing CreateProductUseCase for product: {}", product.getName());

        // Validar datos del producto
        if (!product.isValid()) {
            throw new InvalidCatalogDataException("Invalid product data. Name, valid price and stock are required.");
        }

        // Guardar producto
        Catalog savedProduct = productRepository.save(product);
        log.info("Product created successfully with id: {}", savedProduct.getId());

        return savedProduct;
    }
}
