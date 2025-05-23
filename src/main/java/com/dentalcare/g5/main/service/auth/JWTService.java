package com.dentalcare.g5.main.service.auth;

import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

public interface JWTService {
    /**
     * Extrae el nombre de usuario del token JWT.
     * @param token el token JWT
     * @return el nombre de usuario
     */
    String extractUsername(String token);

    /**
     * Extrae la fecha de expiración del token JWT.
     * @param token el token JWT
     * @return la fecha de expiración
     */
    Date extractExpiration(String token);

    /**
     * Extrae una claim específica del token JWT usando una función resolver.
     * @param token el token JWT
     * @param claimsResolver la función para resolver la claim
     * @param <T> el tipo de la claim
     * @return la claim extraída
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Genera un token JWT para un usuario.
     * @param userData los detalles del usuario (de Spring Security, o un objeto similar)
     * @return el token JWT generado
     */
    String generateToken(UsuarioDto userData);

    /**
     * Valida un token JWT.
     * Verifica si el token pertenece al usuario y si no ha expirado.
     * @param token el token JWT
     * @param userData los detalles del usuario (de Spring Security, o un objeto similar)
     * @return true si el token es válido, false en caso contrario
     */
    Boolean validateToken(String token, UsuarioDto userData);
}
