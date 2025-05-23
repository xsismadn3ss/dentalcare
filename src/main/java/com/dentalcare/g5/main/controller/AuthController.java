package com.dentalcare.g5.main.controller;

import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.payload.auth.LoginRequest;
import com.dentalcare.g5.main.model.payload.auth.LoginResponse;
import com.dentalcare.g5.main.service.auth.JWTService;
import com.dentalcare.g5.main.service.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest payload) {
        try {
            Boolean isAuthenticated = usuarioService
                    .authenticate(payload.getUsername(), payload.getPassword());
            if (isAuthenticated) {
                UsuarioDto usuarioDto = usuarioService.findByUsername(payload.getUsername());
                String token = jwtService.generateToken(usuarioDto);
                return ResponseEntity.ok(new LoginResponse(token, usuarioDto.getUsername()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidad");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas: Usuario no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error durante el proceso de login");
        }
    }
}
