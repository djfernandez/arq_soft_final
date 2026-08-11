package com.tecsup.app.micro.pago.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pago.domain.model.Catalog;
import com.tecsup.app.micro.pago.domain.repository.CatalogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllCatalogsIdsUseCase {
  private final CatalogRepository pagoRepository;

  public List<Catalog> execute(List<Long> ids) {
    return pagoRepository.findAllById(ids);
  }
}
