package com.tecsup.app.micro.entrega.application.usecase;

import com.tecsup.app.micro.entrega.domain.exception.CatalogNotFoundException;
import com.tecsup.app.micro.entrega.domain.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Eliminar un producto
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteCatalogUseCase {

    private final CatalogRepository productRepository;

    public void execute(Long id) {
        log.debug("Executing DeleteProductUseCase for id: {}", id);

        // Verificar que el producto existe
        if (!productRepository.existsById(id)) {
            throw new CatalogNotFoundException(id);
        }

        // Eliminar producto
        productRepository.deleteById(id);
        log.info("Product deleted successfully with id: {}", id);
    }
}
