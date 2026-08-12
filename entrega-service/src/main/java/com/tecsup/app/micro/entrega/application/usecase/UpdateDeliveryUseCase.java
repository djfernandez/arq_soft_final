package com.tecsup.app.micro.entrega.application.usecase;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.entrega.domain.exception.DeliveryNotFoundException;
import com.tecsup.app.micro.entrega.domain.model.Delivery;
import com.tecsup.app.micro.entrega.domain.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Actualizar un producto existente
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateDeliveryUseCase {

    private final DeliveryRepository productRepository;

    public Delivery execute(Long id, Delivery delivery) {
        log.debug("Executing UpdateProductUseCase for id: {}", id);

        // Verificar que el producto existe
        Delivery existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));

        // Actualizar campos
        existingProduct.setStatus(delivery.getStatus());

        // Guardar cambios
        Delivery updatedProduct = productRepository.save(existingProduct);
        log.info("Product updated successfully with id: {}", updatedProduct.getId());

        return updatedProduct;
    }
}
