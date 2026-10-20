package com.example.TpSpringBoot.dto.usuario;

public record UsuarioCreate(
        String nombre,
        String apellido,
        String mail,
        String celular,
        String contrasena,
        String rol) {
}
