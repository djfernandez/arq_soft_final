package com.tecsup.app.micro.user.presentation.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tecsup.app.micro.user.domain.model.User;
import com.tecsup.app.micro.user.presentation.dto.CreateUserRequest;
import com.tecsup.app.micro.user.presentation.dto.UpdateUserRequest;
import com.tecsup.app.micro.user.presentation.dto.UserResponse;

/**
 * Mapper entre DTOs de presentación y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    /**
     * Convierte CreateUserRequest a User de dominio
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toDomain(CreateUserRequest request);

    /**
     * Convierte UpdateUserRequest a User de dominio
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toDomain(UpdateUserRequest request);

    /**
     * Convierte User de dominio a UserResponse
     */
    UserResponse toResponse(User user);

    /**
     * Convierte lista de Users a lista de UserResponse
     */
    List<UserResponse> toResponseList(List<User> users);
}
