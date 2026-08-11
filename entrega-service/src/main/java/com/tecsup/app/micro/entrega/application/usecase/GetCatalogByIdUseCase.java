package com.tecsup.app.micro.entrega.application.usecase;

import com.tecsup.app.micro.entrega.domain.exception.CatalogNotFoundException;
import com.tecsup.app.micro.entrega.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.entrega.domain.model.Catalog;
import com.tecsup.app.micro.entrega.domain.model.User;
import com.tecsup.app.micro.entrega.domain.repository.CatalogRepository;
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
public class GetCatalogByIdUseCase {

    private final CatalogRepository productRepository;

    private final UserClient userClient;

    public Catalog execute(Long id, String jwtToken) { // NUEVO PARAMETRO
        log.debug("Executing GetProductByIdUseCase for id: {}", id);

        Catalog prod = productRepository.findById(id)
                .orElseThrow(() -> new CatalogNotFoundException(id));

        // --------------------------------------------------------
        // Llama al microservicio user-service
        // --------------------------------------------------------
        // Validar que el usuario existe en userdb
        User user = userClient.getUserById(prod.getCreatedBy(), jwtToken); // NUEVO PARAMETRO
        log.info("Fetching user from userdb: {}", user);

        if (user == null) {
            log.warn("User with id {} not found in userdb", prod.getCreatedBy());
            throw new UserNotFoundException(id);
        }

        prod.setCreatedByUser(user);

        return prod;
    }

}
