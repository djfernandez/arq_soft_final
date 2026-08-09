package com.tecsup.app.micro.catalogo.application.service;

import com.tecsup.app.micro.catalogo.application.usecase.*;
import com.tecsup.app.micro.catalogo.domain.model.Catalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de Aplicación de Producto
 * Orquesta los casos de uso y maneja las transacciones
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogApplicationService {

    private final GetAllCatalogsUseCase getAllCatalogsUseCase;
    private final GetCatalogByIdUseCase getCatalogByIdUseCase;
    private final GetAvailableCatalogsUseCase getAvailableCatalogsUseCase;
    private final GetCatalogsByUserUseCase getCatalogsByUserUseCase;
    private final CreateCatalogUseCase createCatalogUseCase;
    private final UpdateCatalogUseCase updateCatalogUseCase;
    private final DeleteCatalogUseCase deleteCatalogUseCase;

    @Transactional(readOnly = true)
    public List<Catalog> getAllCatalogs() {
        return getAllCatalogsUseCase.execute();
    }

    @Transactional(readOnly = true)
    /*
     * public Product getProductById(Long id) {
     * return getProductByIdUseCase.execute(id);
     * }
     */
    public Catalog getCatalogById(Long id, String jwtToken) {
        return getCatalogByIdUseCase.execute(id, jwtToken);
    }

    @Transactional(readOnly = true)
    public List<Catalog> getAvailableCatalogs() {
        return getAvailableCatalogsUseCase.execute();
    }

    @Transactional(readOnly = true)
    public List<Catalog> getCatalogsByUser(Long userId, String jwtToken) {
        return getCatalogsByUserUseCase.execute(userId, jwtToken);
    }

    @Transactional
    public Catalog createCatalog(Catalog product) {
        return createCatalogUseCase.execute(product);
    }

    @Transactional
    public Catalog updateCatalog(Long id, Catalog product) {
        return updateCatalogUseCase.execute(id, product);
    }

    @Transactional
    public void deleteCatalog(Long id) {
        deleteCatalogUseCase.execute(id);
    }
}
