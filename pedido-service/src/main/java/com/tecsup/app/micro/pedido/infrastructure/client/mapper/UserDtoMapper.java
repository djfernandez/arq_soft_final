package com.tecsup.app.micro.pedido.infrastructure.client.mapper;

import org.mapstruct.Mapper;

import com.tecsup.app.micro.pedido.infrastructure.client.dto.UserDTO;

/**
 * Mapper entre entidades JPA y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDTO toDomain(UserDTO dto);

    // UserResponse toResponse(User user);

}
