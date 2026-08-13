package com.tecsup.app.micro.catalogo.application.usecase;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.catalogo.domain.exception.InvalidCatalogDataException;
import com.tecsup.app.micro.catalogo.domain.model.Catalog;
import com.tecsup.app.micro.catalogo.domain.model.User;
import com.tecsup.app.micro.catalogo.domain.repository.CatalogRepository;
import com.tecsup.app.micro.catalogo.infrastructure.client.UserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Crear un nuevo catalogo
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateCatalogUseCase {

    private final CatalogRepository catalogRepositoryRepository;
    private final UserClient userClient; // Cliente para comunicarse con el servicio de usuarios

    public Catalog execute(Catalog catalogo, String jwtToken) {
        log.debug("Executing CreateCatalogUseCase for catalog: {}", catalogo.getName());

        // Validar datos del catalogo
        if (!catalogo.isValid()) {
            throw new InvalidCatalogDataException("Invalid catalog data. Name, valid price and stock are required.");
        }

        User user = userClient.getUserById(catalogo.getCreatedBy(), jwtToken);
        if (user == null) {
            throw new InvalidCatalogDataException("User with id " + catalogo.getCreatedBy() + " does not exist.");
        }

        // Guardar catalogo
        Catalog savedCatalog = catalogRepositoryRepository.save(catalogo);
        log.info("Catalog created successfully with id: {}", savedCatalog.getId());

        return savedCatalog;
    }
}
