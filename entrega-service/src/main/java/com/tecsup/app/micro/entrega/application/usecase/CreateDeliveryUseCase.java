package com.tecsup.app.micro.entrega.application.usecase;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.entrega.domain.exception.InvalidDeliveryDataException;
import com.tecsup.app.micro.entrega.domain.model.Delivery;
import com.tecsup.app.micro.entrega.domain.model.Order;
import com.tecsup.app.micro.entrega.domain.model.User;
import com.tecsup.app.micro.entrega.domain.repository.DeliveryRepository;
import com.tecsup.app.micro.entrega.infrastructure.client.OrderClient;
import com.tecsup.app.micro.entrega.infrastructure.client.UserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Crear un nuevo producto
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateDeliveryUseCase {

    private final DeliveryRepository productRepository;
    private final UserClient userClient;
    private final OrderClient orderClient;

    public Delivery execute(Delivery devilery, String jwtToken) {
        log.debug("Executing CreateProductUseCase for product: {}", devilery.getOrderId());

        // Validar datos del producto
        if (!devilery.isValid()) {
            throw new InvalidDeliveryDataException("Invalid product data. Name, valid price and stock are required.");
        }

        User user = userClient.getUserById(devilery.getUserId(), jwtToken);
        if (user == null) {
            throw new InvalidDeliveryDataException("User with ID " + devilery.getUserId() + " does not exist.");
        }

        Order order = orderClient.getOrderById(devilery.getOrderId(), jwtToken);
        if (order == null) {
            throw new InvalidDeliveryDataException("Order with ID " + devilery.getOrderId() + " does not exist.");
        }

        // Guardar producto
        Delivery devilerySave = productRepository.save(devilery);
        log.info("Delivery created successfully with id: {}", devilerySave.getId());

        return devilerySave;
    }
}
