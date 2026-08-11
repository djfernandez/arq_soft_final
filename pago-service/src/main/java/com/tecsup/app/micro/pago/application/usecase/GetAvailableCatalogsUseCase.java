package com.tecsup.app.micro.pago.application.usecase;

import com.tecsup.app.micro.pago.domain.model.Catalog;
import com.tecsup.app.micro.pago.domain.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Caso de uso: Obtener productos disponibles (stock > 0)
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetAvailableCatalogsUseCase {

    private final CatalogRepository productRepository;

    public List<Catalog> execute() {
        log.debug("Executing GetAvailableCatalogsUseCase");
        return productRepository.findAvailableCatalogs();
    }
}
