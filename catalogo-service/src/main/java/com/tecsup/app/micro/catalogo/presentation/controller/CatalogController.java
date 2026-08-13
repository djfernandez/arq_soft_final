package com.tecsup.app.micro.catalogo.presentation.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.app.micro.catalogo.application.service.CatalogApplicationService;
import com.tecsup.app.micro.catalogo.domain.model.Catalog;
import com.tecsup.app.micro.catalogo.presentation.dto.CreateCatalogRequest;
import com.tecsup.app.micro.catalogo.presentation.dto.CatalogResponse;
import com.tecsup.app.micro.catalogo.presentation.dto.UpdateCatalogRequest;
import com.tecsup.app.micro.catalogo.presentation.mapper.CatalogDtoMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST de Catálogos
 */
@RestController
@RequestMapping("/api/catalogs")
@RequiredArgsConstructor
@Slf4j
public class CatalogController {

    private final CatalogApplicationService catalogApplicationService;
    private final CatalogDtoMapper catalogDtoMapper;

    /**
     * Obtiene todos los catálogos
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CatalogResponse>> getAllCatalogs() {
        log.info("REST request to get all catalogs");
        List<Catalog> catalogs = catalogApplicationService.getAllCatalogs();
        return ResponseEntity.ok(catalogDtoMapper.toResponseList(catalogs));
    }

    /**
     * Obtiene catálogos disponibles (stock > 0)
     */
    @GetMapping("/available")
    public ResponseEntity<List<CatalogResponse>> getAvailableCatalogs() {
        log.info("REST request to get available catalogs");
        List<Catalog> catalogs = catalogApplicationService.getAvailableCatalogs();
        return ResponseEntity.ok(catalogDtoMapper.toResponseList(catalogs));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CatalogResponse> getCatalogById(@PathVariable Long id,
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

        Catalog catalog = catalogApplicationService.getCatalogById(id, jwtToken);
        return ResponseEntity.ok(catalogDtoMapper.toResponse(catalog));
    }

    /**
     * Obtiene los catálogos por IDs (público)
     */
    @GetMapping("/ids")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CatalogResponse>> getCatalogsByIds(@RequestParam List<Long> ids) {
        log.info("REST request to get catalogs by ids: {}", ids);
        List<Catalog> catalogs = catalogApplicationService.getCatalogsByIds(ids);
        return ResponseEntity.ok(catalogDtoMapper.toResponseList(catalogs));
    }

    /**
     * Obtiene catálogos por usuario creador (autenticado)
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CatalogResponse>> getCatalogsByUser(@PathVariable Long userId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("REST request to get catalogs by user: {}", userId);

        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for catalog retrieval");
        }

        List<Catalog> catalogs = catalogApplicationService.getCatalogsByUser(userId, jwtToken);
        return ResponseEntity.ok(catalogDtoMapper.toResponseList(catalogs));
    }

    /**
     * Crea un nuevo catálogo (solo ADMIN)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogResponse> createCatalog(
            @Valid @RequestBody CreateCatalogRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("REST request to create catalog: {}", request.getName());

        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for catalog retrieval");
        }

        Catalog catalog = catalogDtoMapper.toDomain(request);
        Catalog createdCatalog = catalogApplicationService.createCatalog(catalog, jwtToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(catalogDtoMapper.toResponse(createdCatalog));
    }

    /**
     * Actualiza un catálogo existente (solo ADMIN)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogResponse> updateCatalog(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCatalogRequest request) {
        log.info("REST request to update catalog with id: {}", id);
        Catalog catalog = catalogDtoMapper.toDomain(request);
        Catalog updatedCatalog = catalogApplicationService.updateCatalog(id, catalog);
        return ResponseEntity.ok(catalogDtoMapper.toResponse(updatedCatalog));
    }

    /**
     * Elimina un catálogo (solo ADMIN)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCatalog(@PathVariable Long id) {
        log.info("REST request to delete catalog with id: {}", id);
        catalogApplicationService.deleteCatalog(id);
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
