package com.example.TpSpringBoot.dto.usuario;

public record UsuarioEdit(
        Long id,
        String nombre,
        String apellido,
        String mail,
        String celular,
        String contrasena,
        String rol) {
}
