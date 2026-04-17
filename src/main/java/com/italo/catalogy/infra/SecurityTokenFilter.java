package com.italo.catalogy.infra;

import com.italo.catalogy.model.UserModel;
import com.italo.catalogy.respository.UserRepository;
import com.italo.catalogy.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class SecurityTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityTokenFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.separateToken(request);
        if (token!=null){
            String userId = this.tokenService.authenticateToken(token);
            UserModel user = this.userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new RuntimeException("Deu ruin"));
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String separateToken(HttpServletRequest request){
        String auth = request.getHeader("Authorization");
        return auth == null ? null : auth.replaceAll("Bearer ", "");
    }
}
