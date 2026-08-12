package com.tecsup.app.micro.entrega.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsup.app.micro.entrega.application.usecase.CreateDeliveryUseCase;
import com.tecsup.app.micro.entrega.application.usecase.DeleteDeliveryUseCase;
import com.tecsup.app.micro.entrega.application.usecase.GetAllDeliverysUseCase;
import com.tecsup.app.micro.entrega.application.usecase.GetDeliveryByIdUseCase;
import com.tecsup.app.micro.entrega.application.usecase.GetDeliverysByUserUseCase;
import com.tecsup.app.micro.entrega.application.usecase.UpdateDeliveryUseCase;
import com.tecsup.app.micro.entrega.domain.model.Delivery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Servicio de Aplicación de Producto
 * Orquesta los casos de uso y maneja las transacciones
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryApplicationService {

    private final GetAllDeliverysUseCase getAllCatalogsUseCase;
    private final GetDeliveryByIdUseCase getCatalogByIdUseCase;
    private final GetDeliverysByUserUseCase getCatalogsByUserUseCase;
    private final CreateDeliveryUseCase createCatalogUseCase;
    private final UpdateDeliveryUseCase updateCatalogUseCase;
    private final DeleteDeliveryUseCase deleteCatalogUseCase;

    @Transactional(readOnly = true)
    public List<Delivery> getAllCatalogs() {
        return getAllCatalogsUseCase.execute();
    }

    @Transactional(readOnly = true)
    /*
     * public Product getProductById(Long id) {
     * return getProductByIdUseCase.execute(id);
     * }
     */
    public Delivery getCatalogById(Long id, String jwtToken) {
        return getCatalogByIdUseCase.execute(id, jwtToken);
    }

    @Transactional(readOnly = true)
    public List<Delivery> getCatalogsByUser(Long userId, String jwtToken) {
        return getCatalogsByUserUseCase.execute(userId, jwtToken);
    }

    @Transactional
    public Delivery createCatalog(Delivery product) {
        return createCatalogUseCase.execute(product);
    }

    @Transactional
    public Delivery updateCatalog(Long id, Delivery product) {
        return updateCatalogUseCase.execute(id, product);
    }

    @Transactional
    public void deleteCatalog(Long id) {
        deleteCatalogUseCase.execute(id);
    }

}
