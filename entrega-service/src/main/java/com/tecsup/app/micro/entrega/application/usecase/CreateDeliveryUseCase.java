package com.tecsup.app.micro.entrega.application.usecase;

import com.tecsup.app.micro.entrega.domain.exception.InvalidDeliveryDataException;
import com.tecsup.app.micro.entrega.domain.model.Delivery;
import com.tecsup.app.micro.entrega.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Crear un nuevo producto
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateDeliveryUseCase {

    private final DeliveryRepository productRepository;

    public Delivery execute(Delivery product) {
        log.debug("Executing CreateProductUseCase for product: {}", product.getOrderId());

        // Validar datos del producto
        if (!product.isValid()) {
            throw new InvalidDeliveryDataException("Invalid product data. Name, valid price and stock are required.");
        }

        // Guardar producto
        Delivery savedProduct = productRepository.save(product);
        log.info("Product created successfully with id: {}", savedProduct.getId());

        return savedProduct;
    }
}
