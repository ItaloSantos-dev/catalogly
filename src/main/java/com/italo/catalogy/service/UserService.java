package com.italo.catalogy.service;

import com.italo.catalogy.dto.auth.RegisterRequestDTO;
import com.italo.catalogy.dto.user.UserResponseDTO;
import com.italo.catalogy.mapper.UserMapper;
import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.respository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username);
    }

    public UserModel createUser(RegisterRequestDTO registerRequestDTO){
        if (this.userRepository.existsByEmail(registerRequestDTO.email()))
            throw new RuntimeException("Email ja cadastrado");
        UserModel newUser = this.userMapper.registerToModel(registerRequestDTO, passwordEncoder);
        return this.userRepository.save(newUser);
    }


}
