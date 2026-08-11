package com.tecsup.app.micro.catalogo.presentation.controller;

import com.tecsup.app.micro.catalogo.application.service.CatalogApplicationService;
import com.tecsup.app.micro.catalogo.domain.model.Catalog;
import com.tecsup.app.micro.catalogo.presentation.dto.CatalogResponse;
import com.tecsup.app.micro.catalogo.presentation.dto.CreateCatalogRequest;
import com.tecsup.app.micro.catalogo.presentation.dto.UpdateCatalogRequest;
import com.tecsup.app.micro.catalogo.presentation.mapper.CatalogDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CatalogControllerTest {

    private final CatalogApplicationService catalogApplicationService = mock(CatalogApplicationService.class);
    private final CatalogDtoMapper catalogDtoMapper = mock(CatalogDtoMapper.class);
    private final CatalogController catalogController = new CatalogController(catalogApplicationService,
            catalogDtoMapper);

    @Test
    void getAllCatalogsShouldReturnMappedResponses() {
        Catalog catalog = Catalog.builder()
                .id(1L)
                .name("Pizza")
                .description("Pizza familiar")
                .price(BigDecimal.valueOf(12.50))
                .stock(10)
                .category("Comida")
                .createdBy(100L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        CatalogResponse response = CatalogResponse.builder()
                .id(1L)
                .name("Pizza")
                .description("Pizza familiar")
                .price(BigDecimal.valueOf(12.50))
                .stock(10)
                .category("Comida")
                .createdBy(100L)
                .build();

        when(catalogApplicationService.getAllCatalogs()).thenReturn(List.of(catalog));
        when(catalogDtoMapper.toResponseList(List.of(catalog))).thenReturn(List.of(response));

        ResponseEntity<List<CatalogResponse>> result = catalogController.getAllCatalogs();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody().get(0).getName()).isEqualTo("Pizza");
        verify(catalogApplicationService).getAllCatalogs();
        verify(catalogDtoMapper).toResponseList(List.of(catalog));
    }

    @Test
    void getCatalogByIdShouldReturnMappedResponse() {
        Catalog catalog = Catalog.builder()
                .id(2L)
                .name("Hamburguesa")
                .description("Hamburguesa doble")
                .price(BigDecimal.valueOf(8.90))
                .stock(5)
                .category("Fast food")
                .createdBy(200L)
                .build();

        CatalogResponse response = CatalogResponse.builder()
                .id(2L)
                .name("Hamburguesa")
                .description("Hamburguesa doble")
                .price(BigDecimal.valueOf(8.90))
                .stock(5)
                .category("Fast food")
                .createdBy(200L)
                .build();

        when(catalogApplicationService.getCatalogById(2L, null)).thenReturn(catalog);
        when(catalogDtoMapper.toResponse(catalog)).thenReturn(response);

        ResponseEntity<CatalogResponse> result = catalogController.getCatalogById(2L, null);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getName()).isEqualTo("Hamburguesa");
        verify(catalogApplicationService).getCatalogById(2L, null);
        verify(catalogDtoMapper).toResponse(catalog);
    }

    @Test
    void createCatalogShouldReturnCreatedResponse() {
        CreateCatalogRequest request = CreateCatalogRequest.builder()
                .name("Ensalada")
                .description("Ensalada saludable")
                .price(BigDecimal.valueOf(6.50))
                .stock(15)
                .category("Saludable")
                .createdBy(300L)
                .build();

        Catalog catalog = Catalog.builder()
                .id(3L)
                .name("Ensalada")
                .description("Ensalada saludable")
                .price(BigDecimal.valueOf(6.50))
                .stock(15)
                .category("Saludable")
                .createdBy(300L)
                .build();

        CatalogResponse response = CatalogResponse.builder()
                .id(3L)
                .name("Ensalada")
                .description("Ensalada saludable")
                .price(BigDecimal.valueOf(6.50))
                .stock(15)
                .category("Saludable")
                .createdBy(300L)
                .build();

        when(catalogDtoMapper.toDomain(request)).thenReturn(catalog);
        when(catalogApplicationService.createCatalog(catalog)).thenReturn(catalog);
        when(catalogDtoMapper.toResponse(catalog)).thenReturn(response);

        ResponseEntity<CatalogResponse> result = catalogController.createCatalog(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getName()).isEqualTo("Ensalada");
        verify(catalogDtoMapper).toDomain(request);
        verify(catalogApplicationService).createCatalog(catalog);
        verify(catalogDtoMapper).toResponse(catalog);
    }

    @Test
    void updateCatalogShouldReturnUpdatedResponse() {
        UpdateCatalogRequest request = UpdateCatalogRequest.builder()
                .name("Taco")
                .description("Taco de pollo")
                .price(BigDecimal.valueOf(4.50))
                .stock(20)
                .category("Mexicano")
                .build();

        Catalog catalog = Catalog.builder()
                .id(4L)
                .name("Taco")
                .description("Taco de pollo")
                .price(BigDecimal.valueOf(4.50))
                .stock(20)
                .category("Mexicano")
                .build();

        CatalogResponse response = CatalogResponse.builder()
                .id(4L)
                .name("Taco")
                .description("Taco de pollo")
                .price(BigDecimal.valueOf(4.50))
                .stock(20)
                .category("Mexicano")
                .build();

        when(catalogDtoMapper.toDomain(request)).thenReturn(catalog);
        when(catalogApplicationService.updateCatalog(4L, catalog)).thenReturn(catalog);
        when(catalogDtoMapper.toResponse(catalog)).thenReturn(response);

        ResponseEntity<CatalogResponse> result = catalogController.updateCatalog(4L, request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getName()).isEqualTo("Taco");
        verify(catalogDtoMapper).toDomain(request);
        verify(catalogApplicationService).updateCatalog(4L, catalog);
        verify(catalogDtoMapper).toResponse(catalog);
    }

    @Test
    void deleteCatalogShouldReturnNoContent() {
        ResponseEntity<Void> result = catalogController.deleteCatalog(5L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(catalogApplicationService).deleteCatalog(5L);
    }
}
