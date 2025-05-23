package com.dentalcare.g5.main.config.security;

import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import com.dentalcare.g5.main.repository.usuario.UsuarioRepository;
import com.dentalcare.g5.main.service.auth.JWTService;
import com.dentalcare.g5.main.service.usuario.UsuarioService;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${server.base:/api}")
    private String serverBase;

    @Value("${server.version}:/v1")
    private String serverVersion;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // construir ruta de login para excluirla del filtro
        String loginPath = serverBase + serverVersion + "auth/login";
        if (request.getServletPath().equals(loginPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            username = jwtService.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsuarioDto usuarioDto = usuarioService.findByUsername(username);

                if (jwtService.validateToken(jwt, usuarioDto)) {
                    Usuario usuario = usuarioRepository.finByUsername(username)
                            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
                    UsernamePasswordAuthenticationToken authToken = getUsernamePasswordAuthenticationToken(usuario, usuarioDto);
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    if(jwtService.isTokenNearingExpiration(jwt)){
                        String newJwt = jwtService.generateToken(usuarioDto);
                        response.setHeader("Authorization","Bearer " + newJwt);
                        logger.info("JWT token renewed for user: " + username);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("JWT Token processing error: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(Usuario usuario, UsuarioDto usuarioDto) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                (usuario.getRol() != null && usuario.getRol().getNombre() != null) ?
                        usuario.getRol().getNombre() : "ROLE_DEFAULT"
        );

        return new UsernamePasswordAuthenticationToken(
                usuarioDto,
                null,
                Collections.singletonList(authority)
        );
    }
}
