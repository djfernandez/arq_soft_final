package com.tecsup.app.micro.entrega.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.app.micro.entrega.application.service.DeliveryApplicationService;
import com.tecsup.app.micro.entrega.domain.model.Delivery;
import com.tecsup.app.micro.entrega.presentation.dto.CreateDeliveryRequest;
import com.tecsup.app.micro.entrega.presentation.dto.DeliveryResponse;
import com.tecsup.app.micro.entrega.presentation.dto.UpdateDeliveryRequest;
import com.tecsup.app.micro.entrega.presentation.mapper.DeliveryDtoMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST de Catálogos
 */
@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryApplicationService deliveryApplicationService;
    private final DeliveryDtoMapper deliveryDtoMapper;

    /**
     * Obtiene todos los catálogos
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DeliveryResponse>> getAllCatalogs() {
        log.info("REST request to get all catalogs");
        List<Delivery> catalogs = deliveryApplicationService.getAllCatalogs();
        return ResponseEntity.ok(deliveryDtoMapper.toResponseList(catalogs));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DeliveryResponse> getCatalogById(@PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("REST request to get catalog by id: {}", id);

        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for catalog retrieval");
        }

        log.info("jwtToken extracted for catalog retrieval: {}", jwtToken != null);

        Delivery catalog = deliveryApplicationService.getCatalogById(id, jwtToken);
        return ResponseEntity.ok(deliveryDtoMapper.toResponse(catalog));
    }

    /**
     * Obtiene catálogos por usuario creador (autenticado)
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DeliveryResponse>> getCatalogsByUser(@PathVariable Long userId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("REST request to get catalogs by user: {}", userId);

        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for catalog retrieval");
        }

        List<Delivery> catalogs = deliveryApplicationService.getCatalogsByUser(userId, jwtToken);
        return ResponseEntity.ok(deliveryDtoMapper.toResponseList(catalogs));
    }

    /**
     * Crea un nuevo catálogo (solo ADMIN)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeliveryResponse> createCatalog(@Valid @RequestBody CreateDeliveryRequest request) {
        log.info("REST request to create catalog: {}", request.getOrderId());
        Delivery catalog = deliveryDtoMapper.toDomain(request);
        Delivery createdCatalog = deliveryApplicationService.createCatalog(catalog);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deliveryDtoMapper.toResponse(createdCatalog));
    }

    /**
     * Actualiza un catálogo existente (solo ADMIN)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeliveryResponse> updateCatalog(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDeliveryRequest request) {
        log.info("REST request to update catalog with id: {}", id);
        Delivery catalog = deliveryDtoMapper.toDomain(request);
        Delivery updatedCatalog = deliveryApplicationService.updateCatalog(id, catalog);
        return ResponseEntity.ok(deliveryDtoMapper.toResponse(updatedCatalog));
    }

    /**
     * Elimina un catálogo (solo ADMIN)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCatalog(@PathVariable Long id) {
        log.info("REST request to delete catalog with id: {}", id);
        deliveryApplicationService.deleteCatalog(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint de salud
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Catalog Service running with Clean Architecture!");
    }
}
