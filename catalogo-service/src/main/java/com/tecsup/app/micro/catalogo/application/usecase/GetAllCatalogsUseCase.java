package com.tecsup.app.micro.catalogo.application.usecase;

import com.tecsup.app.micro.catalogo.domain.model.Catalog;
import com.tecsup.app.micro.catalogo.domain.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Caso de uso: Obtener todos los productos
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllCatalogsUseCase {

    private final CatalogRepository productRepository;

    public List<Catalog> execute() {
        log.debug("Executing GetAllProductsUseCase");
        return productRepository.findAll();
    }
}
