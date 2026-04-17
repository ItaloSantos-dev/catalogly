package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.auth.RegisterRequestDTO;
import com.italo.catalogy.dto.user.UserResponseDTO;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.model.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {


    public UserModel registerToModel(RegisterRequestDTO registerRequestDTO, PasswordEncoder passwordEncoder){
        UserModel newuser = new UserModel();
        newuser.setFirstName(registerRequestDTO.firstName());
        newuser.setLastName(registerRequestDTO.lastName());
        newuser.setEmail(registerRequestDTO.email());
        newuser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        newuser.setRole(UserRole.USER);
        newuser.setCreatedAt(LocalDateTime.now());
        newuser.setUpdatedAt(LocalDateTime.now());
        return newuser;
    }

    public UserResponseDTO modelToResponse(UserModel userModel){
        return new UserResponseDTO(userModel.getFirstName(), userModel.getLastName(), userModel.getRole());
    }
}
