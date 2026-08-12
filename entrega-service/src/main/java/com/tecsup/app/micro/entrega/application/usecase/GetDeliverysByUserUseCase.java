package com.tecsup.app.micro.entrega.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.entrega.domain.model.Delivery;
import com.tecsup.app.micro.entrega.domain.model.User;
import com.tecsup.app.micro.entrega.domain.repository.DeliveryRepository;
import com.tecsup.app.micro.entrega.infrastructure.client.UserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obtener productos por usuario creador
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetDeliverysByUserUseCase {

    private final DeliveryRepository productRepository;
    private final UserClient userClient;

    public List<Delivery> execute(Long userId, String jwtToken) {

        // --------------------------------------------------------
        // Llama al microservicio user-service
        // --------------------------------------------------------
        // Validar que el usuario existe en userdb
        // UserDTO user = userClient.getUserById(userId);
        User user = userClient.getUserById(userId, jwtToken);

        log.info("Fetching products for user from userdb: {}", user.getName());

        log.debug("Executing GetCatalogsByUserUseCase for userId: {}", userId);
        return productRepository.findByUserId(userId);
    }
}
