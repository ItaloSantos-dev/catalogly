package com.italo.catalogy.controller;

import com.italo.catalogy.dto.auth.LoginRequestDTO;
import com.italo.catalogy.dto.auth.RegisterRequestDTO;
import com.italo.catalogy.dto.user.UserResponseDTO;
import com.italo.catalogy.mapper.UserMapper;
import com.italo.catalogy.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(this.authService.login(loginRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        return ResponseEntity.ok(this.userMapper.modelToResponse(this.authService.register(registerRequestDTO)));
    }
}
