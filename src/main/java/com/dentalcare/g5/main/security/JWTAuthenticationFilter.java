package com.dentalcare.g5.main.security;

import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.service.auth.JWTService;
import com.dentalcare.g5.main.service.usuario.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UsuarioService usuarioService;

    @Value("/${server.base}/${server.version}")
    private String path_prefix;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // Excluir rutas específicas
        if (path.equals(path_prefix + "/auth/login") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        } else {
            final String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing or invalid Authorization header");
                return;
            }

            final String jwt = authHeader.substring(7);
            log.info(jwt);
            final String username = jwtService.extractUsername(jwt);
            log.info(username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsuarioDto userData = usuarioService.findByUsername(username);
                boolean isTokenValid = jwtService.validateToken(jwt, userData);
                log.info("token valido: {}", isTokenValid);
                if (isTokenValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userData, null, new ArrayList<>() // puedes poner roles aquí si los usas
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    if (jwtService.isTokenNearingExpiration(jwt)) {
                        String nuevoToken = jwtService.generateToken(userData);
                        response.setHeader("Authorization", "Bearer " + nuevoToken);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token inválido");
                    return;
                }
            }

            filterChain.doFilter(request, response);

        }
    }
}
