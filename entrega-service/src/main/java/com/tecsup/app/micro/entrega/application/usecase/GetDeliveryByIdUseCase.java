package com.tecsup.app.micro.entrega.application.usecase;

import com.tecsup.app.micro.entrega.domain.exception.DeliveryNotFoundException;
import com.tecsup.app.micro.entrega.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.entrega.domain.model.Delivery;
import com.tecsup.app.micro.entrega.domain.model.User;
import com.tecsup.app.micro.entrega.domain.repository.DeliveryRepository;
import com.tecsup.app.micro.entrega.infrastructure.client.UserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Obtener producto por ID
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetDeliveryByIdUseCase {

    private final DeliveryRepository productRepository;

    private final UserClient userClient;

    public Delivery execute(Long id, String jwtToken) { // NUEVO PARAMETRO
        log.debug("Executing GetProductByIdUseCase for id: {}", id);

        Delivery prod = productRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));

        // --------------------------------------------------------
        // Llama al microservicio user-service
        // --------------------------------------------------------
        // Validar que el usuario existe en userdb
        User user = userClient.getUserById(prod.getUserId(), jwtToken); // NUEVO PARAMETRO
        log.info("Fetching user from userdb: {}", user);

        if (user == null) {
            log.warn("User with id {} not found in userdb", prod.getUserId());
            throw new UserNotFoundException(id);
        }

        prod.setCreatedByUser(user);

        return prod;
    }

}
