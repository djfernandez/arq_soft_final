package com.tecsup.app.micro.catalogo.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.catalogo.domain.model.Catalog;
import com.tecsup.app.micro.catalogo.domain.model.User;
import com.tecsup.app.micro.catalogo.domain.repository.CatalogRepository;
import com.tecsup.app.micro.catalogo.infrastructure.client.UserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obtener productos por usuario creador
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetCatalogsByUserUseCase {

    private final CatalogRepository productRepository;
    private final UserClient userClient;

    public List<Catalog> execute(Long userId, String jwtToken) {

        // --------------------------------------------------------
        // Llama al microservicio user-service
        // --------------------------------------------------------
        // Validar que el usuario existe en userdb
        // UserDTO user = userClient.getUserById(userId);
        User user = userClient.getUserById(userId, jwtToken);

        log.info("Fetching products for user from userdb: {}", user.getName());

        // TODO : Validar existencia de usuario o lanzar excepcion

        log.debug("Executing GetCatalogsByUserUseCase for userId: {}", userId);
        return productRepository.findByCreatedBy(userId);
    }
}
