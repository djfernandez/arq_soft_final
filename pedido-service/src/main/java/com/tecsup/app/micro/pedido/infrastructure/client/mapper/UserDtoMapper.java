package com.tecsup.app.micro.pedido.infrastructure.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tecsup.app.micro.pedido.domain.model.User;
import com.tecsup.app.micro.pedido.infrastructure.client.dto.UserDTO;

/**
 * Mapper entre entidades JPA y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toDomain(UserDTO dto);

    // UserResponse toResponse(User user);

}
