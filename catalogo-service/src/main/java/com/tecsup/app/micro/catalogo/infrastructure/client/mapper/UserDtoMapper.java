package com.tecsup.app.micro.catalogo.infrastructure.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tecsup.app.micro.catalogo.domain.model.User;
import com.tecsup.app.micro.catalogo.infrastructure.client.dto.UserDTO;
import com.tecsup.app.micro.catalogo.presentation.dto.UserResponse;

/**
 * Mapper entre entidades JPA y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toDomain(UserDTO dto);

    UserResponse toResponse(User user);

}
