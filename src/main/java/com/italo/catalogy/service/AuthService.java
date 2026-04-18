package com.italo.catalogy.service;

import com.italo.catalogy.dto.auth.LoginRequestDTO;
import com.italo.catalogy.dto.auth.RegisterRequestDTO;
import com.italo.catalogy.dto.user.UserResponseDTO;
import com.italo.catalogy.mapper.UserMapper;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.respository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;



    public AuthService(UserRepository userRepository, UserService userService, AuthenticationManager authenticationManager, TokenService tokenService, UserMapper userMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String login(LoginRequestDTO loginRequestDTO){
        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        return this.tokenService.genereateToken((UserModel) auth.getPrincipal());
    }

    public UserModel register(RegisterRequestDTO registerRequestDTO){
        return this.userService.createUser(registerRequestDTO);
    }
}
