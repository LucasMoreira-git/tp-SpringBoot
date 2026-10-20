package com.example.TpSpringBoot.dto.usuario;

import java.time.LocalDateTime;

public record UsuarioDto(
        Long id,
        boolean eliminado,
        LocalDateTime createdAt,
        String nombre,
        String apellido,
        String mail,
        String celular,
        String contrasena,
        String rol) {
}
