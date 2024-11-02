package com.example.identity_service.config.mappper;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper
{
    User userCreationRequestToUser(UserCreationRequest request);

    void updateUserFromRequest(@MappingTarget User user, UserUpdateRequest request);

    UserResponse userToUserResponse(User user);
}
