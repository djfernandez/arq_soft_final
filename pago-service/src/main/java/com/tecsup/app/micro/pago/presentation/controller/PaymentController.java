package com.tecsup.app.micro.pago.presentation.controller;

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

import com.tecsup.app.micro.pago.application.service.PaymentApplicationService;
import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.presentation.dto.CreatePaymentRequest;
import com.tecsup.app.micro.pago.presentation.dto.PaymentResponse;
import com.tecsup.app.micro.pago.presentation.dto.UpdatePaymentRequest;
import com.tecsup.app.micro.pago.presentation.mapper.PaymentDtoMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST de Catálogos
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;
    private final PaymentDtoMapper paymentDtoMapper;

    /**
     * Obtiene todos los pagos
     */
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        log.info("REST request to get all payments");
        List<Payment> payments = paymentApplicationService.getAllPayments();
        return ResponseEntity.ok(paymentDtoMapper.toResponseList(payments));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("REST request to get payment by id: {}", id);

        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for catalog retrieval");
        }

        log.info("jwtToken extracted for catalog retrieval: {}", jwtToken != null);

        Payment payment = paymentApplicationService.getPaymentById(id, jwtToken);
        return ResponseEntity.ok(paymentDtoMapper.toResponse(payment));
    }

    /**
     * Obtiene catálogos por usuario creador (autenticado)
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByUser(@PathVariable Long userId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("REST request to get payments by user: {}", userId);

        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for catalog retrieval");
        }

        List<Payment> payments = paymentApplicationService.getPaymentsByUserId(userId, jwtToken);
        return ResponseEntity.ok(paymentDtoMapper.toResponseList(payments));
    }

    /**
     * Crea un nuevo catálogo (solo ADMIN)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("REST request to create payment: {}", request.getUserId());
        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for catalog retrieval");
        }

        Payment payment = paymentDtoMapper.toDomain(request);
        Payment createdCatalog = paymentApplicationService.createPayment(payment, jwtToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentDtoMapper.toResponse(createdCatalog));
    }

    /**
     * Actualiza un catálogo existente (solo ADMIN)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePaymentRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("REST request to update payment with id: {}", id);
        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for payment retrieval");
        }
        Payment payment = paymentDtoMapper.toDomain(request);
        Payment updatedCatalog = paymentApplicationService.updatePayment(id, payment, jwtToken);
        return ResponseEntity.ok(paymentDtoMapper.toResponse(updatedCatalog));
    }

    /**
     * Elimina un catálogo (solo ADMIN)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        log.info("REST request to delete payment with id: {}", id);
        paymentApplicationService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint de salud
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payment Service running with Clean Architecture!");
    }
}
