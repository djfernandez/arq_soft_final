package com.tecsup.app.micro.entrega.application.usecase;

import com.tecsup.app.micro.entrega.domain.model.Delivery;
import com.tecsup.app.micro.entrega.domain.repository.DeliveryRepository;
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
public class GetAllDeliverysUseCase {

    private final DeliveryRepository productRepository;

    public List<Delivery> execute() {
        log.debug("Executing GetAllProductsUseCase");
        return productRepository.findAll();
    }
}
