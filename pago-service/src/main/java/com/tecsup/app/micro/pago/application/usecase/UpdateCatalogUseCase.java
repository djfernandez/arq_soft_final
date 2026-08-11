package com.tecsup.app.micro.pago.application.usecase;

import com.tecsup.app.micro.pago.domain.exception.InvalidCatalogDataException;
import com.tecsup.app.micro.pago.domain.exception.CatalogNotFoundException;
import com.tecsup.app.micro.pago.domain.model.Catalog;
import com.tecsup.app.micro.pago.domain.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Actualizar un producto existente
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateCatalogUseCase {

    private final CatalogRepository productRepository;

    public Catalog execute(Long id, Catalog productDetails) {
        log.debug("Executing UpdateProductUseCase for id: {}", id);

        // Verificar que el producto existe
        Catalog existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new CatalogNotFoundException(id));

        // Validar datos del producto
        if (!productDetails.isValid()) {
            throw new InvalidCatalogDataException("Invalid product data. Name, valid price and stock are required.");
        }

        // Actualizar campos
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStock(productDetails.getStock());
        existingProduct.setCategory(productDetails.getCategory());

        // Guardar cambios
        Catalog updatedProduct = productRepository.save(existingProduct);
        log.info("Product updated successfully with id: {}", updatedProduct.getId());

        return updatedProduct;
    }
}
