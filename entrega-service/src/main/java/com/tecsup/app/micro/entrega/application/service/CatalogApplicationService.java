package com.tecsup.app.micro.entrega.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsup.app.micro.entrega.application.usecase.CreateCatalogUseCase;
import com.tecsup.app.micro.entrega.application.usecase.DeleteCatalogUseCase;
import com.tecsup.app.micro.entrega.application.usecase.GetAllCatalogsIdsUseCase;
import com.tecsup.app.micro.entrega.application.usecase.GetAllCatalogsUseCase;
import com.tecsup.app.micro.entrega.application.usecase.GetAvailableCatalogsUseCase;
import com.tecsup.app.micro.entrega.application.usecase.GetCatalogByIdUseCase;
import com.tecsup.app.micro.entrega.application.usecase.GetCatalogsByUserUseCase;
import com.tecsup.app.micro.entrega.application.usecase.UpdateCatalogUseCase;
import com.tecsup.app.micro.entrega.domain.model.Catalog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final GetAllCatalogsIdsUseCase getAllCatalogsIdsUseCase;

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

    @Transactional(readOnly = true)
    public List<Catalog> getCatalogsByIds(List<Long> ids) {
        return getAllCatalogsIdsUseCase.execute(ids);
    }
}
