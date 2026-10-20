package com.example.TpSpringBoot.dto.categoria;

import java.time.LocalDateTime;

public record CategoriaDto(
        Long id,
        boolean eliminado,
        LocalDateTime createdAt,
        String nombre,
        String descripcion) {
}
